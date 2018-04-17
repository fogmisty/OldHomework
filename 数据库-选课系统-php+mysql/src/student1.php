<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询学生基本信息</title>
</head>
<a href="catalogue.php">返回</a>
<style type="text/css">
<!--
body{
background-image: url(picture/9.png);
background-color:#C6E2FF;
background-size:100% auto;
background-repeat: no-repeat;
position: relative;
left: 70px;
top:40px;
}
</style>

<body>
<center>
<table style = "border:dotted; border-color:#2200FF" border = 1 width=90% align=center><tr>
<h3>所有学生基本信息</h3>
<tr><th>姓名</th><th>学号</th><th>性别</th><th>班级</th><th>入学年份</th><th>入学年龄</th></tr>
<?PHP
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$q = "select * from Student";//设置查询指令
$result = mysql_query($q);//执行查询
while($row = mysql_fetch_assoc($result))//将result结果集中查询结果取出一条
{
 echo"<tr><td>".$row["name"]."</td><td>".$row["sid"]."</td><td>".$row["sex"]."</td><td>".$row["sclass"]."</td><td>".$row["syear"]."</td><td>".$row["sage"]."</td><tr>";
 
}
mysql_close($link);
?>
</table>

</center>
</body>

<body>
<center>
<h3>查询指定学生信息</h3>
<form action="" method="post">
请输入学生学号：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=90% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");
if($_POST['submit']!="")
{
$key=$_POST['key'];
$sql="select * from student where sid like '$key'";
$rs=mysql_query($sql,$conn);
while($info = mysql_fetch_array($rs)){
	echo"<tr><td>".$info['name']."</td><td>".$info['sid']."</td><td>".$info['sex']."</td><td>".$info['sclass']."</td><td>".$info['syear']."</td><td>".$info['sage']."</td><tr>";
}
}
	mysql_close($conn);
?>
</table>

<form action="" method="post">
请输入学生姓名：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=90% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");
if($_POST['submit']!="")
{
$key=$_POST['key'];
$sql="select * from student where name like '$key'";
$rs=mysql_query($sql,$conn);
while($info = mysql_fetch_array($rs)){
	echo"<tr><td>".$info['name']."</td><td>".$info['sid']."</td><td>".$info['sex']."</td><td>".$info['sclass']."</td><td>".$info['syear']."</td><td>".$info['sage']."</td><tr>";
}
}
	mysql_close($conn);
?>
</table>


</center>
</body>

</html>
