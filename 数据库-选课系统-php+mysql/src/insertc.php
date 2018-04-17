<?PHP
$link = mysql_connect('127.0.0.1','root','toor')or die("数据库连接失败");
//连接数据库
mysql_select_db('CurriculaVariable',$link);//选择数据库
mysql_query("set names utf8");//设置编码格式
//$mysqli=new mysqli("localhost:8889","root","root","test");

//$sql = "insert into course(id,called,teacher,credit,grade,dyear) values ($_POST[\"id'],$_POST['called'],$_POST['teacher'],$_POST['credit'],$_POST['grade'],$_POST['dyear'])";
$id = $_POST['id'];
$called = $_POST['called'];
$teacher = $_POST['teacher'];
$credit = $_POST['credit'];
$grade = $_POST['grade'];
$dyear = $_POST['dyear'];


$sql = "insert into Course(id,called,teacher,credit,grade,dyear)
values('$id','$called','$teacher','$credit','$grade','$dyear')";

if(!mysql_query($sql,$link))
{
   die('Error: ' . mysql_error());
}
echo "<script> {window.alert('插入成功');location.href='Course2.php'} </script>";

mysql_close($link)
?>
<a href="catalogue.php">返回</a>

</center>
</body>