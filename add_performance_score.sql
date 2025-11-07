

-- Add performance_score column to CPU table
ALTER TABLE cpu
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to GPU table
ALTER TABLE gpu
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to Mainboard table
ALTER TABLE mainboard
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to Memory table
ALTER TABLE memory
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to Storage table
ALTER TABLE storage
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to PSU table
ALTER TABLE psu
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to PCCase table
ALTER TABLE pccase
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- Add performance_score column to Cooling table
ALTER TABLE cooling
ADD COLUMN performance_score INT DEFAULT 50
CHECK (performance_score >= 0 AND performance_score <= 100);

-- ============================================
-- Create Indexes for Performance
-- ============================================

-- Index for faster filtering by score
CREATE INDEX idx_cpu_score ON cpu(performance_score);
CREATE INDEX idx_gpu_score ON gpu(performance_score);
CREATE INDEX idx_mainboard_score ON mainboard(performance_score);
CREATE INDEX idx_memory_score ON memory(performance_score);
CREATE INDEX idx_storage_score ON storage(performance_score);
CREATE INDEX idx_psu_score ON psu(performance_score);
CREATE INDEX idx_pccase_score ON pccase(performance_score);
CREATE INDEX idx_cooling_score ON cooling(performance_score);

-- Composite index for price + score queries (common in AI filtering)
CREATE INDEX idx_cpu_price_score ON cpu(price, performance_score);
CREATE INDEX idx_gpu_price_score ON gpu(price, performance_score);
CREATE INDEX idx_mainboard_price_score ON mainboard(price, performance_score);
CREATE INDEX idx_memory_price_score ON memory(price, performance_score);
CREATE INDEX idx_storage_price_score ON storage(price, performance_score);
CREATE INDEX idx_psu_price_score ON psu(price, performance_score);
CREATE INDEX idx_pccase_price_score ON pccase(price, performance_score);
CREATE INDEX idx_cooling_price_score ON cooling(price, performance_score);

-- ============================================
-- Sample Score Updates (Examples)
-- ============================================

-- CPU Examples
UPDATE cpu SET performance_score = 100 WHERE model LIKE '%i9-14900K%' OR model LIKE '%i9-14900KS%';
UPDATE cpu SET performance_score = 95 WHERE model LIKE '%i9-13900K%' OR model LIKE '%Ryzen 9 7950X%';
UPDATE cpu SET performance_score = 90 WHERE model LIKE '%i7-14700K%' OR model LIKE '%Ryzen 9 7900X%';
UPDATE cpu SET performance_score = 85 WHERE model LIKE '%i7-13700K%' OR model LIKE '%Ryzen 7 7700X%';
UPDATE cpu SET performance_score = 80 WHERE model LIKE '%i5-14600K%' OR model LIKE '%Ryzen 7 5800X%';
UPDATE cpu SET performance_score = 75 WHERE model LIKE '%i5-13600K%' OR model LIKE '%Ryzen 5 7600X%';
UPDATE cpu SET performance_score = 70 WHERE model LIKE '%i5-12600K%' OR model LIKE '%Ryzen 5 5600X%';
UPDATE cpu SET performance_score = 60 WHERE model LIKE '%i5-12400%' OR model LIKE '%Ryzen 5 3600%';
UPDATE cpu SET performance_score = 50 WHERE model LIKE '%i3-%';

-- GPU Examples
UPDATE gpu SET performance_score = 100 WHERE chipset LIKE '%RTX 4090%';
UPDATE gpu SET performance_score = 95 WHERE chipset LIKE '%RTX 4080%' OR chipset LIKE '%RX 7900 XTX%';
UPDATE gpu SET performance_score = 90 WHERE chipset LIKE '%RTX 4070 Ti%' OR chipset LIKE '%RX 7900 XT%';
UPDATE gpu SET performance_score = 85 WHERE chipset LIKE '%RTX 4070%' OR chipset LIKE '%RX 7800 XT%';
UPDATE gpu SET performance_score = 80 WHERE chipset LIKE '%RTX 3090%' OR chipset LIKE '%RX 6900 XT%';
UPDATE gpu SET performance_score = 75 WHERE chipset LIKE '%RTX 4060 Ti%' OR chipset LIKE '%RTX 3080%';
UPDATE gpu SET performance_score = 70 WHERE chipset LIKE '%RTX 3070%' OR chipset LIKE '%RX 6800%';
UPDATE gpu SET performance_score = 65 WHERE chipset LIKE '%RTX 4060%' OR chipset LIKE '%RX 6700 XT%';
UPDATE gpu SET performance_score = 60 WHERE chipset LIKE '%RTX 3060%' OR chipset LIKE '%RX 6600 XT%';
UPDATE gpu SET performance_score = 50 WHERE chipset LIKE '%RTX 3050%' OR chipset LIKE '%GTX 1660%';
UPDATE gpu SET performance_score = 40 WHERE chipset LIKE '%GTX 1650%';

-- Memory Examples (based on capacity and speed)
UPDATE memory SET performance_score = 95 WHERE capacity LIKE '%64GB%' AND speed LIKE '%6000%';
UPDATE memory SET performance_score = 90 WHERE capacity LIKE '%32GB%' AND speed LIKE '%6000%';
UPDATE memory SET performance_score = 85 WHERE capacity LIKE '%32GB%' AND speed LIKE '%5200%';
UPDATE memory SET performance_score = 80 WHERE capacity LIKE '%32GB%' AND speed LIKE '%4800%';
UPDATE memory SET performance_score = 75 WHERE capacity LIKE '%16GB%' AND speed LIKE '%6000%';
UPDATE memory SET performance_score = 70 WHERE capacity LIKE '%16GB%' AND speed LIKE '%3600%';
UPDATE memory SET performance_score = 65 WHERE capacity LIKE '%16GB%' AND speed LIKE '%3200%';
UPDATE memory SET performance_score = 60 WHERE capacity LIKE '%8GB%' AND speed LIKE '%3200%';

-- Storage Examples
UPDATE storage SET performance_score = 95 WHERE type LIKE '%NVMe%' AND capacity LIKE '%2TB%' AND cache LIKE '%7000%';
UPDATE storage SET performance_score = 85 WHERE type LIKE '%NVMe%' AND capacity LIKE '%1TB%' AND cache LIKE '%5000%';
UPDATE storage SET performance_score = 75 WHERE type LIKE '%NVMe%' AND capacity LIKE '%1TB%' AND cache LIKE '%3500%';
UPDATE storage SET performance_score = 65 WHERE type LIKE '%SSD%' AND capacity LIKE '%1TB%';
UPDATE storage SET performance_score = 55 WHERE type LIKE '%SSD%' AND capacity LIKE '%500GB%';
UPDATE storage SET performance_score = 45 WHERE type LIKE '%HDD%' AND capacity LIKE '%2TB%';

-- PSU Examples
UPDATE psu SET performance_score = 95 WHERE wattage LIKE '%1000W%' AND efficiency LIKE '%Platinum%';
UPDATE psu SET performance_score = 90 WHERE wattage LIKE '%1000W%' AND efficiency LIKE '%Gold%';
UPDATE psu SET performance_score = 85 WHERE wattage LIKE '%850W%' AND efficiency LIKE '%Gold%';
UPDATE psu SET performance_score = 80 WHERE wattage LIKE '%750W%' AND efficiency LIKE '%Gold%';
UPDATE psu SET performance_score = 75 WHERE wattage LIKE '%750W%' AND efficiency LIKE '%Bronze%';
UPDATE psu SET performance_score = 70 WHERE wattage LIKE '%650W%' AND efficiency LIKE '%Gold%';
UPDATE psu SET performance_score = 65 WHERE wattage LIKE '%650W%' AND efficiency LIKE '%Bronze%';
UPDATE psu SET performance_score = 60 WHERE wattage LIKE '%550W%';

-- Case Examples (simpler, based on size)
UPDATE pccase SET performance_score = 85 WHERE formfactor LIKE '%Full Tower%';
UPDATE pccase SET performance_score = 75 WHERE formfactor LIKE '%Mid Tower%';
UPDATE pccase SET performance_score = 65 WHERE formfactor LIKE '%Micro ATX%';
UPDATE pccase SET performance_score = 60 WHERE formfactor LIKE '%Mini ITX%';

-- Cooling Examples
UPDATE cooling SET performance_score = 95 WHERE radiatorsize >= 360;
UPDATE cooling SET performance_score = 85 WHERE radiatorsize >= 280 AND radiatorsize < 360;
UPDATE cooling SET performance_score = 75 WHERE radiatorsize >= 240 AND radiatorsize < 280;
UPDATE cooling SET performance_score = 70 WHERE radiatorsize >= 120 AND radiatorsize < 240;
UPDATE cooling SET performance_score = 65 WHERE radiatorsize = 0; -- Air coolers

-- ============================================
-- Verification Queries
-- ============================================

-- Check score distribution for each component type
SELECT
    'CPU' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM cpu
UNION ALL
SELECT
    'GPU' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM gpu
UNION ALL
SELECT
    'Mainboard' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM mainboard
UNION ALL
SELECT
    'Memory' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM memory
UNION ALL
SELECT
    'Storage' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM storage
UNION ALL
SELECT
    'PSU' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM psu
UNION ALL
SELECT
    'Case' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM pccase
UNION ALL
SELECT
    'Cooling' as component,
    COUNT(*) as total,
    AVG(performance_score) as avg_score,
    MIN(performance_score) as min_score,
    MAX(performance_score) as max_score
FROM cooling;

-- ============================================
-- Test Query: Find products by AI suggestion
-- ============================================

-- Example: Find CPUs for gaming-high build
-- Budget: $550, Score: 80-95
SELECT model, price, performance_score
FROM cpu
WHERE price <= 550
  AND performance_score >= 80
  AND performance_score <= 95
ORDER BY performance_score DESC, price ASC
LIMIT 10;

-- Example: Find GPUs for gaming-high build
-- Budget: $1100, Score: 85-100
SELECT chipset, memory, price, performance_score
FROM gpu
WHERE price <= 1100
  AND performance_score >= 85
  AND performance_score <= 100
ORDER BY performance_score DESC, price ASC
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

