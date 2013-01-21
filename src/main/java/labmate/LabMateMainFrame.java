/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package labmate;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import labmate.model.Entry;
import labmate.model.Project;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXEditorPane;

public class LabMateMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JXDatePicker datePicker = new JXDatePicker();
	JComboBox projectSelector = new JComboBox();
	JButton projectAddButton = new JButton("+");
	JXEditorPane editor = new JXEditorPane();

	Entry currentEntry = null;

	List<Project> projects;

	public LabMateMainFrame() {
		super("LabMate");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel(new BorderLayout(2, 2));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		topPanel.add(new JLabel("LabMate"));
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(projectSelector);
		topPanel.add(projectAddButton);
		topPanel.add(datePicker);

		mainPanel.add(topPanel, BorderLayout.PAGE_START);
		JScrollPane scroller = new JScrollPane(editor);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		mainPanel.add(scroller, BorderLayout.CENTER);

		getContentPane().add(mainPanel);
		getContentPane().setPreferredSize(new Dimension(800, 500));

		projectSelector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				changeProject((Project) projectSelector.getSelectedItem());
			}
		});

		projectAddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addProject();
			}
		});

		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dateChange();
			}
		});

		editor.setMargin(new Insets(5, 5, 5, 5));

		pack();
	}

	private void dateChange() {
		Date picked = datePicker.getDate();
		Date limit = new Date(picked.getTime() + 24 * 60 * 60 * 1000);

		for (Entry e : ((Project) projectSelector.getSelectedItem())
				.getEntries()) {
			if ((e.getDate().equals(picked) || e.getDate().after(picked))
					&& e.getDate().before(limit)) {
				if (!editor.getText().trim().equals("") && currentEntry != null) {
					currentEntry.setText(editor.getText());
				}
				
				if (e.getDate().before(getTodayStart())) {
					editor.setEditable(false);
				}
				else {
					editor.setEditable(true);
				}
				editor.setText(e.getText());
				currentEntry = e;
				break;
			}
		}
	}

	private void changeProject(Project newProject) {
		if (!editor.getText().trim().equals("") && currentEntry != null) {
			currentEntry.setText(editor.getText());
		}
		currentEntry = newProject.getTodaysEntry();
		editor.setText(currentEntry.getText());
		projectSelector.setSelectedItem(newProject);

		List<Date> dates = new ArrayList<Date>();
		for (Entry e : newProject.getEntries()) {
			dates.add(e.getDate());
		}
		datePicker.getMonthView().setFlaggedDates(dates.toArray(new Date[0]));
	}

	private void addProject() {
		String projectName = JOptionPane.showInputDialog("Project Title");
		if (projectName == null || projectName.trim().equals("")) {
			return;
		}
		Project p = new Project(projectName);
		projects.add(p);
		projectSelector.addItem(p);
		changeProject(p);
	}

	static Timer timer = new Timer();

	public static void main(String[] args) {
		final LabMateMainFrame frame = new LabMateMainFrame();
		frame.load();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});

		TimerTask ts = new TimerTask() {
			public void run() {
				frame.save();
			}
		};
		timer.schedule(ts, 5000, 5000);
	}

	private static final String SAVE_FILE = System.getProperty("user.home")
			+ File.separator + ".labmate.oos";

	@SuppressWarnings("unchecked")
	private void load() {
		try {
			Project lastProject = null;
			Date lastEntryDate = new Date(0);

			Object o = null;

			if (new File(SAVE_FILE).exists()) {
				ObjectInputStream ois = new ObjectInputStream(
						new FileInputStream(SAVE_FILE));
				o = ois.readObject();
				ois.close();
			}

			if (o == null) {
				projects = new ArrayList<Project>();
				addProject();
				changeProject(projects.get(0));
				return;
			}

			if (o instanceof List) {
				projects = (List<Project>) o;
			}
			
			for (Project p : projects) {
				projectSelector.addItem(p);
				for (Entry e : p.getEntries()) {
					if (e.getDate().after(lastEntryDate)) {
						lastProject = p;
						lastEntryDate = e.getDate();
					}
				}
			}

			if (lastProject != null) {
				changeProject(lastProject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Date getTodayStart() {
		Calendar date = new GregorianCalendar();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

	synchronized private void save() {
		try {
			String content = editor.getText();

			if (!content.trim().equals("") && currentEntry != null) {
				currentEntry.setText(content);
			}
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(SAVE_FILE));
			oos.writeObject(projects);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
