<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin page</title>
</head>
<body>
<p>You are: ${username}</p>
<#if user??>
    <p>User: ${user.name}</p>
    <p>User age: ${user.age}</p>
    <p>User street: ${user.address.street}</p>
</#if>
<form action="/admin" method="GET">
    User id: <input type="text" name="id"/>
    <input type="submit" value="Submit">
</form>
<#if numberOfUsers??>
    <p>Number of users in DB: ${numberOfUsers}</p>
</#if>
</body>
</html>