-- Check and create sequence if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relkind = 'S' AND relname = 'user_image_sequence') THEN
        CREATE SEQUENCE user_image_sequence
        START WITH 1
        INCREMENT BY 1
        NO MINVALUE
        NO MAXVALUE
        CACHE 1;
    END IF;
END $$;

-- Check and create table if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'user_image') THEN
        CREATE TABLE user_image (
            id BIGINT DEFAULT nextval('user_image_sequence') PRIMARY KEY,
            user_id BIGINT NOT NULL,
            image_url VARCHAR(255) NOT NULL,
            created_at TIMESTAMP NOT NULL DEFAULT NOW()
        );
    END IF;
END $$;
