package game;

import java.awt.Event;
import java.awt.Font;
import java.awt.GraphicsConfiguration;

import framework.RWT.RWTContainer;
import framework.RWT.RWTLabel;
import framework.RWT.RWTVirtualController;
import framework.RWT.RWTVirtualKey;
import framework.game2D.Ground2D;

public class EndingContainer extends RWTContainer {
	private TemplateAction2D game;

	public EndingContainer(TemplateAction2D game) {
		this.game = game;
	}

	@Override
	public void build(GraphicsConfiguration gc) {
		RWTLabel startLabel = new RWTLabel();
		startLabel.setString("Game Over");
		startLabel.setRelativePosition(0.2f, 0.5f);
		Font f = new Font("", Font.PLAIN, 60);		
		startLabel.setFont(f);
		addWidget(startLabel);
	}

	@Override
	public void keyPressed(RWTVirtualKey key) {
		if (key.getVirtualKey() == RWTVirtualController.BUTTON_A) {
			game.restart();
		}
	}

	@Override
	public void keyReleased(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(RWTVirtualKey key) {
		// TODO Auto-generated method stub

	}

}
