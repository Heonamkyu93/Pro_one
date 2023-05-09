<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
  <nav>
		<a href="#">HTML</a> |
		<a href="#">CSS</a> |
		<a href="#">JavaScript</a>
	</nav>
	<script>
	function (){
		httpRequest.open("POST", "/examples/media/request_ajax.php?city=Seoul&zipcode=06141", true);

		httpRequest.send();
	}
	
	
	
	</script>
</body>
</html>