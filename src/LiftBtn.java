import java.io.Serializable;

public class LiftBtn implements Serializable{

    boolean on;  // When on, somebody has pushed the button and the light is red. Otherwise it is green.
    int floor;

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    public LiftBtn(int floor) {
        this.floor = floor;
    }

    public void push()
    {
        on = true;
    }

    public void reset()
    {
        on = false;
    }

}
