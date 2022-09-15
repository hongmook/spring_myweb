<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
      <div> 
	      <form name="boardConn" method="post" enctype="multipart/form-data" action="boardCon.bo" onsubmit="return check()">
	      <div>
	      	<input style="width:100%; height:40px;" name="title" type="text" placeholder="제목" maxlength="30" required><P>
	      </div>
	      <c:set value="${loginUser }" var="id" />
	      	<input type="hidden" value="${id.id }" name="sess_id">
	      <div>	
		      <fieldset style="width:100%; text-align:left;">
			      <input type="radio" name="open" value='Y'> 공개
			      <input type="radio" name="open" value='N'> 비공개
		      </fieldset>
	      </div>
	      	<p>
	      <div class="bb" style="text-align:center;">	
	       		<textarea name="content" style="width:100%; height:460px;" rows="5" cols="50" placeholder="내용(최대500자)"></textarea>
	       		<input type="file" name="filename">
	       	</div>
	       	
	       	<input type="submit" value="등록">
	       	<input type="button" onclick="location.href='board.bo'" value="취소">       	
	      </form>
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


<%@ include file="../member/login_modal.jsp" %>
</body>
</html>

<script>
	function check(f){
		if(f.open.value == ""){
			alert("공개여부를 체크하세요");
			return false;
		}
		return true;
	}
</script>

