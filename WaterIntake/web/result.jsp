<%-- 
    Document   : result
    Created on : Jun 5, 2025, 5:50:52 PM
    Author     : tengk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
<head>
    <title>Water Intake Result</title>
    <style>
        body { font-family: Arial; background-color: #f0f8ff; padding: 40px; }
        .result-box {
            max-width: 500px; margin: auto; background: white; padding: 30px;
            border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 { color: #1e88e5; }
    </style>
</head>
<body>
<div class="result-box">
    <h2>Water Intake Result</h2>
    <p><%= request.getAttribute("result") %></p>
    <br>
    <a href="index.html">Calculate Again</a>
</div>
</body>
</html>