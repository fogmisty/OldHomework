<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课程基本信息／选课情况</title>
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
	<div align = "center">
		<form action="insertc.php" method="post">
			<h3>添加课程</h3>
			课程编号：<input type = "text" name = "id"/> <br/>
			课程名称：<input type = "text" name = "called"/> <br/>
			授课教师：<input type = "text" name = "teacher"/> <br/>
			学分：<input type = "text" name = "credit"/> <br/>
		    面向年级：<input type = "text" name = "grade"/> <br/>
		    取消年份：<input type = "text" name = "dyear"/> <br/>
			<input type="submit" value="添加课程"/>
		</form>
	</div>
</body>

</html>



<html>
<body>
<center>
<h3>删除课程</h3>
<table width="80%" border="1" cellpadding="0" cellspacing="0">
<tr>
<td>课程编号</td>
<td>课程名称</td>
<td>授课教师</td>
<td>学分</td>
<td>面向年级</td>
<td>取消年份</td>
<td>操作</td>
</tr>
<?php
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$sql = "select * from Course";//设置查询指令
//执行
$result = mysql_query($sql);

//读取数据
while($attr = mysql_fetch_row($result))
{
echo "<tr>
<td>{$attr[0]}</td>       
<td>{$attr[1]}</td>
<td>{$attr[2]}</td>
<td>{$attr[3]}</td>
<td>{$attr[4]}</td>
<td>{$attr[5]}</td>
<td><a onclick=\"return confirm('确定删除么')\" href='deletec.php?code={$attr[0]}'>删除</a></td>
</tr>";
}
?>
</table>
</center>
</body>

</html>
 