package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.LineBorder;

import ca.mcgill.ecse223.quoridor.model.Direction;
public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;

	private JButton saveExitToMenuButton;
	private JButton grabWallButton;
	private JPanel controlUI;
	private JPanel gameBoard;
	//private JButton pawn;

	public GamePanel() {
		initComponents();
	}

	private void initComponents() {
		
		//------------------------	
		// Set up components
	    //------------------------
		
		// Buttons
		saveExitToMenuButton = new JButton("Save and return to Menu");
		grabWallButton = new JButton("Grab Wall");
		// Panes
		controlUI = new JPanel();
		gameBoard = new JPanel(new GridBagLayout());
		gameBoard.setBorder(new LineBorder(Color.BLACK));
		gameBoard.setPreferredSize(new Dimension(600, 600));
		//Icon for Pawns
		ImageIcon wPawnIcon = new ImageIcon(getClass().getClassLoader().getResource("whitePawn.png"));
		Image img = wPawnIcon.getImage();
		img = img.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
		wPawnIcon.setImage(img);
		ImageIcon bPawnIcon = new ImageIcon(getClass().getClassLoader().getResource("blackPawn.jpg"));
		Image img1 = bPawnIcon.getImage();
		img1 = img1.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
		bPawnIcon.setImage(img1);
		
		//------------------------	
		// Group Layout Managers
	    //------------------------
		
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
			
		//------------------------	
		// Action Listeners
		//------------------------
		
		// listeners for save button
		saveExitToMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveExitToMenuButtonActionPerformed(evt);
			}
		});
		
		//------------------------	
		// Game Board
		//------------------------
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				GridBagConstraints c = new GridBagConstraints();
				JButton  square = new JButton ();
				square.setOpaque(true);
				square.setBorder(new LineBorder(Color.BLACK));
				c.gridx=i;
				c.gridy=j;
				c.weightx=1.0;
				c.weighty=1.0;
				c.fill=GridBagConstraints.BOTH;
				c.insets=new Insets(2, 2, 2, 2);
				square.setBackground(Color.LIGHT_GRAY);
				// Check if the square is a slot or a tile
				if ( j%2 == 0 || i%2 == 0) {
					square.setBackground(Color.blue);
					square.setBorder(null);
					c.insets=new Insets(0, 0, 0, 0);
					c.weightx=0.0;
					c.weighty=0.0;
					// set minimum dimensions for vertical or horizontal slots
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
				} else {
					// Add white and black invisible pawns to each tile, 
					JButton whitePawn = new JButton();  
					whitePawn.setIcon(wPawnIcon);
					square.setLayout(new OverlayLayout(square));
					whitePawn.setVisible(false);
					whitePawn.setAlignmentX(CENTER_ALIGNMENT);
					whitePawn.setAlignmentY(CENTER_ALIGNMENT);
					whitePawn.setBorder(null);
					whitePawn.setOpaque(false);
					whitePawn.setBackground(Color.LIGHT_GRAY);
					square.add(whitePawn);
					
					JButton blackPawn = new JButton();   
					blackPawn.setIcon(bPawnIcon);
					square.setLayout(new OverlayLayout(square));
					//pawn.setBackground(Color.WHITE);
					blackPawn.setVisible(false);
					blackPawn.setAlignmentX(CENTER_ALIGNMENT);
					blackPawn.setAlignmentY(CENTER_ALIGNMENT);
					blackPawn.setBorder(null);
					blackPawn.setOpaque(false);
					blackPawn.setBackground(Color.LIGHT_GRAY);
					square.add(blackPawn);	
				}
				// Make the outline of the board black
				if(j==0||j==18||i==0||i==18) {
					square.setBackground(Color.BLACK);
				}
				gameBoard.add(square,c);
			}
		}
		// Just run a few methods to test the board
		showPawn(0,0,null);
		drawWall(0,0,null);
	}

	private void saveExitToMenuButtonActionPerformed(ActionEvent evt) {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Menu Panel");
	}
	
	/**
	 * currently just a demo to show how to display pawns
	 * @param x
	 * @param y
	 * @param c
	 */
	private void showPawn(int x, int y, Color c) {
		JButton whiteStart = (JButton) gameBoard.getComponent(20 + 19*8);
		whiteStart.getComponent(0).setVisible(true);
		JButton blackStart = (JButton) gameBoard.getComponent(20 + 19*8 + 16);
		blackStart.getComponent(1).setVisible(true);
	}
	/**
	 * currently just a demo to show how to display walls
	 * @param x
	 * @param y
	 * @param dir
	 */
	private void drawWall(int x, int y, Direction dir) {
		// Walls are made of three sections (two tile lengths and the slot between them)
		JButton wallA = (JButton) gameBoard.getComponent(20 + 19*7);
		JButton wallB = (JButton) gameBoard.getComponent(20 + 19*7 + 1);
		JButton wallC = (JButton) gameBoard.getComponent(20 + 19*7 + 2);
		
		wallA.setBackground(Color.red);
		wallB.setBackground(Color.red);
		wallC.setBackground(Color.red);
	}

}
