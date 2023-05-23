<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
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
	<style type="text/css">
	div{
	padding: 5px;
	}
	   #m{
	   margin: 85px;
        }
	
	
	</style>
</head>
<body>
	<jsp:include page="logoutheader.jsp"></jsp:include>

	<section>
		<div class="container-md">
			<div class="row" >
				<div class="col-md-4" >
				</div>
				<div class="col-md-4 " style="background-color: #F2F2F2" id='m' >
					<form action="./login" method="post">
								
								
								<div class="row" >
								<div class="col-md-12" align="center">
								<font size="6em" color="black">로그인</font>
								</div>
								</div>
								
								
								
								<div class="row" >
								<div class="col-md-2" ><font size="4em" color="black">&nbsp;아&nbsp;&nbsp;이&nbsp;&nbsp;디&nbsp;&nbsp;&nbsp;</font>
								</div>
								<div class="col-md-10" >
								<input type="text" maxlength="15" name="peid" class="form-control">
								</div>
								</div>
								<div class="row" >
								<div class="col-md-2"><font size="4em" color="black">&nbsp;비&nbsp;밀&nbsp;번&nbsp;호&nbsp;</font>
								</div><div class="col-md-10" >
								<input type="password" maxlength="15" name="pepwd" class="form-control">
								</div>
								</div>
								<div class="row" align="center">
								<div class="col-md-4" >
								</div>
								<div class="col-md-4" >
								<input type="submit" value="로그인" class="btn btn-primary btn-lg">
								</div>
								<div class="col-md-4" >
								</div>
								</div>
					</form>
				</div>
				<div class="col-md-4" >
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>