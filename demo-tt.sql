CREATE TABLE users (
  id NUMBER(19,0) GENERATED ALWAYS AS IDENTITY NOT NULL,
  username VARCHAR2(255) NOT NULL,
  email VARCHAR2(255) NOT NULL,
  password VARCHAR2(255) NOT NULL,
  image VARCHAR2(255),
  role_id NUMBER(19, 0),
  enable NUMBER(1, 0),
  CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_username_uk UNIQUE (username),
    CONSTRAINT users_email_uk UNIQUE (email),
    CONSTRAINT users_fk_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE passwordresettoken(
  id NUMBER(19,0) GENERATED ALWAYS AS IDENTITY NOT NULL,
  token VARCHAR2(255) NOT NULL,
  user_id NUMBER(19, 0) NOT NULL,
  CONSTRAINT passwordresettoken_pk PRIMARY KEY (id),
  CONSTRAINT passwordresettoken_user_fk FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

ALTER TABLE users MODIFY id NUMBER(19,0) GENERATED ALWAYS AS IDENTITY START WITH 1;

CREATE TABLE role (
  id NUMBER(19,0) NOT NULL,
  name VARCHAR2(20),
  CONSTRAINT role_pk PRIMARY KEY (id)
);

INSERT INTO role VALUES (1, 'ROLE_USER');
INSERT INTO role VALUES (2, 'ROLE_ADMIN');

-- CREATE TABLE user_roles (
--     user_id NUMBER(19, 0) NOT NULL,
--     role_id NUMBER(19, 0) NOT NULL,
--     CONSTRAINT user_roles_pk PRIMARY KEY (user_id, role_id),
--     CONSTRAINT user_roles_user_fk FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
--     CONSTRAINT user_roles_role_fk FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
-- );

CREATE TABLE genre (
  id NUMBER(19,0) GENERATED ALWAYS AS IDENTITY NOT NULL,
  name VARCHAR2(255),
  enable NUMBER(1, 0),
  CONSTRAINT genre_pk PRIMARY KEY (id)
);

CREATE TABLE comic (
  id NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY NOT NULL,
  description CLOB,
  name VARCHAR2(255),
  publisher VARCHAR2(255),
  writer VARCHAR2(255),
  artist VARCHAR2(255),
  image VARCHAR2(255), -- Thêm trường image với kiểu dữ liệu CLOB
  status NUMBER(1,0),
  view_count NUMBER(25),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP,
  enable NUMBER(1, 0),
  CONSTRAINT comic_pk PRIMARY KEY (id)
);

CREATE TABLE chapter (
    id NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY NOT NULL,
    comic_id NUMBER(19, 0),
    comic_name VARCHAR2(25),
    name VARCHAR2(255),
    content VARCHAR2(25),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    enable NUMBER(1, 0),
    CONSTRAINT chapter_pk PRIMARY KEY(id),
    CONSTRAINT chapter_fk FOREIGN KEY(comic_id) REFERENCES comic(id) ON DELETE CASCADE
);


CREATE TABLE comic_genre (
  comic_id NUMBER(19, 0) NOT NULL,
  genre_id NUMBER(19, 0) NOT NULL,
  CONSTRAINT comic_genre_pk PRIMARY KEY (comic_id, genre_id),
  CONSTRAINT comic_genre_fk_comic FOREIGN KEY (comic_id) REFERENCES comic(id) ON DELETE CASCADE,
  CONSTRAINT comic_genre_fk_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

CREATE TABLE follow (
    user_id NUMBER(19, 0) NOT NULL,
    comic_id NUMBER(19, 0) NOT NULL,
  CONSTRAINT follow_pk PRIMARY KEY (comic_id, user_id),
  CONSTRAINT follow_fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT follow_fk_comic FOREIGN KEY (comic_id) REFERENCES comic(id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id NUMBER(19, 0) GENERATED ALWAYS AS IDENTITY NOT NULL,
    user_id NUMBER(19, 0) NOT NULL,
    comic_id NUMBER(19, 0) NOT NULL,
    comment_content CLOB NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT comments_pk PRIMARY KEY (id),
  CONSTRAINT comments_fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT comments_fk_comic FOREIGN KEY (comic_id) REFERENCES comic(id) ON DELETE CASCADE
);

CREATE TABLE image (
  id NUMBER(19,0)GENERATED ALWAYS AS IDENTITY NOT NULL,
  name VARCHAR2(255),
  type VARCHAR2(255),
  img_size NUMBER(19, 0),
  data BLOB,
  CONSTRAINT image_pk PRIMARY KEY(id),
);

CREATE TABLE chap_image (
  chap_id NUMBER(19, 0) NOT NULL,
  user_roles_pk NUMBER(19, 0) NOT NULL,
sortindec
  CONSTRAINT chap_image_pk PRIMARY KEY (chap_id, image_id),
  CONSTRAINT chap_image_fk_chap FOREIGN KEY (chap_id) REFERENCES chap(id) ON DELETE CASCADE,
  CONSTRAINT chap_image_fk_genre FOREIGN KEY (image_id) REFERENCES image(id) ON DELETE CASCADE
);