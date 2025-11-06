package dungeon;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Room {
    private final int id;
    private final String title;
    private final String description;
    private final List<String> dialogue;
    private final RoomType type;

    public Room(int id, String title, String description, List<String> dialogue, RoomType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dialogue = dialogue;
        this.type = type;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public RoomType getType() { return type; }

    // NUEVO / CONFIRMADO: getter de diálogo
    public List<String> getDialogue() {
        return dialogue;
    }

    public void play(BufferedReader br) throws IOException {
        System.out.println();
        System.out.println("== " + title + " ==");
        if (description != null && !description.isEmpty()) System.out.println(description);
        System.out.println();

        if (dialogue != null && !dialogue.isEmpty()) {
            for (String line : dialogue) {
                System.out.println(line);
                System.out.println("(Pulsa Enter para continuar...)");
                br.readLine();
            }
        } else {
            System.out.println("No hay nada de interés aquí. Pulsa Enter para continuar.");
            br.readLine();
        }
    }
}
