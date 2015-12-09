import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LiftSim extends Application{

    int floors = 5;
    GridPane grid;
    Lift lift;
    ArrayList<Lift> lifts = new ArrayList<Lift>();
    int startingRow = 0;

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

        VBox vbox = new VBox();

        HBox hbox = new HBox();

        // contents

        Button runBtn = new Button("Run");

        Button addLiftBtn = new Button("Add Lift");

        hbox.getChildren().setAll(runBtn, addLiftBtn);

        vbox.getChildren().setAll(hbox);

        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                for (Lift lift : lifts)
                {
                    lift.run();

                    lift.grid.getChildren().remove(lift.elevatorShape);
                    lift.grid.add(lift.elevatorShape, 4, lift.floors + 2 - lift.currentFloor);

                    for (Polygon upBtnShape : lift.upBtnShapes) {
                        if (lift.upButtons[(int) upBtnShape.getUserData()].on) upBtnShape.setFill(Color.RED);
                        else upBtnShape.setFill(Color.BLACK);

                    }

                    for (Polygon downButton : lift.downBtnShapes) {

                        if (lift.downButtons[(int) downButton.getUserData()].on) downButton.setFill(Color.RED);
                        else downButton.setFill(Color.BLACK);
                    }

                    for (Circle innerButton : lift.innerBtnShapes) {
                        if (lift.innerButtons[(int) innerButton.getUserData()].on) innerButton.setFill(Color.RED);
                        else innerButton.setFill(Color.BLACK);
                    }
                }

            }
        });

        addLiftBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lift lift = new Lift(floors);
                lifts.add(lift);
                vbox.getChildren().addAll(lift.grid);
            }
        });

        // set scene

        Scene scene = new Scene(vbox, 300, 275);
        primaryStage.setTitle("Lift Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

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

