<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<c:set value="${loingUser }" var="loginuser"/>
	<div>
		<button id="disconnectBtn">뒤로</button>
	</div>
	
	<div id="msgArea">
		<input type="text" id="msg" name="msg" placeholder="메세지를 입력하세요">
		<input type="button" value="전송" id="sendBtn">
	</div>

</body>
<script>
	var userid = '${loginUser.id}';
	var chatNo = '<c:out value="${chatNo}"/>';
	var ws;

	console.log("채팅방 번호 : " + chatNo);
	
	connect();
	
	function connect(){
		ws = new WebSocket("ws://localhost:8088/chatServer");
		
		ws.onopen = function(){
			console.log('연결생성');
			sendMsg("enter","");
			$("#msg").focus();
		};
		
		ws.onmessage = function(e){
			console.log('받은메세지 : ' + e.data)
			addMsg(e.data);
		};
		
		ws.onclose = function(){
			console.log('연결 종료');
		};
	}
	
	function addMsg(data){
		var tag = '<div stlye="margin-bottom:3px;">';
			tag += data;
			tag += '<sapn style="font-size:11px; color:#gray;">';
			tag += new Date().toLocaleTimeString();
			tag += '</span>'
			tag += '</div>'
			
			$('#msgArea').append(tag);
	}
	
	$('#sendBtn').on("click",function(){
				
		sendMsg("chat", $('#msg').val());
		
		$('#msg').val('');
		$('#msg').focus();
	});
	
	//enter로 메세지 전송
	$('#msg').keydown(function(){
		if(event.keyCode == 13){ //enter 아스키 코드가 13
			sendMsg("chat", $('#msg').val());
			
			$('#msg').val('');
			$('#msg').focus();
		}
	});
	
	//메세지 전송
	function sendMsg(step, msg){
		var msg = {
			step : step,
			chatNo : chatNo,
			userid : userid,
			msg : msg
		};
		
		ws.send(JSON.stringify(msg));
	}
	
	$('#disconnectBtn').click(function(){
		location.replace("/chatList");
		sendMsg("out","");
		
		//서버 @onClose호출
		ws.close();
	});
</script>
</html>