import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonHandler implements EventHandler<ActionEvent>{

    private Lift lift;
    private Button runBtn;

    public ButtonHandler(Lift lift, Button runBtn) {
        this.lift = lift;
        this.runBtn = runBtn;
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == runBtn) lift.run();
    }
}
