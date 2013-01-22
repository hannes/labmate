package labmate.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.html.HTMLEditorKit;

import labmate.model.LabNotesModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXEditorPane;

/*
 * this is the view of the mvc and is concerned with the gui ONLY 
 */
public class LabNotesView extends JFrame {
	private static final long serialVersionUID = 1L;

	LabNotesModel model;

	JToolBar toolbar;
	JComboBox projectPicker;
	JButton addProjectButton;
	JButton renameProjectButton;
	
	JButton dateBackButton;
	JXDatePicker datePicker = new JXDatePicker();
	JButton dateForwardButton;

	JScrollPane scrollPane;
	JXEditorPane editor = new JXEditorPane();
	
	
	/**
	 * Construct all gui components and save reference to model
	 * 
	 * @param model
	 */
	public LabNotesView(LabNotesModel model){
		this.model = model;
		
		// construct
		toolbar = new JToolBar();
		projectPicker = new JComboBox();
		addProjectButton = new JButton("add"); 
		renameProjectButton = new JButton("rename");
		
		dateBackButton = new JButton("<");
		datePicker = new JXDatePicker();
		dateForwardButton = new JButton(">");

		editor = new JXEditorPane();
		
		// assemble gui
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
		toolbar.setFloatable(false);
		toolbar.add(projectPicker);
		toolbar.add(addProjectButton);
		toolbar.add(renameProjectButton);
		toolbar.add(Box.createHorizontalGlue());
		toolbar.add(dateBackButton);
		toolbar.add(datePicker);
		toolbar.add(dateForwardButton);
		container.add(toolbar, BorderLayout.NORTH);
		
		editor.setEditorKit(new HTMLEditorKit());
		editor.setMargin(new Insets(5, 5, 5, 5));
		JScrollPane scrollPane = new JScrollPane(editor);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		container.add(scrollPane, BorderLayout.CENTER);
		
		// setup frame
		setTitle("LabMate (v0.2)");
		setPreferredSize(new Dimension(600, 300));
		
		// show
		pack();
		setVisible(true);
	}
	
	
	
	//--------------------------------------------------------------------------
	// dialogs called by controller 
	//--------------------------------------------------------------------------
	
	public void showErrorDialog(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	public String showRenameProjectDialog() {
		return JOptionPane.showInputDialog("Rename current project");
		
	}
	
	public String showNewProjectDialog() {
		return JOptionPane.showInputDialog("New project title");
	}
	
	
	
	//--------------------------------------------------------------------------
	// provide getter for controller 
	//--------------------------------------------------------------------------
	
	public String getSelectedProject() {
		return (String)projectPicker.getSelectedItem();
	}
	
	public Date getSelectedDate() {
		return datePicker.getDate();
	}
	
	public String getEditorText() {
		return editor.getText();
	}
	
	
	//--------------------------------------------------------------------------
	// provide setter for controller
	//--------------------------------------------------------------------------
	
	public void setAvailableProjects(ArrayList<String> projectList) {
		projectPicker.removeAllItems();
		for (String name : projectList) {
			projectPicker.addItem(name);
		}
	}
	
	public void setSelectedProject(String project) {
		projectPicker.setSelectedItem(project);
	}
	
	public void setSelectedDate(Date date) {
		datePicker.setDate(date);
	}
	
	public void setDatesInDatePicker(ArrayList<Date> dates) {
		datePicker.getMonthView().setFlaggedDates(dates.toArray(new Date[0]));
	}
	
	public void setEditorText(String text) {
		editor.setText(text);
	}
	
	
	//--------------------------------------------------------------------------
	// listeners will be set by controller
	//--------------------------------------------------------------------------
	public void setProjectPickerListener(ActionListener listener) {
		projectPicker.addActionListener(listener);
	}
	
	public void setAddProjectButtonListener(ActionListener listener) {
		addProjectButton.addActionListener(listener);
	}
	
	public void setRenameProjectButtonListener(ActionListener listener) {
		renameProjectButton.addActionListener(listener);
	}
	
	public void setDateBackButtonListener(ActionListener listener) {
		dateBackButton.addActionListener(listener);
	}
	
	public void setDatePickerListener(ActionListener listener) {
		datePicker.addActionListener(listener);
	}
	
	public void setDateForwardButtonListener(ActionListener listener) {
		dateForwardButton.addActionListener(listener);
	}
	
	public void setEditorListener(KeyListener listener) {
		editor.addKeyListener(listener);
	}

	public void disableEditor(boolean b) {
		editor.setEditable(b);
	}
}