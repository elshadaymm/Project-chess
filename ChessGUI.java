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
    //Canvas canvas = new Canvas();
    //GraphicsContext board = canvas.GraphicsContext2D();

    Rectangle rect1 = new Rectangle(10, 10, 65, 65);
    rect1.setFill(Color.BLUE);
    root.getChildren().add(rect1);

	Rectangle chessBoard = new Rectangle(10,90,580,580);
    chessBoard.setFill(Color.BLACK);
    root.getChildren().add(chessBoard);


    Scene scene = new Scene(root, 780, 780);
    primaryStage.setTitle("Chess Game");
    primaryStage.setScene(scene);
    primaryStage.show();


  }
}
