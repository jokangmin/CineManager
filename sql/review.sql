-- review_id가 주키, movie_code & user_id : 외래키
CREATE TABLE review (
    review_id NUMBER PRIMARY KEY,
    movie_code NUMBER,
    user_id VARCHAR2(30),
    review CLOB,
    logdate DATE,
    FOREIGN KEY (movie_code) REFERENCES movies(code),
    FOREIGN KEY (user_id) REFERENCES members(id)
);

CREATE SEQUENCE review_id_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;