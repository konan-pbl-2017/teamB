package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.util.ArrayList;

import javax.vecmath.Vector2d;

import template.shooting2D.StartContainer;
import template.shooting2D.TemplateShooting2DMultiStates;

import framework.RWT.RWTBoard;
import framework.RWT.RWTCanvas3D;
import framework.RWT.RWTContainer;
import framework.RWT.RWTFrame3D;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;
import framework.game2D.OvergroundActor2D;
import framework.game2D.Velocity2D;
import framework.gameMain.IGameState;
import framework.gameMain.SimpleActionGame;
import framework.model3D.Object3D;
import framework.model3D.Universe;
import framework.physics.PhysicsUtility;

public class TemplateAction2D extends SimpleActionGame {
	private Player player;
	private Enemy enemy;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Item2> items2 = new ArrayList<Item2>();
	private ArrayList<Item3> items3 = new ArrayList<Item3>();
	private Ground2D stage;
	int count=128;
	/*RWTLabel scoreLabel;
	RWTBoard scoreBoard;
	*/
	// あとで設計変更
	// Enemyクラスでこの値を使いたいため。
	public static final int RANGE = 40;

	// プレイヤーの現在の速度が代入されるグローバル変数
	private Velocity2D curV;
	Point point =new Point();//単位の計算用



	
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
		
		Item item = new Item();//itemの設置
		item.setPosition(-24.0, 16.0);
		item.setDirection(1.0, 0.0);
		((Object3D) item.getBody()).scale(0.01);
		universe.place(item); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items.add(item);
		
		
		Item2 item2 = new Item2();//item2の設置
		item2.setPosition(0.0, 6.0);
		item2.setDirection(1.0, 0.0);
		((Object3D) item2.getBody()).scale(0.01);
		universe.place(item2); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items2.add(item2);
		
		Item3 item3 = new Item3();//item3の設置
		item3.setPosition(-56.0, 16.0);
		item3.setDirection(1.0, 0.0);
		((Object3D) item3.getBody()).scale(0.01);
		universe.place(item3); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items3.add(item3);
		
		Point point =new Point();
		
		
		
   //アイテム設置
		//アイテム1
		Item item21 = new Item();//itemの設置
		item21.setPosition(-14.0, 3.0);
		item21.setDirection(1.0, 0.0);
		((Object3D) item21.getBody()).scale(0.01);
		universe.place(item21); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items.add(item21);
		
		Item item31 = new Item();//itemの設置
		item31.setPosition(-32.0, 6.0);
		item31.setDirection(1.0, 0.0);
		((Object3D) item31.getBody()).scale(0.01);
		universe.place(item31); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items.add(item31);
		
				//アイテム2

		Item2 item22 = new Item2();//item2の設置
		item22.setPosition(-64.0, 20.0);
		item22.setDirection(1.0, 0.0);
		((Object3D) item22.getBody()).scale(0.01);
		universe.place(item22); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items2.add(item22);
		

		Item2 item32 = new Item2();//item2の設置
		item32.setPosition(15.0, 6.0);
		item32.setDirection(1.0, 0.0);
		((Object3D) item32.getBody()).scale(0.01);
		universe.place(item32); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items2.add(item32);
		
		//アイテム3
		Item3 item23 = new Item3();//item3の設置
		item23.setPosition(-74.0, 20.0);
		item23.setDirection(1.0, 0.0);
		((Object3D) item23.getBody()).scale(0.01);
		universe.place(item23); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items3.add(item23);
		
		
		Item3 item33 = new Item3();//item3の設置
		item33.setPosition(-84.0, 19.0);
		item33.setDirection(1.0, 0.0);
		((Object3D) item33.getBody()).scale(0.01);
		universe.place(item33); // universeに置く。後で取り除けるようにオブジェクトを配置する。
		items3.add(item33);
		
		// ステージの3Dデータを読み込み配置する
		stage = new Ground2D("data\\images\\KDstage2.obj",
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
			point.Genten();	
		}
		//衝突判定（プレイヤーとitem）
		for (int i = 0; i < items.size(); i++)  {
			Item item = items.get(i);
			if (player.checkCollision(item)) {
				System.out.println("アイテムをゲット");
				universe.displace(item);
				items.remove(i);
				i--;
				point.Katen(5);
				
			}
		}

		//衝突判定（プレイヤーとitem2）
		for (int j = 0; j < items2.size(); j++)  {
			Item2 item2 = items2.get(j);
			if (player.checkCollision(item2)) {
				System.out.println("アイテムをゲット");
				universe.displace(item2);
				items2.remove(j);
				j--;
				point.Katen(10);
			}
		}

		//衝突判定（プレイヤーとitem3）
		for (int j = 0; j < items3.size(); j++)  {
			Item3 item3 = items3.get(j);
			if (player.checkCollision(item3)) {
				System.out.println("アイテムをゲット");
				universe.displace(item3);
				items3.remove(j);
				j--;
				point.Katen(15);				
			}
		}
	}
	/*
	protected RWTContainer createRWTContainer() {
		return new RWTContainer() {
			RWTCanvas3D canvas;

			@Override
			public void build(GraphicsConfiguration gc) {
				if (gc != null) {
					canvas = new RWTCanvas3D(gc);
				} else {
					canvas = new RWTCanvas3D();
				}
				canvas.setRelativePosition(0.0f, 0.0f);
				canvas.setRelativeSize(1.0f, 1.0f);
				
				scoreBoard = new RWTBoard(0.2f, 0.2f, 0.2f, 0.05f, Color.WHITE);
				scoreBoard.setVisible(true);
				canvas.addWidget(scoreBoard);
				scoreLabel = new RWTLabel(0.5f, 0.2f, "score"+count, Color.RED, new Font("", Font.ITALIC, 50));
				scoreLabel.setVisible(true);
				canvas.addWidget(scoreLabel);

				addCanvas(canvas);
				repaint();
			}

			// RWT側ではイベント処理をしない
			@Override
			public void keyPressed(RWTVirtualKey key) {
			}

			@Override
			public void keyReleased(RWTVirtualKey key) {
			}

			@Override
			public void keyTyped(RWTVirtualKey key) {
			}
		};
	}
	*/
	/*
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