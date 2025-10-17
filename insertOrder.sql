use pconlineshop;

INSERT INTO orders (
    account_id, 
    final_amount, 
    status, 
    created_date, 
    shipping_method, 
    note, 
    shipping_full_name, 
    shipping_phone, 
    shipping_address, 
    shipper_account_id, 
    tracking_number
) VALUES
-- CUSTOMER 1 (account_id=1) - 4 orders
(1, 1460000, 'Completed', '2023-01-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 4220000, 'Shipped', '2023-02-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 910000, 'Processing', '2023-03-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 5440000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),

-- CUSTOMER 3 (account_id=3) - 4 orders
(3, 680000, 'Completed', '2023-01-25', 'Giao hàng tận nơi', NULL, 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 2080000, 'Shipped', '2023-02-28', 'Giao hàng tận nơi', NULL, 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 230000, 'Completed', '2023-03-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 970000, 'Processing', '2023-04-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),

-- CUSTOMER 5 (account_id=5) - 4 orders
(5, 1830000, 'Completed', '2023-02-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 2610000, 'Shipped', '2023-03-12', 'Giao hàng tận nơi', NULL, 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 540000, 'Processing', '2023-04-01', 'Giao hàng tận nơi', NULL, 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 1770000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),

-- CUSTOMER 7 (account_id=7) - 4 orders
(7, 1300000, 'Completed', '2023-02-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 690000, 'Completed', '2023-03-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 370000, 'Shipped', '2023-04-18', 'Giao hàng tận nơi', NULL, 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 4110000, 'Processing', '2023-05-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),

-- CUSTOMER 9 (account_id=9) - 4 orders
(9, 400000, 'Completed', '2023-02-25', 'Giao hàng tận nơi', NULL, 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 1940000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 260000, 'Shipped', '2023-03-30', 'Giao hàng tận nơi', NULL, 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 550000, 'Processing', '2023-04-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),

-- CÁC ĐƠN HÀNG CÒN LẠI (21-60) KHÔNG CÓ DỮ LIỆU ORDER_DETAIL
-- CUSTOMER 11 (account_id=11)
(11, 0, 'Completed', '2023-03-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Completed', '2023-04-12', 'Giao hàng tận nơi', NULL, 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Shipped', '2023-05-08', 'Giao hàng tận nơi', NULL, 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Processing', '2023-05-25', 'Giao hàng tận nơi', NULL, 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),

-- CUSTOMER 13 (account_id=13)
(13, 0, 'Completed', '2023-03-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Shipped', '2023-04-22', 'Giao hàng tận nơi', NULL, 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Processing', '2023-05-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),

-- CUSTOMER 15 (account_id=15)
(15, 0, 'Completed', '2023-03-25', 'Giao hàng tận nơi', NULL, 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Shipped', '2023-05-01', 'Giao hàng tận nơi', NULL, 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Completed', '2023-05-18', 'Giao hàng tận nơi', NULL, 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Processing', '2023-06-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),

-- CUSTOMER 17 (account_id=17)
(17, 0, 'Completed', '2023-04-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Shipped', '2023-05-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Processing', '2023-06-12', 'Giao hàng tận nơi', NULL, 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),

-- CUSTOMER 19 (account_id=19)
(19, 0, 'Completed', '2023-04-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Shipped', '2023-06-01', 'Giao hàng tận nơi', NULL, 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Processing', '2023-06-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Completed', '2023-07-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),

-- CUSTOMER 21 (account_id=21)
(21, 0, 'Completed', '2023-05-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Shipped', '2023-06-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Processing', '2023-07-08', 'Giao hàng tận nơi', NULL, 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),

-- CUSTOMER 23 (account_id=23)
(23, 0, 'Completed', '2023-05-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Shipped', '2023-07-01', 'Giao hàng tận nơi', NULL, 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Completed', '2023-07-18', 'Giao hàng tận nơi', NULL, 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Processing', '2023-08-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),

-- CUSTOMER 25 (account_id=25)
(25, 0, 'Completed', '2023-05-25', 'Giao hàng tận nơi', NULL, 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Processing', '2023-06-30', 'Giao hàng tận nơi', NULL, 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Shipped', '2023-08-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),

-- CUSTOMER 27 (account_id=27)
(27, 0, 'Completed', '2023-06-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Shipped', '2023-07-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Processing', '2023-08-15', 'Giao hàng tận nơi', NULL, 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Completed', '2023-09-05', 'Giao hàng tận nơi', NULL, 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),

-- CUSTOMER 29 (account_id=29)
(29, 0, 'Completed', '2023-06-20', 'Giao hàng tận nơi', NULL, 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Shipped', '2023-08-01', 'Giao hàng tận nơi', NULL, 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Processing', '2023-09-10', 'Giao hàng tận nơi', NULL, 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL);

