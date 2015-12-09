import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ClickHandler implements EventHandler<MouseEvent>{
    private final Lift lift;
    private final Rectangle elevator;

    public ClickHandler(Lift lift, Rectangle elevator) {

        this.lift = lift;
        this.elevator = elevator;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getSource() == elevator) {
            lift.floorSensor.toggle();
            elevator.setFill(lift.floorSensor.on ? Color.GREEN : Color.RED);
        }
    }
}
