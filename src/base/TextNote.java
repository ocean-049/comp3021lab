package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;




public class TextNote extends Note implements java.io.Serializable {
	
	public String content;	
	private static final long serialVersionUID = 1L;
	
	public TextNote(String title)
	{
		super(title);
	}
	
	public TextNote(String title, String content)
	{
		super(title);
		this.content = content;
	}
	
	public TextNote(File f)
	{
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	
	private String getTextFromFile(String absolutePath) {
		String result = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		int i;
		
		try {
			fis = new FileInputStream(absolutePath);
			isr = new InputStreamReader(fis);
			
			while((i = isr.read()) != -1)
			{
				result += (char) i;
			}
			if(fis!= null)
				fis.close();
			if(isr!= null)
				isr.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} return result;
		
	}
	
	public void exportTextToFile(String pathFolder) 
	{
		String Title = this.getTitle().replaceAll(" ", "_").toLowerCase();
		
		
		
		File file = new File( Title + ".txt");
		//pathFolder + File.separator + Title + ".txt"

		if(file.exists()) {
			System.out.println("file already created");

		}
		try {
			
			if(file.createNewFile())
			{
				System.out.println("Created file");
			}else {
				System.out.println("file existed?");
			}
			
			FileWriter writer = new FileWriter(file);
			
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
