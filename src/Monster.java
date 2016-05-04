import java.awt.Color;
import java.awt.Graphics2D;

public class Monster extends Actor {
	protected static final double FIRING_FREQUENCY = 0.01;
	protected int vx;
	public static final int MAX_HP = 20;
	String []pot = { "EStatek1.png" };

	public Monster(Stage stage) {
		super(stage);
		setSpriteNames( pot );
		setFrameSpeed(10);
		hp = MAX_HP;
	}
	
	public int procHP(int a) {
		float prHP = (float)(hp)/(float)(MAX_HP);
		int Width = (int) ((float)(a) * prHP);
		return Width;
	}
	
	public void paint(Graphics2D g) {
		g.drawImage(spriteCache.getSprite(spriteNames[currentFrame]), x, y, stage);
		g.setPaint(Color.red);
		g.fillRect(x, y - 4, procHP(getWidth()), 6);
	}
	
	public int getHp() {
		return hp;
	}

	public void setHp(int i) {
		hp = i;
	}
	
	public void addHp(int i) {
		if (hp > -1 * i) {
			hp += i;
		} else {
			hp = 0;
			destroy();
		}
	}
	
	public void destroy() {
		if(hp <= 0) {
			stage.getPlayer().addScore(20);
			remove();
			spawn();
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
		if(a instanceof Bullet) {
			addHp(-10);
		}
		if(a instanceof Bomb) {
			addHp(-20);
		}
		if(a instanceof Player) {
			addHp(-40);
		}
		if(a instanceof Expl) {
			addHp(-30);
		}
		if(a instanceof REBullet) {
			addHp(-10);
		}
	}
}