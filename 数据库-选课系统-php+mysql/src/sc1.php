<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>查询选课信息</title>
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
<h3>查询选课信息</h3>
<form action="" method="post">
请输入姓名：
<input type="text" name="key">
<input type="submit" name="submit" value="查询">
</form>

<table style = "border:dotted; border-color:#2200FF" border = 1 width=90% align=center><tr>
<?php
$conn=mysql_connect("127.0.0.1","root","toor");
mysql_select_db("CurriculaVariable",$conn);
mysql_query("set names utf8");//设置编码格式
if($_POST['submit']!="")
{
$key=$_POST['key'];
$sql = mysql_query("SELECT id FROM Choose left join Student on  Choose.sid = Student.sid where Student.name='$key'");
while($info = mysql_fetch_array($sql))
{
	 echo "<tr><td>".$info['0']."</td><tr>";
}
$sql2 = "SELECT * FROM Choose where id='$sql'";
$rs=mysql_query($sql2,$conn);
while($info = mysql_fetch_array($rs)){
	echo"<tr><td>".$info['id']."</td><td>".$info['called']."</td><td>".$info['teacher']."</td><td>".$info['credit']."</td><td>".$info['grade']."</td><td>".$info['dyear']."</td><tr>";
}
}
	mysql_close($conn);
?>
</table>

<table>
<body>
	<div align = "center">
		<form action="insertscore.php" method="post">
			<h3>给分</h3>
			学号：<input type = "text" name = "sid"/> <br/>
			课程号：<input type = "text" name = "id"/> <br/>
			年级：<input type = "text" name = "grade"/> <br/>
			分数：<input type = "text" name = "score"/> <br/>
			<input type="submit" value="给分"/>
		</form>
	</div>
</body>
</table>

</center>
</body>
</html>