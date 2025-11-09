# BE
---
## Setting
- java 17
- springBoot 3.5.6
- gradle 8.14.3
- swagger 확인 URI localhost:8080/api-test 또는 localhost:8080/swagger-ui/index.html


## Security
- 민감 정보 .env 파일로 gitignore처리
- Spring Security를 통해 password 암호화
- JWT


## DB
- MySQL 


## 추가 설명
- 변수명은 camelCase로, 패키지명은 소문자, class 파일은 PascalCase로 작명
- 모든 api 통신을 위한 엔드포인트 앞은 /api로 requestMapping
- JPA와 MySQL 연결을 위한 세팅을 해둔 상태라서 해당 버전을 로컬 DB 세팅 없이 돌릴 시 error
- yaml 파일에 datasource와 jpa 부분
- build.gradle 파일에 jpa, DB 최신 적용 부분
- 위 파트 주석처리 후 빌드하면 정상 작동 but jpa 관련 사용 시 정상동작 x
