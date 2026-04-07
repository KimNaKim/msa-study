# Order 마이크로서비스 컨텍스트

## 개요
이 프로젝트는 MSA 환경에서 주문 처리를 담당하는 핵심 서비스입니다. 상품 서비스(Product Service)와 배달 서비스(Delivery Service)와 통신하여 전체 주문 프로세스를 조율하며, 실패 시 보상 트랜잭션(Compensating Transaction)을 통해 데이터 일관성을 유지합니다.

## 기술 스택
- **프레임워크:** Spring Boot 4.0.1
- **언어:** Java 21
- **통신:** RestClient (동기 HTTP 통신)
- **데이터 영속성:** Spring Data JPA 및 H2 (인메모리 데이터베이스)
- **보안:** JWT 기반 인증/인가 (JwtAuthenticationFilter 포함)
- **빌드 도구:** Gradle

## 프로젝트 구조 (`/order`)
- **Controller:** `OrderController.java` - 주문 생성, 조회, 취소 API를 제공합니다.
- **Service:** `OrderService.java` - 주문의 핵심 비즈니스 로직을 처리하며, 타 서비스와의 연동 및 트랜잭션 관리를 수행합니다.
- **Adapter:** `ProductClient.java`, `DeliveryClient.java` - 외부 마이크로서비스(상품, 배달)와의 통신을 담당합니다.
- **Entity:** 
  - `Order.java`: 주문의 마스터 정보를 담고 있습니다.
  - `OrderItem.java`: 주문에 포함된 상세 상품 정보를 담고 있습니다.
- **Core:** JWT 필터, 글로벌 예외 처리기, 공통 응답 유틸리티 등을 포함합니다.

## 주요 기능 및 프로세스

### 1. 주문 생성 (`POST /api/orders`)
주문 생성 시 다음과 같은 단계가 순차적으로 실행됩니다:
1.  **주문 레코드 생성:** 데이터베이스에 기본 주문 정보를 저장합니다.
2.  **상품 재고 차감:** `ProductClient`를 통해 상품 서비스의 재고를 차감합니다.
3.  **주문 아이템 저장:** 상세 주문 내역을 저장합니다.
4.  **배달 생성 요청:** `DeliveryClient`를 통해 배달 서비스에 배달 생성을 요청합니다.
5.  **완료:** 주문 상태를 완료로 변경합니다.

**보상 트랜잭션:** 프로세스 도중 예외가 발생하면 이미 실행된 단계에 대해 역작업(배달 취소, 재고 복구)을 수행하여 일관성을 맞춥니다.

### 2. 주문 조회 (`GET /api/orders/{orderId}`)
- 특정 주문의 상세 정보와 포함된 상품 목록을 조회합니다.

### 3. 주문 취소 (`PUT /api/orders/{orderId}`)
- 주문 상태를 취소로 변경하고, 상품 재고를 복구하며, 배달 서비스에 배달 취소를 요청합니다.

## 타 서비스 연동 (External Services)
- **Product Service:** `http://product-service:8082` (재고 증감)
- **Delivery Service:** `http://delivery-service:8084` (배달 생성 및 취소)
