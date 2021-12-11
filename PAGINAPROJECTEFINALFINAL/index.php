<!DOCTYPE html>
<html lang="ca">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="CSS/catalegEstils.css">
    <title>Catàleg de productes</title>
</head>
<body>

<?php 
$categories = array();
unset($ordenar);
?>

    <div id="all">
    <header>
        <h1><a href="index.php">Catàleg de productes</a></h1>
    </header>

    <nav>
    <div class="dropdown">
    <span><a href="#">Ordenar</a></span>
    <div class="dropdown-content">
        <form action="ordenar_productes.php" method="POST">
            <p><input type="submit" name="alfabetic" value="Alfabèticament A-Z"></p>
            <p><input type="submit" name="asc" value="ID ascendent"></p>
            <p><input type="submit" name="desc" value="ID descendent"></p>
        </form>
        
    </div>
</div>
    </nav>

    <main>
        <div id="main-all">
        <div class="filtrar">
            <div class="categories"><br>
                <p><b><a href="index.php">Categories</a></b></p>

                <form action="productes_filtrats.php" method="POST">
                <?php

                $con = new mysqli('localhost', 'root', 'root', 'empresa');

                if ($con->connect_errno) {
                    die("Ha hagut un problema de connexio");
                } else {
                    $sql = "SELECT codi, nom FROM categories";
                    $result = $con->query($sql);

                    if ($result->num_rows > 0) {

                        while ($row = $result->fetch_assoc()) {
                            echo "<input type=submit name=categoria value=$row[nom]>";
                        }
                    }

                    $con->close();
                }
                ?>
                </form>
            </div>

            <div class="materials">
                <br>
                <p><b>Materials</b></p><br>
                <form method="POST" action="filtrar_material.php">
                <?php
                $con = new mysqli('localhost', 'root', 'root', 'empresa');

                if ($con->connect_errno) {
                    die("Ha hagut un problema de connexio");
                } else {

                $sql = "SELECT codi, nom, tipus FROM materials";
                $result = $con->query($sql);

                if ($result->num_rows > 0) {

                    while ($row = $result->fetch_assoc()) {
                        echo "<input type=submit name=material value=$row[nom]>";
                    }
                }
                $con->close();
                }
                ?>
                </form>
            </div>
        </div>

        <div id="main-content">
            <div id="grid-container">
            <?php
            $con = new mysqli('localhost', 'root', 'root', 'empresa');

            $sql = "SELECT id, nom, estoc, imatge FROM productes";
            $result = $con->query($sql);

            if ($result->num_rows > 0) {

                while ($row = $result->fetch_assoc()) {

                    echo "<div class=grid-text>
                        <div class=grid-item>
                        <img src=$row[imatge] alt=>
                        </div>
                        <p><b>$row[nom]</b></p>
                        <p><b>Estoc:</b> $row[estoc]</p> 
                        <p><b>ID: </b> $row[id]</p>            
                        </div>";
                }
            }
            $con->close();
            ?>
            </div>

        </div>
        </div>
    </main>

    <footer>

    </footer>
    </div>
</body>
</html>

