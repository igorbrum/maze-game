import com.senac.SimpleJava.Graphics.Color;

public class Key extends Item {
	
	private Color color;
	private int roomNumber;
	private GameObject obj;
	
	public GameObject getObj() {
		return obj;
	}
	public void setObj(GameObject obj) {
		this.obj = obj;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

}
