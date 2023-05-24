<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<style type="text/css">
	.b{
	border: 1px solid black;
	
	}
	#p{
	padding: 40px;	
	}
	
	</style>
</head>
<body>
<jsp:include page="adminheader.jsp"></jsp:include>
	<section>
	${mList}
	<div class='container'id='p'>
	<div class='row'>
	<div class='com-md-5'></div>
	<div class='com-md-2'>
	${mpage}
	</div>
	<div class='com-md-5'></div>
	</div>
	</div>
	</section>
<jsp:include page="footer.jsp"></jsp:include>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>