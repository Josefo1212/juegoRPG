package game.combat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import entities.Player;
import enemies.Enemy;
import enemies.Boss;
import entities.Relic;

public class QTECombat {
    public static boolean run(BufferedReader br, Player player, Enemy enemy) throws IOException {
        System.out.println();
        System.out.println("--- QTE Parry/Dodge ---");
        System.out.println("Encuentro: " + enemy.getName());

        // Impacto inicial con mensaje (sin desglose)
        int impactoInicial = Math.max(5 - player.getMaxHp()/100, 0);
        player.takeDamage(impactoInicial);
        System.out.println("Ataque inicial: el jugador recibe " + impactoInicial + " de daño. Vida: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println();

        Random rnd = new Random();
        int round = 1;

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println();
            System.out.println("--- Ronda " + round + " ---");
            System.out.println("El enemigo parece prepararse...");

            int prepMs = 2000 + rnd.nextInt(8001);
            try { Thread.sleep(prepMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

            char parryKey = (char) ('a' + rnd.nextInt(26));
            System.out.println("El enemigo prepara un ataque. Presiona '" + parryKey + "' para parry.");

            long timeoutMs = Math.max(200L, enemy.getReactionTimeMs());
            long deadline = System.currentTimeMillis() + timeoutMs;
            char c = '\0';
            boolean gotInput = false;
            try {
                while (System.currentTimeMillis() < deadline) {
                    if (br.ready()) {
                        int r = br.read();
                        if (r == -1) break;
                        char readChar = (char) r;
                        if (Character.isWhitespace(readChar)) {
                            if (br.ready()) {
                                while (br.ready()) {
                                    int rr = br.read();
                                    if (rr == -1 || rr == '\n' || rr == '\r') break;
                                }
                            }
                            continue;
                        }
                        c = Character.toLowerCase(readChar);
                        if (br.ready()) {
                            while (br.ready()) {
                                int rr = br.read();
                                if (rr == -1 || rr == '\n' || rr == '\r') break;
                            }
                        }
                        gotInput = true;
                        break;
                    }
                    try { Thread.sleep(50); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
                }
            } catch (IOException ioe) {
                // ...existing code...
            }

            if (gotInput && c == parryKey) {
                int baseParry = 4;
                int def = enemy.getDefense();
                int dmg = Math.max(baseParry - def, 0);
                enemy.takeDamage(dmg);
                System.out.println("Parry exitoso! Repeles el ataque y contraatacas por " + dmg + " daño.");
                System.out.println("Vida jugador: " + player.getHp() + ", Vida enemigo: " + enemy.getHp());
            } else {
                System.out.println("Parry fallido.");
                player.takeDamage(enemy.rollAttack());
                System.out.println("Vida jugador: " + player.getHp() + ", Vida enemigo: " + enemy.getHp());
                if (!player.isAlive()) {
                    if (player.consumeSecondChance()) {
                        int reviveHp = Math.max(1, player.getMaxHp() / 2);
                        player.heal(reviveHp);
                        System.out.println("Tu segunda oportunidad se activa.");
                        System.out.println("Vida jugador: " + player.getHp() + ", Vida enemigo: " + enemy.getHp());
                    } else {
                        System.out.println("Has sido derrotado. Fin del combate.");
                        return false;
                    }
                }
            }

            if (!enemy.isAlive()) break;

            // Turno de jugador
            System.out.println("Es tu turno: (a) atacar  (p) usar pocion");
            System.out.print("> ");
            String action = null;
            while (true) {
                action = br.readLine();
                if (action == null) break;
                if (!action.trim().isEmpty()) break;
                System.out.print("> ");
            }
            char act = (action != null && !action.isEmpty()) ? action.trim().toLowerCase().charAt(0) : '\0';
            if (act == 'a') {
                int atk = player.rollAttack();
                int def = enemy.getDefense();
                int dmg = Math.max(atk - def, 0);
                enemy.takeDamage(dmg);
                System.out.println("Atacaste por " + dmg + " daño. Vida enemiga: " + enemy.getHp());
            } else if (act == 'u') {
                player.heal(20);
                System.out.println("Usaste poción.");
            } else if (act == 'p') {
                while (true) {
                    System.out.println("Pociones disponibles:");
                    System.out.println("1) Poción de vida (restaura 50% HP) - cantidad: " + player.getHealthPotions());
                    System.out.println("2) Poción de daño (+10 ataque por 1 combate) - cantidad: " + player.getDamagePotions());
                    System.out.println("0) No usar poción");
                    System.out.print("> ");
                    String opt = br.readLine();
                    if (opt == null) { System.out.println("No usaste poción."); break; }
                    opt = opt.trim();
                    if (opt.equals("0")) { System.out.println("No usaste poción."); break; }
                    if (opt.equals("1")) {
                        if (player.useHealthPotion()) {
                            System.out.println("Usaste una Poción de vida.");
                        } else {
                            System.out.println("No tienes pociones de vida.");
                        }
                        break;
                    }
                    if (opt.equals("2")) {
                        if (player.useDamagePotion()) {
                            System.out.println("Usaste una Poción de daño.");
                        } else {
                            System.out.println("No tienes pociones de daño.");
                        }
                        break;
                    }
                    System.out.println("Opción inválida.");
                }
            } else {
                System.out.println("Acción no válida. Pierdes el turno.");
            }

            if (!enemy.isAlive()) break;

            round++;
        }

        System.out.println();
        if (!player.isAlive()) {
            System.out.println("Has sido derrotado. Fin del combate.");
            return false;
        } else if (!enemy.isAlive()) {
            System.out.println("Has derrotado al enemigo. Fin del combate.");
            if (enemy.getSoulsReward() > 0) {
                player.addSouls(enemy.getSoulsReward());
                System.out.println("Has obtenido " + enemy.getSoulsReward() + " almas. Total: " + player.getSouls());
            }
            if (enemy instanceof Boss) {
                Relic relic = ((Boss) enemy).getRelicReward();
                if (relic != null) {
                    player.addRelic(relic);
                    System.out.println("Has obtenido la reliquia: " + relic.getName());
                }
            }
            return true;
        } else {
            System.out.println("Fin del combate.");
            return false;
        }
    }

    /** Convenience demo method using a fresh Player and a sample enemy */
    public static void runDemo(BufferedReader br) throws IOException {
        // demo enemy: Centinela Oxidado (4s reaction)
        Enemy demo = new enemies.RustySentinels("Centinela Oxidado", 1, 30, 8, 1, 4000L, 1);
        run(br, new Player(), demo);
    }
}
