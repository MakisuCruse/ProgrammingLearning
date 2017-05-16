CREATE TABLE tweet (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  tweet_id   BIGINT NOT NULL UNIQUE,
  user_id    BIGINT NOT NULL,
  text_info  BLOB,
  trend_name VARCHAR(100),
  create_at  DATETIME,
  latitude   VARCHAR(50),
  longitude  VARCHAR(50)
);


CREATE TABLE user (
  id                INT PRIMARY KEY AUTO_INCREMENT,
  user_id           BIGINT NOT NULL UNIQUE,
  screen_name       VARCHAR(100),
  description       BLOB,
  time_zone         VARCHAR(100),
  image_original    BLOB,
  follower_count    INT,
  user_status_count INT,
  location          BLOB,
  friend_count      INT,
  favorite_count    INT
)
  CHARACTER SET utf8
  COLLATE utf8_unicode_ci;


CREATE TABLE image (
  id    INT PRIMARY KEY  AUTO_INCREMENT,
  image BLOB
);