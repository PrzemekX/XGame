public class ShieldBox extends Actor {
	protected static final int BULLET_SPEED = 1;

	public ShieldBox(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "ShieldBox.png" });
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
}