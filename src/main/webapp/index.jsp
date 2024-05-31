<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="style.css">
    <script src="https://kit.fontawesome.com/c7b06806bb.js" crossorigin="anonymous"></script>
</head>
<body>
<div id = "searchOptions">
    <label for="searchBox">Search:</label>
    <input type="text" id = "searchBox">
    <button class = "searchBarBtns" id="searchButton">Search</button>
    <button class = "searchBarBtns" id = "filterButton">Filter</button>
    <button id="exitButton">x</button>
    <hr id="break">
    <select name="" id="selectFeature" multiple size = "2">
        <option value="dog Friendly">dog Friendly</option>
        <option value="outdoor seating">outdoor seating</option>
        <option value="free wifi">free wifi</option>
        <option value="wheelchair accessible">wheelchair accessible</option>
    </select><br>
    <input type="text" name="" id="lowStarInput" placeholder="from">
    <input type="text" name="" id="highStarInput" placeholder="to"><br>
    <select name="" id="costSelect">
        <option value="$$$">$$$</option>
        <option value="$$">$$</option>
        <option value="$">$</option>
    </select>

</div>

<div id = "searchBar">
    <button id="removeBtn" onclick="removeAllElements()">purge</button>
    <button id="addBtm" onclick="insertElement()">add</button>
    <i id="searchIcon" class="fa-solid fa-magnifying-glass"></i>
    <button></button>
</div>

<div id="soapBox">
    <div id="leftBtnDiv" class = "buttonDiv">
        <button id="leftScrollButton" onclick="scrollBack()" class = "scrollBtn">&lt;</button>
    </div>

    <ul id = "enumerate-list">
        <li><div class = "enumerate"></div></li>
        <li><div class = "enumerate"></div></li>
        <li><div class = "enumerate"></div></li>
    </ul>
    <div id="rightBtnDiv" class = "buttonDiv">
        <button id="rightScrollButton" onclick="scrollRight()" class = "scrollBtn">&gt;</button>
    </div>
</div>

<script src="index.js"></script>
</body>
</html>