<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="./filetest" method="post" enctype="multipart/form-data">
<input type="text" name='te'>
<input type="text" name='te2'>
<input type="file" name='file' value="파일">
<input type="file" name='file2' value="파일2">
<input type="submit" value="전송">
</form>
</body>
</html>