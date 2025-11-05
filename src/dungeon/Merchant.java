package dungeon;

import java.io.BufferedReader;
import java.io.IOException;
import entities.Player;


public class Merchant {
    public void open(BufferedReader br, Player player) throws IOException {
        System.out.println();
        System.out.println("Entras al Mercado del Silencio. Un mercader te observa desde su puesto.");
        while (true) {
            System.out.println("Mercader: 'Tengo objetos a cambio de Almas.' Tu tienes: " + player.getSouls() + " almas.");
            System.out.println("1) Poción de vida (restaura 50% HP) - 2 almas");
            System.out.println("2) Poción de daño (+10 ataque por 1 combate) - 3 almas");
            System.out.println("3) Segunda oportunidad (revive una vez en batalla) - 4 almas");
            System.out.println("0) Salir del mercado");
            System.out.print("> ");
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.equals("0")) break;
            if (line.equals("1")) {
                if (player.spendSouls(2)) {
                    player.addHealthPotions(1);
                    System.out.println("Has comprado una poción de vida. Pociones de vida totales: " + player.getHealthPotions());
                } else System.out.println("No tienes suficientes almas.");
            } else if (line.equals("2")) {
                if (player.spendSouls(3)) {
                    player.addDamagePotions(1);
                    System.out.println("Has comprado una poción de daño. Pociones de daño totales: " + player.getDamagePotions());
                } else System.out.println("No tienes suficientes almas.");
            } else if (line.equals("3")) {
                if (player.spendSouls(4)) {
                    player.grantSecondChance();
                    System.out.println("Has obtenido una segunda oportunidad: se consumirá si mueres en combate.");
                } else System.out.println("No tienes suficientes almas.");
            } else {
                System.out.println("Opción inválida.");
            }
        }
    }
}
