package base;
import java.util.Date;

public class Note {

	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		Date date = new Date(System.currentTimeMillis());
	}
	
	public String getTitle() 
	{
		return title;
	}
	

	public boolean equals(Note obj) {
		if (this.title != obj.title)
			return false;
		if (title == null)
		{
			if(obj.title != null)
			return false;
		}
		return true;
		

	}
}
