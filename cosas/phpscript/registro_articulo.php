<?php

require('conexion.php');
require('control_acceso.php');

// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Obtener datos del cuerpo de la solicitud
    $nombre = $_POST['nombre'];
    $categoria =$_POST['categoria'];
    $descripcion = $_POST['descripcion'];
    $precio= doubleval($_POST['precio']);
    $stock = intval($_POST['stock']);
    $origen = $_POST['origen'];
    $destacado = intval($_POST['destacado']);
    $oferta = intval($_POST['oferta']);

    // Validar que no haya valores nulos
    if (!$nombre || !$categoria || !$descripcion || !$precio || !$stock || !$origen || !$destacado || !$oferta) {
        echo json_encode(["status" => "error", "message" => "Faltan datos obligatorios"]);
        exit;
    }

    $sql = "INSERT INTO articulos (nombre, categoria, descripcion, precio, stock, origen, destacado, oferta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    if ($stmt = $conn->prepare($sql)) {
        try {
            $stmt->bind_param("sssdisii", $nombre, $categoria, $descripcion, $precio, $stock, $origen, $destacado, $oferta);
            $stmt->execute();
            
            echo json_encode(["status" => "success", "message" => "Registro exitoso"]);
        } catch (Exception $e) {
            echo json_encode(["status" => "error", "message" => "Error al registrar", "error" => $e->getMessage()]);
        }

        $stmt->close();
    } else {
        echo json_encode(["status" => "error", "message" => "Error al preparar la consulta"]);
    }

    $conn->close();
}

?>