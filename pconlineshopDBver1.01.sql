CREATE DATABASE IF NOT EXISTS pconlineshop;
USE pconlineshop;

-- ==============================================
-- Table: brand
-- ==============================================
CREATE TABLE brand (
    brand_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    website VARCHAR(255)
);

-- ==============================================
-- Table: category
-- ==============================================
CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    display_order INT,
    created_at DATE
);

-- ==============================================
-- Table: product
-- ==============================================
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

-- ==============================================
-- Table: cpu
-- ==============================================
CREATE TABLE cpu (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    socket VARCHAR(50),
    tdp INT,
    max_memory_size INT,
    memory_channels INT,
    has_igpu BIT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: gpu
-- ==============================================
CREATE TABLE gpu (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    vram INT,
    memory_type VARCHAR(50),
    tdp INT,
    gpu_interface VARCHAR(50),
    length INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: mainboard
-- ==============================================
CREATE TABLE mainboard (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    socket VARCHAR(50),
    chipset VARCHAR(50),
    form_factor VARCHAR(50),
    ram_type VARCHAR(50),
    ram_slots INT,
    max_ram_size INT,
    pcie_version VARCHAR(20),
    m2_slots INT,
    sata_ports INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: memory
-- ==============================================
CREATE TABLE memory (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    capacity INT,
    speed INT,
    voltage DECIMAL(4,2),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: storage
-- ==============================================
CREATE TABLE storage (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    capacity INT,
    interface VARCHAR(50),
    read_speed INT,
    write_speed INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: pc_case
-- ==============================================
CREATE TABLE pc_case (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    form_factor VARCHAR(50),
    gpu_max_length INT,
    cpu_max_cooler_height INT,
    psu_max_length INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: power_supply
-- ==============================================
CREATE TABLE power_supply (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    wattage INT,
    efficiency VARCHAR(50),
    modular BIT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: cooling
-- ==============================================
CREATE TABLE cooling (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50),
    max_tdp INT,
    fan_size INT,
    radiator_size INT,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: image
-- ==============================================
CREATE TABLE image (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    image_url VARCHAR(255),
    created_at DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: account
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
-- Table: cart
-- ==============================================
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    status VARCHAR(50),
    updated_date DATE,
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE
);

-- ==============================================
-- Table: cart_item
-- ==============================================
CREATE TABLE cart_item (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- ==============================================
-- Table: order
-- ==============================================
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    total_amount DECIMAL(10,2),
    discount_amount DECIMAL(10,2),
    final_amount DECIMAL(10,2),
    voucher_code VARCHAR(50),
    status VARCHAR(50),
    created_date DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);

-- ==============================================
-- Table: order_detail
-- ==============================================
CREATE TABLE order_detail (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

-- ==============================================
-- Table: feedback
-- ==============================================
CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT NOT NULL,
    product_id INT NOT NULL,
    comment VARCHAR(500),
    comment_status VARCHAR(50),
    created_at DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (account_id) REFERENCES account(account_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
