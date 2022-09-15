<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.khm.dto.*" %>
<%@ page import="java.util.*" %>


<% 
Board board = (Board)request.getAttribute("boardDetail");
List<Reply> reply = board.getReply(); 
%>

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
    <div class="card" >
		<button type="button" onclick="location.href='/board/board_change.jsp?no='">수정</button>
		<button type="button" onclick="location.href='/board/boardDelete.jsp?no='">삭제</button>
		<hr>
	
	<table class="detail">
		<thead>
			<tr>
				<th colspan='3'><%=board.getTitle() %></th>
		     </tr>
		</thead>
		<tbody>
			<tr>
				<td><%=board.getWdate() %></td>
				<td><%=board.getName() %></td>
				<td><%=board.getCount() %></td>
			</tr>
			<tr>
				<td colspan='3'><%=board.getContent() %></td>
			</tr>
		</tbody>
	</table>
	<hr>
	
	<form method="post" action="/replayProc.jsp">	
		<input type="hidden" name="board_seqno" value="">
		<textarea name="comment" style="width:100%; height:60px;" rows="5" cols="50" placeholder="댓글을 입력하세요"></textarea>
		<input style="float:right" type="submit" value="등록">
	</form>
	
	
	<table class="detail">
	<thead>
		<tr>
			<th>작성자</th>
			<th colspan='2'>댓글내용</th>
			<th>작성일자</th>
		</tr>
	</thead>
	<% for(Reply rss : reply) {%>
		<tbody>
			<tr>
				<td><%=rss.getName()%></td>
				<td colspan='2'><%=rss.getComment() %></td>			
				<td><%=rss.getWdate()%></td>
			</tr>
		</tbody>
	<% } %>
 
<%-- 	
	<%  int num=0;
		for(int i=0; i<reply.size(); i++) {
		Reply r = reply.get(i);
		num++;%>
		<tbody>
			<tr>
				<td><%=r.getName()%></td>
				<td colspan='2'><%=r.getComment() %></td>			
				<td><%=r.getWdate()%></td>
			</tr>
		</tbody>
	<% } %>
--%> 		
	</table>
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

