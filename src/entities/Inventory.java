package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
	private int healthPotions = 0;
	private int damagePotions = 0;
	private boolean secondChance = false;

	// NUEVO: almacenamiento de reliquias
	private final List<Relic> relics = new ArrayList<>();

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

	// NUEVO: utilidades de reliquias
	public void addRelic(Relic relic) {
		if (relic == null) return;
		if (!hasRelic(relic.getType())) {
			relics.add(relic);
		}
	}

	public boolean hasRelic(RelicType type) {
		if (type == null) return false;
		for (Relic r : relics) {
			if (r.getType() == type) return true;
		}
		return false;
	}

	public List<Relic> getRelics() {
		return Collections.unmodifiableList(relics);
	}
}
