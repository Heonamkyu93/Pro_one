<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
</body>
</html>