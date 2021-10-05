<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% request.setCharacterEncoding("UTF-8"); %>
<% response.setContentType("text/html; charset=UTF-8"); %>

<%@ page import="com.db.dao.BoardDao" %>
<%@ page import="com.db.dto.BoardDto" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<style>
th,td{padding:15px;}
.center{
margin: 5px 25px; padding: 20pxx	
}
</style>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<head>
<meta charset="UTF-8">
<title>UST 뉴스 스크랩</title>
<script type="text/javascript">
	function allChk(bool){
		var chks = document.getElementsByName("chk");
		for(var i=0; i<chks.length; i++){
			chks[i].checked = bool;
		}
	}
	//체크한 글 없다면 submit 이벤트 취소시키기 -> multidel.jsp로 이동 취소
	$(function(){
		$("#multidelete").submit(function(){
			if($("#multidelete input:checked").length==0){
				alert("하나 이상 체크하세요");
				return false;
			}
		});
	});
	
	function removeCheck() {		//보여주기용 메일발송

		 if (confirm("메일발송 하시겠습니까") == true){    

		     return false;

		 }else{   //취소

		     return false;

		 }

	}
	
</script>
</head>
<%
	BoardDao dao = new BoardDao();
	List<BoardDto> list = dao.selectAll();
%>
<body>

<%@ include file="./fix/header.jsp" %>
    <div>
    <h3></h3>
	<span style="margin-top:50px; font-size:40px;" >뉴스 목록</span> 
	<input type="date" id="now_date"> 
	<script>
		document.getElementById('now_date').valueAsDate = new Date();
		document.getElementById ( 'usingVar' ).innerHTML = localeDate
	</script>
	</div>
	<form action="multidel.jsp" method="post" id="multidelete">
	<table border="1">
		<col width="50px">
		<col width="70px">
		<col width="550px">
		<col width="140px">
		<col width="120px">
		<tr align=center>
			<th style="background-color:#fafafa;"><input type="checkbox" name="all" onclick="allChk(this.checked);"></th>
			<th style="background-color:#fafafa;">순서</th>
			<th style="background-color:#fafafa;">뉴스 제목</th>
			<th style="background-color:#fafafa;">링크</th>
			<th style="background-color:#fafafa;">날짜</th>
	<!--	<th>update</th> -->
			<th style="background-color:#fafafa;">삭제</th>
		</tr>
<%
	//for 반복문으로 테이블에 tr 태그 추가
	for(int i=0; i<list.size(); i++){
%>
	<tr>
		<td align=center><input type="checkbox" name="chk" value="<%=list.get(i).getBd_no()%>"></td>
		<td align=center><%=list.get(i).getBd_no()%></td>
		<td><%=list.get(i).getBd_title()%></td>
		<td align=center><a href=<%=list.get(i).getBd_name()%> target=\"_blank\" >바로가기</a></td>		
		<td align=center><%=list.get(i).getBd_date()%></td>
		<!-- <td><a href="update.jsp?bd_no=<%=list.get(i).getBd_no()%>">수정</a></td>  -->
		<td align=center><a href="delete.jsp?bd_no=<%=list.get(i).getBd_no()%>">삭제</a></td>
	</tr>
<% 
	}
%>	
	<tr>
		<td colspan="7">
			<input type="submit" value="삭제" class="btn btn-outline-info">       				
	        <input type="button" value="기사추가" class="btn btn-outline-info" onclick="location.href='insert.jsp'">	
			<input type="button" value="메일발송"  class="btn btn-outline-info" onclick="removeCheck()">
		</td>
	</tr>
	</table>
	</form>
	
<%@ include file="./fix/footer.jsp" %>
	
	
</body>
</html>