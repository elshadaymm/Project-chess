import java.util.*;

class ChessGUI extends Application{
  public static void main(String[] args)
  {
     launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception{
    Scene scene = new Scene(root, 700, 700);
    primaryStage.setTitle("Chess Game");
    primaryStage.setScene(scene);

    BorderPane root = new BorderPane();


    primaryStage.show();


  }
}
