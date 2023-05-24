<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
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
				<div class="col-md-3" >
				</div>
				<div class="col-md-6 " style="background-color: #F2F2F2" id='m' >
					<form action="./login" method="post">
								
								
								<div class="row" >
								<div class="col-md-12" align="center">
								<font size="6em" color="black">로그인</font>
								</div>
								</div>
								
								
								
								<div class="row" >
								<div class="col-md-3" ><font size="4em" color="black">&nbsp;아&nbsp;&nbsp;이&nbsp;&nbsp;디&nbsp;&nbsp;&nbsp;</font>
								</div>
								<div class="col-md-7" >
								<input type="text" maxlength="15" name="peid" class="form-control">
								</div>
								<div class="col-md-2"></div>
								</div>
								<div class="row" >
								<div class="col-md-3"><font size="4em" color="black">&nbsp;비&nbsp;밀&nbsp;번&nbsp;호&nbsp;</font>
								</div><div class="col-md-7" >
								<input type="password" maxlength="15" name="pepwd" class="form-control">
								</div>
								<div class="col-md-2"></div>
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
				<div class="col-md-3" >
				</div>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp"></jsp:include>
	<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>