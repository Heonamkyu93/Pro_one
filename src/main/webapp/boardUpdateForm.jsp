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
			<div class="col-md-12" align="center" >글쓰기</div>
		</div>
		<form action="./boardUpdate" method="post"  enctype="multipart/form-data" onsubmit='return vali();'>
		<input type="hidden" value="${bosequence}" name='bosequence' > 
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
					
				<div class='row'>
				
				<div class="col-md-10" >
					<input type="file" name='bofile1' id='bofile1'  style="margin: 15px" onchange="prefileremove1();"><button type="button" class="btn btn-danger"onclick="upcancle(1);" >취소</button>
					<input type="file" name='bofile2' id='bofile2'  style="margin: 15px" onchange="prefileremove2();"><button type="button" class="btn btn-danger"onclick="upcancle(2);">취소</button>
					<input type="file" name='bofile3' id='bofile3'  style="margin: 15px" onchange="prefileremove3();"> <button type="button" class="btn btn-danger"onclick="upcancle(3);" >취소</button>
					</div>
						
					<div class="col-md-2">
										<i>파일용량은 10mb가 최대입니다.</i><br></div></div>
						<div class='row'>${file} <i>파일을 새로 올리면 기존의 파일은 삭제됩니다</i>
						</div>			<div class="col-md-4"><button type="button" class="btn btn-danger"onclick="prefileremove1();" >삭제</button></div>
						<div class="col-md-4"><button type="button" class="btn btn-danger"onclick="prefileremove2();" >삭제</button></div>
						<div class="col-md-4"><button type="button" class="btn btn-danger"onclick="prefileremove3();" >삭제</button></div>					
			</div>
			<div class="row">
				<div class="col-md-1" align='center'>제목</div>
				<div class="col-md-10"></div>
				<div class="col-md-1">
					<i id='e2'></i>
				</div>
			</div>
			<div class='row'>

				<div class="col-md-12">
					<input type="text" class="form-control" placeholder="제목을 입력하세요.50자"
						id='botitle' name='botitle' onkeyup="title_count();" value="${botitle}">
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-1" align='center'>내용</div>
				<div class="col-md-10"></div>
				<div class="col-md-1">
					<i id='e'></i>
				</div>
			</div>
			<div class='row'>

				<div class="col-md-12">
					<textarea class="form-control" rows="15" cols="1000"
						maxlength="3990" placeholder="내용을 입력하세요. 글자수제한 3990" 
						id='bocontent' name='bocontent' onkeyup="cont_count();">${bocontent}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-12" align='center'>
					<button type="submit" class="btn btn-primary">글쓰기</button>
					<button type="button" class="btn btn-primary" onclick="back();">취소</button>
				</div>

			</div>
		</form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>

	<script>
	
	function prefileremove1(){
		let fi=document.getElementById('prediv1');
		let fi2=document.getElementById('prefilenum1');
		fi.innerText='';
		fi2.value=fi2.value+"/delete";
	}
	function prefileremove2(){
		let fi=document.getElementById('prediv2');
		let fi2=document.getElementById('prefilenum2');
		fi.innerText='';
		fi2.value=fi2.value+"/delete";
	}

	function prefileremove3(){
		let fi=document.getElementById('prediv3');
		let fi2=document.getElementById('prefilenum3');
		fi.innerText='';
		fi2.value=fi2.value+"/delete";
	}

	
	function pre(file){
		   text = $(file).text()
	}
	
	
	function back(){
		location.href="./boardList"
	}
		function upcancle(a){
	
		if(a===1){document.getElementById('bofile1').value ='';
		
		
		}
		else if (a===2){
			document.getElementById('bofile2').value='';	
		}else if (a===3){
			document.getElementById('bofile3').value='';	
		}
		
		
		}
		
		
		
		
		
		
		
		
		
	function vali(){
		let botitle=document.getElementById('botitle');
		let bocontent=document.getElementById('bocontent');
		if(botitle.value.length===0){
			alert('제목입력은 필수입니다.');
			return false;
		}else if(bocontent.value.length===0){
			alert('내용입력은 필수입니다.');
			return false;
		}
		
	}
	
	
	
	
	
	function cont_count() {
			let con = document.getElementById('bocontent').value;
			let count = con.length;
			document.getElementById('e').innerText = count + "/3990";

		}
		function title_count() {
			let con = document.getElementById('botitle').value;
			let count2 = con.length;
			document.getElementById('e2').innerText = count2 + "/50";
		}

		window.onload = function() {
			time();
		}
		function time() {
			var today = new Date();

			var year = today.getFullYear();
			var month = ('0' + (today.getMonth() + 1)).slice(-2);
			var day = ('0' + today.getDate()).slice(-2);

			var dateString = year + '-' + month + '-' + day;

			console.log(dateString);
			var today = new Date();

			var hours = ('0' + today.getHours()).slice(-2);
			var minutes = ('0' + today.getMinutes()).slice(-2);
			var seconds = ('0' + today.getSeconds()).slice(-2);

			var timeString = hours + ':' + minutes + ':' + seconds;
			var now = dateString + ' ' + timeString;
			document.getElementById('bodate').value = now;
		}

		function init() {
			setInterval(time, 1000);
		}

		init();
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>