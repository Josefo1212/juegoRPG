package entities;

public class Player {
    private static final String playerName = "Aurelion";

    private final String name = playerName;
    private int level = 1;
    private int maxHp = 50;
    private int hp = 50;
    private int strength = 8;
    private int magicPower = 0;
    private int defense = 5;
    private final Inventory inventory = new Inventory();
    private int souls = 0; // 'almas' moneda del juego
    private int tempAttackBuff = 0;
    private int tempBuffDuration = 0;

        public String getName() { return name; }
        public int getLevel() { return level; }
        public int getMaxHp() { return maxHp; }
    public int getHp() { return hp; }

        public boolean isAlive() { return hp > 0; }

    public int rollAttack() { return strength + tempAttackBuff; }

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

        public void setStrength(int s) { this.strength = s; }
        public void setDefense(int d) { this.defense = d; }
    public void setMaxHp(int mh) { this.maxHp = mh; this.hp = Math.min(this.hp, mh); }

    // Metodos para almas
    public int getSouls() { return souls; }
    public void addSouls(int n) { this.souls += n; }
    public boolean spendSouls(int n) {
        if (n <= 0) return false;
        if (this.souls >= n) { this.souls -= n; return true; }
        return false;
    }

    // Metodos de inventario
    public Inventory getInventory() { return inventory; }

    public int getHealthPotions() { return inventory.getHealthPotions(); }
    public void addHealthPotions(int n) { inventory.addHealthPotions(n); }
    public boolean useHealthPotion() {
        if (inventory.consumeHealthPotion()) { healPercent(50); return true; }
        return false;
    }

    public int getDamagePotions() { return inventory.getDamagePotions(); }
    public void addDamagePotions(int n) { inventory.addDamagePotions(n); }
    public boolean useDamagePotion() {
        if (inventory.consumeDamagePotion()) { addTempAttackBuff(10, 1); return true; }
        return false;
    }

    public boolean hasSecondChance() { return inventory.hasSecondChance(); }
    public void grantSecondChance() { inventory.grantSecondChance(); }
    public boolean consumeSecondChance() { return inventory.consumeSecondChance(); }

    // Buffo temporal
    public void addTempAttackBuff(int amount, int durationCombats) {
        this.tempAttackBuff += amount;
        this.tempBuffDuration = Math.max(this.tempBuffDuration, durationCombats);
    }

    // quitar buffos al terminar combate
    public void onCombatEnd() {
        if (tempBuffDuration > 0) tempBuffDuration--;
        if (tempBuffDuration <= 0) {
            tempAttackBuff = 0;
            tempBuffDuration = 0;
        }
    }

    // curar completamente al maximo de HP (usado en hub/rest)
    public void fullRest() { this.hp = this.maxHp; }
}
