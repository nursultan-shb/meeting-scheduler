
CREATE TABLE USERS
(
    id         UUID PRIMARY KEY,
    email      VARCHAR(255) UNIQUE NOT NULL,
    name       VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP           NOT NULL,
    version    INT  NOT NULL
);

CREATE TABLE TIME_SLOTS
(
    id         UUID PRIMARY KEY,
    user_id    UUID        NOT NULL,
    start_time TIMESTAMP   NOT NULL,
    end_time   TIMESTAMP   NOT NULL,
    status     VARCHAR(20) NOT NULL,
    meeting_id UUID NULL,

    version    INT      NOT NULL,
    created_at TIMESTAMP   NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE INDEX idx_slot_user_time
    ON time_slots(user_id, start_time, end_time);

CREATE INDEX idx_slot_user_status
    ON time_slots(user_id, status);

CREATE INDEX idx_slot_time_range
    ON time_slots(start_time, end_time);

CREATE TABLE MEETINGS (
    id UUID PRIMARY KEY,
    time_slot_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL,
    version  INT NOT NULL,

    FOREIGN KEY (time_slot_id) REFERENCES TIME_SLOTS (id)
);

CREATE TABLE MEETING_PARTICIPANTS (
    id UUID PRIMARY KEY,
    meeting_id UUID NOT NULL,
    user_id UUID NOT NULL,
    version INT NOT NULL,

    CONSTRAINT unique_meeting_id_user_id UNIQUE (meeting_id, user_id),

    FOREIGN KEY (meeting_id) REFERENCES meetings(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

ALTER TABLE TIME_SLOTS
ADD FOREIGN KEY (meeting_id) REFERENCES meetings (id);