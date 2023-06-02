<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>
<jsp:include page="loginheader.jsp"></jsp:include>
<div class='container' align="center" style="margin-top: 25px">
<div class='row'>
<div class='col-md-8'></div>
<div class='col-md-2'><input type='text' class='form-control' onkeyup="searchboard();" id='searchdata' name='searchdata' maxlength="49"></div>
<div class='col-md-1' align="left"><!-- <button class='btn btn-primary'>검색</button> --></div>
<div class='col-md-1'></div>
</div>
</div>
<div class='container' align="center">
<div class='row'>
<div class='col-md-1'></div>
<div class='col-md-10' align="center" id='ma'>${html}</div>
<div class='col-md-1'></div>
</div>

</div>
<div class='container' align="center">
<div class='row'>
<div class='col-md-3'></div>
<div class='col-md-6' align="center" id='p'>
${page }

</div>
<div class='col-md-3'><button type="button" class="btn btn-info" onclick="location.href='./boardInsertForm'">글쓰기</button></div> 
</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
function searchboard(){
	let data=document.getElementById('searchdata').value;
	$.ajax({
		type : 'post',
		url : './searchdata',
		data : {
			'data' : data
		},
		contentType : "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(res) {
			let ma=document.getElementById('ma');
			ma.innerHTML=res;
			let p=document.getElementById('p');
			p.innerText='';
		}

	});
	
	
	
}


</script>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>