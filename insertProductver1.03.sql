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
('Western Digital', 'Western Digital - HDD/SSD nổi tiếng', 'https://www.westerndigital.com'),
('NZXT', 'Hãng sản xuất case, tản nhiệt và mainboard với thiết kế tối giản.', 'https://nzxt.com'),
('Lian Li', 'Thương hiệu case và tản nhiệt cao cấp từ nhôm.', 'https://lian-li.com'),
('Fractal Design', 'Hãng case và tản nhiệt từ Thụy Điển, nổi tiếng với thiết kế tinh tế và hiệu quả.', 'https://www.fractal-design.com'),
('Phanteks', 'Chuyên về case, tản nhiệt và quạt hiệu năng cao.', 'https://www.phanteks.com'),
('Thermaltake', 'Thương hiệu lớn về case, nguồn, tản nhiệt và gaming gear.', 'https://www.thermaltake.com'),
('Noctua', 'Hãng tản nhiệt khí và quạt hàng đầu thế giới từ Áo, nổi tiếng về sự yên tĩnh và hiệu năng.', 'https://noctua.at'),
('Deepcool', 'Hãng sản xuất tản nhiệt, case, nguồn với P/P tốt.', 'https://www.deepcool.com'),
('Thermalright', 'Thương hiệu tản nhiệt khí hiệu năng cao, giá cả cạnh tranh.', 'https://www.thermalright.com'),
('be quiet!', 'Hãng sản xuất linh kiện từ Đức, tập trung vào sự yên tĩnh.', 'https://www.bequiet.com'),
('Arctic', 'Hãng tản nhiệt nổi tiếng với dòng AIO Liquid Freezer II.', 'https://www.arctic.de'),
('Crucial', 'Thương hiệu con của Micron, chuyên về RAM và SSD.', 'https://www.crucial.com'),
('Teamgroup', 'Hãng sản xuất RAM và SSD, đặc biệt là dòng T-Force gaming.', 'https://www.teamgroupinc.com'),
('Samsung', 'Tập đoàn điện tử hàng đầu, sản xuất SSD tiêu dùng tốt nhất thị trường.', 'https://www.samsung.com'),
('SK Hynix', 'Nhà sản xuất chip nhớ lớn, cũng sản xuất SSD hiệu năng cao.', 'https://ssd.skhynix.com'),
('Sabrent', 'Thương hiệu nổi tiếng với các SSD NVMe tốc độ cao.', 'https://sabrent.com'),
('Adata', 'Hãng sản xuất RAM và SSD, gaming gear dưới thương hiệu XPG.', 'https://www.adata.com'),
('SanDisk', 'Thương hiệu con của Western Digital, chuyên về bộ nhớ flash và SSD.', 'https://www.sandisk.com'),
('Toshiba', 'Tập đoàn Nhật Bản, sản xuất ổ cứng HDD và chip nhớ.', 'https://www.toshiba-storage.com'),
('Seasonic', 'Thương hiệu nguồn máy tính (PSU) cao cấp và đáng tin cậy nhất.', 'https://seasonic.com'),
('EVGA', 'Hãng sản xuất card đồ họa, mainboard và nguồn nổi tiếng.', 'https://www.evga.com'),
('FSP', 'Nhà sản xuất nguồn máy tính (PSU) lớn và uy tín.', 'https://www.fsplifestyle.com'),
('Antec', 'Thương hiệu lâu đời về case, nguồn và quạt.', 'https://www.antec.com'),
('Hyte', 'Thương hiệu con của NZXT, chuyên về các vỏ case độc đáo như Y60.', 'https://hyte.com');

-- ==============================================
--  PRODUCT + MAINBOARD SEED DATA (30 bản ghi)
-- ==============================================

-- 1
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS PRIME B550M-A', 150.00, 1, 'Micro ATX AM4 mainboard', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'Micro-ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

-- 2
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI MPG X670E CARBON', 390.00, 1, 'High-end AM5 mainboard', 'Chipset X670E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 3, 6);

-- 3
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE B760M DS3H DDR4', 140.00, 1, 'Intel LGA1700 mid-range board', 'Chipset B760');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'B760', 'Micro-ATX', 'DDR4', 4, 5333, '5.0', 2, 4);

-- 4
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock A620M-HDV/M.2+', 100.00, 1, 'Budget AM5 mainboard', 'Chipset A620');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'A620', 'Micro-ATX', 'DDR5', 2, 6400, '4.0', 1, 4);

-- 5
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS ROG STRIX Z790-E GAMING', 420.00, 1, 'Top-tier Intel board', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

-- 6
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI PRO H610M-E DDR4', 110.00, 1, 'Entry-level LGA1700', 'Chipset H610');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

-- 7
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE X570 AORUS ELITE', 200.00, 1, 'Mid-range AM4 board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 8
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS TUF GAMING B550-PLUS', 170.00, 1, 'Durable AM4 board', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'ATX', 'DDR4', 4, 4800, '4.0', 2, 6);

-- 9
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock H510M-HDV', 90.00, 1, 'Intel entry board', 'Chipset H510');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1200', 'H510', 'Micro-ATX', 'DDR4', 2, 3200, '3.0', 1, 4);

-- 10
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI B450 TOMAHAWK MAX II', 120.00, 1, 'Popular AM4 board', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'ATX', 'DDR4', 4, 4133, '3.0', 1, 6);

-- 11
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE B650 AORUS ELITE', 260.00, 1, 'AM5 PCIe5 DDR5', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

-- 12
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS PRIME H610M-K D4', 110.00, 1, 'Intel H610 DDR4 board', 'Chipset H610');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

-- 13
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock Z690 PG RIPTIDE', 250.00, 1, 'Z690 chipset DDR5 board', 'Chipset Z690');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z690', 'ATX', 'DDR5', 4, 6400, '5.0', 3, 6);

-- 14
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI MAG B550 TOMAHAWK', 180.00, 1, 'AM4 gaming board', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

-- 15
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE Z790 AORUS MASTER', 480.00, 1, 'Z790 flagship', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

-- 16
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS ROG CROSSHAIR X670E HERO', 490.00, 1, 'AM5 Enthusiast board', 'Chipset X670E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 4, 6);

-- 17
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI MPG B650M EDGE WIFI', 240.00, 1, 'AM5 DDR5 compact', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'Micro-ATX', 'DDR5', 4, 7000, '5.0', 2, 6);

-- 18
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock X570 PHANTOM GAMING 4', 180.00, 1, 'AM4 solid board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 19
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE A520M S2H', 90.00, 1, 'Entry AM4 mainboard', 'Chipset A520');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'A520', 'Micro-ATX', 'DDR4', 2, 4400, '3.0', 1, 4);

-- 20
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS PRIME B450M-K II', 100.00, 1, 'Budget AM4', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

-- 21
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI Z590 PRO WIFI', 260.00, 1, 'Intel 11th Gen Z590', 'Chipset Z590');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1200', 'Z590', 'ATX', 'DDR4', 4, 5333, '4.0', 2, 6);

-- 22
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE B365M DS3H', 110.00, 1, 'Older Intel 9th Gen', 'Chipset B365');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1151', 'B365', 'Micro-ATX', 'DDR4', 4, 2666, '3.0', 1, 4);

-- 23
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock B450M PRO4', 110.00, 1, 'AM4 classic', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

-- 24
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS PRIME X570-P', 190.00, 1, 'AM4 PCIe 4.0 board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 25
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI B650 TOMAHAWK WIFI', 250.00, 1, 'AM5 DDR5 mid-range', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

-- 26
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE X399 AORUS XTREME', 450.00, 1, 'TR4 workstation board', 'Chipset X399');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'TR4', 'X399', 'E-ATX', 'DDR4', 8, 3600, '3.0', 3, 8);

-- 27
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 4, 'ASRock Z790M-ITX WIFI', 280.00, 1, 'Compact Intel Z790', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'Mini-ITX', 'DDR5', 2, 7800, '5.0', 2, 4);

-- 28
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 1, 'ASUS ROG STRIX B650E-F GAMING WIFI', 300.00, 1, 'AM5 DDR5 gaming', 'Chipset B650E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'B650E', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

-- 29
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 2, 'MSI PRO X670-P WIFI', 270.00, 1, 'AM5 PCIe5 DDR5', 'Chipset X670');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM5', 'X670', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

-- 30
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (1, 3, 'GIGABYTE B550I AORUS PRO AX', 220.00, 1, 'Mini ITX AM4 board with Wi-Fi', 'Chipset B550I');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (LAST_INSERT_ID(), 'AM4', 'B550I', 'Mini-ITX', 'DDR4', 2, 4733, '4.0', 2, 4);

-- ==============================================
--  PRODUCT + CPU SEED DATA (30 bản ghi)
-- ==============================================

-- 1
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 5 5600X', 220.00, 1, '6 cores 12 threads', 'Base 3.7GHz Boost 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '4.0', 0);

-- 2
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i5-12400F', 180.00, 1, '6 Performance cores', 'Base 2.5GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 0);

-- 3
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i7-13700K', 420.00, 1, '16 cores hybrid', 'Base 3.4GHz Boost 5.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 4
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 9 7950X', 590.00, 1, '16 cores 32 threads', 'Base 4.5GHz Boost 5.7GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 170, 5200, 2, '5.0', 1);

-- 5
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 7 5800X3D', 370.00, 1, '8 cores 3D cache', 'Base 3.4GHz Boost 4.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

-- 6
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i9-13900K', 600.00, 1, '24 cores hybrid', 'Base 3.0GHz Boost 5.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 7
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 5 7600', 240.00, 1, '6 cores AM5', 'Base 3.8GHz Boost 5.1GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 1);

-- 8
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i3-12100F', 120.00, 1, '4 cores 8 threads', 'Base 3.3GHz Boost 4.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 60, 4800, 2, '5.0', 0);

-- 9
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 7 7700', 340.00, 1, '8 cores AM5', 'Base 3.8GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 1);

-- 10
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i5-13600K', 320.00, 1, '14 cores hybrid', 'Base 3.5GHz Boost 5.1GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 11
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 9 5950X', 550.00, 1, '16 cores 32 threads', 'Base 3.4GHz Boost 4.9GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

-- 12
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i9-12900KS', 580.00, 1, '16 cores 24 threads', 'Base 3.4GHz Boost 5.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 13
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 3 4100', 90.00, 1, '4 cores entry level', 'Base 3.8GHz Boost 4.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 0);

-- 14
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i5-14600K', 360.00, 1, '14 cores Raptor Lake Refresh', 'Base 3.5GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 15
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 5 5500', 130.00, 1, '6 cores AM4', 'Base 3.6GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 0);

-- 16
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 9 7900X', 470.00, 1, '12 cores 24 threads AM5', 'Base 4.7GHz Boost 5.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 170, 5200, 2, '5.0', 1);

-- 17
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 7 7800X3D', 450.00, 1, '8 cores 3D V-Cache AM5', 'Base 4.2GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 120, 5200, 2, '5.0', 1);

-- 18
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i9-14900K', 620.00, 1, '24 cores Raptor Lake Refresh', 'Base 3.2GHz Boost 6.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 19
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i7-12700F', 310.00, 1, '12 cores hybrid no iGPU', 'Base 2.1GHz Boost 4.9GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 0);

-- 20
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i5-12400', 190.00, 1, '6 cores 12 threads', 'Base 2.5GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 1);

-- 21
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i3-13100', 140.00, 1, '4 cores 8 threads 13th Gen', 'Base 3.4GHz Boost 4.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1700', 60, 4800, 2, '5.0', 1);

-- 22
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 5 4600G', 150.00, 1, '6 cores APU with Vega GPU', 'Base 3.7GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

-- 23
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 7 5700G', 240.00, 1, '8 cores APU', 'Base 3.8GHz Boost 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

-- 24
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 3 5300G', 120.00, 1, '4 cores APU entry', 'Base 4.0GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

-- 25
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i5-11400F', 160.00, 1, '6 cores 11th Gen', 'Base 2.6GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1200', 65, 3200, 2, '4.0', 0);

-- 26
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i7-11700K', 330.00, 1, '8 cores 16 threads', 'Base 3.6GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1200', 125, 3200, 2, '4.0', 1);

-- 27
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 9 5900X', 420.00, 1, '12 cores AM4', 'Base 3.7GHz Boost 4.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

-- 28
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 5 7500F', 210.00, 1, '6 cores AM5 no iGPU', 'Base 3.7GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 0);

-- 29
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 2, 'Intel Core i9-11900K', 400.00, 1, '8 cores high-end', 'Base 3.5GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'LGA1200', 125, 3200, 2, '4.0', 1);

-- 30
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (2, 1, 'AMD Ryzen 3 3200G', 90.00, 1, '4 cores Vega 8 APU', 'Base 3.6GHz Boost 4.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (LAST_INSERT_ID(), 'AM4', 65, 2933, 2, '3.0', 1);



-- ==============================================
--  PRODUCT + GPU SEED DATA (30 bản ghi)
-- ==============================================

-- 1
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 3060', 330.00, 1, '12GB GDDR6 mid-range GPU', 'Ampere architecture');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 170, 'PCIe x16', '4.0', 242);

-- 2
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6600 XT', 320.00, 1, '8GB GDDR6', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 230);

-- 3
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4070', 550.00, 1, '12GB GDDR6X', 'Ada Lovelace GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 200, 'PCIe x16', '4.0', 244);

-- 4
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 7900 XT', 880.00, 1, '20GB GDDR6', 'RDNA 3 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 20, 'GDDR6', 300, 'PCIe x16', '4.0', 276);

-- 5
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4080', 1200.00, 1, '16GB GDDR6X high-end GPU', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6X', 320, 'PCIe x16', '4.0', 304);

-- 6
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6800', 580.00, 1, '16GB GDDR6', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 250, 'PCIe x16', '4.0', 267);

-- 7
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 3050', 250.00, 1, '8GB GDDR6 entry GPU', 'Ampere');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 130, 'PCIe x8', '4.0', 242);

-- 8
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6500 XT', 180.00, 1, '4GB GDDR6', 'Entry RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 107, 'PCIe x4', '4.0', 190);

-- 9
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4070 Ti', 790.00, 1, '12GB GDDR6X high-end', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 285, 'PCIe x16', '4.0', 285);

-- 10
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 7800 XT', 500.00, 1, '16GB GDDR6', 'RDNA 3 mid-high');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 263, 'PCIe x16', '4.0', 267);

-- 11
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4060', 310.00, 1, '8GB GDDR6', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 115, 'PCIe x8', '4.0', 242);

-- 12
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6700 XT', 370.00, 1, '12GB GDDR6 mid-range', 'RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 230, 'PCIe x16', '4.0', 267);

-- 13
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce GTX 1660 SUPER', 220.00, 1, '6GB GDDR6', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 125, 'PCIe x16', '3.0', 229);

-- 14
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 5600 XT', 280.00, 1, '6GB GDDR6', 'RDNA 1 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 150, 'PCIe x16', '4.0', 242);

-- 15
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 2080 SUPER', 600.00, 1, '8GB GDDR6 high-end', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 250, 'PCIe x16', '3.0', 285);

-- 16
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4090', 1600.00, 1, '24GB GDDR6X flagship', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 450, 'PCIe x16', '4.0', 336);

-- 17
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 7600', 270.00, 1, '8GB GDDR6', 'RDNA 3 entry');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 165, 'PCIe x8', '4.0', 240);

-- 18
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4060 Ti', 420.00, 1, '8GB GDDR6 mid-high', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

-- 19
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6950 XT', 700.00, 1, '16GB GDDR6 high-end', 'RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 335, 'PCIe x16', '4.0', 267);

-- 20
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 3070', 500.00, 1, '8GB GDDR6', 'Ampere');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 220, 'PCIe x16', '4.0', 242);

-- 21
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 3090', 1400.00, 1, '24GB GDDR6X high-end GPU', 'Ampere architecture');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 350, 'PCIe x16', '4.0', 336);

-- 22
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4070 SUPER', 620.00, 1, '12GB GDDR6X refreshed Ada GPU', 'High performance efficiency');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 220, 'PCIe x16', '4.0', 244);

-- 23
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 7700 XT', 470.00, 1, '12GB GDDR6 mid-high range', 'RDNA 3 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 245, 'PCIe x16', '4.0', 267);

-- 24
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6800 XT', 650.00, 1, '16GB GDDR6 enthusiast', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 300, 'PCIe x16', '4.0', 267);

-- 25
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 2060', 260.00, 1, '6GB GDDR6', 'Turing mid-range GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 160, 'PCIe x16', '3.0', 229);

-- 26
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce GTX 1650 SUPER', 180.00, 1, '4GB GDDR6', 'Turing budget GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 100, 'PCIe x16', '3.0', 229);

-- 27
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 5500 XT', 160.00, 1, '8GB GDDR6', 'RDNA entry GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 130, 'PCIe x8', '4.0', 230);

-- 28
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 2080 Ti', 1000.00, 1, '11GB GDDR6 flagship', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 11, 'GDDR6', 260, 'PCIe x16', '3.0', 285);

-- 29
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 1, 'NVIDIA GeForce RTX 4060 SUPER', 400.00, 1, '8GB GDDR6 Ada mid-range', 'Ada Lovelace GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

-- 30
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification)
VALUES (3, 2, 'AMD Radeon RX 6400', 140.00, 1, '4GB GDDR6 compact GPU', 'Low power RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 53, 'PCIe x4', '4.0', 170);

-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM BỘ NHỚ RAM (MEMORY)
-- =================================================================

-- -----------------------------------------------------------------
-- RAM DDR4
-- -----------------------------------------------------------------

-- 1. Corsair Vengeance LPX 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance LPX 16GB (2x8GB) DDR4 3200MHz C16', 1290000, 'Kit RAM DDR4 phổ biến, hiệu năng ổn định, tản nhiệt thấp.', 'Kit 16GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3200);

-- 2. G.Skill Ripjaws V 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Ripjaws V 16GB (2x8GB) DDR4 3600MHz C18', 1450000, 'Hiệu năng cao cho các hệ thống AMD Ryzen.', 'Kit 16GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3600);

-- 3. Kingston Fury Beast 8GB DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Beast 8GB DDR4 3200MHz', 690000, 'Thanh RAM lẻ 8GB cho các cấu hình cơ bản.', '8GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 8, 3200);

-- 4. Crucial Ballistix 32GB (2x16GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 16, 'Crucial Ballistix 32GB (2x16GB) DDR4 3200MHz C16', 2500000, 'Kit 32GB hiệu năng tốt cho gaming và làm việc đa nhiệm.', 'Kit 32GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3200);

-- 5. Teamgroup T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 17, 'Teamgroup T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz', 1150000, 'RAM giá rẻ với tản nhiệt cách điệu.', 'Kit 16GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3200);

-- 6. G.Skill Trident Z RGB 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Trident Z RGB 16GB (2x8GB) DDR4 3600MHz C18', 1990000, 'Thiết kế RGB đẹp mắt, hiệu năng cao.', 'Kit 16GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3600);

-- 7. Corsair Dominator Platinum RGB 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Dominator Platinum RGB 32GB (2x16GB) DDR4 3600MHz', 4200000, 'Dòng RAM cao cấp nhất của Corsair, LED Capellix.', 'Kit 32GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3600);

-- 8. Kingston Fury Beast 32GB (2x16GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Beast 32GB (2x16GB) DDR4 3200MHz', 2400000, 'Kit 32GB cho người dùng cần nhiều dung lượng.', 'Kit 32GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3200);

-- 9. Crucial Pro 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 16, 'Crucial Pro 16GB (2x8GB) DDR4 3200MHz', 1250000, 'RAM ổn định, không RGB, tập trung vào hiệu năng.', 'Kit 16GB, DDR4, 3200MHz, CL22');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3200);

-- 10. G.Skill Ripjaws V 32GB (2x16GB) DDR4 4000MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Ripjaws V 32GB (2x16GB) DDR4 4000MHz C18', 3500000, 'Tốc độ bus cực cao cho các hệ thống Intel cao cấp.', 'Kit 32GB, DDR4, 4000MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 4000);

-- 11. Corsair Vengeance RGB Pro 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance RGB Pro 16GB (2x8GB) DDR4 3200MHz', 1650000, 'Dòng RAM RGB tầm trung phổ biến.', 'Kit 16GB, DDR4, 3200MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3200);

-- 12. Teamgroup T-Create Expert 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 17, 'Teamgroup T-Create Expert 32GB (2x16GB) DDR4 3600MHz', 2300000, 'Dòng RAM chuyên cho người làm sáng tạo nội dung.', 'Kit 32GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3600);

-- 13. Kingston Fury Renegade 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Renegade 16GB (2x8GB) DDR4 3600MHz', 1800000, 'Dòng RAM hiệu năng cao của Kingston.', 'Kit 16GB, DDR4, 3600MHz, CL16');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 16, 3600);

-- 14. G.Skill Trident Z Royal Silver 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Trident Z Royal Silver 32GB (2x16GB) DDR4 3600MHz', 4500000, 'Thiết kế kim cương sang trọng, hiệu năng đỉnh cao.', 'Kit 32GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3600);

-- 15. Corsair Vengeance LPX 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance LPX 32GB (2x16GB) DDR4 3600MHz C18', 2600000, 'Kit 32GB tốc độ cao, tản nhiệt thấp không cấn tản khí.', 'Kit 32GB, DDR4, 3600MHz, CL18');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR4', 32, 3600);

-- -----------------------------------------------------------------
-- RAM DDR5
-- -----------------------------------------------------------------

-- 16. Corsair Vengeance 32GB (2x16GB) DDR5 5600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance 32GB (2x16GB) DDR5 5600MHz C36', 3200000, 'Kit RAM DDR5 tiêu chuẩn cho các hệ thống mới.', 'Kit 32GB, DDR5, 5600MHz, CL36');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 5600);

-- 17. G.Skill Trident Z5 RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Trident Z5 RGB 32GB (2x16GB) DDR5 6000MHz C36', 3800000, 'Tốc độ tối ưu cho AMD EXPO, thiết kế đẹp.', 'Kit 32GB, DDR5, 6000MHz, CL36');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6000);

-- 18. Kingston Fury Beast 16GB (2x8GB) DDR5 5200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Beast 16GB (2x8GB) DDR5 5200MHz', 2200000, 'Kit 16GB DDR5 cho các cấu hình tầm trung.', 'Kit 16GB, DDR5, 5200MHz, CL40');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 16, 5200);

-- 19. Crucial Pro 32GB (2x16GB) DDR5 5600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 16, 'Crucial Pro 32GB (2x16GB) DDR5 5600MHz', 3100000, 'RAM DDR5 ổn định, hỗ trợ cả Intel XMP và AMD EXPO.', 'Kit 32GB, DDR5, 5600MHz, CL46');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 5600);

-- 20. Teamgroup T-Force Delta RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 17, 'Teamgroup T-Force Delta RGB 32GB (2x16GB) DDR5 6000MHz', 3500000, 'Thiết kế RGB góc cạnh, hiệu năng tốt.', 'Kit 32GB, DDR5, 6000MHz, CL38');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6000);

-- 21. Corsair Dominator Platinum RGB 32GB (2x16GB) DDR5 6200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Dominator Platinum RGB 32GB (2x16GB) DDR5 6200MHz', 5500000, 'Dòng RAM DDR5 cao cấp nhất của Corsair.', 'Kit 32GB, DDR5, 6200MHz, CL36');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6200);

-- 22. G.Skill Ripjaws S5 32GB (2x16GB) DDR5 5200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Ripjaws S5 32GB (2x16GB) DDR5 5200MHz Black', 3000000, 'Thiết kế tản nhiệt thấp, phù hợp nhiều loại tản khí.', 'Kit 32GB, DDR5, 5200MHz, CL40');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 5200);

-- 23. Kingston Fury Renegade 32GB (2x16GB) DDR5 6400MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Renegade 32GB (2x16GB) DDR5 6400MHz', 4200000, 'Hiệu năng cao, tản nhiệt bạc-đen.', 'Kit 32GB, DDR5, 6400MHz, CL32');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6400);

-- 24. G.Skill Trident Z5 RGB 64GB (2x32GB) DDR5 6400MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Trident Z5 RGB 64GB (2x32GB) DDR5 6400MHz', 7500000, 'Dung lượng cực lớn cho workstation và các tác vụ nặng.', 'Kit 64GB, DDR5, 6400MHz, CL32');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 64, 6400);

-- 25. Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz C36', 3900000, 'Dòng Vengeance với dải LED RGB toàn cảnh.', 'Kit 32GB, DDR5, 6000MHz, CL36');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6000);

-- 26. Crucial 16GB (1x16GB) DDR5 4800MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 16, 'Crucial 16GB DDR5 4800MHz', 1400000, 'Thanh RAM lẻ DDR5 tiêu chuẩn JEDEC, không tản nhiệt.', '16GB, DDR5, 4800MHz, CL40');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 16, 4800);

-- 27. Teamgroup T-Force Xtreem 32GB (2x16GB) DDR5 7600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 17, 'Teamgroup T-Force Xtreem 32GB (2x16GB) DDR5 7600MHz', 5800000, 'Tốc độ bus cực cao cho những người đam mê ép xung.', 'Kit 32GB, DDR5, 7600MHz, CL36');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 7600);

-- 28. Kingston Fury Beast 64GB (2x32GB) DDR5 5600MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 15, 'Kingston Fury Beast 64GB (2x32GB) DDR5 5600MHz', 6500000, 'Kit 64GB dung lượng lớn, ổn định.', 'Kit 64GB, DDR5, 5600MHz, CL40');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 64, 5600);

-- 29. G.Skill Flare X5 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 14, 'G.Skill Flare X5 32GB (2x16GB) DDR5 6000MHz CL30', 4000000, 'Tối ưu cho AMD EXPO với độ trễ C30 cực thấp.', 'Kit 32GB, DDR5, 6000MHz, CL30');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 32, 6000);

-- 30. Corsair Vengeance 64GB (2x32GB) DDR5 5200MHz
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (4, 2, 'Corsair Vengeance 64GB (2x32GB) DDR5 5200MHz', 6300000, 'Kit 64GB không RGB, tập trung vào dung lượng và sự ổn định.', 'Kit 64GB, DDR5, 5200MHz, CL40');
INSERT INTO memory (product_id, type, capacity, speed) VALUES (LAST_INSERT_ID(), 'DDR5', 64, 5200);

-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM LƯU TRỮ (STORAGE)
-- =================================================================

-- -----------------------------------------------------------------
-- SSD NVMe M.2 (Tốc độ cao)
-- -----------------------------------------------------------------

-- 1. Samsung 980 Pro 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 18, 'Samsung 980 Pro 1TB PCIe 4.0 NVMe', 2890000, 'SSD NVMe Gen4 hàng đầu, tốc độ cực nhanh cho gaming và công việc.', '1TB, NVMe, Read 7000MB/s, Write 5000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7000, 5000);

-- 2. Western Digital Black SN850X 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Black SN850X 1TB PCIe 4.0 NVMe', 2750000, 'Đối thủ cạnh tranh của 980 Pro, hiệu năng đỉnh cao.', '1TB, NVMe, Read 7300MB/s, Write 6300MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7300, 6300);

-- 3. Crucial P5 Plus 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 16, 'Crucial P5 Plus 1TB PCIe 4.0 NVMe', 2400000, 'SSD Gen4 hiệu năng/giá thành tốt.', '1TB, NVMe, Read 6600MB/s, Write 5000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 6600, 5000);

-- 4. Kingston KC3000 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 15, 'Kingston KC3000 1TB PCIe 4.0 NVMe', 2800000, 'Tốc độ đọc ghi rất cao, ổn định.', '1TB, NVMe, Read 7000MB/s, Write 6000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7000, 6000);

-- 5. Samsung 970 Evo Plus 500GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 18, 'Samsung 970 Evo Plus 500GB PCIe 3.0 NVMe', 1500000, 'SSD NVMe Gen3 huyền thoại, vẫn rất mạnh.', '500GB, NVMe, Read 3500MB/s, Write 3200MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 500, 'NVMe', 3500, 3200);

-- 6. Western Digital Blue SN570 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Blue SN570 1TB PCIe 3.0 NVMe', 1890000, 'Lựa chọn NVMe Gen3 giá tốt cho người dùng phổ thông.', '1TB, NVMe, Read 3500MB/s, Write 3000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 3500, 3000);

-- 7. SK Hynix Platinum P41 2TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 20, 'SK Hynix Platinum P41 2TB PCIe 4.0 NVMe', 5500000, 'Một trong những SSD Gen4 nhanh nhất thị trường.', '2TB, NVMe, Read 7000MB/s, Write 6500MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 2000, 'NVMe', 7000, 6500);

-- 8. Crucial P3 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 16, 'Crucial P3 1TB PCIe 3.0 NVMe', 1700000, 'SSD QLC NVMe giá rẻ, dung lượng lớn.', '1TB, NVMe, Read 3500MB/s, Write 3000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 3500, 3000);

-- 9. Samsung 990 Pro 2TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 18, 'Samsung 990 Pro 2TB PCIe 4.0 NVMe', 5900000, 'Flagship mới nhất của Samsung, hiệu năng đỉnh cao.', '2TB, NVMe, Read 7450MB/s, Write 6900MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 2000, 'NVMe', 7450, 6900);

-- 10. Kingston NV2 500GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 15, 'Kingston NV2 500GB PCIe 4.0 NVMe', 1100000, 'SSD NVMe Gen4 giá rẻ nhất thị trường.', '500GB, NVMe, Read 3500MB/s, Write 2100MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 500, 'NVMe', 3500, 2100);

-- 11. Western Digital Black SN770 500GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Black SN770 500GB PCIe 4.0 NVMe', 1600000, 'SSD Gen4 DRAM-less hiệu năng cao.', '500GB, NVMe, Read 5000MB/s, Write 4000MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 500, 'NVMe', 5000, 4000);

-- 12. Sabrent Rocket 4 Plus 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 21, 'Sabrent Rocket 4 Plus 1TB PCIe 4.0 NVMe', 3200000, 'Thương hiệu nổi tiếng với các SSD hiệu năng cao.', '1TB, NVMe, Read 7100MB/s, Write 6600MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7100, 6600);

-- 13. Gigabyte AORUS Gen4 7000s 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 3, 'Gigabyte AORUS Gen4 7000s 1TB', 3100000, 'SSD Gen4 đi kèm tản nhiệt hầm hố.', '1TB, NVMe, Read 7000MB/s, Write 5500MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7000, 5500);

-- 14. Corsair MP600 PRO LPX 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 2, 'Corsair MP600 PRO LPX 1TB', 2900000, 'Tương thích với PS5, tản nhiệt thấp.', '1TB, NVMe, Read 7100MB/s, Write 6800MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7100, 6800);

-- 15. Adata XPG Gammix S70 Blade 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 22, 'Adata XPG Gammix S70 Blade 1TB', 2600000, 'Tốc độ cao với tản nhiệt mỏng đi kèm.', '1TB, NVMe, Read 7400MB/s, Write 6400MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'NVMe', 7400, 6400);

-- -----------------------------------------------------------------
-- SSD SATA 2.5 inch
-- -----------------------------------------------------------------

-- 16. Samsung 870 Evo 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 18, 'Samsung 870 Evo 1TB 2.5 inch SATA III', 2100000, 'SSD SATA 2.5" tốt nhất thị trường, độ bền cao.', '1TB, SATA, Read 560MB/s, Write 530MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'SATA', 560, 530);

-- 17. Crucial MX500 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 16, 'Crucial MX500 1TB 2.5 inch SATA III', 1850000, 'Đối thủ của 870 Evo, hiệu năng ổn định và bền bỉ.', '1TB, SATA, Read 560MB/s, Write 510MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'SATA', 560, 510);

-- 18. Western Digital Blue 500GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Blue 500GB 2.5 inch SATA III', 1100000, 'SSD SATA phổ thông, lựa chọn tốt để nâng cấp từ HDD.', '500GB, SATA, Read 560MB/s, Write 530MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 500, 'SATA', 560, 530);

-- 19. Kingston A400 480GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 15, 'Kingston A400 480GB 2.5 inch SATA III', 850000, 'SSD giá rẻ cho các máy tính văn phòng.', '480GB, SATA, Read 500MB/s, Write 450MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 480, 'SATA', 500, 450);

-- 20. Samsung 870 QVO 2TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 18, 'Samsung 870 QVO 2TB 2.5 inch SATA III', 3500000, 'SSD QLC dung lượng lớn để lưu game và dữ liệu.', '2TB, SATA, Read 560MB/s, Write 530MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 2000, 'SATA', 560, 530);

-- 21. SanDisk Ultra 3D 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 23, 'SanDisk Ultra 3D 1TB 2.5 inch SATA III', 1900000, 'SSD 3D NAND đáng tin cậy.', '1TB, SATA, Read 560MB/s, Write 530MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 1000, 'SATA', 560, 530);

-- 22. Crucial BX500 240GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 16, 'Crucial BX500 240GB 2.5 inch SATA III', 650000, 'Dung lượng nhỏ để cài hệ điều hành.', '240GB, SATA, Read 540MB/s, Write 500MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 240, 'SATA', 540, 500);

-- 23. Gigabyte SSD 120GB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 3, 'Gigabyte SSD 120GB 2.5 inch SATA III', 490000, 'SSD giá cực rẻ cho các máy tính cũ.', '120GB, SATA, Read 500MB/s, Write 380MB/s');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'SSD', 120, 'SATA', 500, 380);

-- -----------------------------------------------------------------
-- HDD 3.5 inch (Ổ cứng cơ)
-- -----------------------------------------------------------------

-- 24. Seagate Barracuda 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 24, 'Seagate Barracuda 1TB 3.5 inch 7200RPM', 950000, 'Ổ cứng HDD phổ thông nhất để lưu trữ dữ liệu.', '1TB, HDD, 7200RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 1000, 'SATA', 220, 220);

-- 25. Western Digital Blue 2TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Blue 2TB 3.5 inch 7200RPM', 1350000, 'Lựa chọn 2TB tin cậy từ Western Digital.', '2TB, HDD, 7200RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 2000, 'SATA', 215, 215);

-- 26. Seagate IronWolf 4TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 24, 'Seagate IronWolf 4TB NAS HDD', 2800000, 'Ổ cứng chuyên dụng cho hệ thống lưu trữ mạng (NAS).', '4TB, HDD, 5900RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 4000, 'SATA', 180, 180);

-- 27. Western Digital Black 4TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Black 4TB 3.5 inch 7200RPM', 3500000, 'Dòng HDD hiệu năng cao dành cho game thủ và người dùng chuyên nghiệp.', '4TB, HDD, 7200RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 4000, 'SATA', 227, 227);

-- 28. Toshiba P300 1TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 25, 'Toshiba P300 1TB 3.5 inch 7200RPM', 920000, 'Ổ cứng HDD 1TB từ Toshiba, bền bỉ.', '1TB, HDD, 7200RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 1000, 'SATA', 210, 210);

-- 29. Seagate SkyHawk 2TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 24, 'Seagate SkyHawk 2TB Surveillance HDD', 1500000, 'Ổ cứng chuyên dụng cho ghi hình camera giám sát.', '2TB, HDD, 5900RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 2000, 'SATA', 180, 180);

-- 30. Western Digital Purple 4TB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (5, 19, 'WD Purple 4TB Surveillance HDD', 2600000, 'Đối thủ của SkyHawk, chuyên cho camera.', '4TB, HDD, 5400RPM, SATA');
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed) VALUES (LAST_INSERT_ID(), 'HDD', 4000, 'SATA', 150, 150);





-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM VỎ CASE (PC CASE)
-- =================================================================

-- -----------------------------------------------------------------
-- Phân khúc giá rẻ (Budget Cases)
-- -----------------------------------------------------------------

-- 1. Cooler Master MasterBox Q300L
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 1, 'Cooler Master MasterBox Q300L', 1190000, 'Vỏ case Micro-ATX nhỏ gọn, linh hoạt với tấm lọc bụi từ tính.', 'Micro-ATX, Mini-ITX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Micro-ATX', 360, 159, 160);

-- 2. Corsair Carbide Series 175R RGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 2, 'Corsair Carbide Series 175R RGB', 1290000, 'Thiết kế tối giản với mặt kính cường lực và logo RGB.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 330, 160, 180);

-- 3. Thermaltake Versa H18
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 8, 'Thermaltake Versa H18', 990000, 'Vỏ case Micro-ATX giá rẻ, tối ưu cho luồng khí.', 'Micro-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Micro-ATX', 350, 155, 220);

-- 4. NZXT H510
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 3, 'NZXT H510 Matte White', 1650000, 'Thiết kế biểu tượng của NZXT, hiện đại và sạch sẽ.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 381, 165, 180);

-- 5. Phanteks Eclipse P300A Mesh
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 6, 'Phanteks Eclipse P300A Mesh', 1450000, 'Mặt trước dạng lưới cho hiệu năng tản nhiệt vượt trội.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 355, 165, 200);

-- -----------------------------------------------------------------
-- Phân khúc tầm trung (Mid-range Cases)
-- -----------------------------------------------------------------

-- 6. Fractal Design Meshify C
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 5, 'Fractal Design Meshify C TG', 2290000, 'Thiết kế mặt lưới kim cương tối ưu luồng gió, nội thất thông minh.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 315, 170, 175);

-- 7. Lian Li Lancool 215
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 4, 'Lian Li Lancool 215 Black', 2150000, 'Trang bị sẵn 2 quạt ARGB 200mm phía trước cho luồng gió cực mạnh.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 370, 166, 210);

-- 8. Cooler Master MasterBox TD500 Mesh
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 1, 'Cooler Master MasterBox TD500 Mesh ARGB', 2490000, 'Mặt trước 3D độc đáo, đi kèm 3 quạt ARGB.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 410, 165, 180);

-- 9. Corsair 4000D Airflow
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 2, 'Corsair 4000D Airflow Black', 2350000, 'Thiết kế thông minh, quản lý dây cáp dễ dàng, luồng khí tốt.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 360, 170, 180);

-- 10. NZXT H5 Flow
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 3, 'NZXT H5 Flow Black', 2200000, 'Phiên bản tối ưu luồng gió của H5, có quạt nghiêng cho GPU.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 365, 165, 180);

-- 11. ASUS TUF Gaming GT301
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 9, 'ASUS TUF Gaming GT301', 1990000, 'Thiết kế hầm hố, mặt trước tổ ong, có giá treo tai nghe.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 320, 160, 160);

-- 12. Phanteks Eclipse G360A
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 6, 'Phanteks Eclipse G360A Black', 2500000, 'Hiệu năng tản nhiệt cao với 3 quạt ARGB và mặt lưới siêu mịn.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 400, 162, 220);

-- -----------------------------------------------------------------
-- Phân khúc cao cấp (High-end Cases)
-- -----------------------------------------------------------------

-- 13. Lian Li O11 Dynamic EVO
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 4, 'Lian Li O11 Dynamic EVO Black', 4290000, 'Thiết kế bể cá trứ danh, có thể đảo ngược layout.', 'Mid-Tower, ATX, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 422, 167, 220);

-- 14. Corsair 5000D Airflow
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 2, 'Corsair 5000D Airflow White', 4190000, 'Không gian rộng rãi, hỗ trợ radiator lớn, quản lý dây cáp chuyên nghiệp.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 420, 170, 225);

-- 15. Fractal Design Torrent
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 5, 'Fractal Design Torrent Black TG', 5200000, 'Vua tản nhiệt khí với 2 quạt 180mm phía trước, thiết kế độc đáo.', 'Mid-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 423, 188, 230);

-- 16. NZXT H7 Flow
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 3, 'NZXT H7 Flow White', 3300000, 'Thiết kế tối giản, không gian rộng, luồng gió tốt hơn H7 tiêu chuẩn.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 400, 185, 200);

-- 17. Cooler Master HAF 500
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 1, 'Cooler Master HAF 500 Black', 3150000, 'Huyền thoại tản nhiệt HAF trở lại với 2 quạt 200mm ARGB.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 410, 167, 180);

-- 18. Phanteks Enthoo Evolv X
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 6, 'Phanteks Enthoo Evolv X Glass', 5500000, 'Chất liệu nhôm cao cấp, hỗ trợ lắp 2 hệ thống.', 'Mid-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 435, 190, 200);

-- 19. Lian Li Lancool III
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 4, 'Lian Li Lancool III RGB Black', 3990000, 'Thiết kế module, dễ dàng lắp đặt, đi kèm 4 quạt PWM ARGB.', 'Mid-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 435, 185, 220);

-- -----------------------------------------------------------------
-- Dòng Full Tower và các kích thước đặc biệt
-- -----------------------------------------------------------------

-- 20. Corsair 7000D Airflow
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 2, 'Corsair 7000D Airflow Black', 6990000, 'Vỏ case Full-Tower siêu rộng rãi, hỗ trợ tản nhiệt nước custom đỉnh cao.', 'Full-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 450, 190, 225);

-- 21. Cooler Master Cosmos C700M
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 1, 'Cooler Master Cosmos C700M', 11500000, 'Thiết kế module đỉnh cao, có thể xoay và đảo ngược layout, vật liệu cao cấp.', 'Full-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 490, 198, 300);

-- 22. Thermaltake Tower 900
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 8, 'Thermaltake The Tower 900 Black', 6500000, 'Thiết kế dạng tủ trưng bày độc đáo, tối ưu cho tản nhiệt nước custom.', 'Super-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 400, 260, 220);

-- 23. Fractal Design Define 7 XL
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 5, 'Fractal Design Define 7 XL', 6200000, 'Không gian cực lớn, tối ưu cho sự im lặng và khả năng lưu trữ.', 'Full-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 549, 185, 250);

-- -----------------------------------------------------------------
-- Dòng ITX (nhỏ gọn)
-- -----------------------------------------------------------------

-- 24. Cooler Master MasterBox NR200P
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 1, 'Cooler Master MasterBox NR200P White', 2450000, 'Vỏ case ITX phổ biến nhất, lắp đặt dễ dàng, hỗ trợ cả kính và mặt lưới.', 'Mini-ITX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Mini-ITX', 330, 155, 130);

-- 25. Lian Li A4-H2O
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 4, 'Lian Li A4-H2O Black', 3990000, 'Thiết kế sandwich nhỏ gọn, chất liệu nhôm cao cấp, hỗ trợ AIO 240mm.', 'Mini-ITX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Mini-ITX', 322, 55, 130);

-- 26. NZXT H1 (V2)
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 3, 'NZXT H1 (V2) Matte Black', 4500000, 'Thiết kế dạng tháp, đi kèm sẵn nguồn 750W và tản nhiệt nước AIO.', 'Mini-ITX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Mini-ITX', 324, 140, 130);

-- 27. Fractal Design Terra
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 5, 'Fractal Design Terra Jade', 4800000, 'Thiết kế sang trọng với nhôm và gỗ, có thể điều chỉnh không gian bên trong.', 'Mini-ITX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'Mini-ITX', 322, 77, 130);

-- -----------------------------------------------------------------
-- Một vài lựa chọn đa dạng khác
-- -----------------------------------------------------------------

-- 28. ASUS ROG Strix Helios
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 9, 'ASUS ROG Strix Helios White', 7500000, 'Vỏ case cao cấp cho fan ROG, kính cường lực 3 mặt, có quai xách.', 'Mid-Tower, E-ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'E-ATX', 450, 190, 220);

-- 29. Thermaltake Core P3
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 8, 'Thermaltake Core P3 TG Black', 3600000, 'Thiết kế khung mở (Open-Frame), có thể treo tường.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 450, 180, 200);

-- 30. Hyte Y60
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (6, 3, 'Hyte Y60 Black', 4990000, 'Thiết kế bể cá 3 mặt kính độc đáo, tối ưu cho việc khoe linh kiện.', 'Mid-Tower, ATX');
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_max_length) VALUES (LAST_INSERT_ID(), 'ATX', 375, 160, 235);

-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM NGUỒN MÁY TÍNH (POWER SUPPLY)
-- =================================================================

-- -----------------------------------------------------------------
-- Phân khúc 450W - 650W (Văn phòng, Gaming phổ thông)
-- -----------------------------------------------------------------

-- 1. Corsair CV550
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair CV550 550W 80+ Bronze', 1250000, 'Nguồn 550W chứng nhận 80+ Bronze, ổn định cho các cấu hình phổ thông.', '550W, 80+ Bronze, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 550, '80+ Bronze', 0);

-- 2. Cooler Master MWE Bronze V2 650W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 1, 'Cooler Master MWE Bronze V2 650W', 1490000, 'Nguồn 650W hiệu năng tốt, quạt HDB êm ái.', '650W, 80+ Bronze, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 650, '80+ Bronze', 0);

-- 3. Seasonic S12III 550W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 26, 'Seasonic S12III 550W 80+ Bronze', 1350000, 'Nguồn từ thương hiệu Seasonic danh tiếng, bền bỉ.', '550W, 80+ Bronze, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 550, '80+ Bronze', 0);

-- 4. Thermaltake Smart BX1 650W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 8, 'Thermaltake Smart BX1 650W 80+ Bronze', 1450000, 'Nguồn 650W với quạt 120mm Hydraulic Bearing.', '650W, 80+ Bronze, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 650, '80+ Bronze', 0);

-- 5. EVGA 600 W1
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 27, 'EVGA 600 W1 600W 80+', 1190000, 'Nguồn 600W giá rẻ, chứng nhận 80+ White.', '600W, 80+ White, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 600, '80+ White', 0);

-- 6. be quiet! System Power 9 500W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 12, 'be quiet! System Power 9 500W 80+ Bronze', 1300000, 'Nguồn 500W hoạt động cực kỳ yên tĩnh.', '500W, 80+ Bronze, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 500, '80+ Bronze', 0);

-- 7. Corsair CX650M
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair CX650M 650W 80+ Bronze', 1750000, 'Nguồn semi-modular, giúp đi dây gọn gàng hơn.', '650W, 80+ Bronze, Semi-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 650, '80+ Bronze', 1);

-- -----------------------------------------------------------------
-- Phân khúc 750W - 850W (Gaming cao cấp)
-- -----------------------------------------------------------------

-- 8. Corsair RM750e
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair RM750e 750W 80+ Gold', 2890000, 'Nguồn 750W 80+ Gold, full modular, tụ điện Nhật Bản.', '750W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 750, '80+ Gold', 1);

-- 9. Seasonic Focus Plus Gold 850W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 26, 'Seasonic Focus Plus Gold FX-850', 3200000, 'Nguồn 850W 80+ Gold, full modular, chất lượng hàng đầu.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 10. Cooler Master MWE Gold V2 850W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 1, 'Cooler Master MWE Gold V2 850W', 2950000, 'Nguồn 850W 80+ Gold, full modular, 2 đầu cắm EPS cho CPU.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 11. ASUS ROG Strix 850G
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 9, 'ASUS ROG Strix 850G 850W 80+ Gold', 3700000, 'Nguồn 850W cho fan ROG, tản nhiệt tích hợp, full modular.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 12. Thermaltake Toughpower GF1 750W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 8, 'Thermaltake Toughpower GF1 750W 80+ Gold', 2600000, 'Nguồn full modular với quạt Zero RPM thông minh.', '750W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 750, '80+ Gold', 1);

-- 13. EVGA SuperNOVA 850 G6
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 27, 'EVGA SuperNOVA 850 G6 850W 80+ Gold', 3300000, 'Nguồn 850W từ EVGA, chất lượng được khẳng định.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 14. be quiet! Pure Power 11 FM 750W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 12, 'be quiet! Pure Power 11 FM 750W 80+ Gold', 2800000, 'Full modular, hoạt động êm ái, hiệu suất cao.', '750W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 750, '80+ Gold', 1);

-- 15. Corsair RM850x SHIFT
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair RM850x SHIFT 850W 80+ Gold', 3900000, 'Thiết kế độc đáo với cổng cắm bên hông, dễ đi dây.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 16. NZXT C850 Gold
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 3, 'NZXT C850 Gold V2 850W', 3100000, 'Nguồn 850W full modular, thiết kế tối giản.', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- -----------------------------------------------------------------
-- Phân khúc trên 1000W (Workstation, Enthusiast)
-- -----------------------------------------------------------------

-- 17. Corsair RM1000e
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair RM1000e 1000W 80+ Gold', 4200000, 'Nguồn 1000W 80+ Gold, hỗ trợ ATX 3.0 và PCIe 5.0.', '1000W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1000, '80+ Gold', 1);

-- 18. Seasonic PRIME TX-1000
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 26, 'Seasonic PRIME TX-1000 1000W 80+ Titanium', 7500000, 'Hiệu suất đỉnh cao 80+ Titanium, chất lượng tốt nhất.', '1000W, 80+ Titanium, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1000, '80+ Titanium', 1);

-- 19. Cooler Master V1300 Platinum
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 1, 'Cooler Master V1300 Platinum 1300W', 6900000, 'Nguồn 1300W hiệu suất Platinum, cho các cấu hình khủng.', '1300W, 80+ Platinum, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1300, '80+ Platinum', 1);

-- 20. ASUS ROG Thor 1200P2
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 9, 'ASUS ROG Thor 1200P2 1200W 80+ Platinum', 8900000, 'Nguồn 1200W có màn hình OLED hiển thị công suất.', '1200W, 80+ Platinum, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1200, '80+ Platinum', 1);

-- 21. EVGA SuperNOVA 1600 P+
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 27, 'EVGA SuperNOVA 1600 P+ 1600W 80+ Platinum', 9500000, 'Nguồn 1600W cho các hệ thống đào coin hoặc workstation đa GPU.', '1600W, 80+ Platinum, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1600, '80+ Platinum', 1);

-- 22. be quiet! Dark Power Pro 12 1500W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 12, 'be quiet! Dark Power Pro 12 1500W 80+ Titanium', 11000000, 'Nguồn 1500W 80+ Titanium, vỏ nhôm, quạt không viền siêu tĩnh.', '1500W, 80+ Titanium, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1500, '80+ Titanium', 1);

-- 23. Thermaltake Toughpower PF1 1200W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 8, 'Thermaltake Toughpower PF1 1200W 80+ Platinum', 5800000, 'Nguồn 1200W Platinum với quạt Riing Duo RGB.', '1200W, 80+ Platinum, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1200, '80+ Platinum', 1);

-- -----------------------------------------------------------------
-- Dòng SFX (Cho case ITX nhỏ gọn)
-- -----------------------------------------------------------------

-- 24. Corsair SF750
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'Corsair SF750 750W 80+ Platinum', 4100000, 'Nguồn SFX 750W tốt nhất thị trường, hiệu suất Platinum.', '750W, SFX, 80+ Platinum, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 750, '80+ Platinum', 1);

-- 25. Cooler Master V850 SFX Gold
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 1, 'Cooler Master V850 SFX Gold 850W', 3600000, 'Nguồn SFX công suất cao 850W, 80+ Gold.', '850W, SFX, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 26. Lian Li SP850
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 4, 'Lian Li SP850 850W 80+ Gold White', 3500000, 'Nguồn SFX 850W màu trắng, dây cáp bọc dù.', '850W, SFX, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- -----------------------------------------------------------------
-- Các lựa chọn đa dạng khác
-- -----------------------------------------------------------------

-- 27. Deepcool PQ1000M
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 10, 'Deepcool PQ1000M 1000W 80+ Gold', 3900000, 'Nguồn 1000W từ Deepcool, dựa trên nền tảng của Seasonic.', '1000W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1000, '80+ Gold', 1);

-- 28. FSP Hydro G Pro 1000W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 28, 'FSP Hydro G Pro 1000W 80+ Gold', 4100000, 'Nguồn 1000W từ FSP, bền bỉ và ổn định.', '1000W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 1000, '80+ Gold', 1);

-- 29. MSI MPG A850G PCIE5
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 2, 'MSI MPG A850G 850W 80+ Gold PCIE5', 3500000, 'Nguồn 850W sẵn sàng cho chuẩn PCIe 5.0 (cáp 12VHPWR).', '850W, 80+ Gold, Full-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 850, '80+ Gold', 1);

-- 30. Antec NeoECO Gold Zen 700W
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (7, 29, 'Antec NeoECO Gold Zen 700W', 2100000, 'Nguồn 700W 80+ Gold, non-modular giá tốt.', '700W, 80+ Gold, Non-Modular');
INSERT INTO power_supply (product_id, wattage, efficiency, modular) VALUES (LAST_INSERT_ID(), 700, '80+ Gold', 0);

-- =================================================================
-- SCRIPT INSERT 20 SẢN PHẨM TẢN NHIỆT (COOLING)
-- =================================================================

-- -----------------------------------------------------------------
-- Tản nhiệt khí (Air Coolers)
-- -----------------------------------------------------------------

-- 1. Cooler Master Hyper 212 Spectrum V3
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 1, 'Cooler Master Hyper 212 Spectrum V3', 750000, 'Tản nhiệt quốc dân, hiệu năng tốt trong tầm giá.', 'Tản khí, 4 ống đồng, quạt ARGB');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 180, 120, NULL);

-- 2. Noctua NH-D15
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 7, 'Noctua NH-D15 chromax.black', 2890000, 'Vua tản nhiệt khí, hiệu năng đỉnh cao và cực kỳ yên tĩnh.', 'Tản khí, tháp đôi, 2 quạt 140mm');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 250, 140, NULL);

-- 3. Deepcool AK400
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 10, 'Deepcool AK400 Performance', 650000, 'Hiệu năng/giá thành xuất sắc, thiết kế gọn gàng.', 'Tản khí, 4 ống đồng, quạt 120mm FDB');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 220, 120, NULL);

-- 4. Thermalright Phantom Spirit 120 SE ARGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 11, 'Thermalright Phantom Spirit 120 SE ARGB', 1190000, 'Hiệu năng cực mạnh với 7 ống đồng và 2 quạt.', 'Tản khí, tháp đôi, 2 quạt 120mm');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 280, 120, NULL);

-- 5. Deepcool AK620
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 10, 'Deepcool AK620 Black', 1550000, 'Tản nhiệt tháp đôi hiệu năng cao, cạnh tranh trực tiếp với các tản cao cấp.', 'Tản khí, 6 ống đồng, 2 quạt 120mm');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 260, 120, NULL);

-- 6. Noctua NH-U12A
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 7, 'Noctua NH-U12A', 2750000, 'Hiệu năng của tản 140mm trong hình hài 120mm, không cấn RAM.', 'Tản khí, 7 ống đồng, 2 quạt NF-A12x25');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 220, 120, NULL);

-- 7. Cooler Master MasterAir MA624 Stealth
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 1, 'Cooler Master MasterAir MA624 Stealth', 2600000, 'Thiết kế đen tuyền, hiệu năng cao, đi kèm cả quạt 120mm và 140mm.', 'Tản khí, tháp đôi, 6 ống đồng');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 250, 140, NULL);

-- 8. Noctua NH-L9i-17xx
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 7, 'Noctua NH-L9i-17xx', 1350000, 'Tản nhiệt low-profile cho các case ITX siêu nhỏ gọn, socket LGA1700.', 'Tản khí low-profile');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 95, 92, NULL);

-- 9. Thermalright Assassin X 120 Refined SE
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 11, 'Thermalright Assassin X 120 Refined SE', 490000, 'Tản nhiệt khí giá rẻ cho các CPU tầm trung.', 'Tản khí, 4 ống đồng');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 180, 120, NULL);

-- 10. be quiet! Dark Rock Pro 4
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 12, 'be quiet! Dark Rock Pro 4', 2400000, 'Hiệu năng đỉnh cao đi kèm với sự tĩnh lặng tuyệt đối.', 'Tản khí, tháp đôi, quạt Silent Wings');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'Air', 250, 135, NULL);


-- -----------------------------------------------------------------
-- Tản nhiệt nước AIO (All-In-One Liquid Coolers)
-- -----------------------------------------------------------------

-- 11. Cooler Master MasterLiquid 240L Core ARGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 1, 'Cooler Master MasterLiquid 240L Core ARGB', 1890000, 'AIO 240mm giá cả phải chăng, pump Gen S mới.', 'AIO, Radiator 240mm, 2 quạt ARGB');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 250, 120, 240);

-- 12. Corsair H150i Elite Capellix XT
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 2, 'Corsair H150i Elite Capellix XT White', 4990000, 'AIO 360mm cao cấp, hiệu năng mạnh mẽ, LED Capellix rực rỡ.', 'AIO, Radiator 360mm, 3 quạt AF RGB ELITE');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 350, 120, 360);

-- 13. NZXT Kraken 240 RGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 3, 'NZXT Kraken 240 RGB Black', 3500000, 'Thiết kế mặt pump LCD độc đáo, hiển thị thông số hoặc ảnh GIF.', 'AIO, Radiator 240mm, màn hình LCD');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 280, 120, 240);

-- 14. Lian Li Galahad II Trinity 360
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 4, 'Lian Li Galahad II Trinity 360 ARGB', 3800000, 'Hiệu năng cao, có 3 nắp pump thay thế, quạt dễ dàng nối tiếp.', 'AIO, Radiator 360mm, 3 quạt ARGB');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 320, 120, 360);

-- 15. Deepcool LT720
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 10, 'Deepcool LT720 WH 360mm', 3200000, 'Thiết kế pump 3D vô cực, hiệu năng hàng đầu.', 'AIO, Radiator 360mm, Pump thế hệ 4');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 300, 120, 360);

-- 16. Arctic Liquid Freezer II 280
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 13, 'Arctic Liquid Freezer II 280', 3100000, 'Hiệu năng vượt trội với radiator dày và quạt VRM trên pump.', 'AIO, Radiator 280mm, 2 quạt 140mm');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 300, 140, 280);

-- 17. Corsair H100i RGB PRO XT
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 2, 'Corsair H100i RGB PRO XT', 2990000, 'AIO 240mm hiệu năng cao, điều khiển qua iCUE.', 'AIO, Radiator 240mm, 2 quạt ML');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 250, 120, 240);

-- 18. ASUS ROG RYUJIN III 360 ARGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 9, 'ASUS ROG RYUJIN III 360 ARGB', 8990000, 'Tản nhiệt AIO đầu bảng với màn hình LCD lớn và pump Asetek gen 8.', 'AIO, Radiator 360mm, màn hình LCD 3.5"');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 400, 120, 360);

-- 19. NZXT Kraken Elite 360 RGB
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 3, 'NZXT Kraken Elite 360 RGB White', 6990000, 'Phiên bản cao cấp với màn hình LCD độ phân giải cao hơn.', 'AIO, Radiator 360mm, màn hình LCD Elite');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 350, 120, 360);

-- 20. Thermalright Frozen Notte 360
INSERT INTO product (category_id, brand_id, product_name, price, description, specification) VALUES (8, 11, 'Thermalright Frozen Notte 360 Black ARGB', 2100000, 'AIO 360mm hiệu năng tốt với mức giá cực kỳ cạnh tranh.', 'AIO, Radiator 360mm, 3 quạt ARGB');
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size) VALUES (LAST_INSERT_ID(), 'AIO', 300, 120, 360);




