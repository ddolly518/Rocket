# 🛠️ **스프링부트를 활용한 A/S 서비스**

## 🖥️ FixTrack
- **A/S 접수 및 처리 플랫폼**

---

## 🌟 프로젝트 소개

### ✅ 제작 목표
- A/S 센터에서 발생하는 고객 요청 및 수리 처리 내역을 체계적으로 **전산화**하여 업무 효율성과 고객 응대 품질을 향상시키고 처리 이력의 통계를 확보

---

## ⚙️ 시스템 개발 내용

- **고객**
    - 계정 및 인증관리
        - 회원가입 / 로그인 / 로그아웃
    - A/S
        - 자연어 기반 A/S 접수
        - 자연어 기반 고장 증상 분석 및 자동 카테고리화
        - A/S 접수 내역 조회
    - 고객 문의 관리
        - AI기반 고객 문의 처리 지원
    - OpenAI API 연동
        - Rate Limiting
        - 대화 컨텍스트 관리
        - SSE기반 스트리밍 응답
        - 비동기 처리
        - LLM 페르소나 & 프롬프트 엔지니어링 설계
- **관리자**

- **기술 스택**
    - Back-End
    	- Spring Boot
      - Spring Security
      - JPA
      - JWT
      - Vaildation
      - Lombok
      - PostgreSQL
      - Redis
      - OpenAI API 
    - Front-End
    	- React
        - Vite
    	- React Router
    	- Axios
        - HTML/CSS/JS
    - DevOps
    	- Railway
---

## 💡 아이디어 착안

- 전화, 메모, 엑셀 등으로 관리되던 A/S 업무를 체계화함으로써 누락 방지, 담당자 분배, 처리 추적 등 다양한 업무 과정을 일관된 방식으로 처리
- 통계(접수 수, 접수 처리 현황) 자동화 요구 증가

---
## :link: 배포 링크

> ### [⛪ FixTrack](https://rocket-client-production.up.railway.app)

---
## 🗣️ 프로젝트 발표 영상 & 발표 문서

> ### 🗓️ 2026.02.02 - 2026.02.27
> ### [📑 발표 PPT 링크](https://www.canva.com/design/DAHCUHP2Nuk/idcJGYsSCua3TdjQNFwMpQ/edit?utm_content=DAHCUHP2Nuk&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
> ### [📑 시연 영상 링크](https://drive.google.com/file/d/1mv1P67s9GKKUvJVR8UmfvtEYeRgbDm5R/view?usp=sharing)

---

## 🧰 사용 스택

### :wrench: System Architecture

<img src="https://drive.google.com/uc?export=view&id=1m5AwEd5X9whC5_3FMwR0JpDU0H_DyVmL"/>

---

## 📑 프로젝트 규칙

### Branch Strategy
> - main / develop 브랜치 기본 생성
> - main과 develop로 직접 push 제한

### Git Convention
> 1. 적절한 커밋 접두사 작성
> 2. 커밋 메시지 내용 작성
> 3. 내용 뒤에 이슈 (#이슈 번호)와 같이 작성하여 이슈 연결

> | 접두사        | 설명                           |
> | ------------- | ------------------------------ |
> | Feat :     | 새로운 기능 구현               |
> | Add :      | 에셋 파일 추가                 |
> | Fix :      | 버그 수정                      |
> | Docs :     | 문서 추가 및 수정              |
> | Style :    | 스타일링 작업                  |
> | Refactor : | 코드 리팩토링 (동작 변경 없음) |
> | Test :     | 테스트                         |
> | Deploy :   | 배포                           |
> | Conf :     | 빌드, 환경 설정                |
> | Chore :    | 기타 작업                      |


### Pull Request
> ### Title
> * 제목은 'feat: 홈 페이지 구현'과 같이 작성합니다.

> ### PR Type
> - [ ] FEAT: 새로운 기능 구현
> - [ ] ADD : 에셋 파일 추가
> - [ ] FIX: 버그 수정
> - [ ] DOCS: 문서 추가 및 수정
> - [ ] STYLE: 포맷팅 변경
> - [ ] REFACTOR: 코드 리팩토링
> - [ ] TEST: 테스트 관련
> - [ ] DEPLOY: 배포 관련
> - [ ] CONF: 빌드, 환경 설정
> - [ ] CHORE: 기타 작업

### Code Convention
>BE
> - 패키지명 전체 소문자
> - 클래스명, 인터페이스명 CamelCase
> - 클래스 이름 명사 사용
> - 상수명 SNAKE_CASE
> - Controller, Service, Dto, Repository, mapper 앞에 접미사로 통일(ex. MemberController)
> - service 계층 메서드명 create, update, find, delete로 CRUD 통일(ex. createMember)
> - Test 클래스는 접미사로 Test 사용(ex. memberFindTest)

> FE
> - Event handler 사용 (ex. handle ~)
> - export방식 (ex. export default ~)
> - 화살표 함수 사용

## :clipboard: Documents
> [📜 API 명세서](https://drive.google.com/file/d/17iNbOUqZdF7EafdaAAcDW2jBWqf-lUxW/view?usp=sharing)
>
> [📜 요구사항 정의서](https://drive.google.com/file/d/16AGwz_TPQuQmtvbpF-9Kqioh-ZfsTAr9/view?usp=sharing)
>
> [📜 ERD](https://drive.google.com/file/d/1m4USR1EQtoIPjALPM7rbmLQzQttkOVlk/view?usp=sharing)
