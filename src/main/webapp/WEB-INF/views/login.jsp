<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="application/x-www-form-urlencoded; charset=EUC-KR">
<title>로그인</title>
<script type="text/javascript" src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>
   <!-- <form action="/login" method="post"> -->
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="username" placeholder="이메일 입력해주세요">
        <input type="password" name="password" placeholder="비밀번호">
        <button type="submit" onclick="login(event)">로그인</button>
    <!--</form>-->
</form>
<script type="text/javascript">
async function login(e){
    if(e) {

        e.preventDefault();
    }

    let username = document.querySelector('input[name=username]').value;
    let password = document.querySelector('input[name=password]').value;

    let params = {username, password}

    const config={method:'POST', url:`/login`, data:params}

    try{
        let res = await axios(config);
        console.log(res.data);
    }catch(err){
        console.log(err)
    }
}
</script>
</body>
</html>

