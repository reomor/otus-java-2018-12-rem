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
    <p>User phone numbers:</p>
    <ul>
        <#list user.phones as phones>
            <li>${phones.number}</li>
        </#list>
    </ul>
</#if>
<form action="/admin" method="GET">
    User id: <input type="text" name="id"/>
    <input type="submit" value="Submit">
</form>
<#if numberOfUsers??>
    <p>Number of users in DB: ${numberOfUsers}</p>
</#if>
<form action="/admin" method="POST">
    User name: <input type="text" name="name"/>
    <br>
    Age: <input type="text" name="age"/>
    <br>
    Address: <input type="text" name="street"/>
    <br>
    Phone 1:<input type="text" name="phones" >
    <br>
    Phone 2:<input type="text" name="phones"/>
    <br>
    <input type="submit" value="Submit">
</form>
</body>
</html>