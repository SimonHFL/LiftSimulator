import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LiftSim extends Application{

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    /**
     * This method creates a lift that puts all its visual
     * components on primaryStage. When the start method returns, the system
     * will show the lift. Ideally it should be possible to create and run several
     * lifts at the same time.
     *
     * @param primaryStage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane grid = new GridPane();

        Scene scene = new Scene(grid, 300, 275);

        primaryStage.setTitle("Lift");

        primaryStage.setScene(scene);

        //primaryStage.show();

        Lift lift = new Lift(10);
        lift.innerButtons[2].push();
        lift.innerButtons[3].push();
        lift.upButtons[4].push();

        lift.run();

    }

    public static void main(String[] args) {
        launch(args);
    }

    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */
}
