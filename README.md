# gateway
모든 서비스 요청을 받아서 프레젠테이션 기능을 담당

## 요구사항
- Thymeleaf 템플릿 엔진을 사용하여 화면을 표시합니다.

- AccountApi와 TaskApi에서 데이터를 가져와서 서비스합니다.

- 사용자 인증을 담당합니다. 인증 세션은 Redis를 사용하여 관리합니다.

- 인증 데이터는 AccountApi를 사용합니다.
