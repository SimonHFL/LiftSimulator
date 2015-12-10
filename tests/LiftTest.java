import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LiftTest {

    @Test
    public void it_responds_to_inner_buttons_if_floor_sensor_is_on() throws Exception {
        Lift lift = new Lift(10);
        lift.floorSensor.on = true;
        lift.innerButtons[5].push();
        lift.run();
        assertTrue(lift.currentFloor == 5);
    }

    @Test
    public void it_ignores_inner_buttons_if_floor_sensor_is_off() throws Exception {
        Lift lift = new Lift(10);
        lift.floorSensor.on = false;
        lift.innerButtons[5].push();
        lift.run();
        assertTrue(lift.currentFloor == 0);
    }

    @Test
    public void it_responds_to_up_buttons() throws Exception {
        // case: elevator occupied
        Lift lift = new Lift(10);
        lift.floorSensor.on = true;
        lift.upButtons[5].push();
        lift.run();
        assertTrue(lift.currentFloor == 5);

        // case: elevator empty
        Lift lift2 = new Lift(10);
        lift2.floorSensor.on = false;
        lift2.upButtons[5].push();
        lift2.run();
        assertTrue(lift2.currentFloor == 5);
    }

    @Test
    public void it_responds_to_down_buttons() throws Exception {
        // case: elevator occupied
        Lift lift = new Lift(10);
        lift.floorSensor.on = true;
        lift.downButtons[5].push();
        lift.run();
        assertTrue(lift.currentFloor == lift.downButtons[5].floor);

        // case: elevator empty
        Lift lift2 = new Lift(10);
        lift2.floorSensor.on = false;
        lift2.downButtons[5].push();
        lift2.run();
        assertTrue(lift2.currentFloor == lift.downButtons[5].floor);
    }

    @Test
    public void it_continues_going_up_until_done_before_going_down() throws Exception {
        Lift lift = new Lift(10);
        lift.currentFloor = 5;
        lift.floorSensor.on = true;
        lift.direction = 1;
        lift.innerButtons[10].push();
        lift.innerButtons[6].push();
        lift.innerButtons[4].push();
        lift.run();
        assertTrue(lift.currentFloor == 6);
        lift.run();
        assertTrue(lift.currentFloor == 10);
        lift.run();
        assertTrue(lift.currentFloor == 4);
    }

    @Test
    public void it_continues_going_down_until_done_before_going_up() throws Exception {
        Lift lift = new Lift(10);
        lift.currentFloor = 5;
        lift.floorSensor.on = true;
        lift.direction = -1;
        lift.innerButtons[0].push();
        lift.innerButtons[6].push();
        lift.innerButtons[4].push();
        lift.run();
        assertTrue(lift.currentFloor == 4);
        lift.run();
        assertTrue(lift.currentFloor == 0);
        lift.run();
        assertTrue(lift.currentFloor == 6);
    }

    @Test
    public void it_stays_if_no_buttons_are_pressed() throws Exception {
        Lift lift = new Lift(10);
        lift.run();
        assertTrue(lift.currentFloor == 0);
    }

    @Test
    public void it_gives_no_preference_to_button_type() throws Exception {
        // case: inner button is closest
        Lift lift = new Lift(10);
        lift.floorSensor.on = true;
        lift.innerButtons[5].push();
        lift.downButtons[7].push(); // floor 8
        lift.run();
        assertTrue(lift.currentFloor == 5);

        // case: down button is closest
        Lift lift2 = new Lift(10);
        lift2.floorSensor.on = true;
        lift2.downButtons[3].push(); // floor 8
        lift2.innerButtons[5].push();
        lift2.run();
        assertTrue(lift2.currentFloor == lift2.downButtons[3].floor);

        // case: up button is closest
        Lift lift3 = new Lift(10);
        lift3.floorSensor.on = true;
        lift3.upButtons[3].push(); // floor 8
        lift3.innerButtons[5].push();
        lift3.run();
        assertTrue(lift3.currentFloor == lift3.upButtons[3].floor);
    }

}
