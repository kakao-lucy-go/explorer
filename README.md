# 소개
장소 검색 API 를 활용해 장소를 검색하고, 인기 있는 장소 랭킹을 확인할 수 있다.

# 개발 환경 
1. 언어 : Java 8
2. 프레임워크 : Spring Boot 2
3. 데이터베이스 : H2
4. 프론트 구현 : JSP, Javascript
5. OS : MacOS
6. 빌드 : Gradle
7. 테스트 : Spring Boot test

# 기능
## 로그인
* 가입되어 있는 사용자만 로그인이 가능하다.
* 로그인 정보는 어플리케이션 실행 시점에 등록된다.
* 비밀번호는 암호화된다.

## 장소 검색
* 키워드를 통해 장소 데이터를 검색할 수 있다.
* 한 번에 최대 15개의 데이터를 볼 수 있다.
* 장소 데이터가 많은 경우 페이지네이션을 제공한다
* 페이징을 넘기는 경우에는 인기 검색어 순위에 영향을 주지 않는다.

## 장소 상세 보기
* 검색된 장소의 상세 정보를 확인할 수 있다.
* 다음 지도 상세 화면도 볼 수 있다.

## 인기 검색어 TOP 10
* 장소를 검색할 때마다 인기 장소 검색어 순위가 변동된다.
* 인기 장소 검색어의 검색 횟수를 같이 확인할 수 있다.

## 매일 로깅 파일 축적
* /log 에 매일 축적되는 로그 파일을 확인해볼 수 있다.

## 사용자의 롤에 따라 기능에 제한
* 해당 어플리케이션에는 특정 role 을 가진 사용자만 기능을 사용할 수 있다.

# 실행 방법
1. 직접 build
```$xslt
$ git clone https://github.com/mychum1/explorer.git
$ cd explorer
$ ./gradlew build
$ java -jar build/libs/explorer-0.0.1-SNAPSHOT.jar
```

2. build 된 jar 실행
```$xslt
$ git clone https://github.com/mychum1/explorer.git
$ cd explorer
$ java -jar explorer-0.0.1-SNAPSHOT.jar
```

# 접속 정보
* url : localhost:8080
* 유저정보
    * 서비스 이용 가능 사용자
        * ID : client
        * PW : password
    * 서비스 이용 불가능 사용자
        * ID : client2
        * PW : password
 
# 실행 화면
## 로그인
![로그인](https://github.com/mychum1/explorer/blob/master/images/login.png)

## 인기검색어
![인기 검색어](https://github.com/mychum1/explorer/blob/master/images/hotplace.png)

## 장소 검색
![장소 검색](https://github.com/mychum1/explorer/blob/master/images/search-place.png)

## 장소 페이지네이션
![페이지네이션](https://github.com/mychum1/explorer/blob/master/images/pagenation.png)

## 장소 상세보기
![상세 화면](https://github.com/mychum1/explorer/blob/master/images/detail.png)

# 사용 라이브러리

1. Spring boot data JPA : 데이터베이스 persistence layer 로 사용 
2. Spring boot security : 로그인 기능을 위해 사용
3. Spring boot web : WEB 환경 구성
4. h2 : 데이터베이스
5. tomcat embed jasper : 프론트 구현을 위한 JSP 사용
6. axios : 프론트에서 API 통신 
7. kakao map : 다음 맵 구현 

# API 명세서
## GET /api/kakao/places
### Request
keyword로 장소 데이터를 검색한다. 

parameter name|value type|의미|required|default
--------------|------------|-----|-----|------
keyword|String|장소 검색 키워드|TRUE|x
repeat|Boolean|인기검색어 순위 반영 여부. true이면 인기 검색어 순위에 영향을 주지 않는다.|FALSE|false
page|Integer|페이지 번호|FALSE|1
size|Integer|데이터 사이즈|FALSE|15

### Response
field name|value type
----------|------------
code|Integer
msg|String
data|Object

## GET /api/rank
num으로 넘어온 숫자만큼 인기 검색어 키워드 랭킹을 반환한다.
### Request
parameter name|value type|의미|required|default
--------------|-----------|---|-------|--------
num|Integer|인기 검색어 데이터 사이즈|FALSE|10

### Response
field name|value type
----------|-----------
code|Integer
msg|String
data|Object
