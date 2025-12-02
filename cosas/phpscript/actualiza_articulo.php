<?php

require('conexion.php');
require('control_acceso.php');
// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");


// Verificar que la solicitud sea POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Validar si las variables están definidas
    if (!isset($_POST['id'], $_POST['precio'], $_POST['stock'])) {
        echo json_encode(["status" => "error", "message" => "Faltan datos obligatorios"]);
        exit;
    }

    // Obtener y validar los datos
    $idArticulo = intval($_POST['id']);
    $precio = floatval($_POST['precio']);
    $stock = intval($_POST['stock']);

    // Validar que los valores sean correctos
    if ($idArticulo <= 0 || $precio < 0 || $stock < 0) {
        echo json_encode(["status" => "error", "message" => "Datos inválidos"]);
        exit;
    }

    // Validar conexión a la base de datos
    if (!$conn) {
        echo json_encode(["status" => "error", "message" => "Error de conexión a la base de datos"]);
        exit;
    }

    // Query de actualización
    $sql = "UPDATE articulos SET precio=?, stock=? WHERE idArticulo=?";

    if ($stmt = $conn->prepare($sql)) {
        try {
            $stmt->bind_param("dii", $precio, $stock, $idArticulo);
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
