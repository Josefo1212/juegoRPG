package enemies;

import entities.Relic;
import entities.RelicType;

public class Selmira extends Boss{
    public Selmira(String name, int floor, int maxHp, int attack, int defense, long reactionTimeMs, int soulsReward) {
        super(name, floor, maxHp, attack, defense, reactionTimeMs, soulsReward);
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
