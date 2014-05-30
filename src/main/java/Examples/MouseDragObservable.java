package Examples;

import Controls.MouseObservables;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;
import rx.functions.Func1;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MouseDragObservable extends JFrame {
    public MouseDragObservable()
    {
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
        this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());

        final Observable<MouseEvent> mouseDrags =
                MouseObservables.fromWorldWindMouseDragEvents(wwd.getInputHandler());

        final Observable<MouseEvent> mousePresses =
                MouseObservables.fromWorldWindMousePressedEvents(wwd.getInputHandler());

        final Observable<MouseEvent> mouseReleases =
                MouseObservables.fromWorldWindMouseReleasedEvents(wwd.getInputHandler());

        final Observable<Observable<MouseEvent>> mouseDragsSeparated =
                mousePresses.map(new Func1<MouseEvent, Observable<MouseEvent>>() {
                    @Override
                    public Observable<MouseEvent> call(MouseEvent mouseEvent) {
                        return mouseDrags.takeUntil(mouseReleases);
                    }
                });

        mouseDragsSeparated
                .subscribe(new Action1<Observable<MouseEvent>>() {
                    @Override
                    public void call(Observable<MouseEvent> mouseEventObservable) {
                        System.out.println("STARTED MOUSE DRAG");
                        mouseEventObservable.subscribe(new Observer<MouseEvent>() {
                            @Override
                            public void onCompleted() {
                                System.out.println("FINISHED MOUSE DRAG");
                            }

                            @Override
                            public void onError(Throwable e) {}

                            @Override
                            public void onNext(MouseEvent mouseEvent) {
                                //mouse is being dragged
                            }
                        });
                    }
                });


    }

    public static void main(String[] args)
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new MouseDragObservable();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
