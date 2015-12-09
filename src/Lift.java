import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Lift {
    Stage stage; //The Form where the simulator appears.
    Pane pane; // The panel where the graphical components reside
    Scene scene;  // The area inside the Form. It must have a size that allows all the floors to show
    int direction;  // +1 when the current move direction is up, -1 when down
    int currentFloor;  // Where the lift is right now
    LiftBtn[] innerButtons;  //Array of inner buttons.
    LiftBtn[] upButtons; // Array of the outer buttons showing "up".
    LiftBtn[] downButtons; // Array of the outer buttons showing "down".
    FloorSensor floorSensor;  // On when someone is in the floor.


    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    /**
     * Create a lift simulator with floors 0 to N. Create the
     * buttons and the graphical components on the Pane root. When
     * everything is created, call stage.show( ) and return.
     *
     * @param stg
     * @param floors
     */
    public Lift(Stage stg, int floors)
    {
        currentFloor = 0;

        innerButtons = new LiftBtn[floors+1];
        for(int i = 0; i<=floors; i++)
        {
            innerButtons[i] = new LiftBtn(i);
        }

    }

    //TODO: remove
    public Lift(int floors)
    {
        currentFloor = 0;

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
        int nextFloor = getNextFloor();

        if (nextFloor > currentFloor) direction = 1;
        else if(nextFloor < currentFloor) direction = -1;

        currentFloor = nextFloor;

        innerButtons[currentFloor].reset(); // reset button of the floor we moved to.
    }

    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */

    //TODO: what if two equally close floors
    protected int getNextFloor()
    {
        ArrayList<LiftBtn> activeButtons = getActiveInnerButtons();

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

        // if no direction
        if (direction == 0) nextFloor = closestFloor;

        // if going upwards
        if (direction == 1 )
        {
            if(closestUpwardFloor != currentFloor) nextFloor = closestUpwardFloor;
            else nextFloor = closestDownwardFloor;
        }

        // if going downwards
        if (direction == -1)
        {
            if(closestDownwardFloor != currentFloor) nextFloor = closestDownwardFloor;
            else nextFloor = closestUpwardFloor;
        }

        return nextFloor;
    }

    private ArrayList<LiftBtn> getActiveInnerButtons() {
        ArrayList<LiftBtn> activeButtons = new ArrayList<LiftBtn>();
        // check if any inner buttons are pressed
        for (LiftBtn innerButton: innerButtons)
        {
            if (innerButton.on){
                activeButtons.add(innerButton);
            }
        }
        return activeButtons;
    }
}
