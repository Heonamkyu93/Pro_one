<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>

<!-- CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
				<div class="panel panel-default" align="center">
					<div class="panel-heading">
						<h2 class="panel-title">
							<span class="glyphicon glyphicon-tags"></span> &nbsp;&nbsp;회원가입
						</h2>
					</div>
					<div class="panel-body">
						<div class="media"></div>
					</div>
					<form class="form-inline" role="form" action="./memberInsert"
						onsubmit="return validCheck()">

						<table class="table">
							<tr>
								<td colspan="5">
									<div>
										<font color="#f67001" size=6>&nbsp;&nbsp;01</font> <font
											size=5>&nbsp;&nbsp;회원정보</font>
									</div>
								</td>
							</tr>

							<tr align="left">
								<td><font size=4>&nbsp;아&nbsp;이&nbsp;디&nbsp;:&nbsp;</font></td>
								<td><input type="text" class="form-control" name="peid"
									id="peid" name="peid" maxlength="15" onchange="change();"></td>
								<td><input type="button" value="중복확인"
									onclick="dupliCheck();"></td>
								<td></td>
							</tr>
							<tr align="left">
								<td><font size=4>&nbsp;비&nbsp;&nbsp;밀&nbsp;&nbsp;번&nbsp;&nbsp;호&nbsp;:</font></td>
								<td colspan="2"><input type="text" class="form-control"
									size="35" name="pepwd" id="pepwd" maxlength="15"></td>

							</tr>
							<tr align="left">
								<td><font size=4>&nbsp;비&nbsp;&nbsp;밀&nbsp;&nbsp;번&nbsp;&nbsp;호&nbsp;확&nbsp;인&nbsp;:</font></td>
								<td colspan="2"><input type="text" class="form-control" 
									size="35" name="pepwd2" id="pepwd2" maxlength="15"></td>

							</tr>

							<tr align="left">
								<td align="left"><font size=4>&nbsp;이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="text" class="form-control"
									name="pename" id="pename" maxlength="15"></td>
							</tr>
								<tr align="left">
								<td align="left"><font size=4>&nbsp;나&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="text" class="form-control"
									name="peage" id="peage" maxlength="15"></td>
							</tr>
							<tr align="left">
								<td align="left"><font size=4>&nbsp;전&nbsp;화&nbsp;번&nbsp;호&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="text" class="form-control"
									id="pephonenumber" name="pephonenumber" maxlength="11"></td>
							</tr>
							<tr align="left">
								<td align="left"><font size=4>이&nbsp;메&nbsp;일&nbsp;:</font></td>
								<td colspan="2"><input type="text" class="form-control"
									size="12" name="pemail" id="pemail" maxlength="40"></td>
							</tr>

							<tr align="left">
								<td align="left"><font size=4>성&nbsp;&nbsp;별&nbsp;:</font></td>
								<td colspan="2">남&nbsp;자&nbsp;:&nbsp;<input
									class="form-check-input" type="radio" name="pegender" checked
									id="pegender" value="m"> 여&nbsp;자&nbsp;:&nbsp;<input
									class="form-check-input" type="radio" name="pegender"
									id="pegender" value="f">
								</td>
							</tr>
							<tr align="center">
								<td colspan="4"><input type="submit"
									class="btn btn-danger btn-lg" value="회 원 가 입"></td>

							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
		var duplch = 1;
		
		function change(){
			duplch = 1;
		}
		
		function dupliCheck() { // 아이디 중복체크 함수
			var peid = document.getElementById("peid").value;
			var xhr = new XMLHttpRequest();
			xhr.open("get", "./dupliCheck?peid=" + peid, true);
			xhr.onreadystatechange = function() {
				if (xhr.readyState == 4 && xhr.status == 200) {
					alert(xhr.responseText);
					if(xhr.responseText==='n'){
						duplch =2;						
					}
				}
			}
			xhr.send();
		}
		var pepwd = document.getElementById('pepwd');
		var pepwd2 = document.getElementById('pepwd2');
		
		function validCheck() { //유효성체크
			var peage= document.getElementById('peage');
			var peid = document.getElementById('peid');
			var pename = document.getElementById('pename');
			var peMail = document.getElementById('pemail');
			var pephonenumber = document.getElementById('pephonenumber');
			//			var peid = document.getElementById().value;

			if (peid.value == null || peid.value == "" || peid.value.length < 4) {
				alert("아이디는 4자이상 필수 입력값입니다.");
				document.getElementById('peid').focus;
				return false;

			} else if (pepwd.value == null || pepwd.value == ""
					|| pepwd.value.length < 6) {
				alert("비밀번호는 6자이상 필수 입력값입니다.");
				document.getElementById('pepwd').focus;
				return false;
			} else if (pepwd2.value == null || pepwd2.value == ""
					|| pepwd2.value.length < 6) {
				alert("비밀번호와 비밀번호 확인은  6자이상 필수 입력값이며 서로 일치해야 합니다.");
				document.getElementById('pepwd2').focus;
				return false;
			} else if (pename.value == null || pename.value == "") {
				alert("이름은 필수 입력값입니다.");
				document.getElementById('pename').focus;
				return false;
			} else if (pephonenumber.value == null || pephonenumber.value == "") {
				alert("전화번호는 필수 입력값입니다.");
				document.getElementById('pephonenumber').focus;
				return false;
			} else if (pepwd.value != pepwd2.value) {
				alert("비밀번호와 비밀번호 확인은  6자이상 필수 입력값이며 서로 일치해야 합니다.");
				document.getElementById('pepwd').focus;
				return false;
			}else if(peage.value.length==0 ){
				alert("나이를 입력해주세요.");
				document.getElementById('peage').focus;
				return false;
			}else if(duplch===1){
				alert("ID중복체크를 해주세요.");
				document.getElementById('peid').focus;
				return false;
			}
		}
	</script>
</body>
</html>