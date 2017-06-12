
public class Creatures {
	private double accuracyHit;
	private int life;
	private GameObject obj;
	private boolean showEnemy;
	public boolean isShowEnemy() {
		return showEnemy;
	}
	public void setShowEnemy(boolean showEnemy) {
		this.showEnemy = showEnemy;
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
