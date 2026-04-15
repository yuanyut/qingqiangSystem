-- Update user_behavior table to add status and update_time fields
ALTER TABLE user_behavior
ADD COLUMN `status` INT DEFAULT 1 COMMENT '状态：1=有效，0=取消',
ADD COLUMN `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- Set default status to 1 for existing records
UPDATE user_behavior SET status = 1 WHERE status IS NULL;

-- Add indexes if needed
CREATE INDEX idx_user_behavior_status ON user_behavior(status);
CREATE INDEX idx_user_behavior_user_target_action ON user_behavior(user_id, target_type, target_id, action);
