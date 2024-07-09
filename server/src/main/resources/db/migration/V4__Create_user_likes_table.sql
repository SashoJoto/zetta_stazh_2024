-- V4__Create_user_likes_table.sql
DO $$ BEGIN
   IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'user_likes') THEN
      CREATE TABLE user_likes (
          user_id UUID NOT NULL,
          liked_user_id UUID NOT NULL,
          CONSTRAINT user_likes_pkey PRIMARY KEY (user_id, liked_user_id),
          CONSTRAINT user_likes_user_id_fkey FOREIGN KEY (user_id)
              REFERENCES user_ (id) ON DELETE CASCADE,
          CONSTRAINT user_likes_liked_user_id_fkey FOREIGN KEY (liked_user_id)
              REFERENCES user_ (id) ON DELETE CASCADE
      );
   END IF;
END $$;
