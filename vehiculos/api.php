<?php
$host = "localhost";
$user = "vehiculos_user";
$password = "Vehiculos123!";
$database = "VehiculosDeportivos";

$connection = new mysqli($host, $user, $password, $database);

if ($connection->connect_error) {
    die("Error de conexión: " . $connection->connect_error);
}

if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_GET['action'])) {
    $action = $_GET['action'];
    if ($action === "insert") {
        $marca = $_POST['marca'];
        $modelo = $_POST['modelo'];
        $año = $_POST['año'];
        $precio = $_POST['precio'];
        $descripcion = $_POST['descripcion'];

        $query = "INSERT INTO Vehiculos (marca, modelo, año, precio, descripcion) VALUES ('$marca', '$modelo', '$año', '$precio', '$descripcion')";
        $result = $connection->query($query);

        echo json_encode(['success' => $result]);
    }
} elseif ($_SERVER['REQUEST_METHOD'] === 'GET' && isset($_GET['action'])) {
    if ($_GET['action'] === "getAll") {
        $query = "SELECT * FROM Vehiculos";
        $result = $connection->query($query);

        $vehicles = [];
        while ($row = $result->fetch_assoc()) {
            $vehicles[] = $row;
        }
        echo json_encode($vehicles);
    }
} else {
    echo json_encode(['error' => 'No se ha encontrado la acción']);
}

$connection->close();
