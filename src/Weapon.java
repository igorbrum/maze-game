
public class Weapon extends Item{
	private String typeWeapon;
	private int damageWeapon;
	private double weaponAccuracy;
	public String getTypeWeapon() {
		return typeWeapon;
	}
	public void setTypeWeapon(String typeWeapon) {
		this.typeWeapon = typeWeapon;
	}
	public int getDamageWeapon() {
		return damageWeapon;
	}
	public void setDamageWeapon(int damageWeapon) {
		this.damageWeapon = damageWeapon;
	}
	public double getWeaponAccuracy() {
		return weaponAccuracy;
	}
	public void setWeaponAccuracy(double weaponAccuracy) {
		this.weaponAccuracy = weaponAccuracy;
	}
}
