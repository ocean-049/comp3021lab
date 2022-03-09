package base;
import java.util.Date;

public class Note implements Comparable<Note> {

	private Date date;
	private String title;
	
	public Note(String title) {
		this.title = title;
		this.date = new Date(System.currentTimeMillis());
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
	@Override
	public int compareTo(Note o) {

		if(this.date.after(o.date))
		{
			return -1;
		}else {
			return 1;
		}
	}
	
	public String toString()
	{
		return date.toString() + "\t" + title;
	}
}
