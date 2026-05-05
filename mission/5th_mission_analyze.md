# 오프라인 미션 해석과 ERD/API 변경점

이전 4주차때 매우 대충 ERD를 설계하고 시작했던 것 같아서, 다시한번 이미지 및 기능 요구사항을 보며 api 명세서 및 ERD를 수정하였다. 대충 테이블, 도메인 몇개 추가하고 끝날까 싶었는데, 해석 관점이  달라져서 정리해보려고한다.


## 1. 해석의 출발점

화면 정의서에서 미션 성공 요청 후 `사장님 구분 번호`를 보여주는 흐름이 있었다.

이 화면을 기준으로 보면 이 서비스의 미션은 단순 온라인 주문 기반 미션이라기보다, 회원이 오프라인 매장에서 실제로 식사한 뒤 사장님에게 인증을 받는 구조에 가깝다고 생각되었다.

따라서 기존의 `주문 기록(order)` 중심 설계보다, 다음 흐름이 더 자연스럽다고 판단했다.

```text  
회원 미션 도전  
-> 미션 성공 인증 요청  
-> 인증 번호 생성  
-> 사장님 확인  
-> 회원 미션 완료 처리  
-> 포인트 지급  
-> 완료된 회원 미션에 대해 리뷰 작성  
```  

화면 정의서만 보고 나에게 익숙한 배달 앱이라고 단정했던 것 같다. 그런데 식별 번호를 가져가고, 모든 미션에 리뷰하기가 달리는 형태를 보았을 때 이앱은 온라인이 아니라 오프라인을 겨냥하고 만들었다는 결론을 가지게 되었다.

## 2. 기존 해석과 달라진 점

### 2.1 주문 기록 중심 설계를 제거

처음에는 `mission - order - review` 흐름도 고려했다.

하지만 화면상 핵심은 주문 데이터가 아니라 사장님이 확인하는 인증 번호였다. 온라인 주문 앱이라면 주문 완료 로그만으로 미션 완료를 자동 판정할 수 있지만, 현재 화면은 오프라인 확인 절차를 전제로 한다.

그래서 별도의 `order_history` 테이블은 최종 ERD에서 제외했다.

### 2.2 미션과 회원 미션을 분리

`mission`은 운영자가 만들어 둔 미션 원본이다.

예시:

```text  
가게: 춘리마라탕  
조건: 10,000원 이상의 식사 시  
보상: 500P  
```  

`member_mission`은 특정 회원이 특정 미션에 도전한 기록이다.

예시:

```text  
member 1번이 mission 101번에 도전  
상태: IN_PROGRESS, SUCCESS_REQUESTED, COMPLETED 등  
```  

이 둘을 분리해야 회원의 진행 상태, 성공 요청 시각, 완료 시각, 취소/거절 상태를 안전하게 관리할 수 있다.

### 2.3 성공 요청과 완료를 분리

회원이 "성공 요청" 버튼을 누르는 시점은 미션 완료가 아니다.

성공 요청 시점에는 `member_mission.status`를 `SUCCESS_REQUESTED`로 변경하고, `member_mission_verification`에 인증 번호를 생성한다.

실제 완료는 사장님이 인증 번호를 확인한 뒤 처리된다.

```text  
IN_PROGRESS  
-> SUCCESS_REQUESTED  
-> COMPLETED  
```  

포인트 지급은 `COMPLETED`가 되는 시점에 처리하는 것이 자연스럽다.

### 2.4 리뷰는 가게 리뷰지만 회원 미션과 1:1로 연결

리뷰는 화면 진입 위치와 무관하게 가게에 달리는 리뷰다.

다만 이 서비스에서는 리뷰 작성이 "실제로 미션을 완료한 회원"에게만 허용되는 흐름으로 해석했다.

따라서 `review`는 `store_id`를 가진 가게 리뷰이면서, 동시에 `member_mission_id`를 필수로 가진다.

```text  
review.store_id          NOT NULL  
review.member_id         NOT NULL  
review.member_mission_id   NOT NULL UNIQUE  
```  

이렇게 하면 하나의 완료 미션에 리뷰가 하나만 작성되도록 보장할 수 있다.

## 3. 최종 주요 테이블 역할

![](https://img.boostad.site/2026/04/e060f2c55248e4e227d35c4c480f493a.svg)

https://dbdiagram.io/d/69f2ff32c6a36f9c1bc70072

### member

서비스 회원 정보.

Spring Security의 `User`와 이름 혼동을 줄이기 위해 도메인명은 `Member`를 사용한다.

### member_address

회원 주소 정보.

홈 화면은 선택 지역 기준으로 미션과 목표 진행도를 보여주므로, 회원은 주소/지역 정보를 가진다.

### region

지역 정보.

예시: 오금동, 안암동 등.

### region_goal

지역별 월간 목표 미션 수와 보상 포인트.

지역마다 목표 수와 보상 포인트가 다를 수 있으므로 `region`과 분리했다.

### member_region_goal

회원별, 지역별, 월별 목표 진행 상태.

예시:

```text  
member 1번이 2026년 4월 오금동에서 7개 미션 완료  
```  

### store

가게 정보.

미션과 리뷰의 중심 대상이다.

### food_category

음식 카테고리.

회원은 여러 선호 카테고리를 가질 수 있고, 가게도 여러 음식 카테고리에 속할 수 있도록 매핑 테이블을 둔다.

### mission

가게가 제공하는 미션 원본.

아직 특정 회원에게 할당된 상태가 아니다.

홈 화면의 `missions`는 이 `mission` 기준의 도전 가능한 목록이다.

### member_mission

회원이 실제로 도전한 미션 기록.

진행중, 성공 요청, 완료, 거절, 취소 등의 상태를 가진다.

### member_mission_verification

오프라인 인증 번호 정보.

회원이 미션 성공 요청을 하면 생성되고, 사장님이 확인하면 미션 완료 처리로 이어진다.

### review

가게 리뷰.

단, 완료된 회원 미션과 1:1로 연결되어야 한다.

### review_photo

리뷰 이미지.

리뷰 하나에 여러 이미지가 붙을 수 있다.

### review_reply

리뷰 답글.

일반 회원 댓글보다는 사장님/관리자 답글 성격으로 해석했다.

### point_history

포인트 지급/차감 이력.

개별 미션 보상, 지역 목표 보상, 포인트 사용/차감 이력을 구분하기 위해 둔다.

### notification, notification_setting

새 미션, 리뷰 요청, 문의 답변 등의 알림 및 회원별 알림 설정.

### inquiry, inquiry_photo, inquiry_reply

1:1 문의, 문의 이미지, 문의 답변.

문의는 하나의 답변만 갖는 구조로 해석했다.

## 4. API 설계에서 달라진 점

### 4.1 홈 화면 조회

```http  
GET /api/home  
```  

홈 화면의 미션 목록은 아직 회원이 도전하기 전의 일반 `mission` 목록이다.

따라서 `memberMissionId`가 아니라 `missionId`를 내려준다.

### 4.2 회원 미션 목록 조회

```http  
GET /api/member-missions  
```  

이 API는 회원이 이미 도전한 미션 목록이다.

따라서 `member_mission` 기준으로 조회하며 `memberMissionId`, 상태, 도전 시각, 성공 요청 시각, 완료 시각 등을 내려준다.

### 4.3 미션 성공 인증 요청

```http  
POST /api/member-missions/{memberMissionId}/verifications  
```  

기존의 `PATCH /api/missions/{memberMissionId}/success`보다 이 경로가 더 자연스럽다.

이유:

1. 대상 리소스가 일반 `mission`이 아니라 `member_mission`이다.
2. 성공 요청 시 실제로 생성되는 데이터는 `member_mission_verification`이다.
3. 인증 번호 생성이라는 하위 리소스 생성 행위이므로 `POST /verifications`가 적합하다.

### 4.4 리뷰 작성

```http  
POST /api/member-missions/{memberMissionId}/review  
```  

리뷰는 가게 리뷰지만, 이 서비스에서는 완료된 회원 미션이 있어야 리뷰를 작성할 수 있다.

따라서 `POST /api/stores/{storeId}/reviews`보다 `memberMissionId` 기준이 더 안전하다.

서버는 `memberMission -> mission -> store` 관계를 통해 리뷰가 달릴 가게를 알 수 있다.

## 5. 코드 패키지 해석

### domain

진짜 비즈니스 개념을 둔다.

예시:

```text  
member  
mission  
store  
review  
region  
point  
term  
notification  
inquiry  
```  

### api

특정 화면이나 외부 API 응답을 위해 여러 도메인을 조합하는 계층이다.

`home`은 테이블이 있는 도메인이 아니라 화면 조합 API에 가깝다.

그래서 다음처럼 분리했다.

```text  
api/home  
domain/mission  
domain/member  
domain/region  
```  

## 6. 5주차 PR 리뷰 반영 리팩토링

동료 PR 리뷰를 통해 네이밍 일관성에 대한 피드백을 받았다.

프로젝트에서는 회원 도메인명을 `Member`로 사용하기로 했기 때문에, 기존에 남아 있던 `UserMission`, `UserRegionGoal`, `userMissionId`, `/api/user-missions` 등의 표현을 `MemberMission`, `MemberRegionGoal`, `memberMissionId`, `/api/member-missions` 기준으로 정리했다.

또한 리뷰 이미지 요청 필드는 현재 `List<String>`으로 이미지 URL을 받는 구조이므로, 실제 파일 업로드처럼 보일 수 있는 `images`보다 의미가 명확한 `imageUrls`로 변경했다.