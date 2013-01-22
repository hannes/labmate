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
	
	@SuppressWarnings("unchecked") // TODO
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
		
		public Project() {
			entries = new ArrayList<Entry>();
		}
		
		void renameTo(String newName) {
			name = newName;
		}
		
		ArrayList<Entry> getEntries() {
			return entries;
		}
		
		Entry getEntry(Date date) {
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
	}
}
