package ca.mcgill.ecse223.quoridor.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
		gameBoard.setBorder(new LineBorder(Color.BLACK));

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
		
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				GridBagConstraints c = new GridBagConstraints();
				//JPanel square = new JPanel(new BorderLayout());
				JButton square = new JButton();
				c.gridx=i;
				c.gridy=j;
				c.weightx=1.0;
				c.weighty=1.0;
				c.fill=GridBagConstraints.BOTH;
				c.insets=new Insets(2, 2, 2, 2);
				
				square.setBackground(Color.LIGHT_GRAY);

				if ( j%2 == 0 || i%2 == 0) {
					square.setBackground(Color.blue);
					
					c.insets=new Insets(0, 0, 0, 0);
					
					if ( j%2 == 0 && i%2 == 0) {
						square.setPreferredSize(new Dimension(10, 10));
						c.fill=GridBagConstraints.NONE;
					} else if(j%2==0) {
						square.setPreferredSize(new Dimension(0, 10));
						c.fill=GridBagConstraints.HORIZONTAL;
					} else { 
						square.setPreferredSize(new Dimension(10, 0));
						c.fill=GridBagConstraints.VERTICAL;
					}
					c.weightx=0.0;
					c.weighty=0.0;
				}
				if(j==0||j==18||i==0||i==18) {
					square.setBackground(Color.BLACK);
				}
				square.setBorder(new LineBorder(Color.BLACK));
				gameBoard.add(square,c);
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
