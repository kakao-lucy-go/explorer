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

(function() {

   console.log("Initial")
   fetchRank();

})();

async function fetchRank(){
    //e.preventDefault();
    let rankParams = {num:10}
    const rankConfig = {
            method: 'GET',
            url: `/api/rank`,
            rankParams
        }

    try {
        let ranking = await axios(rankConfig);
        console.log(ranking)
    } catch(err) {
        console.log(err)
    }
}


async function fetchData(e, repeat){
    e.preventDefault();

    let keyword = document.querySelector('input[name=keyword]').value;
    let page = document.querySelector('input[name=page]').value;
    let size = document.querySelector('input[name=size]').value;
    console.log(keyword)

    let params = {
        keyword,
        page,
        size,
        repeat
    }

    const config = {
        method: 'GET',
        url: `/api/kakao/places`,
        params
    }
    try {
        let res = await axios(config);
        console.log(res)
    } catch(err) {
        console.log(err)
    }

    fetchRank();

 /*   axios.get('/api/kakao/places', {
        params: {
          keyword: keyword,
          page: page,
          size : size
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
      }); */
}

</script>

</head>
<body>
    <form>
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="keyword" placeholder="검색하고자 하는 값을 입력하세요.">
        <input type="text" name="page" placeholder="page">
        <input type="text" name="size" placeholder="size">
        <button type="submit" onclick="fetchData(event, false)" >검색</button>
        <<button type="submit" onclick="fetchData(event, true)" >다음 페이지</button>
    </form>



</body>
</html>

