package Examples;

import Controls.EventConsumer;
import Controls.MouseObservables;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import rx.Observable;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;


public class ConsumeZoomEvents extends JFrame {
    public ConsumeZoomEvents()
    {
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
        this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());

        final Observable<MouseWheelEvent> mouseWheelObservable =
                MouseObservables.fromWorldWindMouseWheelEvents(wwd.getInputHandler());

        mouseWheelObservable.subscribe(EventConsumer.createMouseEventConsumer());
    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new ConsumeZoomEvents();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
