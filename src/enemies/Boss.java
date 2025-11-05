package enemies;

import entities.Relic;

public abstract class Boss extends Enemy{

    protected Boss(String name, int floor, int maxHp, int attack, int defense, long reactionTimeMs, int soulsReward) {
        super(name, floor, maxHp, attack, defense, reactionTimeMs, soulsReward);
    }

    public abstract Relic getRelicReward();
}
