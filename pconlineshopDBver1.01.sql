CREATE DATABASE IF NOT EXISTS pconlineshop;
USE pconlineshop;

-- ==============================================
-- Table: Brand
-- ==============================================
CREATE TABLE Brand (
    BrandID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Description VARCHAR(500),
    Website VARCHAR(255)
);

-- ==============================================
-- Table: Category
-- ==============================================
CREATE TABLE Category (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(100) NOT NULL,
    Description VARCHAR(500),
    DisplayOrder INT,
    CreatedAt DATE
);

-- ==============================================
-- Table: Product
-- This table is the central entity.
-- Every specific component (CPU, GPU, etc.)
-- links to Product with a 1–0..1 relation.
-- ==============================================
CREATE TABLE Product (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryID INT NOT NULL,
    BrandID INT,
    ProductName VARCHAR(255) NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    Status BIT DEFAULT 1,
    Description VARCHAR(500),
    Specification VARCHAR(500),
    CreatedAt DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (CategoryID) REFERENCES Category(CategoryID),
    FOREIGN KEY (BrandID) REFERENCES Brand(BrandID)
);

-- ==============================================
-- Table: CPU
-- ==============================================
CREATE TABLE CPU (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Socket VARCHAR(50),
    TDP INT,
    MaxMemorySize INT,
    MemoryChannels INT,
    HasIGPU BIT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: GPU
-- ==============================================
CREATE TABLE GPU (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    VRAM INT,
    MemoryType VARCHAR(50),
    TDP INT,
    Interface VARCHAR(50),
    Length INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Motherboard
-- ==============================================
CREATE TABLE Mainboard (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Socket VARCHAR(50),
    Chipset VARCHAR(50),
    FormFactor VARCHAR(50),
    RAMType VARCHAR(50),
    RAMSlots INT,
    MaxRAMSize INT,
    PCIEVersion VARCHAR(20),
    M2Slots INT,
    SataPorts INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);
-- ==============================================
-- Table: Memory (RAM)
-- ==============================================
CREATE TABLE Memory (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(50),
    Capacity INT,
    Speed INT,
    Voltage DECIMAL(4,2),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Storage
-- ==============================================
CREATE TABLE Storage (
	ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(50),
    Capacity INT,
    Interface VARCHAR(50),
    ReadSpeed INT,
    WriteSpeed INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Case
-- ==============================================
CREATE TABLE PC_Case (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    FormFactor VARCHAR(50),
    GPUMaxLength INT,
    CPUMaxCoolerHeight INT,
    PSUMaxLength INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Power Supply
-- ==============================================
CREATE TABLE PowerSupply (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Wattage INT,
    Efficiency VARCHAR(50),
    Modular BIT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Cooling
-- ==============================================
CREATE TABLE Cooling (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    Type VARCHAR(50),
    MaxTDP INT,
    FanSize INT,
    RadiatorSize INT,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Image
-- ==============================================
CREATE TABLE Image (
    ImageID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT NOT NULL,
    ImageUrl VARCHAR(255), -- store as URL or path instead of binary
    CreatedAt DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Account
-- ==============================================
CREATE TABLE Account (
    AccountID INT AUTO_INCREMENT PRIMARY KEY,
    PhoneNumber VARCHAR(20) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(50) DEFAULT 'Customer',
    Email VARCHAR(100),
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Gender BIT,
    Address VARCHAR(255),
    Avatar VARCHAR(255),
    Enabled BIT DEFAULT 1
);


-- ==============================================
-- Table: Cart
-- ==============================================
CREATE TABLE Cart (
    CartID INT AUTO_INCREMENT PRIMARY KEY,
    AccountID INT NOT NULL,
    Status VARCHAR(50),
    UpdatedDate DATE,
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID)
        ON DELETE CASCADE
);

-- ==============================================
-- Table: Cart Item
-- ==============================================
CREATE TABLE CartItem (
    CartItemID INT AUTO_INCREMENT PRIMARY KEY,
    CartID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT DEFAULT 1,
    FOREIGN KEY (CartID) REFERENCES Cart(CartID)
        ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

-- ==============================================
-- Table: Order
-- ==============================================
CREATE TABLE `Order` (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    AccountID INT NOT NULL,
    TotalAmount DECIMAL(10,2),
    DiscountAmount DECIMAL(10,2),
    FinalAmount DECIMAL(10,2),
    VoucherCode VARCHAR(50),
    Status VARCHAR(50),
    CreatedDate DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID)
);

-- ==============================================
-- Table: Order Detail
-- ==============================================
CREATE TABLE OrderDetail (
    OrderDetailID INT AUTO_INCREMENT PRIMARY KEY,
    OrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    Price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID)
        ON DELETE CASCADE,
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);

-- ==============================================
-- Table: Feedback
-- ==============================================
CREATE TABLE Feedback (
    FeedbackID INT AUTO_INCREMENT PRIMARY KEY,
    AccountID INT NOT NULL,
    ProductID INT NOT NULL,
    Comment VARCHAR(500),
    CommentStatus VARCHAR(50),
    CreatedAt DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);