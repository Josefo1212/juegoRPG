package entities;

public class Inventory {
	private int healthPotions = 0;
	private int damagePotions = 0;
	private boolean secondChance = false;

	public int getHealthPotions() { return healthPotions; }
	public void addHealthPotions(int n) { if (n > 0) this.healthPotions += n; }
	public boolean consumeHealthPotion() {
		if (healthPotions > 0) { healthPotions--; return true; }
		return false;
	}

	public int getDamagePotions() { return damagePotions; }
	public void addDamagePotions(int n) { if (n > 0) this.damagePotions += n; }
	public boolean consumeDamagePotion() {
		if (damagePotions > 0) { damagePotions--; return true; }
		return false;
	}

	public boolean hasSecondChance() { return secondChance; }
	public void grantSecondChance() { this.secondChance = true; }
	public boolean consumeSecondChance() {
		if (secondChance) { secondChance = false; return true; }
		return false;
	}


}
