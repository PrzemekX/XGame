public class MissleBox extends Actor {
	protected static final int BULLET_SPEED = 1;

	public MissleBox(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "MissleBox.png" });
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
}