# 카카오페이 사전과제 - 카카오페이 뿌리기 기능 구현하기
## 목차
- [개발 환경](#개발-환경)
- [API 명세](#API-명세)
---

## 개발 환경
- 기본 환경
    - OS: Mac OS X
- Server
    - Java11
    - Spring Boot 2.3.1
    - JPA
    - H2
    - Gradle
    - Junit5
    
- 주의사항
    token 의 길이가 3자리수 문자열이 아닐 수 있습니다. 
    Encoder / Decoder 를 추상화하여 구현한수 있도록 설계 해놓았습니다.

## API-명세

### 1. 뿌리기 API
- Request

HTTP Header

"X-USER-ID" : value (Numeric)

```
http://localhost:8080/distribution
```

```
POST /distribution HTTP/1.1
```

- Response

```json
{
    "data": "1",
    "message": "뿌리기 생성이 완료되었습니다."
}

* data: 토큰값
```
#### 핵심 문제해결 전략
- 토큰 생성
  - 암호화 / 복호화 하는 부분을 추상화하여 확장성 있도록 설계

- 분배 전략
  - 뿌리기 실행 시 역동적인 결과가 나오도록 전략 구현
  - 전략패턴 적용으로 OCP 준수
  
### 2. 받기 API
- Request

HTTP Header

"X-USER-ID" : value (Numeric)

"X-ROOM-ID" : value (String)

```
http://localhost:8080/distribution/{token}/dividends
```

```
PUT /distribution/{token}/dividends HTTP/1.1
```

- Response

```json
{
    "data": 101,
    "message": "101원을 받으셨습니다!"
}
* data: 받은 액수  
```
#### 핵심 문제해결 전략
- 1 Request 1 Thread 환경에서 동시에 여러 Thread가 같은 DB Row를 바라볼 때 문제가 생기지 않도록 노력

  고려사항
  
  첫번째 방법: 큐를 이용한 순차적 요청처리 시도
  
  두번째 방법: Transaction 격리수준 제어
  
  세번째 방법: DB Lock 제어 - 선택
  읽기 성능보다는 데이터 무결성이 더 중요하다고 판단하여 비관적 락 사용으로 여러 Thread 에서 접근시 
  문제 없도록 처리 

### 3. 조회 API
- Request

HTTP Header

"X-USER-ID" : value (Numeric)

"X-ROOM-ID" : value (String)

```
http://localhost:8080/distribution/{token}
```

```
GET /distribution/{token} HTTP/1.1
```

- Response

```json
{
    "data": {
        "initialAmount": 1000,
        "distributedAmount": 684,
        "distributedAt": "2020-06-27T16:06:17.874434",
        "distributedHistory": [
            {
                "amount": 584,
                "receiverId": 2
            },
            {
                "amount": 100,
                "receiverId": 4
            }
        ]
    },
    "message": "뿌리기 조회가 완료되었습니다."
}
```
#### 핵심 문제해결 전략
- read 기능이기 때문에 Transaction 종료, flush 시점에 일어나는
스냅샷 비교, 쿼리문 생성등의 무거운 기능을 수행하지 않기 위해 읽기전용 Transaction 사용

- Lazy Loading 사용으로 검증 과정에서 예외가 발생할 경우 필요하지 않은 연관 Entity 를 조회하지 않음

