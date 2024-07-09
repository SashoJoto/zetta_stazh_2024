DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'recommended_users') THEN
        CREATE TABLE recommended_users (
            user_id UUID NOT NULL,
            recommended_user_id UUID NOT NULL,
            CONSTRAINT recommended_users_pkey PRIMARY KEY (user_id, recommended_user_id),
            CONSTRAINT recommended_users_user_id_fkey FOREIGN KEY (user_id)
                REFERENCES user_ (id) ON DELETE CASCADE,
            CONSTRAINT recommended_users_recommended_user_id_fkey FOREIGN KEY (recommended_user_id)
                REFERENCES user_ (id) ON DELETE CASCADE
        );
    END IF;
END $$;
