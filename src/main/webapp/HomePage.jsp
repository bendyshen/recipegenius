<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Recipe Search</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 150px;
            background-image: url('./resource/IMG_6491.PNG'); /* Replace 'your-image.jpg' with the path to your image */
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }

        /* Apply a blur effect to the background image */
        .background-blur {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            z-index: -1;
            filter: blur(8px); /* Adjust the px value to increase or decrease the blur */
            -webkit-filter: blur(8px); /* For Safari */
            background-image: inherit; /* Inherits the same background from body */
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
        }

        input[type="text"] {
            width: 50%;
            padding: 12px 20px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 2px solid #ccc;
            border-radius: 4px;
            background: rgba(255, 255, 255, 0.8); /* Slightly transparent white */
        }

        input[type="submit"], .btn {
            background-color: #add8e6; /* Light blue */
            color: white;
            padding: 10px 15px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover, .btn:hover {
            background-color: #99c2ff; /* Slightly darker light blue on hover */
        }

        .btn {
            font-size: 0.85rem;
            text-decoration: none;
            display: inline-block;
        }

        .logo {
            margin-bottom: 20px;
        }

        .content-wrapper {
            position: relative; /* Ensures the content is on top of the blurred background */
            z-index: 10;
        }

    </style>
</head>
<body>

<!-- The blurred background overlay -->
<div class="background-blur"></div>

<!-- Content wrapper to keep your content on top of the blurred background -->
<div class="content-wrapper">
    <div class="logo">
        <!-- Logo can be added using an image tag -->
        <img src="./resource/IMG_6321.PNG" width="400"> <!-- Adjust the src as needed -->
    </div> 

    <form action="searchresult" method="get"> <!-- Update the action to point to your searchresult servlet -->
        <input type="text" name="query" placeholder="Find a recipe...">
        <input type="submit" value="Search">
    </form>

    <!-- Add a button that links to Users.html -->
    <a href="./resource/Users.html" class="btn">Sign Up</a>
</div>

</body>
</html>
