/**
 * 2022.9.26 댓글 모음
 */
 
 console.log("Reply Module start")
 
 var replyService = (function(){
	
	function add(reply, callback){
		console.log("reply add...")		
	
		$.ajax({
			type : 'post',
			url  : '/reply/add',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8", //제이슨 형태로 던져주겠다
			success : function(result, status, xhr){
				if(callback){
					callback(result);
				}	
			},
			error : function(xhr, status, er){
				if(error){
					error(er)
				}
			}
		});
	} 

	function getList(param, callback, error){
		var bno = param.bno;
		var page = param.page || 1; //없으면 ||(or 연산자) 초기값 1로 설정
		
		$.getJSON("/reply/list" + bno + "/" + page + ".json", function(data){
			if(callback){
				callback(data);
			}	
			
		}).fail(function(xhr, status, err){//.fail은 실패하면 실행
			if(err){
				error();
			}
		}); 
	}

	return {
			add:add,
			getList : getList
			};
	
})(); //뒤에 ()는 바로 실행
 