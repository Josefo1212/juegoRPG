package enemies;

import entities.Relic;
import entities.RelicType;

public class Zarketh extends Boss{
    public Zarketh(String name, int floor, int maxHp, int attack, int defense, long reactionTimeMs, int soulsReward) {
        super(name, floor, maxHp, attack, defense, reactionTimeMs, soulsReward);
    }

    @Override
    public Relic getRelicReward() {
        return new Relic(
                "Emblema del Eterno Retorno",
                RelicType.emblemOfEternalReturn,
                "Restaura la divinidad e inmortalidad de Aurelion."
        );
    }
}
