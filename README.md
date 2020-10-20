# CIA(Collective Intelligence in army)
<div>
<img width="300" style="text-align: center; margin: 0 auto;" src="https://user-images.githubusercontent.com/37019259/96568573-100c4000-1303-11eb-9bfb-7862f7c4e574.png">
</div>

```
프로젝트명은 CIA(Collective Intelligence in army)으로 軍 집단지성을 의미합니다. 
```

# 팀 소개 및 프로젝트 설명 동영상
```
"돌아오지 않는 내 시간, 군대에서 '나'를 업그레이드 하자!" 라는 미션을 가진 CIA는 비대면 강의, 스터디 플랫폼 앱(App)을 만들고자 합니다. 
빅데이터 기반 AI 기술을 통해 사용자들의 행동 데이터를 분석하고 관심있는 강의와 스터디를 맞춤형으로 추천해줍니다.
```
`소개영상` * 업데이트 예정

[![이미지 텍스트](스크린샷 이미지)](유투브링크)

# 기능 설계 
+ Android
```
메인 화면 - 인기 강의, 스터디, 카테고리, 추천 강의, 스터디 확인 
검색 화면 - 제목과 내용으로 강의, 스터디 검색 
내 정보 화면 - 내 정보, 내가 수강중인 강의, 스터디, 내가 등록한 강의 스터디 확인 
강의, 스터디 상세 정보 화면 - 제목, 썸네일, 설명, 시간, 장소, 멤버, 강의 등 상세 정보 확인 
카테고리 화면 - 카테고리 별로 강의, 스터디 탐색
강의, 스터디 업로드 화면 - 강의, 스터디를 등록 
```
+ API 문서
```
업로드 예정
```

+ DB 설계
```
회원 정보		
create table hs_user_info (
	idx int(11) unsigned not null auto_increment primary key,
	password text not null,
	name varchar(15) not null unique,
	email varchar(50) not null unique,
	phonenm varchar(15) not null,
	regdate datetime not null,
	img varchar(255) null,
	enabled tinyint(1) not null default false
);

회원 권한
create table hs_user_auth (
	idx int(11) unsigned not null auto_increment primary key,
	userIdx int(11) unsigned not null,
	authority varchar(50) not null
);

스터디 정보
create table hs_study_info (
	idx int(11) unsigned not null auto_increment primary key,
	userIdx int(11) unsigned not null,
	regdate datetime not null,
	img text null,
	title varchar(255) not null,
	note text null,
	viewCount int(11) unsigned not null default 0,
	station text null,
	signdate datetime not null,
	maxPeople int(11) unsigned not null,
	enabled tinyint(1) not null default false
);

스터디 수강(참여) 
create table hs_study_regist (
	idx int(11) unsigned not null auto_increment primary key,
	studyIdx int(11) unsigned not null,
	userIdx int(11) unsigned not null,
	regdate datetime not null
);

클래스 정보
create table hs_class_info (
idx int(11) unsigned not null auto_increment primary key,
userIdx int(11) unsigned not null comment 'user idx',
regdate datetime not null,
thumbnail text null comment 'thumbnail image',
openFlag tinyint(1) not null default false,
title text null,
note text null,
viewCount int(11) unsigned not null default 0,
catIdx int(11) unsigned not null comment 'category idx'
);

클래스 카테고리
create table hs_class_category (
idx int(11) unsigned not null auto_increment primary key,
name varchar(30) not null,
regdate datetime not null
);

클래스 회차정보 
create table hs_class_curriculum (
idx int(11) unsigned not null auto_increment primary key,
classIdx int(11) unsigned not null,
regdate datetime not null,
numb int(11) unsigned not null comment 'class ordering number',
title varchar(100) not null,
note text null,
viewCount int(11) unsigned not null default 0,
videoPath text null,,
videoSeconds int(11) unsigned not null,
thumbnail text null comment 'thumbnail image'
);

클래스 좋아요
create table hs_class_like (
idx int(11) unsigned not null auto_increment primary key,
userIdx int(11) unsigned not null comment 'user idx',
regdate datetime not null
);
클래스 수강정보
create table hs_class_user_record (
idx int(11) unsigned not null auto_increment primary key,
curriculumIdx int(11) unsigned not null,
userIdx int(11) unsigned not null comment 'user idx',
regdate datetime not null
);
```


# 컴퓨터 구성 / 필수 조건 안내 (Prerequisites)
+ APP : 안드로이드 스마트폰
+ WEB Management System : Google Chrome(권장)



# 기술 스택 (Technique Used) 
### 1. UI / UX 
+ AdobeXD, Photoshop

_목표_
```   
- 사용자 중심의 직관적인 디자인과 사용자 경험을 제공한다.
```
### 2. Android(front-end)
+ Android

_목표_
```
- 사용자는 앱 내에서 회원가입 및 로그인하고 프로필 정보를 조회할 수 있다.
- 사용자는 강의 콘텐츠를 개설하고 수강할 수 있다.
- 사용자는 (대면/비대면) 스터디를 생성하고 오픈 채팅 할 수 있다. 필요시, 줌 서비스와 연동할 수 있다.  
- 강의와 스터디를 검색하고, 카테고리별로 탐색할 수 있다.
- 최근 인기있는 강의와 스터디, 나에게 맞는 강의와 스터디를 확인할 수 있다.
```
### 3. Server(back-end)
+ Java, Tomcat 8.0.x / MariaDB 10.1.x UTF-8

_목표_
```
모든 API는  RESTful 하게 설계하고 개발한다.
사용자들의 행동데이터를 로그 형태로 기록한다.
```

### 4. Web Management System
+ Spring, Java, Tomcat 8.0.x / MariaDB 10.1.x UTF-8

_목표_
```
- 모든 API는  RESTful 하게 설계하고 개발한다.
- 시각화된 사용자들의 행동 데이터를 확인할 수 있다.
- 사용자가 개설한 강의, 스터디, 수강, 이용, 참여한 강의나 스터디에 대한 통계 정보를 확인할 수 있다.
```

### 5. 기타 

+ 프로젝트 관리 : trello
+ 영상 제작 : PremierePro

# 설치 안내 (Installation Process)
+ Android
```
repository의 build.apk 파일을 설치하여 사용
```
+ WebManagementSystem

[링크 제목](https://theorydb.github.io "마우스를 올려놓으면 말풍선이 나옵니다.")

```
서버 설치를 위해 tomcat, mysql을 설치하고 OOO.xml 설정값을 변경해준다.
```

# 프로젝트 사용법 (Getting Started)
```
CIA는공개SW의 취지와 비슷합니다. 
좋은 콘텐츠를 공개하고 공유하는 것입니다. 크게 두가지 기능으로 이루어져 있습니다.

○ 강의
· 특정 분야의 전문가인 사용자는 재능기부를 할 수 있습니다.
· 이는 App상에서 강의를 개설하고, 동영상, 글 등의 컨텐츠를 업로드하는 것으로 진행됩니다.
· 특정 분야에 관심 있는 사용자는 무료로 배울 수 있습니다.
· 이는 다른 사용자가 개설한 강의에서 동영상, 글 등의 컨텐츠를 시청하는 것으로 진행됩니다.

○ 스터디
· 소모임(대면, 비대면)을 만들 수 있습니다.
· 특정 분야에 대해 정보를 공유하고, 서로 도움을 주고 싶은 사용자가 모이고, 일정을 공유할 수 있는 사이버 공간을 제공합니다.
이 두가지 기능을 효율적으로 사용하기 위해 검색, 즐겨찾기, AI 추천 등 여러가지 기능을 지원합니다.


1. 최초 자기계발에 관심있는 군인은 회원 가입 후 로그인을 합니다.
2. 관심 카테고리를 선택하면 자신에게 추천되는 여러 강의와 스터디를 카테고리별로 구분하여 만나볼 수 있습니다.
3. 제공되는 강의와 스터디 등을 확인 후, 원하는 콘텐츠에 대해 자유롭게 강의를 듣거나 대면/비대면으로 스터디에 참여하여 함께 자기계발을 할 수 있습니다. 
4. 또한, 함께 공유하고 싶은 콘텐츠가 있는 회원은 크리에이터로써 강의 영상을 찍어 올릴 수 있고, 스터디를 리드하고 싶은 회원은 스터디 모집을 하여 함께 성장할 수 있는 기회를 얻을 수 있습니다.
5. 등록 한 강의 및 나의 정보는 '내 정보' 탭에서 관리 할 수 있습니다.

```

# 팀 정보 (Team Information)
```
- 대위 정동훈 (Project Management, Web Development), 
- 8급 이유연 (Database, Data Analytics, Design), 
- 병장 정희성(API Server Development), 
- 상병 김도엽(Android Application Development)
```
