package labmate;

import labmate.model.LabNotesModel;
import labmate.view.LabNotesView;

/*
 * this is the controller in mvc, which ties everything together
 * and handles the user interaction
 */
public class LabNotesController {
	LabNotesView view;
	LabNotesModel model;
	
	/*
	 * Constructor
	 */
	public LabNotesController(LabNotesModel model, LabNotesView view) {
		this.model = model;
		this.view = view;
	}

	public void main() {
		
		// check for diary file
		
		// set to current date
		
	}

	/*--------------------------------------------------------------------------
	 * Action Listeners 
	 *-------------------------------------------------------------------------*/
	// TODO
}
