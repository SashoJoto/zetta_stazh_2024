-- V6__Create_user_interests_table.sql
DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'user_interests') THEN
      CREATE TABLE user_interests (
          user_id UUID NOT NULL,
          interest_id BIGINT NOT NULL,
          CONSTRAINT user_interests_pkey PRIMARY KEY (user_id, interest_id),
          CONSTRAINT user_interests_user_id_fkey FOREIGN KEY (user_id)
              REFERENCES user_ (id) ON DELETE CASCADE,
          CONSTRAINT user_interests_interest_id_fkey FOREIGN KEY (interest_id)
              REFERENCES interest (id) ON DELETE CASCADE
      );
   END IF;
END $$;
