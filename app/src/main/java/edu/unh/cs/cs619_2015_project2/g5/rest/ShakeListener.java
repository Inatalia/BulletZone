package edu.unh.cs.cs619_2015_project2.g5.rest;

/**
 * ShakeListener
 * Created by rabbit on 11/10/15.
 */
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class ShakeListener implements SensorEventListener {
    private String TAG = ShakeListener.class.getSimpleName();
    private static final int FORCE_THRESHOLD = 800;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 5;

    private SensorManager mSensorMgr;
    private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
    private long mLastTime;
    private OnShakeListener mShakeListener;
    private Context mContext;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;

    /**
     * OnShakeListener interface.
     */
    public interface OnShakeListener {
        public void onShake();
    }

    /**
     * Constructor
     * @param context Context
     */
    public ShakeListener(Context context) {

        //Log.d(TAG,"ShakeListener invoked---->");
        mContext = context;
        resume();
    }

    /**
     * Sets ShakeListener
     * @param listener OnShakeListener
     */
    public void setOnShakeListener(OnShakeListener listener) {
        //Log.d(TAG,"ShakeListener setOnShakeListener invoked---->");
        mShakeListener = listener;
    }

    /**
     * Register the Session Manager Listener onResume
     */
    public void resume() {
        mSensorMgr = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (mSensorMgr == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }
        boolean supported = false;
        try {
            supported = mSensorMgr.registerListener(this,
                    mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_GAME);
        } catch (Exception e) {
            Toast.makeText(mContext, "Shaking not supported", Toast.LENGTH_LONG)
                    .show();
        }

        if ((!supported) && (mSensorMgr != null))
            mSensorMgr.unregisterListener(this);
    }

    /**
     * Unregister the Sensor Manager onPause
     */
    public void pause() {
        if (mSensorMgr != null) {

            mSensorMgr.unregisterListener(this);
            mSensorMgr = null;
        }
    }

    /**
     * onAccuracyChanged
     * @param sensor Sensor
     * @param accuracy int
     */
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * SensorChanged
     * @param event SensorEvent
     */
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        long now = System.currentTimeMillis();

        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            long diff = now - mLastTime;
            float speed = Math.abs(event.values[SensorManager.DATA_X]
                    + event.values[SensorManager.DATA_Y]
                    + event.values[SensorManager.DATA_Z] - mLastX - mLastY
                    - mLastZ)
                    / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT)
                        && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now;
                    mShakeCount = 0;
                    Log.d(TAG,"ShakeListener mShakeListener---->"+mShakeListener);
                    if (mShakeListener != null) {
                        mShakeListener.onShake();
                    }
                }
                mLastForce = now;
            }
            mLastTime = now;
            mLastX = event.values[SensorManager.DATA_X];
            mLastY = event.values[SensorManager.DATA_Y];
            mLastZ = event.values[SensorManager.DATA_Z];
        }
    }


}