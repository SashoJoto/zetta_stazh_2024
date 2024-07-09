-- V2__Create_user_table.sql
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user_') THEN
        CREATE TABLE user_ (
            id UUID NOT NULL,
            profile_status VARCHAR(255) NOT NULL,
            date_of_birth DATE,
            description TEXT,
            address TEXT,
            phone_number TEXT,
            desired_min_age INTEGER,
            desired_max_age INTEGER,
            gender VARCHAR(255),
            desired_gender VARCHAR(255),
            CONSTRAINT user_pkey PRIMARY KEY (id)
        );
    END IF;
END $$;
