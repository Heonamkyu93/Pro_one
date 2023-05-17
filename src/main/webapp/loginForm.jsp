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
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<div class="container">
  <div class="row" style="background-color: red">
    <div class="col-md-4" style="background-color: blue;"><h1>dddd</h1>
    </div>
    <div class="col-md-4" style="background-color: yellow;">
   <form action="./login">
    <table border="1">
    <tr>
    <th>아&nbsp;이&nbsp;디&nbsp;:&nbsp;</th>
    <th><input type="text" maxlength="15"name="peid"></th>
    </tr>
    
    <tr>
    <th>아&nbsp;이&nbsp;디&nbsp;:&nbsp;</th>
    <th><input type="text" maxlength="15" name="pepwd"></th>
    </tr>
    
    <tr>
    <th><input type="submit" value="로그인"></th>
     <th><input type="button"value="회원가입"></th>
    </tr>
    
    </table>
   	</form>
    </div>
    <div class="col-md-4" style="background-color: red;"><h1>dddd</h1>
    </div>
  </div>
</div>
<%String name=request.getParameter("name"); %>
<script type="text/javascript">

	

 
$(document).ready(function(){
	<%=name%>lert('탈퇴한 회원이거나 이메일 인증을 해주세요.');
});
/*
$(window).onload(function(){
	alert('hi - load2');
});
 */</script>
</body>
</html>