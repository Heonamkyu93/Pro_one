<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<body>
<jsp:include page="loginheader.jsp"></jsp:include>
	<form action="./withdrawalCheck">
		<table border="1">
			<tr>
				<th>아&nbsp;이&nbsp;디&nbsp;:&nbsp;</th>
				<th><input type="text" maxlength="15" name="peid" readonly="redonly" value=${peid}></th>
			</tr>

			<tr>
				<th>비&nbsp;밀&nbsp;번&nbsp;호&nbsp;:&nbsp;</th>
				<th><input type="text" maxlength="15" name="pepwd"></th>
			</tr>
			<tr>
				<th colspan="2"><p>회원탈퇴시 ~~~~</p></th>
			</tr>
			<tr>
				<th colspan="2"><input type="submit" value="회원탈퇴"></th>
			</tr>
		</table>
	</form>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>