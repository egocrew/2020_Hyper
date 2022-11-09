<%@page import="com.min.app.dto.Day_Dto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="widgetDate" class="widget">
	<div id="widgetDateBox">
		<span id="widgetDateBox-time"></span>
	</div>
	<div id="widgetDayBox">
	<%
			List<Day_Dto> Mdto = (List<Day_Dto>)request.getAttribute("dayList");
			if(Mdto != null){
				for (int i = 0; i < Mdto.size(); i++) {
					if(Mdto.get(i).getDay_show().equalsIgnoreCase("Y")){
						String dday = Mdto.get(i).getDay_date();
						String ddayF = dday.substring(0, 1);
						String ddayL = dday.substring(1, dday.length());
						%>
							<%=Mdto.get(i).getDay_title()%> <span><%=ddayF+" "+ddayL %>일</span>
						<%
					}
				}
			}
		%>
	</div>
</div>
<div id="widgetKey" class="widget">
	<div class="widgetKey-key">
		<div id="key-ctrl-name" class="key-name">→</div>
		<div id="key-ctrl-explain" class="key-explain" onclick="keydownTrans();">빠른검색 모드</div>
	</div>
	<div class="widgetKey-key">
		<div id="key-tab-name" class="key-name">Tab</div>
		<div id="key-tab-explain" class="key-explain" onclick="keydownTab();">편집</div>
	</div>
	<div class="widgetKey-key" id="key-enter" style="display: none;">
		<div id="key-enter-name" class="key-name">Enter</div>
		<div id="key-enter-explain" class="key-explain"
			onclick="keydownEnter();">검색</div>
	</div>
</div>
