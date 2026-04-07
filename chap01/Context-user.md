# User 마이크로서비스 컨텍스트

## 개요
이 프로젝트는 MSA 환경에서 사용자 관리 및 인증(Authentication)을 담당하는 마이크로서비스입니다. 사용자의 로그인 요청을 처리하고, 서비스 간 보안 통신을 위한 JWT(JSON Web Token)를 생성 및 검증하는 역할을 수행합니다.

## 기술 스택
- **프레임워크:** Spring Boot 4.0.1
- **언어:** Java 21
- **데이터 영속성:** Spring Data JPA 및 H2 (인메모리 데이터베이스)
- **보안/인증:** JWT (java-jwt), Bcrypt (또는 단순 비밀번호 비교), JwtAuthenticationFilter
- **빌드 도구:** Gradle

## 프로젝트 구조 (`/user`)
- **Controller:** `UserController.java` - 로그인 및 회원 정보 조회를 위한 엔드포인트를 제공합니다.
- **Service:** `UserService.java` - 사용자 검증 및 토큰 생성 비즈니스 로직을 처리합니다.
- **Repository:** `UserRepository.java` - 사용자 데이터베이스 접근을 담당합니다.
- **Entity:** `User.java` - 유저네임, 비밀번호, 이메일 등의 정보를 포함하는 사용자 엔티티입니다.
- **Core:** 
  - `JwtUtil.java`, `JwtProvider.java`: JWT 생성 및 파싱 로직을 포함합니다.
  - `JwtAuthenticationFilter.java`: API 요청 시 토큰의 유효성을 검사합니다.
  - `GlobalExceptionHandler.java`: 전역 예외 처리를 담당합니다.

## 주요 기능

### 1. 로그인 (`POST /login`)
- 사용자의 `username`과 `password`를 받아 일치 여부를 확인합니다.
- 인증 성공 시, 사용자의 ID와 이름을 담은 JWT 토큰을 생성하여 반환합니다.

### 2. 회원 정보 조회 (`GET /api/users/{userId}`)
- 특정 사용자의 상세 정보(비밀번호 제외)를 조회합니다.
- 보통 인증된 상태에서 자신의 정보를 조회할 때 사용됩니다.

## 보안 및 인증 흐름
- **토큰 기반 인증:** 로그인 성공 시 클라이언트에게 JWT를 발급합니다.
- **필터 기반 검증:** `/api/**` 경로로 들어오는 모든 요청은 `JwtAuthenticationFilter`를 거쳐 토큰의 유효성을 검증받아야 합니다.
- **보안 예외 처리:** 유효하지 않은 토큰(401 Unauthorized), 권한 부족(403 Forbidden), 사용자 미존재(404 Not Found) 등에 대한 예외를 처리합니다.
