package enemies;

import entities.Relic;
import entities.RelicType;

public class Thargron extends Boss{
    public Thargron(String name, int floor, int maxHp, int attack, int defense, long reactionTimeMs, int soulsReward, int expReward) {
        super(name, floor, maxHp, attack, defense, reactionTimeMs, soulsReward, expReward);
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
