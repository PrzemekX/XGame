public class Bullet extends Actor {
	protected static final int BULLET_SPEED = 10;

	public Bullet(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "bullet.gif" });
	}

	public void act() {
		super.act();
		y -= BULLET_SPEED;
		if (y < 0)
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