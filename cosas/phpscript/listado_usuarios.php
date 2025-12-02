<?php
require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

$sql = "SELECT nombre, apellidos, usuario, tipo FROM usuarios";
$result = $conn->query($sql);

$usuarios = array();

while ($row = $result->fetch_assoc()) {
    $usuarios[] = $row;
}

echo json_encode($usuarios);

$conn->close();
?>