USE pconlineshop;

-- Tạo orders với final_amount tính toán sẵn
INSERT INTO orders (account_id, final_amount, status, created_date, shipping_method, shipping_full_name, shipping_phone, shipping_address) VALUES
-- Customer 1 (0239473473) - Nguyễn Quang Anh
(1, 1460000, 'Completed', '2023-01-15', 'Giao hàng tận nơi', 'Nguyen An', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 4220000, 'Ready to Ship', '2023-02-20', 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 910000, 'Ready to Ship', '2023-10-11', 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 5440000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),

-- Customer 2 (0901234567)
(3, 680000, 'Completed', '2023-01-25', 'Giao hàng tận nơi', 'Le Cuong', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(3, 2080000, 'Ready to Ship', '2023-02-28', 'Giao hàng tận nơi', 'Le Cuong', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(3, 230000, 'Completed', '2023-03-15', 'Giao hàng tận nơi', 'Le Cuong', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(3, 970000, 'Ready to Ship', '2023-04-05', 'Giao hàng tận nơi', 'Le Cuong', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),

-- Customer 3 (0912345678)
(5, 1830000, 'Completed', '2023-02-05', 'Giao hàng tận nơi', 'Hoàng Em', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(5, 2610000, 'Ready to Ship', '2023-03-12', 'Giao hàng tận nơi', 'Hoàng Em', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(5, 540000, 'Ready to Ship', '2023-04-01', 'Giao hàng tận nơi', 'Hoàng Em', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(5, 1770000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Hoàng Em', '0912345678', '789 Đường DEF, Q5, TP.HCM'),

-- Customer 4 (0923456789)
(7, 1300000, 'Completed', '2023-02-15', 'Giao hàng tận nơi', 'Vũ Lâm', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(7, 690000, 'Completed', '2023-03-20', 'Giao hàng tận nơi', 'Vũ Lâm', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(7, 370000, 'Ready to Ship', '2023-04-18', 'Giao hàng tận nơi', 'Vũ Lâm', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(7, 4110000, 'Ready to Ship', '2023-05-10', 'Giao hàng tận nơi', 'Vũ Lâm', '0923456789', '101 Đường GHI, Q7, TP.HCM'),

-- Customer 5 (0934567890)
(9, 400000, 'Completed', '2023-02-25', 'Giao hàng tận nơi', 'Bùi Minh Phúc', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(9, 1340000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Bùi Minh Phúc', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(9, 260000, 'Ready to Ship', '2023-03-30', 'Giao hàng tận nơi', 'Bùi Minh Phúc', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(9, 270000, 'Ready to Ship', '2023-04-20', 'Giao hàng tận nơi', 'Bùi Minh Phúc', '0934567890', '202 Đường JKL, Q9, TP.HCM')
;

-- Order detail khớp với final_amount
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
-- ORDER 1: 1,460,000 = (150,000 + 220,000 + 330,000) + (180,000 × 4)
(1, 1, 1, 150000),   -- ASUS PRIME B550M-A
(1, 31, 1, 220000),  -- AMD Ryzen 5 5600X
(1, 61, 1, 330000),  -- NVIDIA RTX 3060
(1, 32, 4, 180000),  -- Intel Core i5-12400F (4 cái)

-- ORDER 2: 4,220,000 = (390,000 + 590,000 + 880,000 × 2) + (370,000 × 4)
(2, 2, 1, 390000),   -- MSI MPG X670E CARBON
(2, 34, 1, 590000),  -- AMD Ryzen 9 7950X
(2, 64, 2, 880000),  -- AMD Radeon RX 7900 XT (2 cái)
(2, 35, 4, 370000),  -- AMD Ryzen 7 5800X3D (4 cái)

-- ORDER 3: 910,000 = (180,000 × 2) + 550,000
(3, 32, 2, 180000),  -- Intel Core i5-12400F (2 cái)
(3, 63, 1, 550000),  -- NVIDIA RTX 4070

-- ORDER 4: 5,440,000 = (470,000 + 1,600,000 × 2) + 420,000 + (450,000 × 3)
(4, 46, 1, 470000),  -- AMD Ryzen 9 7900X
(4, 76, 2, 1600000), -- NVIDIA RTX 4090 (2 cái)
(4, 65, 1, 420000),  -- ASUS ROG STRIX Z790-E
(4, 47, 3, 450000),  -- AMD Ryzen 7 7800X3D (3 cái)

-- ORDER 5: 680,000 = (130,000 × 2) + 250,000 + 170,000
(5, 45, 2, 130000),  -- AMD Ryzen 5 5500 (2 cái)
(5, 67, 1, 250000),  -- NVIDIA RTX 3050
(5, 64, 1, 170000),  -- ASRock A620M-HDV/M.2+

-- ORDER 6: 2,080,000 = 340,000 + (580,000 × 2) + 260,000 + 320,000
(6, 39, 1, 340000),  -- AMD Ryzen 7 7700
(6, 66, 2, 580000),  -- AMD Radeon RX 6800 (2 cái)
(6, 63, 1, 260000),  -- GIGABYTE B760M DS3H
(6, 40, 1, 320000),  -- Intel Core i5-13600K

-- ORDER 7: 230,000 = 120,000 + 110,000
(7, 38, 1, 120000),  -- Intel Core i3-12100F
(7, 9, 1, 110000),   -- ASRock H510M-HDV

-- ORDER 8: 970,000 = 790,000 + 180,000
(8, 69, 1, 790000),  -- NVIDIA RTX 4070 Ti
(8, 68, 1, 180000),  -- AMD Radeon RX 6500 XT

-- ORDER 9: 1,830,000 = 240,000 + 320,000 + 140,000 + 550,000 + 580,000
(9, 37, 1, 240000),  -- AMD Ryzen 5 7600
(9, 62, 1, 320000),  -- AMD Radeon RX 6600 XT
(9, 90, 1, 140000),  -- AMD Radeon RX 6400
(9, 41, 1, 550000),  -- AMD Ryzen 9 5950X
(9, 42, 1, 580000),  -- Intel Core i9-12900KS

-- ORDER 10: 2,610,000 = 620,000 + 1,200,000 + 480,000 + 310,000
(10, 48, 1, 620000),  -- Intel Core i9-14900K
(10, 65, 1, 1200000), -- NVIDIA RTX 4080
(10, 15, 1, 480000),  -- GIGABYTE Z790 AORUS MASTER
(10, 49, 1, 310000),  -- Intel Core i7-12700F

-- ORDER 11: 540,000 = 420,000 + 120,000
(11, 33, 1, 420000),  -- Intel Core i7-13700K
(11, 10, 1, 120000),  -- MSI B450 TOMAHAWK MAX II

-- ORDER 12: 1,770,000 = (450,000 × 2) + 320,000 + 550,000
(12, 47, 2, 450000),  -- AMD Ryzen 7 7800X3D (2 cái)
(12, 62, 1, 320000),  -- AMD Radeon RX 6600 XT
(12, 63, 1, 550000),  -- NVIDIA RTX 4070

-- ORDER 13: 1,300,000 = 550,000 + 500,000 + 250,000
(13, 41, 1, 550000),  -- AMD Ryzen 9 5950X
(13, 80, 1, 500000),  -- NVIDIA RTX 3070
(13, 13, 1, 250000),  -- ASRock Z690 PG RIPTIDE

-- ORDER 14: 690,000 = 270,000 + 420,000
(14, 77, 1, 270000),  -- AMD Radeon RX 7600
(14, 78, 1, 420000),  -- NVIDIA RTX 4060 Ti

-- ORDER 15: 370,000 = 260,000 + 110,000
(15, 85, 1, 260000),  -- NVIDIA RTX 2060
(15, 22, 1, 110000),  -- GIGABYTE B365M DS3H

-- ORDER 16: 4,110,000 = 600,000 + 1,400,000 + 110,000 + 600,000 + 1,400,000
(16, 36, 1, 600000),  -- Intel Core i9-13900K
(16, 76, 1, 1400000), -- NVIDIA RTX 4090
(16, 12, 1, 110000),  -- ASUS PRIME H610M-K D4
(16, 36, 1, 600000),  -- Intel Core i9-13900K (lặp lại)
(16, 76, 1, 1400000), -- NVIDIA RTX 4090 (lặp lại)

-- ORDER 17: 400,000 = 90,000 + 310,000
(17, 43, 1, 90000),   -- AMD Ryzen 3 4100
(17, 71, 1, 310000),  -- NVIDIA RTX 4060

-- ORDER 18: 1,340,000 = 450,000 + 700,000 + 190,000
(18, 47, 1, 450000),  -- AMD Ryzen 7 7800X3D
(18, 79, 1, 700000),  -- AMD Radeon RX 6950 XT
(18, 24, 1, 190000),  -- ASUS PRIME X570-P

-- ORDER 19: 260,000 = 260,000
(19, 85, 1, 260000),  -- NVIDIA RTX 2060

-- ORDER 20: 270,000 = 270,000
(20, 77, 1, 270000);  -- AMD Radeon RX 7600


INSERT INTO feedback (account_id, product_id, comment, rating, comment_status, reply, created_at) VALUES
                                                                                                      (1, 3, 'Mới mua về test tạm thấy ổn, nhưng chưa kiểm chứng lâu dài.', 4, 'PENDING', NULL, '2025-09-28'),
                                                                                                      (3, 7, 'Lắp vào case hơi chật, cần xem lại kích thước ghi trong mô tả.', 3, 'PENDING', NULL, '2025-09-25'),
                                                                                                      (5, 2, 'Tạm ổn nhưng giá hơi cao so với cấu hình, sẽ theo dõi thêm.', 3, 'PENDING', NULL, '2025-09-20'),
                                                                                                      (7, 9, 'Dùng được, chưa gặp lỗi gì nhưng vẫn đang xem hiệu năng lâu dài.', 4, 'PENDING', NULL, '2025-09-17'),
                                                                                                      (9, 1, 'Build xong nhưng BIOS chưa update, phải tự flash lại khá phiền.', 2, 'PENDING', NULL, '2025-09-10');
