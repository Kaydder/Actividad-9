import java.util.Scanner;
import java.util.regex.*;

class ValidadorContrasena implements Runnable {
    private final String contrasena;

    public ValidadorContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public void run() {
        System.out.println("Validando: " + contrasena);
        if (validarContrasena(contrasena)) {
            System.out.println("\u2714 Contraseña válida: " + contrasena);
        } else {
            System.out.println("\u2718 Contraseña inválida: " + contrasena);
        }
    }

    private boolean validarContrasena(String contrasena) {
        if (contrasena.length() < 8) return false;

        Pattern mayusculas = Pattern.compile("[A-Z]");
        Pattern minusculas = Pattern.compile("[a-z]");
        Pattern numeros = Pattern.compile("[0-9]");
        Pattern especiales = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]");


        Matcher m1 = mayusculas.matcher(contrasena);
        Matcher m2 = minusculas.matcher(contrasena);
        Matcher m3 = numeros.matcher(contrasena);
        Matcher m4 = especiales.matcher(contrasena);

        int countMayus = 0;
        while (m1.find()) countMayus++;

        int countMinus = 0;
        while (m2.find()) countMinus++;

        boolean tieneNumero = m3.find();
        boolean tieneEspecial = m4.find();

        return countMayus >= 2 && countMinus >= 3 && tieneNumero && tieneEspecial;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de contraseñas a validar: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < cantidad; i++) {
            System.out.print("Contraseña " + (i + 1) + ": ");
            String contrasena = scanner.nextLine();
            Thread hilo = new Thread(new ValidadorContrasena(contrasena));
            hilo.start();
        }

        scanner.close();
    }
}
