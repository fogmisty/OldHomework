<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加／删除／修改学生信息</title>
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
		<form action="inserts.php" method="post">
			<h3>添加学生信息</h3>
			姓名：<input type = "text" name = "name"/> <br/>
			学号：<input type = "text" name = "sid"/> <br/>
            性别：
                  <select name="sex">
                     <option selected="selected" value="1">男</option>
                     <option value="2">女</option> 
                  </select>
                  <br/>
			班级：<input type = "text" name = "sclass"/> <br/>
		 入学年份：<input type = "text" name = "syear"/> <br/>
		 入学年龄：<input type = "text" name = "sage"/> <br/>
			<input type="submit" value="添加学生"/>
		</form>
	</div>
</body>


<body>
	<div align = "center">
		<form action="changes.php" method="post">
			<h3>修改学生信息</h3>
			姓名：<input type = "text" name = "name"/> <br/>
			学号：<input type = "text" name = "sid"/> <br/>
            性别：
                  <select name="sex">
                     <option selected="selected" value="1">男</option>
                     <option value="2">女</option> 
                  </select>
                  <br/>
			班级：<input type = "text" name = "sclass"/> <br/>
		 入学年份：<input type = "text" name = "syear"/> <br/>
		 入学年龄：<input type = "text" name = "sage"/> <br/>
			<input type="submit" value="修改学生信息"/>
		</form>
	</div>
</body>

</html>

<html>
<body>
<center>
<h3>删除学生信息</h3>
<table width="80%" border="1" cellpadding="0" cellspacing="0">
<tr>
<td>姓名</td>
<td>学号</td>
<td>性别</td>
<td>班级</td>
<td>入学年份</td>
<td>入学年龄</td>
<td>操作</td>
</tr>
<?php
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式

$sql = "select * from Student";//设置查询指令
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
<td><a onclick=\"return confirm('确定删除么')\" href='deletes.php?code={$attr[1]}'>删除</a></td>
</tr>";
}
?>
</table>
</center>
</body>

</html>
 
 
