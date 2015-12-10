import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LiftSim extends Application{

    //int floors = 5;
    ArrayList<Lift> lifts = new ArrayList<Lift>();
    final Text errorMessage = new Text();
    TextField floorsInput = new TextField("floors");


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

        Button runBtn = new Button("Run");

        Button addLiftBtn = new Button("Add Lift");
        floorsInput = new TextField("floors");

        hbox.getChildren().setAll(runBtn, addLiftBtn, floorsInput);

        vbox.getChildren().setAll(hbox);

        HBox errorBox = new HBox(10);
        errorBox.setAlignment(Pos.BOTTOM_LEFT);
        errorBox.getChildren().add(errorMessage);
        vbox.getChildren().addAll(errorBox);

        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(lifts.isEmpty())
                {
                    setErrorMessage("No lifts are available");
                    return;
                }

                for (Lift lift : lifts)
                {
                    lift.run();

                    // move elevator
                    lift.grid.getChildren().remove(lift.elevatorShape);
                    lift.grid.add(lift.elevatorShape, 4, lift.floors + 2 - lift.currentFloor);

                    // set button colors
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

                errorMessage.setText("");

            }
        });

        addLiftBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int floors = Integer.parseInt(floorsInput.getText());
                    Lift lift = new Lift(floors);
                    lifts.add(lift);
                    vbox.getChildren().addAll(lift.grid);
                    errorMessage.setText("");
                } catch (NumberFormatException e) {
                    setErrorMessage("Please enter an integer");
                }
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

    private void setErrorMessage(String message)
    {
        errorMessage.setFill(Color.FIREBRICK);
        errorMessage.setText(message);
    }

}

