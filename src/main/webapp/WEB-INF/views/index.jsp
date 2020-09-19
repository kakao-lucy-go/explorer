<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>장소 검색</title>
<script type="text/javascript" src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script type="text/javascript">
<!--const axios = require('axios');-->


function fetchData(e){
e.preventDefault();
    console.log("in")
    axios.get('/api/kakao/places', {
        params: {
          keyword: "수지구청",
          page: 1,
          size : 15
        }
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      })
      .finally(function () {
        // always executed
      });
}

</script>

</head>
<body>
<!-- action="/api/kakao/places"  method="get"-->
    <form>
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
<!--
        <input type="text" name="keyword" placeholder="검색하고자 하는 값을 입력하세요.">
        <input type="text" name="page" placeholder="page">
        <input type="text" name="size" placeholder="size"> -->
        <button type="submit" onclick="fetchData(event)" >검색</button>
    </form>
<button onclick="fetchData()">test</button>

</body>
</html>

