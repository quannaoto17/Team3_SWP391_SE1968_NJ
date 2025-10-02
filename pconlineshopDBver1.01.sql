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

-- ================================================
-- Insert Table Account
-- ================================================

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled)
VALUES ('0978370541', 'UlsDsEw9)R8t', 'Admin', 'davisgabrielle@hotmail.com', 'Corey', 'Brown', 0, '02190 Conway Circle, Brianchester, VA 14654', 'https://example.com/avatar1.png', 1);

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled)
VALUES ('0971008160', ')o98OULmA9MA', 'Admin', 'irwinjulia@macias.com', 'Brandon', 'Berger', 0, '4962 Megan Bypass Suite 133, North Jennifer, WY 69066', 'https://example.com/avatar2.png', 1);

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled)
VALUES ('0990300487', '7e9r)Pu6@**)', 'Customer', 'turnererica@cooper.org', 'Jennifer', 'Cervantes', 1, '940 Brandon Neck Suite 070, South Juliestad, WA 77238', 'https://example.com/avatar3.png', 1);

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled)
VALUES ('0928164705', 'vEt6o%ukSP3l', 'Staff', 'hsmith@jenkins.com', 'Robert', 'Smith', 1, '1234 West Street, New York, NY 10001', 'https://example.com/avatar4.png', 1);

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled)
VALUES ('0965423781', 'aP*7H@e!1Zt$', 'Customer', 'andersonpaul@torres.org', 'Paul', 'Anderson', 0, '5678 South Ave, Los Angeles, CA 90001', 'https://example.com/avatar5.png', 1);

INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0917982236', 'rK$2+Yp8hQ7!', 'Staff', 'patrickcook@russell.org', 'Christopher', 'Hernandez', 1, '299 Randall Neck Apt. 275, New Tracy, RI 12849', 'https://example.com/avatar6.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0928126734', 'N#5wRs8!qZ0L', 'Customer', 'sarahmills@gmail.com', 'Sarah', 'Mills', 0, '4821 Warren View Suite 872, East Kevin, CO 80423', 'https://example.com/avatar7.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0937284561', '8dQ!$r7GhT2P', 'Admin', 'brianadams@yahoo.com', 'Brian', 'Adams', 1, 'PO Box 423, Lake Anthony, TX 75182', 'https://example.com/avatar8.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0945678923', 'Lp#9Xq$1aG7E', 'Customer', 'emilyjohnson@outlook.com', 'Emily', 'Johnson', 0, '1200 Perry Park, Suite 100, Miami, FL 33101', 'https://example.com/avatar9.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0956781234', 'Yt2@!nW8sE4$', 'Staff', 'roberthall@company.com', 'Robert', 'Hall', 1, '233 Howard Lane, Apt. 45, Denver, CO 80205', 'https://example.com/avatar10.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0962345678', 'Qe7!jR2x@M5P', 'Customer', 'angelathomas@hotmail.com', 'Angela', 'Thomas', 0, 'Box 234, Station A, Toronto, ON M4B1B3', 'https://example.com/avatar11.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0978765432', 'Vr!5Xp9#Qm8L', 'Admin', 'michaelroberts@gmail.com', 'Michael', 'Roberts', 1, 'Suite 500, 77 Queen St, London, UK', 'https://example.com/avatar12.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0987654321', 'Bn@4Lk7!pQ2E', 'Customer', 'sophiabrown@yahoo.com', 'Sophia', 'Brown', 0, '89 West Street, New York, NY 10001', 'https://example.com/avatar13.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0991122334', 'Wq3$De6!jT8O', 'Staff', 'williamwhite@company.com', 'William', 'White', 1, '56 Oak Avenue, Chicago, IL 60610', 'https://example.com/avatar14.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0912345679', 'Ht!2xP7@Lk9V', 'Customer', 'oliviawilson@gmail.com', 'Olivia', 'Wilson', 0, '22 Elm Road, San Francisco, CA 94107', 'https://example.com/avatar15.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0923456780', 'Js9!pL4@Wq2T', 'Admin', 'jamesmartin@outlook.com', 'James', 'Martin', 1, '901 Lake Drive, Dallas, TX 75201', 'https://example.com/avatar16.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0934567891', 'Zt8!qR5@Mn3L', 'Customer', 'isabellagarcia@yahoo.com', 'Isabella', 'Garcia', 0, '77 River Street, Boston, MA 02108', 'https://example.com/avatar17.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0945678902', 'Pl7!wE6@Gh4K', 'Staff', 'daniellee@company.com', 'Daniel', 'Lee', 1, '304 Maple Blvd, Houston, TX 77002', 'https://example.com/avatar18.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0956789013', 'Xc6!aS7@Vb5N', 'Customer', 'miahernandez@gmail.com', 'Mia', 'Hernandez', 0, '105 Pine Avenue, Seattle, WA 98101', 'https://example.com/avatar19.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0967890124', 'Rz5!oD8@Kl6J', 'Admin', 'ethanjackson@hotmail.com', 'Ethan', 'Jackson', 1, '502 Birch St, Atlanta, GA 30303', 'https://example.com/avatar20.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0978901235', 'Vb4!nF9@Xq7H', 'Customer', 'amelialopez@gmail.com', 'Amelia', 'Lopez', 0, 'Suite 200, 345 Cedar Ave, Austin, TX 73301', 'https://example.com/avatar21.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0989012346', 'Ty3!kG0@Zp8M', 'Staff', 'alexanderscott@company.com', 'Alexander', 'Scott', 1, 'Unit 4, 900 Walnut Lane, Philadelphia, PA 19103', 'https://example.com/avatar22.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0990123457', 'Ui2!mH1@Cr9N', 'Customer', 'charlotteking@yahoo.com', 'Charlotte', 'King', 0, 'PO Box 1234, Nashville, TN 37201', 'https://example.com/avatar23.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0911234568', 'Op1!lJ2@Bs0K', 'Admin', 'danieldavis@gmail.com', 'Daniel', 'Davis', 1, 'Main St 56, Phoenix, AZ 85001', 'https://example.com/avatar24.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0922345679', 'Yh9!eK3@Gt1L', 'Customer', 'abigailmartinez@hotmail.com', 'Abigail', 'Martinez', 0, '789 Market Square, Orlando, FL 32801', 'https://example.com/avatar25.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0933456780', 'Df8!rL4@Qw2M', 'Staff', 'loganthompson@company.com', 'Logan', 'Thompson', 1, 'Suite 450, 2100 Broadway, Denver, CO 80202', 'https://example.com/avatar26.png', 0);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0944567891', 'Gh7!tM5@Er3N', 'Customer', 'harperclark@gmail.com', 'Harper', 'Clark', 0, '11 Sunset Blvd, Los Angeles, CA 90028', 'https://example.com/avatar27.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0955678902', 'Jk6!yN6@Wt4O', 'Admin', 'lucasmartin@yahoo.com', 'Lucas', 'Martin', 1, '44 Ocean Drive, Miami, FL 33139', 'https://example.com/avatar28.png', 1);
INSERT INTO Account (PhoneNumber, Password, Role, Email, FirstName, LastName, Gender, Address, Avatar, Enabled) VALUES ('0966789013', 'Lm5!uO7@Yp5P', 'Customer', 'evelynrodriguez@gmail.com', 'Evelyn', 'Rodriguez', 0, '22 Hillcrest Ave, San Diego, CA 92103', 'https://example.com/avatar29.png', 0);
