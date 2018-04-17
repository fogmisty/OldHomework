<?PHP
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
$sid = $_POST['sid'];
$id = $_POST['id'];
$grade = $_POST['grade'];
$score = $_POST['score'];

$sql = "update Choose set score='".$score."' where sid='".$sid."' and id='".$id."'";


if(!mysql_query($sql,$link))
{
   die('Error: ' . mysql_error());
}
echo "<script> {window.alert('成功');location.href='sc2.php'} </script>";

mysql_close($link)
?>
<a href="catalogue.php">返回</a>

</center>
</body>

