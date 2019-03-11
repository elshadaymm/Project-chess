import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

  private static Game game = new Game();
  private static Player playerWhite = new AIMinMax(game);
  private static Player playerBlack = new AIRandom(game);

  public static void main(String[] args){
     launch(args);}
  
	public void drawBoard(GridPane b){
		GameHelper.printState(game);
		for(int i = game.getRankSize() -1; i >= 0; i--) {
			for(int j = game.getFileSize() -1; j >= 0; j--) {

				boolean pieceColor = game.getPiece(i, j).getColor();
				String picture;
				switch (game.getPiece(i,j).getType()) {
					case King:
						picture = pieceColor ? "/pictures/80/WhiteKing.png" : "/pictures/80/BlackKing.png";
						break;
					case Queen:
						picture = pieceColor ? "/pictures/80/WhiteQueen.png" : "/pictures/80/BlackQueen.png";
						break;
					case Rook:
						picture = pieceColor ? "/pictures/80/WhiteRook.png" : "/pictures/80/BlackRook.png";
						break;
					case Bishop:
						picture = pieceColor ? "/pictures/80/WhiteBishop.png" : "/pictures/80/BlackBishop.png";
						break;
					case Knight:
						picture = pieceColor ? "/pictures/80/WhiteKnight.png" : "/pictures/80/BlackKnight.png";
						break;
					case Pawn:
						picture = pieceColor ? "/pictures/80/WhitePawn.png" : "/pictures/80/BlackPawn.png";
						break;
					default:
						picture = null;
						break;}
					if(picture == null) continue;
					Image pic = new Image(picture);
					ImageView toPlace = new ImageView(pic);
					toPlace.setPreserveRatio(true);
					b.add(toPlace, j, 7 - i, 1, 1);}  //7 - i is what makes the GridPane start from the bottom left and not top left
			}
	}
	
	public void baseBoard(GridPane board){
		for(int i=0; i<8; i++) {
				for(int j=0; j<8; j++) {
					int check_1= i+j;
					Rectangle square_s = new Rectangle(0, 0,80,80);
					if (check_1%2==0) {
						square_s.setFill(Color.WHITE);} 
					else {
						square_s.setFill(Color.SILVER);}
					board.add(square_s, i, j, 1, 1);}
		}
	}
	 
	public void update(GridPane board){
		baseBoard(board);
		drawBoard(board);
	}
 
  public void start(Stage primaryStage) throws Exception{
	  Pane root = new Pane();
	  
	  //VBox for the right side with the info diplays and the input.  The HBox is a inside this VBox.
	  VBox infoDisplay = new VBox();
		infoDisplay.getChildren().add(new Label("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn."));
		//whose turn will go here.
		infoDisplay.getChildren().add(new Label("Turn: " + game.getTurn()));
		//Turn Number will go here.
		infoDisplay.getChildren().add(new Label("Fifty-move Rule: " + game.getPeace()));
		//50 Move will go here
		infoDisplay.getChildren().add(new Label("FEN: " + GameHelper.toFEN(game)));
		infoDisplay.setPadding(new Insets(200,100,20,100));
		infoDisplay.setSpacing(10);
		infoDisplay.setLayoutX(600);
		infoDisplay.setLayoutY(100);
		
		//Gets put into the VBox as the last element
		HBox userInput = new HBox();
		userInput.getChildren().add(new Label("Input Move: "));
		TextField txtName = new TextField();
		txtName.setPrefWidth(150);
		userInput.getChildren().add(txtName);
		userInput.setPadding(new Insets(0,0,40,50));
		Button submit = new Button("Submit");
		userInput.getChildren().add(submit);
		infoDisplay.getChildren().add(userInput);
		
		GridPane board = new GridPane();
		board.setLayoutX(25);
		board.setLayoutY(25);
		
		for(int i=0; i<8; i++) {
					for(int j=0; j<8; j++) {
						int check_1= i+j;
						Rectangle square_s = new Rectangle(0, 0,80,80);
						if (check_1%2==0) {
							square_s.setFill(Color.WHITE);} 
						else {
							square_s.setFill(Color.SILVER);}
						board.add(square_s, i, j, 1, 1);}
		}

	
		drawBoard(board);
		
		//Button Action Handler
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				String moveInput = txtName.getText();
				if(playerWhite.move()){
						
					playerBlack.move();
					baseBoard(board);
					drawBoard(board);
				}
			}
		});

	
	
		root.getChildren().add(infoDisplay);
		root.getChildren().add(board);
		int h = 700;
		int w = (int) (h * 1.6);
		Scene scene = new Scene(root, w, h);
		primaryStage.setTitle("Chess Game");
		primaryStage.setScene(scene);
		primaryStage.show();
  }
  
}
