<%@page import="com.min.app.dto.Log_Dto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hyper - Log</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style type="text/css">
#container{
	width:80%;
	margin:auto;
}
#nav{
	max-width:100%;
	height: 150px;
	border : 1px solid #ededed;
	padding:20px;
	margin-bottom:20px;
}
.logBtnLine{
	min-width:100%;
	height: 60px;
	line-height: 20px;
	overflow-x:scroll;
	white-space:nowrap;
}
.logBtnLine {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
}
.logBtnLine::-webkit-scrollbar {
    display: none; /* Chrome, Safari, Opera*/
}
.logBtnBox{
	display: inline-block;
	margin-right:40px;
	border-left: 2px solid #222;
	padding-left:10px;
}
.logBtnBox>div{
	display: inline-block;
	padding: 2px 10px;
	font-size:12px;
	font-weight: bold;
	background: #f2f2f2;
	color:#aaa;
	border-radius: 4px;
	cursor: pointer;
}
.logBtnBox>div:hover{
	background: #222;
	color:#f2f2f2;
}
#logBtnSubmit{
	width:100%;
	text-align: center;
}
#logBtnSubmit>button{
	width:30%;
	height:30px;
	background: #222;
	color:#f2f2f2;
	font-size:12px;
	font-weight: bold;
	border:none;
	border-radius: 4px;
}
#datePicker{
	width:70px;
	border:none;
	font-size:12px;
	font-weight: bold;
	background: #f2f2f2;
	color:#aaa;
	border-radius: 4px;
	cursor: pointer;
	padding: 2px 10px;
	line-height:20px;
	text-align: center;
}
table{
	width:100%;
	border: 1px solid #f2f2f2;
	padding:20px;
	text-align: center;
	font-size:14px;
}
thead {
	font-weight: bold;
	border-bottom:1px solid #f2f2f2;
}
tr{
	line-height:40px;
	vertical-align: middle;
}
</style>
</head>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<body>
<div id="container">
	<div id="nav">
		<div class="logBtnLine">
			<div class="logBtnBox">
				<!-- 전체 로그 보기 -->
				<div onclick="getAllLog();">전체</div>
			</div>
			
			<div class="logBtnBox box1">
				<!-- 간편 날짜 선택 -->
				<div onclick="setDateLog('오늘');">오늘</div>
				<div onclick="setDateLog('어제');">어제</div>
			</div>
			
			<div class="logBtnBox box1">
				<!-- 직접 날짜 선택 -->
				<input type="text" id="datePicker" />
			</div>
		</div>
		
		<div class="logBtnLine">
			<div class="logBtnBox box2">
			<!-- 상세 정보 설정 -->
				<div data-tag="접속">#방문</div>
				<div data-tag="회원">#가입/탈퇴</div>
				<div data-tag="로그">#접속</div>
				<div data-tag="즐겨찾기">#즐겨찾기</div>
				<div data-tag="환경설정">#환경설정</div>
				<div data-tag="닉네임">#닉네임</div>
				<div data-tag="비밀번호">#비밀번호</div>
				<div data-tag="error">#에러</div>
			</div>
		</div>
		
		<div id="logBtnSubmit">
			<button onclick="getLog()">선택</button>
		</div>
	</div>
	<form id="logForm" method="post">
		<input type="hidden" id="log_date" name="log_date"/>
		<input type="hidden" id="log_message" name="log_message"/>
	</form>
	
	<%
		List<Log_Dto> list = (List<Log_Dto>)request.getAttribute("log");
		Log_Dto dto = new Log_Dto();
	%>
	<table>
		<thead>
			<tr>
				<td>SEQ</td>
				<td>날짜</td>
				<td>메시지</td>
				<td>IP</td>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i = 0; i < list.size(); i++){
			%>
			<tr>
				<td><%=list.get(i).getLog_seq()%></td>
				<td><%=list.get(i).getLog_date()%></td>
				<td><%=list.get(i).getLog_message()%></td>
				<td><%=list.get(i).getLog_ip()%></td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>
	
</div>

	
</body>

<script type="text/javascript">
	
	$(function(){
	    $("#datePicker").datepicker({
	    	dateFormat: 'yy-mm-dd'
	    });
	    
	    $('#datePicker').datepicker('setDate', 'today');
	    
	    $('.box1>*').click(function(){
	    	$('.box1>*').css("background","#f2f2f2");
	    	$('.box1>*').css("color","#aaa");
    		$(this).css("background","#222");
	    	$(this).css("color","#f2f2f2");
	    });
	    $('.box2>div').click(function(){
	    	$('.box2>div').css("background","#f2f2f2");
	    	$('.box2>div').css("color","#aaa");
    		$(this).css("background","#222");
	    	$(this).css("color","#f2f2f2");
	    	var tag = $(this).data("tag");
	    	$("#log_message").val(tag);
	    });
	});

	function getAllLog() {
		location.href = "../log/";
	}
	
	function setDateLog(handler) {
		$("#datePicker").val(getToday(handler));
	}
	
	function setActionType() {
		var date = $("#datePicker").val();
		$("#log_date").val(date);
		var message = $("#log_message").val();
		// 날짜만 선택되었다면
		if (message=='') {
	 		$("#logForm").attr("action","././date");
		}else{ // 둘 다 입력이 되었다면
	 		$("#logForm").attr("action","././detail");
		}
	}
	
	function getLog() {
		setActionType();
		$("#logForm").submit();
	}

	function getToday(handler) {
		var today = new Date();
		
		if (handler=='어제') {
			var yesterday = today.getTime() - (1 * 24 * 60 * 60 * 1000);
			today.setTime(yesterday);
		}
		
		var dd = today.getDate();
		var mm = today.getMonth()+1;
		var yyyy = today.getFullYear();

		dd = dd<10?'0'+dd:dd;
		mm = mm<10?'0'+mm:mm;

		today = yyyy+'-'+mm+'-'+dd;
		
		return today;
	}
</script>
</html>