package game;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import framework.RWT.RWTFrame3D;
import framework.RWT.RWTVirtualController;
import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;
import framework.gameMain.SimpleActionGame;
import framework.model3D.Object3D;
import framework.model3D.Universe;
import framework.physics.PhysicsUtility;

public class TemplateAction2D extends SimpleActionGame {
	private Player player;
	private Enemy enemy;
	private ArrayList<Item> items = new ArrayList<Item>();
//	private Item item;
	private Ground2D stage;

	// あとで設計変更
	// Enemyクラスでこの値を使いたいため。
	public static final int RANGE = 40;

	// プレイヤーの現在の速度が代入されるグローバル変数
	private Velocity2D curV;

	@Override
	public void init(Universe universe) {
		player = new Player();
		player.setPosition(20.0, 0.0);
		player.setDirection(0.0, 0.0);
		((Object3D) player.getBody()).scale(0.0075);
		universe.place(player); // universeに置く。後で取り除けるようにオブジェクトを配置する。

		enemy = new Enemy();
		enemy.setPosition(25.0, 2.0);
		enemy.setDirection(0.0, 0.0);
		((Object3D) enemy.getBody()).scale(0.0075);
		universe.place(enemy); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		
		Item item = new Item();
		item.setPosition(2.0, 3.0);
		item.setDirection(1.0, 0.0);
		((Object3D) item.getBody()).scale(0.01);
		universe.place(item); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items.add(item);
		
		
		
		// ステージの3Dデータを読み込み配置する
		stage = new Ground2D("data\\images\\stage.obj",
				"data\\images\\gaikan2.jpg", windowSizeWidth, windowSizeHeight, 0.045);
		universe.place(stage);

		// 表示範囲を決める（左上が原点としてその原点から幅、高さを計算する）
		setViewRange(2*RANGE,RANGE);
	}

	@Override
	public RWTFrame3D createFrame3D() {
		// TODO Auto-generated method stub
		RWTFrame3D f = new RWTFrame3D();
		f.setSize(1600, 800);
		// f.setExtendedState(Frame.MAXIMIZED_BOTH);
		f.setTitle("Template for Action 2DGame");
		return f;
	}

	@Override
	public void progress(RWTVirtualController virtualController, long interval) {
		curV = player.getVelocity();

		// 静止状態はプレイヤーのxを0にする。（坂で滑って行ってしまうため）
		curV.setX(0.0);
		player.setVelocity(curV);

		// キー操作の処理
		// 左
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.movePositionLeft(0.075);
		}
		// 右
		else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			player.movePositionRight(0.075);
		}
		// 上
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			// ジャンプ
			if (player.isOnGround()) {
				curV.setY(10.0);
				player.setVelocity(curV);
			}
		}
		// 下
		else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			player.movePositionDown(0.01);
		}

		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
		}
		
		player.motion(interval, stage);
		enemy.motion(interval, stage, player);

		// 衝突判定（プレイヤーと敵）
		if (player.checkCollision(enemy)) {
			System.out.println("敵に接触した！");
		}
		//衝突判定（プレイヤーとアイテム）
		for (int i = 0; i < items.size(); i++)  {
			Item item = items.get(i);
			if (player.checkCollision(item)) {
				System.out.println("アイテムをゲット");
				universe.displace(item);
				items.remove(i);
				i--;
			}
		}

	}
	

	/**
	 * ゲームのメイン
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		TemplateAction2D game = new TemplateAction2D();
		game.setFramePolicy(5, 33, false);
		game.start();
	}

	@Override
	public OvergroundActor2D getOvergroundActor() {
		return player;
	}

}
