<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>랭킹 확인</title>
</head>
<body>
    <form action="/api/rank" method="get">
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="num" placeholder="top ? ranking">
        <button type="submit">조회</button>
    </form>
</form>
</body>
</html>