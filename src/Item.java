
public class Item {
	
	private boolean itemTaked;
	private boolean itemShow;
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

}
