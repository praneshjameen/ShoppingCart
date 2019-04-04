
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
<title>Shopping Cart - Orders</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	<link rel="stylesheet" href="/style.css" type="text/css">
</head>
<body onload="activeTab('ordersNav')">
	<%@ include file="/WEB-INF/jspf/nav.jspf"%>
	<logic:empty name="orderLists">
		<div class="card" style="width: 50%; margin: auto;">
			<div class="card-body" style="text-align: center;">
				<h1>Your have not purchased anything yet!</h1>
			</div>
		</div>
	</logic:empty>

	<logic:iterate name="orderLists" id="orderItem">
		<bean:define id="orderId" name="orderItem" property="orderId" />

		<div class="card" style="width: 60%; margin: 2%;">
			<h4 class="card-header">
				Order ID :
				<bean:write name="orderId" />
			</h4>
			<div class="card-body" style="font-weight: bold;">

				<p>
					Order Address : <span style="font-weight: normal;" x><bean:write
							name="orderItem" property="address" /></span>
				</p>

				<p>
					Order Time : <span style="font-weight: normal;"><bean:write
							name="orderItem" property="timeStamp" /></span>
				</p>
				<logic:iterate id="product" name="orderItem" property="products">
					<bean:define id="isCancelled" name="product" property="isCancelled" />
					<bean:define id="quantity" name="product"
						property="productQuantity" />
					<bean:define id="productId" name="product" property="productId" />
					<bean:define id="cancelLists" name="product" property="cancelList" />
					<div class="card" style="width: 80%; margin: 2%;">
						<div class="card-body">
							<bean:write name="product" property="productName" />
							<div style="float: right;">
								<logic:notEqual name="quantity" value="0">
									<a id="cancel${orderId}${productId}"
										href="user.do?method=cancelItem&orderId=${orderId}&productId=${productId}&quantity=1"
										class="btn btn-danger">Cancel(1)</a>
								</logic:notEqual>
								<logic:equal name="quantity" value="0">
									<a class="btn btn-secondary">Cancelled</a>
								</logic:equal>
							</div>
							<logic:notEqual name="quantity" value="0">
								(<bean:write name="quantity" />)
								<logic:equal name="isCancelled" value="true">
									<span style="font-weight: normal;"> - (Partially
										Cancelled)</span>
								</logic:equal>
								<div class="form-inline form-group mb-2"
									style="float: right; margin: 1%;">
									<select onchange="quantityValue(${orderId},${productId})"
										id="quantity${orderId}${productId}"
										class=" form-control form-control-sm">
										<%
											for (int i = 1; i <= (Integer) quantity; i++) {
										%>
										<option><%=i%></option>
										<%
											}
										%>
									</select>
								</div>
							</logic:notEqual>
							<logic:equal name="quantity" value="0">
								<span style="font-weight: normal;"> - (You've cancelled
									the entire order)</span>
							</logic:equal>
							<logic:notEmpty name="cancelLists">
							<br>	<div style="margin-top:1%;" class="btn-group">
									<button class="btn btn-info btn-sm dropdown-toggle"
										type="button" data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">Cancelled List</button>
									<div class="dropdown-menu">
										<logic:iterate id="cancelList" name="cancelLists">
											<a class="dropdown-item" href="#">Quantity : <bean:write
													name="cancelList" property="productQuantity" /> Time : <bean:write
													name="cancelList" property="timestamp" /></a>
										</logic:iterate>
									</div>
								</div>
							</logic:notEmpty>
						</div>
					</div>
				</logic:iterate>
			</div>
		</div>
	</logic:iterate>
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
	function quantityValue(id1,id2){
		var id=id1+''+id2;
		var i=$('#quantity'+id).val();
		console.log(i);
		$('#cancel'+id).text('Cancel('+i+')');
		var href='user.do?method=cancelItem&orderId='+id1+'&productId='+id2+'&quantity='+i;
		console.log(href);
		$('#cancel'+id).attr('href',href);
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