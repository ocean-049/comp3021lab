package base;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Collections;
import java.util.List;

public class NoteBook implements java.io.Serializable{
	
	private  ArrayList<Folder> folders;
	
	private static final long serialVersionUID = 1L;
	
	public NoteBook()
	{
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file)
	{
	    FileInputStream fis = null;
	    ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			NoteBook n = (NoteBook) in.readObject();
			this.folders = n.folders;
			in.close();
		}catch(Exception e) {
			System.out.println("A " + e.getMessage());
		}

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
	
	public boolean createTextNote(String foldername, String title, String content)
	{
		TextNote note = new TextNote(title,content);
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
	
	public void sortFolders() 
	{

		Collections.sort(folders);
	}
	
	public List<Note> searchNotes(String keywords)
	{
		List<Note> searchNote;
		List<Note> completeNote = new ArrayList<Note>();
		for(Folder F : folders)
		{
			searchNote = F.searchNotes(keywords);
			for(int j = 0 ; j < searchNote.size(); j++) {
				completeNote.add(searchNote.get(j));
			}
			
		}
		return completeNote;
	}
	
	public boolean save(String file)
	{
		try {
			FileOutputStream fos = null;
			
			ObjectOutputStream out = null;
			
			fos = new FileOutputStream(file);
			
			out = new ObjectOutputStream(fos);
			
			out.writeObject(this);
			
			out.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	
}
