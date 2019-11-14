package ca.mcgill.ecse223.quoridor.application;

import ca.mcgill.ecse223.quoridor.view.QuoridorFrame;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class QuoridorApplication {

	private static Quoridor quoridor;
	
	public static void main(String[] args) {
		// start UI

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuoridorFrame().setVisible(true);          
            }
        });
	}

	public static Quoridor getQuoridor() {
		if (quoridor == null) {
			quoridor = new Quoridor();
		}
 		return quoridor;
	}
}
