package enemies;

import entities.Relic;

public abstract class Boss extends Enemy{

    protected Boss(String name, int floor, int maxHp, int attack, int defense) {
        super(name, floor, maxHp, attack, defense);
    }

    public abstract Relic getRelicReward();
}
