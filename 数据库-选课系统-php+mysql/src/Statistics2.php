<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统计</title>
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
<h3>总成绩</h3>
<table style = "border:dotted; border-color:#2200FF" border = 1 width=10% align=center><tr>
<?PHP
echo "<tr><td>".总平均成绩."</td><tr>"; 
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
	
$result = mysql_query("SELECT avg(score) FROM Choose");

while($end1 = mysql_fetch_array($result))
{ echo "<tr><td>".$end1['0']."</td><tr>"; }

mysql_close($link);
?>

</table>
<table style = "border:dotted; border-color:#2200FF" border = 1 width=20% align=center><tr>
<?PHP
echo "<tr><td>".成绩分布."</td><tr>"; 
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$query = mysql_query("SELECT count(score) FROM Choose WHERE score=100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."满分"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE score >89 AND score <100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."90 <= score < 100"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE score >79 AND score <90");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."80 <= score < 90"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE score >69 AND score <80");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."70 <= score < 80"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE score >59 AND score <70");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."60 <= score < 70"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE score <60");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."score < 60"."<tr><td>".$end2['0']."</td><tr>"; 
}
	
mysql_close($link);
?>
</table>


<body>
<center>
<h3>查询指定学生加权</h3>
<form action="" method="post">
请输入学生学号：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=10% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");
if($_POST['submit']!="")
{
$key=$_POST['key'];
$result = mysql_query("SELECT avg(score) FROM Choose where sid like '$key'");
while($info = mysql_fetch_array($result)){
	echo "<tr><td>".$info['0']."</td><tr>";
}
}
	mysql_close($conn);
?>
</table>
</center>
</body>

<body>
<center>
<h3>查询指定课程平均分及成绩分布</h3>
<form action="" method="post">
请输入课程编号：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=10% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");
if($_POST['submit']!="")
{
$key=$_POST['key'];
$result = mysql_query("SELECT avg(score) FROM Choose where id like '$key'");
while($info = mysql_fetch_array($result)){
	echo "<tr><td>".$info['0']."</td><tr>";
}
}
	mysql_close($conn);
?>
</table>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=20% align=center><tr>
<?PHP
echo "<tr><td>".成绩分布."</td><tr>"; 
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and score=100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."满分"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and score >89 AND score <100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."90 <= score < 100"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and score >79 AND score <90");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."80 <= score < 90"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and score >69 AND score <80");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."70 <= score < 80"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and score >59 AND score <70");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."60 <= score < 70"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose WHERE id like '$key' and  score <60");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."score < 60"."<tr><td>".$end2['0']."</td><tr>"; 
}
	
mysql_close($link);
?>
</table>
</center>
</body>

<body>
<center>
<h3>查询指定班级平均分及成绩分布</h3>
<form action="" method="post">
请输入班级：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=10% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");
if($_POST['submit']!="")
{
	$key = $_POST['key'];
    $result = mysql_query("SELECT avg(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key'") ;
    while($info = mysql_fetch_array($result))
	{
	    echo "<tr><td>".$info['0']."</td><tr>";
	}
}
	mysql_close($conn);
?>
</table>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=20% align=center><tr>
<?PHP
echo "<tr><td>".成绩分布."</td><tr>"; 
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and score=100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."满分"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and score >89 AND score <100");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."90 <= score < 100"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and score >79 AND score <90");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."80 <= score < 90"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and score >69 AND score <80");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."70 <= score < 80"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and score >59 AND score <70");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."60 <= score < 70"."<tr><td>".$end2['0']."</td><tr>"; 
}
$query = mysql_query("SELECT count(score) FROM Choose left join Student on  Choose.sid = Student.sid where Student.sclass='$key' and  score <60");
while($end2 = mysql_fetch_array($query))
{
	echo "<tr><td>"."score < 60"."<tr><td>".$end2['0']."</td><tr>"; 
}
	
mysql_close($link);
?>
</table>
</center>
</body>

</html>