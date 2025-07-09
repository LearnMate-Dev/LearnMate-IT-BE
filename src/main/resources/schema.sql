/**
  Courses
 */
CREATE INDEX IF NOT EXISTS idx_user_step_progress_user_completed_partial
    ON user_step_progress(user_id)
    WHERE completed_at IS NOT NULL;

CREATE INDEX IF NOT EXISTS idx_user_step_progress_user_step_type_completed_partial
    ON user_step_progress(user_id, step_type)
    WHERE completed_at IS NULL;

CREATE INDEX IF NOT EXISTS idx_user_step_progress_id_user
    ON user_step_progress(step_progress_id, user_id);

/**
  Chat
 */
CREATE INDEX IF NOT EXISTS idx_chat_chatroom_id
    ON chat(chat_room_id);

CREATE INDEX IF NOT EXISTS idx_chat_room_user_created_at_title_partial
    ON chat_room(user_id, created_at DESC)
    WHERE title IS NOT NULL;

 /**
   Diary
  */
CREATE INDEX IF NOT EXISTS idx_diary_user_created_diary
    ON diary(user_id, created_at ASC, diary_id);

