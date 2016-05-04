public class Missle extends Actor {
	protected static final int BULLET_SPEED = 10;

	public Missle(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "missle1.png" });
	}

	public void act() {
		super.act();
		y -= BULLET_SPEED;
		if (y < 0)
			remove();
	}
	
	public void Explosion() {
		Expl e = new Expl(stage);
		e.setX(x + (getWidth() / 2 - e.getWidth() / 2));
		e.setY(y + (getWidth() / 2 - e.getWidth() / 2));
		stage.addActor(e);
	}

	public void collision(Actor a) {
		if (a instanceof Monster) {
			remove();
			Explosion();
		}
		if (a instanceof Monster2) {
			remove();
			Explosion();
		}
	}
}