<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<?php
$con = mysql_connect("localhost:3306", "root", "jie520");
mysql_select_db("mysearch");

$result = mysql_query("SELECT * FROM tb_search_content");
if (!$result) { // add this check.
	die('Invalid query: ' . mysql_error());
}
if ($row = mysql_fetch_array($result)) {
	do {
?>
<div class="gray-box">
<div class="gray-line">
<div class="gray-bg">
<div class="features">
<p> <?php


		print $row["id"];
?>:<?php


		print $row["content"];
?></p>
</div>
</div>
 </div>
 </div>
   
		<?php


	} while ($row = mysql_fetch_array($result));
} else {
	print "对不起，没有找到符合的纪录。";
}

mysql_close($con);
?>

</body>
</html>
