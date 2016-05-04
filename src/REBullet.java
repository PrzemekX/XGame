public class REBullet extends Actor {
	protected static final int BULLET_SPEED = 3;

	public REBullet(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "REBullet.gif"});
		setFrameSpeed(10);
	}

	public void act() {
		super.act();
		y -= BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
	
	public void collision(Actor a) {
		if (a instanceof Monster) {
			remove();
		}
		if (a instanceof Monster2) {
			remove();
		}
	}
}