package main;

import game.*;
import ai.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.application.Platform;

import java.io.*;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Timer;
import java.util.TimerTask;

public class ChessGUI extends Application {
	private static Game game = new Game();
	private static Player playerWhite;
	private static Player playerBlack;
	private static Label whosTurn = new Label("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn.");
	private static Label turnNumber = new Label("Turn: " + game.getTurn());
	private static Label fiftyMove = new Label("Fifty-move Rule: " + game.getPeace());
	private static Label fen = new Label("FEN: " + GameInfo.toFEN(game));
	private static Label whiteTime = new Label("White Time: " + (game.getClock().getWhiteTime() / 1000) + " seconds");
	private static Label blackTime = new Label("Black Time: " + (game.getClock().getBlackTime() / 1000) + " seconds");
	private static Label repetition = new Label("Repetition: " + GameHelper.repetition(game));
	private static Label whiteClockDisplay = new Label(game.getClock().whiteTime());
	private static Label blackClockDisplay = new Label(game.getClock().blackTime());
	private static Scene startScene, mainScene;
	private static int fromX;
	private static int fromY;
	private static int toX;
	private static int toY;
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		// start of main scene
		Pane root = new Pane();

		// VBox for the right side with the info diplays and the input. The HBox is a
		// inside this VBox.
		VBox infoDisplay = new VBox();

		// the FischerClock display
		StackPane clock = new StackPane();

		Label blackLabel = new Label("Black Time Remaining:");
		Label whiteLabel = new Label("White Time Remaining:");
		HBox clockLabel = new HBox(whiteLabel, blackLabel);
		clockLabel.setSpacing(120);
		clockLabel.setAlignment(Pos.CENTER);

		infoDisplay.getChildren().add(clockLabel);

		HBox chessClock = new HBox();
		chessClock.setAlignment(Pos.CENTER);
		chessClock.setLayoutX(750);
		chessClock.setLayoutY(180);
		Rectangle blackBackground = new Rectangle(0, 0, 240, 80);
		blackBackground.setFill(Color.BLACK);
		Rectangle whiteBackground = new Rectangle(0, 1, 240, 80);
		whiteBackground.setFill(Color.WHITE);
		chessClock.getChildren().add(whiteBackground);
		chessClock.getChildren().add(blackBackground);
		clock.getChildren().add(chessClock);

		whiteClockDisplay.setTextFill(Color.BLACK);
		whiteClockDisplay.setFont(Font.font("verdana", 40));
		blackClockDisplay.setTextFill(Color.WHITE);
		blackClockDisplay.setFont(Font.font("verdana", 40));
		HBox timeText = new HBox(whiteClockDisplay, blackClockDisplay);
		timeText.setSpacing(150);
		timeText.setAlignment(Pos.CENTER);
		timeText.setLayoutX(900);
		timeText.setLayoutY(300);
		clock.getChildren().add(timeText);
		infoDisplay.getChildren().add(clock);

		// text displays
		infoDisplay.getChildren().add(whosTurn);
		infoDisplay.getChildren().add(turnNumber);
		infoDisplay.getChildren().add(fiftyMove);
		infoDisplay.getChildren().add(fen);
		infoDisplay.getChildren().add(repetition);
		infoDisplay.setPadding(new Insets(190, 100, 20, 50));
		infoDisplay.setSpacing(10);
		infoDisplay.setLayoutX(750);
		infoDisplay.setLayoutY(50);

		// Gets put into the VBox as the last element
		HBox userInput = new HBox();
		userInput.getChildren().add(new Label("Input Move of Format \"a1h8\": "));
		TextField move = new TextField();
		move.setPrefWidth(50);
		userInput.getChildren().add(move);
		Button submit = new Button("Turn");
		userInput.getChildren().add(submit);
		infoDisplay.getChildren().add(userInput);

		// load game button
		HBox loadGame = new HBox();
		TextField FEN = new TextField();
		FEN.setPrefWidth(400);
		loadGame.getChildren().add(FEN);
		Button loadString = new Button("Load from FEN");
		loadGame.getChildren().add(loadString);
		infoDisplay.getChildren().add(loadGame);

		HBox newGame = new HBox(10);
		Button newStandard = new Button("New Game");
		newGame.getChildren().add(newStandard);
		Button newFischerRandom = new Button("New Fischer Random Game");
		newGame.getChildren().add(newFischerRandom);
		Button save = new Button("Save Game");
		newGame.getChildren().add(save);
		Button load = new Button("Load Game");
		newGame.getChildren().add(load);
		infoDisplay.getChildren().add(newGame);

		HBox backToMenu = new HBox();
		Button resetGame = new Button("Main Menu");
		backToMenu.getChildren().add(resetGame);
		infoDisplay.getChildren().add(backToMenu);

		// the board
		GridPane board = new GridPane();
		board.setLayoutX(50);
		board.setLayoutY(50);
		
		board.setOnMouseReleased(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				toX=(int)(event.getX());
				toY=(int)(event.getY());
				toX=toX/80;
				toY=toY/80;
				if (toX==8 || toY==8) {
					toX=7;
					toY=7;
				}
				
				String moveInput = "" + (char)('a' + fromX) + (8 - fromY) + (char)('a' + toX) + (8 - toY);
				if (game.getWhiteTurn()) {
					if (playerWhite.move(moveInput))
						if (playerBlack.getKind() != Intelligence.Human)
							if (game.getEnd() == Constant.ONGOING)
								playerBlack.move();
				} else {
					if (playerBlack.move(moveInput))
						if (playerWhite.getKind() != Intelligence.Human)
							if (game.getEnd() == Constant.ONGOING)
								playerWhite.move();
				}
				update(board, root);
			}
		});
		
		/**
		 * Creates the squares for displaying valid moves for the selected pieces
		 */
		board.setOnMousePressed(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				fromX=(int)(event.getX());
				fromY=(int)(event.getY());
				fromX=fromX/80;
				fromY=fromY/80;
				Rectangle selected = new Rectangle(1,1,77,77);
				if (fromX==8 || fromY==8) {
					fromX=7;
					fromY=7;
				}

				selected.setFill(Color.TRANSPARENT);
				selected.setStrokeWidth(2.8);
				selected.setStroke(Color.BLACK);
				board.add(selected,fromX,fromY);
				Object[] moves=(game.getPiece(7-fromY,fromX).legalMoves(game, new Cord(7-fromY,fromX))).toArray();
				for(int i=0;i<moves.length;i++) {
					String e=moves[i].toString();
					Rectangle validSq = new Rectangle(1,1,77,77);
					validSq.setFill(Color.GREEN);
					validSq.setStrokeWidth(2.8);
					validSq.setStroke(Color.BLACK);
					board.add(validSq,Converter.stringToCord(e).file(),7-Converter.stringToCord(e).rank());
				}
				
			}
		});
		
		
		baseBoard(board);
		drawBoard(board);
		drawEdges(root);

		/**
		 * The button used to submit the move typed in manually
		 */
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (game.getEnd() == Constant.ONGOING) {
					String moveInput = move.getText();
					moveInput = moveInput + " ";
					if (game.getWhiteTurn()) {
						if (playerWhite.move(moveInput))
							if (playerWhite.getKind() == Intelligence.Human && playerBlack.getKind() != Intelligence.Human)
								if (game.getEnd() == Constant.ONGOING)
									playerBlack.move();
					} else {
						if (playerBlack.move(moveInput))
							if (playerBlack.getKind() == Intelligence.Human && playerWhite.getKind() != Intelligence.Human)
								if (game.getEnd() == Constant.ONGOING)
									playerWhite.move();
					}
					update(board, root);

					move.clear();
				}
			}
		});

		/**
		 * Loads the game from a FEN String
		 */
		loadString.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.importGame(FEN.getText());
				update(board, root);

				startTimer();
				game.getClock().startClock();
			}
		});

		/**
		 * Resets the game
		 */
		newStandard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.reset();
				update(board, root);

				startTimer();
				game.getClock().startClock();
			}
		});

		/**
		 * Resets game to fischer random
		 */
		newFischerRandom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.importGame(GameHelper.newFischerRandom());
				update(board, root);

				startTimer();
				game.getClock().startClock();
			}
		});

		/**
		 * Save a game to text file using Save button
		 */
		save.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();

			// Sets FileChooser to only save .txt files
			FileChooser.ExtensionFilter chessOnly = new FileChooser.ExtensionFilter("Chess Files (*.chess)", "*.chess");
			fileChooser.getExtensionFilters().add(chessOnly);

			// popup window to browse where to save the game
			File saveFile = fileChooser.showSaveDialog(primaryStage);

			if (saveFile != null) {
				saveTextToFile(GameInfo.toFEN(game), saveFile);
			}
		});

		/**
		 * Load a game from text file using Load button
		 */
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();

				// set to only load txt files
				FileChooser.ExtensionFilter onlyLoadChess = new FileChooser.ExtensionFilter("Chess Files (*.chess)",
						"*.chess");
				fileChooser.getExtensionFilters().add(onlyLoadChess);

				// display window to find file to load
				File loadFile = fileChooser.showOpenDialog(primaryStage);
				if (loadFile != null) {
					game.importGame(readFile(loadFile));
					update(board, root);
					game.getClock().startClock();
				}
			}
		});

		/**
		 * Button to reset the game
		 */
		resetGame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				game.reset();
				update(board, root);
				primaryStage.setScene(drawStartingPage(primaryStage, startScene));
			}
		});

		root.getChildren().add(infoDisplay);
		root.getChildren().add(board);
		int h = 750;
		int w = (int) (h * 16 / 9);
		mainScene = new Scene(root, w, h);
		primaryStage.setTitle("Chess Game");
		primaryStage.setScene(drawStartingPage(primaryStage, startScene));
		primaryStage.show();
		update(board, root);

	}

	/**
	 * Draws the numbers and letters around the edge of the board
	 */
	public void drawEdges(Pane root) {
		int hspace = 67;
		VBox leftNumberEdge = new VBox();
		VBox rightNumberEdge = new VBox();
		HBox topLetterEdge = new HBox();
		HBox bottomLetterEdge = new HBox();

		for (int i = 0; i < game.getFileSize(); i++) {
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

		for (int i = 0; i < game.getFileSize(); i++) {
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

		for (int i = game.getRankSize(); i > 0; i--) {
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

		for (int i = game.getRankSize(); i > 0; i--) {
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

	/**
	 * saves the information to a .chess file
	 */
	public void saveTextToFile(String content, File file) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(file);
			writer.println(content);
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(ChessGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * loads the .chess file to a String
	 */
	private String readFile(File file) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {

			bufferedReader = new BufferedReader(new FileReader(file));

			String text;
			while ((text = bufferedReader.readLine()) != null) {
				stringBuffer.append(text);
			}

		} catch (FileNotFoundException ex) {
			Logger.getLogger(ChessGUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ChessGUI.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException ex) {
				Logger.getLogger(ChessGUI.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * Draws the squares for the board in the display
	 */
	public void baseBoard(GridPane board) {
		for (int i = 0; i < game.getRankSize(); i++) {
			for (int j = 0; j < game.getFileSize(); j++) {
				int check_1 = i + j;
				Rectangle square_s = new Rectangle(0, 0, 80, 80);
				if (check_1 % 2 == 0) {
					square_s.setFill(Color.WHITE);
				} else {
					square_s.setFill(Color.SILVER);
				}
				board.add(square_s, i, j, 1, 1);
			}
		}
	}

	/**
	 * Adds the pictures of the pieces to the board
	 */
	public void drawBoard(GridPane b) {
		GameInfo.printState(game);
		for (int i = game.getRankSize() - 1; i >= 0; i--) {
			for (int j = game.getFileSize() - 1; j >= 0; j--) {

				boolean pieceColor = game.getPiece(i, j).getColor();
				String base = "file:../pictures/80/";
				String picture;
				switch (game.getPiece(i, j).getType()) {
				case King:
					picture = pieceColor ? "WhiteKing.png" : "BlackKing.png";
					break;
				case Queen:
					picture = pieceColor ? "WhiteQueen.png" : "BlackQueen.png";
					break;
				case Princess:
					picture = pieceColor ? "WhitePrincess.png" : "BlackPrincess.png";
					break;
				case Rook:
					picture = pieceColor ? "WhiteRook.png" : "BlackRook.png";
					break;
				case Bishop:
					picture = pieceColor ? "WhiteBishop.png" : "BlackBishop.png";
					break;
				case Knight:
					picture = pieceColor ? "WhiteKnight.png" : "BlackKnight.png";
					break;
				case Pawn:
					picture = pieceColor ? "WhitePawn.png" : "BlackPawn.png";
					break;
				default:
					picture = null;
					break;
				}
				if (picture == null)
					continue;
				Image pic = new Image(base + picture);
				ImageView toPlace = new ImageView(pic);
				toPlace.setPreserveRatio(true);
				b.add(toPlace, j, 7 - i, 1, 1);
			} // 7 - i is what makes the GridPane start from the bottom left and not top left
		}
	}

	/**
	 * Updates the display of the board
	 */
	public void update(GridPane board, Pane root) {
		baseBoard(board);
		whosTurn.setText("Currently " + GameHelper.turnToString(game.getWhiteTurn()) + "'s turn.");
		turnNumber.setText("Turn: " + game.getTurn());
		fiftyMove.setText("Fifty-move Rule: " + game.getPeace());
		fen.setText("FEN: " + GameInfo.toFEN(game));
		whiteTime.setText("White Time: " + (game.getClock().getWhiteTime() / 1000) + " seconds");
		blackTime.setText(("Black Time: " + (game.getClock().getBlackTime() / 1000) + " seconds"));
		repetition.setText("Repetition: " + GameHelper.repetition(game));

		StackPane endDisplay = new StackPane();
		Rectangle background = new Rectangle(0, 0, 320, 80);
		background.setFill(Color.BLACK);
		Text winnerText = new Text();
		String win;
		switch (game.getEnd()){
			case Constant.ONGOING:
				win = "Ongoing";
				break;
			case Constant.WHITE_WIN:
				win = "White wins.";
				break;
			case Constant.BLACK_WIN:
				win = "Black wins.";
				break;
			case Constant.DRAW_BY_FIFTY_MOVE_RULE:
				win = "Draw by 50 move";
				break;
			case Constant.STALEMATE:
				win = "Stalemate";
				break;
			case Constant.WHITE_TIMEOUT:
				win = "White Timeout";
				break;
			case Constant.BLACK_TIMEOUT:
				win = "Black Timeout";
				break;
			case Constant.DRAW_BY_THREEFOLD_REPETITION:
				win = "Draw by 3-fold rep";
				break;
			default:
				win = "Error";
				break;
		}
		winnerText.setText(win);
		winnerText.setFont(Font.font("verdana", 20));
		winnerText.setFill(Color.WHITE);
		endDisplay.getChildren().add(background);
		endDisplay.getChildren().add(winnerText);
		endDisplay.setLayoutX(750);
		endDisplay.setLayoutY(10);
		root.getChildren().add(endDisplay);
		
		drawBoard(board);
	}

	/**
	 * Updates the display of the Fischer Clock in the GUI
	 */
	public void updateClock(){
		if(game.getEnd() == Constant.ONGOING){
			whiteClockDisplay.setText(game.getClock().whiteTime());
			blackClockDisplay.setText(game.getClock().blackTime());
		}
	}

	/**
	 * Starts the Fischer Clock
	 */
	public void startTimer() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Platform.runLater(() -> {
					updateClock();
				});
			}
		}, 1000, 1000);
	}

	/**
	 * Functin to draw the main menu
	 */
	public Scene drawStartingPage(Stage primaryStage, Scene s){
		Pane surface = new Pane();

		HBox title = new HBox();
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(100,0,0,550));
		Text t = new Text("Chess");
		t.setTextAlignment(TextAlignment.CENTER);
		t.setFont(Font.font ("Verdana", 80));
		t.setFill(Color.BLACK);
		title.getChildren().add(t);


		VBox modes = new VBox();
		modes.setAlignment(Pos.CENTER);
		Text txt = new Text("Choose Game Mode");
		txt.setTextAlignment(TextAlignment.CENTER);
		txt.setFont(Font.font ("Verdana", 40));
		txt.setFill(Color.BLACK);
		modes.getChildren().add(txt);
		Button hVSh = new Button("Human vs Human");
		Button hVSm = new Button("Human vs AlphaBeta");
		Button mVSr = new Button("AlphaBeta vs MinMax");
		Button rVSr = new Button("Random vs AlphaBeta");
		modes.getChildren().add(hVSh);
		modes.getChildren().add(hVSm);
		modes.getChildren().add(mVSr);
		modes.getChildren().add(rVSr);
		modes.setPadding(new Insets(300,0,0,490));
		modes.setSpacing(10);

		modes.setPrefWidth(350);
		hVSh.setMinWidth(modes.getPrefWidth());
		hVSh.setMinHeight((modes.getPrefWidth() / 9));
		hVSm.setMinWidth(modes.getPrefWidth());
		hVSm.setMinHeight((modes.getPrefWidth() / 9));
		mVSr.setMinWidth(modes.getPrefWidth());
		mVSr.setMinHeight((modes.getPrefWidth() / 9));
		rVSr.setMinWidth(modes.getPrefWidth());
		rVSr.setMinHeight((modes.getPrefWidth() / 9));

		hVSh.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
			playerWhite = new Human(game);
			playerBlack = new Human(game);
			startTimer();
			game.getClock().startClock();
			primaryStage.setScene(mainScene);
		}
		});
		hVSm.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				playerWhite = new Human(game);
				playerBlack = new AIAlphaBeta(game);
				startTimer();
				game.getClock().startClock();
				primaryStage.setScene(mainScene);
			}
		});
		mVSr.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				playerWhite = new AIAlphaBeta(game);
				playerBlack = new AIMinMax(game);
				startTimer();
				game.getClock().startClock();
				primaryStage.setScene(mainScene);
			}
		});
		rVSr.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				playerWhite = new AIRandom(game);
				playerBlack = new AIAlphaBeta(game);
				startTimer();
				game.getClock().startClock();
				primaryStage.setScene(mainScene);
			}
		});

		surface.getChildren().add(title);
		surface.getChildren().add(modes);
		int h = 750;
		int w = (int) (h * 16 / 9);
		s = new Scene(surface, w, h);
		return s;
	}
}