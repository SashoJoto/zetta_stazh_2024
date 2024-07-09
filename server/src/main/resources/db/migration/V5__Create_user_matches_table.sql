-- V5__Create_user_matches_table.sql
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'user_matches') THEN
        CREATE TABLE user_matches (
            user_id UUID NOT NULL,
            matched_user_id UUID NOT NULL,
            CONSTRAINT user_matches_pkey PRIMARY KEY (user_id, matched_user_id),
            CONSTRAINT user_matches_user_id_fkey FOREIGN KEY (user_id)
                REFERENCES user_ (id) ON DELETE CASCADE,
            CONSTRAINT user_matches_matched_user_id_fkey FOREIGN KEY (matched_user_id)
                REFERENCES user_ (id) ON DELETE CASCADE
        );
    END IF;
END $$;
