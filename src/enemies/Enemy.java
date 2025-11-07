package enemies;


public abstract class Enemy {
    private final String name;
    private final int floor;
    private final int maxHp;
    private int hp;
    private final int attack;
    private final int defense;
    private final long reactionTimeMs;
    private final int soulsReward;
    private final int expReward;

    protected Enemy(String name, int floor, int maxHp, int attack, int defense, long reactionTimeMs, int soulsReward, int expReward) {
        this.name = name;
        this.floor = floor;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.reactionTimeMs = reactionTimeMs;
        this.soulsReward = soulsReward;
        this.expReward = expReward;
    }

    public String getName() { return name; }
    public int getFloor() { return floor; }
    public int getMaxHp() { return maxHp; }
    public int getHp() { return hp; }

    public long getReactionTimeMs() { return reactionTimeMs; }
    public int getSoulsReward() { return soulsReward; }
    public int getExpReward() { return expReward; }
    public int getDefense() { return defense; }

    public boolean isAlive() { return hp > 0; }

    public int rollAttack() { return attack; }

    public void takeDamage(int incomingDamage) {
        int damageTaken = Math.max(incomingDamage - defense, 0);
        hp = Math.max(hp - damageTaken, 0);
    }
}
