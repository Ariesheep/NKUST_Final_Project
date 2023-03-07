<!DOCTYPE html>
<html>
<head>
<title>Worst Game Ever Ranking</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/styles.css">

</head>

<body>

<main>


<h1 class="">TOP 20</h1>
<p id="clock">0000年00月00日， 00時00分00秒</p>
<div class="container">
        <table class="">
        <tr>    
                <th>排名</th>
                <th>姓名</th>
                <th>時間</th>
                <th>分數</th>                
        </tr>
        <?php           
                $conn = mysqli_connect("localhost","root","","bigrecord");
                $sqlcode = "SELECT * FROM `spamton` ORDER BY `分數` DESC LIMIT 20";
                $result = $conn->query($sqlcode);

                


                $i=1; 
                if($result -> num_rows >0){     
                        while($row = $result-> fetch_assoc()){
                                // $R = rand(0,255);
                                // $G = rand(0,255); 
                                // $B = rand(0,255); 
                                // $A = rand(0,100);
                                
                                // echo"<tr style='background-color:rgba(".$R.",".$G.",".$B.",".$A.")'><td>". 
                                // $row["姓名"]."</td><td>" . $row["時間"]."</td><td>". $row["分數"]."</td></tr>";
                                
                                echo"<tr><td>".$i."</td><td>".$row["姓名"]."</td><td>" . $row["時間"]."</td><td>". $row["分數"]."</td></tr>"; 
                                $i++;
                        }
                }
        ?>
        </table>
</div>


</main>



<script> 
let nowtime ;
function getnowtime(){
        let time = new Date();       

        nowtime = `${time.getFullYear()}年${time.getMonth()+1}月${time.getDate()}日，${time.getHours()}時${time.getMinutes()}分${time.getSeconds()}秒`

        document.getElementById("clock").innerHTML = nowtime;
}
setInterval(getnowtime,1000);//每秒更新
</script> 

</body>
</html>
