<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Recipes</title>
    <!-- 引入Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f8f8;
            color: #333;
            font-family: Arial, sans-serif;
        }
        h2, h3 {
            color: #444;
        }
        .table {
            background-color: #fff;
        }
        .table th, .table td {
            border: 1px solid #ddd;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
      	.app-icon {
            height: 150px;
            width: 150px;
            position: absolute;
            top: 10px; 
            left: 10px;
        }
    </style>
</head>



<body class="container mt-4">
<img src="./resource/icon.png" class="app-icon"/>
    <h2>Search Recipes</h2>
    <form action="searchresult" method="get" class="form-inline mb-4">
        <input type="text" name="name" value="${name}" class="form-control mr-2">
        <input type="submit" value="Search" class="btn btn-primary">
    </form>

    <table class="table">
        <thead class="thead-light">
            <tr>
                <th>Recipe ID</th>
                <th>Name</th>
                <th>Preparation Time</th>
                <th>Method</th>
                <th>Details</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${recipesList}" var="recipe">
                <tr>
                    <td>${recipe.recipeId}</td>
                    <td>${recipe.name}</td>
                    <td>${recipe.prepTime} minutes</td>
                    <td>${recipe.method}</td>
                    <td>
                        <form action="recipedetail" method="get">
                            <input type="hidden" name="recipeId" value="${recipe.recipeId}" />
                            <input type="submit" value="View Details" class="btn btn-info btn-sm" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
