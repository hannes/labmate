package labmate.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

/*
 * this is the model for the application and is concerned with the
 * data ONLY !
 */
public class LabNotesModel {
	String fileName = ".labmate.oos";
	ArrayList<Project> projects;
	
	public LabNotesModel() {
		projects = new ArrayList<Project>();
	}
	
	public boolean addProject(String name) {
		if (projects.contains(name)) {
			return false;
		} else {
			projects.add(new Project(name));
			return true;
		}
	}
	
	public ArrayList<String> getProjectNames() {
		ArrayList<String> names = new ArrayList<String>();
		for (Project p : projects) {
			names.add(p.getName());
		}
		return names;
	}
	
	
	@SuppressWarnings("unchecked") 
	boolean load() {
		if (new File(fileName).exists()) {
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(new FileInputStream(fileName));
				Object o = ois.readObject();
				ois.close();
				
				if (o instanceof ArrayList) {
					projects = (ArrayList<Project>) o;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	
	
	boolean save() {
		boolean flag = false;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(fileName));
			oos.writeObject(projects);
			oos.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	public class Project {
		String name;
		ArrayList<Entry> entries;
		
		public Project(String name) {
			this.name = name;
			entries = new ArrayList<Entry>();
		}
		
		void renameTo(String newName) {
			name = newName;
		}
		
		ArrayList<Entry> getEntries() {
			return entries;
		}
		
		public Entry getEntry(Date date) {
			for (Entry e : entries) {
				if (e.getDate() == date) {
					return e;
				}
			}
			return null;
		}
		
		Entry newEntry(Date date) {
			Entry e = new Entry(date);
			entries.add(e);
			return e;
		}

		public String getName() {
			return name;
		}
	}
	
	public class Entry {
		Date date;
		String text;
		
		public Entry(Date date) {
			this.date = date;
		}
		
		Date getDate() {
			return date;
		}
		
		void setText(String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
		
	}

	public boolean renameProject(String selectedProject, String newName) {

		// don't allow projects with the same name
		for (Project p : projects) {
			if (p.getName().equals(newName)) return false;
		}
		
		// rename
		for (Project p : projects) {
			if (p.getName().equals(selectedProject)) {
				p.renameTo(newName);
			}
		}
		
		return true;
	}

	public Project getProject(String projectName) {
		for (Project p : projects) {
			if (p.getName().equals(projectName)) return p;
		}
		return null;
	}
}
