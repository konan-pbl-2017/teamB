
	package game;

	import javax.vecmath.Vector2d;

	import framework.game2D.Ground2D;
	import framework.game2D.OvergroundActor2D;
	import framework.game2D.Velocity2D;

	/**
	 * �G�̃N���X
	 * 
	 * @author H.Kotobuki
	 * 
	 */
	public class Item extends OvergroundActor2D {
		private static final double ITEM_SPEED = 0.0;

		@Override
		public String getAnimationFileName() {
			return null;
		}

		@Override
		public String getModelFileName() {
			return "data\\images\\leaf.obj";
		}
		
		public void motion(long interval, Ground2D ground, Player player) {
			Vector2d v = player.getPosition().getVector2d();
			v.sub(getPosition().getVector2d());
			v.normalize();
			v.scale(ITEM_SPEED);
			setVelocity(new Velocity2D(v));
			super.motion(interval, ground);
		}
	}

