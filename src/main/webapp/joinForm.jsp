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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
						onsubmit="return validCheck()" method="post">

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
									id="peid" name="peid" maxlength="15" onchange="change();">
									<br> <br> <strong id='idnotice'>아이디는 4글자이상
										15자 이하로 필수로 입력해야합니다.</strong></td>
								<td><input type="button" value="중복확인"
									onclick="dupliCheck();"><br> <br> <strong
									id='dupnotic'>중복확인은 필수입니다.</strong></td>
								<td></td>
							</tr>
							<tr align="left">
								<td><font size=4>&nbsp;비&nbsp;&nbsp;밀&nbsp;&nbsp;번&nbsp;&nbsp;호&nbsp;:</font></td>
								<td colspan="2"><input type="password" class="form-control"
									size="35" name="pepwd" id="pepwd" maxlength="15"><br>
									<br> <strong>비밀번호는 6자이상이여야하며 필수로 입력해야합니다.</strong></td>

							</tr>
							<tr align="left">
								<td><font size=4>&nbsp;비&nbsp;&nbsp;밀&nbsp;&nbsp;번&nbsp;&nbsp;호&nbsp;확&nbsp;인&nbsp;:</font></td>
								<td colspan="2"><input type="password" class="form-control"
									size="35" name="pepwd2" id="pepwd2" maxlength="15"><br>
									<br> <strong id='pewarn'>비밀번호를 한번더 확인해주세요.</strong></td>

							</tr>

							<tr align="left">
								<td align="left"><font size=4>&nbsp;이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="text" class="form-control"
									name="pename" id="pename" maxlength="15"><br> <br>
									<strong>이름은 필수로 입력해야합니다.</strong></td>
							</tr>
							<tr align="left">
								<td align="left"><font size=4>&nbsp;나&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="number" class="form-control"
									name="peage" id="peage" min="0" max="110"><br> <br>
									<strong>나이는 숫자만 입력해주세요.110이상은 입력이 불가능합니다.</strong></td>
							</tr>
							<tr align="left">
								<td align="left"><font size=4>&nbsp;전&nbsp;화&nbsp;번&nbsp;호&nbsp;:&nbsp;</font></td>
								<td colspan="2"><input type="text" class="form-control"
									id="pephonenumber" name="pephonenumber" maxlength="11"
									onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"/><br> <br> <strong>전화번호는-없이
										11자리 입력해주세요.</strong></td>
							</tr>
							<tr align="left">
								<td align="left"><font size=4>이&nbsp;메&nbsp;일&nbsp;:</font></td>
								<td><input type="text" class="form-control" size="12"
									name="pemail" id="pemail" maxlength="40"
									onchange="changemail();"><br> <br> <strong id='em'>이메일을 입력해주세요.</strong><br><strong id='em3'></strong></td>
								<td><input type="button" value="중복확인" onclick="emailch();"><br>
									<br> <strong id='em2'>중복확인은 필수입니다.</strong></td>
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
		let duplem = 1;    //이메일중복체크 1이면  onsubmit시 validCheck에서 걸리게 되어있음 중복체크를 해도 값이 변하면 못넘어감
		let duplch = 1;   // 아이디중복체크 기본값1이고 값이 1이면 onsubmit시 validCheck에서 걸리게 되어있음 중복체크를해도 값이 변하면 onchange 펑션 change에서 값이 1으로 변경되어서 못넘어감
		let emvali = 1;		// 이메일 정규식체크 1이면  onsubmit시 validCheck에서 걸리게 되어있음 정규식을 만족해도 값이 변하면 역시 못넘어감
		
		function emailch() {
			if(emvali ===1){
				alert('이메일 형식을 맞춰주세요.');
			}
			else{
			
			
			let pemail = document.getElementById('pemail').value;
			if (document.getElementById('pemail').value == null
					|| document.getElementById('pemail').value == ""
					|| document.getElementById('pemail').value.length <= 6) {
				alert("이메일은 필수 입력값입니다.");
				document.getElementById('em').style.color = 'red';
			} else {

				$
						.ajax({
							type : 'post',
							url : './emaildup',
							data : {
								'pemail' : pemail
							},
							contentType : "application/x-www-form-urlencoded; charset=UTF-8",
							success : function(res) {
								alert(res);
								duplem = 2;
							}

						});
			}
		}
		}

		function change() {
			duplch = 1;
		}
		function changemail() {
			duplem = 1;
			emvali = 1;
			let ee = document.getElementById('pemail').value;
			let regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
						
			if(ee.match(regExp)!= null){
				document.getElementById('em3').innerText='';
				emvali = 2;
			}else{
				document.getElementById('em3').innerText='이메일형식에 맞는 값을 입력해주세요.';	
				}
		}

		function dupliCheck() { // 아이디 중복체크 함수
			if (document.getElementById('peid').value == null
					|| document.getElementById('peid').value == "") {
				alert("아이디는 4자이상 필수 입력값입니다.");
				document.getElementById('idnotice').style.color = 'red';
			} else {

				var peid = document.getElementById("peid").value;
				var xhr = new XMLHttpRequest();
				xhr.open("get", "./dupliCheck?peid=" + peid, true);
				xhr.onreadystatechange = function() {
					if (xhr.readyState == 4 && xhr.status == 200) {
						//	alert(xhr.responseText);
						if (xhr.responseText === 'n') {
							duplch = 2;
							alert('중복된 아이디가 없습니다.');
						} else {
							alert('중복된 아이디가 있습니다 다른 아이디를 입력해주세요.');
						}
					}
				}
				xhr.send();
			}
		}
		var pepwd = document.getElementById('pepwd');
		var pepwd2 = document.getElementById('pepwd2');

		function validCheck() { //유효성체크
			var peage = document.getElementById('peage');
			var peid = document.getElementById('peid');
			var pename = document.getElementById('pename');
			let pemail = document.getElementById('pemail');
			let pephonenumber = document.getElementById('pephonenumber');

			if (peid.value == null || peid.value == "" || peid.value.length < 4) {
				alert("아이디는 4자이상 필수 입력값입니다.");
				document.getElementById('peid').focus;
				document.getElementById('idnotice').style.color = 'red';
				return false;

			} else if (pepwd.value == null || pepwd.value == ""
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
			} else if (pename.value == null || pename.value == "") {
				alert("이름은 필수 입력값입니다.");
				document.getElementById('pename').focus;
				document.getElementById('pename').style.color = 'red';
				return false;
			} else if (pephonenumber.value == null || pephonenumber.value == "") {
				alert("전화번호는 필수 입력값입니다.");
				document.getElementById('pephonenumber').focus;
				document.getElementById('pephonenumber').style.color = 'red';
				return false;
			} else if (pepwd.value != pepwd2.value) {
				alert("비밀번호와 비밀번호 확인은  6자이상 필수 입력값이며 서로 일치해야 합니다.");
				document.getElementById('pepwd').focus;
				document.getElementById('pewarn').style.color = 'red';
				return false;
			} else if (peage.value.length == 0) {
				alert("나이를 입력해주세요.");
				document.getElementById('peage').focus;
				document.getElementById('peage').style.color = 'red';
				return false;
			} else if (duplch === 1) {
				alert("ID중복체크를 해주세요.");
				document.getElementById('peid').focus;
				document.getElementById('dupnotic').style.color = 'red';
				return false;
			} else if (pemail.value.length == 0) {
				alert('이메일을 입력해주세요');
				document.getElementById('em').style.color = 'red';
				return false;
			} else if (duplem === 1) {
				alert('이메일 중복확인은 필수입니다.');
				pemail.focus;
				document.getElementById('em2').style.color = 'red';
				return false;
			}else if (emvali ===1){
				document.getElementById('em3').style.color = 'red';
				document.getElementById('em3').innerText='이메일형식에 맞는 값을 입력해주세요.';	
				return false;
			}

		}
	</script>
</body>
</html>