<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin page</title>
    <link href="resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<p>You are: ${username}</p>
<form action="admin" method="GET">
    <ul class="form-style-1">
        <li><label>User id<span class="required">*</span></label><input type="text" name="id" class="field-long" placeholder="1" required></li>
        <input type="submit" value="Get user">
    </ul>
</form>
<p>User by id:</p>
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
<#if numberOfUsers??>
    <p>Number of users in DB: ${numberOfUsers}</p>
</#if>
<form action="admin" method="POST">
    <ul class="form-style-1">
        <li><label>User name<span class="required">*</span></label><input type="text" name="name" class="field-long" placeholder="Name" required></li>
        <li><label>Age<span class="required">*</span></label><input type="text" name="age" class="field-long" placeholder="18" required></li>
        <li><label>Street<span class="required">*</span></label><input type="text" name="street" class="field-long" placeholder="Lenina, 1" required></li>
        <li><label>Phone 1<span class="required">*</span></label><input type="text" name="phones" class="field-long" placeholder="123" required></li>
        <li><label>Phone 2</label><input type="text" name="phones" class="field-long" placeholder="123"></li>
        <input type="submit" value="Submit">
    </ul>
</form>
<#if cachedUsers??>
    <#list cachedUsers as user>
        <li>${user.name}</li>
        <li>${user.age}</li>
    </#list>
</#if>
</body>
</html>