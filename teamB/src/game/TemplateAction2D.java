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
	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 40;

	// �v���C���[�̌��݂̑��x����������O���[�o���ϐ�
	private Velocity2D curV;
	Point point =new Point();//�P�ʂ̌v�Z�p



	
	@Override
	public void init(Universe universe) {
		player = new Player();
		player.setPosition(20.0, 0.0);
		player.setDirection(0.0, 0.0);
		((Object3D) player.getBody()).scale(0.0075);
		universe.place(player); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B

		enemy = new Enemy();
		enemy.setPosition(25.0, 2.0);
		enemy.setDirection(0.0, 0.0);
		((Object3D) enemy.getBody()).scale(0.0075);
		universe.place(enemy); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		
		Item item = new Item();//item�̐ݒu
		item.setPosition(-24.0, 16.0);
		item.setDirection(1.0, 0.0);
		((Object3D) item.getBody()).scale(0.01);
		universe.place(item); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items.add(item);
		
		
		Item2 item2 = new Item2();//item2�̐ݒu
		item2.setPosition(0.0, 6.0);
		item2.setDirection(1.0, 0.0);
		((Object3D) item2.getBody()).scale(0.01);
		universe.place(item2); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items2.add(item2);
		
		Item3 item3 = new Item3();//item3�̐ݒu
		item3.setPosition(-56.0, 16.0);
		item3.setDirection(1.0, 0.0);
		((Object3D) item3.getBody()).scale(0.01);
		universe.place(item3); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items3.add(item3);
		
		Point point =new Point();
		
		
		
   //�A�C�e���ݒu
		//�A�C�e��1
		Item item21 = new Item();//item�̐ݒu
		item21.setPosition(-14.0, 3.0);
		item21.setDirection(1.0, 0.0);
		((Object3D) item21.getBody()).scale(0.01);
		universe.place(item21); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items.add(item21);
		
		Item item31 = new Item();//item�̐ݒu
		item31.setPosition(-32.0, 6.0);
		item31.setDirection(1.0, 0.0);
		((Object3D) item31.getBody()).scale(0.01);
		universe.place(item31); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items.add(item31);
		
				//�A�C�e��2

		Item2 item22 = new Item2();//item2�̐ݒu
		item22.setPosition(-64.0, 20.0);
		item22.setDirection(1.0, 0.0);
		((Object3D) item22.getBody()).scale(0.01);
		universe.place(item22); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items2.add(item22);
		

		Item2 item32 = new Item2();//item2�̐ݒu
		item32.setPosition(15.0, 6.0);
		item32.setDirection(1.0, 0.0);
		((Object3D) item32.getBody()).scale(0.01);
		universe.place(item32); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items2.add(item32);
		
		//�A�C�e��3
		Item3 item23 = new Item3();//item3�̐ݒu
		item23.setPosition(-74.0, 20.0);
		item23.setDirection(1.0, 0.0);
		((Object3D) item23.getBody()).scale(0.01);
		universe.place(item23); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items3.add(item23);
		
		
		Item3 item33 = new Item3();//item3�̐ݒu
		item33.setPosition(-84.0, 19.0);
		item33.setDirection(1.0, 0.0);
		((Object3D) item33.getBody()).scale(0.01);
		universe.place(item33); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items3.add(item33);
		
		// �X�e�[�W��3D�f�[�^��ǂݍ��ݔz�u����
		stage = new Ground2D("data\\images\\KDstage2.obj",
				"data\\images\\gaikan2.jpg", windowSizeWidth, windowSizeHeight, 0.045);
		universe.place(stage);

		// �\���͈͂����߂�i���オ���_�Ƃ��Ă��̌��_���畝�A�������v�Z����j
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

		// �Î~��Ԃ̓v���C���[��x��0�ɂ���B�i��Ŋ����čs���Ă��܂����߁j
		curV.setX(0.0);
		player.setVelocity(curV);

		// �L�[����̏���
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.LEFT)) {
			player.movePositionLeft(0.075);
		}
		// �E
		else if (virtualController.isKeyDown(0, RWTVirtualController.RIGHT)) {
			player.movePositionRight(0.075);
		}
		// ��
		if (virtualController.isKeyDown(0, RWTVirtualController.UP)) {
			// �W�����v
			if (player.isOnGround()) {
				curV.setY(10.0);
				player.setVelocity(curV);
			}
		}
		// ��
		else if (virtualController.isKeyDown(0, RWTVirtualController.DOWN)) {
			player.movePositionDown(0.01);
		}

		if (virtualController.isKeyDown(0, RWTVirtualController.BUTTON_B)) {
		}
		
		player.motion(interval, stage);
		enemy.motion(interval, stage, player);
		// �Փ˔���i�v���C���[�ƓG�j
		if (player.checkCollision(enemy)) {
			System.out.println("�G�ɐڐG�����I");
			point.Genten();	
		}
		//�Փ˔���i�v���C���[��item�j
		for (int i = 0; i < items.size(); i++)  {
			Item item = items.get(i);
			if (player.checkCollision(item)) {
				System.out.println("�A�C�e�����Q�b�g");
				universe.displace(item);
				items.remove(i);
				i--;
				point.Katen(5);
				
			}
		}

		//�Փ˔���i�v���C���[��item2�j
		for (int j = 0; j < items2.size(); j++)  {
			Item2 item2 = items2.get(j);
			if (player.checkCollision(item2)) {
				System.out.println("�A�C�e�����Q�b�g");
				universe.displace(item2);
				items2.remove(j);
				j--;
				point.Katen(10);
			}
		}

		//�Փ˔���i�v���C���[��item3�j
		for (int j = 0; j < items3.size(); j++)  {
			Item3 item3 = items3.get(j);
			if (player.checkCollision(item3)) {
				System.out.println("�A�C�e�����Q�b�g");
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

			// RWT���ł̓C�x���g���������Ȃ�
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
	 * �Q�[���̃��C��
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