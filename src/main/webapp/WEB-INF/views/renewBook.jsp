<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Renew Book</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Renewscript.js"></script>
</head>
<body>
    <div class="header">
        <h1>Digital Library</h1>
        <div class="nav">
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="#">Logout</a>
        </div>
    </div>
    <div class="form-container">
        <h2>Renew Book</h2>
        <form action="submitRenewBook" method="post">
            <label path="id" for="id">Student id</label>
            <input type="text" id="id" name="id">
            
            <label path="bookname" for="bookname">Book name</label>
            <input path="bookname" type="text" id="bookname" name="bookname">
            
            <label path="date_of_lending" for="date_of_lending">Date Of Lending</label>
            <input path="date_of_lending" type="text" id="date_of_lending" name="date_of_lending" value="2024-04-28" readonly>
            
            <label path="date_of_return" for="date_of_return">Date Of Return</label>
            <input path="date_of_return" type="text" id="date_of_return" name="date_of_return" value="2024-06-28" readonly>
            
            <label path="extended_return_date" for="extended_return_date">Extended return date</label>
            <input path="extended_return_date" type="text" id="extended_return_date" name="extended_return_date" readonly>
            
            <label path="fine" for="fine">Fine</label>
            <input path="fine" type="text" id="fine" name="fine" readonly>
            
            <label path="fine_amount_left" for="fine_amount_left">Fine Amount left</label>
            <input path="fine_amount_left" type="text" id="fine_amount_left" name="fine_amount_left" readonly></br>
            
            <button type="submit">Renew</button>
        </form>
    </div>
</body>
</html>
