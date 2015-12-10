import java.io.Serializable;
import java.util.ArrayList;

public class Lift implements Serializable{
    int direction;  // +1 when the current move direction is up, -1 when down
    int currentFloor = 0;
    int floors;
    FloorSensor floorSensor = new FloorSensor();
    LiftBtn[] innerButtons;
    LiftBtn[] upButtons;
    LiftBtn[] downButtons;
    LiftVisualizer visualizer = new LiftVisualizer(this);

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    public Lift(int floors)
    {
        this.floors = floors;

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
}
