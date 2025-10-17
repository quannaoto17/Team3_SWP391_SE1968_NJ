INSERT INTO orders (
    account_id, 
    final_amount, 
    status, 
    created_date, 
    shipping_method, 
    note, 
    customer_email, 
    shipping_full_name, 
    shipping_phone, 
    shipping_address, 
    shipper_account_id, 
    tracking_number
) VALUES
-- CUSTOMER 1 (account_id=1) - 4 orders
(1, 1460000, 'Completed', '2023-01-15', 'Giao hàng tận nơi', NULL, 'customer1@email.com', 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 4220000, 'Shipped', '2023-02-20', 'Giao hàng tận nơi', NULL, 'customer1@email.com', 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 910000, 'Processing', '2023-03-10', 'Giao hàng tận nơi', NULL, 'customer1@email.com', 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),
(1, 5440000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer1@email.com', 'Khách hàng 1', '0900000001', '123 Đường ABC, Q1, TP.HCM', NULL, NULL),

-- CUSTOMER 3 (account_id=3) - 4 orders
(3, 680000, 'Completed', '2023-01-25', 'Giao hàng tận nơi', NULL, 'customer3@email.com', 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 2080000, 'Shipped', '2023-02-28', 'Giao hàng tận nơi', NULL, 'customer3@email.com', 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 230000, 'Completed', '2023-03-15', 'Giao hàng tận nơi', NULL, 'customer3@email.com', 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(3, 970000, 'Processing', '2023-04-05', 'Giao hàng tận nơi', NULL, 'customer3@email.com', 'Khách hàng 3', '0900000003', '456 Đường XYZ, Q3, TP.HCM', NULL, NULL),

-- CUSTOMER 5 (account_id=5) - 4 orders
(5, 1830000, 'Completed', '2023-02-05', 'Giao hàng tận nơi', NULL, 'customer5@email.com', 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 2610000, 'Shipped', '2023-03-12', 'Giao hàng tận nơi', NULL, 'customer5@email.com', 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 540000, 'Processing', '2023-04-01', 'Giao hàng tận nơi', NULL, 'customer5@email.com', 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),
(5, 1770000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer5@email.com', 'Khách hàng 5', '0900000005', '789 Đường DEF, Q5, TP.HCM', NULL, NULL),

-- CUSTOMER 7 (account_id=7) - 4 orders
(7, 1300000, 'Completed', '2023-02-15', 'Giao hàng tận nơi', NULL, 'customer7@email.com', 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 690000, 'Completed', '2023-03-20', 'Giao hàng tận nơi', NULL, 'customer7@email.com', 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 370000, 'Shipped', '2023-04-18', 'Giao hàng tận nơi', NULL, 'customer7@email.com', 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),
(7, 4110000, 'Processing', '2023-05-10', 'Giao hàng tận nơi', NULL, 'customer7@email.com', 'Khách hàng 7', '0900000007', '101 Đường GHI, Q7, TP.HCM', NULL, NULL),

-- CUSTOMER 9 (account_id=9) - 4 orders
(9, 400000, 'Completed', '2023-02-25', 'Giao hàng tận nơi', NULL, 'customer9@email.com', 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 1940000, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer9@email.com', 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 260000, 'Shipped', '2023-03-30', 'Giao hàng tận nơi', NULL, 'customer9@email.com', 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),
(9, 550000, 'Processing', '2023-04-20', 'Giao hàng tận nơi', NULL, 'customer9@email.com', 'Khách hàng 9', '0900000009', '202 Đường JKL, Q9, TP.HCM', NULL, NULL),

-- CÁC ĐƠN HÀNG CÒN LẠI (21-60) KHÔNG CÓ DỮ LIỆU ORDER_DETAIL
-- CUSTOMER 11 (account_id=11)
(11, 0, 'Completed', '2023-03-05', 'Giao hàng tận nơi', NULL, 'customer11@email.com', 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Completed', '2023-04-12', 'Giao hàng tận nơi', NULL, 'customer11@email.com', 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Shipped', '2023-05-08', 'Giao hàng tận nơi', NULL, 'customer11@email.com', 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),
(11, 0, 'Processing', '2023-05-25', 'Giao hàng tận nơi', NULL, 'customer11@email.com', 'Khách hàng 11', '0900000011', '303 Đường MNO, Q11, TP.HCM', NULL, NULL),

-- CUSTOMER 13 (account_id=13)
(13, 0, 'Completed', '2023-03-15', 'Giao hàng tận nơi', NULL, 'customer13@email.com', 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Shipped', '2023-04-22', 'Giao hàng tận nơi', NULL, 'customer13@email.com', 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Processing', '2023-05-15', 'Giao hàng tận nơi', NULL, 'customer13@email.com', 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),
(13, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer13@email.com', 'Khách hàng 13', '0900000013', '404 Đường PQR, Q12, TP.HCM', NULL, NULL),

-- CUSTOMER 15 (account_id=15)
(15, 0, 'Completed', '2023-03-25', 'Giao hàng tận nơi', NULL, 'customer15@email.com', 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Shipped', '2023-05-01', 'Giao hàng tận nơi', NULL, 'customer15@email.com', 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Completed', '2023-05-18', 'Giao hàng tận nơi', NULL, 'customer15@email.com', 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),
(15, 0, 'Processing', '2023-06-05', 'Giao hàng tận nơi', NULL, 'customer15@email.com', 'Khách hàng 15', '0900000015', '505 Đường STU, Q.Tân Bình, TP.HCM', NULL, NULL),

-- CUSTOMER 17 (account_id=17)
(17, 0, 'Completed', '2023-04-10', 'Giao hàng tận nơi', NULL, 'customer17@email.com', 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Shipped', '2023-05-20', 'Giao hàng tận nơi', NULL, 'customer17@email.com', 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Processing', '2023-06-12', 'Giao hàng tận nơi', NULL, 'customer17@email.com', 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),
(17, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer17@email.com', 'Khách hàng 17', '0900000017', '606 Đường VWX, Q.Bình Thạnh, TP.HCM', NULL, NULL),

-- CUSTOMER 19 (account_id=19)
(19, 0, 'Completed', '2023-04-20', 'Giao hàng tận nơi', NULL, 'customer19@email.com', 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Shipped', '2023-06-01', 'Giao hàng tận nơi', NULL, 'customer19@email.com', 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Processing', '2023-06-20', 'Giao hàng tận nơi', NULL, 'customer19@email.com', 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),
(19, 0, 'Completed', '2023-07-10', 'Giao hàng tận nơi', NULL, 'customer19@email.com', 'Khách hàng 19', '0900000019', '707 Đường YZ, Q.Gò Vấp, TP.HCM', NULL, NULL),

-- CUSTOMER 21 (account_id=21)
(21, 0, 'Completed', '2023-05-05', 'Giao hàng tận nơi', NULL, 'customer21@email.com', 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Shipped', '2023-06-15', 'Giao hàng tận nơi', NULL, 'customer21@email.com', 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Processing', '2023-07-08', 'Giao hàng tận nơi', NULL, 'customer21@email.com', 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),
(21, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer21@email.com', 'Khách hàng 21', '0900000021', '808 Đường 123, Q.Phú Nhuận, TP.HCM', NULL, NULL),

-- CUSTOMER 23 (account_id=23)
(23, 0, 'Completed', '2023-05-15', 'Giao hàng tận nơi', NULL, 'customer23@email.com', 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Shipped', '2023-07-01', 'Giao hàng tận nơi', NULL, 'customer23@email.com', 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Completed', '2023-07-18', 'Giao hàng tận nơi', NULL, 'customer23@email.com', 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),
(23, 0, 'Processing', '2023-08-05', 'Giao hàng tận nơi', NULL, 'customer23@email.com', 'Khách hàng 23', '0900000023', '909 Đường 456, Q.Bình Tân, TP.HCM', NULL, NULL),

-- CUSTOMER 25 (account_id=25)
(25, 0, 'Completed', '2023-05-25', 'Giao hàng tận nơi', NULL, 'customer25@email.com', 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Processing', '2023-06-30', 'Giao hàng tận nơi', NULL, 'customer25@email.com', 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Shipped', '2023-08-10', 'Giao hàng tận nơi', NULL, 'customer25@email.com', 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),
(25, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer25@email.com', 'Khách hàng 25', '0900000025', '111 Đường 789, TP.Thủ Đức, TP.HCM', NULL, NULL),

-- CUSTOMER 27 (account_id=27)
(27, 0, 'Completed', '2023-06-10', 'Giao hàng tận nơi', NULL, 'customer27@email.com', 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Shipped', '2023-07-20', 'Giao hàng tận nơi', NULL, 'customer27@email.com', 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Processing', '2023-08-15', 'Giao hàng tận nơi', NULL, 'customer27@email.com', 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),
(27, 0, 'Completed', '2023-09-05', 'Giao hàng tận nơi', NULL, 'customer27@email.com', 'Khách hàng 27', '0900000027', '222 Đường ABC, Q1, TP.HCM', NULL, NULL),

-- CUSTOMER 29 (account_id=29)
(29, 0, 'Completed', '2023-06-20', 'Giao hàng tận nơi', NULL, 'customer29@email.com', 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Shipped', '2023-08-01', 'Giao hàng tận nơi', NULL, 'customer29@email.com', 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Pending', CURDATE(), 'Giao hàng tận nơi', NULL, 'customer29@email.com', 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL),
(29, 0, 'Processing', '2023-09-10', 'Giao hàng tận nơi', NULL, 'customer29@email.com', 'Khách hàng 29', '0900000029', '333 Đường XYZ, Q3, TP.HCM', NULL, NULL);

INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
-- ORDER 1 (Total: 1,460,000)
(1, 1, 2, 220000),
(1, 31, 1, 330000),
(1, 61, 1, 150000),
(1, 2, 3, 180000),

-- ORDER 2 (Total: 4,220,000)
(2, 4, 1, 590000),
(2, 34, 2, 880000),
(2, 62, 1, 390000),
(2, 5, 4, 370000),

-- ORDER 3 (Total: 910,000)
(3, 2, 2, 180000),
(3, 33, 1, 550000),

-- ORDER 4 (Total: 5,440,000)
(4, 16, 1, 470000),
(4, 46, 2, 1600000),
(4, 65, 1, 420000),
(4, 17, 3, 450000),

-- ORDER 5 (Total: 680,000)
(5, 15, 2, 130000),
(5, 37, 1, 250000),
(5, 64, 1, 170000),

-- ORDER 6 (Total: 2,080,000)
(6, 9, 1, 340000),
(6, 36, 2, 580000),
(6, 63, 1, 260000),
(6, 10, 1, 320000),

-- ORDER 7 (Total: 230,000)
(7, 8, 1, 120000),
(7, 67, 1, 110000),

-- ORDER 8 (Total: 970,000)
(8, 35, 1, 790000),
(8, 38, 1, 180000),

-- ORDER 9 (Total: 1,830,000)
(9, 7, 1, 240000),
(9, 32, 1, 320000),
(9, 60, 1, 140000),
(9, 11, 1, 550000),
(9, 12, 1, 580000),

-- ORDER 10 (Total: 2,610,000)
(10, 18, 1, 620000),
(10, 45, 1, 1200000),
(10, 66, 1, 480000),
(10, 19, 1, 310000),

-- ORDER 11 (Total: 540,000)
(11, 3, 1, 420000),
(11, 68, 1, 120000),

-- ORDER 12 (Total: 1,770,000)
(12, 17, 2, 450000),
(12, 32, 1, 320000),
(12, 33, 1, 550000),

-- ORDER 13 (Total: 1,300,000)
(13, 11, 1, 550000),
(13, 39, 1, 500000),
(13, 69, 1, 250000),

-- ORDER 14 (Total: 690,000)
(14, 40, 1, 270000),
(14, 41, 1, 420000),

-- ORDER 15 (Total: 370,000)
(15, 70, 1, 260000),
(15, 71, 1, 110000),

-- ORDER 16 (Total: 4,110,000)
(16, 6, 1, 600000),
(16, 44, 1, 1400000),
(16, 72, 1, 110000),
(16, 6, 1, 600000),
(16, 44, 1, 1400000),

-- ORDER 17 (Total: 400,000)
(17, 13, 1, 90000),
(17, 42, 1, 310000),

-- ORDER 18 (Total: 1,940,000)
(18, 17, 1, 450000),
(18, 47, 1, 700000),
(18, 73, 1, 190000),
(18, 99, 2, 300000),

-- ORDER 19 (Total: 260,000)
(19, 43, 1, 260000),

-- ORDER 20 (Total: 550,000)
(20, 74, 1, 270000),
(20, 100, 1, 160000),
(20, 125, 1, 120000);