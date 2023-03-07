<?php
class SelectFunction {
    public function SelectFunc(){
        // 連接資料庫
        $db_host = 'localhost';
        $db_name = 'bigrecord';
        $db_user = 'root';
        $db_passwd = '';
        $dsn = "mysql:host=$db_host;dbname=$db_name;charset=utf8";
        $conn = new PDO($dsn, $db_user, $db_passwd);

        // 接收POST資料，並串接SQL語法
        $Name = $_POST['Name'];        
        $sql = "SELECT * FROM `spamton` WHERE `姓名` = '".$Name."' ORDER BY `分數` desc limit 10";

        

        $stmt = $conn->prepare($sql);        
        $result = $stmt->execute();

        // 若成功，將資料轉成Json並以echo回傳
        
        if($result){
            $rows = $stmt->fetchall(PDO::FETCH_ASSOC);
            echo json_encode($rows);
        }else{
            echo "連接失敗";
        }       
        
    }
}
// 頁面被啟動後新建物件，執行函數
$obj = new SelectFunction();
$obj->SelectFunc();
?>