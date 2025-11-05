package game.combat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import entities.Player;

public class QTECombat {
    /**
     * Run a QTE combat between a Player and a simple enemy described by parameters.
     * Returns true if the player wins, false if the player is defeated.
     */
    public static boolean run(BufferedReader br, Player player, String enemyName, int enemyHpInit, int enemyAttack, int enemyDefense) throws IOException {
        int enemyHp = enemyHpInit;

        System.out.println();
        System.out.println("--- QTE Parry/Dodge Demo ---");
        System.out.println("Antes de iniciar: el jugador está siendo atacado por un enemigo.");
        System.out.println("El enemigo prepara un ataque inicial...");

        // initial hit: use a small fixed hit to kick off
        player.takeDamage(Math.max(5 - player.getMaxHp()/100, 0)); // keep small initial impact
        System.out.println("Ataque inicial: el jugador recibe 5 daño. Vida: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println();

        Random rnd = new Random();
        int round = 1;

        while (player.isAlive() && enemyHp > 0) {
            System.out.println();
            System.out.println("--- Ronda " + round + " ---");
            System.out.println("El enemigo parece prepararse...");

            // random preparation delay between 2 and 10 seconds (hidden)
            int prepMs = 2000 + rnd.nextInt(8001); // 2000..10000
            try {
                Thread.sleep(prepMs);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            // random parry key a-z
            char parryKey = (char) ('a' + rnd.nextInt(26));
            System.out.println("El enemigo prepara un ataque. Presiona '" + parryKey + "' para parry.");

            // Timed input: wait up to 4000 ms for the correct char using non-blocking polling
            long timeoutMs = 4000L;
            long deadline = System.currentTimeMillis() + timeoutMs;
            char c = '\0';
            boolean gotInput = false;
            try {
                while (System.currentTimeMillis() < deadline) {
                    if (br.ready()) {
                        int r = br.read(); // read one char non-blocking (since ready() true)
                        if (r == -1) break;
                        char readChar = (char) r;
                        // skip newlines and spaces
                        if (Character.isWhitespace(readChar)) {
                            // consume and continue
                            if (br.ready()) {
                                while (br.ready()) {
                                    int rr = br.read();
                                    if (rr == -1 || rr == '\n' || rr == '\r') break;
                                }
                            }
                            continue;
                        }
                        c = Character.toLowerCase(readChar);
                        // consume rest of the line if any
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
                // fall back to no input
            }

            if (gotInput && c == parryKey) {
                System.out.println("Parry exitoso! Repeles el ataque y contraatacas por 4 daño.");
                int dmg = Math.max(4 - enemyDefense, 0);
                enemyHp = Math.max(enemyHp - dmg, 0);
            } else {
                // failed parry or timeout: player takes damage
                System.out.println("No has parryeado a tiempo o golpeaste la tecla equivocada.");
                player.takeDamage(enemyAttack);
                System.out.println("Parry fallido. Recibes daño. Vida jugador: " + player.getHp());
            }

            System.out.println("Vida jugador: " + player.getHp() + ", Vida enemigo: " + enemyHp);
            if (enemyHp <= 0) break;

            // Player turn
            System.out.println("Es tu turno: (a) atacar  (p) usar pocion");
            System.out.print("> ");
            // Read player action; loop if the line is empty to avoid losing turn due to leftover newline
            String action = null;
            while (true) {
                action = br.readLine();
                if (action == null) break; // EOF
                if (!action.trim().isEmpty()) break; // got non-empty input
                // otherwise prompt again
                System.out.print("> ");
            }
            char act = (action != null && !action.isEmpty()) ? action.trim().toLowerCase().charAt(0) : '\0';
            if (act == 'a') {
                int dmg = Math.max(player.rollAttack() - enemyDefense, 0);
                enemyHp = Math.max(enemyHp - dmg, 0);
                System.out.println("Atacaste por " + dmg + " daño. Vida enemiga: " + enemyHp);
            } else if (act == 'u') {
                // legacy: 'u' small heal
                player.heal(20);
                System.out.println("Usaste poción. Vida jugador: " + player.getHp());
            } else if (act == 'p') {
                player.healPercent(25);
                System.out.println("Usaste poción. Vida jugador: " + player.getHp());
            } else {
                System.out.println("Acción no válida. Pierdes el turno.");
            }

            if (enemyHp <= 0) break;

            round++;
        }

        System.out.println();
        if (!player.isAlive()) {
            System.out.println("Has sido derrotado. Fin del combate.");
            return false;
        } else if (enemyHp <= 0) {
            System.out.println("Has derrotado al enemigo. Fin del combate.");
            return true;
        } else {
            System.out.println("Fin del combate.");
            return false;
        }
    }

    /** Convenience demo method using a fresh Player and a sample enemy */
    public static void runDemo(BufferedReader br) throws IOException {
        run(br, new Player(), "Enemigo demo", 50, 10, 2);
    }
}
