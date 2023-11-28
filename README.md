# spotifies

네, 주어진 Spring Controller를 바탕으로 API 문서를 작성하겠습니다.

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