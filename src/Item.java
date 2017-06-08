
public class Item {
	
	private boolean itemTaked;
	private boolean itemShow;
	private int roomNumber;
	private GameObject obj;

	public GameObject getObj() {
		return obj;
	}

	public void setObj(GameObject obj) {
		this.obj = obj;
	}

	public boolean isItemShow() {
		return itemShow;
	}

	public void setItemShow(boolean itemShow) {
		this.itemShow = itemShow;
	}

	public boolean isItemTaked() {
		return itemTaked;
	}

	public void setItemTaked(boolean itemTaked) {
		this.itemTaked = itemTaked;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

}
