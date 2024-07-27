import Models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String line;
        String splitBy = ",";
        int id = 1;
        HashTable hashTableSHA256 = new HashTable();
        HashTable hashTableMD5 = new HashTable();

        try {
            BufferedReader br = new BufferedReader(new FileReader("bussines.csv"));
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                String key = business[0];
                String value = "Name=" + business[1] + ", Address=" + business[2] + ", City=" + business[3] + ", State=" + business[4];
                System.out.println("[" + id + "] Business [ID=" + key + ", Name=" + business[1] + ", Address=" + business[2] + ", City=" + business[3] + ", State=" + business[4] + "]");


                long startTimeSHA256 = System.nanoTime();
                hashTableSHA256.insert(key, value, true);
                long endTimeSHA256 = System.nanoTime();
                double durationSHA256 = (endTimeSHA256 - startTimeSHA256) / 1_000_000_000.0;
                System.out.println("El tiempo de inserción en SHA-256: " + durationSHA256 + " segundos");



                long startTimeMD5 = System.nanoTime();
                hashTableMD5.insert(key, value, false);
                long endTimeMD5 = System.nanoTime();
                double durationMD5 = (endTimeMD5 - startTimeMD5) / 1_000_000_000.0;
                System.out.println("El tiempo de inserción en MD5 es: " + durationMD5 + " segundos");

                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        int opción;
        do {
            try {
                System.out.println("\nMenú:");
                System.out.println("1. Buscar ID usando SHA-256");
                System.out.println("2. Buscar ID usando MD5");
                System.out.println("3. Salir");
                System.out.print("Ingrese una opción: ");
                opción = scanner.nextInt();

                switch (opción) {
                    case 1:
                        searchByID(hashTableSHA256, true);
                        break;
                    case 2:
                        searchByID(hashTableMD5, false);
                        break;
                    case 3:
                        System.out.println("Proceso finalizado.");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, intente nuevamente (1-3).");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Ingrese un número entero válido.");
                scanner.nextLine();
                opción = 0;
            }
        } while (opción != 3);

        scanner.close();
    }

    private static void searchByID(HashTable hashTable, boolean useSHA256) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el ID a buscar: ");
        String keyToSearch = scanner.nextLine();


        long startTime = System.nanoTime();
        String result = hashTable.get(keyToSearch, useSHA256);
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;

        if (result != null) {
            System.out.println("Datos del ID encontrados '" + keyToSearch + "': " + result);
            System.out.println("Tiempo de búsqueda: " + duration + " segundos");
        } else {
            System.out.println("No se encontraron datos para ese ID '" + keyToSearch + "'.");
        }
    }
}
