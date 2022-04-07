package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Folder implements Comparable<Folder>, java.io.Serializable {

	private ArrayList<Note> notes;
	
	private String name;
	
	private static final long serialVersionUID = 1L;
	
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
	@Override
	public int compareTo(Folder o) 
	{
		return name.compareTo(o.name);
	}
	
	public void sortNotes()
	{
		Collections.sort(notes);
	}
	
	public List<Note> searchNotes(String keywords)
	{

		List<Note> t_notes = new ArrayList<Note>(); 
		String [] keyword_all;
		List<String []> keyword_string = new ArrayList<String[]>() ;
		
		

			
			keyword_all = keywords.split(" ");
			for(int j = 0; j < keyword_all.length; j++)
			{
				String []  keyword  = {keyword_all[j].toLowerCase(), keyword_all[j].toLowerCase() };
				if(keyword_all[j].equalsIgnoreCase("or"))
				{
					keyword_string.remove(keyword_string.size() - 1);
					keyword[0] = keyword_all[j - 1].toLowerCase();
					if(j != keyword_all.length + 1)
					keyword[1] = keyword_all[j + 1].toLowerCase();
					j++;
					
				}
				keyword_string.add(keyword);
			}

			
			
			
			
		
		


		for(Note N : notes)
		{
			if(N instanceof TextNote )
			{	
				TextNote t_TextNote = (TextNote)N;

				String content = t_TextNote.content.toLowerCase();
				String title = t_TextNote.getTitle().toLowerCase();
				boolean matchKeyword = true;
				for(String [] keyword : keyword_string)
				{
					if(title.contains(keyword[0]) || title.contains(keyword[1]) || content.contains(keyword[0]) || content.contains(keyword[1]) )
						;
					else
						matchKeyword = false;
				}
				if(matchKeyword == true)
					t_notes.add(t_TextNote);

			}
			else if(N instanceof ImageNote)
			{
				ImageNote t_ImageNote = (ImageNote)N;
				String title = t_ImageNote.getTitle().toLowerCase();
				boolean matchKeyword = true;
				
				for(String [] keyword : keyword_string)
				{
					if(title.contains(keyword[0]) || title.contains(keyword[1]))
						;
					else
						matchKeyword = false;
				}
				if(matchKeyword == true)
					t_notes.add(t_ImageNote);

			}
		}
		return t_notes;
	}
	
		
	
	
}
