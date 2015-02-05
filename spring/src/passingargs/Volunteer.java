package passingargs;

public class Volunteer implements Thinker {
	
	private String thoughts;

	@Override
	public void think(String thoughts) {
		this.thoughts = thoughts;
	}
	
	public String getThoughts() {
		return this.thoughts;
	}

}
