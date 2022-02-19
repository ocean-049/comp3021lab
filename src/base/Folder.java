package base;

import java.util.ArrayList;
public class Folder {

	private ArrayList<Note> notes;
	
	private String name;
	
	public Folder(String name)
	{
		this.name = name;
		notes = new ArrayList<Note>();
	}
	public void addNote(Note note)
	{
		notes.add(note);
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<Note> getNotes()
	{
		return notes;
	}
	public String toString()
	{
		int nText = 0;
		int nImage = 0;
		
		for (Note N : notes)
		{
			if(N instanceof TextNote ) 
			{
				nText += 1;
			}else if(N instanceof ImageNote)
			{
				nImage += 1;
			}
		}
		return name + ":" + nText + ":" + nImage;
		
	}
	public boolean equals(Folder object)
	{
		return name == object.name;
	}
	
}
