import javafx.scene.shape.Shape;

public class LiftBtn {
    boolean on;  // When on, somebody has pushed the button and the light is red. Otherwise it is green.
    Shape btn; // Reference to a JavaFX circle, rectangle or polygon on the screen
    int floor;


    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    /**
     * // The constructor. The caller has created the button Shape,
     e.g. a circle, and passes it on to LiftBtn.
     */


    //TODO: fix later
    public LiftBtn(int floor) {
        this.floor = floor;
    }

    /**
     * should not only set "on", but also update the
     * Button color. There is no reason to refresh the screen. JavaFX does it
     * automatically.
     */
    public void push()
    {
        on = true;
        //TODO: update color
    }

    /**
     * should not only set "on", but also update the
     * Button color. There is no reason to refresh the screen. JavaFX does it
     * automatically.
     */
    public void reset()
    {
        on = false;
        //TODO: update color
    }

    /*
    |--------------------------------------------------------------------------
    | Private  Methods
    |--------------------------------------------------------------------------
    |
    */
}
