import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class ChessGUI extends Application{
  public static void main(String[] args)
  {
     launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception{

    BorderPane root = new BorderPane();

    //rightPane: displays info about the game. example whos turn it is.
    VBox rightPane = new VBox();
    rightPane.getChildren().add(new Label("Who's Turn: "));
    //whose turn will go here.
    rightPane.getChildren().add(new Label("Turn Number: "));
    //Turn Numberwill go here.
    rightPane.getChildren().add(new Label("Advantage: "));
    //Advantage will go here
    rightPane.getChildren().add(new Label("50 Move: "));
    //50 Move will go here
    rightPane.getChildren().add(new Label("En Passent: "));
    //En Passent will go here
    rightPane.setPadding(new Insets(200,100,20,100));
    rightPane.setSpacing(10);
    root.setRight(rightPane);

    //bottomPane displays a text field to collect input from user
    FlowPane bottomPane = new FlowPane();
    bottomPane.getChildren().add(new Label("Input Move: "));
    TextField txtName = new TextField();
    txtName.setPrefWidth(150);
    bottomPane.getChildren().add(txtName);
    bottomPane.setPadding(new Insets(0,0,40,50));
    Button submit = new Button("Submit");
    bottomPane.getChildren().add(submit);
    root.setBottom(bottomPane);

    //draws the board
    Rectangle outline = new Rectangle(49, 29, 529, 529);
    outline.setStroke(Color.BLACK);
    root.getChildren().add(outline);
    outline.setStrokeWidth(5);

    Rectangle baseLayer = new Rectangle(50, 30, 528, 528);
      baseLayer.setFill(Color.GREY);
      root.getChildren().add(baseLayer);
      int x = 50;
      int y = 30;
      for(int i=0; i<8; i++) {
      	for(int j=0; j<8; j++) {
      		int check_1= i+j;
      		Rectangle square_s = new Rectangle(50+(66*j),30+(66*i),66,66);
      		if (check_1%2==0) {
      			square_s.setFill(Color.WHITE);
      		}
      		else {
      			square_s.setFill(Color.BLACK);
      		}
      		root.getChildren().add(square_s);
      	}
      }

<<<<<<< HEAD
=======
	Rectangle chessBoard = new Rectangle(10,90,580,580);
    chessBoard.setFill(Color.BLACK);
    chessBoard.setStroke(Color.BLACK);
    root.getChildren().add(chessBoard);
    
    for(int i=0; i<8; i++) {
    	for(int j=0; j<8; j++) {
    		int check_1= i+j;
    		Rectangle square_s = new Rectangle(10+(72.5*j),90+(72.5*i),72.5,72.5);
    		if (check_1%2==0) {
    			square_s.setFill(Color.WHITE);	
    		}
    		else {
    			square_s.setFill(Color.BLACK);
    		}
    		root.getChildren().add(square_s);
    	}
    }
>>>>>>> 1bb85fa8e6ac181838c8fc7ae186b5f02f4b8818



    Scene scene = new Scene(root, 800, 650);
    primaryStage.setTitle("Chess Game");
    primaryStage.setScene(scene);
    primaryStage.show();


  }
}
