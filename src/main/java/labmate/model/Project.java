/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package labmate.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import labmate.LabMateMainFrame;

public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Entry> entries = new ArrayList<Entry>();
	private String name = "";

	public Project(String projectName) {
		this.name = projectName;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public String toString() {
		return name;
	}

	public void addEntry(Entry todaysEntry) {
		if (todaysEntry != null) {
			entries.add(todaysEntry);
		}
	}

	public Entry getTodaysEntry() {
		for (Entry e : getEntries()) {
			if (e.getDate().after(LabMateMainFrame.getTodayStart())) {
				return e;
			}
		}
		Entry ne = new Entry();
		addEntry(ne);
		return ne;
	}
}
