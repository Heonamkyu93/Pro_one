<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
div {
	padding: 10px;
}

textarea {
	resize: none;
}

.b {
	border: 1px solid #E6E6E6;
}

footer {
	padding: 10px;
	margin: 10px;
}

i {
	opacity: 0.6;
}
.c{
min-height: 600px;
border: 1px solid #E6E6E6;
}
</style>
<script>
	
</script>
</head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>
	<jsp:include page="loginheader.jsp"></jsp:include>

	<div class='container'>
		<div class="row">
			<div class="col-md-12" align="center"></div>
		</div>
		<div class="row">
			<div class="col-md-2 b">작성자</div>
			<div class="col-md-4 b">
				<input type="text" readonly="readonly" value='${peid}'
					class="form-control" id='peid' name='peid'>
			</div>
			<div class="col-md-2 b">작성일</div>
			<div class="col-md-4 b">
				<input type="text" readonly="readonly" value='${bodate}'
					class="form-control" id='bodate' name='bodate'>
			</div>

			<!-- 	<div class='row'>
				
				<div class="col-md-5" >
					<input type="file" name='bofile1' value="파일" style="margin: 15px">
					<input type="file" name='bofile2' value="파일" style="margin: 15px">
					<i>파일 업로드</i>
					</div>
					<div class="col-md-5">
						<input type="file" name='bofile3' value="이미지" 
							 style="margin: 15px"> <i>이미지 업로드</i><br>
						<i>파일용량은 10mb가 최대입니다.</i> 
					</div>
					<div class="col-md-2">
					<input type="submit" value="전송"
							class="btn btn-primary" style="margin-left: 15px">
							</div></div> -->

		</div>
	
		<div class='row'>
			제목 <br><br>
			<div class="col-md-12 b">
					v${botitle }
			</div>
		</div>
		<input type="hidden" value=${bosequence} id='bosequence'>
		<div class='row'>

	<div class='col-md-2'>내용</div>
	<div class='col-md-4'></div>
	<div class='col-md-6'>${file}</div>
<br><br>
			<div class="col-md-12 c">
				<p>${bocontent}</p>
			</div>
		</div>
			<div class='row '>
			<div class="col-md-3 b"></div>
				<div class="col-md-6 b">
				좋아요 싫어요 찍을곳 조회수 
				</div>
				<div class='col-md-3 b'>${update}&nbsp;&nbsp;&nbsp;&nbsp; ${delete}</div>
				</div>
		
	</div>
<div class='container'>
<div class='row'>
	<div class="col-md-12 b"  >
	<div class='row'>
	
	<div class="col-md-2 b" id='repeid' align="center">${repeid}</div>
	<div class="col-md-7 b"><input type="text" class="form-control" id='reple'></div>
	<div class="col-md-2 b" id='redate'></div>
	<div class="col-md-1 b"><button class="btn btn-primary" onclick="replein();">버튼</button></div>
	</div>
${reple}
<div class='row' id='gon'>
</div>
</div>
</div>
</div>

<script>

window.onload = function() {
	time();
}
function time() {
	var today = new Date();

	var year = today.getFullYear();
	var month = ('0' + (today.getMonth() + 1)).slice(-2);
	var day = ('0' + today.getDate()).slice(-2);

	var dateString = year + '-' + month + '-' + day;

	var today = new Date();

	var hours = ('0' + today.getHours()).slice(-2);
	var minutes = ('0' + today.getMinutes()).slice(-2);
	var seconds = ('0' + today.getSeconds()).slice(-2);

	var timeString = hours + ':' + minutes + ':' + seconds;
	var now = dateString + ' ' + timeString;
	document.getElementById('bodate').value = now;
	
	
	
	 let time = document.getElementById("redate");
	 time.innerText=now;
	
	
}

function init() {
	setInterval(time, 1000);
}

init();








function replein(){
	let reple=document.getElementById('reple').value;
	 let replediv = document.getElementById("repeid");
	 let divText = replediv.textContent;
	 let repeid = divText;
	 const bosequence=document.getElementById('bosequence').value;
	 
	 let datediv = document.getElementById("redate");
	 let dateText = datediv.textContent;
	 let redate = dateText;
	
	
	let json = {reple:reple,repeid:repeid,redate:redate,boSequence:bosequence}
	
	
	
/* 	let json3= JSON.stringify(json2);
	console.log(json3);	
	let json4= JSON.stringify(json3);
	console.log(json4);	
	let json5=JSON.parse(json4);
	console.log(json5);
	let json6=JSON.parse(json5);
	console.log(json6);
	let json7=JSON.parse(json6);
	console.log(json7);
	 let json8=JSON.parse(json7);
	console.log(json8); 
	
	 */
	
	$.ajax({
		type : 'post',
		url : './repleIn',
		 dataType : 'json',
		data : {json:JSON.stringify(json)},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(res) {
		/* 	document.getElementById('gon').innerHTML= "<div class='col-md-2 b' align='center'>"+res.repeid+
			"</div><div class='col-md-7 b'>"+res.reple+"</div><div class='col-md-3 b'>"+res.redate+"</div>";
		 */	location.reload();
		}

		
		
	}); 
	
	
}






</script>




	<jsp:include page="footer.jsp"></jsp:include>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
		
		
		
		
</body>
</html>