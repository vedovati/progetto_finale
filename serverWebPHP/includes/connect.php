<?php
class Connect
{
    private $con;
    function __construct()
    { }
    function connect($table,$host ="localhost",$user="root",$password="")
    {
        $this->con = new mysqli($host,$user, $password, $table);

        if (mysqli_connect_errno()) {
            echo "Failed to connect to MySQL: " . mysqli_connect_error();
        }
        return $this->con;
    }
}
