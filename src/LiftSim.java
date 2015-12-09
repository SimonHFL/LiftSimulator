import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LiftSim extends Application{

    int floors = 5;
    Lift lift = new Lift(floors);
    Polygon[] upButtons = new Polygon[floors-1];
    Polygon[] downButtons = new Polygon[floors-1];
    Circle[] innerButtons = new Circle[floors];

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

        // setup grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Button runBtn = new Button("Run");
        grid.add(runBtn, 0, 0);

        // contents
        Text outerButtonsTitle = new Text("Outer Buttons");
        //outerButtonsTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(outerButtonsTitle, 1, 0, 3, 1);

        Text elevatorTitle = new Text("Elevator");
        grid.add(elevatorTitle, 4, 0, 2, 1);

        for(int i =0; i <= floors; i++)
        {
            Text floorNumber = new Text(""+i);
            grid.add(floorNumber, 0, floors-i+1);

            // upButtons
            Polygon upButton = new Polygon();

            upButton.getPoints().addAll(new Double[]{
                    0.0, 10.0,
                    5.0, 0.0,
                    10.0, 10.0});

            grid.add(upButton, 1, floors - i + 1);
            upButtons[i] = upButton;


            // down buttons
            Polygon downButton = new Polygon();

            downButton.getPoints().addAll(new Double[]{
                    0.0, 0.0,
                    10.0, 0.0,
                    5.0, 10.0});

            grid.add(downButton, 2, floors - i + 1);
            downButtons[i] = downButton;

            // inner buttons
            Circle innerButton = new Circle(5.0);
            grid.add(innerButton, 5, floors-i+1);
            innerButtons[i] = innerButton;

        }


        Rectangle elevator = new Rectangle(10.0,10.0);

        grid.add(elevator, 4, floors + 1);

        //actions

        runBtn.setOnAction(new ButtonHandler(lift, runBtn));

        ClickHandler clickHandler = new ClickHandler(lift, elevator);

        elevator.setOnMouseClicked(clickHandler);


        // set scene
        Scene scene = new Scene(grid, 300, 275);

        primaryStage.setTitle("Weather System");

        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);



    }
    }

    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */


