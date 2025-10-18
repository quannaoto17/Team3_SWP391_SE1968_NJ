USE pconlineshop;

-- Category
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
('ASRock', 'ASRock - Mainboard chất lượng', 'https://www.asrock.com');

-- ==============================================
-- MAINBOARD PRODUCTS (30 sản phẩm - category_id = 1)
-- ==============================================

-- Mainboard 1-10
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS PRIME B550M-A', 150.00, 1, 'Micro ATX AM4 mainboard', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'Micro-ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI MPG X670E CARBON', 390.00, 1, 'High-end AM5 mainboard', 'Chipset X670E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE B760M DS3H DDR4', 140.00, 1, 'Intel LGA1700 mid-range board', 'Chipset B760');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'B760', 'Micro-ATX', 'DDR4', 4, 5333, '5.0', 2, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock A620M-HDV/M.2+', 100.00, 1, 'Budget AM5 mainboard', 'Chipset A620');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'A620', 'Micro-ATX', 'DDR5', 2, 6400, '4.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS ROG STRIX Z790-E GAMING', 420.00, 1, 'Top-tier Intel board', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI PRO H610M-E DDR4', 110.00, 1, 'Entry-level LGA1700', 'Chipset H610');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE X570 AORUS ELITE', 200.00, 1, 'Mid-range AM4 board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS TUF GAMING B550-PLUS', 170.00, 1, 'Durable AM4 board', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'ATX', 'DDR4', 4, 4800, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock H510M-HDV', 90.00, 1, 'Intel entry board', 'Chipset H510');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1200', 'H510', 'Micro-ATX', 'DDR4', 2, 3200, '3.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI B450 TOMAHAWK MAX II', 120.00, 1, 'Popular AM4 board', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'ATX', 'DDR4', 4, 4133, '3.0', 1, 6);

-- Mainboard 11-20
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE B650 AORUS ELITE', 260.00, 1, 'AM5 PCIe5 DDR5', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS PRIME H610M-K D4', 110.00, 1, 'Intel H610 DDR4 board', 'Chipset H610');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'H610', 'Micro-ATX', 'DDR4', 2, 4800, '4.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock Z690 PG RIPTIDE', 250.00, 1, 'Z690 chipset DDR5 board', 'Chipset Z690');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z690', 'ATX', 'DDR5', 4, 6400, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI MAG B550 TOMAHAWK', 180.00, 1, 'AM4 gaming board', 'Chipset B550');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B550', 'ATX', 'DDR4', 4, 4733, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE Z790 AORUS MASTER', 480.00, 1, 'Z790 flagship', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'ATX', 'DDR5', 4, 7800, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS ROG CROSSHAIR X670E HERO', 490.00, 1, 'AM5 Enthusiast board', 'Chipset X670E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'X670E', 'ATX', 'DDR5', 4, 8000, '5.0', 4, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI MPG B650M EDGE WIFI', 240.00, 1, 'AM5 DDR5 compact', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'Micro-ATX', 'DDR5', 4, 7000, '5.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock X570 PHANTOM GAMING 4', 180.00, 1, 'AM4 solid board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE A520M S2H', 90.00, 1, 'Entry AM4 mainboard', 'Chipset A520');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'A520', 'Micro-ATX', 'DDR4', 2, 4400, '3.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS PRIME B450M-K II', 100.00, 1, 'Budget AM4', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

-- Mainboard 21-30
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI Z590 PRO WIFI', 260.00, 1, 'Intel 11th Gen Z590', 'Chipset Z590');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1200', 'Z590', 'ATX', 'DDR4', 4, 5333, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE B365M DS3H', 110.00, 1, 'Older Intel 9th Gen', 'Chipset B365');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1151', 'B365', 'Micro-ATX', 'DDR4', 4, 2666, '3.0', 1, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock B450M PRO4', 110.00, 1, 'AM4 classic', 'Chipset B450');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B450', 'Micro-ATX', 'DDR4', 4, 4000, '3.0', 1, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS PRIME X570-P', 190.00, 1, 'AM4 PCIe 4.0 board', 'Chipset X570');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'X570', 'ATX', 'DDR4', 4, 5100, '4.0', 2, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI B650 TOMAHAWK WIFI', 250.00, 1, 'AM5 DDR5 mid-range', 'Chipset B650');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'B650', 'ATX', 'DDR5', 4, 7200, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE X399 AORUS XTREME', 450.00, 1, 'TR4 workstation board', 'Chipset X399');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'TR4', 'X399', 'E-ATX', 'DDR4', 8, 3600, '3.0', 3, 8);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 7, 'ASRock Z790M-ITX WIFI', 280.00, 1, 'Compact Intel Z790', 'Chipset Z790');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'LGA1700', 'Z790', 'Mini-ITX', 'DDR5', 2, 7800, '5.0', 2, 4);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 1, 'ASUS ROG STRIX B650E-F GAMING WIFI', 300.00, 1, 'AM5 DDR5 gaming', 'Chipset B650E');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'B650E', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 2, 'MSI PRO X670-P WIFI', 270.00, 1, 'AM5 PCIe5 DDR5', 'Chipset X670');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM5', 'X670', 'ATX', 'DDR5', 4, 7600, '5.0', 3, 6);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (1, 3, 'GIGABYTE B550I AORUS PRO AX', 220.00, 1, 'Mini ITX AM4 board with Wi-Fi', 'Chipset B550I');
INSERT INTO mainboard (product_id, socket, chipset, form_factor, memory_type, memory_slots, max_memory_speed, pcie_version, m2_slots, sata_ports) VALUES (LAST_INSERT_ID(), 'AM4', 'B550I', 'Mini-ITX', 'DDR4', 2, 4733, '4.0', 2, 4);

-- ==============================================
-- CPU PRODUCTS (30 sản phẩm - category_id = 2)
-- ==============================================

-- CPU 31-40
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 5 5600X', 220.00, 1, '6 cores 12 threads', 'Base 3.7GHz Boost 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '4.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i5-12400F', 180.00, 1, '6 Performance cores', 'Base 2.5GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i7-13700K', 420.00, 1, '16 cores hybrid', 'Base 3.4GHz Boost 5.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 9 7950X', 590.00, 1, '16 cores 32 threads', 'Base 4.5GHz Boost 5.7GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 170, 5200, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 7 5800X3D', 370.00, 1, '8 cores 3D cache', 'Base 3.4GHz Boost 4.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i9-13900K', 600.00, 1, '24 cores hybrid', 'Base 3.0GHz Boost 5.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 5 7600', 240.00, 1, '6 cores AM5', 'Base 3.8GHz Boost 5.1GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i3-12100F', 120.00, 1, '4 cores 8 threads', 'Base 3.3GHz Boost 4.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 60, 4800, 2, '5.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 7 7700', 340.00, 1, '8 cores AM5', 'Base 3.8GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i5-13600K', 320.00, 1, '14 cores hybrid', 'Base 3.5GHz Boost 5.1GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

-- CPU 41-50
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 9 5950X', 550.00, 1, '16 cores 32 threads', 'Base 3.4GHz Boost 4.9GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i9-12900KS', 580.00, 1, '16 cores 24 threads', 'Base 3.4GHz Boost 5.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 3 4100', 90.00, 1, '4 cores entry level', 'Base 3.8GHz Boost 4.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i5-14600K', 360.00, 1, '14 cores Raptor Lake Refresh', 'Base 3.5GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 5 5500', 130.00, 1, '6 cores AM4', 'Base 3.6GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 9 7900X', 470.00, 1, '12 cores 24 threads AM5', 'Base 4.7GHz Boost 5.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 170, 5200, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 7 7800X3D', 450.00, 1, '8 cores 3D V-Cache AM5', 'Base 4.2GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 120, 5200, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i9-14900K', 620.00, 1, '24 cores Raptor Lake Refresh', 'Base 3.2GHz Boost 6.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 125, 5600, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i7-12700F', 310.00, 1, '12 cores hybrid no iGPU', 'Base 2.1GHz Boost 4.9GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i5-12400', 190.00, 1, '6 cores 12 threads', 'Base 2.5GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 65, 4800, 2, '5.0', 1);

-- CPU 51-60
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i3-13100', 140.00, 1, '4 cores 8 threads 13th Gen', 'Base 3.4GHz Boost 4.5GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1700', 60, 4800, 2, '5.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 5 4600G', 150.00, 1, '6 cores APU with Vega GPU', 'Base 3.7GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 7 5700G', 240.00, 1, '8 cores APU', 'Base 3.8GHz Boost 4.6GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 3 5300G', 120.00, 1, '4 cores APU entry', 'Base 4.0GHz Boost 4.2GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 3200, 2, '3.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i5-11400F', 160.00, 1, '6 cores 11th Gen', 'Base 2.6GHz Boost 4.4GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1200', 65, 3200, 2, '4.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i7-11700K', 330.00, 1, '8 cores 16 threads', 'Base 3.6GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1200', 125, 3200, 2, '4.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 9 5900X', 420.00, 1, '12 cores AM4', 'Base 3.7GHz Boost 4.8GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 105, 3200, 2, '4.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 5 7500F', 210.00, 1, '6 cores AM5 no iGPU', 'Base 3.7GHz Boost 5.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM5', 65, 5200, 2, '5.0', 0);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 4, 'Intel Core i9-11900K', 400.00, 1, '8 cores high-end', 'Base 3.5GHz Boost 5.3GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'LGA1200', 125, 3200, 2, '4.0', 1);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (2, 5, 'AMD Ryzen 3 3200G', 90.00, 1, '4 cores Vega 8 APU', 'Base 3.6GHz Boost 4.0GHz');
INSERT INTO cpu (product_id, socket, tdp, max_memory_speed, memory_channels, pcie_version, has_igpu) VALUES (LAST_INSERT_ID(), 'AM4', 65, 2933, 2, '3.0', 1);

-- ==============================================
-- GPU PRODUCTS (30 sản phẩm - category_id = 3)
-- ==============================================

-- GPU 61-70
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 3060', 330.00, 1, '12GB GDDR6 mid-range GPU', 'Ampere architecture');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 170, 'PCIe x16', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6600 XT', 320.00, 1, '8GB GDDR6', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 230);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4070', 550.00, 1, '12GB GDDR6X', 'Ada Lovelace GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 200, 'PCIe x16', '4.0', 244);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 7900 XT', 880.00, 1, '20GB GDDR6', 'RDNA 3 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 20, 'GDDR6', 300, 'PCIe x16', '4.0', 276);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4080', 1200.00, 1, '16GB GDDR6X high-end GPU', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 16, 'GDDR6X', 320, 'PCIe x16', '4.0', 304);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6800', 580.00, 1, '16GB GDDR6', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 250, 'PCIe x16', '4.0', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 3050', 250.00, 1, '8GB GDDR6 entry GPU', 'Ampere');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 130, 'PCIe x8', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6500 XT', 180.00, 1, '4GB GDDR6', 'Entry RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 107, 'PCIe x4', '4.0', 190);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4070 Ti', 790.00, 1, '12GB GDDR6X high-end', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 285, 'PCIe x16', '4.0', 285);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 7800 XT', 500.00, 1, '16GB GDDR6', 'RDNA 3 mid-high');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 263, 'PCIe x16', '4.0', 267);

-- GPU 71-80
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4060', 310.00, 1, '8GB GDDR6', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 115, 'PCIe x8', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6700 XT', 370.00, 1, '12GB GDDR6 mid-range', 'RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 230, 'PCIe x16', '4.0', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce GTX 1660 SUPER', 220.00, 1, '6GB GDDR6', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 125, 'PCIe x16', '3.0', 229);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 5600 XT', 280.00, 1, '6GB GDDR6', 'RDNA 1 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 150, 'PCIe x16', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 2080 SUPER', 600.00, 1, '8GB GDDR6 high-end', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 250, 'PCIe x16', '3.0', 285);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4090', 1600.00, 1, '24GB GDDR6X flagship', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 450, 'PCIe x16', '4.0', 336);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 7600', 270.00, 1, '8GB GDDR6', 'RDNA 3 entry');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 165, 'PCIe x8', '4.0', 240);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4060 Ti', 420.00, 1, '8GB GDDR6 mid-high', 'Ada Lovelace');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6950 XT', 700.00, 1, '16GB GDDR6 high-end', 'RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 335, 'PCIe x16', '4.0', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 3070', 500.00, 1, '8GB GDDR6', 'Ampere');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 220, 'PCIe x16', '4.0', 242);

-- GPU 81-90
INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 3090', 1400.00, 1, '24GB GDDR6X high-end GPU', 'Ampere architecture');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 24, 'GDDR6X', 350, 'PCIe x16', '4.0', 336);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4070 SUPER', 620.00, 1, '12GB GDDR6X refreshed Ada GPU', 'High performance efficiency');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6X', 220, 'PCIe x16', '4.0', 244);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 7700 XT', 470.00, 1, '12GB GDDR6 mid-high range', 'RDNA 3 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 12, 'GDDR6', 245, 'PCIe x16', '4.0', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6800 XT', 650.00, 1, '16GB GDDR6 enthusiast', 'RDNA 2 GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 16, 'GDDR6', 300, 'PCIe x16', '4.0', 267);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 2060', 260.00, 1, '6GB GDDR6', 'Turing mid-range GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 6, 'GDDR6', 160, 'PCIe x16', '3.0', 229);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce GTX 1650 SUPER', 180.00, 1, '4GB GDDR6', 'Turing budget GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 100, 'PCIe x16', '3.0', 229);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 5500 XT', 160.00, 1, '8GB GDDR6', 'RDNA entry GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 130, 'PCIe x8', '4.0', 230);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 2080 Ti', 1000.00, 1, '11GB GDDR6 flagship', 'Turing');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 11, 'GDDR6', 260, 'PCIe x16', '3.0', 285);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 6, 'NVIDIA GeForce RTX 4060 SUPER', 400.00, 1, '8GB GDDR6 Ada mid-range', 'Ada Lovelace GPU');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 8, 'GDDR6', 160, 'PCIe x8', '4.0', 242);

INSERT INTO product (category_id, brand_id, product_name, price, status, description, specification) VALUES (3, 5, 'AMD Radeon RX 6400', 140.00, 1, '4GB GDDR6 compact GPU', 'Low power RDNA 2');
INSERT INTO gpu (product_id, vram, memory_type, tdp, gpu_interface, pcie_version, length) VALUES (LAST_INSERT_ID(), 4, 'GDDR6', 53, 'PCIe x4', '4.0', 170);