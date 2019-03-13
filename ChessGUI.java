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

	public void drawEdges(Pane root) {
		int hspace = 67;
		VBox leftNumberEdge = new VBox();
		VBox rightNumberEdge = new VBox();
		HBox topLetterEdge = new HBox();
		HBox bottomLetterEdge = new HBox();

		for (int i = 0; i < 8; i++) {
			char ttemp = (char) ('a' + i);
			String tfile = "" + ttemp;
			Text tfileText = new Text();
			tfileText.setText(tfile);
			tfileText.setFont(Font.font("Verdana", 20));
			topLetterEdge.getChildren().add(tfileText);
		}
		topLetterEdge.setPrefSize(600, 30);
		topLetterEdge.setLayoutX(88);
		topLetterEdge.setLayoutY(20);
		topLetterEdge.setSpacing(hspace);
		root.getChildren().add(topLetterEdge);

		for (int i = 0; i < 8; i++) {
			char btemp = (char) ('a' + i);
			String bfile = "" + btemp;
			Text bfileText = new Text();
			bfileText.setText(bfile);
			bfileText.setFont(Font.font("Verdana", 20));
			bottomLetterEdge.getChildren().add(bfileText);
		}
		bottomLetterEdge.setPrefSize(560, 30);
		bottomLetterEdge.setLayoutX(88);
		bottomLetterEdge.setLayoutY(690);
		bottomLetterEdge.setSpacing(hspace);
		root.getChildren().add(bottomLetterEdge);

		for (int i = 8; i > 0; i--) {
			int ntemp = (i);
			String number = "" + ntemp;
			Text tempNumber = new Text();
			tempNumber.setText(number);
			tempNumber.setFont(Font.font("Verdana", 20));
			leftNumberEdge.getChildren().add(tempNumber);
		}
		leftNumberEdge.setPrefSize(560, 30);
		leftNumberEdge.setLayoutX(15);
		leftNumberEdge.setLayoutY(85);
		leftNumberEdge.setSpacing(53);
		root.getChildren().add(leftNumberEdge);

		for (int i = 8; i > 0; i--) {
			int ntemp = (i);
			String number = "" + ntemp;
			Text tempNumber = new Text();
			tempNumber.setText(number);
			tempNumber.setFont(Font.font("Verdana", 20));
			rightNumberEdge.getChildren().add(tempNumber);
		}
		rightNumberEdge.setPrefSize(560, 30);
		rightNumberEdge.setLayoutX(705);
		rightNumberEdge.setLayoutY(85);
		rightNumberEdge.setSpacing(53);
		root.getChildren().add(rightNumberEdge);
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
				winnerText.setText("White wins.");
			} else if (winner == 2) {
				winnerText.setText("Black wins.");
			}	else if (winner == 3) {
				winnerText.setText("Draw by fifty-move rule.");
			}	else if (winner == 4) {
				winnerText.setText("Stalemate.");
			}
			winnerText.setFont(Font.font("verdana", 30));
			winnerText.setFill(Color.WHITE);
			endDisplay.getChildren().add(background);
			endDisplay.getChildren().add(winnerText);
			endDisplay.setLayoutX(750);
			endDisplay.setLayoutY(10);
			root.getChildren().add(endDisplay);
		}

		
	  drawBoard(board);
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
		infoDisplay.setLayoutX(750);
		infoDisplay.setLayoutY(100);

		//Gets put into the VBox as the last element
		HBox userInput = new HBox();
		userInput.getChildren().add(new Label("Input Move of Format \"a1h8\": "));
		TextField txtName = new TextField();
		txtName.setPrefWidth(50);
		userInput.getChildren().add(txtName);
		userInput.setPadding(new Insets(0,0,40,50));
		Button submit = new Button("Submit");
		userInput.getChildren().add(submit);
		infoDisplay.getChildren().add(userInput);

		GridPane board = new GridPane();
	  board.setLayoutX(50);
	  board.setLayoutY(50);

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
	  drawEdges(root);

	  //Button Action Handler
		submit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if (game.getEnd() == Constant.ONGOING) {
					String moveInput = txtName.getText();
					moveInput = moveInput + " ";
					if(game.getWhiteTurn()){
						if(playerWhite.move(moveInput))
							if(playerBlack.getKind() != Intelligence.Human)
								if(game.getEnd() == Constant.ONGOING)
									playerBlack.move();
					}else{
						if(playerBlack.move(moveInput))
							if(playerWhite.getKind() != Intelligence.Human)
								if(game.getEnd() == Constant.ONGOING)
									playerWhite.move();
					}
					update(board, root);

					/*
					if(playerBlack.getKind() != Intelligence.Human
						&& playerWhite.getKind() != Intelligence.Human){
							while(game.getEnd() == Constant.ONGOING){
									playerWhite.move();
									update(board, root);
									if(game.getEnd() == Constant.ONGOING){
											playerBlack.move();
											update(board, root);
									}
							}
					}
					*/
				}
			}
		});


		root.getChildren().add(infoDisplay);
		root.getChildren().add(board);
		int h = 750;
		int w = (int) (h * 16 / 9);
		Scene scene = new Scene(root, w, h);
		primaryStage.setTitle("Chess Game");
		primaryStage.setScene(scene);
		primaryStage.show();
  }

}
