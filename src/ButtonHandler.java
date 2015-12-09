import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class ButtonHandler implements EventHandler<ActionEvent>{


    private final Lift lift;
    private final GridPane grid;
    private Button runBtn;

    public ButtonHandler(Lift lift, GridPane grid, Button runBtn) {

        this.lift = lift;
        this.grid = grid;
        this.runBtn = runBtn;
    }

    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == runBtn) {

            lift.run();

            grid.getChildren().remove(lift.elevatorShape);
            grid.add(lift.elevatorShape, 4, lift.floors + 2 - lift.currentFloor);

            for (Polygon upButton : lift.upBtnShapes) {
                if (lift.upButtons[(int) upButton.getUserData()].on) upButton.setFill(Color.RED);
                else upButton.setFill(Color.BLACK);
            }

            for (Polygon downButton : lift.downBtnShapes) {

                if (lift.downButtons[(int) downButton.getUserData()].on) downButton.setFill(Color.RED);
                else downButton.setFill(Color.BLACK);
            }

            for (Circle innerButton : lift.innerBtnShapes) {
                if (lift.innerButtons[(int) innerButton.getUserData()].on) innerButton.setFill(Color.RED);
                else innerButton.setFill(Color.BLACK);
            }

        }

    }
}
