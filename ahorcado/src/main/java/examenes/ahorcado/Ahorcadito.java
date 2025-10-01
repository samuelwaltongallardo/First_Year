package examenes.ahorcado;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author samwe
 */
public class Ahorcadito {

    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);

        String[] conjunto = {"hola", "casa", "fleco", "bola", "pereza", "salvador"};

        int ElecccionAleatoria = (int) (Math.random() * (conjunto.length)), intentos = 0;//Con esto elegimos una palabra aleatoria de las predeterminadas

        String defecto = "-----------------------", palabra = conjunto[ElecccionAleatoria], adivina = "";//Aquí elegimos la palabra a adivinar y los demás Strings que necesitaremos

        char[] separado = defecto.toCharArray(), cambio = new char[palabra.length()];

        ArrayList<Character> FalloIntento = new ArrayList<Character>();

        adivina = adivina.copyValueOf(separado, 0, palabra.length());

        System.out.println("BIENVENIDO AL JUEGO DEL AHORCADO");

        while (intentos < 6) {

            System.out.println("Intenta adivinar la plabra:" + adivina + " Llevas " + intentos + " intentos de un maximo de 6");

            System.out.println("Ya has utilizado " + FalloIntento);

            char letra = leer.next().charAt(0);

            boolean fallo = true;

            for (int i = 0; i < palabra.length(); i++) {

                if (letra == palabra.charAt(i)) {

                    separado = palabra.toCharArray();

                    cambio = adivina.toCharArray();

                    cambio[i] = separado[i];

                    adivina = adivina.copyValueOf(cambio, 0, cambio.length);

                    fallo = false;

                }

            }

            if (fallo == true) {

                intentos++;

                System.out.println("FALLASTE!");

                FalloIntento.add(letra);

            }

            if (fallo == false) {

                System.out.println("ACERTASTE!");

            }

            if (palabra.compareTo(adivina) == 0) {

                intentos = 99;

            }

        }

        if (intentos == 99) {

            System.out.println("MENUDA MAQUINA! HAS COMPLETADO EL JUEGO!");

        } else {

            System.out.println("Lo siento mucho pero has fallado. PRUEBA OTRA VEZ!");

        }

    }

}
