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
	
	public void Hpbox() {
		HPBox Hp = new HPBox(stage);
		Hp.setX(x + getWidth() / 2);
		Hp.setY(y + getHeight());
		stage.addActor(Hp);
	}
	
	public void BombBox() {
		BombBox Bomb = new BombBox(stage);
		Bomb.setX(x + getWidth() / 2);
		Bomb.setY(y + getHeight());
		stage.addActor(Bomb);
	}
	
	public void ShieldBox() {
		ShieldBox Shield = new ShieldBox(stage);
		Shield.setX(x + getWidth() / 2);
		Shield.setY(y + getHeight());
		stage.addActor(Shield);
	}
	
	public void MissleBox() {
		MissleBox Missle = new MissleBox(stage);
		Missle.setX(x + getWidth() / 2);
		Missle.setY(y + getHeight());
		stage.addActor(Missle);
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
		if(a instanceof Bullet || a instanceof Bomb || a instanceof Player || a instanceof Expl || a instanceof REBullet) {
			remove();
			spawn();
			stage.getPlayer().addScore(20);
			int rand = (int) (Math.random() * 100);
			if (rand > 90 && rand < 93) {
				Hpbox();
			}
			if (rand > 80 && rand < 83) {
				BombBox();
			}
			if (rand > 70 && rand < 73) {
				ShieldBox();
			}
			if (rand > 60 && rand < 63) {
				MissleBox();
			}
		}
	}
}