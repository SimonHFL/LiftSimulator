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
    TextField floorsInput;
    VBox vbox;
    HBox hbox;
    Button runBtn;
    Button saveBtn;
    Button resetBtn;
    Button addLiftBtn;
    HBox messageBox;

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    @Override
    public void start(Stage primaryStage) throws Exception {

        createView();
        setActions();
        loadLifts();

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

    /**
     * Creates the layout
     */
    private void createView() {
        vbox = new VBox();
        hbox = new HBox();
        runBtn = new Button("Run");
        saveBtn = new Button("Save");
        resetBtn = new Button("Reset");
        addLiftBtn = new Button("Add Lift");
        floorsInput = new TextField("floors");
        messageBox = new HBox(10);
        messageBox.setAlignment(Pos.BOTTOM_LEFT);
        messageBox.getChildren().add(message);

        hbox.getChildren().setAll(runBtn, saveBtn, resetBtn, addLiftBtn, floorsInput);
        vbox.getChildren().setAll(hbox, messageBox);
    }

    /**
     * Sets actions on the elements in the view
     */
    private void setActions() {

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
                    Lift lift = new Lift(Integer.parseInt(floorsInput.getText()));
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
                for (Lift lift : lifts) {
                    vbox.getChildren().remove(lift.visualizer.grid);
                }

                lifts.clear();

                Serializer.clear();
                setSuccessMessage("Reset!");
            }
        });
    }

    /**
     * Sets the message to a custom text and changes the color to red
     *
     * @param text
     */
    private void setErrorMessage(String text)
    {
        message.setFill(Color.FIREBRICK);
        message.setText(text);
    }

    /**
     * Sets the message to a custom text and changes the color to green
     * @param text
     */
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

