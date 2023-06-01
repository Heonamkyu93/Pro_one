<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<jsp:include page="loginheader.jsp"></jsp:include>
	<form action="./memberInfoUpdate"  onsubmit="return valch();" method="post">
		<table border="1">
			<tr>
				<th colspan="2">아이디:</th>
				<th><input type="text" readonly="readonly" id="peid" value=${peid} name='peid'></th>
				<th hidden="hi"></th>
				<th hidden="hi"></th>
			</tr>

			<tr>
				<th colspan="2">이름:</th>
				<th><input type="text" maxlength="15" id='pename' value=${pename} name='pename'></th>
				<th hidden="hi"></th>
				<th hidden="hi"></th>
			</tr>


			<tr>
				<th colspan="2">전화번호:</th>
				<th><input type="text" maxlength="11" id='pephonenumber' value=${pephonenumber} name='pephonenumber'></th>
				<th hidden="hi"></th>
				<th hidden="hi"></th>

			</tr>


			<tr>
				<th colspan="2">메일:</th>
				<th><input type="text" id='peemail' value=${peemail} name='peemail'></th>
				<th hidden="hi"></th>
				<th hidden="hi"></th>

			</tr>

			<tr>
				<th colspan="2">성별:</th>
				<th>남자:<input type="radio" name='pegender' checked value="m">여자:<input
					type="radio" name='pegender' value="f"></th>
				<th hidden="hi"></th>
				<th hidden="hi"></th>

			</tr>
			<tr>
				<th colspan="4" align="center"><input type="submit" value="변경"></th>

			</tr>
		</table>
	</form>
	
	<a href="./beforepwdchangeForm?peid=${peid}"><button>비밀번호변경</button></a>
	
	
	
	
	
	
	<script>
	var peid = document.getElementById('id');
	var pename= document.getElementById('pename');
	var pephonenumber = document.getElementById('pephonenumber');
	var peemail = document.getElementById('peemail');
	var test = document.getElementById('test');
	function valch(){
		if(pename.value.length ===0){
			pename.focus;
		//	test.innerText= "dddd";
			alert("이름을 입력해주세요");
			return false;
		}
		else if(pephonenumber.value.length ===0){
			alert('핸드폰 번호를 입력해주세요');
			pephonenumber.focus;
			return false;
		}
	/* 	else if(peemail.value.length ===0){
			alert('이메일을 입력해주세요');
			peemail.focus;
			return false;
		} */
		
	}
	
	</script>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>