<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ログイン</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

	<h2>ログイン</h2>
		<form action="LoginServlet" method="post">
		
			<label for="username">ユーザーネーム</label>
			<input type="text" id="username" name="userName">
			
			<label for="password">パスワード</label>
			<input type="password" id="password" name="password">
			<input type="submit" value="ログイン">
			
			<button type="submit">ログイン</button>
		</form>

</body>
</html>