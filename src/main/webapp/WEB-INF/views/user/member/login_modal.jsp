<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- 로그인 팝업 -->
	<div id="modal" >
		<div id="container">
			<a class="a" href="javascript:clo()">X</a>
		
			<form method="post" action="/login">
				<input name="id" id="id" type="text" placeholder="아이디" required><br>
				<input name="pw" id="pw" type="password" placeholder="비밀번호" required><p>
				<input type="submit" value="로그인">
			</form>
		
			<p>
			<div class="aaa">
				<a href="/member/memregform">회원가입</a> | <a href="#">아이디 찾기</a> | <a href="#">비밀번호 찾기</a>
			</div>
		</div>
	</div>