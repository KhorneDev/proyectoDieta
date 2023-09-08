<?php phpinfo(); ?>
<?php
require "conexion/conexion.php";

$response = array();

if (!empty($_POST['nombre']) && !empty($_POST['calorias']) && !empty($_POST['proteinas']) && !empty($_POST['grasas']) && !empty($_POST['carbohidratos'])) {
    $nombre = $_POST['nombre'];
    $calorias = $_POST['calorias'];
    $proteinas = $_POST['proteinas'];
    $grasas = $_POST['grasas'];
    $carbohidratos = $_POST['carbohidratos'];

    if ($con) {
        $sql = "INSERT INTO alimento (nombre, calorias, proteinas, grasas, carbohidratos) VALUES ('" . $nombre . "','" . $calorias . "','" . $proteinas . "', '" . $grasas . "', '" . $carbohidratos . "')";
        if (mysqli_query($con, $sql)) {
            $response['success'] = true;
            $response['message'] = "Alimento registrado";
        } else {
            $response['success'] = false;
            $response['message'] = "Registro fallido";
        }
    } else {
        $response['success'] = false;
        $response['message'] = "Conexión fallida";
    }
} else {
    $response['success'] = false;
    $response['message'] = "Todos los campos deben ser llenados";
}

echo json_encode($response);
?>
