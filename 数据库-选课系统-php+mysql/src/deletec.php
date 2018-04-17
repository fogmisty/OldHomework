<?php
$code = $_GET["code"];
//echo $code;
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
$sql = "delete from Course where id = $code";

//执行
$result = mysql_query($sql);

if($result)
{
	echo "<script> {window.alert('删除成功');location.href='Course2.php'} </script>";
}
else
{
	die('Error: ' . mysql_error());
}