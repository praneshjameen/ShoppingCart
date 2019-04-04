<% response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.addHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<logic:equal name="role" value="admin">
	<logic:redirect page="/login.do"></logic:redirect>
</logic:equal>
<logic:notPresent name="username">
	<logic:redirect page="/products.do"></logic:redirect>
</logic:notPresent>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart - Cart</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="/style.css" type="text/css">
</head>
<body onload="activeTab('cartNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:empty name="cartLists">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body" style="text-align: center;">
				<h1>Your Cart is empty</h1>
			</div>
		</div>
	</logic:empty>
	<div style="color: red; text-align: center;">
		<html:errors />
	</div>
	<%
		Integer totalPrice = 0;
		Integer count = 0;
	%>
	<logic:notEmpty name="cartLists">
		<input type="checkbox" name="productCheckList" id="allProduct"
			onclick="check(false)"
			style="margin-left: 2%; margin-top: 2%; zoom: 1.5;"
			<logic:equal name="selectAll" value="true"> checked</logic:equal>>
		<span style="font-weight: bold; font-size: 21px;"> SELECT ALL<span>
	</logic:notEmpty>
	<div id="checkboxes">
		<logic:iterate name="cartLists" id="cartItems">
			<bean:define id="productId" name="cartItems" property="productId" />
			<bean:define id="productQuantity" name="cartItems"
				property="productQuantity" />
			<bean:define id="cartQuantity" name="cartItems"
				property="cartQuantity" />
			<bean:define id="productPrice" name="cartItems"
				property="productPrice" />
			<bean:define id="available" name="cartItems" property="isAvailable" />
			<bean:define id="cartStatus" name="cartItems"
				property="cartAvailable" />
			<div class="card" style="width: 70%; margin: 2%;">
				<h3 class="card-header">
					<input type="checkbox" class="productCheckList" id="${productId}"
						onclick="checkBox()"
						<logic:equal name="cartStatus" value="true"> checked</logic:equal>>
					<bean:write name="cartItems" property="productName" />
					(&#8377;
					<bean:write name="cartItems" property="productPrice" />
					)
				</h3>
				<div class="card-body"
					style="padding: 2%; font-size: large; font-weight: bold;">
					<logic:equal name="available" value="true">
						<a
							href="user.do?method=removeFromCart&id=${productId}&quantity=${cartQuantity}" onclick="return appendId('remove${productId}')"
							id="remove${productId}" class="btn btn-danger" style="margin: 1%;">-</a>
						<bean:write name="cartItems" property="cartQuantity" />
						<logic:notEqual value="0" name="productQuantity">
							<a href="user.do?method=addToCart&id=${productId}&page=2" onclick="return appendId('add${productId}')"
								id="add${productId}" class="btn btn-success" style="margin: 1%; margin-right: 4%;">+</a>
						</logic:notEqual>
						<logic:equal name="productQuantity" value="0">
							<a class="btn btn-secondary"
								style="margin: 1%; margin-right: 4%;">+</a>
						</logic:equal>
						<%
							Integer price = (Integer) productPrice;
									Integer quantity = (Integer) cartQuantity;
									Integer result = price * quantity;
									totalPrice += result;
									count++;
						%>
								&#8377; <span id="result${productId}"><%=result%></span>
						<a
							href="user.do?method=removeFromCart&id=${productId}&deleteCart=${cartQuantity}" onclick="return appendId('removeAll${productId}')"
						id="removeAll${productId}"	style="float: right;" class="btn btn-danger">Remove</a>
					</logic:equal>
					<logic:equal name="available" value="false">
						<a class="btn btn-secondary">Not Available</a>
					</logic:equal>
				</div>
			</div>
		</logic:iterate>
		<logic:notEmpty name="cartLists">
	</div>
	<div class="card" style="width: 70%; margin: 2%;">

		<div class="card-body"
			style="padding: 2%; font-size: large; font-weight: bold;">
			Total Amount = &#8377; <span id="totalPrice"><%=totalPrice%></span>
		</div>
	</div>
	<a href="user.do?method=viewPurchase" class="btn btn-success"
		id="purchase" onclick="return validate()" style="margin: 2%">PURCHASE
	</a>
	</logic:notEmpty>
	<div id="snackbar">${message}</div>
	<script>
	<%if(session.getAttribute("message")!=null){%>
	show();
	<%
	session.setAttribute("message",null);
	}%>
	function show() {
		  var x = document.getElementById("snackbar");
		  x.className = "show";
		  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 2000);
		}
	var cartlen =
		<%=count%>;
		var totalPrice =
			<%=totalPrice%>
				;
		function check(bool) {
			
			if (bool) {
				$('.productCheckList').prop('checked', true);
				$('#allProduct').attr('onclick', 'check(false)');
				$('#totalPrice').text(totalPrice);
				$('#purchase').attr('href',
						'user.do?method=viewPurchase&selectAll=1');
			} else {
				$('.productCheckList').prop('checked', false);
				$('#allProduct').attr('onclick', 'check(true)');
				$('#totalPrice').text(0);
			}
		}
		function checkBox() {
			var total = 0;
			var href = 'user.do?method=viewPurchase';
			var len = $('input[class=productCheckList]:checked').length;
			if (len == cartlen) {
				$('#allProduct').prop('checked', true);
				$('#allProduct').attr('onclick', 'check(false)');
				$('#purchase').attr('href',
						'user.do?method=viewPurchase&selectAll=1');
				$('#totalPrice').text(totalPrice);
			} else {
				$('#allProduct').prop('checked', false);
				$('#allProduct').attr('onclick', 'check(true)');
				$('div#checkboxes input[class=productCheckList]').each(
						function() {
							if ($(this).is(":checked")) {
								var checkId = $(this).attr('id');
								href += '&idList=' + checkId;
								var result = Number($('#result' + checkId)
										.text());
								total += result;
							}
						});
				$('#purchase').attr('href', href);
				$('#totalPrice').text(total);
			}

		}
		function validate() {
			var len = $('input[class=productCheckList]:checked').length;
			if (len == 0) {
				alert('Nothing is selected from the cart');
				return false;
			} else {
				return true;
			}
		}
		function appendId(id){
			var href=$('#'+id).attr('href');
			console.log(href);
			var len = $('input[class=productCheckList]:checked').length;
			if (len == cartlen) {
				href=href+'&selectAll=1';
				$('#'+id).attr('href',href);
			} else {
				$('div#checkboxes input[class=productCheckList]').each(
						function() {
							if ($(this).is(":checked")) {
								var checkId = $(this).attr('id');
								href += '&idList=' + checkId;
							}
						});
				$('#'+id).attr('href', href);
			}
			return true;
		}
	</script>
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
</body>
</html>