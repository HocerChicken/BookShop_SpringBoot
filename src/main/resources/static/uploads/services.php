<?php
$db = new mysqli("localhost","root","","tiki");
if ($db->connect_errno){
    echo "Failed to connect to MySQL: " . $db->connect_error;
    die();
}
$db->set_charset("utf8mb4");
header("Content-Type:application/json");
$res = $db->query("select * from account");
$account = [];
while($r = $res->fetch_assoc()){
    $account[] = ["email"=>$r["Email"],"hoten"=>$r["HoTen"],"gioitinh"=>$r["GioiTinh"],"ngaysinh"=>$r["NgaySinh"]];
}
$json_response = json_encode($account);
echo $json_response;
$db->close();
//các thao tác: thêm, xóa, sửa
?>