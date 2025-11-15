
-- ==============================================
-- CATEGORY SEED DATA (Technical specs for compatibility)
-- ==============================================
INSERT INTO category (category_name, description, display_order)
VALUES
-- Component types
('Mainboard', 'Motherboard / Bo mạch chủ cho PC', 1),
('CPU', 'Central Processing Unit', 2),
('GPU', 'Graphics Processing Unit', 3),
('Memory', 'RAM modules', 4),
('Storage', 'HDD/SSD storage', 5),
('Case', 'PC Case', 6),
('Power Supply', 'PSU', 7),
('Cooling', 'CPU/GPU cooling', 8),
('Other', 'Other product', 9),
-- Socket types
('Socket AM4', 'AMD AM4 Socket', 100),
('Socket AM5', 'AMD AM5 Socket', 101),
('Socket LGA1700', 'Intel LGA1700', 102),
('Socket LGA1200', 'Intel LGA1200', 103),
('Socket LGA1151', 'Intel LGA1151', 104),
-- Form factors
('ATX', 'ATX Form Factor', 300),
('Micro-ATX', 'Micro-ATX Form Factor', 301),
('Mini-ITX', 'Mini-ITX Form Factor', 302),
('E-ATX', 'E-ATX Form Factor', 303),
-- Memory types
('DDR4', 'DDR4 Memory', 400),
('DDR5', 'DDR5 Memory', 401),
-- PCIe versions
('PCIe 3.0', 'PCIe 3.0', 200),
('PCIe 4.0', 'PCIe 4.0', 201),
('PCIe 5.0', 'PCIe 5.0', 202),
-- Storage interfaces
('SATA', 'SATA Interface', 500),
('M.2 NVMe', 'M.2 NVMe', 501),
('M.2 SATA', 'M.2 SATA', 502),
-- PSU form factors
('ATX PSU', 'ATX PSU', 600),
('SFX PSU', 'SFX PSU', 601),
-- Cooling types
('Air Cooling', 'Air Cooling', 700),
('AIO Cooling', 'AIO Liquid Cooling', 701);

-- ==============================================
-- BRAND DATA
-- ==============================================
-- Brand (with status column - 1=active, 0=inactive)
INSERT INTO brand (name, description, website, status)
VALUES
    ('ASUS', 'ASUS - nhà sản xuất phần cứng máy tính hàng đầu', 'https://www.asus.com', 1),
    ('MSI', 'MSI - thương hiệu gaming nổi tiếng', 'https://www.msi.com', 1),
    ('Gigabyte', 'Gigabyte - hãng phần cứng máy tính', 'https://www.gigabyte.com', 1),
    ('Intel', 'Intel - CPU, chipset, và nhiều sản phẩm khác', 'https://www.intel.com', 1),
    ('AMD', 'AMD - CPU và GPU nổi tiếng', 'https://www.amd.com', 1),
    ('NVIDIA', 'NVIDIA - hãng GPU nổi tiếng', 'https://www.nvidia.com', 1),
    ('Corsair', 'Corsair - RAM, PSU, case và cooling', 'https://www.corsair.com', 1),
    ('G.Skill', 'G.Skill - RAM hiệu năng cao', 'https://www.gskill.com', 1),
    ('Kingston', 'Kingston - RAM và SSD phổ biến', 'https://www.kingston.com', 1),
    ('Cooler Master', 'Cooler Master - chuyên case và cooling', 'https://www.coolermaster.com', 1),
    ('Seagate', 'Seagate - ổ cứng HDD/SSD', 'https://www.seagate.com', 1),
    ('Western Digital', 'Western Digital - HDD/SSD nổi tiếng', 'https://www.westerndigital.com', 1),
    ('NZXT', 'Hãng sản xuất case, tản nhiệt và mainboard với thiết kế tối giản.', 'https://nzxt.com', 1),
    ('Lian Li', 'Thương hiệu case và tản nhiệt cao cấp từ nhôm.', 'https://lian-li.com', 1),
    ('Fractal Design', 'Hãng case và tản nhiệt từ Thụy Điển, nổi tiếng với thiết kế tinh tế và hiệu quả.', 'https://www.fractal-design.com', 1),
    ('Phanteks', 'Chuyên về case, tản nhiệt và quạt hiệu năng cao.', 'https://www.phanteks.com', 1),
    ('Thermaltake', 'Thương hiệu lớn về case, nguồn, tản nhiệt và gaming gear.', 'https://www.thermaltake.com', 1),
    ('Noctua', 'Hãng tản nhiệt khí và quạt hàng đầu thế giới từ Áo, nổi tiếng về sự yên tĩnh và hiệu năng.', 'https://noctua.at', 1),
    ('Deepcool', 'Hãng sản xuất tản nhiệt, case, nguồn với P/P tốt.', 'https://www.deepcool.com', 1),
    ('Thermalright', 'Thương hiệu tản nhiệt khí hiệu năng cao, giá cả cạnh tranh.', 'https://www.thermalright.com', 1),
    ('be quiet!', 'Hãng sản xuất linh kiện từ Đức, tập trung vào sự yên tĩnh.', 'https://www.bequiet.com', 1),
    ('Arctic', 'Hãng tản nhiệt nổi tiếng với dòng AIO Liquid Freezer II.', 'https://www.arctic.de', 1),
    ('Crucial', 'Thương hiệu con của Micron, chuyên về RAM và SSD.', 'https://www.crucial.com', 1),
    ('Teamgroup', 'Hãng sản xuất RAM và SSD, đặc biệt là dòng T-Force gaming.', 'https://www.teamgroupinc.com', 1),
    ('Samsung', 'Tập đoàn điện tử hàng đầu, sản xuất SSD tiêu dùng tốt nhất thị trường.', 'https://www.samsung.com', 1),
    ('SK Hynix', 'Nhà sản xuất chip nhớ lớn, cũng sản xuất SSD hiệu năng cao.', 'https://ssd.skhynix.com', 1),
    ('Sabrent', 'Thương hiệu nổi tiếng với các SSD NVMe tốc độ cao.', 'https://sabrent.com', 1),
    ('Adata', 'Hãng sản xuất RAM và SSD, gaming gear dưới thương hiệu XPG.', 'https://www.adata.com', 1),
    ('SanDisk', 'Thương hiệu con của Western Digital, chuyên về bộ nhớ flash và SSD.', 'https://www.sandisk.com', 1),
    ('Toshiba', 'Tập đoàn Nhật Bản, sản xuất ổ cứng HDD và chip nhớ.', 'https://www.toshiba-storage.com', 1),
    ('Seasonic', 'Thương hiệu nguồn máy tính (PSU) cao cấp và đáng tin cậy nhất.', 'https://seasonic.com', 1),
    ('EVGA', 'Hãng sản xuất card đồ họa, mainboard và nguồn nổi tiếng.', 'https://www.evga.com', 1),
    ('FSP', 'Nhà sản xuất nguồn máy tính (PSU) lớn và uy tín.', 'https://www.fsplifestyle.com', 1),
    ('Antec', 'Thương hiệu lâu đời về case, nguồn và quạt.', 'https://www.antec.com', 1),
    ('Hyte', 'Thương hiệu con của NZXT, chuyên về các vỏ case độc đáo như Y60.', 'https://hyte.com', 1),
    ('ASRock', 'Nhà sản xuất mainboard và GPU, nổi tiếng với giá trị tốt.', 'https://www.asrock.com', 1),
    ('Golden Field', 'Thương hiệu case và nguồn giá rẻ từ Trung Quốc.', 'https://www.goldenfield.com.cn', 1),
    ('SAMA', 'Hãng sản xuất case và cooling giá rẻ, phổ biến ở thị trường châu Á.', 'https://www.sama.com', 1),
    ('KENOO', 'Thương hiệu case budget từ Trung Quốc.', 'https://www.kenoo.com', 1),
    ('Vision', 'Hãng sản xuất case văn phòng giá rẻ.', 'https://www.visioncase.com', 1),
    ('Techware', 'Thương hiệu case và accessories gaming giá phải chăng.', 'https://www.techware.com', 1),
    ('Xigmatek', 'Hãng sản xuất case, nguồn và cooling từ Đài Loan.', 'https://www.xigmatek.com', 1),
    ('VSP', 'Thương hiệu case và PSU giá rẻ phổ biến ở Việt Nam.', 'https://www.vspgroup.vn', 1);


-- ==============================================
--  PRODUCT + MAINBOARD SEED DATA (30 bản ghi)
-- ==============================================

-- ==================================================================
-- MAINBOARD PRODUCTS
-- ==================================================================
-- Note: Mỗi mainboard sẽ có nhiều categories:
-- - Primary: Mainboard (category_id = 1)
-- - Socket: AM4/AM5/LGA1700/etc
-- - Form Factor: ATX/Micro-ATX/Mini-ITX
-- - Memory Type: DDR4/DDR5
-- - PCIe Version: 3.0/4.0/5.0
-- ==================================================================

-- 1. ASUS PRIME B550M-A (AM4, Micro-ATX, DDR4, PCIe 4.0)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity, performance_score)
VALUES (1, 'ASUS PRIME B550M-A', 150.00, 1, 'Micro ATX AM4 mainboard', 'Chipset B550', 1000, 65);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),  -- Mainboard
(@product_id, 10),  -- Socket AM4,
(@product_id, 16),  -- Micro-ATX,
(@product_id, 19),  -- DDR4,
(@product_id, 22);  -- PCIe 4.0;
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B550', 'Micro-ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

-- 2. MSI MPG X670E CARBON (AM5, ATX, DDR5, PCIe 5.0)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity, performance_score)
VALUES (2, 'MSI MPG X670E CARBON', 390.00, 1, 'High-end AM5 mainboard', 'Chipset X670E', 1000, 90);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),
(@product_id, 11),  -- Socket AM5,
(@product_id, 15),  -- ATX,
(@product_id, 20),  -- DDR5,
(@product_id, 23);  -- PCIe 5.0;
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 3, 6);

-- 3. GIGABYTE B760M DS3H DDR4 (LGA1700, Micro-ATX, DDR4, PCIe 5.0)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity, performance_score)
VALUES (3, 'GIGABYTE B760M DS3H DDR4', 140.00, 1, 'Intel LGA1700 mid-range board', 'Chipset B760', 30, 60);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),
(@product_id, 12),  -- Socket LGA1700,
(@product_id, 16),  -- Micro-ATX,
(@product_id, 19),  -- DDR4,
(@product_id, 23);  -- PCIe 5.0;
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'B760', 'Micro-ATX', 'DDR4', 4, 5333, '5.0', 2, 4);

-- 4. ASRock A620M-HDV/M.2+ (AM5, Micro-ATX, DDR5, PCIe 4.0)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity, performance_score)
VALUES (4, 'ASRock A620M-HDV/M.2+', 100.00, 1, 'Budget AM5 mainboard', 'Chipset A620', 35, 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),
(@product_id, 11),  -- Socket AM5,
(@product_id, 16),  -- Micro-ATX,
(@product_id, 20),  -- DDR5,
(@product_id, 22);  -- PCIe 4.0;
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'A620', 'Micro-ATX', 'DDR5', 2, 6400, '4.0', 1, 4);

-- 5
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS ROG STRIX Z790-E GAMING', 420.00, 1, 'Top-tier Intel board', 'Chipset Z790', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

-- 6
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI PRO H610M-E DDR4', 110.00, 1, 'Entry-level LGA1700', 'Chipset H610', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

-- 7
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE X570 AORUS ELITE', 200.00, 1, 'Mid-range AM4 board', 'Chipset X570', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 8
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS TUF GAMING B550-PLUS', 170.00, 1, 'Durable AM4 board               ', 'Chipset B550', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B550', 'ATX', 'DDR4', 4, 4800, '4.0', 2, 6);

-- 9
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock H510M-HDV', 90.00, 1, 'Intel entry board', 'Chipset H510', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 13),  -- Socket LGA1200
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1200', 'H510', 'Micro-ATX', 'DDR4', 2, 3200, '3.0', 1, 4);

-- 10
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI B450 TOMAHAWK MAX II', 120.00, 1, 'Popular AM4 board', 'Chipset B450', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B450', 'ATX', 'DDR4', 4, 4133, '3.0', 1, 6);

-- 11
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE B650 AORUS ELITE', 260.00, 1, 'AM5 PCIe5 DDR5', 'Chipset B650', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

-- 12
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS PRIME H610M-K D4', 110.00, 1, 'Intel H610 DDR4 board', 'Chipset H610', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

-- 13
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock Z690 PG RIPTIDE', 250.00, 1, 'Z690 chipset DDR5 board', 'Chipset Z690', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'Z690', 'ATX', 'DDR5', 4, 6400, '5.0', 3, 6);

-- 14
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI MAG B550 TOMAHAWK', 180.00, 1, 'AM4 gaming board', 'Chipset B550', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B550', 'ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

-- 15
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE Z790 AORUS MASTER', 480.00, 1, 'Z790 flagship', 'Chipset Z790', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

-- 16
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS ROG CROSSHAIR X670E HERO', 490.00, 1, 'AM5 Enthusiast board', 'Chipset X670E', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 4, 6);

-- 17
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI MPG B650M EDGE WIFI', 240.00, 1, 'AM5 DDR5 compact', 'Chipset B650', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 16),  -- Micro-ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'B650', 'Micro-ATX', 'DDR5', 4, 7000, '5.0', 2, 6);

-- 18
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock X570 PHANTOM GAMING 4', 180.00, 1, 'AM4 solid board', 'Chipset X570', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 19
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE A520M S2H', 90.00, 1, 'Entry AM4 mainboard', 'Chipset A520', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'A520', 'Micro-ATX', 'DDR4', 2, 4400, '3.0', 1, 4);

-- 20
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS PRIME B450M-K II', 100.00, 1, 'Budget AM4', 'Chipset B450', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

-- 21
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI Z590 PRO WIFI', 260.00, 1, 'Intel 11th Gen Z590', 'Chipset Z590', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 13),  -- Socket LGA1200
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1200', 'Z590', 'ATX', 'DDR4', 4, 5333, '4.0', 2, 6);

-- 22
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE B365M DS3H', 110.00, 1, 'Older Intel 9th Gen', 'Chipset B365', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'B365', 'Micro-ATX', 'DDR4', 4, 2666, '3.0', 1, 4);

-- 23
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock B450M PRO4', 110.00, 1, 'AM4 classic', 'Chipset B450', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

-- 24
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS PRIME X570-P', 190.00, 1, 'AM4 PCIe 4.0 board', 'Chipset X570', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 15),  -- ATX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

-- 25
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI B650 TOMAHAWK WIFI', 250.00, 1, 'AM5 DDR5 mid-range', 'Chipset B650', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

-- 26
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE X399 AORUS XTREME', 450.00, 1, 'TR4 workstation board', 'Chipset X399', 8);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket (TR4 not in list, using AM4 placeholder)
(@product_id, 18),  -- E-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'TR4', 'X399', 'E-ATX', 'DDR4', 8, 3600, '3.0', 3, 8);

-- 27
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock Z790M-ITX WIFI', 280.00, 1, 'Compact Intel Z790', 'Chipset Z790', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 12),  -- Socket LGA1700
(@product_id, 17),  -- Mini-ITX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1700', 'Z790', 'Mini-ITX', 'DDR5', 2, 7800, '5.0', 2, 4);

-- 28
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS ROG STRIX B650E-F GAMING WIFI', 300.00, 1, 'AM5 DDR5 gaming', 'Chipset B650E', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'B650E', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

-- 29
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI PRO X670-P WIFI', 270.00, 1, 'AM5 PCIe5 DDR5', 'Chipset X670', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 11),  -- Socket AM5
(@product_id, 15),  -- ATX
(@product_id, 20),  -- DDR5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM5', 'X670', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

-- 30
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'GIGABYTE B550I AORUS PRO AX', 220.00, 1, 'Mini ITX AM4 board with Wi-Fi', 'Chipset B550I', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 17),  -- Mini-ITX
(@product_id, 19),  -- DDR4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'B550I', 'Mini-ITX', 'DDR4', 2, 4733, '4.0', 2, 4);

-- 31. ASUS Prime H310M-K R2.0 (Entry-level Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS Prime H310M-K R2.0', 45.00, 1, 'Intel H310 basic mainboard for 8th/9th Gen CPUs', 'Chipset H310', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 0, 4);

-- 32. MSI A320M-A Pro (Entry-level AMD A320, AM4)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI A320M-A Pro', 48.00, 1, 'AMD A320 minimal mainboard for Ryzen CPUs', 'Chipset A320', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'A320', 'Micro-ATX', 'DDR4', 2, 3200, '3.0', 1, 4);

-- 33. Gigabyte H410M H (Entry Intel H410, LGA1200)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte H410M H', 55.00, 1, 'Intel H410 basic mainboard for 10th Gen CPUs', 'Chipset H410', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 13),  -- Socket LGA1200
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1200', 'H410', 'Micro-ATX', 'DDR4', 2, 2933, '3.0', 1, 4);

-- 34. ASRock H310CM-HDV (Ultra budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock H310CM-HDV', 42.00, 1, 'Ultra budget Intel H310 for 8th/9th Gen CPUs', 'Chipset H310', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 0, 4);

-- 35. MSI H310M PRO-VDH PLUS (Budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI H310M PRO-VDH PLUS', 46.00, 1, 'Budget Intel H310 with VGA output', 'Chipset H310', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 1, 4);

-- 36. Gigabyte H310M S2H 2.0 (Budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte H310M S2H 2.0', 49.00, 1, 'Intel H310 with HDMI and VGA support', 'Chipset H310', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 1, 4);

-- 37. ASRock A320M-HDV R4.0 (Budget AMD A320, AM4)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (4, 'ASRock A320M-HDV R4.0', 45.00, 1, 'AMD A320 for Ryzen 1st-3rd Gen CPUs', 'Chipset A320', 46);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'A320', 'Micro-ATX', 'DDR4', 2, 3200, '3.0', 1, 4);

-- 38. Biostar A320MH (Ultra budget AMD A320, AM4)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'Biostar A320MH', 40.00, 1, 'Ultra budget AMD A320 basic features', 'Chipset A320', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 10),  -- Socket AM4
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'AM4', 'A320', 'Micro-ATX', 'DDR4', 2, 2933, '3.0', 0, 4);

-- 39. ASUS PRIME H310M-E R2.0 (Budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS PRIME H310M-E R2.0', 47.00, 1, 'ASUS budget Intel H310 with basic features', 'Chipset H310', 44);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 0, 4);

-- 40. MSI H310M PRO-M2 PLUS (Compact budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI H310M PRO-M2 PLUS', 44.00, 1, 'Compact Intel H310 with M.2 support', 'Chipset H310', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2666, '3.0', 1, 4);

-- 41. Gigabyte H310M DS2 2.0 (Ultra compact budget Intel H310, LGA1151)
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte H310M DS2 2.0', 41.00, 1, 'Ultra compact Intel H310 for office PCs', 'Chipset H310', 52);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 1),   -- Mainboard
(@product_id, 14),  -- Socket LGA1151
(@product_id, 16),  -- Micro-ATX
(@product_id, 19),  -- DDR4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports)
VALUES (@product_id, 'LGA1151', 'H310', 'Micro-ATX', 'DDR4', 2, 2400, '3.0', 0, 4);

-- ==============================================
--  PRODUCT + CPU SEED DATA (30 bản ghi)
-- ==============================================

-- 1
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 5 5600X', 220.00, 1, '6 cores 12 threads', 'Base 3.7GHz Boost 4.6GHz', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '4.0', 0);

-- 2
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i5-12400F', 180.00, 1, '6 Performance cores', 'Base 2.5GHz Boost 4.4GHz', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 65, 4800, 2, '5.0', 0);

-- 3
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i7-13700K', 420.00, 1, '16 cores hybrid', 'Base 3.4GHz Boost 5.4GHz', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 4
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 9 7950X', 590.00, 1, '16 cores 32 threads', 'Base 4.5GHz Boost 5.7GHz', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 170, 5200, 2, '5.0', 1);

-- 5
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 7 5800X3D', 370.00, 1, '8 cores 3D cache', 'Base 3.4GHz Boost 4.5GHz', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 105, 3200, 2, '4.0', 0);

-- 6
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i9-13900K', 600.00, 1, '24 cores hybrid', 'Base 3.0GHz Boost 5.8GHz', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 7
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 5 7600', 240.00, 1, '6 cores AM5', 'Base 3.8GHz Boost 5.1GHz', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 65, 5200, 2, '5.0', 1);

-- 8
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i3-12100F', 120.00, 1, '4 cores 8 threads', 'Base 3.3GHz Boost 4.3GHz', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 60, 4800, 2, '5.0', 0);

-- 9
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 7 7700', 340.00, 1, '8 cores AM5', 'Base 3.8GHz Boost 5.3GHz', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 65, 5200, 2, '5.0', 1);

-- 10
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i5-13600K', 320.00, 1, '14 cores hybrid', 'Base 3.5GHz Boost 5.1GHz', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 11
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 9 5950X', 550.00, 1, '16 cores 32 threads', 'Base 3.4GHz Boost 4.9GHz', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 105, 3200, 2, '4.0', 0);

-- 12
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i9-12900KS', 580.00, 1, '16 cores 24 threads', 'Base 3.4GHz Boost 5.5GHz', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 13
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 3 4100', 90.00, 1, '4 cores entry level', 'Base 3.8GHz Boost 4.0GHz', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '3.0', 0);

-- 14
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i5-14600K', 360.00, 1, '14 cores Raptor Lake Refresh', 'Base 3.5GHz Boost 5.3GHz', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 15
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 5 5500', 130.00, 1, '6 cores AM4', 'Base 3.6GHz Boost 4.2GHz', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '3.0', 0);

-- 16
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 9 7900X', 470.00, 1, '12 cores 24 threads AM5', 'Base 4.7GHz Boost 5.6GHz', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 170, 5200, 2, '5.0', 1);

-- 17
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 7 7800X3D', 450.00, 1, '8 cores 3D V-Cache AM5', 'Base 4.2GHz Boost 5.0GHz', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 120, 5200, 2, '5.0', 1);

-- 18
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i9-14900K', 620.00, 1, '24 cores Raptor Lake Refresh', 'Base 3.2GHz Boost 6.0GHz', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 125, 5600, 2, '5.0', 1);

-- 19
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i7-12700F', 310.00, 1, '12 cores hybrid no iGPU', 'Base 2.1GHz Boost 4.9GHz', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 65, 4800, 2, '5.0', 0);

-- 20
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i5-12400', 190.00, 1, '6 cores 12 threads', 'Base 2.5GHz Boost 4.4GHz', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 65, 4800, 2, '5.0', 1);

-- 21
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i3-13100', 140.00, 1, '4 cores 8 threads 13th Gen', 'Base 3.4GHz Boost 4.5GHz', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 12),  -- Socket LGA1700
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1700', 60, 4800, 2, '5.0', 1);

-- 22
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 5 4600G', 150.00, 1, '6 cores APU with Vega GPU', 'Base 3.7GHz Boost 4.2GHz', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '3.0', 1);

-- 23
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 7 5700G', 240.00, 1, '8 cores APU', 'Base 3.8GHz Boost 4.6GHz', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '3.0', 1);

-- 24
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 3 5300G', 120.00, 1, '4 cores APU entry', 'Base 4.0GHz Boost 4.2GHz', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 3200, 2, '3.0', 1);

-- 25
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i5-11400F', 160.00, 1, '6 cores 11th Gen', 'Base 2.6GHz Boost 4.4GHz', 36);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 13),  -- Socket LGA1200
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1200', 65, 3200, 2, '4.0', 0);

-- 26
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i7-11700K', 330.00, 1, '8 cores 16 threads', 'Base 3.6GHz Boost 5.0GHz', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 13),  -- Socket LGA1200
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1200', 125, 3200, 2, '4.0', 1);

-- 27
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 9 5900X', 420.00, 1, '12 cores AM4', 'Base 3.7GHz Boost 4.8GHz', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 105, 3200, 2, '4.0', 0);

-- 28
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 5 7500F', 210.00, 1, '6 cores AM5 no iGPU', 'Base 3.7GHz Boost 5.0GHz', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 11),  -- Socket AM5
(@product_id, 23);  -- PCIe 5.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM5', 65, 5200, 2, '5.0', 0);

-- 29
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'Intel Core i9-11900K', 400.00, 1, '8 cores high-end', 'Base 3.5GHz Boost 5.3GHz', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 13),  -- Socket LGA1200
(@product_id, 22);  -- PCIe 4.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'LGA1200', 125, 3200, 2, '4.0', 1);

-- 30
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'AMD Ryzen 3 3200G', 90.00, 1, '4 cores Vega 8 APU', 'Base 3.6GHz Boost 4.0GHz', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 2),   -- CPU
(@product_id, 10),  -- Socket AM4
(@product_id, 21);  -- PCIe 3.0
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu)
VALUES (@product_id, 'AM4', 65, 2933, 2, '3.0', 1);



-- ==============================================
--  PRODUCT + GPU SEED DATA (30 bản ghi)
-- ==============================================

-- 1
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 3060', 330.00, 1, '12GB GDDR6 mid-range GPU', 'Ampere architecture', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6', 170, 'PCIe x16', '4.0', 242);

-- 2
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6600 XT', 320.00, 1, '8GB GDDR6', 'RDNA 2 GPU', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 160, 'PCIe x8', '4.0', 230);

-- 3
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4070', 550.00, 1, '12GB GDDR6X', 'Ada Lovelace GPU', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6X', 200, 'PCIe x16', '4.0', 244);

-- 4
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 7900 XT', 880.00, 1, '20GB GDDR6', 'RDNA 3 GPU', 10);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 20, 'GDDR6', 300, 'PCIe x16', '4.0', 276);

-- 5
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4080', 1200.00, 1, '16GB GDDR6X high-end GPU', 'Ada Lovelace', 8);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 16, 'GDDR6X', 320, 'PCIe x16', '4.0', 304);

-- 6
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6800', 580.00, 1, '16GB GDDR6', 'RDNA 2 GPU', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 16, 'GDDR6', 250, 'PCIe x16', '4.0', 267);

-- 7
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 3050', 250.00, 1, '8GB GDDR6 entry GPU', 'Ampere', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 130, 'PCIe x8', '4.0', 242);

-- 8
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6500 XT', 180.00, 1, '4GB GDDR6', 'Entry RDNA 2 GPU', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 4, 'GDDR6', 107, 'PCIe x4', '4.0', 190);

-- 9
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4070 Ti', 790.00, 1, '12GB GDDR6X high-end', 'Ada Lovelace', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6X', 285, 'PCIe x16', '4.0', 285);

-- 10
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 7800 XT', 500.00, 1, '16GB GDDR6', 'RDNA 3 mid-high', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 16, 'GDDR6', 263, 'PCIe x16', '4.0', 267);

-- 11
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4060', 310.00, 1, '8GB GDDR6', 'Ada Lovelace', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 115, 'PCIe x8', '4.0', 242);

-- 12
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6700 XT', 370.00, 1, '12GB GDDR6 mid-range', 'RDNA 2', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6', 230, 'PCIe x16', '4.0', 267);

-- 13
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce GTX 1660 SUPER', 220.00, 1, '6GB GDDR6', 'Turing', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 21);  -- PCIe 3.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 6, 'GDDR6', 125, 'PCIe x16', '3.0', 229);

-- 14
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 5600 XT', 280.00, 1, '6GB GDDR6', 'RDNA 1 GPU', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 6, 'GDDR6', 150, 'PCIe x16', '4.0', 242);

-- 15
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 2080 SUPER', 600.00, 1, '8GB GDDR6 high-end', 'Turing', 10);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 21);  -- PCIe 3.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 250, 'PCIe x16', '3.0', 285);

-- 16
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4090', 1600.00, 1, '24GB GDDR6X flagship', 'Ada Lovelace', 6);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 24, 'GDDR6X', 450, 'PCIe x16', '4.0', 336);

-- 17
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 7600', 270.00, 1, '8GB GDDR6', 'RDNA 3 entry', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 165, 'PCIe x8', '4.0', 240);

-- 18
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4060 Ti', 420.00, 1, '8GB GDDR6 mid-high', 'Ada Lovelace', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

-- 19
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6950 XT', 700.00, 1, '16GB GDDR6 high-end', 'RDNA 2', 9);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 16, 'GDDR6', 335, 'PCIe x16', '4.0', 267);

-- 20
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 3070', 500.00, 1, '8GB GDDR6', 'Ampere', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 220, 'PCIe x16', '4.0', 242);

-- 21
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 3090', 1400.00, 1, '24GB GDDR6X high-end GPU', 'Ampere architecture', 7);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 24, 'GDDR6X', 350, 'PCIe x16', '4.0', 336);

-- 22
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4070 SUPER', 620.00, 1, '12GB GDDR6X refreshed Ada GPU', 'High performance efficiency', 13);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6X', 220, 'PCIe x16', '4.0', 244);

-- 23
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 7700 XT', 470.00, 1, '12GB GDDR6 mid-high range', 'RDNA 3 GPU', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 12, 'GDDR6', 245, 'PCIe x16', '4.0', 267);

-- 24
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6800 XT', 650.00, 1, '16GB GDDR6 enthusiast', 'RDNA 2 GPU', 11);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 16, 'GDDR6', 300, 'PCIe x16', '4.0', 267);

-- 25
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 2060', 260.00, 1, '6GB GDDR6', 'Turing mid-range GPU', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 21);  -- PCIe 3.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 6, 'GDDR6', 160, 'PCIe x16', '3.0', 229);

-- 26
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce GTX 1650 SUPER', 180.00, 1, '4GB GDDR6', 'Turing budget GPU', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 21);  -- PCIe 3.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 4, 'GDDR6', 100, 'PCIe x16', '3.0', 229);

-- 27
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 5500 XT', 160.00, 1, '8GB GDDR6', 'RDNA entry GPU', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 130, 'PCIe x8', '4.0', 230);

-- 28
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 2080 Ti', 1000.00, 1, '11GB GDDR6 flagship', 'Turing', 8);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 21);  -- PCIe 3.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 11, 'GDDR6', 260, 'PCIe x16', '3.0', 285);

-- 29
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'NVIDIA GeForce RTX 4060 SUPER', 400.00, 1, '8GB GDDR6 Ada mid-range', 'Ada Lovelace GPU', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

-- 30
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'AMD Radeon RX 6400', 140.00, 1, '4GB GDDR6 compact GPU', 'Low power RDNA 2', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 3),   -- GPU
(@product_id, 22);  -- PCIe 4.0
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length)
VALUES (@product_id, 4, 'GDDR6', 53, 'PCIe x4', '4.0', 170);

-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM BỘ NHỚ RAM (MEMORY) - GIÁ ĐÃ SỬA SANG USD
-- =================================================================

-- -----------------------------------------------------------------
-- RAM DDR4
-- -----------------------------------------------------------------

-- 1. Corsair Vengeance LPX 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance LPX 16GB (2x8GB) DDR4 3200MHz C16', 54.99, 'Kit RAM DDR4 phổ biến, hiệu năng ổn định, tản nhiệt thấp.', 'Kit 16GB, DDR4, 3200MHz, CL16', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 5, 2);

-- 2. G.Skill Ripjaws V 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Ripjaws V 16GB (2x8GB) DDR4 3600MHz C18', 61.99, 'Hiệu năng cao cho các hệ thống AMD Ryzen.', 'Kit 16GB, DDR4, 3600MHz, CL18', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3600, 6, 2);

-- 3. Kingston Fury Beast 8GB DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Beast 8GB DDR4 3200MHz', 29.99, 'Thanh RAM lẻ 8GB cho các cấu hình cơ bản.', '8GB, DDR4, 3200MHz, CL16', 60);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 8, 3200, 5, 1);

-- 4. Crucial Ballistix 32GB (2x16GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial Ballistix 32GB (2x16GB) DDR4 3200MHz C16', 109.99, 'Kit 32GB hiệu năng tốt cho gaming và làm việc đa nhiệm.', 'Kit 32GB, DDR4, 3200MHz, CL16', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3200, 7, 2);

-- 5. Teamgroup T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (17, 'Teamgroup T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz', 49.99, 'RAM giá rẻ với tản nhiệt cách điệu.', 'Kit 16GB, DDR4, 3200MHz, CL16', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 5, 2);

-- 6. G.Skill Trident Z RGB 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Trident Z RGB 16GB (2x8GB) DDR4 3600MHz C18', 84.99, 'Thiết kế RGB đẹp mắt, hiệu năng cao.', 'Kit 16GB, DDR4, 3600MHz, CL18', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3600, 8, 2);

-- 7. Corsair Dominator Platinum RGB 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Dominator Platinum RGB 32GB (2x16GB) DDR4 3600MHz', 179.99, 'Dòng RAM cao cấp nhất của Corsair, LED Capellix.', 'Kit 32GB, DDR4, 3600MHz, CL18', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3600, 10, 2);

-- 8. Kingston Fury Beast 32GB (2x16GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Beast 32GB (2x16GB) DDR4 3200MHz', 104.99, 'Kit 32GB cho người dùng cần nhiều dung lượng.', 'Kit 32GB, DDR4, 3200MHz, CL16', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3200, 7, 2);

-- 9. Crucial Pro 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial Pro 16GB (2x8GB) DDR4 3200MHz', 52.99, 'RAM ổn định, không RGB, tập trung vào hiệu năng.', 'Kit 16GB, DDR4, 3200MHz, CL22', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 5, 2);

-- 10. G.Skill Ripjaws V 32GB (2x16GB) DDR4 4000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Ripjaws V 32GB (2x16GB) DDR4 4000MHz C18', 149.99, 'Tốc độ bus cực cao cho các hệ thống Intel cao cấp.', 'Kit 32GB, DDR4, 4000MHz, CL18', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 4000, 8, 2);

-- 11. Corsair Vengeance RGB Pro 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance RGB Pro 16GB (2x8GB) DDR4 3200MHz', 69.99, 'Dòng RAM RGB tầm trung phổ biến.', 'Kit 16GB, DDR4, 3200MHz, CL16', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 7, 2);

-- 12. Teamgroup T-Create Expert 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (17, 'Teamgroup T-Create Expert 32GB (2x16GB) DDR4 3600MHz', 99.99, 'Dòng RAM chuyên cho người làm sáng tạo nội dung.', 'Kit 32GB, DDR4, 3600MHz, CL18', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3600, 7, 2);

-- 13. Kingston Fury Renegade 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Renegade 16GB (2x8GB) DDR4 3600MHz', 75.99, 'Dòng RAM hiệu năng cao của Kingston.', 'Kit 16GB, DDR4, 3600MHz, CL16', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3600, 7, 2);

-- 14. G.Skill Trident Z Royal Silver 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Trident Z Royal Silver 32GB (2x16GB) DDR4 3600MHz', 189.99, 'Thiết kế kim cương sang trọng, hiệu năng đỉnh cao.', 'Kit 32GB, DDR4, 3600MHz, CL18', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3600, 10, 2);

-- 15. Corsair Vengeance LPX 32GB (2x16GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance LPX 32GB (2x16GB) DDR4 3600MHz C18', 114.99, 'Kit 32GB tốc độ cao, tản nhiệt thấp không cấn tản khí.', 'Kit 32GB, DDR4, 3600MHz, CL18', 34);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 32, 3600, 7, 2);

-- -----------------------------------------------------------------
-- RAM DDR5
-- -----------------------------------------------------------------

-- 16. Corsair Vengeance 32GB (2x16GB) DDR5 5600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance 32GB (2x16GB) DDR5 5600MHz C36', 134.99, 'Kit RAM DDR5 tiêu chuẩn cho các hệ thống mới.', 'Kit 32GB, DDR5, 5600MHz, CL36', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 5600, 8, 2);

-- 17. G.Skill Trident Z5 RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Trident Z5 RGB 32GB (2x16GB) DDR5 6000MHz C36', 159.99, 'Tốc độ tối ưu cho AMD EXPO, thiết kế đẹp.', 'Kit 32GB, DDR5, 6000MHz, CL36', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6000, 10, 2);

-- 18. Kingston Fury Beast 16GB (2x8GB) DDR5 5200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Beast 16GB (2x8GB) DDR5 5200MHz', 94.99, 'Kit 16GB DDR5 cho các cấu hình tầm trung.', 'Kit 16GB, DDR5, 5200MHz, CL40', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 16, 5200, 7, 2);

-- 19. Crucial Pro 32GB (2x16GB) DDR5 5600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial Pro 32GB (2x16GB) DDR5 5600MHz', 129.99, 'RAM DDR5 ổn định, hỗ trợ cả Intel XMP và AMD EXPO.', 'Kit 32GB, DDR5, 5600MHz, CL46', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 5600, 8, 2);

-- 20. Teamgroup T-Force Delta RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (17, 'Teamgroup T-Force Delta RGB 32GB (2x16GB) DDR5 6000MHz', 149.99, 'Thiết kế RGB góc cạnh, hiệu năng tốt.', 'Kit 32GB, DDR5, 6000MHz, CL38', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6000, 10, 2);

-- 21. Corsair Dominator Platinum RGB 32GB (2x16GB) DDR5 6200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Dominator Platinum RGB 32GB (2x16GB) DDR5 6200MHz', 229.99, 'Dòng RAM DDR5 cao cấp nhất của Corsair.', 'Kit 32GB, DDR5, 6200MHz, CL36', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6200, 12, 2);

-- 22. G.Skill Ripjaws S5 32GB (2x16GB) DDR5 5200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Ripjaws S5 32GB (2x16GB) DDR5 5200MHz Black', 124.99, 'Thiết kế tản nhiệt thấp, phù hợp nhiều loại tản khí.', 'Kit 32GB, DDR5, 5200MHz, CL40', 36);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 5200, 8, 2);

-- 23. Kingston Fury Renegade 32GB (2x16GB) DDR5 6400MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Renegade 32GB (2x16GB) DDR5 6400MHz', 179.99, 'Hiệu năng cao, tản nhiệt bạc-đen.', 'Kit 32GB, DDR5, 6400MHz, CL32', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6400, 10, 2);

-- 24. G.Skill Trident Z5 RGB 64GB (2x32GB) DDR5 6400MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Trident Z5 RGB 64GB (2x32GB) DDR5 6400MHz', 319.99, 'Dung lượng cực lớn cho workstation và các tác vụ nặng.', 'Kit 64GB, DDR5, 6400MHz, CL32', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 64, 6400, 12, 2);

-- 25. Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz C36', 164.99, 'Dòng Vengeance với dải LED RGB toàn cảnh.', 'Kit 32GB, DDR5, 6000MHz, CL36', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6000, 10, 2);

-- 26. Crucial 16GB (1x16GB) DDR5 4800MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial 16GB DDR5 4800MHz', 59.99, 'Thanh RAM lẻ DDR5 tiêu chuẩn JEDEC, không tản nhiệt.', '16GB, DDR5, 4800MHz, CL40', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 16, 4800, 6, 1);

-- 27. Teamgroup T-Force Xtreem 32GB (2x16GB) DDR5 7600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (17, 'Teamgroup T-Force Xtreem 32GB (2x16GB) DDR5 7600MHz', 249.99, 'Tốc độ bus cực cao cho những người đam mê ép xung.', 'Kit 32GB, DDR5, 7600MHz, CL36', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 7600, 12, 2);

-- 28. Kingston Fury Beast 64GB (2x32GB) DDR5 5600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Beast 64GB (2x32GB) DDR5 5600MHz', 279.99, 'Kit 64GB dung lượng lớn, ổn định.', 'Kit 64GB, DDR5, 5600MHz, CL40', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 64, 5600, 10, 2);

-- 29. G.Skill Flare X5 32GB (2x16GB) DDR5 6000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Flare X5 32GB (2x16GB) DDR5 6000MHz CL30', 169.99, 'Tối ưu cho AMD EXPO với độ trễ C30 cực thấp.', 'Kit 32GB, DDR5, 6000MHz, CL30', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 32, 6000, 9, 2);

-- 30. Corsair Vengeance 64GB (2x32GB) DDR5 5200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance 64GB (2x32GB) DDR5 5200MHz', 269.99, 'Kit 64GB không RGB, tập trung vào dung lượng và sự ổn định.', 'Kit 64GB, DDR5, 5200MHz, CL40', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 20);  -- DDR5
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR5', 64, 5200, 9, 2);

-- -----------------------------------------------------------------
-- Budget Memory (Giá rẻ dưới $50) - DDR4 cho build giá thấp
-- -----------------------------------------------------------------

-- 31. Crucial 8GB (1x8GB) DDR4 2666MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial 8GB DDR4 2666MHz', 19.99, 'RAM DDR4 giá rẻ cho văn phòng và build tiết kiệm.', '8GB, DDR4, 2666MHz, CL19', 60);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 8, 2666, 4, 1);

-- 32. Kingston ValueRAM 8GB (1x8GB) DDR4 2400MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston ValueRAM 8GB DDR4 2400MHz', 17.99, 'RAM DDR4 entry-level, ổn định cho máy văn phòng.', '8GB, DDR4, 2400MHz, CL17', 70);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 8, 2400, 3, 1);

-- 33. Corsair Vengeance LPX 8GB (1x8GB) DDR4 3000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance LPX 8GB DDR4 3000MHz', 24.99, 'RAM gaming giá rẻ với tản nhiệt thấp, phù hợp cho budget gaming.', '8GB, DDR4, 3000MHz, CL16', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 8, 3000, 4, 1);

-- 34. G.Skill Aegis 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (14, 'G.Skill Aegis 16GB (2x8GB) DDR4 3200MHz', 39.99, 'Kit 16GB DDR4 giá tốt cho gaming 1080p, không có tản nhiệt fancy.', 'Kit 16GB, DDR4, 3200MHz, CL16', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 8, 2);

-- 35. Crucial Ballistix 16GB (2x8GB) DDR4 3000MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial Ballistix 16GB (2x8GB) DDR4 3000MHz', 42.99, 'RAM gaming với tản nhiệt đơn giản, ổn định cho gaming.', 'Kit 16GB, DDR4, 3000MHz, CL15', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3000, 8, 2);

-- 36. Kingston Fury Beast 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston Fury Beast 16GB (2x8GB) DDR4 3200MHz', 44.99, 'RAM gaming DDR4 với tản nhiệt đen, phù hợp cho mọi build.', 'Kit 16GB, DDR4, 3200MHz, CL16', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 8, 2);

-- 37. Patriot Viper Steel 16GB (2x8GB) DDR4 3600MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Patriot Viper Steel 16GB (2x8GB) DDR4 3600MHz', 47.99, 'RAM DDR4 3600MHz giá tốt, hiệu năng cao cho gaming.', 'Kit 16GB, DDR4, 3600MHz, CL17', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3600, 9, 2);

-- 38. Team T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (17, 'Team T-Force Vulcan Z 16GB (2x8GB) DDR4 3200MHz', 38.99, 'RAM DDR4 giá rẻ với tản nhiệt màu xám, tốt cho budget build.', 'Kit 16GB, DDR4, 3200MHz, CL16', 52);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 3200, 8, 2);

-- 39. Corsair Vengeance LPX 16GB (2x8GB) DDR4 2666MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Vengeance LPX 16GB (2x8GB) DDR4 2666MHz', 36.99, 'RAM DDR4 tốc độ cơ bản, giá rẻ nhất cho build 16GB.', 'Kit 16GB, DDR4, 2666MHz, CL16', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 16, 2666, 7, 2);

-- 40. Silicon Power 8GB (1x8GB) DDR4 3200MHz
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Silicon Power 8GB DDR4 3200MHz', 21.99, 'RAM DDR4 3200MHz giá rẻ, phù hợp cho nâng cấp hoặc build entry-level.', '8GB, DDR4, 3200MHz, CL16', 65);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 4),   -- Memory
(@product_id, 19);  -- DDR4
INSERT INTO memory (product_id, type, capacity, speed, tdp, modules)
VALUES (@product_id, 'DDR4', 8, 3200, 4, 1);

-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM LƯU TRỮ (STORAGE) - GIÁ ĐÃ SỬA SANG USD
-- =================================================================

-- -----------------------------------------------------------------
-- SSD NVMe M.2 (Tốc độ cao)
-- -----------------------------------------------------------------

-- 1. Samsung 980 Pro 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (18, 'Samsung 980 Pro 1TB PCIe 4.0 NVMe', 119.99, 'SSD NVMe Gen4 hàng đầu, tốc độ cực nhanh cho gaming và công việc.', '1TB, NVMe, Read 7000MB/s, Write 5000MB/s', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7000, 5000);

-- 2. Western Digital Black SN850X 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Black SN850X 1TB PCIe 4.0 NVMe', 114.99, 'Đối thủ cạnh tranh của 980 Pro, hiệu năng đỉnh cao.', '1TB, NVMe, Read 7300MB/s, Write 6300MB/s', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7300, 6300);

-- 3. Crucial P5 Plus 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial P5 Plus 1TB PCIe 4.0 NVMe', 99.99, 'SSD Gen4 hiệu năng/giá thành tốt.', '1TB, NVMe, Read 6600MB/s, Write 5000MB/s', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 6600, 5000);

-- 4. Kingston KC3000 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston KC3000 1TB PCIe 4.0 NVMe', 119.99, 'Tốc độ đọc ghi rất cao, ổn định.', '1TB, NVMe, Read 7000MB/s, Write 6000MB/s', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7000, 6000);

-- 5. Samsung 970 Evo Plus 500GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (18, 'Samsung 970 Evo Plus 500GB PCIe 3.0 NVMe', 64.99, 'SSD NVMe Gen3 huyền thoại, vẫn rất mạnh.', '500GB, NVMe, Read 3500MB/s, Write 3200MB/s', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 500, 'NVMe', 3500, 3200);

-- 6. Western Digital Blue SN570 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Blue SN570 1TB PCIe 3.0 NVMe', 79.99, 'Lựa chọn NVMe Gen3 giá tốt cho người dùng phổ thông.', '1TB, NVMe, Read 3500MB/s, Write 3000MB/s', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 3500, 3000);

-- 7. SK Hynix Platinum P41 2TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (20, 'SK Hynix Platinum P41 2TB PCIe 4.0 NVMe', 229.99, 'Một trong những SSD Gen4 nhanh nhất thị trường.', '2TB, NVMe, Read 7000MB/s, Write 6500MB/s', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 2000, 'NVMe', 7000, 6500);

-- 8. Crucial P3 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial P3 1TB PCIe 3.0 NVMe', 71.99, 'SSD QLC NVMe giá rẻ, dung lượng lớn.', '1TB, NVMe, Read 3500MB/s, Write 3000MB/s', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 3500, 3000);

-- 9. Samsung 990 Pro 2TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (18, 'Samsung 990 Pro 2TB PCIe 4.0 NVMe', 249.99, 'Flagship mới nhất của Samsung, hiệu năng đỉnh cao.', '2TB, NVMe, Read 7450MB/s, Write 6900MB/s', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 2000, 'NVMe', 7450, 6900);

-- 10. Kingston NV2 500GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston NV2 500GB PCIe 4.0 NVMe', 45.99, 'SSD NVMe Gen4 giá rẻ nhất thị trường.', '500GB, NVMe, Read 3500MB/s, Write 2100MB/s', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 500, 'NVMe', 3500, 2100);

-- 11. Western Digital Black SN770 500GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Black SN770 500GB PCIe 4.0 NVMe', 69.99, 'SSD Gen4 DRAM-less hiệu năng cao.', '500GB, NVMe, Read 5000MB/s, Write 4000MB/s', 36);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 500, 'NVMe', 5000, 4000);

-- 12. Sabrent Rocket 4 Plus 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (21, 'Sabrent Rocket 4 Plus 1TB PCIe 4.0 NVMe', 134.99, 'Thương hiệu nổi tiếng với các SSD hiệu năng cao.', '1TB, NVMe, Read 7100MB/s, Write 6600MB/s', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7100, 6600);

-- 13. Gigabyte AORUS Gen4 7000s 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte AORUS Gen4 7000s 1TB', 129.99, 'SSD Gen4 đi kèm tản nhiệt hầm hố.', '1TB, NVMe, Read 7000MB/s, Write 5500MB/s', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7000, 5500);

-- 14. Corsair MP600 PRO LPX 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair MP600 PRO LPX 1TB', 124.99, 'Tương thích với PS5, tản nhiệt thấp.', '1TB, NVMe, Read 7100MB/s, Write 6800MB/s', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- M.2 NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7100, 6800);

-- 15. Adata XPG Gammix S70 Blade 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (22, 'Adata XPG Gammix S70 Blade 1TB', 109.99, 'Tốc độ cao với tản nhiệt mỏng đi kèm.', '1TB, NVMe, Read 7400MB/s, Write 6400MB/s', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 25);  -- NVMe
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'NVMe', 7400, 6400);

-- -----------------------------------------------------------------
-- SSD SATA 2.5 inch
-- -----------------------------------------------------------------

-- 16. Samsung 870 Evo 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (18, 'Samsung 870 Evo 1TB 2.5 inch SATA III', 89.99, 'SSD SATA 2.5" tốt nhất thị trường, độ bền cao.', '1TB, SATA, Read 560MB/s, Write 530MB/s', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'SATA', 560, 530);

-- 17. Crucial MX500 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial MX500 1TB 2.5 inch SATA III', 79.99, 'Đối thủ của 870 Evo, hiệu năng ổn định và bền bỉ.', '1TB, SATA, Read 560MB/s, Write 510MB/s', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'SATA', 560, 510);

-- 18. Western Digital Blue 500GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Blue 500GB 2.5 inch SATA III', 45.99, 'SSD SATA phổ thông, lựa chọn tốt để nâng cấp từ HDD.', '500GB, SATA, Read 560MB/s, Write 530MB/s', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 500, 'SATA', 560, 530);

-- 19. Kingston A400 480GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (15, 'Kingston A400 480GB 2.5 inch SATA III', 35.99, 'SSD giá rẻ cho các máy tính văn phòng.', '480GB, SATA, Read 500MB/s, Write 450MB/s', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 480, 'SATA', 500, 450);

-- 20. Samsung 870 QVO 2TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (18, 'Samsung 870 QVO 2TB 2.5 inch SATA III', 149.99, 'SSD QLC dung lượng lớn để lưu game và dữ liệu.', '2TB, SATA, Read 560MB/s, Write 530MB/s', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 2000, 'SATA', 560, 530);

-- 21. SanDisk Ultra 3D 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (23, 'SanDisk Ultra 3D 1TB 2.5 inch SATA III', 81.99, 'SSD 3D NAND đáng tin cậy.', '1TB, SATA, Read 560MB/s, Write 530MB/s', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 1000, 'SATA', 560, 530);

-- 22. Crucial BX500 240GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (16, 'Crucial BX500 240GB 2.5 inch SATA III', 27.99, 'Dung lượng nhỏ để cài hệ điều hành.', '240GB, SATA, Read 540MB/s, Write 500MB/s', 60);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 240, 'SATA', 540, 500);

-- 23. Gigabyte SSD 120GB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte SSD 120GB 2.5 inch SATA III', 20.99, 'SSD giá cực rẻ cho các máy tính cũ.', '120GB, SATA, Read 500MB/s, Write 380MB/s', 65);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'SSD', 120, 'SATA', 500, 380);

-- -----------------------------------------------------------------
-- HDD 3.5 inch (Ổ cứng cơ)
-- -----------------------------------------------------------------

-- 24. Seagate Barracuda 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (24, 'Seagate Barracuda 1TB 3.5 inch 7200RPM', 39.99, 'Ổ cứng HDD phổ thông nhất để lưu trữ dữ liệu.', '1TB, HDD, 7200RPM, SATA', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 1000, 'SATA', 220, 220);

-- 25. Western Digital Blue 2TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Blue 2TB 3.5 inch 7200RPM', 57.99, 'Lựa chọn 2TB tin cậy từ Western Digital.', '2TB, HDD, 7200RPM, SATA', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 2000, 'SATA', 215, 215);

-- 26. Seagate IronWolf 4TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (24, 'Seagate IronWolf 4TB NAS HDD', 119.99, 'Ổ cứng chuyên dụng cho hệ thống lưu trữ mạng (NAS).', '4TB, HDD, 5900RPM, SATA', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 4000, 'SATA', 180, 180);

-- 27. Western Digital Black 4TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Black 4TB 3.5 inch 7200RPM', 149.99, 'Dòng HDD hiệu năng cao dành cho game thủ và người dùng chuyên nghiệp.', '4TB, HDD, 7200RPM, SATA', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 4000, 'SATA', 227, 227);

-- 28. Toshiba P300 1TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (25, 'Toshiba P300 1TB 3.5 inch 7200RPM', 38.99, 'Ổ cứng HDD 1TB từ Toshiba, bền bỉ.', '1TB, HDD, 7200RPM, SATA', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 1000, 'SATA', 210, 210);

-- 29. Seagate SkyHawk 2TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (24, 'Seagate SkyHawk 2TB Surveillance HDD', 64.99, 'Ổ cứng chuyên dụng cho ghi hình camera giám sát.', '2TB, HDD, 5900RPM, SATA', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 2000, 'SATA', 180, 180);

-- 30. Western Digital Purple 4TB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (19, 'WD Purple 4TB Surveillance HDD', 109.99, 'Đối thủ của SkyHawk, chuyên cho camera.', '4TB, HDD, 5400RPM, SATA', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 5),   -- Storage
(@product_id, 24);  -- SATA
INSERT INTO storage (product_id, type, capacity, interface, read_speed, write_speed)
VALUES (@product_id, 'HDD', 4000, 'SATA', 150, 150);


-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM VỎ CASE (PC CASE) - GIÁ ĐÃ SỬA SANG USD
-- =================================================================

-- -----------------------------------------------------------------
-- Phân khúc giá rẻ (Budget Cases)
-- -----------------------------------------------------------------

-- 1. Cooler Master MasterBox Q300L
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MasterBox Q300L', 49.99, 'Vỏ case Micro-ATX nhỏ gọn, linh hoạt với tấm lọc bụi từ tính.', 'Micro-ATX, Mini-ITX', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 360, 159, 'ATX');

-- 2. Corsair Carbide Series 175R RGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Carbide Series 175R RGB', 54.99, 'Thiết kế tối giản với mặt kính cường lực và logo RGB.', 'Mid-Tower, ATX', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 330, 160, 'ATX');

-- 3. Thermaltake Versa H18
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Versa H18', 44.99, 'Vỏ case Micro-ATX giá rẻ, tối ưu cho luồng khí.', 'Micro-ATX', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 350, 155, 'ATX');

-- 4. NZXT H510
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT H510 Matte White', 69.99, 'Thiết kế biểu tượng của NZXT, hiện đại và sạch sẽ.', 'Mid-Tower, ATX', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 381, 165, 'ATX');

-- 5. Phanteks Eclipse P300A Mesh
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (6, 'Phanteks Eclipse P300A Mesh', 59.99, 'Mặt trước dạng lưới cho hiệu năng tản nhiệt vượt trội.', 'Mid-Tower, ATX', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 355, 165, 'ATX');

-- -----------------------------------------------------------------
-- Phân khúc tầm trung (Mid-range Cases)
-- -----------------------------------------------------------------

-- 6. Fractal Design Meshify C
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (5, 'Fractal Design Meshify C TG', 94.99, 'Thiết kế mặt lưới kim cương tối ưu luồng gió, nội thất thông minh.', 'Mid-Tower, ATX', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 315, 170, 'ATX');

-- 7. Lian Li Lancool 215
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li Lancool 215 Black', 89.99, 'Trang bị sẵn 2 quạt ARGB 200mm phía trước cho luồng gió cực mạnh.', 'Mid-Tower, ATX', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 370, 166, 'ATX');

-- 8. Cooler Master MasterBox TD500 Mesh
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MasterBox TD500 Mesh ARGB', 104.99, 'Mặt trước 3D độc đáo, đi kèm 3 quạt ARGB.', 'Mid-Tower, ATX', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 410, 165, 'ATX');

-- 9. Corsair 4000D Airflow
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair 4000D Airflow Black', 99.99, 'Thiết kế thông minh, quản lý dây cáp dễ dàng, luồng khí tốt.', 'Mid-Tower, ATX', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 360, 170, 'ATX');

-- 10. NZXT H5 Flow
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT H5 Flow Black', 94.99, 'Phiên bản tối ưu luồng gió của H5, có quạt nghiêng cho GPU.', 'Mid-Tower, ATX', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 365, 165, 'ATX');

-- 11. ASUS TUF Gaming GT301
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (9, 'ASUS TUF Gaming GT301', 84.99, 'Thiết kế hầm hố, mặt trước tổ ong, có giá treo tai nghe.', 'Mid-Tower, ATX', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 320, 160, 'ATX');

-- 12. Phanteks Eclipse G360A
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (6, 'Phanteks Eclipse G360A Black', 109.99, 'Hiệu năng tản nhiệt cao với 3 quạt ARGB và mặt lưới siêu mịn.', 'Mid-Tower, ATX', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 400, 162, 'ATX');

-- -----------------------------------------------------------------
-- Phân khúc cao cấp (High-end Cases)
-- -----------------------------------------------------------------

-- 13. Lian Li O11 Dynamic EVO
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li O11 Dynamic EVO Black', 179.99, 'Thiết kế bể cá trứ danh, có thể đảo ngược layout.', 'Mid-Tower, ATX, E-ATX', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 422, 167, 'ATX');

-- 14. Corsair 5000D Airflow
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair 5000D Airflow White', 174.99, 'Không gian rộng rãi, hỗ trợ radiator lớn, quản lý dây cáp chuyên nghiệp.', 'Mid-Tower, ATX', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 420, 170, 'ATX');

-- 15. Fractal Design Torrent
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (5, 'Fractal Design Torrent Black TG', 219.99, 'Vua tản nhiệt khí với 2 quạt 180mm phía trước, thiết kế độc đáo.', 'Mid-Tower, E-ATX', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 423, 188, 'ATX');

-- 16. NZXT H7 Flow
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT H7 Flow White', 139.99, 'Thiết kế tối giản, không gian rộng, luồng gió tốt hơn H7 tiêu chuẩn.', 'Mid-Tower, ATX', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 400, 185, 'ATX');

-- 17. Cooler Master HAF 500
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master HAF 500 Black', 134.99, 'Huyền thoại tản nhiệt HAF trở lại với 2 quạt 200mm ARGB.', 'Mid-Tower, ATX', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 410, 167, 'ATX');

-- 18. Phanteks Enthoo Evolv X
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (6, 'Phanteks Enthoo Evolv X Glass', 229.99, 'Chất liệu nhôm cao cấp, hỗ trợ lắp 2 hệ thống.', 'Mid-Tower, E-ATX', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 435, 190, 'ATX');

-- 19. Lian Li Lancool III
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li Lancool III RGB Black', 169.99, 'Thiết kế module, dễ dàng lắp đặt, đi kèm 4 quạt PWM ARGB.', 'Mid-Tower, E-ATX', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 435, 185, 'ATX');

-- -----------------------------------------------------------------
-- Dòng Full Tower và các kích thước đặc biệt
-- -----------------------------------------------------------------

-- 20. Corsair 7000D Airflow
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair 7000D Airflow Black', 299.99, 'Vỏ case Full-Tower siêu rộng rãi, hỗ trợ tản nhiệt nước custom đỉnh cao.', 'Full-Tower, E-ATX', 10);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 450, 190, 'ATX');

-- 21. Cooler Master Cosmos C700M
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master Cosmos C700M', 489.99, 'Thiết kế module đỉnh cao, có thể xoay và đảo ngược layout, vật liệu cao cấp.', 'Full-Tower, E-ATX', 8);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 490, 198, 'ATX');

-- 22. Thermaltake Tower 900
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake The Tower 900 Black', 279.99, 'Thiết kế dạng tủ trưng bày độc đáo, tối ưu cho tản nhiệt nước custom.', 'Super-Tower, E-ATX', 9);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 400, 260, 'ATX');

-- -----------------------------------------------------------------
-- Budget Cases (Giá rẻ dưới $30) - Thêm cho build gaming giá thấp
-- -----------------------------------------------------------------

-- 23. Golden Field 1000B (Giá: ~15$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (32, 'Golden Field 1000B', 15.00, 'Vỏ case văn phòng cơ bản, nhỏ gọn, không gian hạn chế cho các linh kiện.', 'Micro-ATX, Mini-ITX', 60);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 200, 140, 'ATX');

-- 24. SAMA MT100 (Giá: ~18$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (33, 'SAMA MT100', 18.00, 'Case Mid-Tower giá siêu rẻ, thiết kế tối giản, phù hợp cho lắp ráp PC cơ bản.', 'Mid-Tower, ATX', 55);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 280, 150, 'ATX');

-- 25. KENOO M200 (Giá: ~20$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (34, 'KENOO M200', 20.00, 'Vỏ case Micro-ATX với mặt nạ nhựa đơn giản, không gian hạn chế.', 'Micro-ATX, Mini-ITX', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 240, 145, 'ATX');

-- 26. Xigmatek Eros (Giá: ~25$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (37, 'Xigmatek Eros', 25.00, 'Case Mid-Tower với 1 quạt LED cơ bản, thiết kế mặt trước đơn giản, tối ưu chi phí.', 'Mid-Tower, ATX', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 290, 150, 'ATX');

-- 27. Vision V1 (Giá: ~22$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (35, 'Vision V1', 22.00, 'Vỏ case văn phòng với kiểu dáng truyền thống, không có kính cường lực.', 'Mid-Tower, ATX', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 270, 148, 'ATX');

-- 28. Sama Falcon (Giá: ~28$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (33, 'SAMA Falcon', 28.00, 'Case Micro-ATX nhỏ gọn, có mặt lưới nhỏ phía trước, hỗ trợ tản nhiệt vừa phải.', 'Micro-ATX, Mini-ITX', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 250, 150, 'ATX');

-- 29. VSP V21 (Giá: ~29$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (38, 'VSP V21', 29.00, 'Vỏ case Mid-Tower với thiết kế đơn giản nhưng đủ không gian cho các linh kiện phổ thông.', 'Mid-Tower, ATX', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 300, 155, 'ATX');

-- 30. Golden Field N1 (Giá: ~12$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (32, 'Golden Field N1', 12.00, 'Case Micro-ATX siêu nhỏ gọn, lý tưởng cho các cấu hình HTPC hoặc văn phòng cơ bản.', 'Micro-ATX, Mini-ITX', 70);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 180, 130, 'SFX');

-- 31. Techware Neo (Giá: ~24$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (36, 'Techware Neo', 24.00, 'Vỏ case Mid-Tower cơ bản, đi kèm mặt kính acrylic bên hông, giá cả phải chăng.', 'Mid-Tower, ATX', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 290, 150, 'ATX');

-- 32. VSP KA-280 (Giá: ~19$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (38, 'VSP KA-280', 19.00, 'Case Mid-Tower đơn giản, phù hợp cho các cấu hình PC không yêu cầu cao về thẩm mỹ và tản nhiệt.', 'Mid-Tower, ATX', 48);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 270, 145, 'ATX');

-- -----------------------------------------------------------------
-- Budget Cases ($30-$50) - Thêm cho build gaming giá vừa phải
-- -----------------------------------------------------------------

-- 33. Deepcool Matrexx 30 SI
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Deepcool Matrexx 30 SI', 34.99, 'Case Micro-ATX giá rẻ với mặt kính cường lực, hỗ trợ GPU dài.', 'Micro-ATX, Mini-ITX', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 310, 155, 'ATX');

-- 34. Cooler Master N200
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master N200', 39.99, 'Case Micro-ATX cổ điển, tốt cho budget build, nhiều khe mở rộng.', 'Micro-ATX, Mini-ITX', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 355, 160, 'ATX');

-- 25. Thermaltake Versa H17
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Versa H17', 32.99, 'Ultra budget Micro-ATX case với thiết kế đơn giản, đủ chức năng.', 'Micro-ATX, Mini-ITX', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 300, 150, 'ATX');

-- 36. Corsair Spec-01
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair Spec-01', 44.99, 'Budget mid-tower với khả năng mở rộng tốt, hỗ trợ GPU dài.', 'ATX, Micro-ATX', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 380, 162, 'ATX');

-- 27. Aerocool Cylon
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Aerocool Cylon', 37.99, 'Case ATX giá rẻ với LED RGB, hỗ trợ GPU 320mm.', 'ATX, Micro-ATX', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 320, 158, 'ATX');

-- 28. Deepcool Matrexx 50
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Deepcool Matrexx 50', 42.99, 'Mid-tower với 4 mặt kính cường lực, hỗ trợ GPU 370mm.', 'ATX, Micro-ATX', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 370, 165, 'ATX');

-- 29. Cooler Master MB311L ARGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MB311L ARGB', 47.99, 'Micro-ATX với mặt lưới ARGB, thông gió tốt, hỗ trợ GPU 340mm.', 'Micro-ATX, Mini-ITX', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 340, 158, 'ATX');

-- 30. Thermaltake V200 TG
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake V200 Tempered Glass', 49.99, 'ATX mid-tower với mặt kính, hỗ trợ GPU 350mm, giá hợp lý.', 'ATX, Micro-ATX', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 350, 160, 'ATX');

-- 31. Cooler Master K501L
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master K501L', 45.99, 'Mid-tower RGB với không gian rộng rãi, hỗ trợ GPU 410mm.', 'ATX, Micro-ATX', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 410, 163, 'ATX');

-- 32. Golden Field N2
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'Golden Field N2', 29.99, 'Ultra budget Micro-ATX case nhỏ gọn, phù hợp cho văn phòng và gaming nhẹ.', 'Micro-ATX, Mini-ITX', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 16);  -- Micro-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Micro-ATX', 250, 145, 'ATX');

-- 23. Fractal Design Define 7 XL
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (5, 'Fractal Design Define 7 XL', 264.99, 'Không gian cực lớn, tối ưu cho sự im lặng và khả năng lưu trữ.', 'Full-Tower, E-ATX', 11);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 549, 185, 'ATX');

-- -----------------------------------------------------------------
-- Dòng ITX (nhỏ gọn)
-- -----------------------------------------------------------------

-- 24. Cooler Master MasterBox NR200P
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MasterBox NR200P White', 104.99, 'Vỏ case ITX phổ biến nhất, lắp đặt dễ dàng, hỗ trợ cả kính và mặt lưới.', 'Mini-ITX', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 17);  -- Mini-ITX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Mini-ITX', 330, 155, 'SFX');

-- 25. Lian Li A4-H2O
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li A4-H2O Black', 169.99, 'Thiết kế sandwich nhỏ gọn, chất liệu nhôm cao cấp, hỗ trợ AIO 240mm.', 'Mini-ITX', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 17);  -- Mini-ITX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Mini-ITX', 322, 55, 'SFX');

-- 26. NZXT H1 (V2)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT H1 (V2) Matte Black', 199.99, 'Thiết kế dạng tháp, đi kèm sẵn nguồn 750W và tản nhiệt nước AIO.', 'Mini-ITX', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 17);  -- Mini-ITX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Mini-ITX', 324, 140, 'SFX');

-- 27. Fractal Design Terra
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (5, 'Fractal Design Terra Jade', 204.99, 'Thiết kế sang trọng với nhôm và gỗ, có thể điều chỉnh không gian bên trong.', 'Mini-ITX', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 17);  -- Mini-ITX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'Mini-ITX', 322, 77, 'SFX');

-- -----------------------------------------------------------------
-- Một vài lựa chọn đa dạng khác
-- -----------------------------------------------------------------

-- 28. ASUS ROG Strix Helios
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (9, 'ASUS ROG Strix Helios White', 319.99, 'Vỏ case cao cấp cho fan ROG, kính cường lực 3 mặt, có quai xách.', 'Mid-Tower, E-ATX', 10);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 18);  -- E-ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'E-ATX', 450, 190, 'ATX');

-- 29. Thermaltake Core P3
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Core P3 TG Black', 154.99, 'Thiết kế khung mở (Open-Frame), có thể treo tường.', 'Mid-Tower, ATX', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 450, 180, 'ATX');

-- 30. Hyte Y60
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (30, 'Hyte Y60 Black', 209.99, 'Thiết kế bể cá 3 mặt kính độc đáo, tối ưu cho việc khoe linh kiện.', 'Mid-Tower, ATX', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 6),   -- Case
(@product_id, 15);  -- ATX
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES (@product_id, 'ATX', 375, 160, 'ATX');

-- 1. Golden Field 1000B (Giá: ~15$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (36, 'Golden Field 1000B', 15.00, 'Vỏ case văn phòng cơ bản, nhỏ gọn, không gian hạn chế cho các linh kiện.', 'Micro-ATX, Mini-ITX', 60);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'Micro-ATX', 200, 140, 'ATX');

-- 2. SAMA MT100 (Giá: ~18$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (37, 'SAMA MT100', 18.00, 'Case Mid-Tower giá siêu rẻ, thiết kế tối giản, phù hợp cho lắp ráp PC cơ bản.', 'Mid-Tower, ATX', 55);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 280, 150, 'ATX');

-- 3. KENOO M200 (Giá: ~20$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (38, 'KENOO M200', 20.00, 'Vỏ case Micro-ATX với mặt nạ nhựa đơn giản, không gian hạn chế.', 'Micro-ATX, Mini-ITX', 50);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'Micro-ATX', 240, 145, 'ATX');

-- 4. Xigmatek Eros (Giá: ~25$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (41, 'Xigmatek Eros', 25.00, 'Case Mid-Tower với 1 quạt LED cơ bản, thiết kế mặt trước đơn giản, tối ưu chi phí.', 'Mid-Tower, ATX', 48);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 290, 150, 'ATX');

-- 5. Vision V1 (Giá: ~22$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (39, 'Vision V1', 22.00, 'Vỏ case văn phòng với kiểu dáng truyền thống, không có kính cường lực.', 'Mid-Tower, ATX', 45);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 270, 148, 'ATX');

-- 6. Sama Falcon (Giá: ~28$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (37, 'SAMA Falcon', 28.00, 'Case Micro-ATX nhỏ gọn, có mặt lưới nhỏ phía trước, hỗ trợ tản nhiệt vừa phải.', 'Micro-ATX, Mini-ITX', 40);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'Micro-ATX', 250, 150, 'ATX');

-- 7. VSP V21 (Giá: ~29$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (42, 'VSP V21', 29.00, 'Vỏ case Mid-Tower với thiết kế đơn giản nhưng đủ không gian cho các linh kiện phổ thông.', 'Mid-Tower, ATX', 35);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 300, 155, 'ATX');

-- 8. Golden Field N1 (Giá: ~12$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (36, 'Golden Field N1', 12.00, 'Case Micro-ATX siêu nhỏ gọn, lý tưởng cho các cấu hình HTPC hoặc văn phòng cơ bản.', 'Micro-ATX, Mini-ITX', 70);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'Micro-ATX', 180, 130, 'SFX');

-- 9. Techware Neo (Giá: ~24$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (40, 'Techware Neo', 24.00, 'Vỏ case Mid-Tower cơ bản, đi kèm mặt kính acrylic bên hông, giá cả phải chăng.', 'Mid-Tower, ATX', 42);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height,  psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 290, 150, 'ATX');

-- 10. VSP KA-280 (Giá: ~19$)
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES
    (42, 'VSP KA-280', 19.00, 'Case Mid-Tower đơn giản, phù hợp cho các cấu hình PC không yêu cầu cao về thẩm mỹ và tản nhiệt.', 'Mid-Tower, ATX', 48);
INSERT INTO pc_case (product_id, form_factor, gpu_max_length, cpu_max_cooler_height, psu_form_factor)
VALUES
    (LAST_INSERT_ID(), 'ATX', 270, 145, 'ATX');
-- =================================================================
-- SCRIPT INSERT 30 SẢN PHẨM NGUỒN (POWER SUPPLY) - GIÁ ĐÃ SỬA SANG USD
-- =================================================================

-- -----------------------------------------------------------------
-- Phân khúc 450W - 650W (Văn phòng, Gaming phổ thông)
-- -----------------------------------------------------------------

-- 1. Corsair CV550
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair CV550 550W 80+ Bronze', 52.99, 'Nguồn 550W chứng nhận 80+ Bronze, ổn định cho các cấu hình phổ thông.', '550W, 80+ Bronze, Non-Modular', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 550, '80+ Bronze', 0, 'ATX');

-- 2. Cooler Master MWE Bronze V2 650W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MWE Bronze V2 650W', 62.99, 'Nguồn 650W hiệu năng tốt, quạt HDB êm ái.', '650W, 80+ Bronze, Non-Modular', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 650, '80+ Bronze', 0, 'ATX');

-- 3. Seasonic S12III 550W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (26, 'Seasonic S12III 550W 80+ Bronze', 57.99, 'Nguồn từ thương hiệu Seasonic danh tiếng, bền bỉ.', '550W, 80+ Bronze, Non-Modular', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 550, '80+ Bronze', 0, 'ATX');

-- 4. Thermaltake Smart BX1 650W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Smart BX1 650W 80+ Bronze', 59.99, 'Nguồn 650W với quạt 120mm Hydraulic Bearing.', '650W, 80+ Bronze, Non-Modular', 36);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 650, '80+ Bronze', 0, 'ATX');

-- 5. EVGA 600 W1
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (27, 'EVGA 600 W1 600W 80+', 49.99, 'Nguồn 600W giá rẻ, chứng nhận 80+ White.', '600W, 80+ White, Non-Modular', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 600, '80+ White', 0, 'ATX');

-- 6. be quiet! System Power 9 500W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (12, 'be quiet! System Power 9 500W 80+ Bronze', 54.99, 'Nguồn 500W hoạt động cực kỳ yên tĩnh.', '500W, 80+ Bronze, Non-Modular', 39);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 500, '80+ Bronze', 0, 'ATX');

-- 7. Corsair CX650M
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair CX650M 650W 80+ Bronze', 74.99, 'Nguồn semi-modular, giúp đi dây gọn gàng hơn.', '650W, 80+ Bronze, Semi-Modular', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 650, '80+ Bronze', 1, 'ATX');

-- -----------------------------------------------------------------
-- Phân khúc 750W - 850W (Gaming cao cấp)
-- -----------------------------------------------------------------

-- 8. Corsair RM750e
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair RM750e 750W 80+ Gold', 119.99, 'Nguồn 750W 80+ Gold, full modular, tụ điện Nhật Bản.', '750W, 80+ Gold, Full-Modular', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 750, '80+ Gold', 1, 'ATX');

-- 9. Seasonic Focus Plus Gold 850W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (26, 'Seasonic Focus Plus Gold FX-850', 134.99, 'Nguồn 850W 80+ Gold, full modular, chất lượng hàng đầu.', '850W, 80+ Gold, Full-Modular', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 10. Cooler Master MWE Gold V2 850W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MWE Gold V2 850W', 124.99, 'Nguồn 850W 80+ Gold, full modular, 2 đầu cắm EPS cho CPU.', '850W, 80+ Gold, Full-Modular', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 11. ASUS ROG Strix 850G
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (9, 'ASUS ROG Strix 850G 850W 80+ Gold', 154.99, 'Nguồn 850W cho fan ROG, tản nhiệt tích hợp, full modular.', '850W, 80+ Gold, Full-Modular', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 12. Thermaltake Toughpower GF1 750W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Toughpower GF1 750W 80+ Gold', 109.99, 'Nguồn full modular với quạt Zero RPM thông minh.', '750W, 80+ Gold, Full-Modular', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 750, '80+ Gold', 1, 'ATX');

-- 13. EVGA SuperNOVA 850 G6
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (27, 'EVGA SuperNOVA 850 G6 850W 80+ Gold', 139.99, 'Nguồn 850W từ EVGA, chất lượng được khẳng định.', '850W, 80+ Gold, Full-Modular', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 14. be quiet! Pure Power 11 FM 750W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (12, 'be quiet! Pure Power 11 FM 750W 80+ Gold', 119.99, 'Full modular, hoạt động êm ái, hiệu suất cao.', '750W, 80+ Gold, Full-Modular', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 750, '80+ Gold', 1, 'ATX');

-- 15. Corsair RM850x SHIFT
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair RM850x SHIFT 850W 80+ Gold', 164.99, 'Thiết kế độc đáo với cổng cắm bên hông, dễ đi dây.', '850W, 80+ Gold, Full-Modular', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 16. NZXT C850 Gold
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT C850 Gold V2 850W', 129.99, 'Nguồn 850W full modular, thiết kế tối giản.', '850W, 80+ Gold, Full-Modular', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- -----------------------------------------------------------------
-- Phân khúc trên 1000W (Workstation, Enthusiast)
-- -----------------------------------------------------------------

-- 17. Corsair RM1000e
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair RM1000e 1000W 80+ Gold', 179.99, 'Nguồn 1000W 80+ Gold, hỗ trợ ATX 3.0 và PCIe 5.0.', '1000W, 80+ Gold, Full-Modular', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1000, '80+ Gold', 1, 'ATX');

-- 18. Seasonic PRIME TX-1000
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (26, 'Seasonic PRIME TX-1000 1000W 80+ Titanium', 319.99, 'Hiệu suất đỉnh cao 80+ Titanium, chất lượng tốt nhất.', '1000W, 80+ Titanium, Full-Modular', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1000, '80+ Titanium', 1, 'ATX');

-- 19. Cooler Master V1300 Platinum
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master V1300 Platinum 1300W', 299.99, 'Nguồn 1300W hiệu suất Platinum, cho các cấu hình khủng.', '1300W, 80+ Platinum, Full-Modular', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1300, '80+ Platinum', 1, 'ATX');

-- 20. ASUS ROG Thor 1200P2
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (9, 'ASUS ROG Thor 1200P2 1200W 80+ Platinum', 379.99, 'Nguồn 1200W có màn hình OLED hiển thị công suất.', '1200W, 80+ Platinum, Full-Modular', 10);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1200, '80+ Platinum', 1, 'ATX');

-- 21. EVGA SuperNOVA 1600 P+
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (27, 'EVGA SuperNOVA 1600 P+ 1600W 80+ Platinum', 399.99, 'Nguồn 1600W cho các hệ thống đào coin hoặc workstation đa GPU.', '1600W, 80+ Platinum, Full-Modular', 8);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1600, '80+ Platinum', 1, 'ATX');

-- 22. be quiet! Dark Power Pro 12 1500W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (12, 'be quiet! Dark Power Pro 12 1500W 80+ Titanium', 469.99, 'Nguồn 1500W 80+ Titanium, vỏ nhôm, quạt không viền siêu tĩnh.', '1500W, 80+ Titanium, Full-Modular', 6);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1500, '80+ Titanium', 1, 'ATX');

-- 23. Thermaltake Toughpower PF1 1200W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (8, 'Thermaltake Toughpower PF1 1200W 80+ Platinum', 249.99, 'Nguồn 1200W Platinum với quạt Riing Duo RGB.', '1200W, 80+ Platinum, Full-Modular', 16);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1200, '80+ Platinum', 1, 'ATX');

-- -----------------------------------------------------------------
-- Dòng SFX (Cho case ITX nhỏ gọn)
-- -----------------------------------------------------------------

-- 24. Corsair SF750
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair SF750 750W 80+ Platinum', 174.99, 'Nguồn SFX 750W tốt nhất thị trường, hiệu suất Platinum.', '750W, SFX, 80+ Platinum, Full-Modular', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 28);  -- SFX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 750, '80+ Platinum', 1, 'SFX');

-- 25. Cooler Master V850 SFX Gold
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master V850 SFX Gold 850W', 149.99, 'Nguồn SFX công suất cao 850W, 80+ Gold.', '850W, SFX, 80+ Gold, Full-Modular', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 28);  -- SFX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'SFX');

-- 26. Lian Li SP850
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li SP850 850W 80+ Gold White', 149.99, 'Nguồn SFX 850W màu trắng, dây cáp bọc dù.', '850W, SFX, 80+ Gold, Full-Modular', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 28);  -- SFX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'SFX');

-- -----------------------------------------------------------------
-- Các lựa chọn đa dạng khác
-- -----------------------------------------------------------------

-- 27. Deepcool PQ1000M
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (10, 'Deepcool PQ1000M 1000W 80+ Gold', 169.99, 'Nguồn 1000W từ Deepcool, dựa trên nền tảng của Seasonic.', '1000W, 80+ Gold, Full-Modular', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1000, '80+ Gold', 1, 'ATX');

-- 28. FSP Hydro G Pro 1000W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (28, 'FSP Hydro G Pro 1000W 80+ Gold', 174.99, 'Nguồn 1000W từ FSP, bền bỉ và ổn định.', '1000W, 80+ Gold, Full-Modular', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 1000, '80+ Gold', 1, 'ATX');

-- 29. MSI MPG A850G PCIE5
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'MSI MPG A850G 850W 80+ Gold PCIE5', 149.99, 'Nguồn 850W sẵn sàng cho chuẩn PCIe 5.0 (cáp 12VHP).', '850W, 80+ Gold, Full-Modular', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 850, '80+ Gold', 1, 'ATX');

-- 30. Antec NeoECO Gold Zen 700W
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (29, 'Antec NeoECO Gold Zen 700W', 89.99, 'Nguồn 700W 80+ Gold, non-modular giá tốt.', '700W, 80+ Gold, Non-Modular', 34);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 7),   -- Power Supply
(@product_id, 27);  -- ATX PSU
INSERT INTO power_supply (product_id, wattage, efficiency, modular, form_factor)
VALUES (@product_id, 700, '80+ Gold', 0, 'ATX');

-- =================================================================
-- SCRIPT INSERT 20 SẢN PHẨM TẢN NHIỆT (COOLING) - GIÁ ĐÃ SỬA SANG USD
-- =================================================================

-- -----------------------------------------------------------------
-- Tản nhiệt khí (Air Coolers)
-- -----------------------------------------------------------------

-- 1. Cooler Master Hyper 212 Spectrum V3
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master Hyper 212 Spectrum V3', 31.99, 'Tản nhiệt quốc dân, hiệu năng tốt trong tầm giá.', 'Tản khí, 4 ống đồng, quạt ARGB', 45);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 180, 120, 0);

-- 2. Noctua NH-D15
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (7, 'Noctua NH-D15 chromax.black', 119.99, 'Vua tản nhiệt khí, hiệu năng đỉnh cao và cực kỳ yên tĩnh.', 'Tản khí, tháp đôi, 2 quạt 140mm', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 250, 140, 0);

-- 3. Deepcool AK400
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (10, 'Deepcool AK400 Performance', 27.99, 'Hiệu năng/giá thành xuất sắc, thiết kế gọn gàng.', 'Tản khí, 4 ống đồng, quạt 120mm FDB', 38);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 220, 120, 0);

-- 4. Thermalright Phantom Spirit 120 SE ARGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (11, 'Thermalright Phantom Spirit 120 SE ARGB', 49.99, 'Hiệu năng cực mạnh với 7 ống đồng và 2 quạt.', 'Tản khí, tháp đôi, 2 quạt 120mm', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 280, 120, 0);

-- 5. Deepcool AK620
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (10, 'Deepcool AK620 Black', 64.99, 'Tản nhiệt tháp đôi hiệu năng cao, cạnh tranh trực tiếp với các tản cao cấp.', 'Tản khí, 6 ống đồng, 2 quạt 120mm', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 260, 120, 0);

-- 6. Noctua NH-U12A
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (7, 'Noctua NH-U12A', 115.99, 'Hiệu năng của tản 140mm trong hình hài 120mm, không cấn RAM.', 'Tản khí, 7 ống đồng, 2 quạt NF-A12x25', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 220, 120, 0);

-- 7. Cooler Master MasterAir MA624 Stealth
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MasterAir MA624 Stealth', 109.99, 'Thiết kế đen tuyền, hiệu năng cao, đi kèm cả quạt 120mm và 140mm.', 'Tản khí, tháp đôi, 6 ống đồng', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 250, 140, 0);

-- 8. Noctua NH-L9i-17xx
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (7, 'Noctua NH-L9i-17xx', 56.99, 'Tản nhiệt low-profile cho các case ITX siêu nhỏ gọn, socket LGA1700.', 'Tản khí low-profile', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 95, 92, 0);

-- 9. Thermalright Assassin X 120 Refined SE
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (11, 'Thermalright Assassin X 120 Refined SE', 20.99, 'Tản nhiệt khí giá rẻ cho các CPU tầm trung.', 'Tản khí, 4 ống đồng', 42);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 180, 120, 0);

-- 10. be quiet! Dark Rock Pro 4
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (12, 'be quiet! Dark Rock Pro 4', 99.99, 'Hiệu năng đỉnh cao đi kèm với sự tĩnh lặng tuyệt đối.', 'Tản khí, tháp đôi, quạt Silent Wings', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 29);  -- Air Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'Air', 250, 135, 0);


-- -----------------------------------------------------------------
-- Tản nhiệt nước AIO (All-In-One Liquid Coolers)
-- -----------------------------------------------------------------

-- 11. Cooler Master MasterLiquid 240L Core ARGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (1, 'Cooler Master MasterLiquid 240L Core ARGB', 79.99, 'AIO 240mm giá cả phải chăng, pump Gen S mới.', 'AIO, Radiator 240mm, 2 quạt ARGB', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 250, 120, 240);

-- 12. Corsair H150i Elite Capellix XT
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair H150i Elite Capellix XT White', 209.99, 'AIO 360mm cao cấp, hiệu năng mạnh mẽ, LED Capellix rực rỡ.', 'AIO, Radiator 360mm, 3 quạt AF RGB ELITE', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 350, 120, 360);

-- 13. NZXT Kraken 240 RGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT Kraken 240 RGB Black', 149.99, 'Thiết kế mặt pump LCD độc đáo, hiển thị thông số hoặc ảnh GIF.', 'AIO, Radiator 240mm, màn hình LCD', 22);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 280, 120, 240);

-- 14. Lian Li Galahad II Trinity 360
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (4, 'Lian Li Galahad II Trinity 360 ARGB', 159.99, 'Hiệu năng cao, có 3 nắp pump thay thế, quạt dễ dàng nối tiếp.', 'AIO, Radiator 360mm, 3 quạt ARGB', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 320, 120, 360);

-- 15. Deepcool LT720
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (10, 'Deepcool LT720 WH 360mm', 134.99, 'Thiết kế pump 3D vô cực, hiệu năng hàng đầu.', 'AIO, Radiator 360mm, Pump thế hệ 4', 24);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 300, 120, 360);

-- 16. Arctic Liquid Freezer II 280
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (13, 'Arctic Liquid Freezer II 280', 129.99, 'Hiệu năng vượt trội với radiator dày và quạt VRM trên pump.', 'AIO, Radiator 280mm, 2 quạt 140mm', 26);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 300, 140, 280);

-- 17. Corsair H100i RGB PRO XT
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (2, 'Corsair H100i RGB PRO XT', 124.99, 'AIO 240mm hiệu năng cao, điều khiển qua iCUE.', 'AIO, Radiator 240mm, 2 quạt ML', 28);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 250, 120, 240);

-- 18. ASUS ROG RYUJIN III 360 ARGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (9, 'ASUS ROG RYUJIN III 360 ARGB', 379.99, 'Tản nhiệt AIO đầu bảng với màn hình LCD lớn và pump Asetek gen 8.', 'AIO, Radiator 360mm, màn hình LCD 3.5"', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 400, 120, 360);

-- 19. NZXT Kraken Elite 360 RGB
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (3, 'NZXT Kraken Elite 360 RGB White', 299.99, 'Phiên bản cao cấp với màn hình LCD độ phân giải cao hơn.', 'AIO, Radiator 360mm, màn hình LCD Elite', 14);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO Cooling
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 350, 120, 360);

-- 20. Thermalright Frozen Notte 360
INSERT INTO product (brand_id, product_name, price, description, specification, inventory_quantity)
VALUES (11, 'Thermalright Frozen Notte 360 Black ARGB', 89.99, 'AIO 360mm hiệu năng tốt với mức giá cực kỳ cạnh tranh.', 'AIO, Radiator 360mm, 3 quạt ARGB', 32);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES
(@product_id, 8),   -- Cooling
(@product_id, 30);  -- AIO
INSERT INTO cooling (product_id, type, max_tdp, fan_size, radiator_size)
VALUES (@product_id, 'AIO', 300, 120, 360);

--Insert Other Product
INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (6, 'Logitech K120 Office Keyboard', 12.99, 1, 'Bàn phím văn phòng đơn giản, bền, chống nước.', 'Membrane, USB', 50);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (7, 'Corsair K55 RGB Pro', 49.99, 1, 'Bàn phím gaming RGB nhiều chế độ.', 'Membrane, RGB, USB', 30);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (20, 'Razer BlackWidow V3 Mechanical', 129.99, 1, 'Bàn phím cơ switch xanh nổi tiếng.', 'Mechanical, RGB, USB', 25);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (6, 'Logitech G102 LightSync', 19.99, 1, 'Chuột gaming giá rẻ, cảm biến chính xác.', '8000 DPI, RGB', 40);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (20, 'Razer DeathAdder Essential', 29.99, 1, 'Chuột gaming huyền thoại, thiết kế công thái học.', '6400 DPI, USB', 35);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS ROG Gladius III', 74.99, 1, 'Chuột gaming cao cấp, switch hot-swap.', '26000 DPI, RGB', 20);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (1, 'ASUS VZ249HE 24-inch IPS', 129.99, 1, 'Màn hình IPS siêu mỏng, 75Hz.', '24 inch, IPS, 75Hz', 18);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (2, 'MSI Optix G241 144Hz', 189.99, 1, 'Màn hình gaming 144Hz, màu đẹp.', '24 inch, IPS, 144Hz', 15);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

INSERT INTO product (brand_id, product_name, price, status, description, specification, inventory_quantity)
VALUES (3, 'Gigabyte G27F2 165Hz', 229.99, 1, 'Màn hình 165Hz dành cho game thủ.', '27 inch, IPS, 165Hz', 12);
SET @product_id = LAST_INSERT_ID();
INSERT INTO product_category (product_id, category_id)
VALUES (@product_id, 15);

-- ============================================
-- Create Indexes for Performance
-- ============================================

-- Index for faster filtering by score
CREATE INDEX idx_product_score ON product(performance_score);

-- Composite index for price + score queries (common in AI filtering)
CREATE INDEX idx_product_price_score ON product(price, performance_score);


-- ============================================
-- Sample Score Updates (Examples)
-- ============================================

-- CPU Examples
UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 100
WHERE p.product_name LIKE '%i9-14900K%' OR p.product_name LIKE '%i9-14900KS%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%i9-13900K%' OR p.product_name LIKE '%Ryzen 9 7950X%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 90
WHERE p.product_name LIKE '%i7-14700K%' OR p.product_name LIKE '%Ryzen 9 7900X%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%i7-13700K%' OR p.product_name LIKE '%Ryzen 7 7700X%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 80
WHERE p.product_name LIKE '%i5-14600K%' OR p.product_name LIKE '%Ryzen 7 5800X%' OR p.product_name LIKE '%Ryzen 7 7800X3D%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%i5-13600K%' OR p.product_name LIKE '%Ryzen 5 7600X%' OR p.product_name LIKE '%Ryzen 5 7600%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%i5-12600K%' OR p.product_name LIKE '%Ryzen 5 5600X%' OR p.product_name LIKE '%Ryzen 5 7500F%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%i5-12400%' OR p.product_name LIKE '%Ryzen 5 3600%' OR p.product_name LIKE '%Ryzen 5 5500%' OR p.product_name LIKE '%Ryzen 5 4600G%';

UPDATE product p
    JOIN cpu c ON p.product_id = c.product_id
    SET p.performance_score = 50
WHERE p.product_name LIKE '%i3-%' OR p.product_name LIKE '%Ryzen 3%';

-- GPU Examples
UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 100
WHERE p.product_name LIKE '%RTX 4090%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%RTX 4080%' OR p.product_name LIKE '%RX 7900 XTX%' OR p.product_name LIKE '%RX 6950 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 90
WHERE p.product_name LIKE '%RTX 4070 Ti%' OR p.product_name LIKE '%RX 7900 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%RTX 4070%' OR p.product_name LIKE '%RX 7800 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 80
WHERE p.product_name LIKE '%RTX 3090%' OR p.product_name LIKE '%RX 6900 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%RTX 4060 Ti%' OR p.product_name LIKE '%RTX 3080%' OR p.product_name LIKE '%RX 7700 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%RTX 3070%' OR p.product_name LIKE '%RX 6800%' OR p.product_name LIKE '%RX 6700 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%RTX 4060%' OR p.product_name LIKE '%RX 7600%' OR p.product_name LIKE '%RX 6600 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%RTX 3060%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 50
WHERE p.product_name LIKE '%RTX 3050%' OR p.product_name LIKE '%GTX 1660%' OR p.product_name LIKE '%RX 6500 XT%' OR p.product_name LIKE '%RX 6400%' OR p.product_name LIKE '%RX 5600 XT%';

UPDATE product p
    JOIN gpu g ON p.product_id = g.product_id
    SET p.performance_score = 40
WHERE p.product_name LIKE '%GTX 1650%' OR p.product_name LIKE '%RX 5500 XT%';

-- Memory Examples (based on capacity and speed)
UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%64GB%' AND p.product_name LIKE '%6000%';

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 90
WHERE p.product_name LIKE '%32GB%' AND p.product_name LIKE '%6000%';

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%32GB%' AND (p.product_name LIKE '%5200%' OR p.product_name LIKE '%5600%');

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 80
WHERE p.product_name LIKE '%32GB%' AND p.product_name LIKE '%4800%';

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%16GB%' AND (p.product_name LIKE '%6000%' OR p.product_name LIKE '%5600%');

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%16GB%' AND p.product_name LIKE '%3600%';

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%16GB%' AND p.product_name LIKE '%3200%';

UPDATE product p
    JOIN memory m ON p.product_id = m.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%8GB%' AND p.product_name LIKE '%3200%';

-- Storage Examples
UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%2TB%' AND (p.product_name LIKE '%7000%' OR p.product_name LIKE '%7400%');

UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%1TB%' AND (p.product_name LIKE '%5000%' OR p.product_name LIKE '%7000%');

UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%1TB%' AND p.product_name LIKE '%3500%';

UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%SSD%' AND p.product_name LIKE '%1TB%';

UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 55
WHERE p.product_name LIKE '%SSD%' AND p.product_name LIKE '%500GB%';

UPDATE product p
    JOIN storage s ON p.product_id = s.product_id
    SET p.performance_score = 45
WHERE p.product_name LIKE '%HDD%' AND p.product_name LIKE '%2TB%';

-- PSU Examples
UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%1000W%' AND p.product_name LIKE '%Platinum%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 90
WHERE p.product_name LIKE '%1000W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%850W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 80
WHERE p.product_name LIKE '%750W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%750W%' AND p.product_name LIKE '%Bronze%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%650W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%650W%' AND p.product_name LIKE '%Bronze%';

UPDATE product p
    JOIN power_supply ps ON p.product_id = ps.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%550W%';

-- Case Examples (simpler, based on size)
UPDATE product p
    JOIN pc_case pc ON p.product_id = pc.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%Full Tower%';

UPDATE product p
    JOIN pc_case pc ON p.product_id = pc.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%Mid Tower%' OR p.product_name LIKE '%ATX%';

UPDATE product p
    JOIN pc_case pc ON p.product_id = pc.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%Micro%';

UPDATE product p
    JOIN pc_case pc ON p.product_id = pc.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%Mini%' OR p.product_name LIKE '%ITX%';

-- Cooling Examples
UPDATE product p
    JOIN cooling co ON p.product_id = co.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%360%' OR p.product_name LIKE '%420%';

UPDATE product p
    JOIN cooling co ON p.product_id = co.product_id
    SET p.performance_score = 85
WHERE p.product_name LIKE '%280%';

UPDATE product p
    JOIN cooling co ON p.product_id = co.product_id
    SET p.performance_score = 75
WHERE p.product_name LIKE '%240%';

UPDATE product p
    JOIN cooling co ON p.product_id = co.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%120%' OR p.product_name LIKE '%140%';

UPDATE product p
    JOIN cooling co ON p.product_id = co.product_id
    SET p.performance_score = 65
WHERE p.product_name LIKE '%Air%' OR p.product_name LIKE '%Tower%';

-- Mainboard Examples (based on chipset tier)
UPDATE product p
    JOIN mainboard mb ON p.product_id = mb.product_id
    SET p.performance_score = 95
WHERE p.product_name LIKE '%X670E%' OR p.product_name LIKE '%Z790%';

UPDATE product p
    JOIN mainboard mb ON p.product_id = mb.product_id
    SET p.performance_score = 90
WHERE p.product_name LIKE '%B650E%' OR p.product_name LIKE '%Z690%' OR p.product_name LIKE '%X570%';

UPDATE product p
    JOIN mainboard mb ON p.product_id = mb.product_id
    SET p.performance_score = 80
WHERE p.product_name LIKE '%B550%' OR p.product_name LIKE '%B760%';

UPDATE product p
    JOIN mainboard mb ON p.product_id = mb.product_id
    SET p.performance_score = 70
WHERE p.product_name LIKE '%B450%' OR p.product_name LIKE '%H610%';

UPDATE product p
    JOIN mainboard mb ON p.product_id = mb.product_id
    SET p.performance_score = 60
WHERE p.product_name LIKE '%A620%' OR p.product_name LIKE '%H510%';




















