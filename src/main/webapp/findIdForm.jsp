<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- CSS only -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="logoutheader.jsp"></jsp:include>

	<!-- <form class="form-inline" role="form" action="#"
		onsubmit="return validCheck()" method="post">
	 -->	<div class='container'>
			<div class='row'>
				<div class='col-md-3'></div>
				<div class='col-md-2'>가입했을때 등록한 이메일을 입력해주세요</div>
				<div class='col-md-3'>
					<input type="text" name='pemail' id='pemail' maxlength="45"
						class='form-control' onchange="emailreg();">
				</div>
				<div class='col-md-4'><strong id='em'></strong></div>
			</div>


			<div class='row'>
				<div class='col-md-4'></div>
				<div class='col-md-4' align="center">
					<input type="button" value="확인" class="btn btn-primary btn-lg" onclick="findidemailch();">
				</div>
				<div class='col-md-4'></div>
			</div>
		</div>





	<jsp:include page="footer.jsp"></jsp:include>
	<!-- JavaScript Bundle with Popper -->
	<script type="text/javascript">
	let emch = 1;
	
	function findidemailch(){
		let pemail =  document.getElementById('pemail').value;
		if (emch===1){
			alert('이메일 형식을 맞춰주세요.')
		} else{
			$.ajax({
				type : 'post',
				url : './findidemailch',
				data : {
					'pemail' : pemail
				},
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				success : function(res) {
					let json = JSON.parse(res);
					alert(json.al);
					let url = json.url;
					location.replace(url);
				}

			});
			
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	function emailreg(){
		emch = 1;
		let email = document.getElementById('pemail').value;
		let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		if(email.match(regExp)!= null){
			document.getElementById('em').innerText='';
			emch=2;
		}else{
			document.getElementById('em').innerText='이메일형식에 맞는 값을 입력해주세요.';	
			}

	}	
	
	
	
	function validCheck() {

			let pemail = document.getElementById('pemail');
			if (pemail.value == null || pemail.value == ""
					|| pepwd.value.length < 5) {
				alert("이메일을 입력해주세요.");
				document.getElementById('pemail').focus;
				return false;
			}else if (emch===1){
				alert('이메일을 확인해 주세요');
				return false;
			} 
		}
	</script>


	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>
</body>
</html>