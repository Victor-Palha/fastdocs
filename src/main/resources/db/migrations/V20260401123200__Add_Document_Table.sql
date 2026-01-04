CREATE TYPE document_visibility AS ENUM ('PUBLIC', 'PRIVATE');

CREATE TABLE IF NOT EXISTS tb_documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fileName VARCHAR(255) NOT NULL,
    url VARCHAR(255),
    mimeType VARCHAR(100) NOT NULL,
    fileSize BIGINT NOT NULL,
    visibility document_visibility NOT NULL,
    companyId UUID NOT NULL
)