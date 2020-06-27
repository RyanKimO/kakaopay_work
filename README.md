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
  - 전략패턴 적용으로 OCP 준수하려 노력
  
### 2. 받기 API
- Request

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
  비관적 락 사용 

### 3. 조회 API
- Request

```
http://localhost:8080/v1/pay/distribute/TK%
```

```
GET /v1/pay/distribute/TK% HTTP/1.1
```

- Response

```json
{
  "code": "00",
  "message": null,
  "body": {
    "amount": 12000,
    "userCount": 2,
    "regDate": "2020-06-27T13:00:06.864234",
    "amountReceive": 10321,
    "recipients": [
      {
        "userId": 3,
        "amount": 1686
      },
      {
        "userId": 2,
        "amount": 8635
      }
    ]
  }
}
```
#### 핵심 문제해결 전략
- 컨버터로 요구사항 데이터 컨버팅
  - distributeConverter
- checked Exception 으로 httpStatus OK 이지만 내부 규정 에러 처리
- invalid 한 토큰 httpStatus UNAUTHORIZED 리턴