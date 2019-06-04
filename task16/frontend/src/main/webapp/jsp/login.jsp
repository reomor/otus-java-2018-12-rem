<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main page</title>
    <link href="resources/css/style.css" rel="stylesheet"/>
</head>
<body>
<p>Link to start page: <a href="index.html">Start</a></p>
<p>This is login page</p>
<form action="login" method="POST">
    <ul class="form-style-1">
        <li><label>User name<span class="required">*</span></label><input type="text" name="username" class="field-long" required></li>
        <li><label>Password<span class="required">*</span></label><input type="password" name="password" class="field-long" required></li>
        <input type="submit" value="Login">
    </ul>
</form>
</body>
</html>