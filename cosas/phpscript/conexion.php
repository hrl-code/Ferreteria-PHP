<?php
    $host = '145.14.151.51'; 
    $user = 'u812167471_android_app';
    $password = '2025-Android'; 
    $database = 'u812167471_android_app';

    $conn = new mysqli($host, $user, $password, $database);

    if ($conn->connect_error) {
        json_encode(["status" => "success", "message" => "Error al conectar"]);
    }
?>