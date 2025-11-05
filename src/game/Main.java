package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import game.combat.QTECombat;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Bienvenido a Thal'Ryn: ¿deseas empezar la partida? (s/n)");
        String start = br.readLine();
        if (start == null || start.isEmpty() || !start.trim().equalsIgnoreCase("s")) {
            System.out.println("No se inicia la partida.");
            return;
        }

        System.out.println();
        System.out.println("--- Lore y Resumen del Mundo ---");
        System.out.println("Thal'Ryn");
        System.out.println("Un continente fracturado por una guerra entre dioses y mortales. El Guardián del Sello, Aurelion, busca recuperar reliquias para restaurar su poder.");
        System.out.println();
        System.out.println("--- Pisos disponibles ---");
        System.out.println("1. La Frontera del Silencio");
        System.out.println("   Boss: Thargron, el Martillo Silente");
        System.out.println("   Reliquia: Núcleo de Titanio Sagrado");
        System.out.println("2. El Velo de los Juramentos");
        System.out.println("   Boss: Selmira, la Voz Encadenada");
        System.out.println("   Reliquia: Cántico de la Llama Interior");
        System.out.println("3. El Núcleo del Abismo");
        System.out.println("   Boss: Zar'keth, el Portador de la Grieta");
        System.out.println("   Reliquia: Emblema del Eterno Retorno");
        System.out.println();
        System.out.println("La partida ha comenzado. (Demo: muestra de dungeon lista para integrar)");
        System.out.println("¿Deseas iniciar un combate de prueba? (s/n)");
        String combate = br.readLine();
        if (combate == null || combate.isEmpty() || !combate.trim().equalsIgnoreCase("s")) {
            System.out.println("No se inicia combate de prueba.");
            return;
        }

        // Correr demo
        QTECombat.runDemo(br);
    }
}