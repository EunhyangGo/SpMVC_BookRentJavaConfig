<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	$(function(){
		$(".u_select").click(function(){
			
			let seq = $(this).attr("data-seq")
			let name = $(this).text()
			
			$("#rent_user_seq").val(seq)
			$("#user_name").text(name)
			
		})
	})

</script>
<style>
	.u_select{
		cursor:pointer;
	}
</style>

<table>
	<tr>
		<th>이름</th>
		<th>전화번호</th>
		
	</tr>
	<c:choose>
		<c:when test="${empty USERS}"> <!-- users가 비어 있으면 -->
		<tr><td colspan="2">데이터 없음</td></tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="user" items="${USERS}">
			
				<tr>
					<td class="u_select" data-seq="${user.user_seq}"
					>${user.user_name}</td>
				<td>${user.user_phone}</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>
<!-- 
jsp// 컨트롤러에서 바로 사용 가능
jspf // include 해서 사용하겠다
 -->