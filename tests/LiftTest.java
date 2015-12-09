import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Simon on 09/12/15.
 */
public class LiftTest {

    //TODO: more tests, edge cases
    @Test
    public void testGetNextFloor() throws Exception {

        // test stationary lift
        Lift lift = new Lift(10);
        lift.innerButtons[5].push();
        assertTrue(lift.getNextFloor()==5);

        // test upward going lift
        Lift lift2 = new Lift(10);
        lift2.currentFloor = 5;
        lift2.direction = 1;
        lift2.innerButtons[10].push();
        lift2.innerButtons[0].push();
        assertTrue(lift2.getNextFloor() == 10);

        // test downward going lift
        Lift lift3 = new Lift(10);
        lift3.currentFloor = 5;
        lift3.direction = -1;
        lift3.innerButtons[10].push();
        lift3.innerButtons[0].push();
        assertTrue(lift3.getNextFloor() == 0);
    }

    @Test
    public void testRun() throws Exception {

        Lift lift = new Lift(10);
        lift.innerButtons[5].push();
        lift.innerButtons[8].push();

        lift.run();
        assertTrue(lift.direction == 1);
        assertTrue(lift.currentFloor == 5);
        assertFalse(lift.innerButtons[5].on);

        lift.innerButtons[3].push();

        lift.run();
        assertTrue(lift.direction == 1);
        assertTrue(lift.currentFloor == 8);
        assertFalse(lift.innerButtons[8].on);

        lift.run();
        assertTrue(lift.direction == -1);
        assertTrue(lift.currentFloor == 3);
        assertFalse(lift.innerButtons[3].on);
    }

    public void it_works_with_up_button() throws Exception {
        Lift lift = new Lift(10);
        lift.innerButtons[5].push();
        lift.upButtons[3].push();
        lift.run();
        assertTrue(lift.currentFloor == );
    }
}