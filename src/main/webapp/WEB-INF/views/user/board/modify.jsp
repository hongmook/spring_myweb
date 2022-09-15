<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    

<!DOCTYPE html>
<html>
<head>
<title> 게시판 등록 </title>
<%@ include file="../common.jsp" %>

<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>
</head>
<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>게시글 등록</h2>
      <hr>
      	<c:set value="${boardDetail}" var="board" />
      <div> 
	      <form name="boardConn" method="post" enctype="multipart/form-data" action="modify.bo">
	      <input type="hidden" name="seqno" value="${board.seqno }">
	      <input type="hidden" name="seqno" value="${board.seqno }">
	      <div>
	      	<input value="${board.title }" style="width:100%; height:40px;" name="title" type="text"  maxlength="30" required><P>
	      </div>
	      	
	      <div>	
		      <fieldset style="width:100%; text-align:left;">
			      <input type="radio" name="open" value='Y' 
			      		<c:if test="${board.open == 'Y' }"> checked </c:if>> 공개
			      <input type="radio" name="open" value='N' 
			      		<c:if test="${board.open == 'N' }"> checked </c:if>> 비공개
		      </fieldset>
	      </div>
	      	<p>
	      <div class="bb" style="text-align:center;">	
	       		<textarea name="content" style="width:100%; height:460px;" rows="5" cols="50">
	       				${board.content }</textarea>
	      </div>
	      	
	      	<!-- 첨부파일 --> 	
	       	<c:set value="${board.attachfile}" var="attachfile" />
	       	<c:choose>
	       		<c:when test="${ empty attachfile }">
	       			<input type="file" name="filename">
	       		</c:when>
	       		<c:when test="${ !empty attachfile }">
	       			<c:forEach items="${attachfile }" var="file">
	       				<c:set value="${file.filetype }" var="filetype" />
	       				<c:set value="${fn:substring(filetype, 0, fn:indexOf(filetype, '/')) }" var="type" />
	       				
	       				<div id="fileSector">	
		       				<c:if test="${type eq 'image' }">
		       					<c:set value="${file.thumbnail.filename }" var="thumb_file" />
		       					<img src="/upload/thumbnail/${thumb_file }">
		       				</c:if>
		       				${file.filename } (사이즈 : ${file.filesize })
		       				<input type="button" value="삭제" onclick="filedel('${file.no}','${file.savefilename}' ,'${file.filepath}', '${thumb_file }')">
	       				</div>
	       				
	       			</c:forEach>
	       		</c:when>
	       	</c:choose>
	       	<input type="submit" value="수정">
	       	<input type="button" onclick="location.href='boardList.bo'" value="취소">       	
	      </form>
      </div>
    </div>
    
<script>
	function filedel(no, savefile, filepath, thumb_filename){
		var ans = confirm("정말로 삭제하시겠습니까?");
		if (ans){
			var x = new XMLHttpRequest();
			x.onreadystatechange = function(){
				if(x.readyState === 4 && x.status === 200){
					var tag = document.getElementById("fileSector");
					
					 if(x.responseText.trim() === "0"){
						 alert('파일 삭제 실패');
					 } else {
						 alert('파일 삭제 성공');
						 tag.innerHTML = "<input type='file' name='filename'>"
					 }
				} else {
					console.log('에러코드 : ' + x.status);
				}
			};
		}
		
		x.open("POST","/file/filedel",true);
		x.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		x.send("no="+no+"&savefilename="+savefile+"&filepath="+filepath+"&thumb_filename="+thumb_filename);
	}
</script>    

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


<%@ include file="../member/login_modal.jsp" %>
</body>
</html>

