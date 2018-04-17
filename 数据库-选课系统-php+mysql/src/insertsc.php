<?PHP
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
$sid = $_POST['sid'];
$id = $_POST['id'];
$year = $_POST['year'];

//$q = "select year from Choose where id = '$id";//设置查询指令
//$result = mysql_query($q);//执行查询
//$end1 = mysql_fetch_array($result);
//有问题
if($year > 2015)
{
    echo "<script> {window.alert('年级不符合要求');location.href='sc2.php'} </script>";
	exit(0);
}
//查询此学生是否存在
$q1 = mysql_query("select name from Student where sid = '$sid'");//设置查询指令
$result1 = mysql_fetch_assoc($q1);
//需要加入课程截止年份的判断
if(!empty($result1))
{
	$sql = "insert into Choose(sid,id,year)values('$sid','$id','$year')";
	
	if(!mysql_query($sql,$link))
    {
       die('Error: ' . mysql_error());
    }
    echo "<script> {window.alert('插入成功');location.href='sc2.php'} </script>";
}
else
{
	echo "<script> {window.alert('此学生不存在');location.href='sc2.php'} </script>";
	exit(0);
}
mysql_close($link)
?>
<a href="catalogue.php">返回</a>

</center>
</body>

