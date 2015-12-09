import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class UpButtonHandler implements EventHandler<MouseEvent>{
    private final Lift lift;
    private Polygon upButton;


    public UpButtonHandler(Lift lift, Polygon upButton) {

        this.lift = lift;
        this.upButton = upButton;
    }

    @Override
    public void handle(MouseEvent event) {
        lift.upButtons[(int) upButton.getUserData()].push();
        upButton.setFill(Color.RED);
    }
}
