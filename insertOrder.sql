USE pconlineshop;

-- Tạo orders với final_amount tính toán sẵn
INSERT INTO orders (account_id, final_amount, status, created_date, shipping_method, shipping_full_name, shipping_phone, shipping_address) VALUES
-- Customer 1 (0239473473) - Nguyễn Quang Anh
(1, 1460000, 'Completed', '2023-01-15', 'Giao hàng tận nơi', 'Nguyen An', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 4220000, 'Shipped', '2023-02-20', 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 910000, 'Processing', '2023-10-11', 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),
(1, 5440000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Nguyễn Quang Anh', '0239473473', '123 Đường ABC, Q1, TP.HCM'),

-- Customer 2 (0901234567)
(2, 680000, 'Completed', '2023-01-25', 'Giao hàng tận nơi', 'Trần Văn Bình', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(2, 2080000, 'Shipped', '2023-02-28', 'Giao hàng tận nơi', 'Trần Văn Bình', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(2, 230000, 'Completed', '2023-03-15', 'Giao hàng tận nơi', 'Trần Văn Bình', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),
(2, 970000, 'Processing', '2023-04-05', 'Giao hàng tận nơi', 'Trần Văn Bình', '0901234567', '456 Đường XYZ, Q3, TP.HCM'),

-- Customer 3 (0912345678)
(3, 1830000, 'Completed', '2023-02-05', 'Giao hàng tận nơi', 'Lê Thị Cẩm', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(3, 2610000, 'Shipped', '2023-03-12', 'Giao hàng tận nơi', 'Lê Thị Cẩm', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(3, 540000, 'Processing', '2023-04-01', 'Giao hàng tận nơi', 'Lê Thị Cẩm', '0912345678', '789 Đường DEF, Q5, TP.HCM'),
(3, 1770000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Lê Thị Cẩm', '0912345678', '789 Đường DEF, Q5, TP.HCM'),

-- Customer 4 (0923456789)
(4, 1300000, 'Completed', '2023-02-15', 'Giao hàng tận nơi', 'Phạm Văn Đức', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(4, 690000, 'Completed', '2023-03-20', 'Giao hàng tận nơi', 'Phạm Văn Đức', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(4, 370000, 'Shipped', '2023-04-18', 'Giao hàng tận nơi', 'Phạm Văn Đức', '0923456789', '101 Đường GHI, Q7, TP.HCM'),
(4, 4110000, 'Processing', '2023-05-10', 'Giao hàng tận nơi', 'Phạm Văn Đức', '0923456789', '101 Đường GHI, Q7, TP.HCM'),

-- Customer 5 (0934567890)
(5, 400000, 'Completed', '2023-02-25', 'Giao hàng tận nơi', 'Hoàng Thị Em', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(5, 1340000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Hoàng Thị Em', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(5, 260000, 'Shipped', '2023-03-30', 'Giao hàng tận nơi', 'Hoàng Thị Em', '0934567890', '202 Đường JKL, Q9, TP.HCM'),
(5, 270000, 'Processing', '2023-04-20', 'Giao hàng tận nơi', 'Hoàng Thị Em', '0934567890', '202 Đường JKL, Q9, TP.HCM')
;
INSERT INTO orders (account_id, final_amount, status, created_date, shipping_method, shipping_full_name, shipping_phone, shipping_address) VALUES
(34, 400000, 'Completed', '2023-10-25', 'Giao hàng tận nơi', 'Nguyễn Phương Anh', '061251565', '202 Đường JKL, Q9, TP.HCM'),
(34, 1340000, 'Pending', CURDATE(), 'Giao hàng tận nơi', 'Nguyễn Phương Anh', '061251565', '202 Đường JKL, Q9, TP.HCM'),
(34, 260000, 'Shipped', '2023-04-04', 'Giao hàng tận nơi', 'Nguyễn Phương Anh', '061251565', '202 Đường JKL, Q9, TP.HCM'),
(34, 270000, 'Processing', '2023-04-20', 'Giao hàng tận nơi', 'Nguyễn Phương Anh', '061251565', '202 Đường JKL, Q9, TP.HCM');