public class EBullet extends Actor {
	protected static final int BULLET_SPEED = 3;

	public EBullet(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "EBullet.gif"});
		setFrameSpeed(10);
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
	
	public void collision(Actor a) {
		if (a instanceof Player) {
			remove();
		}
	}
}
