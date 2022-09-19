<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	<c:set value="${boardDetail}" var="board" />
	<c:set value="${loginUser }" var="user" />
	
		<c:if test="${user.id eq board.id}">
		<button type="button" onclick="location.href='/board/detail?seqno=${board.seqno}&page=modify'">수정</button>
		<button type="button" onclick="del_confirm('${board.seqno}')">삭제</button>
		</c:if>
		<hr>

<script>
function del_confirm(seqno){
	var rs = confirm('정말로 삭제하시겠습니까?');
	if (rs){
		location.href="boardDelete.bo?seqno=" + seqno;
	}
}
</script>	
	
	<table class="detail">
		<thead>
			<tr>
				<th colspan='3'>${board.title}</th>
		     </tr>
		</thead>
		<tbody>
			<tr>
				<td>${board.wdate}</td>
				<td>${board.name}</td>
				<td>${board.count}</td>
			</tr>
			<tr>
				<td colspan='3'>${board.content}</td>
			</tr>
		</tbody>
	</table>
	<hr>
	
	<!-- 첨부파일 -->
	<div>
		<c:set value="${board.attachfile}" var="attachfile" />
		<c:if test="${attachfile != null }">
			<c:forEach items="${attachfile}" var="file">
			
				<form name="filedown" method="post" action="/file/fileDown">
					<input type="hidden" name="filename" value="${file.filename }">
					<input type="hidden" name="savefilename" value="${file.savefilename }">
					<input type="hidden" name="filepath" value="${file.filepath }">
		
					<c:set value="${file.filetype}" var="filetype" />
					<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype,'/')) }" var="type" />
					
					<c:if test="${type eq 'image' }">
						<c:set value="${file.thumbnail.filename}" var="thumb_file" />
						<img src="/upload/thumbnail/${thumb_file}">
					</c:if>
					${file.filename} (사이즈: ${file.filesize} bytes)
					<input type="submit" value="다운로드">
				</form>
				
			</c:forEach>
		</c:if>
	</div>
	
	<hr>
	
	<form method="post" action="/replayProc.bo?seqno=${board.seqno}">	
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
	<c:set value="${board.reply}" var="reply" />
	<c:if test="${reply != null}">
		<c:forEach items="${reply}" var="r">
			<tbody>
				<tr>
					<td>${r.name}</td>
					<td colspan='2'>${r.comment}</td>			
					<td>${r.wdate}</td>
				</tr>
			</tbody>	
		</c:forEach>
 	</c:if>
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

