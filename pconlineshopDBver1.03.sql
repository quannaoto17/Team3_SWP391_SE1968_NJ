CREATE DATABASE IF NOT EXISTS pconlineshop;
USE pconlineshop;

-- ==============================================
-- Bảng quản lý Sản phẩm (Không đổi)
-- ==============================================
CREATE TABLE brand (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    website VARCHAR(255)
);

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    display_order INT,
    created_at DATE
);

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    brand_id INT,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status BIT DEFAULT 1,
    description VARCHAR(500),
    specification VARCHAR(500),
    created_at DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (brand_id) REFERENCES brand(brand_id)
);


-- Table: cpu
-- ==============================================
CREATE TABLE cpu
(
    product_id       INT AUTO_INCREMENT PRIMARY KEY,
    socket           VARCHAR(50),
    tdp              INT,
    max_memory_speed INT,
    memory_channels  INT,
    pcie_version     VARCHAR(20),
    has_igpu         BIT,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: gpu
-- ==============================================
CREATE TABLE gpu
(
    product_id    INT AUTO_INCREMENT PRIMARY KEY,
    vram          INT,
    memory_type   VARCHAR(50),
    tdp           INT,
    gpu_interface VARCHAR(50),
    pcie_version  VARCHAR(20),
    length        INT,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: mainboard
-- ==============================================
CREATE TABLE mainboard
(
    product_id       INT AUTO_INCREMENT PRIMARY KEY,
    socket           VARCHAR(50),
    chipset          VARCHAR(50),
    form_factor      VARCHAR(50),
    memory_slots     INT,
    max_memory_speed INT,
    memory_type      VARCHAR(50),
    pcie_version     VARCHAR(20),
    m2_slots         INT,
    sata_ports       INT,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: memory
-- ==============================================
CREATE TABLE memory
(
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    type       VARCHAR(50),
    capacity   INT,
    speed      INT,
    tdp        INT,
    modules INT DEFAULT 1,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: storage
-- ==============================================
CREATE TABLE storage
(
    product_id  INT AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50),
    capacity    INT,
    interface   VARCHAR(50),
    read_speed  INT,
    write_speed INT,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: pc_case
-- ==============================================
CREATE TABLE pc_case
(
    product_id            INT AUTO_INCREMENT PRIMARY KEY,
    form_factor           VARCHAR(50),
    gpu_max_length        INT,
    cpu_max_cooler_height INT,
    psu_form_factor       VARCHAR(20), -- ATX, SFX, SFX-L, TFX (case hỗ trợ PSU form factor nào)
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: power_supply
-- ==============================================
CREATE TABLE power_supply
(
    product_id  INT AUTO_INCREMENT PRIMARY KEY,
    wattage     INT,
    efficiency  VARCHAR(50),
    modular     BIT,
    form_factor VARCHAR(20),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: cooling
-- ==============================================
CREATE TABLE cooling
(
    product_id    INT AUTO_INCREMENT PRIMARY KEY,
    type          VARCHAR(50),
    max_tdp       INT,
    fan_size      INT,
    radiator_size INT,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE image (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(255),
    created_at DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Bảng quản lý Người dùng (Không đổi)
-- =Ghi chú: Cột 'role' sẽ chứa 'Customer', 'Staff', 'Admin', và 'Shipper'
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

-- ==============================================
-- Bảng Sổ địa chỉ (BẢNG MỚI)
-- =Hỗ trợ tính năng "Địa chỉ của tôi" trên trang checkout
-- ==============================================
CREATE TABLE account_address (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL,
    is_default BIT DEFAULT 0, -- 1 = Địa chỉ mặc định
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

-- ==============================================
-- Bảng Giỏ hàng (Không đổi, nếu bạn vẫn dùng)
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
-- Bảng Đơn hàng (ĐÃ CẬP NHẬT TOÀN DIỆN)
-- ==============================================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL, -- ID của khách hàng

    -- Chỉ giữ lại tổng tiền cuối cùng (đã bỏ total_amount, discount, voucher)
    final_amount DECIMAL(10,2),

    -- Trạng thái (Pending, Processing, Ready to Ship, Delivering, Completed, Cancelled)
    status VARCHAR(50),
    created_date DATE DEFAULT (CURRENT_DATE),

    -- Dữ liệu từ trang checkout (Phương thức, Ghi chú, Email)
    shipping_method VARCHAR(50) NOT NULL, -- "Giao hàng tận nơi" hoặc "Nhận tại cửa hàng"
    note TEXT NULL,

    -- Dữ liệu "Ảnh chụp" địa chỉ giao hàng tại thời điểm đặt
    shipping_full_name VARCHAR(100) NOT NULL,
    shipping_phone VARCHAR(20) NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,

    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- ==============================================
-- Bảng Chi tiết đơn hàng (Không đổi)
-- ==============================================
CREATE TABLE order_detail (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL, -- Giá tại thời điểm mua
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- ==============================================
-- Bảng Feedback (Không đổi)
-- ==============================================
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


ALTER TABLE orders
ADD COLUMN ready_to_ship_date DATETIME NULL
COMMENT 'Timestamp when status became Ready to Ship';

ALTER TABLE orders
ADD COLUMN shipment_received_date DATETIME NULL
COMMENT 'Thời điểm shipper chuyển trạng thái sang Delivering';

alter table product
add	column inventory_quantity int;



