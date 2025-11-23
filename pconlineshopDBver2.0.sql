CREATE DATABASE IF NOT EXISTS pconlineshop;
USE pconlineshop;

-- ==============================================
-- Core tables
-- ==============================================
CREATE TABLE brand (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    website VARCHAR(255),
    status BIT DEFAULT 1
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    display_order INT,
    created_at DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    brand_id INT,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status BIT DEFAULT 1,
    description VARCHAR(500),
    specification VARCHAR(500),
    created_at DATE DEFAULT (CURRENT_DATE),
    inventory_quantity INT DEFAULT 0,
    performance_score INT DEFAULT 50 CHECK (performance_score BETWEEN 0 AND 100),
    FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);

-- Many-to-many product-category (after product & category exist)
CREATE TABLE product_category (
    product_id INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE,
    INDEX idx_product_id (product_id),
    INDEX idx_category_id (category_id)
);

-- ==============================================
-- Component tables (use product_id as PK that references product)
-- ==============================================
CREATE TABLE cpu
(
    product_id       INT PRIMARY KEY,
    socket           VARCHAR(50),
    tdp              INT,
    max_memory_speed INT,
    memory_channels  INT,
    pcie_version     VARCHAR(20),
    has_igpu         BIT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE gpu
(
    product_id    INT PRIMARY KEY,
    vram          INT,
    memory_type   VARCHAR(50),
    tdp           INT,
    gpu_interface VARCHAR(50),
    pcie_version  VARCHAR(20),
    length        INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE mainboard
(
    product_id       INT PRIMARY KEY,
    socket           VARCHAR(50),
    chipset          VARCHAR(50),
    form_factor      VARCHAR(50),
    memory_slots     INT,
    max_memory_speed INT,
    memory_type      VARCHAR(50),
    pcie_version     VARCHAR(20),
    m2_slots         INT,
    sata_ports       INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE memory
(
    product_id INT PRIMARY KEY,
    type       VARCHAR(50),
    capacity   INT,
    speed      INT,
    tdp        INT,
    modules    INT DEFAULT 1,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE storage
(
    product_id  INT PRIMARY KEY,
    type        VARCHAR(50),
    capacity    INT,
    interface   VARCHAR(50),
    read_speed  INT,
    write_speed INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE pc_case
(
    product_id            INT PRIMARY KEY,
    form_factor           VARCHAR(50),
    gpu_max_length        INT,
    cpu_max_cooler_height INT,
    psu_form_factor       VARCHAR(20),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE power_supply
(
    product_id  INT PRIMARY KEY,
    wattage     INT,
    efficiency  VARCHAR(50),
    modular     BIT,
    form_factor VARCHAR(20),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE cooling
(
    product_id    INT PRIMARY KEY,
    type          VARCHAR(50),
    max_tdp       INT,
    fan_size      INT,
    radiator_size INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

CREATE TABLE image (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(255),
    created_at DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Account and related tables
-- ==============================================
CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'Customer',
    email VARCHAR(100),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    gender BIT,
    address VARCHAR(255),
    enabled BIT DEFAULT 1
);

CREATE TABLE account_address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    is_default BIT DEFAULT 0,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

-- ==============================================
-- Cart
-- ==============================================
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    status VARCHAR(50),
    updated_date DATE,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

CREATE TABLE cart_item (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- ==============================================
-- Orders and Payments (orders uses BIGINT)
-- ==============================================
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    final_amount DECIMAL(10,2),
    status VARCHAR(50),
    created_date DATE DEFAULT (CURRENT_DATE),
    shipping_method VARCHAR(50) NOT NULL,
    note TEXT NULL,
    shipping_full_name VARCHAR(100) NOT NULL,
    shipping_phone VARCHAR(20) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    ready_to_ship_date DATETIME NULL COMMENT 'Timestamp when status became Ready to Ship',
    shipment_received_date DATETIME NULL COMMENT 'Thời điểm shipper chuyển trạng thái sang Delivering',
    payment_id VARCHAR(255) NULL,
    payment_status VARCHAR(50) NULL,
    paid_at DATETIME NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    gateway_payment_id VARCHAR(255),
    amount DECIMAL(10,2),
    currency VARCHAR(10) DEFAULT 'VND',
    status VARCHAR(50),
    raw_payload TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    order_code BIGINT NULL UNIQUE,
    CONSTRAINT fk_payments_orders FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

-- ==============================================
-- Order details, Feedback
-- ==============================================
CREATE TABLE order_detail (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    product_id INT NOT NULL,
    comment VARCHAR(500),
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment_status VARCHAR(50),
    reply VARCHAR(500) NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

ALTER TABLE cart_item
ADD COLUMN is_selected BIT DEFAULT 1 NOT NULL,
ADD COLUMN is_build_item BIT DEFAULT 0 NOT NULL,
ADD COLUMN build_id VARCHAR(50) NULL ;

INSERT INTO account (phone_number, password, role, email, first_name, last_name, gender, address, enabled)
VALUES
    ('0900000001', 'pass123', 'Customer', 'user1@example.com', 'Nguyen', 'An', 1, 'Hanoi', 1),
    ('0900000002', 'pass123', 'Staff', 'user2@example.com', 'Tran', 'Binh', 0, 'HCMC', 1),
    ('0900000003', 'pass123', 'Customer', 'user3@example.com', 'Le', 'Cuong', 1, 'Da Nang', 1),
    ('0900000004', 'pass123', 'Staff', 'user4@example.com', 'Pham', 'Dung', 0, 'Hai Phong', 1),
    ('0900000005', 'pass123', 'Customer', 'user5@example.com', 'Hoang', 'Em', 1, 'Can Tho', 1),
    ('0900000006', 'pass123', 'Staff', 'user6@example.com', 'Do', 'Phong', 1, 'Hue', 1),
    ('0900000007', 'pass123', 'Customer', 'user7@example.com', 'Vu', 'Lam', 0, 'Quang Ninh', 1),
    ('0900000008', 'pass123', 'Staff', 'user8@example.com', 'Dang', 'Hieu', 1, 'Thanh Hoa', 1),
    ('0900000009', 'pass123', 'Customer', 'user9@example.com', 'Bui', 'Phuc', 0, 'Nghe An', 1),
    ('0900000010', 'pass123', 'Staff', 'user10@example.com', 'Cao', 'Minh', 1, 'Nam Dinh', 1),

    ('0900000011', 'pass123', 'Customer', 'user11@example.com', 'Nguyen', 'Khanh', 1, 'Hanoi', 1),
    ('0900000012', 'pass123', 'Staff', 'user12@example.com', 'Tran', 'Quang', 0, 'HCMC', 1),
    ('0900000013', 'pass123', 'Customer', 'user13@example.com', 'Le', 'Huy', 1, 'Da Nang', 1),
    ('0900000014', 'pass123', 'Staff', 'user14@example.com', 'Pham', 'Tuan', 0, 'Hai Duong', 1),
    ('0900000015', 'pass123', 'Customer', 'user15@example.com', 'Hoang', 'Bao', 1, 'Thai Binh', 1),
    ('0900000016', 'pass123', 'Staff', 'user16@example.com', 'Do', 'Son', 1, 'Ha Nam', 1),
    ('0900000017', 'pass123', 'Customer', 'user17@example.com', 'Vu', 'Tien', 0, 'Ninh Binh', 1),
    ('0900000018', 'pass123', 'Staff', 'user18@example.com', 'Dang', 'Long', 1, 'Hoa Binh', 1),
    ('0900000019', 'pass123', 'Customer', 'user19@example.com', 'Bui', 'Nam', 0, 'Lang Son', 1),
    ('0900000020', 'pass123', 'Staff', 'user20@example.com', 'Cao', 'Hai', 1, 'Bac Ninh', 1),

    ('0900000021', 'pass123', 'Customer', 'user21@example.com', 'Nguyen', 'Van', 1, 'Hanoi', 1),
    ('0900000022', 'pass123', 'Staff', 'user22@example.com', 'Tran', 'Phong', 0, 'HCMC', 1),
    ('0900000023', 'pass123', 'Customer', 'user23@example.com', 'Le', 'Hung', 1, 'Da Nang', 1),
    ('0900000024', 'pass123', 'Staff', 'user24@example.com', 'Pham', 'Son', 0, 'Hai Phong', 1),
    ('0900000025', 'pass123', 'Customer', 'user25@example.com', 'Hoang', 'Tu', 1, 'Can Tho', 1),
    ('0900000026', 'pass123', 'Staff', 'user26@example.com', 'Do', 'Trung', 1, 'Hue', 1),
    ('0900000027', 'pass123', 'Customer', 'user27@example.com', 'Vu', 'Khoa', 0, 'Quang Ninh', 1),
    ('0900000028', 'pass123', 'Staff', 'user28@example.com', 'Dang', 'Luc', 1, 'Thanh Hoa', 1),
    ('0900000029', 'pass123', 'Customer', 'user29@example.com', 'Bui', 'Dat', 0, 'Nghe An', 1),
    ('0900000030', 'pass123', 'Staff', 'user30@example.com', 'Cao', 'Manh', 1, 'Nam Dinh', 1);