import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LligaEsportiva {

    static Scanner teclat = new Scanner(System.in);

    static String[] nom_equip = new String[10];
    static int puntuació[][] = new int[10][5];

    static int contador = 0;

    public static void main(String[] args) throws NumberFormatException, IOException {
        LlegirFitxer();
        boolean sortir = false;

        do {
            // Creem el menú sobre la lliga esportiva
            System.out.println("*****MENU LLIGA ESPORTIVA*****");
            System.out.println("1. Visualització dels equips amb la seva puntuació");
            System.out.println("2. Afegir un nou equip a la lliga");
            System.out.println("3. Modificar dades de un equip");
            System.out.println("4. Visualitzar les dades del lider i del cuer de la lliga");
            System.out.println("5. Sortir de l'aplicació");
            System.out.println(" TRIA UNA OPCIÓ");

            int opció = teclat.nextInt();
            // Et demana posar un valor a la terminal per executar X comanda
            switch (opció) {
                case 1:
                    VisualitzacióDelsEquips();
                    break;
                case 2:
                    AfegirEquip();
                    break;
                case 3:
                    ModificarEquip();
                    break;
                case 4:
                    ConsultaLiderCuer();
                    break;
                case 5:
                    ActualitzarFitxer();
                    sortir = true;
                    break;
                default:
                    System.out.println("L'opció " + opció + " no existeix.");
            }
        } while (!sortir);

    }

    static void LlegirFitxer() throws IOException {

        // Crearem les variables per llegir el fitxer, que això s'executara al obrir el
        // programa
        FileReader lector = new FileReader("E:\\DAM 1r\\M3\\UF 1\\M3ExerciciNadal\\Fitxer\\Equips.txt");
        BufferedReader br = new BufferedReader(lector);

        // fem un bucle per a passar el contingut de l'arxiu a els arrays nomequips i
        // les seves puntuació
        String linia;
        int n = 0;
        while ((linia = br.readLine()) != null) {
            String[] values = linia.split(":");
            nom_equip[n] = values[0];
            System.out.print(nom_equip[n] + ": ");
            int i = 1;

            for (int p = 0; i < values.length; p++) {
                puntuació[n][p] = Integer.parseInt(values[i]);
                i++;
                System.out.print(puntuació[n][p] + " ");
            }
            n++;
            contador++;

            System.out.println("\n");
        }
        br.close();
        CalcularPunts();

    }

    public static int Contador(int contador) {
        // el contador conté el numero de equips que hi han al array
        return contador = contador++;
    }

    static void CalcularPunts() {
        int suma = 0;
        int guanyats = 0;
        int empatats = 0;
        // Bucle per a calcular les puntuacions dels equips
        for (int e = 0; nom_equip[e] != null; e++) {
            for (int p = 0; p < puntuació[e].length; p++) {

                if (p == 1) {
                    int n = 3;
                    guanyats = n * puntuació[e][p];

                }
                if (p == 2) {
                    int n = 1;
                    empatats = n * puntuació[e][p];
                }

                suma = guanyats + empatats;
            }
            puntuació[e][4] = suma;
        }

    }

    static void ActualitzarFitxer() throws IOException {
        // Apartat que es fara al tanca el programa que actualitzara les dades
        // modificades dels arrays al fitxer
        FileWriter fw = new FileWriter("E:\\DAM 1r\\M3\\UF 1\\M3ExerciciNadal\\Fitxer\\Equips.txt", false);
        PrintWriter pw = new PrintWriter(fw);

        // Passa les dades dels arrays al fitxer equips.txt
        for (int e = 0; nom_equip[e] != null; e++) {
            pw.print(nom_equip[e] + ":");
            for (int p = 0; p < puntuació[e].length; p++) {
                pw.print(puntuació[e][p] + ":");
            }

            pw.println();
        }
        pw.close();
    }

    static void VisualitzacióDelsEquips() {

        // Menu per a que es pugui visualitzar els noms dels equips amb les seves
        // puntuacions
        System.out.println(
                "N.Equips" + "\t " + "P.Jugats" + "\t " + "P.Guanyats" + "\t " + "P.Perduts" + "\t "
                        + "P.Empatats" + "\t " + "Puntuació_Total");

        System.out.println(
                "________________________________________________________________________________________________");
        for (int e = 0; nom_equip[e] != null; e++) {
            System.out.print(nom_equip[e] + ": ");

            for (int p = 0; p < puntuació[e].length; p++) {

                System.out.print(" \t " + puntuació[e][p] + " \t");
                if (p == 4) {
                    System.out.println("punts");
                }

            }
            System.out.println();
        }

    }

    static void AfegirEquip() throws IOException {

        for (int e = 0; e < nom_equip.length; e++) {

            if (e == contador) {

                System.out.print("Introdueix el nom de l'equip que vol afegir:");
                teclat.nextLine();
                String NomEquip = teclat.nextLine();
                nom_equip[e] = NomEquip;

                for (int p = 0; p <= 3; p++) {

                    if (p == 0) {
                        System.out.println("Introdueix els partits jugats: ");
                    }
                    if (p == 1) {
                        System.out.println("Introdueix els partits guanyats: ");
                    }
                    if (p == 2) {
                        System.out.println("Introdueix els partits empatats: ");
                    }
                    if (p == 3) {
                        System.out.println("Introdueix els partits perduts: ");
                    }
                    int NovaPuntuacio = teclat.nextInt();
                    puntuació[e][p] = NovaPuntuacio;
                }
            }
        }
        CalcularPunts();

    }

    static void ModificarEquip() {

        VisualitzacióDelsEquips();

        int e = 0;
        int njugats = 0;
        int nguanyats = 0;
        int nempatats = 0;
        int nperduts = 0;
        String nequip;
        String nouequip;

        System.out.println("Vol modificar algun nom d'algun equip actual?");
        teclat.nextLine();
        String resposta = teclat.nextLine();
        if (resposta.equals("si")) {
            System.out.println("Escriu el nom del equip que vol modificar: ");
            nequip = teclat.nextLine();
            for (; e < contador; e++) {
                if (nom_equip[e].equals(nequip)) {
                    System.out.println("Escriu el nou nom de l'equip ?");
                    nouequip = teclat.nextLine();
                    nom_equip[e] = nouequip;
                }
            }
            e--;
            System.out.println("Vol canviar alguna dada de la seva puntuació ? ");
            String resposta1 = teclat.nextLine();
            if (resposta1.equals("si")) {

                for (int p = 0; p < contador; p++) {

                    if (p == 0) {
                        System.out.println("Vol canviar els partits totals jugats ?");
                        String jugats = teclat.nextLine();
                        if (jugats.equals("si")) {
                            System.out.println("Escriu el nou nombre de partits totals jugats");
                            njugats = teclat.nextInt();
                            teclat.nextLine();
                            puntuació[e][p] = njugats;

                        }
                    }
                    if (p == 1) {
                        System.out.println("Vol canviar els partits guanyats ?");
                        String guanyats = teclat.nextLine();
                        if (guanyats.equals("si")) {
                            System.out.println("Escriu el nou nombre de partits totals guanyats");
                            nguanyats = teclat.nextInt();
                            teclat.nextLine();
                            puntuació[e][p] = nguanyats;

                        }
                    }
                    if (p == 2) {
                        System.out.println("Vol canviar els partits totals empatats ?");
                        String empatats = teclat.nextLine();
                        if (empatats.equals("si")) {
                            System.out.println("Escriu el nou nombre de partits totals jugats");
                            nempatats = teclat.nextInt();
                            teclat.nextLine();
                            puntuació[e][p] = nempatats;

                        }
                    }
                    if (p == 3) {
                        System.out.println("Vol canviar els partits totals perduts ?");
                        String perduts = teclat.nextLine();
                        if (perduts.equals("si")) {
                            System.out.println("Escriu el nou nombre de partits totals jugats");
                            nperduts = teclat.nextInt();
                            teclat.nextLine();
                            puntuació[e][p] = nperduts;

                        }
                    }
                }
            } else {
            }
        }

        // Si no es vol canviar el valor del atribut es quedarà amb el que tenia
        // predeterminat, i això ho fan tots els else següents
        else {
            System.out.println("Vol canviar alguna dada de la puntuació d'algun equip ? ");
            String resposta2 = teclat.nextLine();
            if (resposta2.equals("si")) {
                System.out.println("Escriu el nom del equip que vol modificar la seva puntuació: ");
                nequip = teclat.nextLine();
                for (; e < contador; e++) {
                    if (nom_equip[e].equals(nequip)) {
                        nom_equip[e] = nequip;
                    }
                }
            }
            e--;
            for (int p = 0; p < contador; p++) {

                if (p == 0) {
                    System.out.println("Vol canviar els partits totals jugats ?");
                    String jugats = teclat.nextLine();
                    if (jugats.equals("si")) {
                        System.out.println("Escriu el nou nombre de partits totals jugats");
                        njugats = teclat.nextInt();
                        teclat.nextLine();
                        puntuació[e][p] = njugats;

                    }
                }
                if (p == 1) {
                    System.out.println("Vol canviar els partits guanyats ?");
                    String guanyats = teclat.nextLine();
                    if (guanyats.equals("si")) {
                        System.out.println("Escriu el nou nombre de partits totals guanyats");
                        nguanyats = teclat.nextInt();
                        teclat.nextLine();
                        puntuació[e][p] = nguanyats;

                    }
                }
                if (p == 2) {
                    System.out.println("Vol canviar els partits totals empatats ?");
                    String empatats = teclat.nextLine();
                    if (empatats.equals("si")) {
                        System.out.println("Escriu el nou nombre de partits totals jugats");
                        nempatats = teclat.nextInt();
                        teclat.nextLine();
                        puntuació[e][p] = nempatats;

                    }
                }
                if (p == 3) {
                    System.out.println("Vol canviar els partits totals perduts ?");
                    String perduts = teclat.nextLine();
                    if (perduts.equals("si")) {
                        System.out.println("Escriu el nou nombre de partits totals jugats");
                        nperduts = teclat.nextInt();
                        teclat.nextLine();
                        puntuació[e][p] = nperduts;

                    }
                }
            }
        }
    }

    static void ConsultaLiderCuer() {

        MaxPuntuació();
        MinPuntuació();
    }

    static void MaxPuntuació() {
        int maxim = 0;
        int imaxim = 0;
        int e = 0;
        String equip = "";

        while (nom_equip[e] != null) {

            maxim = puntuació[e][4];
            if (imaxim < maxim) {
                imaxim = maxim;
                equip = nom_equip[e];

            }
            e++;

        }
        System.out.println(" L'equip amb la puntuació més alta és el equip: " + equip + " amb la puntuació de " + imaxim
                + " punts.");
    }

    static void MinPuntuació() {
        int iminim = 100;
        int e = 0;
        String equip = "";

        while (nom_equip[e] != null) {

            int minim = puntuació[e][4];
            if (iminim > minim) {
                iminim = minim;
                equip = nom_equip[e];

            }
            e++;

        }
        System.out.println(" L'equip amb la puntuació més alta és el equip: " + equip + " amb la puntuació de " + iminim
                + " punts.");

    }

}