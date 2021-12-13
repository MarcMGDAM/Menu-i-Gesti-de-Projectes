package Projecte1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.ProviderException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class Projecte1_MenuDefinitiu {

    static Scanner teclat = new Scanner(System.in);

    static Connection connexioBD;

    static String nom_mevaempresa = "Nabitek";
    static String inf_empresaprov = "Tàrrega";

    static FileWriter fitxer = null;
    static BufferedWriter fitxerbuff = null;
    static PrintWriter escritor = null;

    static String[] nom_proveidor = new String[3];
    static int[] num_productes = new int[3];

    // throws SQLException
    static void connexioBD() {
        String servidor = "jdbc:mysql://192.168.1.120:3306/";
        String bbdd = "empresa";
        String user = "roger";
        String password = "roger";
        try {
            connexioBD = DriverManager.getConnection(servidor + bbdd, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException, IOException {

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

        String consulta = "SELECT * FROM Productes order by id";

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

        String inserció = "INSERT INTO Productes (nom, estoc, imatge, codi_categoria) value (?,?,?,?)";
        PreparedStatement sentencia = connexioBD.prepareStatement(inserció);

        sentencia.setString(1, nom);
        sentencia.setInt(2, estoc);
        sentencia.setString(3, imatge);
        sentencia.setInt(4, codi_categoria);

        sentencia.executeUpdate();

        String consulta_prod = "SELECT id FROM Productes where nom='" + nom + "'";
        PreparedStatement sentencia2 = connexioBD.prepareStatement(consulta_prod);

        ResultSet rs = sentencia2.executeQuery();

        int ID = 0;

        while (rs.next()) {
            ID = rs.getInt("id");
        }

        System.out.println("Codi del Material en que està format el Producte: ");
        System.out.println("1. Fusta ");
        System.out.println("2. Fibra ");
        System.out.println("3. Acer ");
        System.out.println("4. Alumini ");
        System.out.println("5. Vidre ");
        System.out.println("6. Tela ");
        System.out.println("7. Guata ");
        System.out.println("8. Plàstic ");

        int codi = teclat.nextInt();

        String inserció2 = "INSERT INTO Formats (id, codi) value (?,?)";
        PreparedStatement sentencia3 = connexioBD.prepareStatement(inserció2);

        sentencia3.setInt(1, ID);
        sentencia3.setInt(2, codi);

        sentencia3.executeUpdate();

    }

    static void ModificarProducte() throws SQLException {

        System.out.println("Escriu la ID del producte que vol modificar: ");
        int id = teclat.nextInt();
        teclat.nextLine();

        // Primer seleccionarem les dades de tots els productes
        String productes = " SELECT * FROM Productes WHERE id =" + id;
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
            String actualitzar = "UPDATE Productes SET nom = ?, estoc= ?, imatge= ?, codi_categoria= ? where id = "
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
        String eliminar = "DELETE from Productes where id=?; ";
        PreparedStatement sentencia = connexioBD.prepareStatement(eliminar);

        sentencia.setInt(1, id);

        sentencia.executeUpdate();

    }

    static void ConsultaProducte() throws SQLException {

        System.out.println("Consulta un producte ");

        int id = teclat.nextInt();

        // Comanda per consulta un sol producte amb els seus atrbiuts
        String consulta = "SELECT * from Productes where id=?; ";

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

    static void ActualitzarEstoc() throws IOException, SQLException {

        // Crear els directoris que no existeix a la ruta que escriuras
        File fitxer2 = new File("ENTRADES PENDENTS");
        fitxer2.mkdirs();
        File fitxer3 = new File("ENTRADES PROCESSADES");
        fitxer3.mkdirs();

        if (fitxer2.isDirectory()) {
            System.out.println("És un directori");

            // Lista dels fitxers que estan a dins de la carpeta
            File[] fitxers = fitxer2.listFiles();
            for (int i = 0; i < fitxers.length; i++) {
                System.out.println(fitxers[i].getName());
                ActualitzarStock(fitxers[i]);
                MoureFitxers(fitxers[i]);
            }

        }
    }

    static void ActualitzarStock(File fitxers) throws IOException, SQLException {

        // Llegeix caràcter a caràcter
        FileReader reader = new FileReader(fitxers);
        // Llegeix linia a linia, es més eficient
        BufferedReader buffer = new BufferedReader(reader);

        String linia;
        while ((linia = buffer.readLine()) != null) {
            System.out.println(linia);

            int posSep = linia.indexOf(":");
            int id = Integer.parseInt(linia.substring(0, posSep));
            int estoc = Integer.parseInt(linia.substring(posSep + 1));

            String actualitzar = "UPDATE Productes SET estoc=estoc + ? where id=?";
            PreparedStatement sentencia = connexioBD.prepareStatement(actualitzar);

            sentencia.setInt(1, estoc);
            sentencia.setInt(2, id);

            sentencia.executeUpdate();

        }

        buffer.close();
        reader.close();

    }

    static void MoureFitxers(File fitxers) throws IOException {
        FileSystem sistemaFitxers = FileSystems.getDefault();
        // La ruta de la carpeta de entrades pendents la passem a una variable
        Path origen = sistemaFitxers.getPath("ENTRADES PENDENTS/" + fitxers.getName());
        // La ruta de la carpeta de processades pendents la passem a una variable
        Path destí = sistemaFitxers.getPath("ENTRADES PROCESSADES/" + fitxers.getName());

        // Comanda per moure els fitxers on estan la id dels productes i el seu nou
        // estoc a la carpeta ENTRADES PROCESSADES
        Files.move(origen, destí, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("S'ha mogut ha processats el fitxer: " + fitxers.getName());
    }

    static void PrepararComandes() throws SQLException, IOException {

        System.out.println("Generació de comandes");

        String consulta = "SELECT Pr.id, Pr.nom, Pr.estoc, P.NIF, Prov.nom, Prov.telèfon, Prov.direcció from Productes Pr join Proveeix P join Proveïdors Prov on Pr.id = P.id and P.NIF = Prov.NIF where estoc <20 order by NIF";

        PreparedStatement ps = connexioBD.prepareStatement(consulta);

        ResultSet rs = ps.executeQuery();

        int i = 0;
        int in = 0;
        int productes = 0;

        // Mostra tots els productes de la BS amb els seus atributs corresponents
        if (rs.next()) {

            String actproveidor = rs.getString("Prov.nom");
            // Crear el fitxer, que de nom es el nom del proveïdor, la data de avui i sera
            // un fitxer .txt

            CrearFitxer(actproveidor, rs);

            nom_proveidor[i] = actproveidor;
            System.out.println("Array:" + nom_proveidor[i]);
            do {

                if (!actproveidor.equals(rs.getString("Prov.nom"))) {

                    System.out.println("Ha canviat de proveïdor");
                    // Em guardo el nou proveïdor
                    actproveidor = rs.getString("Prov.nom");
                    escritor.close();

                    i++;

                    nom_proveidor[i] = actproveidor;
                    System.out.println("Array: " + nom_proveidor[i]);

                    productes = 0;
                    in++;
                    CrearFitxer(actproveidor, rs);

                }

                System.out.print("ID PRODUCTE: " + rs.getInt("id") + " ");
                System.out.print("Estoc restant: " + (300 - rs.getInt("estoc")) + " ");
                System.out.println("NIF proveïdor:  " + rs.getString("NIF") + " ");

                productes++;
                num_productes[in] = productes;

                escritor.println("   " + rs.getInt("id") + "\t\t\t" + (300 - rs.getInt("estoc")));
            } while (rs.next());
            escritor.close();

        }
    }

    static void CrearFitxer(String actproveidor, ResultSet rs) throws SQLException, IOException {

        // Crear el fitxer, que de nom es el nom del proveïdor, la data de avui i sera
        // un fitxer .txt
        fitxer = new FileWriter("D:\\DAM 1r\\M3\\UF 1\\Exercicis programa classe\\COMANDES\\"
                + actproveidor + LocalDate.now() + ".txt", false);
        fitxerbuff = new BufferedWriter(fitxer);
        escritor = new PrintWriter(fitxerbuff);

        escritor.println("Nom empresa sol·licitant:  " + nom_mevaempresa);
        escritor.println(" Informació de la empresa sol·licitant:  " + inf_empresaprov);

        escritor.println("\n Nom empresa Proveïdora:  " + actproveidor);
        escritor.println("Telèfon de la empresa Proveïdora:  " + rs.getInt("telèfon"));
        escritor.println("Direcció de la empresa Proveïdora:  " + rs.getString("direcció"));

        escritor.println("\nID producte" + "       " + "Estoc sol·licitant");
    }

    static void AnalitzarComandes() {

        ProductesDemanats();
        MaxProductesDemanats();
        MinProductesDemanats();
        MitjanaProductesDemanats();

    }

    static void ProductesDemanats() {

        for (int i = 0; i < nom_proveidor.length; i++) {
            System.out.println(
                    "Hem sol·licitat " + num_productes[i] + " productes al proveïdor " + nom_proveidor[i]);
        }

    }

    static void MaxProductesDemanats() {

        int maxim = 0;
        int imaxim = 0;
        for (int i = 0; i < nom_proveidor.length; i++) {

            if (num_productes[i] > maxim) {

                maxim = num_productes[i];
                imaxim = i;
            }

        }
        System.out.println("\n" + "El proveïdor que més productes l'hem sol·licitat és:" + nom_proveidor[imaxim]
                + " amb " + maxim + " producte/s");
    }

    static void MinProductesDemanats() {
        int minim = num_productes[0];
        int iminim = 0;
        for (int i = 0; i < nom_proveidor.length; i++) {

            if (num_productes[i] < minim) {

                minim = num_productes[i];
                iminim = i;
            }
        }

        System.out.println("\n" + "El proveïdor que menys productes l'hem sol·licitat és:" + nom_proveidor[iminim]
                + " amb " + minim + " producte/s");
    }

    static void MitjanaProductesDemanats() {
        double mitjana = 0;
        double suma = 0;

        for (int i = 0; i < nom_proveidor.length; i++) {

            suma += num_productes[i];
        }
        mitjana = suma / num_productes.length;

        System.out.println("\n" + "La mitjana de productes sol·licitats és: " + mitjana);

    }
}
