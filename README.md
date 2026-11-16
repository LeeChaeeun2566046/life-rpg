# 내 인생 RPG

미루기 쉬운 일상의 할 일을 **퀘스트**로 등록하고, 완료하면 경험치(EXP)를 받아 캐릭터가 성장하는 게임형 ToDo 프로그램입니다.

Java프로그래밍 및 실습Ⅱ 팀 프로젝트

## 팀원

- 이해원 - DB (DB 설계, 퀘스트/업적/유저 데이터 저장·조회)
- 이다은 - 메인 로직 (경험치, 레벨업, 능력치 계산, 퀘스트 완료 처리)
- 노현승 - 화면 (Swing GUI)
- 이채은 - 테스트/문서 (단위 테스트, 버그 정리, README, 보고서, 회의록)

## 사용 기술

Java 17, Swing, MariaDB(HeidiSQL), JDBC, JUnit 5, Gradle
IDE는 이클립스로 통일했습니다.

## 처음 받았을 때 세팅

**1. 레포 받기**

이클립스에서 `File > Import > Git > Projects from Git > Clone URI`로 들어가서 레포 주소를 넣고 main 브랜치를 받으면 됩니다.
마지막에 프로젝트 가져오는 화면이 나오는데 그냥 Cancel 눌러도 돼요. (다음 단계에서 Gradle로 엽니다)

**2. Gradle 프로젝트로 열기**

`File > Import > Gradle > Existing Gradle Project`에서 받은 `life-rpg` 폴더를 선택합니다.
MariaDB 드라이버랑 JUnit은 이때 자동으로 받아져요.
Gradle 메뉴가 안 보이면 `Help > Eclipse Marketplace`에서 Buildship 설치하면 됩니다.

**3. DB 만들기**

HeidiSQL로 MariaDB 접속해서 `db/schema.sql`을 실행(F9)하면 됩니다. 테스트용 데이터는 `db/seed.sql`.
- db 폴더는 해원님이 ERD 정리되면 올려 주실 예정이라 아직 없으면 넘어가세요.
- DB 이름은 `life_rpg`, 문자셋은 `utf8mb4`로 맞춰 주세요. (한글 깨짐 방지)

**4. DB 접속 정보 넣기**

레포를 받으면 `src/main/resources/` 안에 `db.properties.example`이 있어요.
 
1. 이 파일을 **복사**해서 같은 폴더에 `db.properties`라는 이름으로 붙여넣기
   (이클립스에서 복사 > 붙여넣기 하면 새 이름을 물어보는 창이 떠요)
2. `db.properties`를 열어서 본인 MariaDB 비밀번호 적기
3. commit할 때 `db.properties`는 .gitignore에 등록돼 있어서 자동으로 빠집니다

주의할 점
- example 파일 이름을 바꾸지 말고 복사해서 쓰기. (이름을 바꾸면 레포에서 example 파일이 지워져요)
- 비밀번호는 db.properties 파일에만 적어 주세요. 이 파일은 .gitignore에 등록돼 있어서 깃허브에 올라가지 않습니다. Java 코드 안에 비밀번호를 직접 쓰지 않도록 주의해 주세요. 

**5. 실행해 보기**

`Main.java` 우클릭 > `Run As > Java Application`
"내 인생 RPG" 창이 뜨면 세팅 끝입니다.

테스트는 `src/test/java` 우클릭 > `Run As > JUnit Test`로 돌리면 돼요.

참고로 build.gradle 고친 다음에는 프로젝트 우클릭 > `Gradle > Refresh Gradle Project` 한 번 눌러 줘야 반영됩니다.

## 폴더 구조

```
life-rpg/
├── db/                     schema.sql, seed.sql (해원님 작성 예정)
├── src/main/java/liferpg/
│   ├── Main.java
│   ├── model/              Quest, Awards, User
│   ├── dao/                QuestDAO, AwardDAO, UserDAO, UserAbilityDAO   (해원)
│   ├── service/            Service                                       (다은)
│   └── view/               MainScreen, ListScreen                        (현승)
├── src/main/resources/     db.properties.example
└── src/test/java/liferpg/  테스트 코드                                    (채은)
```

- model에는 필드랑 getter/setter만 두고, DB 코드는 dao에만 넣기로 했습니다.
- view에서 dao를 바로 부르지 말고 service를 거쳐서 쓰기.

## 깃허브 사용 규칙

main에는 직접 push하지 않고, 각자 브랜치에서 작업한 다음 PR로 합칩니다.
브랜치 이름은 `feature/기능명` 형태로 (예: `feature/quest-dao`, `feature/exp-service`)
PR은 본인 말고 두 명이 확인(Approve)한 다음에 merge 하게 했습니다.

이클립스에서는 대충 이 순서로 하면 됩니다.

1. 작업 시작 전에 프로젝트 우클릭 > `Team > Pull`로 최신 코드 받기
2. `Team > Switch To > New Branch`로 내 브랜치 만들기
3. 작업
4. `Git Staging` 창(`Window > Show View > Other > Git`)에서 바뀐 파일 Staged로 옮기고 커밋 메시지 쓰기
5. `Commit and Push`
6. 깃허브 레포에 들어가면 `Compare & pull request` 버튼이 떠 있음 → PR 작성

처음 push할 때 비밀번호 대신 토큰을 달라고 하면, 깃허브 `Settings > Developer settings > Personal access tokens`에서 만들어서 넣으면 됩니다.


## 게임 규칙 (1차 회의 기준)

- 퀘스트: 일일/일회성 할 일. 이름, 카테고리, 난이도 입력
- 업적: 반복해서 해야 하는 일. 반복해서 할 일을 새로 만들어서 목표 횟수 지정
- 경험치: 쉬움 10 / 보통 20 / 어려움 40
- 레벨업: 다음 레벨까지 필요한 경험치 = 100 × 현재 레벨, 최대 100LV까지
- 능력치: 공부 → 지능, 운동 → 체력, 자기관리 → 청결도 (추후에 카테고리를 추가해서 확장 할 수도 있음.)
- 퀘스트 못 해도 패널티는 없음

## 진행 범위

1차(중간보고서 목표): 메인 화면, 퀘스트 추가, 업적, 완료 체크, 경험치, 레벨업
이후: 로그인, 친구 추가, 랭킹
