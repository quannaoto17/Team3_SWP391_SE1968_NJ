USE pconlineshop;


INSERT INTO category (category_name, description, display_order, created_at) VALUES
('Mainboard', 'Motherboard / Bo mạch chủ cho PC', 1, CURDATE()),
('CPU', 'Central Processing Unit - bộ vi xử lý', 2, CURDATE()),
('GPU', 'Graphics Processing Unit - card đồ họa', 3, CURDATE()),
('Memory', 'RAM - bộ nhớ trong', 4, CURDATE()),
('Storage', 'Ổ cứng HDD / SSD', 5, CURDATE()),
('Case', 'Vỏ máy tính PC Case', 6, CURDATE()),
('Power Supply', 'PSU - Bộ nguồn máy tính', 7, CURDATE()),
('Cooling', 'Hệ thống tản nhiệt CPU/GPU', 8, CURDATE()),
('Fan', 'Quạt case, fan phụ trợ', 9, CURDATE()),
('Other', 'Các linh kiện phụ trợ khác', 10, CURDATE());


-- Brand
INSERT INTO brand (name, description, website) VALUES
('ASUS', 'ASUS - nhà sản xuất phần cứng máy tính hàng đầu', 'https://www.asus.com'),
('MSI', 'MSI - thương hiệu gaming nổi tiếng', 'https://www.msi.com'),
('Gigabyte', 'Gigabyte - hãng phần cứng máy tính', 'https://www.gigabyte.com'),
('Intel', 'Intel - CPU, chipset, và nhiều sản phẩm khác', 'https://www.intel.com'),
('AMD', 'AMD - CPU và GPU nổi tiếng', 'https://www.amd.com'),
('NVIDIA', 'NVIDIA - hãng GPU nổi tiếng', 'https://www.nvidia.com'),
('Corsair', 'Corsair - RAM, PSU, case và cooling', 'https://www.corsair.com'),
('G.Skill', 'G.Skill - RAM hiệu năng cao', 'https://www.gskill.com'),
('Kingston', 'Kingston - RAM và SSD phổ biến', 'https://www.kingston.com'),
('Cooler Master', 'Cooler Master - chuyên case và cooling', 'https://www.coolermaster.com'),
('Seagate', 'Seagate - ổ cứng HDD/SSD', 'https://www.seagate.com'),
('Western Digital', 'Western Digital - HDD/SSD nổi tiếng', 'https://www.westerndigital.com');

-- ======================
-- CPU (10 sản phẩm)
-- ======================

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i3-12100F', 120.00, 1, '4C/8T, entry level', 'Base 3.3GHz, Turbo 4.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 60, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i5-12400F', 180.00, 1, '6C/12T, budget gaming', 'Base 2.5GHz, Turbo 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i7-12700K', 350.00, 1, '12C/20T, high performance', 'Base 3.6GHz, Turbo 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 128, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i9-12900K', 550.00, 1, '16C/24T flagship', 'Unlocked OC');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 128, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 5 5600X', 200.00, 1, '6C/12T gaming CPU', 'Base 3.7GHz, Turbo 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 7 5800X3D', 350.00, 1, '8C/16T, 3D V-Cache', 'Boost gaming perf');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 5950X', 600.00, 1, '16C/32T', 'High-end productivity');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 5 7600X', 250.00, 1, '6C/12T, AM5 platform', 'DDR5 support');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 105, 128, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 7 7700X', 350.00, 1, '8C/16T AM5 DDR5', 'Boost 5.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 105, 128, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 7950X', 700.00, 1, '16C/32T AM5 high-end', 'DDR5, PCIe 5.0');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 170, 128, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i3-13100F', 140.00, 1, '4C/8T, budget CPU', 'Turbo 4.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 128, 2, 'PCIe 5.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i5-13400F', 200.00, 1, '10C/16T midrange', 'Turbo 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 128, 2, 'PCIe 5.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i7-13700K', 410.00, 1, '16C/24T high performance', 'Turbo 5.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i9-13900K', 580.00, 1, '24C/32T flagship', 'Turbo 5.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i5-14600K', 320.00, 1, '14th Gen 14C/20T', 'Gaming & productivity');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i7-14700K', 460.00, 1, '14th Gen 20C/28T', 'High-end gaming CPU');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'Intel Core i9-14900K', 650.00, 1, '14th Gen 24C/32T flagship', 'Turbo 6.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 5 5500', 130.00, 1, '6C/12T budget CPU', 'Base 3.6GHz, Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 128, 2, 'PCIe 3.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 5 5600G', 160.00, 1, '6C/12T APU with Vega GPU', 'Great for budget builds');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 128, 2, 'PCIe 3.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 7 5700X', 250.00, 1, '8C/16T efficient', 'Boost 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 5900X', 380.00, 1, '12C/24T beast', 'Boost 4.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 128, 2, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 7950X3D', 750.00, 1, '16C/32T 3D V-Cache', 'For gaming & productivity');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 120, 192, 2, 'PCIe 5.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 7 7700', 320.00, 1, '8C/16T Zen 4', 'Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 65, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 7 7800X3D', 430.00, 1, '8C/16T 3D V-Cache', 'Best gaming CPU');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 120, 192, 2, 'PCIe 5.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 7900X', 450.00, 1, '12C/24T AM5', 'Boost 5.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 120, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 9 7950X', 600.00, 1, '16C/32T AM5', 'Boost 5.7GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 170, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen 5 7600', 230.00, 1, '6C/12T Zen 4', 'DDR5 support');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 65, 192, 2, 'PCIe 5.0', 1);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen Threadripper 3970X', 1800.00, 1, '32C/64T workstation', 'sTRX4 socket');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'sTRX4', 280, 512, 4, 'PCIe 4.0', 0);

INSERT INTO Product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 5, 'AMD Ryzen Threadripper 3990X', 3500.00, 1, '64C/128T monster', 'For extreme workstation');
INSERT INTO cpu (product_id, socket, tdp, max_memory_size, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'sTRX4', 280, 512, 4, 'PCIe 4.0', 0);



-- ======================
-- GPU (10 sản phẩm)
-- ======================
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 3060', 300.00, 1, '12GB VRAM, midrange', 'Good 1080p/1440p');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 170, 'PCIe 4.0 x16', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 3070', 450.00, 1, '8GB VRAM', '1440p Ultra');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 220, 'PCIe 4.0 x16', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 3080', 650.00, 1, '10GB VRAM', '4K Gaming');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 10, 'GDDR6X', 320, 'PCIe 4.0 x16', 285);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 3090', 1200.00, 1, '24GB VRAM', 'Extreme 4K/8K gaming');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 350, 'PCIe 4.0 x16', 313);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 4060', 299.00, 1, '8GB VRAM', 'DLSS3 support');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 115, 'PCIe 4.0 x8', 200);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 4070', 599.00, 1, '12GB VRAM', '1440p/4K mid-high');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 200, 'PCIe 4.0 x16', 240);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 4080', 1199.00, 1, '16GB VRAM', 'High-end 4K');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6X', 320, 'PCIe 4.0 x16', 300);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 3, 'NVIDIA RTX 4090', 1599.00, 1, '24GB VRAM', 'Flagship GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 450, 'PCIe 4.0 x16', 336);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'AMD RX 6700 XT', 400.00, 1, '12GB VRAM', 'Strong 1440p gaming');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 230, 'PCIe 4.0 x16', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 4, 'AMD RX 6800 XT', 650.00, 1, '16GB VRAM', '4K capable GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 300, 'PCIe 4.0 x16', 267);


-- ======================
-- Mainboard (10 sản phẩm)
-- ======================
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASUS PRIME B550M-A', 150.00, 1, 'Micro ATX AM4 board', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'Micro-ATX', 'DDR4', 4, 128, 'PCIe 4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASUS TUF Gaming X570-Plus', 220.00, 1, 'ATX AM4 board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 128, 'PCIe 4.0', 2, 8);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'Gigabyte B450M DS3H', 90.00, 1, 'Budget AM4 board', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 64, 'PCIe 3.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'MSI B650 Tomahawk', 220.00, 1, 'ATX AM5 board', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'ATX', 'DDR5', 4, 128, 'PCIe 5.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASUS ROG Crosshair X670E Hero', 500.00, 1, 'High-end AM5 board', 'Chipset X670E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'X670E', 'ATX', 'DDR5', 4, 128, 'PCIe 5.0', 3, 8);
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASUS ROG Strix Z690-F', 350.00, 1, 'ATX Intel 12th gen', 'Chipset Z690');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z690', 'ATX', 'DDR5', 4, 128, 'PCIe 5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'MSI PRO Z790-A', 270.00, 1, 'ATX Intel 13th gen', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 128, 'PCIe 5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASRock H610M-HDV', 100.00, 1, 'Entry Intel board', 'Chipset H610');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 64, 'PCIe 4.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'Gigabyte B660M DS3H', 130.00, 1, 'Mid Intel board', 'Chipset B660');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'B660', 'Micro-ATX', 'DDR4', 2, 128, 'PCIe 4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'ASUS PRIME B760M-A', 160.00, 1, 'Intel 13th gen support', 'Chipset B760');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, ram_type, ram_slots, max_ram_size, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'B760', 'Micro-ATX', 'DDR5', 4, 128, 'PCIe 4.0', 2, 6);
