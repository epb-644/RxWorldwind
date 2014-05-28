package Controls;

import rx.Observer;

import java.awt.event.MouseEvent;

/**
 * Created by eburns-admin on 5/28/2014.
 */
public enum EventConsumer { ;
    public static Observer<MouseEvent> createMouseEventConsumer(){
        return new Observer<MouseEvent>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                throw new RuntimeException(e);
            }

            @Override
            public void onNext(MouseEvent t) {
                t.consume();
            }
        };
    }
}
