package enemies;

import entities.Relic;
import entities.RelicType;

public class Zarketh extends Boss{
    protected Zarketh(String name, int floor, int maxHp, int attack, int defense) {
        super(name, floor, maxHp, attack, defense);
    }

    @Override
    public Relic getRelicReward() {
        return new Relic(
                "Emblema del Eterno Retorno",
                RelicType.emblemOfEternalReturn,
                "Restaura la divinidad e inmortalidad de Vaelros."
        );
    }
}
