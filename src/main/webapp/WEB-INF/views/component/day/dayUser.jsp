<%@page import="com.min.app.dto.Day_Dto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="widgetFunctionArea" id="widgetFunctionDay">
	<div class="widgetTitle">
		D - Day <i class="widgetAdd fas fa-plus" onclick="dayAdd();"></i>
		<div id="dayAddBox" class="widgetAddBox">
				<div class="widgetAddLine">
					<input type="text" id="dayAddLabel" placeholder="D-Day Lable" style="padding-left:2px;"/>
				</div>
				<div class="widgetAddLine">
					<input type="date" id="dayAddDate"/>
				</div>
				<div class="widgetAddLine">
					<div><i class="fas fa-check" onclick="daySubmit();"></i></div>
					<div><i class="fas fa-times" onclick="dayCancle();"></i></div>
				</div>
		</div>
	</div>
	<div class="dayBox">
		<%
			List<Day_Dto> Ddto = (List<Day_Dto>)request.getAttribute("dayList");
			if(Ddto != null){
				for (int i = 0; i < Ddto.size(); i++) {
		%>
		<div class="dayLine" onclick="dayOption('<%=Ddto.get(i).getDay_seq() %>');">
			<div class="dayName <%=
			Ddto.get(i).getDay_show().equalsIgnoreCase("Y")?"dayShowCheck":""%>"><%=Ddto.get(i).getDay_title() %></div>
			<div class="dayCount"><%=Ddto.get(i).getDay_date() %>일</div>
			<div class="dayDay"><%=Ddto.get(i).getDay_oldDate() %></div>
		</div>
		<%
				}
			}
		%>
		<div id="dayOption">
			<div><i class="fas fa-star" id="dayShowSet" title="대표 디데이 설정"></i></div>
			<div><i class="fas fa-trash" id="dayDelete" title="디데이 삭제"></i></div>
			<div><i class="fas fa-times" onclick="dayCancle();" title="디데이 편집 취소"></i></div>
		</div>
	</div>
</div>