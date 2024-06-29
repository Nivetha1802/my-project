<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Fine Amount</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <script type="text/javascript" src="./script.js"></script>
</head>
<body>
    <div class="popup">
        <h2>Fine Amount</h2>
        <p>Your fine amount is: <span id="fineAmount">₹10</span></p>
        <button onclick="closePopup()">Close</button>
    </div>
</body>
</html>