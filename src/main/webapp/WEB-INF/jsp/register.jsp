<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>新規登録</title>
</head>

<body>
	
	<h2>ログイン</h2>
		<form ation="register" method="post">
		
			<label for="username">ユーザー名</label>
			<input type="text" id="username" name="username" required>
			
			<label for="password">パスワード</label>
			<input type="text" id="password" name="password" required>
			
			<button type="submit">登録</button>
			
		</form>
		
</body>
</html>