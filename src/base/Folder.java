package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Folder implements Comparable<Folder> {

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
		int Keyword_len_start = 0;
		int Keyword_len_end = -1;
		List<Note> t_notes = new ArrayList<Note>(); 
		String [] keyword_1 = {null,null};
		String []keyword_2 = {null,null};
		
		for(int i = 0; i < 2; i++) {
			
			for(Keyword_len_start = ++Keyword_len_end; Character.isWhitespace(keywords.charAt(Keyword_len_end)) == false && Keyword_len_end<keywords.length(); Keyword_len_end++ )
			{
				;
			}
			keyword_1[i] = keywords.substring(Keyword_len_start, Keyword_len_end);
			
			
			for(Keyword_len_start = ++Keyword_len_end; Character.isWhitespace(keywords.charAt(Keyword_len_end)) == false && Keyword_len_end<keywords.length(); Keyword_len_end++ )
			{
				;
			}
			
			if( Keyword_len_end < keywords.length())	
			for(Keyword_len_start = ++Keyword_len_end;  Keyword_len_end <keywords.length(); Keyword_len_end++ )
			{
				if(Character.isWhitespace(keywords.charAt(Keyword_len_end)))
					break;
			}
			keyword_2[i] = keywords.substring(Keyword_len_start, Keyword_len_end);
				

			
			

			
		}
		
		for(int k = 0; k < 2; k++)
		{
			keyword_1[k] = keyword_1[k].toLowerCase();
			keyword_2[k] = keyword_2[k].toLowerCase();
		}

		for(Note N : notes)
		{
			if(N instanceof TextNote )
			{	
				TextNote t_TextNote = (TextNote)N;

				String content = t_TextNote.content.toLowerCase();
				String title = t_TextNote.getTitle().toLowerCase();
				if(((content.contains(keyword_1[0]) || content.contains(keyword_2[0])) && (content.contains(keyword_1[1]) || content.contains(keyword_2[1]))) || ((title.contains(keyword_1[0]) || title.contains(keyword_2[0])) && (title.contains(keyword_1[1]) || title.contains(keyword_2[1]))) )
				{
					t_notes.add(t_TextNote);
				}

			}
			else if(N instanceof ImageNote)
			{
				ImageNote t_ImageNote = (ImageNote)N;
				String title = t_ImageNote.getTitle().toLowerCase();
				
				if(title.contains(keyword_1[0]) || title.contains(keyword_2[0]) && title.contains(keyword_1[1]) || title.contains(keyword_2[1])) 
				{
					t_notes.add(t_ImageNote);
				}
			}
		}
		return t_notes;
	}
	
		
	
	
}
