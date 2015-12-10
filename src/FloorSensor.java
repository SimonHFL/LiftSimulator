import java.io.Serializable;


public class FloorSensor implements Serializable{

    boolean on;  // When on, somebody is inside the elevator

    /*
    |--------------------------------------------------------------------------
    | Public  Methods
    |--------------------------------------------------------------------------
    |
    */

    public void toggle()
    {
        on = !on;
    }
}
