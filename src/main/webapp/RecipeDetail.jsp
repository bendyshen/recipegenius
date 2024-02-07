<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Recipe Details</title>
    <!-- 引入Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f8f8;
            color: #333;
            font-family: Arial, sans-serif;
        }
        h1, h2, h3 {
            color: #444;
        }
        ul.list-unstyled {
            padding-left: 0;
        }
        ul.list-unstyled li {
            list-style-type: none;
        }
        .btn-secondary {
            background-color: #6c757d;
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
    <h1>Here is the detailed nutrition breakdown:</h1>
    <c:set var="recipe" value="${requestScope.recipe}" />
    <c:set var="nutritionalInfo" value="${requestScope.nutritionalInfo}" />
    <c:set var="amountAndIngredientList" value="${requestScope.amountAndIngredientList}" />
	<hr>
    <h2>${recipe.name}</h2>
    <ul class="list-unstyled">
        <li>Preparation Time: ${recipe.prepTime} minutes</li>
        <li>Method: ${recipe.method}</li>
    </ul>

    <h3>Nutrition Information:</h3>
    <ul class="list-unstyled">
        <li>Calories: ${nutritionalInfo.totalCalories}</li>
        <li>Protein: ${nutritionalInfo.totalProtein}g</li>
        <li>Carbohydrates: ${nutritionalInfo.totalCarbs}g</li>
        <li>Fat: ${nutritionalInfo.totalFat}g</li>
    </ul>

    <h3>Ingredients:</h3>
    <ul class="list-unstyled">
        <c:forEach items="${amountAndIngredientList}" var="entry">
            <li>${entry.key} of ${entry.value}</li>
        </c:forEach>
    </ul>

    <p><a href="#" onclick="history.go(-1); return false;" class="btn btn-secondary">Back to Recipes List</a></p>
</body>
</html>
