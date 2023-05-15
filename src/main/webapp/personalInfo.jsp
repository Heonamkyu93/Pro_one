<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form onsubmit="return valch();">
		<table border="1">
			<tr>
				<th colspan="2">아이디:</th>
				<th><input type="text" readonly="readonly" id="peid" value=${peid} name='peid'></th>
				<th id='test'></th>
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
				<th colspan="4"><input type="submit" value="변경"></th>

			</tr>

		</table>
	</form>
	<script>
	var peid = document.getElementById('id');
	var pename= document.getElementById('pename');
	var pephonenumber = document.getElementById('pephonenumber');
	var peemail = document.getElementById('peemail');
	var test = document.getElementById('test');
	function valch(){
		if(pename.value.length ===0){
			pename.focus;
			test.innerText= "dddd";
			alert("d");
			return false;
		}
		else if(pephonenumber.value.length===0){
			pephonenumber.focus;
			return false;
		}
		else if(peemail.value.length===0){
			peemail.focus;
			return false;
		}
		
	}
	
	
	
	
	
	
	</script>
</body>
</html>