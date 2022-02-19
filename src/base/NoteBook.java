package base;

import java.util.ArrayList;

public class NoteBook {
	
	private  ArrayList<Folder> folders;
	
	public NoteBook()
	{
		folders = new ArrayList<Folder>();
	}
	
	public boolean createImageNote(String foldername, String title)
	{
		ImageNote note = new ImageNote(title);
		return insertNote(foldername, note);
	}
	
	public boolean createTextNote(String foldername, String title)
	{
		TextNote note = new TextNote(title);
		return insertNote(foldername, note);
	}
	
	public ArrayList<Folder> getFolders()
	{
		return folders;
	}
	
	public boolean insertNote(String foldername, Note note)
	{
		Folder f = null;
		for(Folder F : folders)
		{
			if(F.getName() == foldername)
			{
				f = F;
				break;
			}

		}
		if(f == null) 
		{
			Folder newFolder = new Folder(foldername);
			folders.add(newFolder);	
			f = newFolder;
		}
		for(Note N : f.getNotes())
		{
			if(N.equals(note))
			{
				System.out.println("Creating note " + note.getTitle() + " under folder " + foldername + " failed");
				return false;
			}
		}
		f.addNote(note);
		return true;
		
	}
	
	
}
