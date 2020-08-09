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

## Part 2  

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
  
### Section 6
* 컬렉션 조회 최적화
  
* Note
  * DTO 내부에서도 엔티티 사용 금지(노출됨)
  * 페이징 시도 시 하이버네이트가 메모리로 다 들고와서 페이징을 시도함(위험)
  * 페이징 필요없으면 중복 객체 존재 시 distinct 일부 사용가능
  * hibernate batchsize 활용(쿼리를 in 절로 바꿈)
  * 엔티티 내부 엔티티 컬렉션은 돌아가면서 Lazy Load 초기화 해줘야함
  * DTO 반환 쿼리 시 내부 컬렉션은 IN 절로 한번에 뽑아서 어플리케이션에서 부모객체에 세팅해줘야함
  * flat data로 뽑는 방법도 존재(쿼리에서 뽑은 형식 그대로) -> 원하는 모양으로 만들려면 후작업 해줘야함
  * 결론 : batch-fetch-size 정도로만 최적화하는 것을 추천
  * Open Session In View : 뷰와 컨트롤러 단에서도 영속성 컨텍스트가 생존해 있다. (DB connection 생존)
  * OSIV -> DB connection이 반환안되서 모자르는 문제가 생길수있다 -> off 추천
  * Service 단계에서 끝내야함. 커맨드와 쿼리 분리 아키텍처 고민해야함.
  * 핵심 비지니스 로직과 api에 맞춘 읽기전용 트랜잭션 로직을 분리
  
### TODO
* Spring Data JPA
* QueryDSL
