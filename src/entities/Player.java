package entities;

public class Player {
    private static final String playerName = "Aurelion";

    private final String name = playerName;
    private int level = 1;
    private int maxHp = 100;
    private int hp = 100;
    private int strength = 8;
    private int magicPower = 0;
    private int defense = 5;
    private int speed = 5;
    private int potion = 2; // Para la demo 

        public String getName() { return name; }
        public int getLevel() { return level; }
        public int getMaxHp() { return maxHp; }
        public int getHp() { return hp; }

        public boolean isAlive() { return hp > 0; }

        // Attack roll (simple fixed damage based on strength)
        public int rollAttack() { return strength; }

        public void takeDamage(int incoming) {
            int damageTaken = Math.max(incoming - defense, 0);
            hp = Math.max(hp - damageTaken, 0);
        }

        public void heal(int amount) {
            hp = Math.min(hp + amount, maxHp);
        }

        public void healPercent(int percent) {
            int heal = Math.max(1, (maxHp * percent) / 100);
            heal(heal);
        }

        // setters for tuning (optional)
        public void setStrength(int s) { this.strength = s; }
        public void setDefense(int d) { this.defense = d; }
    public void setMaxHp(int mh) { this.maxHp = mh; this.hp = Math.min(this.hp, mh); }
}
