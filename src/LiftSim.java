import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LiftSim extends Application{

    int floors = 5;
    Lift lift = new Lift(floors);
    Polygon[] upButtons = new Polygon[floors];
    Polygon[] downButtons = new Polygon[floors];
    Circle[] innerButtons = new Circle[floors + 1];

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

        // contents

        Button runBtn = new Button("Run");
        grid.add(runBtn, 0, 0);

        Text outerButtonsTitle = new Text("Outer Buttons");
        grid.add(outerButtonsTitle, 1, 0, 3, 1);

        Text elevatorTitle = new Text("Elevator");
        grid.add(elevatorTitle, 4, 0, 2, 1);

        Rectangle elevator = new Rectangle(10.0,10.0);
        elevator.setFill(Color.RED);
        grid.add(elevator, 4, floors + 1);

        for(int i =0; i <= floors; i++)
        {
            // add the floor number
            Text floorNumber = new Text(""+i);
            grid.add(floorNumber, 0, floors-i+1);

            // add an up button to every floor except the top floor
            if(i<floors)
            {
                Polygon upButton = new Polygon();
                upButton.getPoints().addAll(
                        0.0, 10.0,
                        5.0, 0.0,
                        10.0, 10.0);
                upButton.setUserData(i);
                grid.add(upButton, 1, floors - i + 1);
                upButtons[i] = upButton;
            }

            // add a down button to every floor except the bottom floor
            if (i > 0)
            {
                Polygon downButton = new Polygon();
                downButton.getPoints().addAll(
                        0.0, 0.0,
                        10.0, 0.0,
                        5.0, 10.0);

                grid.add(downButton, 2, floors - i + 1);
                downButton.setUserData(i - 1);
                downButtons[i-1] = downButton;
            }

            // add an elevator's inner button to every floor
            Circle innerButton = new Circle(5.0);
            grid.add(innerButton, 5, floors-i+1);
            innerButton.setUserData(i);
            innerButtons[i] = innerButton;
        }

        //actions

        for (Polygon upButton : upButtons) {
            UpButtonHandler upButtonHandler = new UpButtonHandler(lift, upButton);
            upButton.setOnMouseClicked(upButtonHandler);
        }

        for (Polygon downButton : downButtons)
        {
            downButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lift.downButtons[(int) downButton.getUserData()].push();
                    downButton.setFill(Color.RED);
                }
            });
        }

        for (Circle innerButton : innerButtons)
        {
            innerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lift.innerButtons[(int) innerButton.getUserData()].push();
                    innerButton.setFill(Color.RED);
                }
            });
        }
/*
        runBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lift.run();

                grid.getChildren().remove(elevator);
                grid.add(elevator, 4, floors + 1 - lift.currentFloor);

                for (Polygon upButton : upButtons)
                {
                    if(lift.upButtons[(int)upButton.getUserData()].on) upButton.setFill(Color.RED);
                    else upButton.setFill(Color.BLACK);
                }

                for (Polygon downButton : downButtons)
                {
                    System.out.println("cur floor: " + lift.currentFloor);
                    System.out.println((int)downButton.getUserData() + "  : " + lift.downButtons[(int)downButton.getUserData()].on );
                    if(lift.downButtons[(int)downButton.getUserData()].on) downButton.setFill(Color.RED);
                    else downButton.setFill(Color.BLACK);
                }

                for (Circle innerButton : innerButtons)
                {
                    if(lift.innerButtons[(int)innerButton.getUserData()].on) innerButton.setFill(Color.RED);
                    else innerButton.setFill(Color.BLACK);
                }
            }
        });*/

        ButtonHandler buttonHandler = new ButtonHandler(lift, grid, elevator, upButtons, downButtons, innerButtons);
        runBtn.setOnAction(buttonHandler);

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


