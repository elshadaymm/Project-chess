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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javax.sound.sampled.AudioFileFormat.Type;

public class ChessGUI extends Application{

  private static Game game = new Game(8, 8);

  public static void main(String[] args)
  {
     launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception{

    BorderPane root = new BorderPane();

    //rightPane: displays info about the game. example whos turn it is.
    VBox rightPane = new VBox();
    rightPane.getChildren().add(new Label("Who's Turn?: " + (game.getWhiteTurn() ? "White" : "Black")));
    //whose turn will go here.
    rightPane.getChildren().add(new Label("Turn Number: " + game.getTurn()));
    //Turn Numberwill go here.
    rightPane.getChildren().add(new Label("Advantage: " + game.getAdvantage()));
    //Advantage will go here
    rightPane.getChildren().add(new Label("50 Move: " + game.getPeace()));
    //50 Move will go here
    rightPane.getChildren().add(new Label("En Passent: " + game.getEnPassant()));
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


	Rectangle chessBoard = new Rectangle(10,90,580,580);
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

    //looping through setting the pieces in place
    for(int i=0; i<8; i++) {
    	for(int j=0; j<8; j++) {
    		boolean pieceColor = (game.getPiece(i, j).getColor() == Constant.WHITE) ? true : false;
    		Type pieceType = getType(game.getPiece(i,j));
    		String picture;
    		switch (pieceType) {
    			case Pawn:
    				picture = pieceColor ? "/pictures/320/WhitePawn.png" : "/pictures/320/BlackPawn.png";
    				break;
    			case Rook:
    				picture = pieceColor ? "/pictures/320/WhiteRook.png" : "/pictures/320/BlackRook.png";
    				break;
    			case Bishop:
    				picture = pieceColor ? "/pictures/320/WhiteBishop.png" : "/pictures/320/BlackBishop.png";
    				break;
    			case Knight:
    				picture = pieceColor ? "/pictures/320/WhiteKnight.png" : "/pictures/320/BlackKnight.png";
    				break;
    			case Queen:
    				picture = pieceColor ? "/pictures/320/WhiteQueen.png" : "/pictures/320/BlackQueen.png";
    				break;
    			case King:
    				picture = pieceColor ? "/pictures/320/WhiteKing.png" : "/pictures/320/BlackKing.png";
    				break;}
    			Image pic = new Image(picture);
    			ImageView toPlace = new ImageView(pic);
    			toPlace.setPreserveRatio(true);
    			toPlace.setX(10+(72.5 * j));
    			toPlace.setY(90+(72.5 * i));
    			root.getChildren().add(toPlace);
    		}
    	}

    submit.setOnAction(new EventHandler<ActionEvent>()
    {
     @Override
     public void handle(ActionEvent event)
     {
       String moveInput = txtName.getText();
       //PASS THE INPUT TO HUMAN CLASS

     }
    }
   );
    
    Scene scene = new Scene(root, 800, 800);
    primaryStage.setTitle("Chess Game");
    primaryStage.setScene(scene);
    primaryStage.show();


  }
}
