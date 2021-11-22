package Projecte1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Projecte1_Menu {

    static Scanner teclat = new Scanner(System.in);

    static Connection connexioBD;

    // throws SQLException
    static void connexioBD() {
        String servidor = "jdbc:mysql://localhost:3306/";
        String bbdd = "empresa";
        String user = "root";
        String password = "root";
        try {
            connexioBD = DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {

        boolean sortir = false;
        connexioBD();

        do {
            System.out.println("*****MENU GESTOR DE LA BD****");
            System.out.println("1. Gestió dels productes");
            System.out.println("2. Actualitzar l'Estoc");
            System.out.println("3. Preparar les comandes");
            System.out.println("4. Analitzar les comandes");
            System.out.println(" TRIA UNA OPCIÓ");

            int opció = teclat.nextInt();
            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
            case 1:
                GestioProductes();
                break;
            case 2:
                ActualitzarEstoc();
                break;
            case 3:
                PrepararComandes();
                break;
            case 4:
                AnalitzarComandes();
                break;
            case 5:
                sortir = true;
                break;
            default:
                System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    static void GestioProductes() throws SQLException {

        boolean sortir = false;

        do {
            System.out.println("*****GESTIÓ DE PRODUCTES****");
            System.out.println("1. Consultat de tots els productes");
            System.out.println("2. Alta d'un producte");
            System.out.println("3. Modificació d'un producte");
            System.out.println("4. Esborra un producte");
            System.out.println("5. Consulta d'un producte");
            System.out.println(" TRIA UNA OPCIÓ");

            int opció = teclat.nextInt();
            teclat.nextLine();

            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
            case 1:
                LlistatTotsElsProductes();
                break;
            case 2:
                AltaProducte();
                break;
            case 3:
                ModificarProducte();
                break;
            case 4:
                EliminarProducte();
                break;
            case 5:
                ConsultaProducte();
            case 6:
                sortir = true;
                break;
            default:
                System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    static void LlistatTotsElsProductes() throws SQLException {

        System.out.println("LListat de tots els productes");

        String consulta = "SELECT * FROM productes order by id";

        PreparedStatement ps = connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        // Mostra tots els productes de la BS amb els seus atributs corresponents
        while (rs.next()) {
            System.out.print("ID PRODUCTE: " + rs.getInt("id") + " ");
            System.out.print("Nom: " + rs.getString("nom") + " ");
            System.out.println("Estoc: " + rs.getInt("estoc") + " ");
            System.out.println("Imatge: " + rs.getString("imatge") + " ");
            System.out.println("Codi_categoria: " + rs.getInt("codi_categoria"));
        }

    }

    static void AltaProducte() throws SQLException {

        System.out.println("ALTA AL PRODUCTE");

        // Escrius el nom del producte que vols ficar-li al nou producte
        System.out.print("Nom: ");
        String nom = teclat.nextLine();

        // Escrius el estoc del producte que vols ficar-li al nou producte
        System.out.print("Estoc: ");
        int estoc = teclat.nextInt();
        teclat.nextLine();

        // Escrius l'enllaç de la imatge del producte que vols ficar-li al nou producte
        System.out.print("Link de la Imatge: ");
        String imatge = teclat.nextLine();

        // Escrius el codi de la categoria que vol que estigui el nou producte
        System.out.print("Codi_categoria: ");
        int codi_categoria = teclat.nextInt();

        String inserció = "INSERT INTO productes (nom, estoc, imatge, codi_categoria) value (?,?,?,?)";
        PreparedStatement sentencia = connexioBD.prepareStatement(inserció);

        sentencia.setString(1, nom);
        sentencia.setInt(2, estoc);
        sentencia.setString(3, imatge);
        sentencia.setInt(4, codi_categoria);

        sentencia.executeUpdate();

    }

    static void ModificarProducte() throws SQLException {

        System.out.println("Escriu la ID del producte que vol modificar: ");
        int id = teclat.nextInt();
        teclat.nextLine();

        // Primer seleccionarem les dades de tots els productes
        String productes = " SELECT * FROM productes WHERE id =" + id;
        PreparedStatement dades = connexioBD.prepareStatement(productes);
        dades.executeQuery();

        ResultSet producte = dades.executeQuery();

        if (!producte.next()) {

        } else {
            // Agafem els atributs dels productes i les passarem a una variable per si no es
            // vol modificar aquell atribut que es quedi amb el que te
            String nom;
            int estoc;
            String imatge;
            int codi_categoria;

            // Posem els atributs dels productes per a que els hi puguem canviar de valor si
            // volem
            String actualitzar = "UPDATE productes SET nom = ?, estoc= ?, imatge= ?, codi_categoria= ? where id = "
                    + id;
            PreparedStatement sentencia = connexioBD.prepareStatement(actualitzar);

            System.out.println(
                    "Si vol modificar el nom del producte escriu 'si', però si no vol modificar-ho escriu 'no'");
            String resposta = teclat.nextLine();
            if (resposta.equals("si")) {
                System.out.println("Escriu el nom del producte que vol modificar: ");
                nom = teclat.nextLine();
                sentencia.setString(1, nom);
            }
            // Si no es vol canviar el valor del atribut es quedarà amb el que tenia
            // predeterminat, i això ho fan tots els else següents
            else {
                nom = producte.getString("nom");
                sentencia.setString(1, nom);
            }

            System.out.println(
                    "Si vol modificar el estoc del producte escriu 'si', però si no vol modificar-ho escriu 'no'");
            String resposta1 = teclat.nextLine();
            if (resposta1.equals("si")) {
                System.out.println("Escriu el estoc del producte que vol modificar: ");
                estoc = teclat.nextInt();
                sentencia.setInt(2, estoc);
                teclat.nextLine();
            } else {
                estoc = producte.getInt("estoc");
                sentencia.setInt(2, estoc);
            }

            System.out.println(
                    "Si vol modificar l'enllaç de la imatge del producte escriu 'si', però si no vol modificar-ho escriu 'no'");
            String resposta2 = teclat.nextLine();
            if (resposta2.equals("si")) {
                System.out.println("Escriu l'enllaç de la imatge del producte que vol modificar: ");
                imatge = teclat.nextLine();
                sentencia.setString(3, imatge);
            } else {
                imatge = producte.getString("imatge");
                sentencia.setString(3, imatge);
            }

            System.out.println(
                    "Si vol modificar el codi_categoria del producte escriu 'si', però si no vol modificar-ho escriu 'no'");
            String resposta3 = teclat.nextLine();
            if (resposta3.equals("si")) {
                System.out.println("Escriu el codi_categoria del producte que vol modificar: ");
                codi_categoria = teclat.nextInt();
                sentencia.setInt(4, codi_categoria);
                teclat.nextLine();
            } else {
                codi_categoria = producte.getInt("codi_categoria");
                sentencia.setInt(4, codi_categoria);
            }
            sentencia.executeUpdate();
        }
    }

    static void EliminarProducte() throws SQLException {

        System.out.println("Donar de baixa un producte escrivin el seu ID: ");

        int id = teclat.nextInt();

        // Comanda per eliminar un producte juntament amb els seus atributs
        String eliminar = "DELETE from productes where id=?; ";
        PreparedStatement sentencia = connexioBD.prepareStatement(eliminar);

        sentencia.setInt(1, id);

        sentencia.executeUpdate();

    }

    static void ConsultaProducte() throws SQLException {

        System.out.println("Consulta un producte ");

        int id = teclat.nextInt();

        // Comanda per consulta un sol producte amb els seus atrbiuts
        String consulta = "SELECT * from productes where id=?; ";

        PreparedStatement sentencia = connexioBD.prepareStatement(consulta);

        sentencia.setInt(1, id);

        ResultSet rs = sentencia.executeQuery();

        while (rs.next()) {
            System.out.print("ID PRODUCTE: " + rs.getInt("id") + " ");
            System.out.print("Nom: " + rs.getString("nom") + " ");
            System.out.println("Estoc: " + rs.getInt("estoc") + " ");
            System.out.println("Imatge: " + rs.getString("imatge") + " ");
            System.out.println("Codi_categoria: " + rs.getInt("codi_categoria"));
        }

    }

    static void ActualitzarEstoc() {

    }

    static void PrepararComandes() {

    }

    static void AnalitzarComandes() {

    }
}