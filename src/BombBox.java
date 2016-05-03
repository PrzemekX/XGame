public class BombBox extends Actor {
	protected static final int BULLET_SPEED = 1;

	public BombBox(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "BombBox.png" });
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
}