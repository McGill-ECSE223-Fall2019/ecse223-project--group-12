package ca.mcgill.ecse223.quoridor.view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = -4426310869335015542L;
	
	private JButton saveExitToMenuButton;
	
	public GamePanel() {
		initComponents();
	}

	private void initComponents() {
		
		saveExitToMenuButton = new JButton();
		saveExitToMenuButton.setText("Save and return to Menu");

		// Action Listeners
		
		// listeners for driver
		saveExitToMenuButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveExitToMenuButtonActionPerformed(evt);
			}
		});
		
		// layout
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
				
		// Horizontal Layout
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup() // Column 1
					.addComponent(saveExitToMenuButton)
			)
		); 
		
		// Vertical Layout
		layout.setVerticalGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup() // Column 1
				.addComponent(saveExitToMenuButton)
			)
		);
		
		
	}
	

	private void saveExitToMenuButtonActionPerformed(ActionEvent evt) {
		CardLayout cardLayout = (CardLayout) this.getParent().getLayout();
		cardLayout.show(this.getParent(), "Menu Panel");
	}
	
}
