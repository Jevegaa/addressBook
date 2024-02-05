package EvaluableS4;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class addressBook {
    private Map<String, String> contactos = new HashMap<>();
    // Ruta al archivo CSV que almacena los contactos
    private final String filePath = "C:\\Users\\jesus\\eclipse-workspace\\CEJT4\\src\\EvaluableS4\\contacts.csv";

    // Carga los contactos desde el archivo CSV al HashMap
    public void load() {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) { // Verifica si el archivo existe y no está vacío
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    contactos.put(parts[0], parts[1]);
                }
                System.out.println("Acceso a file contacts concedido.");
            } catch (IOException e) {
                System.out.println("Error al cargar los contactos: " + e.getMessage());
            }
        } else if (file.exists()) {
            System.out.println("El archivo de contactos está vacío.");
        } else {
            System.out.println("El archivo de contactos no existe. Se iniciará con una agenda vacía.");
        }
    }


    // Guarda los contactos del HashMap al archivo CSV
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> contacto : contactos.entrySet()) {
                bw.write(contacto.getKey() + "," + contacto.getValue());
                bw.newLine();
            }
            System.out.println("Contactos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    // Muestra todos los contactos almacenados
    public void list() {
        System.out.println("Contactos:");
        contactos.forEach((numero, nombre) -> System.out.println(numero + " : " + nombre));
    }

    // Crea un nuevo contacto y lo guarda en el archivo CSV
    public void create(String nombre, String numero) {
        if (!contactos.containsKey(numero)) { // Verifica si el número ya existe
            contactos.put(numero, nombre);
            save();
            System.out.println("Se agregó correctamente el contacto \"" + nombre + "\" con el número \"" + numero + "\".");
        } else {
            System.out.println("El número \"" + numero + "\" ya está asociado con un contacto.");
        }
    }

    // Elimina un contacto por su número y actualiza el archivo CSV
    public void delete(String numero) {
        if (contactos.containsKey(numero)) {
            String nombre = contactos.remove(numero);
            save();
            System.out.println("Se eliminó el número \"" + numero + "\" asociado a \"" + nombre + "\" con éxito.");
        } else {
            System.out.println("El contacto con el número \"" + numero + "\" no existe.");
        }
    }

    // Método principal que ejecuta el programa y muestra un menú interactivo
    public static void main(String[] args) {
        addressBook addressBook = new addressBook();
        addressBook.load(); // Carga los contactos existentes
        Scanner scanner = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\n1. Listar contactos\n2. Crear contacto\n3. Eliminar contacto\n4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    addressBook.list();
                    break;
                case "2":
                    System.out.print("Ingresa el nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingresa el número: ");
                    String numero = scanner.nextLine();
                    addressBook.create(nombre, numero);
                    break;
                case "3":
                    System.out.print("Ingresa el número a eliminar: ");
                    String numeroAEliminar = scanner.nextLine();
                    addressBook.delete(numeroAEliminar);
                    break;
                case "4":
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!opcion.equals("4"));

        scanner.close();
    }
}