
public class Door {
	
	private GameObject obj;
	private int leadTo;
	private double changeEnemy;
	private boolean enemyAlive;
	
	public boolean isEnemyAlive() {
		return enemyAlive;
	}
	public void setEnemyAlive(boolean enemyAlive) {
		this.enemyAlive = enemyAlive;
	}
	public double getChangeEnemy() {
		return changeEnemy;
	}
	public void setChangeEnemy(double changeEnemy) {
		this.changeEnemy = changeEnemy;
	}
	public GameObject getObj() {
		return obj;
	}
	public void setObj(GameObject obj) {
		this.obj = obj;
	}
	public int getLeadTo() {
		return leadTo;
	}
	public void setLeadTo(int leadTo) {
		this.leadTo = leadTo;
	}

}
