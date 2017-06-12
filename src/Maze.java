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
	public Door createDoor(int number, GameObject obj){
		Door door = new Door();
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
		armor.setRoomNumber(roomNumber);
		armor.setObj(obj);
		armor.setItemShow(show);
		return armor;
	}
	public Weapon createWeapon(int randomRoomNumber, String typeWeapon, GameObject obj, boolean show) {
		Weapon weapon = new Weapon();
		if (typeWeapon.toLowerCase().equals("dagger")) {
			
		}
		weapon.setTypeWeapon(typeWeapon);
		return weapon;
	}
}