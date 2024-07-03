<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Returning</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Returnscript.js"></script>
</head>
<body>
    <div class="returnBook-container">
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="#">Logout</a>
        </div>
    </div>
    <div class="form-container">
        <h2>Book Returning</h2>
        <form:form action="${pageContext.request.contextPath}/submitReturnBook" method="post" modelAttribute="returnbook">

            <form:label path="id" for="id">Student/Teacher id</form:label>
            <form:input path="id" id="id" required="true"/>
            
            <form:label path="bookid" for="bookid">Book ID</form:label>
            <form:input path="bookid" id="bookid" required="true"/>
            </br>
            <button type="button" onclick="setDates();calculateFine();">Enter</button>
            
            <form:label path="date_of_lending" for="date_of_lending">Date Of Lending</form:label>
            <form:input path="date_of_lending" id="date_of_lending" value="2024-04-20" readonly="true"/>
            
            <form:label path="date_of_return" for="date_of_return">Date Of Return</form:label>
            <form:input path="date_of_return" id="date_of_return" value="2024-06-20" readonly="true"/>
             
            <form:label path="actual_date_of_return" for="actual_date_of_return">Actual date of returning</form:label>
            <form:input path="actual_date_of_return" id="actual_date_of_return" readonly="true"/>
            
            <form:label path="fine" for="fine">Fine</form:label>
            <form:input path="fine" id="fine" readonly="true"/>
            
            <form:label path="fine_amount_left" for="fine_amount_left">Fine Amount left</form:label>
            <form:input path="fine_amount_left" id="fine_amount_left" value="100" readonly="true"/>
        </br>
            <button type="button">Pay</button>
        </br>
        </br>   
            <button type="submit">Paid / Return</button>
        </form:form>
    </div>
</div>
</body>
</html>