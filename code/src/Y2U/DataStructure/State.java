package Y2U.DataStructure;

public class State {
	
	public State() {
		this.id = "";
		this.name = "";
		this.invariant = "";
		this.x = 0;
		this.y = 0;
	}
	
	public State(String id) {
		this.id = id;
		this.name = "";
		this.invariant = "";
		this.x = 0;
		this.y = 0;
	}
	
	public State(String id, String name) {
		this.id = id;
		this.name = name;
		this.invariant = "";
		this.x = 0;
		this.y = 0;
	}
	

	//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInvariant() {
		return invariant;
	}
	public void setInvariant(String invariant) {
		this.invariant = invariant;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	



	//
	private String id;
	private String name;
	private String invariant;
	private int x;
	private int y;
}
