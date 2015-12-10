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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import infrastructure.Serializer;

public class LiftSim extends Application{

    ArrayList<Lift> lifts = new ArrayList<Lift>();
    final Text message = new Text();
    TextField floorsInput = new TextField("floors");
    VBox vbox;

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
        vbox = new VBox();
        HBox hbox = new HBox();

        Button runBtn = new Button("Run");
        Button saveBtn = new Button("Save");
        Button resetBtn = new Button("Reset");
        Button addLiftBtn = new Button("Add Lift");
        floorsInput = new TextField("floors");

        hbox.getChildren().setAll(runBtn, saveBtn, resetBtn, addLiftBtn, floorsInput);

        vbox.getChildren().setAll(hbox);

        HBox messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_LEFT);
        messageBox.getChildren().add(message);
        vbox.getChildren().addAll(messageBox);

        loadLifts();

        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                message.setText("");

                if (lifts.isEmpty()) {
                    setErrorMessage("No lifts are available");
                    return;
                }

                for (Lift lift : lifts) {
                    lift.run();
                    lift.visualizer.moveElevator();
                    lift.visualizer.updateButtons();
                }
            }
        });

        addLiftBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Lift lift = new Lift( Integer.parseInt(floorsInput.getText()) );
                    lifts.add(lift);
                    vbox.getChildren().addAll(lift.visualizer.visualize());
                    setSuccessMessage("Lift Added");
                } catch (NumberFormatException e) {
                    setErrorMessage("Please enter an integer");
                }
            }
        });

        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Serializer.save(lifts);
                setSuccessMessage("Saved!");
            }
        });

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (Lift lift : lifts)
                {
                    vbox.getChildren().remove(lift.visualizer.grid);
                }

                lifts.clear();

                Serializer.clear();
                setSuccessMessage("Reset!");
            }
        });

        // set scene
        Scene scene = new Scene(vbox, 600, 500);
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

    private void setErrorMessage(String text)
    {
        message.setFill(Color.FIREBRICK);
        message.setText(text);
    }

    private void setSuccessMessage(String text)
    {
        message.setFill(Color.GREEN);
        message.setText(text);
    }


    /**
     * Loads saved lifts
     */
    private void loadLifts() {

        ArrayList<Lift> loadedLifts = (ArrayList<Lift>) Serializer.load();

        if (loadedLifts != null)
        {
            lifts = loadedLifts;

            for(Lift lift : lifts)
            {
                vbox.getChildren().addAll(lift.visualizer.visualize());
                lift.visualizer.updateButtons();
            }
        }
    }

}

