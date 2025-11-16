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
