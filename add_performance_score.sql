

-- ============================================
-- SOLUTION: Add performance_score to PRODUCT table (Parent table)
-- ============================================
-- This is BETTER because:
-- 1. Only need to add column to 1 table instead of 8 tables
-- 2. Easier to query (no need to join multiple tables)
-- 3. Consistent with current design (product is parent table)
-- 4. AI can query directly from product without knowing component type
-- ============================================

ALTER TABLE product
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- ============================================
-- Create Indexes for Performance
-- ============================================

-- Index for faster filtering by score
CREATE INDEX idx_product_score ON product(performance_score);

-- Composite index for price + score queries (common in AI filtering)
CREATE INDEX idx_product_price_score ON product(price, performance_score);

-- Composite index for category + score (useful for filtering by component type)
CREATE INDEX idx_product_category_score ON product(category_id, performance_score);

-- Composite index for category + price + score (optimal for AI queries)
CREATE INDEX idx_product_category_price_score ON product(category_id, price, performance_score);

-- ============================================
-- Sample Score Updates (Examples)
-- ============================================

-- CPU Examples
UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 100
WHERE p.product_name LIKE '%i9-14900K%' OR p.product_name LIKE '%i9-14900KS%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%i9-13900K%' OR p.product_name LIKE '%Ryzen 9 7950X%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 90
WHERE p.product_name LIKE '%i7-14700K%' OR p.product_name LIKE '%Ryzen 9 7900X%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%i7-13700K%' OR p.product_name LIKE '%Ryzen 7 7700X%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 80
WHERE p.product_name LIKE '%i5-14600K%' OR p.product_name LIKE '%Ryzen 7 5800X%' OR p.product_name LIKE '%Ryzen 7 7800X3D%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%i5-13600K%' OR p.product_name LIKE '%Ryzen 5 7600X%' OR p.product_name LIKE '%Ryzen 5 7600%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%i5-12600K%' OR p.product_name LIKE '%Ryzen 5 5600X%' OR p.product_name LIKE '%Ryzen 5 7500F%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%i5-12400%' OR p.product_name LIKE '%Ryzen 5 3600%' OR p.product_name LIKE '%Ryzen 5 5500%' OR p.product_name LIKE '%Ryzen 5 4600G%';

UPDATE product p
JOIN cpu c ON p.product_id = c.product_id
SET p.performance_score = 50
WHERE p.product_name LIKE '%i3-%' OR p.product_name LIKE '%Ryzen 3%';

-- GPU Examples
UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 100
WHERE p.product_name LIKE '%RTX 4090%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%RTX 4080%' OR p.product_name LIKE '%RX 7900 XTX%' OR p.product_name LIKE '%RX 6950 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 90
WHERE p.product_name LIKE '%RTX 4070 Ti%' OR p.product_name LIKE '%RX 7900 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%RTX 4070%' OR p.product_name LIKE '%RX 7800 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 80
WHERE p.product_name LIKE '%RTX 3090%' OR p.product_name LIKE '%RX 6900 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%RTX 4060 Ti%' OR p.product_name LIKE '%RTX 3080%' OR p.product_name LIKE '%RX 7700 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%RTX 3070%' OR p.product_name LIKE '%RX 6800%' OR p.product_name LIKE '%RX 6700 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%RTX 4060%' OR p.product_name LIKE '%RX 7600%' OR p.product_name LIKE '%RX 6600 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%RTX 3060%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 50
WHERE p.product_name LIKE '%RTX 3050%' OR p.product_name LIKE '%GTX 1660%' OR p.product_name LIKE '%RX 6500 XT%' OR p.product_name LIKE '%RX 6400%' OR p.product_name LIKE '%RX 5600 XT%';

UPDATE product p
JOIN gpu g ON p.product_id = g.product_id
SET p.performance_score = 40
WHERE p.product_name LIKE '%GTX 1650%' OR p.product_name LIKE '%RX 5500 XT%';

-- Memory Examples (based on capacity and speed)
UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%64GB%' AND p.product_name LIKE '%6000%';

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 90
WHERE p.product_name LIKE '%32GB%' AND p.product_name LIKE '%6000%';

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%32GB%' AND (p.product_name LIKE '%5200%' OR p.product_name LIKE '%5600%');

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 80
WHERE p.product_name LIKE '%32GB%' AND p.product_name LIKE '%4800%';

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%16GB%' AND (p.product_name LIKE '%6000%' OR p.product_name LIKE '%5600%');

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%16GB%' AND p.product_name LIKE '%3600%';

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%16GB%' AND p.product_name LIKE '%3200%';

UPDATE product p
JOIN memory m ON p.product_id = m.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%8GB%' AND p.product_name LIKE '%3200%';

-- Storage Examples
UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%2TB%' AND (p.product_name LIKE '%7000%' OR p.product_name LIKE '%7400%');

UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%1TB%' AND (p.product_name LIKE '%5000%' OR p.product_name LIKE '%7000%');

UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%NVMe%' AND p.product_name LIKE '%1TB%' AND p.product_name LIKE '%3500%';

UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%SSD%' AND p.product_name LIKE '%1TB%';

UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 55
WHERE p.product_name LIKE '%SSD%' AND p.product_name LIKE '%500GB%';

UPDATE product p
JOIN storage s ON p.product_id = s.product_id
SET p.performance_score = 45
WHERE p.product_name LIKE '%HDD%' AND p.product_name LIKE '%2TB%';

-- PSU Examples
UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%1000W%' AND p.product_name LIKE '%Platinum%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 90
WHERE p.product_name LIKE '%1000W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%850W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 80
WHERE p.product_name LIKE '%750W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%750W%' AND p.product_name LIKE '%Bronze%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%650W%' AND p.product_name LIKE '%Gold%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%650W%' AND p.product_name LIKE '%Bronze%';

UPDATE product p
JOIN psu ps ON p.product_id = ps.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%550W%';

-- Case Examples (simpler, based on size)
UPDATE product p
JOIN pccase pc ON p.product_id = pc.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%Full Tower%';

UPDATE product p
JOIN pccase pc ON p.product_id = pc.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%Mid Tower%' OR p.product_name LIKE '%ATX%';

UPDATE product p
JOIN pccase pc ON p.product_id = pc.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%Micro%';

UPDATE product p
JOIN pccase pc ON p.product_id = pc.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%Mini%' OR p.product_name LIKE '%ITX%';

-- Cooling Examples
UPDATE product p
JOIN cooling co ON p.product_id = co.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%360%' OR p.product_name LIKE '%420%';

UPDATE product p
JOIN cooling co ON p.product_id = co.product_id
SET p.performance_score = 85
WHERE p.product_name LIKE '%280%';

UPDATE product p
JOIN cooling co ON p.product_id = co.product_id
SET p.performance_score = 75
WHERE p.product_name LIKE '%240%';

UPDATE product p
JOIN cooling co ON p.product_id = co.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%120%' OR p.product_name LIKE '%140%';

UPDATE product p
JOIN cooling co ON p.product_id = co.product_id
SET p.performance_score = 65
WHERE p.product_name LIKE '%Air%' OR p.product_name LIKE '%Tower%';

-- Mainboard Examples (based on chipset tier)
UPDATE product p
JOIN mainboard mb ON p.product_id = mb.product_id
SET p.performance_score = 95
WHERE p.product_name LIKE '%X670E%' OR p.product_name LIKE '%Z790%';

UPDATE product p
JOIN mainboard mb ON p.product_id = mb.product_id
SET p.performance_score = 90
WHERE p.product_name LIKE '%B650E%' OR p.product_name LIKE '%Z690%' OR p.product_name LIKE '%X570%';

UPDATE product p
JOIN mainboard mb ON p.product_id = mb.product_id
SET p.performance_score = 80
WHERE p.product_name LIKE '%B550%' OR p.product_name LIKE '%B760%';

UPDATE product p
JOIN mainboard mb ON p.product_id = mb.product_id
SET p.performance_score = 70
WHERE p.product_name LIKE '%B450%' OR p.product_name LIKE '%H610%';

UPDATE product p
JOIN mainboard mb ON p.product_id = mb.product_id
SET p.performance_score = 60
WHERE p.product_name LIKE '%A620%' OR p.product_name LIKE '%H510%';

-- ============================================
-- Verification Queries
-- ============================================

-- Check score distribution for each component type
SELECT
    'CPU' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN cpu c ON p.product_id = c.product_id
UNION ALL
SELECT
    'GPU' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN gpu g ON p.product_id = g.product_id
UNION ALL
SELECT
    'Mainboard' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN mainboard mb ON p.product_id = mb.product_id
UNION ALL
SELECT
    'Memory' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN memory m ON p.product_id = m.product_id
UNION ALL
SELECT
    'Storage' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN storage s ON p.product_id = s.product_id
UNION ALL
SELECT
    'PSU' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN psu ps ON p.product_id = ps.product_id
UNION ALL
SELECT
    'Case' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN pccase pc ON p.product_id = pc.product_id
UNION ALL
SELECT
    'Cooling' as component,
    COUNT(*) as total,
    AVG(p.performance_score) as avg_score,
    MIN(p.performance_score) as min_score,
    MAX(p.performance_score) as max_score
FROM product p
JOIN cooling co ON p.product_id = co.product_id;

-- ============================================
-- Test Query: Find products by AI suggestion
-- ============================================

-- Example: Find CPUs for gaming-high build
-- Budget: $550, Score: 80-95
SELECT p.product_name, p.price, p.performance_score
FROM product p
JOIN cpu c ON p.product_id = c.product_id
WHERE p.price <= 550
  AND p.performance_score >= 80
  AND p.performance_score <= 95
ORDER BY p.performance_score DESC, p.price ASC
LIMIT 10;

-- Example: Find GPUs for gaming-high build
-- Budget: $1100, Score: 85-100
SELECT p.product_name, p.price, p.performance_score
FROM product p
JOIN gpu g ON p.product_id = g.product_id
WHERE p.price <= 1100
  AND p.performance_score >= 85
  AND p.performance_score <= 100
ORDER BY p.performance_score DESC, p.price ASC
LIMIT 10;

-- ============================================
-- Notes:
-- ============================================
-- 1. Default score is 50 for all new products
-- 2. Score range is 0-100 (enforced by CHECK constraint)
-- 3. Indexes created for fast filtering by score and price+score
-- 4. Sample updates are examples - adjust based on actual data
-- 5. For products not matching any pattern, score remains at default 50
-- 6. You can update scores manually or use PerformanceScoreCalculator.java
-- ============================================
-- ============================================

