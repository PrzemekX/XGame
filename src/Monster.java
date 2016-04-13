public class Monster extends Actor {
	protected int vx;
	String []pot = { "EStatek1.png" };

	public Monster(Stage stage) {
		super(stage);
		setSpriteNames( pot );
		setFrameSpeed(10);
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