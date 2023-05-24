<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
	<form action="./certi">
		<table border="1">
			<tr>
				<th colspan="2"><h6>메일 인증</h6></th>
			</tr>
			<tr>
				<th>이메일:<input type="text" readonly="readonly" id='pe' name='pemail' value=${pemail}></th>
				<th>인증번호:<input type='text' maxlength="15"
					placeholder="인증번호를 입력하세요." name='certi'><br><i>인증번호를 입력하세요.</i></th>

			</tr>
			<tr>
				<th colspan="2"><input type="submit" value="인증확인"></th>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="인증번호 재발송"
					onclick="resend();" /></td>
			</tr>
		</table>
	</form>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
	<script type="text/javascript">
	
	function resend() {
			let pemail = document.getElementById('pe').value;
			alert(pemail);
			$.ajax({
						type : 'get',
						url : './resendmail',
						data : {
							'pemail' : pemail
						},
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						success : function(res) {
							alert(res);
						}

					});
		}

		/* function resend(){
		 alert('dd');
		 //	console.log(pemail);
		 //	alert("인증번호가 재발송되었습니다.메일이 도착하기까지 약간의 시간이 걸릴수있습니다.");
		 $.ajax({
		 type :"get",
		 url :"./resendmail" ,
		 data {"pemail":'pemail'},
		 contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		 seccess : function (res){
		 alert(res);
		 }
		
		
		 });
		 }

		 */
	</script>
</body>

</html>