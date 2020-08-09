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
  * 엔티티 생성 -> 정적 팩토리 메소드, 빌더패턴, 생성자 등
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

### Section 4
* 웹 계층 개발 
  
* Note
  * Req, Res 시 DTO 나 Form객체 활용 추천 (API는 특히 엔티티 X)
  * 컨트롤러에서 엔티티 생성 비추
  * 엔티티 new, but 식별자O -> 준영속 엔티티 (영속성 컨텍스트가 관리 X)
  * 준영속 엔티티 수정하려면 1. 변경감지(추천) 2. 병합
  * 변경감지 : 식별자로 find하여 영속성으로 들어온 객체를 받은 param으로 set
  * 병합 : 동작은 유사하나 모든 필드를 엎어침(선택불가)
  
### Section 5
* API 개발 기본 및 조회 최적화 
  
* Note
  * 템플릿 엔진 컨트롤러, API 컨트롤러 분리 추천
  * 엔티티 노출 금지(별도 객체 활용)
  * DTO @getter, @setter, @data
  * update 등에서 커맨드와 쿼리 분리
  * 결과의 제일 바깥은 Object형식으로
  * Lazy 로딩되기전에는 프록시 객체가 들어가있다. (강제초기화해야함)
  * xToMany관계가 아니면 fetch join 사용
  * DTO 반환 쿼리는 Trade-off가 있음
  
