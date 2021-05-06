<%@page import = "model.ProductManagementModel" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="View/bootstrap.min.css">
    <script src="Components/jquery-3.6.0.js"></script>
    <script src="Components/main.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<form id="formProduct" name="formProduct">

Research ID:<input id="research_id" name="research_id" type="text"class="form-control form-control-sm">
<br>
Name:<input id=name name="name" type="text"class="form-control form-control-sm">
<br>
Description:<input id="description" name="description" type="text"class="form-control form-control-sm">
<br>
Stock Quantity:<input id="stock_quantity" name="stock_quantity" type="text"class="form-control form-control-sm">
<br>
Price:<input id="price" name="price" type="text"class="form-control form-control-sm">
<br>
Added Date:<input id="added_date" name="added_date" type="text"class="form-control form-control-sm">
<br>
<br>
<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">
<input type="hidden" id="hidProductIDSave" name="hidProductIDSave" value="">

</form>

<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>

<br>
<div id="divUsersGrid">
<%
ProductManagementModel product = new ProductManagementModel();
out.print(product.readProducts());
%>
</div>

</body>

</html>