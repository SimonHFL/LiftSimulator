import javafx.scene.shape.Shape;

/**
 * Is similar to LiftBtn, but behaves differently. For instance, it toggles on
 * when clicked and it may use different colors. You may use sub-classing.
 */
public class FloorSensor {
    boolean on;  // When on, somebody has pushed the button and the light is red. Otherwise it is green.
    //TODO: shape?

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    //TODO: fix later, shape
    public FloorSensor() {
    }

    /**
     * should not only set "on", but also update the color.
     */
    public void toggle()
    {
        if(!on) on = true;
        else on = false;
    }

    public void toggleOff() {
        on = false;
    }

    public void toggleOn()
    {
        on = true;
    }
}
