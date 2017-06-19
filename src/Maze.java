import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
	public Room room[] = new Room[32];
	
	private void readFile(String nameFile) {
		try {
			Scanner fileToRead = new Scanner(new FileInputStream(nameFile));
			int i = 1;
			while (fileToRead.hasNext()) {
				String coordinates = fileToRead.nextLine();
				room[i] = createListRoom(coordinates);
				i++;
			}
			fileToRead.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println("Arquivo nao encontrado");
		} catch (Exception e) {
			System.err.println("Erro interno do sistema");
		}
	}
	private Room createListRoom(String coordinates) {
		Room r = new Room();
		r.setRoomCoordinates(coordinates);
		return r;
	}
	public void createMaze(String pathToFile) {
		String nameFile = pathToFile;
		readFile(nameFile);
	}
	public Room callNextRoom(int i) {
		Room r = new Room();
		r = room[i];
		return r;
	}
	public Door createDoor(int number, GameObject obj, double d){
		Door door = new Door();
		door.setChangeEnemy(d);
		door.setLeadTo(number);
		door.setObj(obj);
		return door;
	}
	public Item createItem(GameObject obj, boolean taked){
		Item item = new Item();
		item.setObj(obj);
		item.setItemTaked(taked);
		return item;
	}
	public Key createKeys(int roomNumber, int color, GameObject obj, boolean show){
		Key key = new Key();
		key.setRoomNumber(roomNumber);
		key.setObj(obj);
		key.setItemShow(show);
		return key;
	}
	public Armor createArmor(int roomNumber, String typeArmor, GameObject obj, boolean show) {
		Armor armor = new Armor();
		if (typeArmor.toLowerCase().equals("leathermail")) {
			armor.setArmorClass(2);
		}
		if (typeArmor.toLowerCase().equals("chainmail")) {
			armor.setArmorClass(3);
		}
		if (typeArmor.toLowerCase().equals("mithrilmail")) {
			armor.setArmorClass(3);
		}
		armor.setTypeArmor(typeArmor);
		armor.setRoomNumber(roomNumber);
		armor.setObj(obj);
		armor.setItemShow(show);
		return armor;
	}
	public Weapon createWeapon(int randomRoomNumber, String typeWeapon, GameObject obj, boolean show) {
		Weapon weapon = new Weapon();
		if (typeWeapon.toLowerCase().equals("dagger")) {
			weapon.setDamageWeapon(1);
			weapon.setWeaponAccuracy(0.75);
		}
		if (typeWeapon.toLowerCase().equals("knife")) {
			weapon.setDamageWeapon(2);
			weapon.setWeaponAccuracy(0.8);
		}
		if (typeWeapon.toLowerCase().equals("shortsword")) {
			weapon.setDamageWeapon(3);
			weapon.setWeaponAccuracy(0.85);
		}
		if (typeWeapon.toLowerCase().equals("longsword")) {
			weapon.setDamageWeapon(5);
			weapon.setWeaponAccuracy(0.65);
		}
		weapon.setTypeWeapon(typeWeapon);
		weapon.setRoomNumber(randomRoomNumber);
		weapon.setItemShow(show);
		weapon.setObj(obj);
		return weapon;
	}
	public Enemy createEnemy(String typeEnemy, GameObject obj){
		Enemy enemy = new Enemy();
		if (typeEnemy.toLowerCase().equals("goblin")) {
			enemy.setLife(2);
			enemy.setAccuracyHit(0.8);
			enemy.setDamage(2);
			enemy.setTypeEnemy("goblin");
			enemy.setShow(true);
		}
		if (typeEnemy.toLowerCase().equals("orc")) {
			enemy.setLife(5);
			enemy.setAccuracyHit(0.75);
			enemy.setDamage(4);
			enemy.setTypeEnemy("orc");
			enemy.setShow(true);
		}
		if (typeEnemy.toLowerCase().equals("troll")) {
			enemy.setLife(10);
			enemy.setAccuracyHit(0.5);
			enemy.setDamage(6);
			enemy.setTypeEnemy("troll");
			enemy.setShow(true);
		}
		enemy.setObj(obj);
		return enemy;
	}
}