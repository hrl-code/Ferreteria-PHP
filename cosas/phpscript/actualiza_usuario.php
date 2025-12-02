<?php

require('conexion.php');
require('control_acceso.php');
// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");


// Verificar que la solicitud sea POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Validar si las variables están definidas
    if (!isset($_POST['usuario'], $_POST['nombre'], $_POST['apellidos'], $_POST['edad'], $_POST['password'])) {
        echo json_encode(["status" => "error", "message" => "Faltan datos obligatorios"]);
        exit;
    }

    // Obtener y validar los datos
    $usuario=$_POST['usuario'];
    $nombre = $_POST['nombre'];
    $apellidos = $_POST['apellidos'];
    $edad = intval($_POST['edad']);
    $password = $_POST['password '];


    // Validar conexión a la base de datos
    if (!$conn) {
        echo json_encode(["status" => "error", "message" => "Error de conexión a la base de datos"]);
        exit;
    }

    // Query de actualización
    $sql = "UPDATE usuarios SET nombre=?, apellidos=?, edad=?, password=? WHERE usuario=?";

    if ($stmt = $conn->prepare($sql)) {
        try {
            $stmt->bind_param("ssiss",$nombre, $apellidos, $edad, $password, $usuario);
            $stmt->execute();

            echo json_encode(["status" => "success", "message" => "Actualización realizada"]);
        } catch (Exception $e) {
           echo json_encode(["status" => "error", "message" => "Error al actualizar el artículo", "error" => $e->getMessage()]);
        }

        $stmt->close();
    } else {
        echo json_encode(["status" => "error", "message" => "Error al preparar la consulta"]);
    }

    $conn->close();
}

?>
