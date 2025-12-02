<?php
// Configurar encabezado para respuesta JSON
header("Content-Type: application/json");

// Bloquear accesos desde navegadores verificando el User-Agent de Android
if (!isset($_SERVER['HTTP_USER_AGENT']) || strpos($_SERVER['HTTP_USER_AGENT'], 'Dalvik') === false) {
    echo json_encode(["status" => "error", "message" => "Acceso no autorizado"]);
    exit;
}
?>
