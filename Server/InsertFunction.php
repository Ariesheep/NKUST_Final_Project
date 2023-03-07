<?php
class InsertFunction {
    public function InsertFunc(){
        // 連接資料庫
        $db_host = 'localhost';
        $db_name = 'bigrecord';
        $db_user = 'root';
        $db_passwd = '';
        $dsn = "mysql:host=$db_host;dbname=$db_name;charset=utf8";
        $conn = new PDO($dsn, $db_user, $db_passwd);
        
        // 接收POST資料，並串接SQL語法
        $Datetime = $_POST['Datetime'];
        $Name = $_POST['Name'];
        $Score = $_POST['Score'];
        $sql = "INSERT INTO `spamton`(`時間`, `姓名`, `分數`) VALUES ('".$Datetime."','".$Name."','".$Score."')";

        // 執行SQL新增
        $stmt = $conn->prepare($sql);
        $result = $stmt->execute();

        echo "新增成功"; // 回傳結果
    }
}
// 頁面被啟動後新建物件，執行函數
$obj = new InsertFunction();
$obj->InsertFunc();
?>