use pconlineshop;

USE pconlineshop;

-- ==============================================
-- CHÈN DỮ LIỆU CHO BẢNG ORDERS
-- ==============================================

-- Đơn hàng cho account_id = 1 (5 đơn hàng)
INSERT INTO orders (account_id, total_amount, discount_amount, final_amount, voucher_code, status, created_date) VALUES
(1, 470.00, 0, 470.00, NULL, 'Completed', '2023-01-15'),
(1, 330.00, 0, 330.00, NULL, 'Completed', '2023-03-22'),
(1, 1200.00, 0, 1200.00, NULL, 'Shipped', '2023-05-10'),
(1, 550.00, 0, 550.00, NULL, 'Processing', '2023-06-01'),
(1, 790.00, 0, 790.00, NULL, 'Pending', CURDATE());

-- Đơn hàng cho account_id = 2 (5 đơn hàng)
INSERT INTO orders (account_id, total_amount, discount_amount, final_amount, voucher_code, status, created_date) VALUES
(2, 180.00, 0, 180.00, NULL, 'Completed', '2023-02-05'),
(2, 420.00, 0, 420.00, NULL, 'Completed', '2023-04-11'),
(2, 1600.00, 0, 1600.00, NULL, 'Shipped', '2023-06-22'),
(2, 240.00, 0, 240.00, NULL, 'Processing', '2023-07-30'),
(2, 340.00, 0, 340.00, NULL, 'Pending', CURDATE());

-- Đơn hàng cho account_id = 3 (5 đơn hàng)
INSERT INTO orders (account_id, total_amount, discount_amount, final_amount, voucher_code, status, created_date) VALUES
(3, 370.00, 0, 370.00, NULL, 'Completed', '2023-01-25'),
(3, 220.00, 0, 220.00, NULL, 'Completed', '2023-03-01'),
(3, 880.00, 0, 880.00, NULL, 'Shipped', '2023-05-18'),
(3, 580.00, 0, 580.00, NULL, 'Processing', '2023-08-20'),
(3, 250.00, 0, 250.00, NULL, 'Pending', CURDATE());

-- ==============================================
-- CHÈN DỮ LIỆU CHO BẢNG ORDER_DETAIL
-- ==============================================

-- Chi tiết cho các đơn hàng của account_id = 1 (order_id từ 1 đến 5)
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
-- Đơn hàng 1
(1, 16, 1, 470.00), -- AMD Ryzen 9 7900X
-- Đơn hàng 2
(2, 26, 1, 330.00), -- Intel Core i7-11700K
-- Đơn hàng 3
(3, 5, 1, 590.00), -- AMD Ryzen 9 7950X
(3, 6, 1, 600.00), -- Intel Core i9-13900K
-- Đơn hàng 4
(4, 11, 1, 550.00), -- AMD Ryzen 9 5950X
-- Đơn hàng 5
(5, 9, 1, 790.00); -- NVIDIA GeForce RTX 4070 Ti

-- Chi tiết cho các đơn hàng của account_id = 2 (order_id từ 6 đến 10)
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
-- Đơn hàng 6
(6, 2, 1, 180.00), -- Intel Core i5-12400F
-- Đơn hàng 7
(7, 3, 1, 420.00), -- Intel Core i7-13700K
-- Đơn hàng 8
(8, 16, 1, 1600.00), -- NVIDIA GeForce RTX 4090
-- Đơn hàng 9
(9, 7, 1, 240.00), -- AMD Ryzen 5 7600
-- Đơn hàng 10
(10, 9, 1, 340.00); -- AMD Ryzen 7 7700

-- Chi tiết cho các đơn hàng của account_id = 3 (order_id từ 11 đến 15)
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
-- Đơn hàng 11
(11, 5, 1, 370.00), -- AMD Ryzen 7 5800X3D
-- Đơn hàng 12
(12, 1, 1, 220.00), -- AMD Ryzen 5 5600X
-- Đơn hàng 13
(13, 4, 1, 880.00), -- AMD Radeon RX 7900 XT
-- Đơn hàng 14
(14, 6, 1, 580.00), -- AMD Radeon RX 6800
-- Đơn hàng 15
(15, 7, 1, 250.00); -- NVIDIA GeForce RTX 3050