# 인프런 강의 실습
## 실전! 스프링 부트와 JPA 활용 by 김영한  

## Part 1  

### Section 1
* 프로젝트 생성, 라이브러리 확인, DB 설치 및 설정  
  
* Note
  * https://start.spring.io/
  * Lombok 플러그인(어노테이션으로 코드 줄여줌)
  * 로그 라이브러리 사용 시 운영환경 부하확인 필요
  * spring-boot-devtools -> recompile 가능
  * H2 DB -> 테스트 시 메모리 모드 가능
  
### Section 2
* 요구사항분석, 테이블 설계, 엔티티 개발 및 주의점 
  
* Note
  * 외래키가 있는 곳을 연관관계의 주인으로함 (반대편은 mappedBy로)
  * @ManyToMany 사용 지양하기 (컬럼추가 불가능)
  * Setter는 선별해서 만들기
  * @XtoOne의 경우 LazyFetch (n+1 문제 등 예방)
  * Cascade.ALL -> 딸려있는 것들도 persist가 한번에 같이 됨
  * EnumType.ORDINAL(0, 1, 2 ...) -> 중간에 추가 시 문제됨
  * 값 타입은 변경 불가능하게 설계
  * 컬렉션 필드는 바로 초기화가 best
  * 필요 시 연관관계 메서드 추가 (셋할때 반대편에도 자신을 셋)
  * 테이블, 컬럼 이름규칙은 인터페이스 구현으로 커마 가능
  
### Section 3
* 회원, 상품, 주문 도메인 개발 
  
* Note
  * Service @Transactional (read only 로 최적화) 
  * 생성자 injection 추천
  * 도메인 모델 패턴 -> 엔티티에 비지니스 로직
  * 트랜잭션 스크립트 패턴 -> 서비스에서 비지니스 로직
  * Cascade.All -> 대상이 많은 곳에서 참조되지 않고 persist 생명주기가 같을때만 사용
  * 생성자 protected로 제약하기(private는 JPA 스펙상 X)
  * 테스트는 목킹 활용, 범위는 단위테스트 추천
  * 동적쿼리 -> Querydsl 사용
