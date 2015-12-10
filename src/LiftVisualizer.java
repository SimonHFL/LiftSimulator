import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LiftVisualizer {

    Polygon[] upBtnShapes;
    Polygon[] downBtnShapes;
    Circle[] innerBtnShapes;
    Rectangle elevatorShape;
    GridPane grid;
    Lift lift;


    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    public LiftVisualizer(Lift lift) {
        this.lift = lift;
    }

    /**
     * Creates a grid pane with a visual representation of the lift
     */
    public GridPane visualize() {

        createView();

        setActions();

        return grid;
    }

    /**
     * Check if buttons are on and update shapes accordingly
     */
    public void updateButtons()
    {
        for (Polygon upBtnShape : upBtnShapes) {
            if (lift.upButtons[(int) upBtnShape.getUserData()].on) upBtnShape.setFill(Color.RED);
            else upBtnShape.setFill(Color.BLACK);
        }

        for (Polygon downButton : downBtnShapes) {
            if (lift.downButtons[(int) downButton.getUserData()].on) downButton.setFill(Color.RED);
            else downButton.setFill(Color.BLACK);
        }

        for (Circle innerButton : innerBtnShapes) {
            if (lift.innerButtons[(int) innerButton.getUserData()].on) innerButton.setFill(Color.RED);
            else innerButton.setFill(Color.BLACK);
        }
    }

    /**
     * Move the elevator shape to the current floor
     */
    public void moveElevator()
    {
        grid.getChildren().remove(elevatorShape);
        grid.add(lift.visualizer.elevatorShape, 4, lift.floors + 2 - lift.currentFloor);
    }


    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */

    /**
     * Create the layout of the lift
     */
    private void createView() {
        int startingRow = 0;

        upBtnShapes = new Polygon[lift.floors];
        downBtnShapes = new Polygon[lift.floors];
        innerBtnShapes = new Circle[lift.floors + 1];

        this.grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text outerButtonsTitle = new Text("Outer Buttons");
        grid.add(outerButtonsTitle, 1, startingRow + 1, 2, 1);

        Text elevatorTitle = new Text("Elevator");
        grid.add(elevatorTitle, 4, startingRow + 1, 2, 1);

        Rectangle elevator = new Rectangle(10.0,10.0);
        elevator.setFill(Color.RED);
        grid.add(elevator, 4, startingRow + lift.floors + 2);
        elevatorShape = elevator;

        for(int i =0; i <= lift.floors; i++)
        {
            // add the floor number
            Text floorNumber = new Text(""+i);
            grid.add(floorNumber, 0, startingRow + lift.floors-i+2);

            // add an up button to every floor except the top floor
            if(i<lift.floors)
            {
                Polygon upButton = new Polygon();
                upButton.getPoints().addAll(
                        0.0, 10.0,
                        5.0, 0.0,
                        10.0, 10.0);
                upButton.setUserData(i);
                grid.add(upButton, 1, startingRow + lift.floors - i + 2);

                upBtnShapes[i] = upButton;
            }

            // add a down button to every floor except the bottom floor
            if (i > 0)
            {
                Polygon downButton = new Polygon();
                downButton.getPoints().addAll(
                        0.0, 0.0,
                        10.0, 0.0,
                        5.0, 10.0);

                grid.add(downButton, 2, startingRow + lift.floors - i + 2);
                downButton.setUserData(i - 1);
                downBtnShapes[i-1] = downButton;
            }

            // add an elevator's inner button to every floor
            Circle innerButton = new Circle(5.0);
            grid.add(innerButton, 5, startingRow + lift.floors-i+2);
            innerButton.setUserData(i);
            innerBtnShapes[i] = innerButton;
        }
    }

    /**
     * Sets actions on elements in the view
     */
    private void setActions() {

        for (Polygon upButton : upBtnShapes) {

            upButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lift.upButtons[(int) upButton.getUserData()].push();
                    upButton.setFill(Color.RED);
                }
            });
        }

        for (Polygon downButton : downBtnShapes)
        {
            downButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lift.downButtons[(int) downButton.getUserData()].push();
                    downButton.setFill(Color.RED);
                }
            });
        }

        for (Circle innerButton : innerBtnShapes)
        {
            innerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    lift.innerButtons[(int) innerButton.getUserData()].push();
                    innerButton.setFill(Color.RED);
                }
            });
        }

        elevatorShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lift.floorSensor.toggle();
                elevatorShape.setFill(lift.floorSensor.on ? Color.GREEN : Color.RED);
            }
        });

    }
}
