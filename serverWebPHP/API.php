<?php
error_reporting(E_ERROR | E_PARSE);
require_once 'includes/operation.php';
$errorList = array(
    //"num"=>"Message error"
    "getMedicinali" => array(
        0 => "Passowrd o nome utente errati",
    ),
    "insert" => array(
        1062 => "Chiave primaria già presente",
    ),
    "remove" => array(),
    "query" => array()
);

$response = array();

if (isset($_POST['funz'])) {
    $apicall = $_POST['funz'];
    switch ($apicall) {

        case 'getMedicinali':
            //Controlliamo se sono stati passati i parametri corretti
            $db = new Operation();

            $result = $db->query("select id,nome from Medicinale");
            if ($result == 1) {
                $response['error'] = false;
                $response['message'] = 'query effettuata con successo';
                $response["dati"] = array();
                while ($rows = $db->result->fetch_assoc()) {
                    array_push($response["dati"], $rows);
                }
            } else {
                //impostiamo errore e messaggio di errore 
                $response['error'] = true;
                $response['message'] = array_key_exists($result, $errorList["$apicall"]) ? $errorList["$apicall"][$result] : "Si è verificato errore. n:$result";
            }
            break;

        case 'getMedicinaleSpecifico':
            //Controlliamo se sono stati passati i parametri corretti
            $db = new Operation();

            $result = $db->query("select * from Medicinale where nome ='" . $_POST["nome"]."'");
            if ($result == 1) {
                $response['error'] = false;
                $response['message'] = 'query effettuata con successo';
                $response["dati"] = array();
                while ($rows = $db->result->fetch_assoc()) {
                    array_push($response["dati"], $rows);
                }
            } else {
                //impostiamo errore e messaggio di errore 
                $response['error'] = true;
                $response['message'] = array_key_exists($result, $errorList["$apicall"]) ? $errorList["$apicall"][$result] : "Si è verificato errore. n:$result";
            }
            break;

        default:
            $response['error'] = true;
            $response['message'] = 'Questa chiamata API non viene gestita';
    }
} else {
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

echo json_encode($response);
