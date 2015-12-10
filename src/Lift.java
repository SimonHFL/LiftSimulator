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
import java.util.ArrayList;

public class Lift {
    int direction;  // +1 when the current move direction is up, -1 when down
    int currentFloor = 0;
    int floors;
    FloorSensor floorSensor = new FloorSensor();
    LiftBtn[] innerButtons;
    LiftBtn[] upButtons;
    LiftBtn[] downButtons;
    Polygon[] upBtnShapes;
    Polygon[] downBtnShapes;
    Circle[] innerBtnShapes;
    Rectangle elevatorShape;
    GridPane grid;

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    public Lift(int floors)
    {
        this.floors = floors;

        visualize();

        // create buttons
        innerButtons = new LiftBtn[floors+1];
        for(int i = 0; i<=floors; i++)
        {
            innerButtons[i] = new LiftBtn(i);
        }

        upButtons = new LiftBtn[floors];
        for(int i = 0; i<floors; i++)
        {
            upButtons[i] = new LiftBtn(i);
        }

        downButtons = new LiftBtn[floors];
        for(int i = 0; i<floors; i++)
        {
            downButtons[i] = new LiftBtn(i+1);
        }

    }

    /**
     * Find the next floor and move the lift there. Reset various buttons. Move
     * the lift (the small rectangle) on the screen.
     */
    public void run()
    {
        // if no one is in elevator, turn off inner buttons
        if(! floorSensor.on) {
            for(LiftBtn innerBtn : innerButtons)
            {
                innerBtn.reset();
            }
        }

        int nextFloor = getNextFloor();

        if (nextFloor > currentFloor) direction = 1;
        else if(nextFloor < currentFloor) direction = -1;

        currentFloor = nextFloor;

        // reset buttons if they are on current floor.
        for (LiftBtn innerButton : innerButtons)
        {
            if (innerButton.floor == currentFloor) innerButton.reset();
        }

        for (LiftBtn upButton : upButtons)
        {
            if (upButton.floor == currentFloor) upButton.reset();
        }

        for (LiftBtn downButton : downButtons)
        {
            if (downButton.floor == currentFloor) downButton.reset();
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */

    /**
     * Returns the floor that the elevator should go to next.
     *
     * - If the elevator is stationary it returns the closest floor
     *
     * - If the elevator is going upwards it returns the closest upwards floor,
     *   or in lack thereof it returns the closest downwards floor
     *
     * - If the elevator is going downwards it returns the closest downwards floor,
     *   or in lack thereof it returns the closest upwards floor
     *
     *  @return nextFloor int
     */
    //TODO: what if two equally close floors
    protected int getNextFloor()
    {
        ArrayList<LiftBtn> activeButtons = getActiveButtons();

        int nextFloor = currentFloor;

        int closestFloor = currentFloor;
        int closestDistance = Integer.MAX_VALUE;

        int closestUpwardFloor = currentFloor;
        int closestUpwardDistance = Integer.MAX_VALUE;

        int closestDownwardFloor = currentFloor;
        int closestDownwarDistance = Integer.MAX_VALUE;

        for(LiftBtn btn: activeButtons)
        {
            int distance = btn.floor - currentFloor;
            int absDistance = Math.abs(distance);

            if(absDistance < closestDistance)
            {
                closestFloor = btn.floor;
                closestDistance = absDistance;
            }

            if (distance > 0 && distance < closestUpwardDistance)
            {
                closestUpwardFloor = btn.floor;
                closestUpwardDistance = distance;
            }

            if (distance < 0 && absDistance < closestDownwarDistance)
            {
                closestDownwardFloor = btn.floor;
                closestDownwarDistance = absDistance;
            }
        }

        if (direction == 0) nextFloor = closestFloor;

        if (direction == 1 )
        {
            if(closestUpwardFloor != currentFloor) nextFloor = closestUpwardFloor;
            else nextFloor = closestDownwardFloor;
        }

        if (direction == -1)
        {
            if(closestDownwardFloor != currentFloor) nextFloor = closestDownwardFloor;
            else nextFloor = closestUpwardFloor;
        }

        return nextFloor;
    }

    /**
     * Get all buttons that have been pressed
     *
     * @return
     */
    private ArrayList<LiftBtn> getActiveButtons() {
        ArrayList<LiftBtn> activeButtons = new ArrayList<LiftBtn>();

        for (LiftBtn innerButton: innerButtons)
        {
            if (innerButton.on) activeButtons.add(innerButton);
        }

        for (LiftBtn upButton: upButtons)
        {
            if (upButton.on) activeButtons.add(upButton);
        }

        for (LiftBtn downButton: downButtons)
        {
            if (downButton.on) activeButtons.add(downButton);
        }

        return activeButtons;
    }

    /**
     * Create a grid pane with a visual representation of the lift
     */
    private void visualize() {

        int startingRow = 0;

        upBtnShapes = new Polygon[floors];
        downBtnShapes = new Polygon[floors];
        innerBtnShapes = new Circle[floors + 1];

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
        grid.add(elevator, 4, startingRow + floors + 2);
        elevatorShape = elevator;

        for(int i =0; i <= floors; i++)
        {
            // add the floor number
            Text floorNumber = new Text(""+i);
            grid.add(floorNumber, 0, startingRow + floors-i+2);

            // add an up button to every floor except the top floor
            if(i<floors)
            {
                Polygon upButton = new Polygon();
                upButton.getPoints().addAll(
                        0.0, 10.0,
                        5.0, 0.0,
                        10.0, 10.0);
                upButton.setUserData(i);
                grid.add(upButton, 1, startingRow + floors - i + 2);

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

                grid.add(downButton, 2, startingRow + floors - i + 2);
                downButton.setUserData(i - 1);
                downBtnShapes[i-1] = downButton;
            }

            // add an elevator's inner button to every floor
            Circle innerButton = new Circle(5.0);
            grid.add(innerButton, 5, startingRow + floors-i+2);
            innerButton.setUserData(i);
            innerBtnShapes[i] = innerButton;
        }

        //actions

        for (Polygon upButton : upBtnShapes) {

            upButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    upButtons[(int) upButton.getUserData()].push();
                    upButton.setFill(Color.RED);
                }
            });
        }

        for (Polygon downButton : downBtnShapes)
        {
            downButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    downButtons[(int) downButton.getUserData()].push();
                    downButton.setFill(Color.RED);
                }
            });
        }

        for (Circle innerButton : innerBtnShapes)
        {
            innerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    innerButtons[(int) innerButton.getUserData()].push();
                    innerButton.setFill(Color.RED);
                }
            });
        }

        elevator.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                floorSensor.toggle();
                elevator.setFill(floorSensor.on ? Color.GREEN : Color.RED);
            }
        });

    }
}
