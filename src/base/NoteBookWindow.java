package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;	
import javafx.stage.FileChooser;
import java.util.List;
import java.util.Optional;

import base.Folder;
import base.Note;
import base.NoteBook;
import base.TextNote;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * 
	 * Combobox for selecting the folder
	 * 
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	
	Stage stage;

	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		border.setCenter(addGridPane());

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from file");
		buttonLoad.setPrefSize(100, 20);
		buttonLoad.setDisable(false);
		Button buttonSave = new Button("Save from file");
		buttonSave.setPrefSize(100, 20);
		buttonSave.setDisable(false);
		TextField Search_TextField = new TextField();
		Button Search_button = new Button("Search");
		Search_button.setPrefSize(100, 20);
		Button ClearSearch_button = new Button("Clear Search");
		ClearSearch_button.setPrefSize(100, 20);
		
		
		
		
		 buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 FileChooser fileChooser = new FileChooser();
				 fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");
				 
				 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File(*.ser)", "*.ser");
				 fileChooser.getExtensionFilters().add(extFilter);
				 
				 File file = fileChooser.showOpenDialog(stage);
				 
				 if (file != null) {
					 loadNoteBook(file);
				 }
			 }
			 
		 });
		 
		 
		 buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent event) {
				 FileChooser fileChooser = new FileChooser();
				 fileChooser.setTitle("Please Choose An File To Save a NoteBook Object!");
				 
				 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File(*.ser)", "*.ser");
				 fileChooser.getExtensionFilters().add(extFilter);
				 
				 File file = fileChooser.showSaveDialog(stage);
				 
				 saveNoteBook(file);
				 
			 }
		 });
		
		
		Search_button.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event)
			{
				currentSearch = Search_TextField.getText();
				textAreaNote.setText("");
				List<Note> searchedNotes = null;
				for(int i = 0; i < noteBook.getFolders().size(); i++)
				{
					if(noteBook.getFolders().get(i).getName() == currentFolder)
					{
						searchedNotes = noteBook.getFolders().get(i).searchNotes(currentSearch);
						
					} 
				}
				updateListView(searchedNotes);
			}
			
		});
		
		ClearSearch_button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event)
			{
				currentSearch = "";
				Search_TextField.setText("");
				textAreaNote.setText("");
				updateListView();
			}
		});
		
		
		
		

		hbox.getChildren().addAll(buttonLoad, buttonSave, new Label("Search : "), Search_TextField, Search_button, ClearSearch_button );

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes
		
		HBox hbox_2 = new HBox();
		hbox_2.setPadding(new Insets(10));
		hbox_2.setSpacing(8);
		
		Button buttonAddFolder = new Button("Add a Folder");
		buttonAddFolder.setPrefSize(100, 20);
		buttonAddFolder.setDisable(false);
		
		Button buttonAddNote = new Button("Add a Note");
		buttonAddFolder.setPrefSize(100, 20);
		buttonAddFolder.setDisable(false);
		
		
		
		buttonAddFolder.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			    TextInputDialog dialog = new TextInputDialog("Add a Folder");
			    dialog.setTitle("Input");
			    dialog.setHeaderText("Add a new folder for your notebook:");
			    dialog.setContentText("Please enter the name you want to create:");
			    // Traditional way to get the response value.
			    Optional<String> result = dialog.showAndWait();
			    if (result.isPresent()){
			       if(result.isEmpty())
			       {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("Please input an valid folder name");
						alert.showAndWait().ifPresent(rs -> {
						    if (rs == ButtonType.OK) {
						        System.out.println("Pressed OK.");
						    }
						});
			       }else {
			    	   for(int i = 0; i < noteBook.getFolders().size(); i++)
			    	   {
			    		   if( noteBook.getFolders().get(i).getName().equals(result))
			    		   {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Warning");
								alert.setContentText("You already have a folder named with" + result);
								alert.showAndWait().ifPresent(rs -> {
								    if (rs == ButtonType.OK) {
								        System.out.println("Pressed OK.");
								    }
								});
			    		   }
			    	   }
			       }
			    }
			};
		});

		// TODO: This line is a fake folder list. We should display the folders in noteBook variable! Replace this with your implementation
		foldersComboBox.getItems().addAll(noteBook.getFolders().get(0).getName(), noteBook.getFolders().get(1).getName(), noteBook.getFolders().get(2).getName());

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				currentFolder = t1.toString();
				System.out.println("Current folder text is : "+ currentFolder);
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();

			}

		});

		foldersComboBox.setValue("-----");

		titleslistView.setPrefHeight(100);

		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in
				// textAreNote
				String content = "";
				for(int i = 0; i < noteBook.getFolders().size(); i++)
				{

					for(int j = 0; j <noteBook.getFolders().get(i).getNotes().size(); j++ )
					{
						if(noteBook.getFolders().get(i).getNotes().get(j).getTitle() == title)
						{
							content = ((TextNote)noteBook.getFolders().get(i).getNotes().get(j)).content;
						}
					}
						
					
				}

				
				textAreaNote.setText(content);

			}
		});
		vbox.getChildren().add(new Label("Choose folder: "));
		//vbox.getChildren().add(new Label("Choose folder: "));
		hbox_2.getChildren().add(foldersComboBox);
		hbox_2.getChildren().add(buttonAddFolder);
		vbox.getChildren().add(hbox_2);
		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);
		vbox.getChildren().add(buttonAddNote);

		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);m
		for(int i = 0; i < noteBook.getFolders().size(); i++)
		{
			
			if(noteBook.getFolders().get(i).getName().equalsIgnoreCase(currentFolder))
			{	
				
				for(int j = 0; j <noteBook.getFolders().get(i).getNotes().size(); j++ )
				{	
					if(noteBook.getFolders().get(i).getNotes().get(j) instanceof TextNote)
					combox2.add(noteBook.getFolders().get(i).getNotes().get(j).getTitle());
				}
				
			}
		}

		textAreaNote.setText("");
	}
	
	private void updateListView(List<Note> searchedNotes) {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the
		// currentFolder

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		if(searchedNotes == null)
			return;
		for(int i = 0; i < searchedNotes.size(); i++)
		{
	
			if(searchedNotes.get(i) instanceof TextNote)
			combox2.add(((TextNote)searchedNotes.get(i)).getTitle());
				
						
		}
		

		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(false);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 0);

		return grid;
	}

	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called ?the most shocking play in NFL history? and the Washington Redskins dubbed the ?Throwback Special?: the November 1985 play in which the Redskins? Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award?winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything?until it wasn?t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant?a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether?s Daddy Was a Number Runner and Dorothy Allison?s Bastard Out of Carolina, Jacqueline Woodson?s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood?the promise and peril of growing up?and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;

	}
	private void loadNoteBook(File file) {

	    FileInputStream fis = null;
	    ObjectInputStream in = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			noteBook = (NoteBook) in.readObject();
			System.out.println("New NoteBook notes : " + noteBook.getFolders().get(0).getNotes().toString());
			
//			for(Folder f : n.getFolders())
//			noteBook.getFolders().add(f);
//			foldersComboBox.getItems().add(noteBook.getFolders().get(3).getName());
			in.close();
		}catch(Exception e) {
			System.out.println("A " + e.getMessage());
		}
	}
	private void saveNoteBook(File file) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(file);			
			out = new ObjectOutputStream(fos);
			out.writeObject(noteBook);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Successfully saved");
			alert.setContentText("You file has been saved to file "+ file.getName());
			alert.showAndWait().ifPresent(rs -> {
			    if (rs == ButtonType.OK) {
			        System.out.println("Pressed OK.");
			    }
			});
		}catch(Exception e) {
			System.out.println("B " + e.getMessage());
		}
		
	}

}
