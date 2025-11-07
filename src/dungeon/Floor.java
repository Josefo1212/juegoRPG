package dungeon;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Player;
import game.combat.QTECombat;

public class Floor {
    private final String name;
    private final String description;
    private final List<Room> rooms = new ArrayList<>();
    private final java.util.Set<Integer> solvedRooms = new java.util.HashSet<>();

    public Floor(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addRoom(Room r) { rooms.add(r); }


    public boolean play(BufferedReader br, Player player) throws IOException {
        System.out.println();
        System.out.println("--- Piso: " + name + " ---");
        if (description != null && !description.isEmpty()) System.out.println(description);

        Map<Integer, Room> byId = new HashMap<>();
        for (Room r : rooms) {
            byId.put(r.getId(), r);
        }


        Room entrance = byId.get(1);
        if (entrance != null) entrance.play(br);

        while (true) {
            System.out.println();
            System.out.println("Estás en la Sala 1 (El Hub). Vida: " + player.getHp() + "/" + player.getMaxHp() + ", Almas: " + player.getSouls());
            System.out.println("EXP: " + player.getCurrentExp() + "/" + player.getExpToNextLevel());
            System.out.println("Elige una sala para visitar (2-" + rooms.size() + "), 0 para salir del piso, o 'I' para ver inventario:");
            for (int i = 2; i <= rooms.size(); i++) {
                Room r = byId.get(i);
                if (r != null) System.out.println(i + ") " + r.getTitle());
            }
            System.out.println("I) Ver inventario");
            System.out.print("> ");
            String line = br.readLine();
            if (line == null) return false;
            line = line.trim();

            // NUEVO: ver inventario sin salir del hub
            if (line.equalsIgnoreCase("i") || line.equalsIgnoreCase("inv")) {
                showInventory(br, player);
                continue;
            }

            if (line.equals("0")) {
                System.out.println("Sales del piso y regresas al mundo exterior.");
                return false;
            }
            int choice = -1;
            try { choice = Integer.parseInt(line); } catch (NumberFormatException nfe) { choice = -1; }
            if (choice < 2 || choice > rooms.size()) {
                System.out.println("Opción inválida.");
                continue;
            }

            Room chosen = byId.get(choice);
            if (chosen == null) { System.out.println("Sala no encontrada."); continue; }

            // Handle specific room types with behaviour
            if (chosen.getType() == RoomType.ENEMIES) {
                handleEnemiesRoom(br, player, chosen);
            } else if (chosen.getType() == RoomType.MARKET) {
                handleMarket(br, player, chosen);
            } else if (chosen.getType() == RoomType.PUZZLE) {
                handlePuzzleRoom(br, player, chosen);
            } else if (chosen.getType() == RoomType.RUINS) {
                chosen.play(br);
            } else if (chosen.getType() == RoomType.BOSS) {
                chosen.play(br);
                return true; 
            } else {
                chosen.play(br);
            }

            System.out.println();
            System.out.println("Regresas a la Sala 1 para descansar.");
            if (entrance != null) entrance.play(br);
            player.fullRest();
            System.out.println("Has descansado y recuperado toda la vida: " + player.getHp() + "/" + player.getMaxHp());
        }
    }

    // NUEVO: mostrar inventario y reliquias
    private void showInventory(BufferedReader br, Player player) throws IOException {
        System.out.println();
        System.out.println("=== Inventario ===");
        System.out.println("- Almas: " + player.getSouls());
        System.out.println("- Vida: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("- Ataque actual: " + player.rollAttack());
        // NUEVO: magia y aviso si está en 0
        int mp = player.getMagicPower();
        System.out.println("- Magia: " + mp + (mp == 0 ? "  (Necesitas la reliquia 'Canto de la llama interior' para recuperar la magia)" : ""));
        System.out.println("- Pociones de vida: " + player.getHealthPotions());
        System.out.println("- Pociones de daño: " + player.getDamagePotions());
        System.out.println("- Segunda oportunidad: " + (player.hasSecondChance() ? "Sí" : "No"));
        List<entities.Relic> relics = player.getRelics();
        if (relics.isEmpty()) {
            System.out.println("- Reliquias: (ninguna)");
        } else {
            System.out.println("- Reliquias:");
            for (entities.Relic r : relics) {
                System.out.println("   • " + r.getName());
            }
        }
        System.out.println();
        System.out.println("(Pulsa Enter para volver al hub)");
        br.readLine();
    }


    // CAMBIO: firma recibe Room para imprimir diálogo propio (Sala 2 Piso 2)
    private void handleEnemiesRoom(BufferedReader br, Player player, Room room) throws IOException {
        System.out.println();
        if ("El Velo de los Juramentos".equals(this.name) && "Salón de los Susurros".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
                System.out.println("(Pulsa Enter para continuar...)");
                br.readLine();
            }
        } else if ("El Núcleo del Abismo".equals(this.name) && "Cámara de los Nacidos del Vacío".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
                System.out.println("(Pulsa Enter para continuar...)");
                br.readLine();
            }
        } else {
            System.out.println("Has entrado en la sala de enemigos. Puedes luchar para obtener Almas.");
        }

        while (true) {
            System.out.println("¿Deseas enfrentar a un enemigo? (s = sí / n = no)");
            System.out.print("> ");
            String line = br.readLine();
            if (line == null || !line.trim().equalsIgnoreCase("s")) break;


            boolean win = false;
            try {
                enemies.RustySentinels enemy = new enemies.RustySentinels("Centinela Oxidado", 1, 30, 8, 1, 4000L, 1, 20);
                win = QTECombat.run(br, player, enemy);
            } catch (IOException ioe) {
                System.out.println("Error en combate: " + ioe.getMessage());
            }

            player.onCombatEnd();

            if (win) {
                player.addSouls(1);
                System.out.println("Has obtenido 1 Alma. Almas totales: " + player.getSouls());
            } else {
                System.out.println("Has sido derrotado o te has retirado del combate.");

                if (!player.isAlive()) {
                    System.out.println("Estás inconsciente y te regresan a la Sala 1 para recuperarte.");
                    break;
                }
            }
        }
    }

    // CAMBIO: mercado contextual para Tejedora de Secretos
    private void handleMarket(BufferedReader br, Player player, Room room) throws IOException {
        System.out.println();
        if ("El Velo de los Juramentos".equals(this.name) && "Tejedora de Secretos".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
            }
            while (true) {
                System.out.println("Tienes " + player.getSouls() + " almas.");
                System.out.println("1) Velo de Concentración (+10 ataque 1 combate) - 3 almas");
                System.out.println("2) Fragmento de Cántico (segunda oportunidad) - 4 almas");
                System.out.println("3) Tinta de Juramento (objeto de sabor) - 2 almas");
                System.out.println("0) Salir");
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("0")) break;
                if (line.equals("1")) {
                    if (player.spendSouls(3)) { player.addDamagePotions(1); System.out.println("Compraste Velo de Concentración."); }
                    else System.out.println("No tienes suficientes almas.");
                } else if (line.equals("2")) {
                    if (player.spendSouls(4)) { player.grantSecondChance(); System.out.println("Compraste Fragmento de Cántico."); }
                    else System.out.println("No tienes suficientes almas.");
                } else if (line.equals("3")) {
                    if (player.spendSouls(2)) { System.out.println("Compraste Tinta de Juramento. Percibes símbolos ocultos en las paredes."); }
                    else System.out.println("No tienes suficientes almas.");
                } else {
                    System.out.println("Opción inválida.");
                }
            }
            return;
        } else if ("El Núcleo del Abismo".equals(this.name) && "Comerciante del Vacío".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
            }
            while (true) {
                System.out.println("Tienes " + player.getSouls() + " almas.");
                System.out.println("1) Sangre de Grieta (+10 ataque 1 combate) - 3 almas");
                System.out.println("2) Esquirla de Realidad (objeto de sabor) - 3 almas");
                System.out.println("3) Ojo del Retorno (objeto de sabor) - 2 almas");
                System.out.println("0) Salir");
                System.out.print("> ");
                String line = br.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("0")) break;
                if (line.equals("1")) {
                    if (player.spendSouls(3)) { player.addDamagePotions(1); System.out.println("Compraste Sangre de Grieta."); }
                    else System.out.println("No tienes suficientes almas.");
                } else if (line.equals("2")) {
                    if (player.spendSouls(3)) { System.out.println("Compraste una Esquirla de Realidad. Sientes que podrías eludir un destino."); }
                    else System.out.println("No tienes suficientes almas.");
                } else if (line.equals("3")) {
                    if (player.spendSouls(2)) { System.out.println("Compraste el Ojo del Retorno. Una visión te guía hacia el jefe."); }
                    else System.out.println("No tienes suficientes almas.");
                } else {
                    System.out.println("Opción inválida.");
                }
            }
            return;
        }

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

    private void handlePuzzleRoom(BufferedReader br, Player player, Room room) throws IOException {
        System.out.println();
        if (solvedRooms.contains(room.getId())) {
            System.out.println("La sala del acertijo ya fue resuelta. No hay más recompensas aquí.");
            return;
        }
        if ("El Velo de los Juramentos".equals(this.name) && "Cámara del Velo".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
            }
            System.out.print("Respuesta: ");
            String ans = br.readLine();
            if (ans == null) ans = "";
            String cleaned = ans.trim().toLowerCase();
            if (cleaned.contains("juramento")) {
                System.out.println("Aurelion: “Un juramento puede ser redención… o condena. Hoy será mi redención.”");
                System.out.println("Recompensa: Tinta de Juramento — revela secretos ocultos en el templo.");
                solvedRooms.add(room.getId());
            } else {
                System.out.println("La inscripción se apaga. No obtienes nada.");
            }
            return;
        }
        if ("El Núcleo del Abismo".equals(this.name) && "Cámara del Olvido".equals(room.getTitle())) {
            for (String line : java.util.Objects.requireNonNullElse(room.getDialogue(), java.util.List.<String>of())) {
                System.out.println(line);
            }
            System.out.print("Respuesta: ");
            String ans = br.readLine();
            if (ans == null) ans = "";
            String cleaned = ans.trim().toLowerCase();
            if (cleaned.contains("vacío") || cleaned.contains("vacio")) {
                System.out.println("Aurelion: “El vacío no me consumirá. Yo lo cerraré.”");
                System.out.println("Recompensa: Esquirla de Realidad — permite evitar un combate.");
                solvedRooms.add(room.getId());
            } else {
                System.out.println("El susurro se disipa. No obtienes nada.");
            }
            return;
        }

        System.out.println("Al entrar, una voz metálica resuena desde una estatua rota:");
        System.out.println("\"No tengo filo, pero rompo muros.\nNo soy arma, pero soy temido.\n¿Qué soy?\"");
        System.out.print("Respuesta: ");
        String ans = br.readLine();
        if (ans == null) ans = "";
        String cleaned = ans.trim().toLowerCase();
        if (cleaned.contains("martillo")) {
            System.out.println("Aurelion: 'El silencio se rompe con fuerza. Que el poder regrese a mi mano.'");
            // Recompensa: Fragmento de Núcleo — restaura vida 30% + mejora ataque +10 por 1 combate
            player.healPercent(30);
            player.addTempAttackBuff(10, 1);
            System.out.println("Recompensa: Fragmento de Núcleo recibido. Vida y aumento de ataque por 1 combate.");
            solvedRooms.add(room.getId());
        } else {
            System.out.println("Aurelion: 'El eco se desvanece… y con él, la recompensa.'");
        }
    }
}
