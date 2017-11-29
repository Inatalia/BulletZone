package edu.unh.cs.cs619_2015_project2.g5.event;

import com.google.common.eventbus.EventBus;

import org.androidannotations.annotations.EBean;

/**
 * Created by rabbit on 11/4/15.
 * It is a singleton class wrapper for a bus instance
 */
@EBean(scope = EBean.Scope.Singleton)
public class BusProvider {
    private static EventBus bus;

    /**
     * Instantiate the bus only once
     * @return
     */
    public synchronized EventBus getInstance(){
        if(bus == null)
            bus = new EventBus();
        return bus;
    }

    /**
     * Constructor
     */
    public BusProvider(){
    }
}