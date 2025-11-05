package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import game.combat.QTECombat;
import dungeon.Dungeon;
import entities.Player;

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

        System.out.println("La partida ha comenzado. Preparando escena de inicio y primer piso...");

       
        Player player = new Player();
        boolean bossHere = Dungeon.playPrimerPiso(br, player);

        if (bossHere) {
            System.out.println();
            System.out.println("Frente a ti se yergue Thargron, el Martillo Silente.");
            System.out.println("¿Deseas iniciar el combate contra Thargron? (s/n)");
            String fight = br.readLine();
            if (fight != null && !fight.isEmpty() && fight.trim().equalsIgnoreCase("s")) {
                    enemies.Thargron boss = new enemies.Thargron("Thargron, el Martillo Silente", 1, 60, 12, 3, 1500L, 0);
                    boolean win = QTECombat.run(br, player, boss);
                player.onCombatEnd();
                if (win) {
                    System.out.println("Has vencido a Thargron y recuperado el Núcleo de Titanio Sagrado!");
                } else {
                    System.out.println("Has caído ante Thargron. La Frontera del Silencio reclama otra víctima.");
                }
            } else {
                System.out.println("Te retiras por ahora. La Frontera sigue en silencio...");
            }
        } else {
            System.out.println("Has explorado las salas del primer piso. No hay jefe presente.");
        }
    }
}