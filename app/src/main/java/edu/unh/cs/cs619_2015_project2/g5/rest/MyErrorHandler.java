package edu.unh.cs.cs619_2015_project2.g5.rest;

import android.content.Context;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 * Error Handler to catch ClientHttpError
 * Created by rabbit on 11/9/15.
 */
@EBean
public class MyErrorHandler implements RestErrorHandler {

    @RootContext
    Context context;

    /**
     * Show error of the exception.
     */
    @UiThread
    void showError(){
        Toast.makeText(context, "Rest Error...", Toast.LENGTH_SHORT).show();
    }

    /**
     * onRestClientExceptionThrown
     * @param e NestedRuntimeException
     */
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException e){
        e.printStackTrace();
        Log.d("REST", e.toString());
        showError();
    }
}
