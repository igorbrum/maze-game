
public class Creatures {
	private double accuracyHit;
	private int life;
	private int damage;
	private GameObject obj;
	private boolean show;
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean showCreature) {
		this.show = showCreature;
	}
	public double getAccuracyHit() {
		return accuracyHit;
	}
	public void setAccuracyHit(double accuracyHit) {
		this.accuracyHit = accuracyHit;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public GameObject getObj() {
		return obj;
	}
	public void setObj(GameObject obj) {
		this.obj = obj;
	}
}
