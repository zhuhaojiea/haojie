<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/21 0021
  Time: 上午 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/js/treetable/css/jquery.treetable.css" rel="stylesheet">
    <link href="/js/treetable/css/jquery.treetable.theme.default.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<table id="example-basic">
    <caption>Basic jQuery treetable Example</caption>
    <thead>
    <tr>
        <th>Tree column</th>
        <th>Additional data</th>
    </tr>
    </thead>
    <tbody>
    <tr data-tt-id="1">
        <td>Node 1: Click on the icon in front of me to expand this branch.</td>
        <td>I live in the second column.</td>
    </tr>
    <tr data-tt-id="1.1" data-tt-parent-id="1">
        <td>Node 1.1: Look, I am a table row <em>and</em> I am part of a tree!</td>
        <td>Interesting.</td>
    </tr>
    <tr data-tt-id="1.1.1" data-tt-parent-id="1.1">
        <td>Node 1.1.1: I am part of the tree too!</td>
        <td>That's it!</td>
    </tr>
    <tr data-tt-id="2">
        <td>Node 2: I am another root node, but without children</td>
        <td>Hurray!</td>
    </tr>
    </tbody>
</table>
    <script src="/js/jquery.min.js"></script>
    <script src="/js/treetable/jquery.treetable.js"></script>

    <script>
        $("#example-basic").treetable({ expandable: true });
    </script>
</body>
</html>
