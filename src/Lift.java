import infrastructure.NegativeFloorsException;

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

    public Lift(int floors) throws NegativeFloorsException {

        if (floors <= 0) throw new NegativeFloorsException("Please supply a positive number of floors");

        this.floors = floors;

        // create buttons
        innerButtons = new LiftBtn[floors+1];
        upButtons = new LiftBtn[floors];
        downButtons = new LiftBtn[floors];

        for(int i = 0; i<=floors; i++)
        {
            innerButtons[i] = new LiftBtn(i);
            if (i == floors) break;
            downButtons[i] = new LiftBtn(i+1);
            upButtons[i] = new LiftBtn(i);
        }
    }

    /**
     * Moves the elevator to the next floor and resets buttons corresponding to that floor.
     */
    public void run() {
        // if no one is in the elevator, turn off inner buttons
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

        resetButtons();
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
    private int getNextFloor() {

        int nextFloor = currentFloor;

        int closestUpwardFloor = currentFloor;
        int closestUpwardDistance = Integer.MAX_VALUE;

        int closestDownwardFloor = currentFloor;
        int closestDownwardDistance = Integer.MAX_VALUE;

        for(LiftBtn btn: getActiveButtons())
        {
            int distance = btn.floor - currentFloor;
            int absDistance = Math.abs(distance);

            if (distance > 0 && distance < closestUpwardDistance)
            {
                closestUpwardFloor = btn.floor;
                closestUpwardDistance = distance;
            }

            if (distance < 0 && absDistance < closestDownwardDistance)
            {
                closestDownwardFloor = btn.floor;
                closestDownwardDistance = absDistance;
            }
        }

        if (direction == 0) nextFloor = closestDownwardDistance < closestUpwardDistance ? closestDownwardFloor : closestUpwardFloor;

        if (direction == 1) nextFloor = closestUpwardFloor != currentFloor ? closestUpwardFloor : closestDownwardFloor;

        if (direction == -1) nextFloor = closestDownwardFloor != currentFloor ? closestDownwardFloor : closestUpwardFloor;

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
     *  Resets buttons if their floor is the current floor
     */
    private void resetButtons() {
        for (LiftBtn button : getActiveButtons())
        {
            if (button.floor == currentFloor) button.reset();
        }
    }
}
