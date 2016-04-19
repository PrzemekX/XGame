public class Monster extends Actor {
	protected static final double FIRING_FREQUENCY = 0.01;
	protected int vx;
	String []pot = { "EStatek1.png" };

	public Monster(Stage stage) {
		super(stage);
		setSpriteNames( pot );
		setFrameSpeed(10);
	}
	
	public void fire() {
		EBullet m = new EBullet(stage);
		m.setX(x + getWidth() / 2);
		m.setY(y + getHeight());
		stage.addActor(m);
	}

	public void spawn() {
		Monster m = new Monster(stage);
		m.setX((int) (Math.random() * (Stage.SZEROKOSC - getWidth())));
		m.setY((int) (Math.random() * Stage.WYSOKOSC_GRY / 2));
		m.setVx((int) 1);
		stage.addActor(m);
	}
	
	public void act() {
		super.act();
		x += vx;
		if (x < 0 || x > (Stage.SZEROKOSC - getWidth())) {
			vx = -vx;
		}
		if (Math.random() < FIRING_FREQUENCY)
			fire();
	}
	public int getVx() {
		return vx;
	}

	public void setVx(int i) {
		vx = i;
	}
	
	public void collision(Actor a) {
		if(a instanceof Bullet || a instanceof Bomb || a instanceof Player || a instanceof Expl) {
			remove();
			spawn();
			stage.getPlayer().addScore(20);
		}
	}
}