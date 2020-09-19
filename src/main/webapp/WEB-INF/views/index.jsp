<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>장소 검색</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://unpkg.com/axios/dist/axios.min.js"></script>


</head>
<body>

  <div id="Search">
    <div class="header">
      <div class="title">title</div>
      <div>
        <form class="search-box" action="">
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
          <div class="search-input-box">
            <input class="search-keyword" type="text" name="keyword" placeholder="검색어를 입력하세요">
               <input type="text" name="page" placeholder="page">
               <input type="text" name="size" placeholder="size">
            <!-- <span></span> -->
          </div>
          <button class="search-btn" type="submit" onclick="fetchData(event, false)">검색</button>
        </form>
      </div>
    </div>
    <div class="search-list">
      <div class="search-item">
        <div class="item-head">
          <span>카>테>고>리</span>
          <span>010-2345-6789</span>
        </div>
        <div class="item-body">장소</div>
      </div>
    </div>
  </div>

 <!--   <form>
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="keyword" placeholder="검색하고자 하는 값을 입력하세요.">
        <input type="text" name="page" placeholder="page">
        <input type="text" name="size" placeholder="size">
        <button type="submit" onclick="fetchData(event, false)" >검색</button>
        <<button type="submit" onclick="fetchData(event, true)" >다음 페이지</button>
    </form> -->

<script type="text/javascript">
<!--const axios = require('axios');-->

(function() {

   console.log("Initial")
   //fetchRank();

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

function createEl (type, className) {
  let el = document.createElement(type);
  el.className = className;
  return el;
}

function removeData() {
    let parent = document.querySelector(".search-list");
    while ( parent.hasChildNodes() ) {
    parent.removeChild( parent.firstChild );
    }
}


async function fetchData(e, repeat){
    e.preventDefault();
    removeData();
    let keyword = document.querySelector('input[name=keyword]').value;
    let page = document.querySelector('input[name=page]').value || 1;
    let size = document.querySelector('input[name=size]').value || 15;

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

        let placeList = res.data.data.documents;
        console.log(placeList)
        let elPlaceList = placeList.map((item) => {
            let searchItem = createEl('div', 'search-item')

            let itemHead = createEl('div', 'item-head')

            let categoryName = createEl('span', '')
            categoryName.textContent = item.category_name;
            itemHead.appendChild(categoryName)

            let tel = createEl('span', '')
            tel.textContent = item.phone;
            itemHead.appendChild(tel)

            searchItem.appendChild(itemHead)

            let itemBody = createEl('div', 'item-body')
            itemBody.textContent = item.place_name;
            searchItem.appendChild(itemBody)

            return searchItem;
        })

        console.log(elPlaceList)

        for(let el of elPlaceList) {
          document.querySelector('.search-list').appendChild(el)
        }
    } catch(err) {
        console.log(err)
    }
    fetchRank();
}

</script>
</body>


</html>

