package base;
import java.io.File;

public class ImageNote extends Note implements java.io.Serializable {
	
	public File image;
	
	private static final long serialVersionUID = 1L;
	
	public ImageNote(String title)
	{
		super(title);
	}
	
	
}
