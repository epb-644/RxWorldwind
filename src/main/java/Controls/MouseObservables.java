package Controls;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.event.DragSelectEvent;
import gov.nasa.worldwind.event.InputHandler;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.SwingSubscriptions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Created by eburns-admin on 5/28/2014.
 */
public enum MouseObservables { ;
    public static Observable<MouseEvent> fromWorldWindMouseClickedEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseListener listener = new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        subscriber.onNext(e);
                    }
                };
                inputHandler.addMouseListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMouseEnteredEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseListener listener = new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        subscriber.onNext(e);
                    }
                };
                inputHandler.addMouseListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMouseExitedEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseListener listener = new MouseAdapter() {
                    @Override
                    public void mouseExited(MouseEvent e) {
                        subscriber.onNext(e);
                    }
                };
                inputHandler.addMouseListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMousePressedEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseListener listener = new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        subscriber.onNext(e);
                    }
                };
                inputHandler.addMouseListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMouseReleasedEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseListener listener = new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {
                        subscriber.onNext(e);
                    }
                };
                inputHandler.addMouseListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMouseMotionEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseMotionListener listener = new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent event) {
//                        subscriber.onNext(event);
                    }

                    @Override
                    public void mouseMoved(MouseEvent event) {
                        subscriber.onNext(event);
                    }
                };
                inputHandler.addMouseMotionListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseMotionListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseWheelEvent> fromWorldWindMouseWheelEvents(final InputHandler inputHandler){
        return Observable.create(new Observable.OnSubscribe<MouseWheelEvent>() {
            @Override
            public void call(final Subscriber<? super MouseWheelEvent> subscriber) {
                final MouseWheelListener listener = new MouseWheelListener() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent event) {
                        subscriber.onNext(event);
                    }
                };
                inputHandler.addMouseWheelListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseWheelListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<MouseEvent> fromWorldWindMouseDragEvents(final InputHandler inputHandler) {
        return Observable.create(new Observable.OnSubscribe<MouseEvent>() {
            @Override
            public void call(final Subscriber<? super MouseEvent> subscriber) {
                final MouseMotionListener listener = new MouseMotionListener() {
                    @Override
                    public void mouseDragged(MouseEvent event) {
                        subscriber.onNext(event);
                    }

                    @Override
                    public void mouseMoved(MouseEvent event) {
//                        subscriber.onNext(event);
                    }
                };
                inputHandler.addMouseMotionListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        inputHandler.removeMouseMotionListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<SelectEvent> fromWorldWindSelectEvents(final WorldWindow wwd){
        return Observable.create(new Observable.OnSubscribe<SelectEvent>() {
            @Override
            public void call(final Subscriber<? super SelectEvent> subscriber) {
                final SelectListener listener = new SelectListener() {
                    @Override
                    public void selected(SelectEvent event) {
                        subscriber.onNext(event);
                    }
                };
                wwd.addSelectListener(listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        wwd.removeSelectListener(listener);
                    }
                }));
            }
        });
    }

    public static Observable<DragSelectEvent> fromWorldWindDragSelectionEvents(final WorldWindow wwd) {
        return fromWorldWindSelectEvents(wwd)
                .filter(new Func1<SelectEvent, Boolean>() {
                    @Override
                    public Boolean call(SelectEvent selectEvent) {
                        return selectEvent.getEventAction().equals(SelectEvent.DRAG);
                    }
                })
                .map(new Func1<SelectEvent, DragSelectEvent>() {
                    @Override
                    public DragSelectEvent call(SelectEvent selectEvent) {
                        return (DragSelectEvent) selectEvent;
                    }
                });
    };

    public static Observable<SelectEvent> fromWorldWindDragSelectionEnd(final WorldWindow wwd) {
        return fromWorldWindSelectEvents(wwd)
                .filter(new Func1<SelectEvent, Boolean>() {
                    @Override
                    public Boolean call(SelectEvent selectEvent) {
                        return selectEvent.getEventAction().equals(SelectEvent.DRAG_END);
                    }
                });
    };
}

