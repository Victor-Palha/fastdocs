ALTER TABLE IF EXISTS tb_users ADD COLUMN IF NOT EXISTS company_id UUID NOT NULL;

CREATE INDEX IF NOT EXISTS idx_tb_users_company_id ON tb_users (company_id);