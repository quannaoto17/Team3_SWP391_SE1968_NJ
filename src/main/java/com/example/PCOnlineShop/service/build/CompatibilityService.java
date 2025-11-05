package com.example.PCOnlineShop.service.build;


import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.model.build.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompatibilityService {

    // Kiểm tra tính tương thích giữa các linh kiện với mainboard
    public  boolean checkMainboardCompatibility(BuildItemDto buildItem, Mainboard mainboard) {
        // Kiểm tra CPU socket
        CPU cpu = buildItem.getCpu();
        if (cpu != null && !mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }

        // Kiểm tra GPU PCIe version
        GPU gpu = buildItem.getGpu();
        if (gpu != null && parseVersion(mainboard.getPcieVersion())
                < parseVersion(gpu.getPcieVersion())) {
            return false;
        }

        // Kiểm tra Memory compatibility (type, slots, speed)
        Memory memory = buildItem.getMemory();
        if (memory != null) {
            if (!mainboard.getMemoryType().equals(memory.getType())) {
                return false; // DDR4 vs DDR5 không tương thích
            }
            if (memory.getModules() > mainboard.getMemorySlots()) {
                return false; // Số thanh RAM vượt quá số khe
            }
            if (memory.getSpeed() > mainboard.getMaxMemorySpeed()) {
                return false; // Tốc độ RAM vượt quá hỗ trợ
            }
        }

        // Kiểm tra Storage compatibility (M.2 và SATA)
        Storage storage = buildItem.getStorage();
        if (storage != null) {
            if ("NVMe".equalsIgnoreCase(storage.getInterfaceType())) {
                if (mainboard.getM2Slots() <= 0) {
                    return false; // Mainboard không có khe M.2 cho NVMe
                }
            } else if ("SATA".equalsIgnoreCase(storage.getInterfaceType())) {
                if (mainboard.getSataPorts() <= 0) {
                    return false; // Mainboard không có cổng SATA
                }
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với CPU
    public boolean checkCpuCompatibility(BuildItemDto buildItem, CPU cpu) {
        // Kiểm tra socket với mainboard
        Mainboard mainboard = buildItem.getMainboard();
        if (mainboard != null && !mainboard.getSocket().equals(cpu.getSocket())) {
            return false;
        }

        // Kiểm tra tương thích với Memory đã chọn
        Memory memory = buildItem.getMemory();
        if (memory != null) {
            // Kiểm tra tốc độ RAM không vượt quá CPU hỗ trợ
            if (memory.getSpeed() > cpu.getMaxMemorySpeed()) {
                return false; // RAM quá nhanh cho CPU này
            }

            // Kiểm tra số modules phù hợp với memory channels của CPU
            if (cpu.getMemoryChannels() != null && memory.getModules() > 0) {
                // Số modules phải là bội số của số channels để tối ưu
                // Hoặc ít nhất modules phải <= channels * 2 (thường mỗi channel có 2 slots)
                if (memory.getModules() > cpu.getMemoryChannels() * 2) {
                    return false; // Quá nhiều thanh RAM cho CPU này
                }
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với GPU
    public boolean checkGpuCompatibility(BuildItemDto buildItem, GPU gpu) {
        // Kiểm tra PCIe version với CPU
        CPU cpu = buildItem.getCpu();
        if (cpu != null && parseVersion(cpu.getPcieVersion()) < parseVersion(gpu.getPcieVersion())) {
            return false;
        }

        // Kiểm tra PCIe version với mainboard
        Mainboard mainboard = buildItem.getMainboard();
        if (mainboard != null && parseVersion(mainboard.getPcieVersion()) < parseVersion(gpu.getPcieVersion())) {
            return false;
        }

        // Kiểm tra độ dài GPU với case
        Case pcCase = buildItem.getPcCase();
        if (pcCase != null && pcCase.getGpuMaxLength() < gpu.getLength()) {
            return false; // GPU quá dài cho case
        }

        // Kiểm tra công suất PSU (GPU thường cần 150-450W)
        PowerSupply psu = buildItem.getPowerSupply();
        if (psu != null) {
            int totalTdp = calculateTotalTdp(buildItem);
            if (psu.getWattage() < totalTdp) {
                return false; // PSU không đủ mạnh
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với Case
    public boolean checkCaseCompatibility(BuildItemDto buildItem, Case pcCase) {
        Mainboard mainboard = buildItem.getMainboard();
        GPU gpu = buildItem.getGpu();
        Cooling cooling = buildItem.getCooling();
        PowerSupply powerSupply = buildItem.getPowerSupply();

        // Kiểm tra tương thích form factor với mainboard
        if (mainboard != null) {
            if (!isCaseFormFactorCompatible(pcCase.getFormFactor(), mainboard.getFormFactor())) {
                return false;
            }
        }
        
        // Kiểm tra độ dài GPU có phù hợp với case
        if (gpu != null && pcCase.getGpuMaxLength() > 0) {
            if (pcCase.getGpuMaxLength() < gpu.getLength()) {
                return false; // GPU quá dài cho case
            }
        }

        // Kiểm tra tương thích cooling với case
        if (cooling != null) {
            // Kiểm tra chiều cao tản nhiệt không khí
            if ("Air".equalsIgnoreCase(cooling.getType())) {
                if (pcCase.getCpuMaxCoolerHeight() > 0 && cooling.getFanSize() > 0) {
                    // FanSize có thể dùng để ước lượng chiều cao (fan lớn thường cao hơn)
                    int estimatedHeight = cooling.getFanSize() + 30; // Ước tính chiều cao
                    if (estimatedHeight > pcCase.getCpuMaxCoolerHeight()) {
                        return false; // Tản nhiệt quá cao cho case
                    }
                }
            }

            // Kiểm tra kích thước radiator cho tản nhiệt nước
            if ("Liquid".equalsIgnoreCase(cooling.getType()) && cooling.getRadiatorSize() > 0) {
                int radiatorSize = cooling.getRadiatorSize();

                // Kiểm tra case có đủ không gian cho radiator không
                if (pcCase.getFormFactor().toUpperCase().contains("MINI")) {
                    if (radiatorSize > 240) {
                        return false; // Radiator quá lớn cho case nhỏ
                    }
                } else if (pcCase.getFormFactor().toUpperCase().contains("MICRO")) {
                    if (radiatorSize > 280) {
                        return false; // Radiator quá lớn cho case Micro-ATX
                    }
                }
                // ATX và Full Tower thường hỗ trợ tất cả kích thước radiator
            }
        }

        if (powerSupply != null) {
            // Kiểm tra tương thích PSU form factor với case
            if (pcCase.getPsuFormFactor() != null && powerSupply.getFormFactor() != null) {
                if (!isPsuFormFactorCompatible(pcCase.getPsuFormFactor(), powerSupply.getFormFactor())) {
                    return false; // PSU form factor không tương thích với case
                }
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với Power Supply
    public boolean checkPowerSupplyCompatibility(BuildItemDto buildItem, PowerSupply psu) {
        // Kiểm tra công suất PSU có đủ cho tổng TDP
        int totalTdp = calculateTotalTdp(buildItem);
        if (psu.getWattage() < totalTdp) {
            return false; // PSU không đủ mạnh
        }

        // Kiểm tra form factor PSU với case
        Case pcCase = buildItem.getPcCase();
        if (pcCase != null && pcCase.getPsuFormFactor() != null && psu.getFormFactor() != null) {
            // Kiểm tra xem case có hỗ trợ PSU form factor này không
            if (!isPsuFormFactorCompatible(pcCase.getPsuFormFactor(), psu.getFormFactor())) {
                return false; // PSU form factor không tương thích với case
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với Memory
    public boolean checkMemoryCompatibility(BuildItemDto buildItem, Memory memory) {
        Mainboard mainboard = buildItem.getMainboard();
        CPU cpu = buildItem.getCpu();

        // Kiểm tra với mainboard nếu đã chọn
        if (mainboard != null) {
            // Kiểm tra loại memory (DDR4 vs DDR5)
            if (!mainboard.getMemoryType().equals(memory.getType())) {
                return false; // DDR4 và DDR5 không tương thích
            }

            // Kiểm tra số lượng modules với memory slots
            if (memory.getModules() > mainboard.getMemorySlots()) {
                return false; // Số thanh RAM vượt quá số khe trên mainboard
            }

            // Kiểm tra tốc độ RAM
            if (memory.getSpeed() > mainboard.getMaxMemorySpeed()) {
                return false; // Tốc độ RAM vượt quá hỗ trợ của mainboard
            }
        }

        // Kiểm tra với CPU nếu đã chọn
        if (cpu != null) {
            // Kiểm tra tốc độ RAM không vượt quá CPU hỗ trợ
            if (memory.getSpeed() > cpu.getMaxMemorySpeed()) {
                return false; // RAM quá nhanh cho CPU này
            }

            // Kiểm tra số modules phù hợp với memory channels của CPU
            if (cpu.getMemoryChannels() != null && memory.getModules() > 0) {
                // Số modules phải phù hợp với số channels
                if (memory.getModules() > cpu.getMemoryChannels() * 2) {
                    return false; // Quá nhiều thanh RAM cho CPU này
                }

                // Ưu tiên cấu hình tối ưu: modules phải là bội số của channels
                // Nhưng cho phép 1 module trong trường hợp đặc biệt
                if (memory.getModules() > 1 && memory.getModules() % cpu.getMemoryChannels() != 0) {
                    // Cảnh báo: không tối ưu nhưng vẫn cho phép
                    // Bạn có thể log warning ở đây nếu cần
                }
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với Storage
    public boolean checkStorageCompatibility(BuildItemDto buildItem, Storage storage) {
        Mainboard mainboard = buildItem.getMainboard();
        if (mainboard == null) {
            return true; // Không có mainboard để kiểm tra
        }

        // Kiểm tra interface type
        if ("NVMe".equalsIgnoreCase(storage.getInterfaceType())) {
            if (mainboard.getM2Slots() <= 0) {
                return false; // Mainboard không có khe M.2 cho NVMe
            }
        } else if ("SATA".equalsIgnoreCase(storage.getInterfaceType())) {
            if (mainboard.getSataPorts() <= 0) {
                return false; // Mainboard không có cổng SATA
            }
        }

        return true;
    }

    // Kiểm tra tính tương thích giữa các linh kiện với Cooling
    public boolean checkCoolingCompatibility(BuildItemDto buildItem, Cooling cooling) {
        Case pcCase = buildItem.getPcCase();

        if (pcCase == null) {
            return true; // Không có case để kiểm tra
        }

        // Kiểm tra tản nhiệt không khí (Air cooling)
        if ("Air".equalsIgnoreCase(cooling.getType())) {
            if (pcCase.getCpuMaxCoolerHeight() > 0 && cooling.getFanSize() > 0) {
                // FanSize có thể dùng để ước lượng chiều cao
                // Thông thường: 120mm fan ~ 150-160mm height, 140mm fan ~ 165-170mm height
                int estimatedHeight = cooling.getFanSize() + 30; // Ước tính chiều cao
                if (estimatedHeight > pcCase.getCpuMaxCoolerHeight()) {
                    return false; // Tản nhiệt quá cao cho case
                }
            }
        }

        // Kiểm tra tản nhiệt nước (Liquid cooling)
        if ("Liquid".equalsIgnoreCase(cooling.getType()) && cooling.getRadiatorSize() > 0) {
            int radiatorSize = cooling.getRadiatorSize();

            // Kiểm tra case có đủ không gian cho radiator không
            // Case nhỏ (Mini-ITX) thường chỉ hỗ trợ radiator 120-240mm
            // Case Mid Tower (ATX) hỗ trợ 240-360mm
            // Case Full Tower hỗ trợ 360-420mm
            if (pcCase.getFormFactor().toUpperCase().contains("MINI")) {
                if (radiatorSize > 240) {
                    return false; // Radiator quá lớn cho case nhỏ
                }
            } else if (pcCase.getFormFactor().toUpperCase().contains("MICRO")) {
                if (radiatorSize > 280) {
                    return false; // Radiator quá lớn cho case Micro-ATX
                }
            }
            // ATX và Full Tower thường hỗ trợ tất cả kích thước radiator
        }

        return true;
    }


    private boolean isCaseFormFactorCompatible(String caseFormFactor, String mbFormFactor) {
        caseFormFactor = caseFormFactor.toUpperCase();
        mbFormFactor = mbFormFactor.toUpperCase();
        
        // Case ATX có thể chứa được tất cả loại mainboard
        if (caseFormFactor.contains("ATX")) {
            return true;
        }
        
        // Case Micro-ATX có thể chứa Micro-ATX và Mini-ITX
        if (caseFormFactor.contains("MICRO")) {
            return mbFormFactor.contains("MICRO") || mbFormFactor.contains("MINI");
        }
        
        // Case Mini-ITX chỉ có thể chứa Mini-ITX
        if (caseFormFactor.contains("MINI")) {
            return mbFormFactor.contains("MINI");
        }
        
        return caseFormFactor.equals(mbFormFactor);
    }

    // Tính tổng TDP của tất cả linh kiện trong build
    private int calculateTotalTdp(BuildItemDto buildItem) {
        int totalTdp = 0;

        // CPU TDP
        if (buildItem.getCpu() != null) {
            totalTdp += buildItem.getCpu().getTdp();
        }

        // GPU TDP
        if (buildItem.getGpu() != null) {
            totalTdp += buildItem.getGpu().getTdp();
        }

        // Memory TDP
        if (buildItem.getMemory() != null) {
            totalTdp += buildItem.getMemory().getTdp();
        }

        // Storage: SSD/HDD thường tiêu thụ 5-10W, thêm mặc định 10W
        if (buildItem.getStorage() != null) {
            totalTdp += 10; // Thêm 10W cho storage
        }

        // Cooling TDP (quạt/pump)
        if (buildItem.getCooling() != null) {
            totalTdp += buildItem.getCooling().getTdp();
        }

        // Thêm 20% buffer để đảm bảo an toàn
        return (int) (totalTdp * 1.2);
    }

    public static double parseVersion(String version) {
        if (version == null) return 0.0;
        try {
            return Double.parseDouble(version.replace("PCIe", "")
                    .replace(" ", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    /**
     * Kiểm tra tương thích PSU form factor với case
     * Case ATX hỗ trợ: ATX, SFX-L, SFX, TFX (tất cả)
     * Case SFX hỗ trợ: SFX, TFX (các loại nhỏ hơn)
     * Case TFX hỗ trợ: TFX (chỉ TFX)
     */
    private boolean isPsuFormFactorCompatible(String caseFormFactor, String psuFormFactor) {
        if (caseFormFactor == null || psuFormFactor == null) {
            return true; // Không có thông tin thì cho phép
        }

        String caseFF = caseFormFactor.toUpperCase().trim();
        String psuFF = psuFormFactor.toUpperCase().trim();

        // Case ATX hỗ trợ tất cả PSU form factor
        if (caseFF.contains("ATX")) {
            return true;
        }

        // Case SFX-L hỗ trợ: SFX-L, SFX, TFX
        if (caseFF.contains("SFX-L")) {
            return psuFF.contains("SFX") || psuFF.contains("TFX");
        }

        // Case SFX hỗ trợ: SFX, TFX
        if (caseFF.contains("SFX")) {
            return psuFF.contains("SFX") || psuFF.contains("TFX");
        }

        // Case TFX chỉ hỗ trợ: TFX
        if (caseFF.contains("TFX")) {
            return psuFF.contains("TFX");
        }

        // Mặc định: chỉ cho phép khi form factor khớp chính xác
        return caseFF.equals(psuFF);
    }

    /**
     * Kiểm tra tính tương thích của toàn bộ build
     * Trả về danh sách các thông báo lỗi (empty list nếu tất cả đều tương thích)
     */
    public java.util.List<String> validateFullBuild(BuildItemDto buildItem) {
        java.util.List<String> errors = new java.util.ArrayList<>();

        // Kiểm tra Mainboard với các linh kiện đã chọn
        if (buildItem.getMainboard() != null) {
            if (!checkMainboardCompatibility(buildItem, buildItem.getMainboard())) {
                errors.add("Mainboard không tương thích với một hoặc nhiều linh kiện đã chọn");
            }
        }

        // Kiểm tra CPU với các linh kiện đã chọn
        if (buildItem.getCpu() != null) {
            if (!checkCpuCompatibility(buildItem, buildItem.getCpu())) {
                errors.add("CPU không tương thích với Mainboard hoặc Memory đã chọn");
            }
        }

        // Kiểm tra GPU với các linh kiện đã chọn
        if (buildItem.getGpu() != null) {
            if (!checkGpuCompatibility(buildItem, buildItem.getGpu())) {
                errors.add("GPU không tương thích với CPU, Mainboard, Case hoặc PSU đã chọn");
            }
        }

        // Kiểm tra Memory với các linh kiện đã chọn
        if (buildItem.getMemory() != null) {
            if (!checkMemoryCompatibility(buildItem, buildItem.getMemory())) {
                errors.add("Memory không tương thích với CPU hoặc Mainboard đã chọn");
            }
        }

        // Kiểm tra Storage với các linh kiện đã chọn
        if (buildItem.getStorage() != null) {
            if (!checkStorageCompatibility(buildItem, buildItem.getStorage())) {
                errors.add("Storage không tương thích với Mainboard đã chọn");
            }
        }

        // Kiểm tra Case với các linh kiện đã chọn
        if (buildItem.getPcCase() != null) {
            if (!checkCaseCompatibility(buildItem, buildItem.getPcCase())) {
                errors.add("Case không tương thích với Mainboard hoặc GPU đã chọn");
            }
        }

        // Kiểm tra PSU với các linh kiện đã chọn
        if (buildItem.getPowerSupply() != null) {
            if (!checkPowerSupplyCompatibility(buildItem, buildItem.getPowerSupply())) {
                errors.add("PSU không đủ công suất hoặc không tương thích với Case đã chọn");
            }
        }

        // Kiểm tra Cooling với các linh kiện đã chọn
        if (buildItem.getCooling() != null) {
            if (!checkCoolingCompatibility(buildItem, buildItem.getCooling())) {
                errors.add("Cooling không tương thích với Case đã chọn");
            }
        }

        return errors;
    }

    /**
     * Kiểm tra xem một linh kiện cụ thể có tương thích với build hiện tại không
     * Trả về true nếu tương thích, false nếu không
     */
    public boolean isComponentCompatibleWithBuild(BuildItemDto buildItem, Object component) {
        if (component instanceof CPU) {
            return checkCpuCompatibility(buildItem, (CPU) component);
        } else if (component instanceof GPU) {
            return checkGpuCompatibility(buildItem, (GPU) component);
        } else if (component instanceof Mainboard) {
            return checkMainboardCompatibility(buildItem, (Mainboard) component);
        } else if (component instanceof Memory) {
            return checkMemoryCompatibility(buildItem, (Memory) component);
        } else if (component instanceof Storage) {
            return checkStorageCompatibility(buildItem, (Storage) component);
        } else if (component instanceof Case) {
            return checkCaseCompatibility(buildItem, (Case) component);
        } else if (component instanceof PowerSupply) {
            return checkPowerSupplyCompatibility(buildItem, (PowerSupply) component);
        } else if (component instanceof Cooling) {
            return checkCoolingCompatibility(buildItem, (Cooling) component);
        }
        return true; // Mặc định cho phép nếu không xác định được loại
    }
}
