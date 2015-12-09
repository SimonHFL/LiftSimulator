import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class upButtonHandler implements EventHandler<MouseEvent>{
    private final Lift lift;


    public upButtonHandler(Lift lift) {

        this.lift = lift;
    }

    @Override
    public void handle(MouseEvent event) {
       
    }
}
