<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <a href="/login">Signin</a>/<a href="/signup">Signup</a>
            <a href="#">Logout</a>
        </div>
    </div> 
    <div class="form-container">
        <h2>Book Lending</h2>
        <form action="submitLendBook" method="post" modelAttribute="lend">
            <label path="bookid" for="book_name">Book ID</label>
            <input path="bookid" type="text" id="bookid" name="bookid">
           
            <label path="id" for="id">Lender ID number</label>
            <input path="id" type="text" id="id" name="id">
            </br>
            <button type="button" onclick="setDates()">Enter</button>
            
            <label path="date_of_lending" for="date_of_lending">Date Of Lending</label>
            <input path="date_of_lending" type="text" id="date_of_lending" name="date_of_lending" readonly>
            
            <label path="date_of_return" for="date_of_return">Date Of Return</label>
            <input path="date_of_return" type="text" id="date_of_return" name="date_of_return" readonly>

            <label path="fine_amount_left" for="fine_amount_left">Fine Amount left</label>
            <input path="fine_amount_left" type="text" id="fine_amount_left" name="fine_amount_left" value="100" readonly>
        </br>
            <button type="button">Pay</button>
        </br>
            
            <p class="fine-warning">! If Book is returned after due date ₹10 will be charged as fine for each day after return date</p>
            
            <button type="submit">Add</button>
        </form>
    </div>
</body>
</html>