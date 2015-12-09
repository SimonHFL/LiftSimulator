import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class ButtonHandler implements EventHandler<ActionEvent>{


    private final Lift lift;
    private final GridPane grid;
    private Button runBtn;
    private final Rectangle elevator;
    private final Polygon[] upButtons;
    private final Polygon[] downButtons;
    private final Circle[] innerButtons;

    public ButtonHandler(Lift lift, GridPane grid, Button runBtn, Rectangle elevator, Polygon[] upButtons, Polygon[] downButtons, Circle[] innerButtons) {

        this.lift = lift;
        this.grid = grid;
        this.runBtn = runBtn;
        this.elevator = elevator;
        this.upButtons = upButtons;
        this.downButtons = downButtons;
        this.innerButtons = innerButtons;
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == runBtn) {

            lift.run();

            grid.getChildren().remove(elevator);
            grid.add(elevator, 4, lift.floors + 1 - lift.currentFloor);

            for (Polygon upButton : upButtons) {
                if (lift.upButtons[(int) upButton.getUserData()].on) upButton.setFill(Color.RED);
                else upButton.setFill(Color.BLACK);
            }

            for (Polygon downButton : downButtons) {

                if (lift.downButtons[(int) downButton.getUserData()].on) downButton.setFill(Color.RED);
                else downButton.setFill(Color.BLACK);
            }

            for (Circle innerButton : innerButtons) {
                if (lift.innerButtons[(int) innerButton.getUserData()].on) innerButton.setFill(Color.RED);
                else innerButton.setFill(Color.BLACK);
            }

        }

    }
}
