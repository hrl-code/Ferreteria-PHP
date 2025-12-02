<?php

require('conexion.php');
require('control_acceso.php');
// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");


// Verificar que la solicitud sea POST
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Validar si las variables están definidas
    if (!isset($_POST['id'])) {
        echo json_encode(["status" => "error", "message" => "Faltan datos obligatorios"]);
        exit;
    }


    $idArticulo = intval($_POST['id']);
    
      // Validar conexión a la base de datos
    if (!$conn) {
        echo json_encode(["status" => "error", "message" => "Error de conexión a la base de datos"]);
        exit;
    }

    // consulta de eliminación
    $sql = "DELETE FROM articulos WHERE idArticulo=?";

    if ($stmt = $conn->prepare($sql)) {
        try {
            $stmt->bind_param("i", $idArticulo);
            $stmt->execute();

            echo json_encode(["status" => "success", "message" => "Artículo eliminado correctamente"]);
        } catch (Exception $e) {
            echo json_encode(["status" => "error", "message" => "Error al eliminar el artículo", "error" => $e->getMessage()]);
        }

        $stmt->close();
    } else {
        echo json_encode(["status" => "error", "message" => "Error al preparar la consulta"]);
    }

    $conn->close();
}

?>
