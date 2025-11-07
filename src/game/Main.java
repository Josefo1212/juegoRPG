package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import game.combat.QTECombat;
import dungeon.Dungeon;
import entities.Player;
import java.util.List;
import entities.Relic;
import dungeon.Room;
import dungeon.RoomType;

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
                    // Antes estaba maxHp=1. Ajustado para que el daño avance sin forzar mínimos.
                    enemies.Thargron boss = new enemies.Thargron("Thargron, el Martillo Silente", 1, 100, 12, 3, 1500L, 5,120);
                    boolean win = QTECombat.run(br, player, boss);
                player.onCombatEnd();
                if (win) {
                    // Mejorado: mostrar reliquia obtenida y listar inventario
                    Relic r = boss.getRelicReward();
                    System.out.println("Has vencido a Thargron y obtenido: " + r.getName() + " (guardada en tu inventario).");
                    List<Relic> relics = player.getRelics();
                    if (relics.isEmpty()) {
                        System.out.println("Inventario de reliquias: (vacío)");
                    } else {
                        System.out.println("Inventario de reliquias:");
                        for (Relic rr : relics) {
                            System.out.println(" - " + rr.getName());
                        }
                    }

                    // NUEVO: pasar a la siguiente sala después del boss
                    Room postBoss = new Room(
                            6,
                            "Santuario de Resonancias",
                            "Entre pilares agrietados, la reliquia recién obtenida emite un pulso que fortalece tu espíritu.",
                            java.util.Arrays.asList(
                                    "Aurelion: El Núcleo responde... Siento parte del sello reparándose.",
                                    "Voz antigua: 'Tres fragmentos más y el tejido será restaurado.'",
                                    "Aurelion: Debo continuar hacia el próximo piso."
                            ),
                            RoomType.ENTRANCE
                    );
                    postBoss.play(br);

                    System.out.println("¿Deseas continuar al siguiente piso? (s/n)");
                    String cont = br.readLine();
                    if (cont != null && cont.trim().equalsIgnoreCase("s")) {
                        System.out.println("Ascendiendo al segundo piso...");
                        boolean bossHere2 = Dungeon.playSegundoPiso(br, player);
                        if (bossHere2) {
                            System.out.println();
                            System.out.println("Ante ti: Selmira, la Voz Encadenada. ¿Combatir? (s/n)");
                            String fight2 = br.readLine();
                            if (fight2 != null && fight2.trim().equalsIgnoreCase("s")) {
                                // Antes estaba maxHp=1. Ajustado para progreso normal.
                                enemies.Selmira boss2 = new enemies.Selmira("Selmira, la Voz Encadenada", 2, 150, 14, 3, 1300L, 7,120);
                                boolean win2 = QTECombat.run(br, player, boss2);
                                player.onCombatEnd();
                                if (win2) {
                                    Relic r2 = boss2.getRelicReward();
                                    System.out.println("Has vencido a Selmira y obtenido: " + r2.getName() + " (guardada en tu inventario).");
                                    List<Relic> relics2 = player.getRelics();
                                    System.out.println("Reliquias actuales:");
                                    for (Relic rr : relics2) System.out.println(" - " + rr.getName());

                                    Room postBoss2 = new Room(
                                            6,
                                            "Cámara de Resonancia Ígnea",
                                            "La llama interior despierta y entibia el aire mientras tatuajes espectrales se apagan.",
                                            java.util.Arrays.asList(
                                                    "Aurelion: La segunda pieza... El sello responde.",
                                                    "Eco múltiple: 'Dos voces restauradas. Falta la grieta final.'"
                                            ),
                                            RoomType.ENTRANCE
                                    );
                                    postBoss2.play(br);

                                    System.out.println("¿Continuar hacia el tercer piso (Núcleo del Abismo)? (s/n)");
                                    String cont3 = br.readLine();
                                    if (cont3 != null && cont3.trim().equalsIgnoreCase("s")) {
                                        // Piso 3
                                        boolean bossHere3 = Dungeon.playTercerPiso(br, player);
                                        if (bossHere3) {
                                            System.out.println();
                                            System.out.println("Ante ti: Zar'keth, el Portador de la Grieta. ¿Combatir? (s/n)");
                                            String fight3 = br.readLine();
                                            if (fight3 != null && fight3.trim().equalsIgnoreCase("s")) {
                                                enemies.Zarketh boss3 = new enemies.Zarketh("Zar'keth, el Portador de la Grieta", 3, 300
                                                        , 16, 3, 1100L, 10,200);
                                                boolean win3 = QTECombat.run(br, player, boss3);
                                                player.onCombatEnd();
                                                if (win3) {
                                                    // Al vencer, ya se muestran reliquia y diálogos finales más abajo
                                                    Relic r3 = boss3.getRelicReward();
                                                    System.out.println("Has vencido a Zar'keth y obtenido: " + r3.getName() + " (guardada en tu inventario).");
                                                    List<Relic> relics3 = player.getRelics();
                                                    System.out.println("Reliquias actuales:");
                                                    for (Relic rr : relics3) System.out.println(" - " + rr.getName());

                                                    // Final del juego — Restauración (diálogo correspondiente)
                                                    System.out.println();
                                                    System.out.println("Narrador: “El Núcleo ha sido sellado. La Grieta se cierra.");
                                                    System.out.println("Aurelion, restaurado en fuerza, magia y divinidad, vuelve a ser Guardián.");
                                                    System.out.println("Pero el mundo ha cambiado… y su equilibrio deberá ser vigilado una vez más.”");
                                                    System.out.println("Aurelion: “El abismo me quitó todo.");
                                                    System.out.println("Pero no mi voluntad.");
                                                    System.out.println("Soy Aurelion. Y el sello… permanece.”");
                                                } else {
                                                    System.out.println("Has caído ante Zar'keth. La Grieta continúa abierta.");
                                                }
                                            } else {
                                                System.out.println("Te retiras por ahora. El Núcleo sigue respirando en silencio...");
                                            }
                                        } else {
                                            System.out.println("No encontraste al Portador en este intento.");
                                        }
                                    } else {
                                        System.out.println("Detienes tu ascenso por ahora, guardando el poder reunido.");
                                    }
                                } else {
                                    System.out.println("Has caído ante Selmira. Su silencio consume tu avance.");
                                }
                            } else {
                                System.out.println("Retienes tu avance ante Selmira. El Velo permanece cerrado.");
                            }
                        } else {
                            System.out.println("No encontraste a Selmira en este intento.");
                        }
                    } else {
                        System.out.println("Terminas tu avance por ahora, guardando la fuerza de la reliquia.");
                    }
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