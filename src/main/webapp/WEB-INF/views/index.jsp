<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.PrintWriter" %> -->

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title>장소 검색</title>
<link href="style.css" rel="stylesheet" type="text/css" />
<link href="rankStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=~~&libraries=services,clusterer,drawing"></script>

</head>
<body>

  <div id="Rank">
    <div class="header">
      <div class="title">인기 키워드 랭킹</div>
     <div>
        <form class="rank-box" action="">
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
          <div class="rank-input-box">

            <!-- <span></span> -->
          </div>
          <button class="rank-btn" type="submit" onclick="fetchData(event, false)">검색</button>
        </form>
      </div>
    </div>
    <div class="rank-list">
      <div class="rank-item">
        <div class="rank-item-head">
          <span></span>
          <span></span>
        </div>
        <div class="rank-item-body"></div>
      </div>
    </div>
  </div>
&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
								&nbsp;
  <div id="Search">
    <div class="header">
      <div class="title">장소 검색</div>
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
           <button class="search-btn" type="submit" onclick="paging(event, true)" >다음 페이지</button>
        </form>
      </div>
    </div>
    <div class="search-list">
      <div class="search-item">
        <div class="item-head">
          <span></span>
          <span></span>
        </div>
        <div class="item-body"></div>
      </div>
    </div>
    <div class="page-list">
        <a class="page-item"></a>
    </div>
  </div>

<div id="DataDetail">
    <div class="background-dim">
        <div class="window">
            <div class="modal-header">
                <span class="modal-title"></span>
            </div>
            <div class="modal-body">
                <div class="content-container">
                    <div class="content-item-box">
                        <div class="content-item">
                        <div class="content-title">주소</div>
                        <div class="content-content place_address_name">
                    </div>
                </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">카테고리 그룹 코드</div>
            <div class="content-content place_category_group_code"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">카테고리 그룹 명</div>
            <div class="content-content place_category_group_name"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">카테고리 명</div>
            <div class="content-content place_category_name"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">거리</div>
            <div class="content-content place_distance"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">아이디</div>
            <div class="content-content place_id"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">바로가기</div>
            <div class="content-content place_link"></div>
            <div id="map" style="width:300px;height:200px;"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">연락처</div>
            <div class="content-content place_phone"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">장소명</div>
            <div class="content-content place_place_name"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">위치</div>
            <div class="content-content place_place_url"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">도로명 주소</div>
            <div class="content-content place_road_address_name"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">위도</div>
            <div class="content-content place_x"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">경도</div>
            <div class="content-content place_y"></div>
            </div>
            </div>

            </div>
            </div>
            <div class="modal-footer">
            <button class="close" onclick="hideDetailModal()">확 인</button>
            </div>
        </div>
    </div>
</div>

 <!--   <form>
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />

        <input type="text" name="keyword" placeholder="검색하고자 하는 값을 입력하세요.">
        <input type="text" name="page" placeholder="page">
        <input type="text" name="size" placeholder="size">
        <button type="submit" onclick="fetchData(event, false)" >검색</button>
        <<button type="submit" onclick="paging(event, true)" >다음 페이지</button>
    </form> -->

<script type="text/javascript">


let showDetailModal = (obj) => {
    document.querySelector('.modal-title').textContent = obj['place_name']
    for (const [key, value] of Object.entries(obj)) {
        console.log(key + ': ' + value);
        let target = document.querySelector(`.place_`+key);

        if(target) {
            if(key == "link" ) {
                var container = document.getElementById('map');
                		var options = {
                			center: new kakao.maps.LatLng(obj['y'], obj['x']),
                			level: 3
                		};

                		var map = new kakao.maps.Map(container, options);
            }
            target.textContent = value;
        }
    }

    let modal = document.querySelector('#DataDetail');
    modal.style.display = 'block';
}

let hideDetailModal = ()=> {
let modal = document.querySelector('#DataDetail');
modal.style.display = 'none';
}


(function() {

   console.log("Initial")
   fetchRank();

})();

async function fetchRank(){
    //e.preventDefault();
    removeRank();
    let rankParams = {num:10}
    const rankConfig = {
            method: 'GET',
            url: `/api/rank`,
            rankParams
        }

    try {
        let ranking = await axios(rankConfig);

       let rankList = ranking.data.data;
        console.log(rankList)
        let number = 1
        let elRankList = rankList.map((item) => {
            let rankItem = createEl('div', 'rank-item')

            let rankItemHead = createEl('div', 'rank-item-head')

            let rankKeyword = createEl('span', '')
            rankKeyword.textContent = number;
            number+=1;
            rankItemHead.appendChild(rankKeyword)

            let count = createEl('span', '')
            count.textContent = item.count;
            rankItemHead.appendChild(count)

            rankItem.appendChild(rankItemHead)

            let rankItemBody = createEl('div', 'rank-item-body')
            rankItemBody.textContent = item.keyword;
            rankItem.appendChild(rankItemBody)

            return rankItem;
        })

        console.log(elRankList)

        for(let el of elRankList) {
          document.querySelector('.rank-list').appendChild(el)
        }

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

function removeRank() {
    let parent = document.querySelector(".rank-list");
    while ( parent.hasChildNodes() ) {
    parent.removeChild( parent.firstChild );
    }
}

function removeData() {
    let parent = document.querySelector(".search-list");
    while ( parent.hasChildNodes() ) {
    parent.removeChild( parent.firstChild );
    }

    let parentPage = document.querySelector(".page-list");
    while ( parentPage.hasChildNodes() ) {
    parentPage.removeChild( parentPage.firstChild );
    }

}


async function fetchData(e, repeat){

    if(e) {

        e.preventDefault();
    }
    removeData();
    let keyword = document.querySelector('input[name=keyword]').value;
    if(keyword == null) {
    return;
    }
    //let page = document.querySelector('input[name=page]').value || 1;
    let page = e.page || 1;
    //let size = document.querySelector('input[name=size]').value || 15;
    let size = 15;

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

        console.log(res.data.data)
        let placeList = res.data.data.documents;
        let dataTotalCount = res.data.data.meta.total_count;
        console.log("count : " + dataTotalCount)
        let elPlaceList = placeList.map((item) => {
            let searchItem = createEl('div', 'search-item')
            searchItem.onclick = () => {
                showDetailModal(item)
            }
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

        drawPage(dataTotalCount, e);

    } catch(err) {
        console.log(err)
    }

    fetchRank();
}

function drawPage(total, event) {
    if(!event) {
    console.log("event is null")
    }

    console.log("total : " + total)
    let totalCount = total;
    let totalPage = parseInt((total/15)) +1;
console.log("totalCount : " + totalCount + " , totalPage : " + totalPage)

    for(let i=1;i<=totalPage; i++) {

            let pageItem = createEl('div', 'page-list');

            let pageButton = createEl('a','')

            pageButton.id="page-item"
            pageButton.onclick=function() {
                paging(event, true, i)
            //"paging('"+ event +"'\,'true'\,'"+i+"');"
            }
            pageButton.href="javascript:void(0);"

            pageButton.textContent = i;

            pageItem.appendChild(pageButton);
            document.querySelector('.page-list').append(pageItem)


    }

}

function paging(e, repeat, pageIndex) {
console.log("repeat. " + repeat)
console.log("paging. " + pageIndex)
console.log("e:" + e.keyword)
    e.page = pageIndex;
    fetchData(e,repeat);

}

</script>
</body>


</html>

