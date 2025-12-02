<?php

require('conexion.php');
require('control:acceso.php');

// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // Obtener datos del cuerpo de la solicitud
    $nombre = $_POST['nombre'];
    $apellidos =$_POST['apellidos'];
    $edad = intval($_POST['edad']);
    $usuario = $_POST['usuario'];
    $password = $_POST['password'];
    $tipo = $_POST['tipo'];

    // Validar que no haya valores nulos
    if (!$nombre || !$apellidos || !$edad || !$usuario || !$password || !$tipo) {
        echo json_encode(["status" => "error", "message" => "Faltan datos obligatorios"]);
        exit;
    }

    $sql = "INSERT INTO usuarios (nombre, apellidos, edad, usuario, password, tipo) VALUES (?, ?, ?, ?, ?, ?)";
    
    if ($stmt = $conn->prepare($sql)) {
        try {
            $stmt->bind_param("ssisss", $nombre, $apellidos, $edad, $usuario, $password, $tipo);
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