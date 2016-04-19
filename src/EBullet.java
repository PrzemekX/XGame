public class EBullet extends Actor {
	protected static final int BULLET_SPEED = 3;

	public EBullet(Stage stage) {
		super(stage);
		setSpriteNames(new String[] { "EBullet.gif"});
		setFrameSpeed(10);
	}
	
	public void REBullet() {
		REBullet REB = new REBullet(stage);
		REB.setX(x);
		REB.setY(y);
		stage.addActor(REB);
	}

	public void act() {
		super.act();
		y += BULLET_SPEED;
		if (y > Stage.WYSOKOSC_GRY)
			remove();
	}
	
	public void collision(Actor a) {
		if (a instanceof Player) {
			if(Player.ShieldWork) {
				remove();
				REBullet();
			}
			else
			{
				remove();
			}
		}
	}
}