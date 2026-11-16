# 🎮 내 인생 RPG

미루기 쉬운 일상의 할 일을 **퀘스트**로 등록하고, 완료하면 **경험치(EXP)**를 받아 캐릭터가 성장하는 게임형 ToDo 프로그램입니다.

## 팀원

| 이름 | 역할 | 주요 업무 |
| --- | --- | --- |
| 이해원 | DB | DB 설계, 퀘스트·캐릭터 데이터 저장/조회 |
| 이다은 | 메인로직 | 경험치·레벨업 계산, 퀘스트 완료 처리 |
| 노현승 | View | Swing GUI 화면 설계·구현 |
| 이채은 | 테스트/문서화 | 단위 테스트, 버그 트래킹, README·보고서·회의록 |

## 기술 스택

| 분야 | 기술 |
| --- | --- |
| 언어 | Java 17 이상 |
| IDE | Eclipse |
| 화면 | Java Swing |
| DB | MariaDB (관리 도구: HeidiSQL) |
| DB 연결 | JDBC (`mariadb-java-client`) |
| 테스트 | JUnit 5 |
| 빌드 | Gradle |

## 처음 실행하는 방법

1. **레포 받기 (Clone)**
   - Eclipse에서 `File → Import → Git → Projects from Git → Clone URI`
   - URI에 레포 주소를 붙여 넣고 GitHub 계정으로 로그인 → `main` 브랜치 선택 → 저장할 폴더 지정
   - 마지막 화면에서는 **Cancel**을 눌러도 됩니다. 프로젝트는 다음 단계에서 Gradle로 엽니다.
2. **Gradle 프로젝트로 열기**
   - `File → Import → Gradle → Existing Gradle Project`
   - 1번에서 받은 `life-rpg` 폴더를 선택 → `Finish`
   - MariaDB 드라이버와 JUnit이 자동으로 내려받아집니다.
   - 메뉴에 Gradle이 없다면 `Help → Eclipse Marketplace`에서 **Buildship**을 설치하세요.
3. **DB 만들기**: HeidiSQL에서 MariaDB에 접속한 뒤 `db/schema.sql`을 열어 전체 실행(F9)합니다. 테스트 데이터가 필요하면 `db/seed.sql`도 실행합니다.
   > `db` 폴더는 DB 담당(이해원)이 ERD를 확정한 뒤 올릴 예정입니다. 올라오기 전에는 이 단계를 건너뛰세요.
   > DB 이름은 `life_rpg`, 문자셋은 `utf8mb4`로 통일합니다.
4. **접속 정보 설정**: `src/main/resources/db.properties.example`을 같은 폴더에 `db.properties`로 복사하고 본인 MariaDB 비밀번호를 적습니다.
   > `db.properties`는 GitHub에 올라가지 않습니다. 비밀번호를 코드에 직접 쓰지 마세요.
5. **실행 확인**: `src/main/java/liferpg/Main.java` 우클릭 → `Run As → Java Application`. "내 인생 RPG" 창이 뜨면 성공입니다.
6. **테스트 실행**: `src/test/java` 폴더 우클릭 → `Run As → JUnit Test`. 초록색 막대가 뜨면 성공입니다.

> `build.gradle`을 수정한 뒤에는 프로젝트 우클릭 → `Gradle → Refresh Gradle Project`를 눌러 주세요.

## 폴더 구조

```
life-rpg/
├── db/                     (이해원 작성 예정)
│   ├── schema.sql          테이블 구조 (테이블을 바꾸면 이 파일도 수정)
│   └── seed.sql            테스트용 샘플 데이터
├── src/main/java/liferpg/
│   ├── Main.java           프로그램 시작점
│   ├── model/              데이터 그릇 (Quest, Awards, User)
│   ├── dao/                DB 저장·조회 (QuestDAO, AwardDAO, UserDAO, UserAbilityDAO)  - 이해원
│   ├── service/            게임 규칙 (Service)                                          - 이다은
│   └── view/               Swing 화면 (MainScreen, ListScreen)                          - 노현승
├── src/main/resources/
│   └── db.properties.example   DB 접속 정보 예시
└── src/test/java/liferpg/      JUnit 테스트                            - 이채은
```

화면(view)은 DB(dao)를 직접 부르지 않고 항상 service를 거칩니다.

## 협업 규칙

### 브랜치
- `main`: 항상 실행되는 코드만 둡니다. **직접 push하지 않습니다.**
- `feature/기능명`: 각자 작업하는 브랜치입니다. 예) `feature/quest-dao`, `feature/exp-service`
- 작업이 끝나면 Pull Request를 만들고, **팀원 1명 이상 확인**한 뒤 `main`에 합칩니다.

### 작업 순서 (Eclipse)

| 순서 | 하는 일 | Eclipse에서 |
| --- | --- | --- |
| 1 | 최신 코드 받기 | 프로젝트 우클릭 → `Team → Pull` |
| 2 | 내 브랜치 만들기 | 프로젝트 우클릭 → `Team → Switch To → New Branch...` → 이름에 `feature/기능명` 입력 |
| 3 | 코드 작성 | |
| 4 | 저장 기록 남기기 (commit) | `Window → Show View → Other → Git → Git Staging` 창에서 바뀐 파일을 **Staged Changes**로 옮기고 커밋 메시지 입력 |
| 5 | GitHub에 올리기 (push) | Git Staging 창에서 **Commit and Push** |
| 6 | 합쳐 달라고 요청하기 | GitHub 레포 페이지에 뜨는 **Compare & pull request** 버튼 → 내용 작성 → 팀원 1명 확인 후 Merge |

> 처음 push할 때 비밀번호 대신 GitHub **Personal Access Token**을 요구할 수 있습니다.
> GitHub `Settings → Developer settings → Personal access tokens`에서 만들어 비밀번호 칸에 붙여 넣으세요.

### 커밋 메시지

| 머리말 | 언제 | 예시 |
| --- | --- | --- |
| `feat` | 새 기능 | `feat: 난이도별 EXP 계산` |
| `fix` | 버그 수정 | `fix: 레벨업 경계값 오류 수정` |
| `test` | 테스트 추가·수정 | `test: ExpService 단위 테스트 추가` |
| `docs` | 문서 수정 | `docs: README 실행 방법 추가` |
| `refactor` | 동작은 같고 코드만 정리 | `refactor: QuestDAO 중복 코드 정리` |
| `chore` | 설정, 빌드 등 | `chore: Gradle 의존성 추가` |

## 게임 규칙

| 구분 | 내용 |
| --- | --- |
| 퀘스트 | 일일·일회성 할 일. 이름 / 카테고리 / 난이도를 입력 |
| 업적 | 반복해야 하는 일. 새로 등록하거나 기존 퀘스트에서 선택하고 목표 횟수를 설정 |
| 난이도별 EXP | 쉬움 10 / 보통 20 / 어려움 40 |
| 레벨업 | 다음 레벨까지 필요한 EXP = `100 × 현재 레벨` |
| 능력치 | 공부 → 지능, 운동 → 체력, 자기관리 → 청결도 |
| 미완료 | 패널티 없음 |

## MVP 범위

대시보드 → 퀘스트 추가 → 업적 → 완료 체크 → EXP 획득 → 레벨업

추가 기능(로그인, 친구 추가, 랭킹)은 이후 단계에서 진행합니다.
