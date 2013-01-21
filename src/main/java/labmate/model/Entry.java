/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package labmate.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hannes
 */
public class Entry implements Serializable{
	private static final long serialVersionUID = 1L;
	Date date = Calendar.getInstance().getTime();
    String content = "";
    
    public Entry() {
    	
    }
    
    public Entry(Date time, String content2) {
		this.date = time;
		this.content = content2;
	}

	public Date getDate() {
    	return date;
    }

	public String getText() {
		return content;
	}

	public void setText(String content2) {
		content = content2;
	}
}
