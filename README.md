# place-searcher-webapp
장소 검색 서비스 입니다.
---
### API Test
* {projectRoot}/httprequest/test.http

---
### 기술 요구사항
* 동시성 이슈가 발생할 수 있는 부분을 염두에 둔 설계 및 구현
  * 키워드 별로 검색된 횟수 UPDATE 쿼리에 대한 동시성 보장 → 레코드 락 + Transactional
  * [추후 작업] Message Queue로 조회 event 받고 모아서 조회 값 수정   
    
* API 제공자의 “다양한” 장애 발생 상황에 대한 고려
  * API Provider 별로 HttpConnectionPool 격리
  * WebClient 의 Retry 로직 추가 (retry delay 간격 지수증가)
  * [장애 내성 로직] Provider 한쪽만 error 발생시 다른쪽 결과를 유저에게 내려준다. Provider 모두 error 발생시 ExceptionResponse 내려준다.

* 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
   
* 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
    * [실패] dao, response 추상화 : factory 로 dao 를 받아서 api 를 호출하는 구조를 생각했으나 함수형 프로그래밍 방식의 구현 방법을 찾지 못함. 

* 서비스 오류 및 장애 처리 방법에 대한 고려
    * exception : ApiProviderException
    * handler : ExceptionController

* 대용량 트래픽 처리를 위한 반응성(Low Latency), 확장성(Scalability), 가용성(Availability)을 높이기 위한 고려
    * 전체 flow 를 reactive stack 으로 개발


---
### 참고
* Geo 관련 좌표 변환, 거리 측정 소스 : https://javaexpert.tistory.com/142