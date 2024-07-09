CREATE TABLE IF NOT EXISTS swiped_users (
    user_id UUID NOT NULL,
    swiped_user_id UUID NOT NULL,
    CONSTRAINT swiped_users_pkey PRIMARY KEY (user_id, swiped_user_id),
    CONSTRAINT swiped_users_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES user_ (id) ON DELETE CASCADE,
    CONSTRAINT swiped_users_swiped_user_id_fkey FOREIGN KEY (swiped_user_id)
        REFERENCES user_ (id) ON DELETE CASCADE
);
