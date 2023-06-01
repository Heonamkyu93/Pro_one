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
	<jsp:include page="loginheader.jsp"></jsp:include>

	<form class="form-inline" role="form" action="./repwd"
		onsubmit="return validCheck()" method="post">
		<input type="hidden" value=${peid} name='peid'> 
		<div class='container'>
			<div class='row'>
				<div class='col-md-4'></div>
				<div class='col-md-1'>새로운비밀번호</div>
				<div class='col-md-3'>
					<input type="password" name='pepwd' id='pepwd' maxlength="15"
						class='form-control'>
				</div>
				<div class='col-md-4'></div>
			</div>


			<div class='row'>
				<div class='col-md-4'></div>
				<div class='col-md-1'>비밀번호확인</div>
				<div class='col-md-3'>
					<input type="password" name='pepwd2' id='pepwd2' maxlength="15"
						class='form-control'>
				</div>
				<div class='col-md-4'></div>
			</div>
			<div class='row'>
				<div class='col-md-4'></div>
				<div class='col-md-4' align="center">
					<input type="submit" value="등록" class="btn btn-primary btn-lg">
				</div>
				<div class='col-md-4'></div>
			</div>
		</div>
	</form>





	<jsp:include page="footer.jsp"></jsp:include>
	<!-- JavaScript Bundle with Popper -->
	<script type="text/javascript">
		function validCheck() {

			var pepwd = document.getElementById('pepwd');
			var pepwd2 = document.getElementById('pepwd2');
			
			
			
			if (pepwd.value == null || pepwd.value == ""
					|| pepwd.value.length < 6) {
				alert("비밀번호는 6자이상 필수 입력값입니다.");
				document.getElementById('pepwd').focus;
				document.getElementById('pepwd').style.color = 'red';
				return false;
			} else if (pepwd2.value == null || pepwd2.value == ""
					|| pepwd2.value.length < 6) {
				alert("비밀번호와 비밀번호 확인은  6자이상 필수 입력값이며 서로 일치해야 합니다.");
				document.getElementById('pepwd2').focus;
				document.getElementById('pepwd2').style.color = 'red';
				return false;
			} else if (pepwd.value != pepwd2.value) {
				alert("비밀번호와 비밀번호 확인은  6자이상 필수 입력값이며 서로 일치해야 합니다.");
				document.getElementById('pepwd').focus;
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