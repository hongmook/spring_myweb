<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title> 회원가입 </title>
<%@ include file="../common.jsp" %>

<%@ include file="../header.jsp" %>

<%@ include file="../menu.jsp" %>
</head>
<div class="row">
  <div class="leftcolumn">
    <div class="card">
      <h2>회원가입</h2>
      <hr>
      <div> 
	      <form name="memRegform" method="post" action="memberReg.do" onsubmit="return formCheck()">
	      <div class="b">
	      	<input name="id" type="text" placeholder="아이디" maxlength="10" required onchange="idcheck()"><P>
	      	<input type="hidden" id="isidcheck">
	      	<p id="idcheckmsg" style="color:red;"></p>
	      	
	      	<input name="pw" type="password" placeholder="비밀번호" maxlength="10" required><p>
	      	
	      	<input name="name" type="text" placeholder="이름">
	      	<span id="msg"></span>
	      </div>
	      	
	      <div style="text-align:center; margin:5px;">	
		      <fieldset style="width:45%; float:left;">
			      <legend>성별</legend>
			      <input type="radio" value="M" name="gender"> 남자
			      <input type="radio" value="F" name="gender"> 여자 
			  </fieldset>
		      
		      	
		      <fieldset style="width:45%; float:left;">
			      <legend>취미</legend>
			      <input type="checkbox" name="hobby" value="축구"> 축구
			      <input type="checkbox" name="hobby" value="야구"> 야구
			      <input type="checkbox" name="hobby" value="농구"> 농구
		      </fieldset>
	      </div>
	      	<p>
	      <div class="bb" style="text-align:center;">	
	      	<input type="text" placeholder="이메일" name="eid">@
	      	<input type="text" placeholder="도메인" name="Domain">
	      		<select name="selDomain" onchange="inputDomain()">
	      			<option value="" >직접입력</option>
	      			<option value="naver.com" >naver.com</option>
	      			<option value="daum.net" >daum.net</option>
	      			<option value="gmail.com" >gmail.com</option>
	       		</select>
	       <p>
	       		<textarea name="intro" style="width:100%; height:200px;" rows="5" cols="50" placeholder="자기소개(최대500자)"></textarea>
	       	</div>
	       	
	       	<input type="submit" value="회원등록">
	       	<input type="reset" value="취소">       	
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


<%@ include file="login_modal.jsp" %>
</body>
</html>



