<?php

require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $usuario = $_POST['usuario'];
    $password = $_POST['password'];

    $sql = "SELECT nombre, tipo FROM usuarios WHERE usuario = ? AND password = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ss", $usuario, $password);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo json_encode(["status" => "success", "mensaje" => "OK", "tipo" => $row["tipo"]]);
    } else {
        echo json_encode(["status" => "error", "mensaje" => "Usuario o contraseña incorrectos"]);
    }

    $stmt->close();
    $conn->close();
}
?>