package labmate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;

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
		
		view.setProjectPickerListener(new ProjectPickerListener());
		view.setAddProjectButtonListener(new AddProjectButtonListener());
		view.setRenameProjectButtonListener(new RenameProjectButtonListener());
		view.setDateBackButtonListener(new DateBackButtonListener()); 
		view.setDatePickerListener (new DatePickerListener());
		view.setDateForwardButtonListener(new DateForwardButtonListener()); 
		view.setEditorListener(new EditorListener());
		
	}

	public void main() {
		
		Date date = new Date();
		view.setSelectedDate(date);
	}

	/*--------------------------------------------------------------------------
	 * Action Listeners 
	 *-------------------------------------------------------------------------*/
	public class ProjectPickerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// Populate calendar with entries
			
			// show todays entry
			
		}
	}
	
	/**
	 * adds a new project
	 */
	public class AddProjectButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newProject = view.showNewProjectDialog();
			
			// don't allow empty project names
			if (newProject == null || newProject.trim().equals("")) return;
			
			// don't override existing projects (name is unique)
			if (model.addProject(newProject)) {
				view.setAvailableProjects(model.getProjectNames());
				view.setSelectedProject(newProject);
			}
		}
	}
	
	
	/**
	 * renames currently selected project
	 */
	public class RenameProjectButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newName = view.showRenameProjectDialog();
			
			// don't allow empty project names
			if (newName == null || newName.trim().equals("")) return;
			
			// don't override existing projects (name is unique)
			if (model.renameProject(view.getSelectedProject(), newName)) {
				view.setAvailableProjects(model.getProjectNames());
				view.setSelectedProject(newName);
			}
		}
	}
	
	
	public class DateBackButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// go back one day in the calendar, will be handled by DatePickerListener
		}
	}
	
	public class DatePickerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// show entry in editor of the selected date
			
		}
	}
	
	public class DateForwardButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// go back one day in the calendar, will be handled by DatePickerListener 
		}
	}
	
	public class EditorListener implements KeyListener {
		
		// if user entered changed text in editor save current entry
		
		@Override
		public void keyPressed(KeyEvent arg0) { }
		@Override
		public void keyReleased(KeyEvent arg0) { }
		@Override
		public void keyTyped(KeyEvent arg0) { }
	}
}
