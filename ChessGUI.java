import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
import javafx.scene.text.Text;
import javafx.scene.text.Font;

public class ChessGUI extends Application{

  private static Game game = new Game();
  private static Player playerWhite;
  private static Player playerBlack;
  private static Label whosTurn = new Label("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn.");
  private static Label turnNumber = new Label("Turn: " + game.getTurn());
  private static Label fiftyMove = new Label("Fifty-move Rule: " + game.getPeace());
  private static Label fen = new Label("FEN: " + GameHelper.toFEN(game));

  public static void main(String[] args){
		if(args.length == 2){
			switch (args[0]){
				case "Human": 
					playerWhite = new Human(game);
					break;
				case "Engine": 
					playerWhite = new Engine(game);
					break;
				case "AIMinMax":
					playerWhite = new AIMinMax(game);
					break;
				case "AIAlphaBeta":
					playerWhite = new AIAlphaBeta(game);
					break;
				case "AIRandom":
					playerWhite = new AIRandom(game);
					break;
				default:
					playerWhite = new Human(game);
					break;
			}
			switch (args[1]){
				case "Human": 
					playerBlack = new Human(game);
					break;
				case "Engine": 
					playerBlack = new Engine(game);
					break;
				case "AIMinMax":
					playerBlack = new AIMinMax(game);
					break;
				case "AIAlphaBeta":
					playerBlack = new AIAlphaBeta(game);
					break;
				case "AIRandom":
					playerBlack = new AIRandom(game);
					break;
				default:
					playerBlack = new Human(game);
					break;
			}
		}else{
				playerWhite = new Human(game);
				playerBlack = new Human(game);
		}
		 launch(args);
	}

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

  public void update(GridPane board, Pane root){
    baseBoard(board);
    drawBoard(board);
		whosTurn.setText("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn.");
		fen.setText("FEN: " + GameHelper.toFEN(game));
		turnNumber.setText("Turn: " + game.getTurn());
		fiftyMove.setText("Fifty-move Rule: " + game.getPeace());
		int winner = game.getEnd();
		if (winner == 0) {;
		} else {
			StackPane endDisplay = new StackPane();
			Rectangle background = new Rectangle(0, 0, 320, 160);
			background.setFill(Color.BLACK);
			Text winnerText = new Text();
			if (winner == 1) {
				winnerText.setText("WHITE WINS!!!");
			} else if (winner == 2) {
				winnerText.setText("BLACK WINS!!!");
			}	else if (winner == 3) {
				winnerText.setText("50 MOVE DRAW!!!");
			}	else if (winner == 4) {
				winnerText.setText("STALEMATE!!!");
			}
			winnerText.setFont(Font.font("Comic Sans MS", 30));
			winnerText.setFill(Color.WHITE);
			endDisplay.getChildren().add(background);
			endDisplay.getChildren().add(winnerText);
			endDisplay.setLayoutX(700);
			endDisplay.setLayoutY(10);
			root.getChildren().add(endDisplay);
		}
	}

  public void start(Stage primaryStage) throws Exception{
	  Pane root = new Pane();

	  //VBox for the right side with the info diplays and the input.  The HBox is a inside this VBox.
	  VBox infoDisplay = new VBox();
		infoDisplay.getChildren().add(whosTurn);
		infoDisplay.getChildren().add(turnNumber);
		infoDisplay.getChildren().add(fiftyMove);
		infoDisplay.getChildren().add(fen);
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
				if (check_1%2==0) 
					square_s.setFill(Color.WHITE);
				else 
					square_s.setFill(Color.SILVER);
				board.add(square_s, i, j, 1, 1);
			}
	  }


	  drawBoard(board);

	  //Button Action Handler
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if (game.getEnd() == 0) {
				String moveInput = txtName.getText();
				if(playerWhite.move()){
					update(board, root);
					playerBlack.move();
					update(board, root);
				}
			}
		}
		});


		root.getChildren().add(infoDisplay);
		root.getChildren().add(board);
		int h = 700;
		int w = (int) (h * 16 / 9);
		Scene scene = new Scene(root, w, h);
		primaryStage.setTitle("Chess Game");
		primaryStage.setScene(scene);
		primaryStage.show();
  }

}
