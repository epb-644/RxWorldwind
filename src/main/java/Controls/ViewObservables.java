package Controls;

import gov.nasa.worldwind.View;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.InputHandler;
import gov.nasa.worldwind.view.orbit.OrbitView;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.SwingSubscriptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public enum ViewObservables { ;
    public static Observable<PropertyChangeEvent> fromWorldWindViewChanges(final View view) {
        return createObservable(view, AVKey.VIEW);
    }

    public static Observable<Double> fromWorldWindViewZooms(final OrbitView view) {
//        return Observable.merge(createObservable(view, AVKey.VIEW_ZOOM_IN),
//                createObservable(view, AVKey.VIEW_ZOOM_OUT)); //why don't these work...?

        final Observable<PropertyChangeEvent> viewChanges = createObservable(view, AVKey.VIEW);

        final Observable<Double> zoomChanges = viewChanges
                .map(new Func1<PropertyChangeEvent, Double>() {
                    @Override
                    public Double call(PropertyChangeEvent propertyChangeEvent) {
                        return view.getZoom();
                    }
                })
                .distinctUntilChanged();

        return zoomChanges;
    }

    private static Observable<PropertyChangeEvent> createObservable(final View view, final String avKey){
        return Observable.create(new Observable.OnSubscribe<PropertyChangeEvent>() {
            @Override
            public void call(final Subscriber<? super PropertyChangeEvent> subscriber) {
                final PropertyChangeListener listener = new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent e) {
                        subscriber.onNext(e);
                    }
                };
                view.addPropertyChangeListener(avKey, listener);

                subscriber.add(SwingSubscriptions.unsubscribeInEventDispatchThread(new Action0() {
                    @Override
                    public void call() {
                        view.removePropertyChangeListener(listener);
                    }
                }));
            }
        });
    }
}
