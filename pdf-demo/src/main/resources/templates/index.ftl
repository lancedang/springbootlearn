<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    </meta>
    <title>Title</title>
    <style>
        body {
            font-family: "FangSong", "STFangsong", "SimSun", "SimHei", "Helvetica", "Arial", "sans-serif";
            font-size: 16px;
            color: black;
            background-color: transparent;
            -webkit-font-smoothing: antialiased;
            line-height: 1.42857143;
        }

    </style>
</head>
<body>
<table border="1" style="width:100%; border:0px;">
    <tr>
        <td>用户编号</td>
        <td>用户名称</td>
        <td>用户地址</td>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.address}</td>
            <td>${user.date?number_to_date?string("yyyy-MM-dd")}</td>
            <td><img src="https://img1.bdstatic.com/static/common/img/icon_cf1b905.png"></img></td>
        </tr>
    </#list>
</table>
</body>
</html>