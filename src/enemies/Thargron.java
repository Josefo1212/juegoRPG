package enemies;

import entities.Relic;
import entities.RelicType;

public class Thargron extends Boss{
    protected Thargron(String name, int floor, int maxHp, int attack, int defense) {
        super(name, floor, maxHp, attack, defense);
    }

    @Override
    public Relic getRelicReward() {
        return new Relic(
                "Núcleo de Titanio Sagrado",
                RelicType.sacredTitaniumCore,
                "Restaura la fuerza divina de Aurelion."
        );
    }
}
