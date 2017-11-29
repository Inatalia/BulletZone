package edu.unh.cs.cs619_2015_project2.g5;

import android.app.Activity;
import android.widget.Button;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Class tests components of the TankClientActivity UI.
 *
 * @author Rabbit
 */
@RunWith(RobolectricGradleTestRunner.class)
public class TankClientActivityTest {
    private Activity mTestClientActivity;
    private Button mButtonUp;
    private Button mButtonDown;
    private Button mButtonLeft;
    private Button mButtonRight;
    private Button mButtonFire;


    /**
     * Set up activity for unit test.
     *
     * @throws Exception
     */
    @Before
    protected void setUp() throws Exception {
        mTestClientActivity = Robolectric.setupActivity(TankClientActivity.class);

        mButtonUp = (Button) mTestClientActivity.findViewById(R.id.buttonUp);
        mButtonDown = (Button) mTestClientActivity.findViewById(R.id.buttonDown);
        mButtonLeft = (Button) mTestClientActivity.findViewById(R.id.buttonLeft);
        mButtonRight = (Button) mTestClientActivity.findViewById(R.id.buttonRight);
        mButtonFire = (Button) mTestClientActivity.findViewById(R.id.buttonFire);
    }

    /**
     * Tests the up button functionality.
     */
    @Test
    public void testButtonUp() {
        assertEquals(true, mButtonUp.performClick());
    }

    /**
     * Tests the down button functionality.
     */
    @Test
    public void testButtonDown() {
        assertEquals(true, mButtonDown.performClick());
    }

    /**
     * Tests the left button functionality.
     */
    @Test
    public void testButtonLeft() {
        assertEquals(true, mButtonLeft.performClick());
    }

    /**
     * Tests the right button functionality.
     */
    @Test
    public void testButtonRight() {
        assertEquals(true, mButtonRight.performClick());
    }

    /**
     * Tests the fire button functionality.
     */
    @Test
    public void testButtonFire() {
        assertEquals(true, mButtonFire.performClick());
    }
}
