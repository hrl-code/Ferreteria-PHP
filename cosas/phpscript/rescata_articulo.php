<?php

require('conexion.php');
require('control_acceso.php');

header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $idArt= intval($_POST['id']);


    $sql = "SELECT idArticulo, nombre, categoria, descripcion, precio, stock, origen FROM articulos WHERE idArticulo =". $idArt;
    $stmt = $conn->prepare($sql);
 
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        echo json_encode(["id" => $row["idArticulo"], "nombre" => $row["nombre"], "categoria" => $row["categoria"], "descripcion" => $row["descripcion"], "precio" => $row["precio"], "stock" => $row["stock"], "origen" => $row["origen"]]);
    } else {
        echo json_encode(["status" => "error", "mensaje" => "Es posible que el artículo no exista. Revise el listado para comprobarlo"]);
    }

    $stmt->close();
    $conn->close();
}
?>