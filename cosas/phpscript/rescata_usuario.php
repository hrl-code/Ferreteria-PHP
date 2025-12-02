<?php

require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    
    $usuario=$_POST['usuario'];


    $sql = "SELECT nombre, apellidos, edad, usuario, password, tipo FROM usuarios WHERE usuario = ?";
    $stmt = $conn->prepare($sql);

    $stmt->bind_param("s", $usuario);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo json_encode(["nombre" => $row["nombre"], "apellidos" => $row["apellidos"], "edad" => $row["edad"], "usuario" => $row["usuario"], "password" => $row["password"], "tipo" => $row["tipo"]]);
    } else {
        echo json_encode(["status" => "error", "mensaje" => "Usuario no encontrado"]);
    }

    $stmt->close();
    $conn->close();
}
?>
