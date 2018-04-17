<?PHP
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
$name = $_POST['name'];
$sid = $_POST['sid'];
$sex = $_POST['sex'];
$sclass = $_POST['sclass'];
$syear = $_POST['syear'];
$sage = $_POST['sage'];

if($sage>50 || $sage<10)
{
    echo "<script> {window.alert('年龄不符合要求');location.href='student2.php'} </script>";
	exit(0);
}

$sql = "insert into Student(name,sid,sex,sclass,syear,sage)
values('$name','$sid','$sex','$sclass','$syear','$sage')";

if(!mysql_query($sql,$link))
{
   die('Error: ' . mysql_error());
}
echo "<script> {window.alert('插入成功');location.href='student2.php'} </script>";

mysql_close($link)
?>
<a href="catalogue.php">返回</a>

</center>
</body>

