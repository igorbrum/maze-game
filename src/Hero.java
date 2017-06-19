
public class Hero extends Creatures {
	private int armorClass;
	
	public Hero() {
		this.setLife(20);
		this.setAccuracyHit(0.75);
		this.setDamage(2);
		this.armorClass = 0;
	}
	public int getArmorClass() {
		return armorClass;
	}
	public void setArmorClass(int armorClass) {
		this.armorClass = armorClass;
	}

}
