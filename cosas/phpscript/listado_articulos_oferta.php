<?php
require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

$sql = "SELECT nombre, categoria, descripcion, precio, stock FROM articulos WHERE oferta=1";
$result = $conn->query($sql);

$usuarios = array();

while ($row = $result->fetch_assoc()) {
    $usuarios[] = $row;
}

echo json_encode($usuarios);

$conn->close();
?>