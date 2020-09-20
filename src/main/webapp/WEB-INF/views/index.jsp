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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=65aabc830c37847615ba8956020dc5dd&libraries=services,clusterer,drawing"></script>

</head>
<body>

  <div id="Rank">
    <div class="header">
      <div class="title">인기 키워드 랭킹</div>
        </div>
    <div class="rank-list">
      <div class="rank-item">
        <div class="rank-item-body">
            <span class="rank"></span>
            <span class="title"></span>
            <span class="count"></span>
        </div>
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
               <!--<input type="text" name="page" placeholder="page">
               <input type="text" name="size" placeholder="size"> -->
            <!-- <span></span> -->
          </div>
          <button class="search-btn" type="submit" onclick="fetchData(event, false)">검색</button>
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
        <div></div>
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
            <div class="content-title">X</div>
            <div class="content-content place_x"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">Y</div>
            <div class="content-content place_y"></div>
            </div>
            </div>
            <div class="content-item-box">
            <div class="content-item">
            <div class="content-title">바로가기</div>
            <div class="content-content place_link"> </div>
            <div id="map" style="position:relative;width:300px;height:200px;"></div>
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

<script type="text/javascript">

let nowPage=1

let showDetailModal = (obj) => {
    document.querySelector('.modal-title').textContent = obj['place_name']
    for (const [key, value] of Object.entries(obj)) {
        let target = document.querySelector(`.place_`+key);

        if(target) {
            target.textContent = value;
        }
    }
    let modal = document.querySelector('#DataDetail');
    modal.style.display = 'block';

        let container = document.getElementById('map');
        let options = {
            center: new kakao.maps.LatLng(obj['y'], obj['x']),
            level: 3
        };

        let map = new kakao.maps.Map(container, options);

}

let hideDetailModal = ()=> {
let modal = document.querySelector('#DataDetail');
modal.style.display = 'none';
}


(function() {

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
        let number = 1
        let elRankList = rankList.map((item) => {
            let rankItem = createEl('div', 'rank-item')
            let rankItemBody = createEl('div', 'rank-item-body')

            let rankKeyword = createEl('span', 'rank')
            rankKeyword.textContent = number + '위';
            number+=1;
            rankItemBody.appendChild(rankKeyword)

            let title = createEl('span', 'title')
            title.textContent = item.keyword;
            rankItemBody.appendChild(title)

            let count = createEl('span', 'count')
            count.textContent = item.count+'회';
            rankItemBody.appendChild(count)

            rankItem.appendChild(rankItemBody)

            return rankItem;
        })

        for(let el of elRankList) {
          document.querySelector('.rank-list').appendChild(el)
        }

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

        let placeList = res.data.data.documents;
        let dataTotalCount = res.data.data.meta.total_count;

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

    let totalCount = total;
    let totalPage = parseInt((total/15)) +1;

    for(let i=1;i<=totalPage; i++) {

            //let pageItem = createEl('div', 'page-list');

            let pageDiv = createEl('div',"page-item");
            let pageButton;
            if(nowPage==i){
                pageButton = createEl('a','"page-item-link page-item-link-now')
            }else{
                pageButton = createEl('a','"page-item-link')
            }

            pageButton.onclick=function() {
                paging(event, true, i)
            //"paging('"+ event +"'\,'true'\,'"+i+"');"
            }
            pageButton.href="javascript:void(0);"

            pageButton.textContent = i;

            pageDiv.appendChild(pageButton)
            //pageItem.appendChild(pageButton);
            document.querySelector('.page-list').append(pageDiv)


    }

}

function paging(e, repeat, pageIndex) {

    e.page = pageIndex;
    nowPage=pageIndex;
    fetchData(e,repeat);

}

</script>
</body>


</html>

