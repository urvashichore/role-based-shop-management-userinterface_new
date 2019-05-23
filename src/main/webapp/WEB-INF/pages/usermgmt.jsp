<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Management Tab</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<h2>User Roles</h2>
			<ul>
				<c:forEach var="userProfile" items="${userProfiles}">
					<li>"${userProfiles}"</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</body>
</html>