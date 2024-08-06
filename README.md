# CineManager

![CineManager Logo](https://via.placeholder.com/150x50.png?text=CineManager) <!-- 로고 이미지를 사용 -->

**CineManager**는 영화 정보 등록 및 관리 시스템입니다. 이 콘솔 애플리케이션은 사용자가 영화 정보를 체계적으로 관리하고, 영화에 대한 후기를 추가 및 조회할 수 있게 도와줍니다. 본 시스템은 영화 웹 페이지의 관리자 영화 정보 관리 시스템 및 사용자 후기 페이지, 영화 커뮤니티 사이트 등 다양한 용도로 활용될 수 있습니다.

## 🛠️ 개발 환경

- **프로그래밍 언어**: Java
- **IDE**: Eclipse
- **DBMS**: Oracle Database
- **SQL 툴**: SQL Developer
- **버전 관리**: GitHub
- **협업 도구**: Notion

## 👥 개발 인원

- **조강민** (팀장)
- **박채연**
- **오혜진**

## ✨ 주요 기능

1. **회원 가입**
   - **정보**: 이름, 아이디, 비밀번호, 전화번호
   - **SQL 테이블**:
     ```sql
     CREATE TABLE members (
         name VARCHAR2(15) NOT NULL,
         id VARCHAR2(30) PRIMARY KEY,
         pwd VARCHAR2(50) NOT NULL,
         phone VARCHAR2(20) UNIQUE
     );
     ```

2. **로그인**
   - **입력**: 아이디, 비밀번호

3. **회원 정보 수정**
   - **수정**: 가입한 아이디와 비밀번호로 인증 후 정보 수정

4. **회원 탈퇴**
   - **인증**: 회원의 아이디와 비밀번호 입력

5. **영화 정보 추가**
   - **정보**: 등록 코드, 제목, 감독, 장르, 개봉일, 시놉시스, 시청 유무
   - **SQL 테이블**:
     ```sql
     CREATE TABLE movies (
         code NUMBER PRIMARY KEY,
         title VARCHAR2(100) NOT NULL,
         director VARCHAR2(100) NOT NULL,
         genre VARCHAR2(50) NOT NULL,
         release_date DATE NOT NULL,
         synopsis CLOB,
         user_id VARCHAR2(30) NOT NULL,
         watched CHAR(1) DEFAULT 'N',
         FOREIGN KEY (user_id) REFERENCES members(id) ON DELETE CASCADE
     );
     ```

6. **영화 조회**
   - **전체 조회** 및 제목, 등록 코드로 상세 조회
   - **SQL 쿼리 예시**:
     ```sql
     SELECT * FROM movies;
     SELECT * FROM movies WHERE title = :title OR code = :code;
     ```

7. **영화 수정**
   - **항목**: 제목, 감독, 장르, 개봉일, 시놉시스
   - **SQL 쿼리 예시**:
     ```sql
     UPDATE movies
     SET title = :title, director = :director, genre = :genre, release_date = :release_date, synopsis = :synopsis
     WHERE code = :code;
     ```

8. **영화 삭제**
   - **SQL 쿼리 예시**:
     ```sql
     DELETE FROM movies WHERE code = :code;
     ```

9. **후기 추가**
   - **정보**: 후기 제목, 등록 코드
   - **SQL 테이블**:
     ```sql
     CREATE TABLE review (
         review_id NUMBER PRIMARY KEY,
         movie_code NUMBER,
         user_id VARCHAR2(30),
         review CLOB,
         logdate DATE,
         FOREIGN KEY (movie_code) REFERENCES movies(code) ON DELETE CASCADE,
         FOREIGN KEY (user_id) REFERENCES members(id) ON DELETE CASCADE
     );
     CREATE SEQUENCE review_id_seq
         START WITH 1
         INCREMENT BY 1
         NOCACHE
         NOCYCLE;
     ```

10. **후기 조회**
    - **전체 회원의 후기 조회** 및 제목으로 검색
    - **SQL 쿼리 예시**:
      ```sql
      SELECT m.title AS 제목, m.director AS 감독, r.review AS 리뷰, r.user_id AS 유저아이디, TO_CHAR(r.logdate, 'YYYY-MM-DD HH24:MI:SS') AS 등록날짜
      FROM review r
      JOIN movies m ON r.movie_code = m.code
      WHERE r.movie_code = :movie_code;
      ```

11. **후기 수정**
    - **SQL 쿼리 예시**:
      ```sql
      UPDATE review
      SET review = :review
      WHERE review_id = :review_id;
      ```

12. **후기 삭제**
    - **SQL 쿼리 예시**:
      ```sql
      DELETE FROM review WHERE review_id = :review_id;
      ```

## 📈 코드 수정 & 협업

- **회원가입, 회원탈퇴** 에러 수정
- **영화 수정 및 삭제** 기능 개선
- **후기 작성 시간** 한국시간으로 변경
- **후기 등록 시** 개봉일 확인 및 예외 처리
- **영화 및 후기 관련** 예외 처리 및 기능 추가

## 🤝 기여

본 프로젝트는 오픈 소스 프로젝트로, 기여를 원하시는 분은 [GitHub 리포지토리](https://github.com/your-repo)에서 문제를 보고하거나 Pull Request를 통해 기여해 주세요.

---

이 `README.md` 파일은 `CineManager`의 구조와 기능을 이해하는 데 도움이 될 것입니다. 필요한 경우, 추가적인 정보와 자세한 설명을 포함하여 프로젝트의 문서를 보강할 수 있습니다.

