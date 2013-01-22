package labmate;

import javax.swing.SwingUtilities;

import labmate.model.LabNotesModel;
import labmate.view.LabNotesView;

/**
 * This is the entry point for the mvc architecture
 * 
 * @author julian
 */
public class LabMate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
		final LabNotesModel model = new LabNotesModel();
		final LabNotesView view = new LabNotesView(model);

		// TODO: implement export dump 
		// example: ./prog --dump --project="abc" 
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	LabNotesController controller = new LabNotesController(model, view);
		    	controller.main();
		    }
		});
    }
}
