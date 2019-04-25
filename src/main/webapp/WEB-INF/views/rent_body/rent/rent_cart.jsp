<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<style>
.cart_item_delete{
	cursor: pinter;
}
</style>
<script>
$(function(){
	$("#btn_cart_clear").click(function(){
		$.get("${rootPath}/rent/cart_clear",function(result){
			$("#cart-box").html(result)
		})
		
	})
	$(".cart_item_delete").click(function(){
		let seq = $(this).attr("data-seq")
		$.get("${rootPath}/rent/cart_seq_delete/" + seq,function(result){
		$("#cart-box").html(result)
	})
		/*
		let index = $(this).attr("data-index")
		$.get("${rootPath}/rent/cart_item_delete/" + index,function(result){
			$("#cart-box").html(result)
		})
		*/
	})
	
	$("#btn_check_out").click(function(){
	
	$.get("${rootPath}/check_out", function(result){
		if(result == 'Check Ok!!'){
			$("#cart-box").html("") // checkok이면 다 삭제하고
			alert("결제 완료")
		}else{
			
			alert(result)//그렇지 않으면 결과값을 화면에 보여라
		}
	})
})
})
</script>

<h3>장바구니</h3>
<div>
	<button id="btn_check_out">결제</button>
	<button id="btn_cart_clear">비우기</button>
</div>
<table>
	<tr>
		<th>NO</th>
		<th>도서명</th>
		<th>가격</th>
		<th></th>
	</tr>
	<!--  
		session을 jsp에서 취급하는 방법
		servlet-jsp 버전이 3.x 이전에는
		session.getAttribute()라는 JSP method를 이용해서
		session값을 추출하고 취급을 했어야 한다.
		
		최근버전의 servlet에서는 일반 model에 담긴 데이터처럼
		바로 변수명을 꺼내서 사용할 수 있다.
		
		count는 1부터 시작하고
		index는 0부터 시작함
	-->

	<c:choose>
		<c:when test="${empty CART}">
			<tr><td colspan="4">장바구니가 비어있습니다.</td></tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="item" items="${CART}" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td class="b_title">${item.cart_book_title}</td>
					<td>${item.cart_book_price}</td>
					<td class="cart_item_delete" data-index="${i.index}"
					data-seq="${item.cart_seq}">x</td>
				
			</c:forEach>		
		</c:otherwise>
	</c:choose>
</table>