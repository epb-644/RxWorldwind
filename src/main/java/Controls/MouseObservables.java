package Controls;

import gov.nasa.worldwind.event.InputHandler;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
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
}
