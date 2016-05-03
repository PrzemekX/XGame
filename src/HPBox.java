public class HPBox extends Actor {
	protected static final int BULLET_SPEED = 1;

	public HPBox(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "HPBox.png" });
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
}