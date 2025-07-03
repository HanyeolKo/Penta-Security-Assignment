# 전략패턴 기반 게시판 시스템

## 개요

이 프로젝트는 **전략패턴(Strategy Pattern)** 을 활용하여 **무한스크롤**과 **페이징** 두 가지 게시글 로딩 전략을 선택적으로 제공하는 게시판 리스트 시스템입니다.  
사용자는 UI에서 원하는 로딩 방식을 선택할 수 있으며, 백엔드는 전략 패턴을 적용하여 각 전략을 독립적으로 처리합니다.

---

## 기술 스택

- **백엔드**
  - Java 21
  - Spring Boot 3.x
  - Spring Data JPA
  - H2 Database (In-memory)
  - RESTful API
  - 전략패턴 설계 및 적용
  - Spring Rest Docs

- **프론트엔드**
  - React 18+
  - Material-UI (MUI)
  - Axios

---

## 주요 기능
- 게시글 리스트(목록) API
  - 필드: `id`, `title`, `content`, `author`, `createdAt`
- 전략패턴 기반의 로딩 방식 선택
  - **페이징(Paging)**
  - **무한스크롤(Infinite Scroll)**
- RESTful API, JSON 통신
- Spring Validation 적용 및 예외처리 (404/400)
- CORS 허용
- 프론트엔드 & 백엔드 통신 정상 작동
- 게시글 카드 형태 UI (MUI Card)

---

## 실행 방법
### 도커 컴포즈
``` (bash)
#루트 디렉토리에서
docker compose up --build
```
- 별도 환경설정없이 BE, FE 모두 한번에 실행 가능합니다.
- 프런트 페이지는 **http://{도커 주소}:3000** 로 접근 가능합니다.

## 테스트 커버리지
- 비즈니스 로직 테스트 커버리지 체크
![image](https://github.com/user-attachments/assets/2be118d9-ba93-4393-922b-8e2091024157)


## 기타
- 편의를 위해 실행시 100개의 샘플데이터가 자동으로 생성됩니다.
- 게시글 작성기능은 프런트에서 별도로 제공하지 않으나 (post) /post {title, content, author} api는 제공됩니다.

