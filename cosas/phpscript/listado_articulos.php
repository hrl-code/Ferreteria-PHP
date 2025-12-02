<?php
require('conexion.php');
require ('control_acceso.php');


// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");



// Consultar la base de datos
$sql = "SELECT idArticulo, nombre, categoria, precio FROM articulos";
$result = $conn->query($sql);

$articulos = array();
while ($row = $result->fetch_assoc()) {
    $articulos[] = $row;
}

// Enviar respuesta en formato JSON
echo json_encode($articulos);

// Cerrar conexión
$conn->close();
?>