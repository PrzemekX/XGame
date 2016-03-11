public class Monster extends Actor {
	protected int vx;
	String []pot = { "EStatek1.png"};

	public Monster(Stage stage) {
		super(stage);
		setSpriteNames( pot );
	}

	public void spawn() {
		Monster m = new Monster(stage);
		m.setX((int) (Math.random() * (Stage.SZEROKOSC - getWidth())));
		m.setY((int) (Math.random() * Stage.WYSOKOSC_GRY / 2));
		m.setVx((int) 1);
		stage.addActor(m);
	}

	public int getVx() {
		return vx;
	}

	public void setVx(int i) {
		vx = i;
	}
}