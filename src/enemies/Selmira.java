package enemies;

import entities.Relic;
import entities.RelicType;

public class Selmira extends Boss{
    protected Selmira(String name, int floor, int maxHp, int attack, int defense) {
        super(name, floor, maxHp, attack, defense);
    }

    @Override
    public Relic getRelicReward() {
        return new Relic(
                "Cántico de la Llama Interior",
                RelicType.songOfTheInnerFlame,
                "Restaura la magia ancestral de Aurelion."
        );
    }
}
