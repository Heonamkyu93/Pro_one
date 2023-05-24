<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
		<header>
		
		
		    <nav id="navbar-example2" class="navbar bg-light px-3 mb-3">
  <a class="navbar-brand" href="./index">Home</a>
  <ul class="nav nav-pills">
    <li class="nav-item">
      <a class="nav-link" href="./boardList">게시판</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="./logout">로그아웃</a>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">내정보</a>
      <ul class="dropdown-menu">
        <li><a class="dropdown-item" href="./memberInfoUpdateFrom">개인정보</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="./beforeWithdrawalCheck">회원탈퇴</a></li>
      </ul>
    </li>
  </ul>
</nav>
</header>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>