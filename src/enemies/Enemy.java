package enemies;


public abstract class Enemy {
    private final String name;
    private final int floor;
    private final int maxHp;
    private int hp;
    private final int attack;
    private final int defense;

    protected Enemy(String name, int floor, int maxHp, int attack, int defense) {
        this.name = name;
        this.floor = floor;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() { return name; }
    public int getFloor() { return floor; }
    public int getMaxHp() { return maxHp; }
    public int getHp() { return hp; }

    public boolean isAlive() { return hp > 0; }

    public int rollAttack() { return attack; }

    public void takeDamage(int incomingDamage) {
        int damageTaken = Math.max(incomingDamage - defense, 0);
        hp = Math.max(hp - damageTaken, 0);
    }
}
