-- Oracle
CREATE TABLE movies (
    code NUMBER PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    director VARCHAR2(100) NOT NULL,
    genre VARCHAR2(50) NOT NULL,
    release_date DATE NOT NULL,
    synopsis CLOB
    user_id VARCHAR2(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES members(id) ON DELETE CASCADE -- 추가
);