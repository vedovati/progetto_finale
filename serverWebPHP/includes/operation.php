<?php
class Operation
{
    private $con;
    public $result;
    private $database ="my_login14139";

    function __construct()
    {
        require_once 'connect.php';
        $db = new Connect();
        $this->con = $db->connect($this->database);
    }
    function query($sql)
    {
        if ($this->result = $this->con->query($sql))
            return true;
        return $this->con->errno;
    }
}
