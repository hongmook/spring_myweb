/**
 * 2022년 6월 2일
 * 작성자 : 강홍묵
 * 내용 : 회원가입 폼체크
 */
 // 로그인창
	function login() {
		document.getElementById("modal").style.display = "block";		
	
		document.getElementById("container").style.display = "block";		
	}
	
	function clo() {
		document.getElementById("modal").style.display = "none";		
		
		document.getElementById("container").style.display = "none";		
	}
	
// 비밀번호 오류시 로그인창
	function init(){
		var msg = document.getElementsByName("msg");
		//alert(msg);
		var alert_msg;
		var modal_pop;
		if(msg[0].value == "loginOk") {
			alert_msg ="로그인이 되었습니다";
			modal_pop = false;
		}
		
		if(msg[0].value == "memberOk"){
			alert_msg ="회원등록이 되었습니다.";
			modal_pop = true;
				
		};
		
		if(msg[0].value == "loginFail"){
			alert_msg ="로그인 정보가 없습니다.";
			modal_pop = true;
			
		}
		
		if(msg[0].value != "null") alert(alert_msg);
		if(modal_pop == true) {
			document.getElementById("modal").style.display = "block";
		}
	
	}
		
		
		
		


//회원가입 체크
		//중복검사
	
	function idcheck() {
		//id 있는경우 '아이디를 확인하세요' 경고메세지 주고, 커서를 id 입력난에 포커스가 나타나도록
		/* button 형식
		var idc = document.forms["memRegform"]["id"].value;
		if(idc == ""){
			alert("아이디를 입력하세요");
			document.forms["memRegform"]["id"].focus(); 
		}
		alert("아이디 입력함");*/
		
		var id = document.forms["memRegform"]["id"].value;
		
		var x = new XMLHttpRequest();
		
		x.onreadystatechange = function(){
			
			var msg = document.getElementById("idcheckmsg");
			
			//4: request finished and response is ready
			if(x.readyState === 4 && x.status === 200){
				console.log("ok");
				var rsp = x.responseText.trim(); //trim 앞뒤 공백 삭제
				document.getElementById("isidcheck").value = rsp;
				
				if(rsp == 0){
					msg.innerHTML="사용가능한 아이디 입니다.";
				} else {
					msg.innerHTML="중복된 아이디 입니다.";
				}
				
			} else {
				//오류발생 : 403,404
				console.log("서버에러(403,404)");
			}
		};
		
	/*	
		//전송방식, 요청문서, 비동기식 요청
		x.open("get", "/member/idserch.jsp?id=" + id, true);
		x.send();
	*/
		x.open("get", "/idDoubleCheck.do?id=" + id, true);
		x.send();
	}
	
	
	
	function formCheck() {
		//비밀번호 체크
		var pw = document.forms["memRegform"]["pw"].value;
		//alert(pw); - 안내창 띄우기  
		if(pw.length < 6) {
			alert("비밀번호 문자길이를 확인하세요.");
			return false;
		}	
		
		//이름체크
		if(name = document.forms["memRegform"]["name"].value.length < 1) {
			alert("이름을 입력하세요.");
			//document.getElementById("msg").innerHTML = "이름을 입력하세요";
			return false;
		}
		
		//성별체크
		var gender = document.forms["memRegform"]["gender"].value;
		if(gender == ""){
			alert("성별을 체크하세요.");
			return false;
		} 
		
		//취미체크
		var hobby_length = document.forms["memRegform"]["hobby"].length;
		
		//반복문
		for(var i=0; i < hobby_length ; i++){
		
			if(document.forms["memRegform"]["hobby"][i].checked){
				//alert(i + "번째 취미가 선택되었음");
				console.log(i + "번째 취미가 선택되었음");
				break;
			}
		}	
		
			if(i == hobby_length){
				return false;
			} 
	}
	
	function inputDomain(){
		console.log("도메인선택함");
		var sel = document.forms["memRegform"]["selDomain"].value;
		console.log("선택 옵션값 : " + sel);
		document.forms["memRegform"]["Domain"].value = sel;
		
		if (sel != ""){
			document.forms["memRegform"]["Domain"].readOnly = true;
			document.forms["memRegform"]["Domain"].style.backgroundColor = 'lightgray';
		} else {
			document.forms["memRegform"]["Domain"].readOnly = false;
			document.forms["memRegform"]["Domain"].focus; //커서 생기게 하는거
			document.forms["memRegform"]["Domain"].style.backgroundColor = 'white';
		}
	}
	