<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Lending</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Lendingscript.js"></script>
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Sign In</a>/<a href="/signup">Signup</a>
            <a href="/login">Logout</a>
        </div>
    </div> 
    <div class="form-container">
        <h2>Book Lending</h2>
        <form:form action="${pageContext.request.contextPath}/submitLendBook" method="post" modelAttribute="lend">
            <form:label path="bookid" for="book_name">Book ID</form:label>
            <form:input path="bookid" id="bookid" required="true"/>
            <form:errors path="bookid" cssClass="error"/>
           
            <form:label path="id" for="id">Lender ID number</form:label>
            <form:input path="id" id="id" required="true"/>
            <form:errors path="id" cssClass="error"/>
            </br>
            <button type="button" onclick="setDates()">Enter</button>
            
            <form:label path="date_of_lending" for="date_of_lending">Date Of Lending</form:label>
            <form:input path="date_of_lending" id="date_of_lending" readonly="true"/></br>
            
            
            <form:label path="date_of_return" for="date_of_return">Date Of Return</form:label>
            <form:input path="date_of_return" id="date_of_return" readonly="true"/></br>
            

            <form:label path="fine_amount_left" for="fine_amount_left">Fine Amount left</form:label>
            <form:input path="fine_amount_left" id="fine_amount_left" value="100" readonly="true"/></br>
            
        </br>
            <button type="button">Pay</button>
        </br>
            
            <p class="fine-warning">! If Book is returned after due date ₹10 will be charged as fine for each day after return date</p>
            
            <button type="submit">Add</button>
        </form:form>
    </div>
</body>
</html>