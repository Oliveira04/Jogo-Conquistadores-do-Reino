package kingdom.utils;

import java.util.Scanner;
import java.io.IOException;

public class Type {

    public static void main(String[] args) {
        // String message = "Olá, mundo!";
        // slowPrint(message, 200); // Imprime cada caractere a cada 200ms
    }

    /**
     * Imprime um texto caracter por caracter com um delay.
     *
     * @param message     Mensagem a ser impressa.
     * @param millisDelay Delay em milissegundos entre cada caractere.
     */
    public static void slowPrint(String message, long millisDelay) {
        for (char ch : message.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(millisDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Boa prática para lidar com a interrupção de threads.
                System.out.println("A thread foi interrompida.");
                break;
            }
        }
    }

    public static String ask(String message, String[] options) {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;
        String v = "";

        while (!success) {
            System.out.print(message);
            v = scanner.nextLine();

            boolean encontrado = false;

            for (String option : options) {
                if (option.equals(v)) {
                    success = true;
                    break;
                }
            }

            if (!success) {
                System.out.print("Opção inválida!\n");
            }
        }

        return v;
    }
}
