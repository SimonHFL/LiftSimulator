import javafx.scene.layout.GridPane;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Simon on 09/12/15.
 */
public class LiftTest {

    GridPane grid;


    @Before
    public void setUp() throws Exception {
        GridPane grid = new GridPane();
        this.grid = grid;
    }


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

    @Test
    public void it_stops_at_floors_where_up_is_pressed() throws Exception {
        Lift lift = new Lift(10);
        lift.innerButtons[5].push();
        lift.upButtons[3].push();
        lift.run();
        assertTrue(lift.currentFloor == 3);
    }

    @Test
    public void it_stops_at_floors_where_down_is_pressed() throws Exception {
        Lift lift = new Lift(10);
        lift.currentFloor = 10;
        lift.innerButtons[5].push();
        lift.downButtons[7].push(); // floor 8
        lift.run();
        assertTrue(lift.currentFloor == 8);
    }

    @Test
    public void it_ignores_inner_buttons_if_no_one_in_elevator() throws Exception {
        Lift lift = new Lift(10);
        lift.floorSensor.toggleOff();
        lift.innerButtons[5].push();
        lift.run();
        assertTrue(lift.currentFloor == 0);
    }
}