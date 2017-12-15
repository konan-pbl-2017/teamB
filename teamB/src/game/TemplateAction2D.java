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

	// ���ƂŐ݌v�ύX
	// Enemy�N���X�ł��̒l���g���������߁B
	public static final int RANGE = 40;

	// �v���C���[�̌��݂̑��x����������O���[�o���ϐ�
	private Velocity2D curV;

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
		
		Item item = new Item();
		item.setPosition(2.0, 3.0);
		item.setDirection(1.0, 0.0);
		((Object3D) item.getBody()).scale(0.01);
		universe.place(item); // universe�ɒu���B��Ŏ�菜����悤�ɃI�u�W�F�N�g��z�u����B
		items.add(item);
		
		
		
		// �X�e�[�W��3D�f�[�^��ǂݍ��ݔz�u����
		stage = new Ground2D("data\\images\\stage.obj",
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
		}
		//�Փ˔���i�v���C���[�ƃA�C�e���j
		for (int i = 0; i < items.size(); i++)  {
			Item item = items.get(i);
			if (player.checkCollision(item)) {
				System.out.println("�A�C�e�����Q�b�g");
				universe.displace(item);
				items.remove(i);
				i--;
			}
		}

	}
	

	/**
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
