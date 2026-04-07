# Product 마이크로서비스 컨텍스트

## 개요
이 프로젝트는 MSA 환경에서 상품 정보 관리 및 재고 제어를 담당하는 마이크로서비스입니다. 주문 서비스(Order Service)의 요청에 따라 실시간으로 재고를 차감하거나 복구하는 역할을 수행합니다.

## 기술 스택
- **프레임워크:** Spring Boot 4.0.1
- **언어:** Java 21
- **데이터 영속성:** Spring Data JPA 및 H2 (인메모리 데이터베이스)
- **보안:** JWT 기반 인증/인가 (JwtAuthenticationFilter 포함)
- **빌드 도구:** Gradle

## 프로젝트 구조 (`/product`)
- **Controller:** `ProductController.java` - 상품 조회 및 재고 조절을 위한 엔드포인트를 제공합니다.
- **Service:** `ProductService.java` - 재고 부족 확인, 가격 검증 등 상품 관련 비즈니스 로직을 처리합니다.
- **Repository:** `ProductRepository.java` - 상품 데이터에 대한 데이터베이스 접근을 담당합니다.
- **Entity:** `Product.java` - 상품명, 가격, 현재 재고 수량 등의 정보를 포함하는 상품 엔티티입니다.
- **Core:** JWT 처리, 예외 핸들링, 공통 응답 처리 등 시스템의 핵심 유틸리티를 포함합니다.

## 주요 기능

### 1. 상품 조회 (`GET /api/products/{productId}`)
- 특정 상품의 상세 정보(이름, 가격, 재고 등)를 조회합니다.

### 2. 재고 차감 (`PUT /api/products/{productId}/decrease`)
- 주문이 발생했을 때 호출됩니다.
- 요청된 수량만큼 재고가 충분한지, 요청된 가격이 실제 상품 가격과 일치하는지 검증 후 재고를 줄입니다.

### 3. 재고 복구 (`PUT /api/products/{productId}/increase`)
- 주문이 취소되거나 주문 생성 중 오류가 발생하여 보상 트랜잭션이 실행될 때 호출됩니다.
- 상품의 재고를 다시 원래대로 늘립니다.

## 특징
- **가격 검증:** 재고 변경 시 요청된 가격과 서버의 가격을 대조하여 데이터 무결성을 보장합니다.
- **예외 처리:** 재고 부족(400 Bad Request), 상품 미존재(404 Not Found) 등 상황에 맞는 명확한 예외 메시지를 반환합니다.
