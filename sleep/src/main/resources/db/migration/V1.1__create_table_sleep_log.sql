CREATE TABLE sleep_log (
    id SERIAL PRIMARY KEY,
    sleep_date DATE NOT NULL,
    go_to_bed_time TIME NOT NULL,
    wake_up_time TIME NOT NULL,
    total_time_in_bed INTERVAL NOT NULL,
    morning_mood VARCHAR(20) NOT NULL,
    user_id INTEGER NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES sleep_user(id),
    CONSTRAINT unique_sleep_date_user UNIQUE (sleep_date, user_id)
);