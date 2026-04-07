# Delivery 마이크로서비스 컨텍스트

## 개요
이 프로젝트는 Spring Boot 기반의 마이크로서비스로, 더 큰 MSA (Microservices Architecture) 환경 내에서 배달 운영을 관리하는 데 중점을 둡니다.

## 기술 스택
- **프레임워크:** Spring Boot 4.0.1
- **언어:** Java 21
- **데이터 영속성:** Spring Data JPA 및 H2 (인메모리 데이터베이스)
- **보안:** JWT 기반 인증/인가를 위한 java-jwt (지원 역할)
- **빌드 도구:** Gradle

## 프로젝트 구조 (`/delivery`)
- **Controller:** `DeliveryController.java` - 배달 관리를 위한 RESTful 엔드포인트를 노출합니다.
- **Service:** `DeliveryService.java` - 배달 생성, 조회 및 취소를 위한 비즈니스 로직을 포함합니다.
- **Repository:** `DeliveryRepository.java` - `Delivery` 엔티티에 대한 데이터베이스 상호작용을 처리합니다.
- **Entity:** `Delivery.java` - `orderId`와 연결되고 `DeliveryStatus`를 포함하는 배달 레코드를 나타냅니다.
- **DTOs:** 데이터 전송을 위한 `DeliveryRequest.java` 및 `DeliveryResponse.java`.

## 주요 기능
1. **배달 생성:** 특정 주문과 연결된 새로운 배달 레코드를 생성합니다. 간단한 주소 검증을 포함하며 배달을 완료 상태로 표시합니다.
2. **배달 조회:** ID로 배달 상세 정보를 조회합니다.
3. **배달 취소:** 연관된 `orderId`를 기반으로 배달을 취소할 수 있습니다.

## 엔드포인트
- `POST /api/deliveries`: 새로운 배달 생성.
- `GET /api/deliveries/{deliveryId}`: ID로 배달 상세 정보 조회.
- `PUT /api/deliveries/{orderId}`: 주문 ID로 배달 취소.