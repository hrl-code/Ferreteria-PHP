<?php
require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

$sql = "SELECT nombre, categoria, descripcion FROM articulos WHERE destacado=1";
$result = $conn->query($sql);

$destacados = array();

while ($row = $result->fetch_assoc()) {
    $destacados[] = $row;
}

echo json_encode($destacados);

$conn->close();
?>