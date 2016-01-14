package Y2U.DataStructure;

public class Transition {

	public Transition() {
		this.id = "";
		this.name = "";
		this.Guardid = "";
		this.Guardid = "";
		this.source = "";
		this.target = "";
		this.guard = "";
		this.synchronisation = "";
		this.update = "";
		this.assignment="";
		this.x = 0;
		this.y = 0;
	}
	
	public Transition(String id) {
		this.id = id;
		this.name = "";
		this.Guardid = Guardid;
		this.source = "";
		this.target = "";
		this.guard = "";
		this.synchronisation = "";
		this.update = "";
		this.assignment="";
		this.x = 0;
		this.y = 0;
	}
	
	public Transition(String id, String source, String target) {
		this.id = id;
		this.name = "";
		this.Guardid = Guardid;
		this.source = source;
		this.target = target;
		this.guard = "";
		this.synchronisation = "";
		this.update = "";
		this.assignment="";
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
	
	public String getname() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}
		
	public String getGuardid() {
		return Guardid;
	}
	public void setGuardid(String id) {
		this.Guardid = id;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}
	public String getSynchronisation() {
		return synchronisation;
	}
	public void setSynchronisation(String synchronisation) {
		this.synchronisation = synchronisation;
	}
	public String getassignment() {
		return assignment;
	}
	public void setassignment(String guard) {
		this.guard = assignment;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
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
	private String Guardid;
	private String source;
	private String target;
	private String guard;
	private String synchronisation;
	private String update;
	private String assignment;
	private int x;
	private int y;
}
