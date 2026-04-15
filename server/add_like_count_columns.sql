-- Add like_count column to all relevant tables

-- Add to drama table
ALTER TABLE drama
ADD COLUMN `like_count` INT DEFAULT 0 COMMENT '点赞数';

-- Add to actor table
ALTER TABLE actor
ADD COLUMN `like_count` INT DEFAULT 0 COMMENT '点赞数';

-- Add to news table
ALTER TABLE news
ADD COLUMN `like_count` INT DEFAULT 0 COMMENT '点赞数';

-- Add to content table if not exists
ALTER TABLE content
ADD COLUMN `like_count` INT DEFAULT 0 COMMENT '点赞数';
