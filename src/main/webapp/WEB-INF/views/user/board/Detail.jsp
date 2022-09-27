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
		<!-- 댓글 등록 폼 -->
		<div id ="replyInput">
			<textarea id="comment" name="comment" style="width:100%; height:60px;" rows="5" cols="50" placeholder="댓글을 입력하세요"></textarea>
			<button id="addReplyBtn">댓글등록</button>
		</div>
	
	<!-- 댓글 리스트 출력 영역  -->
	<div id="reply-list">
		<ul class="reply_ul">
			<li data-rno='58'>
				<div>작성자 | 작성일자 | 댓글내용</div>
			</li>
		</ul>		
	</div>
	
	<!-- 댓글 페이지 리스트 출력 -->
	<div class="reply-page-list">
	
	</div>
	
<%-- 	<table class="detail">
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
	</table> --%>
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
	
	<!-- 댓글 모달 -->
	<div id="reply_modal">
		<div id="modal2" >
			<div id="container2">
				<!-- <a class="a" href="javascript:clo()">X</a> -->
				<button class="a" id="modalCloseBtn">&#10062;</button>

				<textarea name="content" rows="5" cols="30"></textarea>

				<button id="replyModifyBtn">댓글수정</button>
				<button id="replyDeleteBtn">댓글삭제</button>
			</div>
		</div>
	</div>

<%@ include file="../member/login_modal.jsp" %>

<script type="text/javascript" src="/js/reply.js"></script>

<script>
/* 즉시 실행 함수(IIFE) 
 * (function(){
		 문장;
 	})();
 */
 
 var seqno = '<c:out value="${board.seqno}"/>'
 var id = '<c:out value="${user.id}"/>'
 
$(document).ready(function(){
	console.log(replyService)
	
	console.log("=======================");
	console.log("Reply get LIST");
	
	var modal = $("#reply_modal");
	var modal_content = modal.find("textarea[name='content']");
	
	modal.hide();
	
	$("#modalCloseBtn").on("click", function(e){
		modal.hide();
	});
	
	$(".reply_ul").on("click","li",function(e){
		
		var rno = $(this).data("rno");
		replyService.get(rno, function(data){
			console.log("REPLY GET DATA")
			console.log("댓글" + rno + "번 내용 : " + data.content);
			modal_content.val(data.content);
			modal.data("rno", data.seqno);			
		});
		
		modal.show();
	});
	
	/* 댓글 등록 버튼 클릭 시 */
	$("#addReplyBtn").on("click", function(e){
		var comment = document.getElementById("comment").value;
		
		/* 변수 객채화 */
		var reply ={
		/* dto랑 똑같아야함 : sql이랑 같아야함 */
			boardNo : seqno,
			id : id,
			comment : comment
		};
		
		replyService.add(reply, function(result){ //result는 js에서 정해준 값을 넣어줄수 있음
			alert(result);
			document.getElementById("comment").value = ""
			showList(1);			
		}); 
	});
	
	/* 댓글 수정버튼 클릭시 */
	$("#replyModifyBtn").on("click", function(e){
		console.log("댓글 수정 번호 : " + modal.data("rno"));
		console.log("댓글 수정 내용 : " + modal_content.val());
		
		var reply = {seqno : modal.data("rno"), 
					 content : modal_content.val() };
		
		replyService.update(reply, function(result){
			alert(result);
			modal.hide();
			showList(1);
		});
	});
	
	/* 댓글 삭제 */
	$("#replyDeleteBtn").on("click", function(e){
		var rno = modal.data("rno");
		console.log("댓글 삭제 번호 : " + rno);
		
		replyService.remove(rno, function(result){
			alert(result);
			modal.hide();
			showList(1);
		});
		
	});
	
	
	showList(1);
	
	function showList(page){
		replyService.getList({bno:seqno, page:1}, function(list){
			
		/* 댓글이 없는 경우 */
		if(list == null || list.length==0){
			$(".reply_ul").html("");
			return; //리턴만 하면 함수가 종료가 됨
		}
		
		/* 댓글이 있는 경우 */
		var str = "";
			for(var i =0, len=list.list.length || 0; i < len; i++){
				console.log(list.list[i]);
				str += "<li data-rno='" + list.list[i].seqno + "'><div class='replyRow'>" + list.list[i].rn + " | " + list.list[i].id;
				str += " | " + list.list[i].wdate + " | " + list.list[i].content + "</div></li>"
			}
			
			$(".reply_ul").html(str);
		});
	}
	
	showReplyPage(18);
	/* 댓글 페이지 리스트 출력 */
	function showReplyPage(replyCnt){
		
		var currentPage = 1;
		
		var endPage = Math.ceil(currentPage/5.0)*5;
		var startPage = endPage - 4;
		console.log("endNum : " + endPage);
		
		var prev = startPage != 1;
		var next = false;
		
		if(endPage*5 >= replyCnt){
			endPage = Math.ceil(replyCnt/5.0);
		}
		if(endPage*5 < replyCnt){
			next = true;
		}
		
		var str = "<ul class='pageUL'>"
		if(prev){
			str += "<li class='page-link'>";
			str += "<a href='" + (startPage - 1) + "'> 이전페이지 </a></li>";
		}
		
		for(var i=startPage; i <= endPage; i++){
			var active = currentPage == i ? "active" : "";
			str += "<li class = 'page-link " + active + "'>"; //띄어쓰기 주의
			str += "<a href='" + i + "'>" + i + "</a></li>";
		}
		
		if(next){
			str += "<li class='page-link'>";
			str += "<a href='" + (endPage+1) + "'> 다음페이지 </a></li>";
		}
		
		str += "</ul>"
		console.log(str);
		$(".reply-page-list").html(str);
	}
	
});
</script>



</body>
</html>

