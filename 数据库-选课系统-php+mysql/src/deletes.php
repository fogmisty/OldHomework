<?php
$code = $_GET["code"];
//echo $code;
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
//删除学生基本信息
$result = mysql_query("delete from Student where sid = $code");
//删除选课信息
$result2 = mysql_query("delete from Choose where sid='$code'") ;
if($result2)
{
	echo "<script> {window.alert('删除成功');location.href='student2.php'} </script>";
}
else
{
	die('Error: ' . mysql_error());
}