<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<%
	String name = (String)session.getAttribute("sess_name");
	boolean isLogin = false;
	if(name != null){
		isLogin = true;
	}
 %>
 --%>
 
<c:set value="${loginUser }" var="loginuser" />
<div class="topnav">
  <a href="/index.jsp">홈</a>
  <a href="#">메뉴</a>
  <a href="boardList.bo">게시판</a>
  
<%-- 
<% if(isLogin) { %> 
	<%=name %>님 어서오세요
  <a id="modal1" href="/member/mypage.jsp">마이페이지</a>
  <a id="modal1" href="logout.do">로그아웃</a>
<% } else { %> 
  <a id="modal1" href="javascript:login()">로그인</a>
<% } %> 
--%>

	<c:if test="${loginuser != null}">
		${loginuser.name} 님 어서오세요
		<a id="modal1" href="/member/mypage.jsp">마이페이지</a>
		<a id="modal1" href="logout.do">로그아웃</a>
	</c:if>  
	
	<c:if test="${loginuser eq null}">
	  <a id="modal1" href="javascript:login()">로그인</a>
	</c:if>
</div>
