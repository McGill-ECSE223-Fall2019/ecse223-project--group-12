package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;

	private JButton saveExitToMenuButton;
	private JButton grabWallButton;
	private JPanel controlUI;
	private JPanel gameBoard;

	public GamePanel() {
		initComponents();
	}

	private void initComponents() {

		saveExitToMenuButton = new JButton();
		grabWallButton = new JButton("Grab Wall");
		saveExitToMenuButton.setText("Save and return to Menu");

		controlUI = new JPanel();
		gameBoard = new JPanel(new GridBagLayout());
		//gameBoard.setBorder(new LineBorder(Color.BLACK));

		// Layout for Game Panel
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		// Horizontal Layout
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup() 
				.addComponent(gameBoard)
			)
			.addGroup(layout.createParallelGroup() 
					.addComponent(controlUI)
			)
		);
		// Vertical Layout
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup()
				.addComponent(controlUI)
				.addComponent(gameBoard)	
			)
			
		);
		
		
		// Action Listeners
		// listeners for save button
		saveExitToMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveExitToMenuButtonActionPerformed(evt);
			}
		});
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				GridBagConstraints c = new GridBagConstraints();
				//JPanel square = new JPanel(new BorderLayout());
				JButton square = new JButton();
				//square.setPreferredSize(new Dimension(50,50));
				square.setMaximumSize(new Dimension(500,500));
				c.gridx=i;
				c.gridy=j;
				c.weightx=1.0;
				c.weighty=0.5;
				c.fill=GridBagConstraints.BOTH;
				gameBoard.add(square,c);
				square.setBackground(Color.LIGHT_GRAY);
				square.setBorder(new LineBorder(Color.BLACK));
			}
		}

		// Layout for control UI
		controlUI.setLayout(new GroupLayout(controlUI));
		GroupLayout controlUILayout = (GroupLayout) controlUI.getLayout();
		controlUILayout.setAutoCreateGaps(true);
		controlUILayout.setAutoCreateContainerGaps(true);
		// Horizontal Layout
		controlUILayout.setHorizontalGroup(controlUILayout.createSequentialGroup()
			.addGroup(controlUILayout.createParallelGroup() // Column 1
				.addComponent(saveExitToMenuButton)
				.addComponent(grabWallButton)
			)
		);
		// Vertical Layout
		controlUILayout.setVerticalGroup(controlUILayout.createSequentialGroup()
			.addGroup(controlUILayout.createParallelGroup() // Row 1
				.addComponent(saveExitToMenuButton)
			)
			.addGroup(controlUILayout.createParallelGroup() // Row 1
				.addComponent(grabWallButton)
			)
		);

	}

	private void saveExitToMenuButtonActionPerformed(ActionEvent evt) {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Menu Panel");
	}

}
