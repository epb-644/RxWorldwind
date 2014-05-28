/*
 * Copyright (C) 2014 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package Examples;

import Controls.ViewObservables;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.view.orbit.OrbitView;
import rx.Observable;
import rx.Observer;

import javax.swing.*;

/**
 * Created by eburns-admin on 5/28/2014.
 */
public class ZoomEvents extends JFrame
{
    public ZoomEvents()
    {
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
        this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());

        final Observable<Double> viewEvents = ViewObservables.fromWorldWindViewZooms((OrbitView) wwd.getView());

        viewEvents.subscribe(new Observer<Double>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Double propertyChangeEvent) {
                System.out.println("VIEW CHANGE");
            }
        });

//        mouseEventObservable.subscribe(EventConsumer.createMouseEventConsumer());
    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new ZoomEvents();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
