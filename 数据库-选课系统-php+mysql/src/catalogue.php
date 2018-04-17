<html xmlns=" http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>简陋的选课系统</title>
<!--[if lt IE 6]>
<style type="text/css">
.z3_ie_fix{
float:left;
}
</style>
<![endif]-->
<center>

<h4>
<span class="STYLE4">欢 迎 使 用 简 陋 的 选 课 系 统</span></h4>

<style type="text/css">
<!--
.passport{
border:10px solid #8470FF;
background-color:#F0FFFF;
width:560px;
height:350px;
position:absolute;
left:43.9%;
top:20.9%;
margin-left:-200px;
margin-top:-55px;
font-size:14px;
text-align:left;
line-height:26px;
color:#6C32FB;
}
-->
</style>
<div class="passport">
<div style="padding-top:6px;">
<form action="?yes" method="post" style="margin:0px;">
天津大学计算机学院需要完成一个学生选课及成绩管理系统，该系统要求能够登记，修改，查询，统计学生、课程、选课的基本信息。
学生的基本信息包括：学号，姓名，性别，入学年龄，入学年份，班级。其中性别只能是（男或女），入学年龄在（10－50），学号10位长。
课程的基本信息包括：课程编号，课程名称，教课教师姓名，学分，课程适合年级，取消年份（可为空）。课程编号：7位长，只有学生的年级大于等于课程适合年级而且选课时间早于取消年份时方可选课。
选课信息：学生学号，课程号，选课年份，成绩。其中学号是指向学生表的外关键字，课程号是指向课程表的外关键字。要求如果学生退学则删除该生的所有选课信息。
能够根据学生姓名或学号查询生的基本信息或所选课的情况。
能够根据学生姓名或学号和课程名称或课程编号查询该生该课程的成绩。
能够根据课程名称或课程编号查询课程的基本信息或该课程的选课情况。
能够统计出学生的加权平均成绩，班级的加权平均成绩，课程的成绩分布（不及格，60－69，70－79，80－89，90－99，满分），课程的平均成绩.
能够修改学生，课程，选课信息中的所有信息。
能够增加删除学生，课程，选课信息。
</form>
</div>
</div>
</body>
</center>
<a href="leave.php">退出</a>
<style type="text/css">
<!--
body{
background-image: url(picture/10.jpg);
background-size:cover;
background-repeat: no-repeat;
position: relative;
left: 70px;
top:40px;
}
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
<!--

body{//字体
    margin-left: 1px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
} .STYLE1 {
    font-size: 12px;
    color: #ffffff;
} .STYLE3 {
    font-size: 12px;
    color: #6414F9;
}.STYLE4 {
    font-size: 30px;
    color: 8470FF;
}
-->
</style>
<table width="170" height="100%" border="0" cellpadding="0" cellspacing="0">
   <tr>
      <td height="30" background="picture/3.png">
         <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
               <td width="19%">&nbsp;
               </td>
               <td width="81%" height="20">
                  <span class="STYLE1">菜单</span>
               </td>
            </tr>
         </table>
     </td>
    </tr>
    <tr>
      <td valign="top">
         <table width="170" border="0" align="center" cellpadding="0" cellspacing="0">
            <!-- test -->
               <tr>
                  <td>
                     <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                           <td height="25" td wide="151" background="picture/4.jpg" id="imgmenu1" class="menu_title" onMouseOver="this.className='menu_title2';" onClick="showsubmenu(1)" onMouseOut="this.className='menu_title';" style="cursor:hand">
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                  <td width="18%">&nbsp;
                                  </td>
                                  <td width="82%" class="STYLE1">
                                    关于学生
                                  </td>
                                  </tr>
                               </table>
                            </td>
                       </tr>
                       <!-- 标题-->
                       <tr>
                       <!-- 所有子项-->
                       <td background="picture/5.png" id="submenu1">
                          <div class="sec_menu">
                              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                 <tr>
                                   <td>
                                     <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                       <tr>
                                         <td width="16%" height="25">
                                            <div align="center">
                                               <img src="picture/6.jpg" width="10" height="10" />
                                            </div>
                                         </td>
                                         <td width="84%" height="23">
                                            <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                               <tr>
                                                <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                  <span class="STYLE3"><a href="student1.php" target="main">查询学生基本信息</a></span>
                                                </td>
                                              </tr>
                                           </table>
                                         </td>
                                      </tr>
                                      <!--sub one-->
                                      <tr>
                                        <td height="23">
                                          <div align="center">
                                             <img src="picture/6.jpg" width="10" height="10" />
                                        </td>
                                          <td height="25">
                                             <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                  <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                  <span class="STYLE3"><a href="student2.php" target="main">添加／删除/修改学生信息</a></span>
                                                  </td>
                                                 </tr>
                                              </table>
                                          </td>
                                      </tr>
								</table>
                              </td>
                             </tr>
                              <tr>
                                 <td height="5">
                                    <img src="picture/7.jpg" width="170" height="5" />
                                 </td>
                              </tr>
                             </table>
                             </div><!-- end menu-->
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr><!-- test -->
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="25" background="picture/4.jpg" id="imgmenu2" class="menu_title" onmouseover="this.className='menu_title2';" onclick="showsubmenu(2)" onmouseout="this.className='menu_title';" style="cursor:hand">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="18%">&nbsp;                                                
                                            </td>
                                            <td width="82%" class="STYLE1">
                                                关于课程
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td background="picture/5.png" id="submenu2">
                                    <div class="sec_menu">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                                        <!-- sub one -->
                                                        <tr>
                                                            <td width="16%" height="25">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td width="84%" height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="Course1.php" target="main">查询课程基本信息</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <!--end sub one --><!-- sub one-->
                                                        <tr>
                                                            <td height="23">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="Course2.php" target="main">添加／删除课程</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height="5">
                                                    <img src="picture/7.jpg" width="170" height="5" />
                                                </td>
                                            </tr>
                                        </table>
                                    </div><!-- end menu-->
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr><!-- test -->
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="25" background="picture/4.jpg" id="imgmenu3" class="menu_title" onmouseover="this.className='menu_title2';" onclick="showsubmenu(3)" onmouseout="this.className='menu_title';" style="cursor:hand">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="18%">&nbsp;                                                
                                            </td>
                                            <td width="82%" class="STYLE1">
                                                关于选课
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td background="picture/5.png" id="submenu3">
                                    <div class="sec_menu">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                                        <!-- sub one -->
                                                        <tr>
                                                            <td width="16%" height="25">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td width="84%" height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="sc1.php" target="main">查询已选课程/给分</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <!--end sub one --><!-- sub one-->
                                                        <tr>
                                                            <td height="23">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="sc2.php" target="main">选课／退课</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td height="5">
                                                    <img src="picture/7.jpg" width="170" height="5" />
                                                </td>
                                            </tr>
                                        </table>
                                    </div><!-- end menu-->
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr><!-- test -->
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="25" background="picture/4.jpg" id="imgmenu4" class="menu_title" onmouseover="this.className='menu_title2';" onclick="showsubmenu(4)" onmouseout="this.className='menu_title';" style="cursor:hand">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="18%">&nbsp;
                                                
                                            </td>
                                            <td width="82%" class="STYLE1">
                                                查询与统计
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td background="picture/5.png" id="submenu4">
                                    <div class="sec_menu">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td>
                                                    <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td width="16%" height="25">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td width="84%" height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="Statistics1.php" target="main">查询成绩</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td height="23">
                                                                <div align="center">
                                                                    <img src="picture/6.jpg" width="10" height="10" />
                                                                </div>
                                                            </td>
                                                            <td height="23">
                                                                <table width="95%" border="0" cellspacing="0" cellpadding="0">
                                                                    <tr>
                                                                        <td height="20" style="cursor:hand" onmouseover="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#7bc4d3'; "onmouseout="this.style.borderStyle='none'">
                                                                            <span class="STYLE3"><a href="Statistics2.php" target="main">统计</a></span>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                    </table>
                                                </td>
                                            </tr>
                                           <tr>
                                             <td height="5">
                                               <img src="picture/7.jpg" width="170" height="5" />
                                             </td>
                                           </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</td>
</tr>
</table>
<script>
    function showsubmenu(sid){
        whichEl = eval("submenu" + sid);
        imgmenu = eval("imgmenu" + sid);
        if (whichEl.style.display == "none") {
            eval("submenu" + sid + ".style.display=\"\";");
            imgmenu.background = "picture/6.jpg";
        }
        else {
            eval("submenu" + sid + ".style.display=\"none\";");
            imgmenu.background = "picture/6.jpg";
        }
    }
</script>
</div>
</div>
</body>
</html> 