<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Renew Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Renewscript.js"></script>
</head>
<body class="library-page">
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div>
    <div class="form-container">
        <h2>Renew Book</h2>
        <form:form action="submitRenewBook" method="post" modelAttribute="renew">
            <form:label path="id" for="id">Student id</form:label>
            <form:input path="id" id="id" required="true"/>
            <form:errors path="id" cssClass="error"/>
            
            <form:label path="bookid" for="bookid">Book ID</form:label>
            <form:input path="bookid" id="bookid" required="true"/>
            <form:errors path="bookid" cssClass="error"/>
            </br>
            <button type="button" onclick="setExtendedReturnDate();calculateFine();">Enter</button>
            
            <form:label path="date_of_lending" for="date_of_lending">Date Of Lending</form:label>
            <form:input path="date_of_lending" id="date_of_lending" value="2024-04-28" readonly="true"/>
            
            <form:label path="date_of_return" for="date_of_return">Date Of Return</form:label>
            <form:input path="date_of_return" id="date_of_return" value="2024-06-28" readonly="true"/>
            
            <form:label path="extended_return_date" for="extended_return_date">Extended return date</form:label>
            <form:input path="extended_return_date" id="extended_return_date" readonly="true"/>
            
            <form:label path="fine" for="fine">Fine</form:label>
            <form:input path="fine" id="fine" readonly="true"/>
            
            <form:label path="fine_amount_left" for="fine_amount_left">Fine Amount left</form:label>
            <form:input path="fine_amount_left" id="fine_amount_left" value="100" readonly="true"/>
        </br>
            <button type="button">Pay</button>
        </br>
            
            <button type="submit">Renew</button>
        </form:form>
    </div>
</body>
</html>