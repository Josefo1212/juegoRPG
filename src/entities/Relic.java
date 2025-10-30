package entities;

import java.util.Objects;


public class Relic {
    private final String name;
    private final RelicType type;
    private final String description;

    public Relic(String name, RelicType type, String description) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.description = description == null ? "":description;
    }

    public String getName() {
        return name;
    }

    public RelicType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public static Relic sacredTitaniumCore(){
        return new Relic("Núcleo de titanio sagrado", RelicType.sacredTitaniumCore, "\n" +
                "Una reliquia que restaura la fuerza divina.");
    }

    public static Relic songOfTheInnerFlame(){
        return new Relic("Canto de la llama interior", RelicType.songOfTheInnerFlame, "\n" +
                "Una reliquia que restaura el poder mágico.");
    }

    public static Relic emblemOfEternalReturn(){
        return new Relic("Emblema del retorno eterno", RelicType.emblemOfEternalReturn, "\n" +
                "Una reliquia que restaura la divinidad.");
    }
}
