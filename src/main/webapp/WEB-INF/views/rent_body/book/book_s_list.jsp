<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	.book_select{
		cursor: pointer;
	}
</style>
<script>
/*
 * 실제로 필요한건 isbn의 값
 	아래의 코드는 검색하고 클릭하면 ___을 구현해내기 위한 코드
 	도서정보를 클릭하면 옆에 도서정보에 대해 나옴
 */
$(function(){
	$(".b_select").on("click",function(){
		let seq = $(this).attr("data-seq")
		let title = $(this).text()
		let price = $(this).attr("data-price")
		
		$("#rent_book_seq").val(seq)
		$("#book_title").text(title)
		$("#rent_book_price").val(price)
	
	})
})
</script>
<table>
		<tr><th>도서명</th><th>저자</th></tr>
		<tr><th>가격</th><th>대여</th></tr>
	<c:choose>
		<c:when test="${empty BOOKS}">
			<tr><td colspan=2>도서목록 없음</td>
		</c:when>
		<c:otherwise>
			<c:forEach var="book" items="${BOOKS}">
				<tr>
					<td class="b_select b_title" data-seq="${book.book_seq}"
						data-price="${book.book_price}">
						${book.book_title}</td>
					<td>${book.book_author}</td>
				</tr>
				<tr>
					<td>${book.book_price}</td>
					<td>
						<c:if test="${book.book_rent_yn =='Y'}">
							대여 가능
						</c:if>
						<c:if test="${book.book_rent_yn =='N'}">
							대여 불가능
						</c:if>
						</td>
						</tr>
						</c:forEach>
						</c:otherwise>
	</c:choose>
</table>