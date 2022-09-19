<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title> board </title>

<%@ include file="../header.jsp" %>

<%@ include file="../common.jsp" %>

<%@ include file="../menu.jsp" %>



</head>


<link rel="stylesheet" href="/css/board.css">
<script src="https://kit.fontawesome.com/737ede07db.js" crossorigin="anonymous"></script>


<div class="row">
  <div class="leftcolumn">
    <div class="card">
    	<h1><i class="fa-solid fa-clipboard-list"></i>&nbsp;게시판</h1>
    	<c:set value="${loginUser }" var="user" />
    	<c:if test="${user.id != null}">
			<button type="button" onclick="location.href='/board/regForm'" id="reg"> 등록 </button> <br>   	
		</c:if>
    <hr>
    
    <form name="search" method ="post" action="/board/list">
    
    <input type="hidden" name="currentPage" value="${pageMaker.cri.currentPage }" />
    	<select name="searchField">
    		<option value="title" 
    		<c:if test="${pageMaker.cri.searchField == 'title'}">selected</c:if>>제목</option>
    		<option value="name"
    		<c:if test="${pageMaker.cri.searchField == 'name'}">selected</c:if>>이름</option>
    	</select>
    
		<input type="text" name="searchText" placeholder="search" value="${pageMaker.cri.searchText }">
    	<input type="button" value="검색" onclick="javascript:document.forms['search'].submit()">
    <!-- 페이지당 레코드 수 -->	
    <select name="rowPerPage" onchange="goAction()">
    <c:forEach var="i" begin="5" end="40" step="5">
    	<option value="${i}" 
    		<c:if test="${i == pageMaker.cri.rowPerPage}">selected</c:if>
    	>${i}개씩</option>
    </c:forEach>	
    </select>
    </form>
    
    
    <script type="text/javascript">
    	function goAction(){
    		document.forms['search'].submit();
    	}
    </script>
    
		<table class="c">
			  <thead>
				  <tr>
				    <th id="b">순번</th>
				    <th>제목</th>
				    <th>작성일자</th>
				    <th>조회수</th>
				    <th>작성자</th>
				  </tr>
			  </thead>
			  <tbody>

			<c:forEach items="${board}" var="rs">			  
				  <tr style="cursor:pointer" onclick="location.href='/board/detail?seqno=${rs.getSeqno()}'">
				    <td id="b"> ${rs.getNo()}</td>
				    <td>${rs.getTitle()}</td>
				    <td>${rs.getWdate()}</td>
				    <td>${rs.getCount()}</td>
				    <td>${rs.getName()}</td>
				  </tr>
			</c:forEach>

			  </tbody>
		</table>
	<div id="board_modal">
		<div id="contain">
			<h2 id="board-title"></h2>
			<a href="javascript:clos()" style="float:right">닫기</a>
			<hr>
			<p id="board-content"></p>
		</div>
	</div>
	 <p>
	 <p>
	 <p>총 레코드개수 : ${pageMaker.totalPage }</p>
	    <div class="pagination">
	    <c:if test="${pageMaker.prev }">
  			<a href="/board/list?currentPage=${pageMaker.startPage-1 }&rowPerPage=${pageMaker.cri.rowPerPage}">&laquo;</a>
  		</c:if>	
  			<c:forEach var="num" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
	  			<a href="/board/list?currentPage=${num}&rowPerPage=${pageMaker.cri.rowPerPage }" 
	  			 class="${pageMaker.cri.currentPage == num ? "active" : "" }">${num }</a>
  			</c:forEach>
<!-- <a class="active" href="#">2</a> -->
	    <c:if test="${pageMaker.next }">
  			<a href="/board/list?currentPage=${pageMaker.endPage+1 }&rowPerPage=${pageMaker.cri.rowPerPage}">&raquo;</a>
  		</c:if>	
		</div>
	    
    </div>
  </div>
  <div class="rightcolumn">
    <div class="card">
      <h2>About Me</h2>
      <div class="fakeimg" style="height:100px;">Image</div>
      <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
    </div>
    <div class="card">
      <h3>Popular Post</h3>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
      <div class="fakeimg"><p>Image</p></div>
    </div>
    <div class="card">
      <h3>Follow Me</h3>
      <p>Some text..</p>
    </div>
  </div>
</div>

<%@ include file="../footer.jsp" %>
<!-- 화면 끝 -->

<%@ include file="../member/login_modal.jsp" %>

</body>
</html>