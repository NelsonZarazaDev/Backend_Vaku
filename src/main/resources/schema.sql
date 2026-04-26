CREATE TABLE IF NOT EXISTS audit_logs (
    audit_id BIGSERIAL PRIMARY KEY,
    http_method VARCHAR(10) NOT NULL,
    request_path VARCHAR(300) NOT NULL,
    query_string VARCHAR(1000),
    status_code INTEGER NOT NULL,
    success BOOLEAN NOT NULL,
    duration_ms BIGINT NOT NULL,
    client_ip VARCHAR(100),
    user_agent VARCHAR(700),
    actor_identifier VARCHAR(150),
    actor_role VARCHAR(120),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_audit_logs_created_at ON audit_logs(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_audit_logs_actor_identifier ON audit_logs(actor_identifier);
