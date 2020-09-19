<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>주민번호 입력받아 전송</title>
</head>
<body>
    <form action="/user/login" method="post">
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="username" placeholder="이메일 입력해주세요">
        <input type="password" name="password" placeholder="비밀번호">
        <button type="submit">로그인</button>
    </form>
</form>
</body>
</html>

