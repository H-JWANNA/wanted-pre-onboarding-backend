🔗 [원티드 사전 과제 페이지](https://github.com/lordmyshepherd-edu/wanted-pre-onboardung-backend-selection-assignment)

<br>

## 지원자의 성명

홍정완

<br>

## 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)

**1. 레포지토리 복제**
- ```git clone https://github.com/H-JWANNA/wanted-pre-onboarding-backend.git```를 통해 로컬에 레포지토리를 복제한다.

**2. 프로젝트 빌드**
- ```./gradlew build```를 통해 프로젝트를 빌드한다.

**3. 환경변수 작성**
- ```/src/main/resource/env.properties``` 파일에 로컬 DB(MySQL)의 URL과 Username, Password를 작성한다.

**4. 실행**
- 루트 디렉토리에서 ```java -jar build/libs/pre-onboarding-0.0.1-SNAPSHOT.jar```를 통해 실행한다.
- 이후 하단의 API 명세서를 통해 엔드포인트를 확인한다.

<br>

## 데이터베이스 테이블 구조

<img src = "https://user-images.githubusercontent.com/110897995/261037710-62a38640-98a3-4259-a6cb-6809ea52936e.png" width = 300px>

<br>

## 구현한 API의 동작을 촬영한 데모 영상 링크

🔗 [유튜브 링크](https://youtu.be/zXl6n0zPa1k)

<br>

## 구현 방법 및 이유에 대한 간략한 설명

불필요한 Depth를 최소화하여 도메인별로만 패키지를 나누었습니다.  
DTO를 사용하여 요청, 응답에 대한 데이터를 선택적으로 선별했습니다.

### 회원 가입

- 이메일, 패스워드의 유효성 검사는 직관적으로 확인할 수 있도록 jakarta 라이브러리를 사용해 어노테이션 방식으로 DTO 클래스에 구성했습니다.
- 패스워드는 Spring Security 6.1을 사용하였기에 PasswordEncoder의 DelegatingPasswordEncoder를 사용하여 암호화하였습니다.

### 로그인
- 로그인은 Spring Security와 JWT를 사용해서 구성하였으며, 로그인 성공 시 Header에 JWT 토큰을 반환하도록 구현했습니다.
- 간단한 구현을 위해 따로 저장소에 토큰을 저장하지 않고, 만료 시간을 60분으로 설정했습니다.
- 로그인 DTO도 마찬가지로 어노테이션 방식으로 유효성 검사를 진행했습니다.
- jwt SercetKey는 운영 환경에서 사용하지 않으므로 환경 변수가 아닌 상수로 고정했습니다.

### 게시글
- 간단한 CRUD 구현을 통해 완성했습니다.
- ```@AuthenticationPrincipal``` 어노테이션을 사용하여 인증된 회원만 접근 가능하도록 설정하였습니다.
- 위의 회원 정보를 통해 게시글 작성자만 수정 혹은 삭제할 수 있도록 설정했습니다.
- 존재하는 게시글인지, 회원이 일치하는지 등의 유효성 검사 메서드를 따로 분리해 재사용 가능하도록 했습니다.

<br>

## API 명세(request/response 포함)

🔗 [API 명세서](https://documenter.getpostman.com/view/23670475/2s9Y5R15yq)
