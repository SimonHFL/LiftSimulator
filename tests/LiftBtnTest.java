import static org.junit.Assert.*;

/**
 * Created by Simon on 09/12/15.
 */
public class LiftBtnTest {

    @org.junit.Test
    public void testPush() throws Exception {
        LiftBtn liftBtn = new LiftBtn(1);
        liftBtn.push();
        assertTrue(liftBtn.on);
    }

    @org.junit.Test
    public void testReset() throws Exception {
        LiftBtn liftBtn = new LiftBtn(1);
        liftBtn.push();
        liftBtn.reset();
        assertFalse(liftBtn.on);
    }
}