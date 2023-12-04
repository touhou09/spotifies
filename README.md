# spotifies

## 개발환경
- jdk 20
- ojdbc 11
- spring boot 3.1.5 사용
- gradle로 dependency settings

## db setting
- 아래와 같은 환경에서 개발함

spring.datasource.url=jdbc:oracle:thin:@14.56.195.170:1521:orcl
spring.datasource.username=spotify
spring.datasource.password=oracle
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver

- 외부 데이터베이스에 연결해서 사용했으므로 실제로는 url을 localhost로 수정해야한다

 
# 실행시 주의사항
프로그램 실행시 http://localhost:8080/loginpage.html 페이지에서 시작한다.

일반 user 의 아이디는 user1,2,3 비밀번호는 password1,2,3 이다.

관리자의 아이디는 aa 비밀번호는 aaaaa 이다.

# API List

## UserController
* Base URL: `/users`
    
    1. **Login**
        - Endpoint: `POST /login`
        - Parameters: `userName` (String), `password` (String)
        - Response:
            - Success (200 OK): User 객체
            - Failure (401 Unauthorized):
    
    2. **Register User**
        - Endpoint: `POST /register`
        - Request Body: newUser (User 객체)
        - Response:
            - Success (201 Created): 등록된 User 객체
            - Failure (400 Bad Request):
    
    3. **Update User**
        - Endpoint: `PUT /update`
        - Request Body: updatedUser (User 객체)
        - Response:
            - Success (200 OK): 갱신된 User 객체
            - Failure (400 Bad Request):
    
    4. **Get User**
        - Endpoint: `GET /{userId}`
        - Parameters: `userId` (String)
        - Response: User 객체
    
    ## PlaylistController
    * Base URL: `/playlist`
    
    5. **Create Time Based Playlist**
        - Endpoint: `POST /randomcreate`
        - Parameters: `targetDuration` (int), `playlistName` (String), `userId` (String)
        - Response: 없음
    
    6. **Create Empty Playlist**
        - Endpoint: `POST /create-empty`
        - Parameters: `playlistName` (String), `userId` (String)
        - Response: 없음
    
    7. **Delete Playlist**
        - Endpoint: `DELETE /delete/{playlistId}`
        - Parameters: `playlistId` (int)
        - Response: 없음
    
    ## ChartController
    * Base URL: `/charts`
    
    8. **Get Top Songs**
        - Endpoint: `GET /top-songs/{timeFrame}`
        - Parameters: `timeFrame` (String)
        - Response: 가장 인기있는 노래 리스트 (Map 형태)
    
    9. **Get Top Followed**
        - Endpoint: `GET /top-followed`
        - Response: 가장 많이 팔로우 받은 사용자 리스트 (Map 형태)
    
    ## SearchController
    * Base URL: `/search`
    
    1. **Search Song**
       - Endpoint: `GET /song`
       - Parameters: `keyword` (String)
       - Response: 노래 검색 결과 List (SearchResult 객체를 항목으로 가짐)
    
    2. **Search Album**
       - Endpoint: `GET /album`
       - Parameters: `keyword` (String)
       - Response: 앨범 검색 결과 List (SearchResult 객체를 항목으로 가짐)
    
    3. **Search Artist**
       - Endpoint: `GET /artist`
       - Parameters: `keyword` (String)
       - Response: 아티스트 검색 결과 List (SearchResult 객체를 항목으로 가짐)
         
       -> 아래와 같이 수정   
    
       - 노래 검색: /search/songs?keyword=<키워드>
       - 앨범 검색: /search/albums?keyword=<키워드>
       - 아티스트 검색: /search/artists?keyword=<키워드>
       - 장르 검색: /search/genres?genre=<장르>
