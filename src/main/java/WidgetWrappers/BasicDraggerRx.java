package WidgetWrappers;

import Controls.MouseObservables;
import Controls.ViewObservables;
import gov.nasa.worldwind.Movable;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.event.DragSelectEvent;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.geom.Intersection;
import gov.nasa.worldwind.geom.Line;
import gov.nasa.worldwind.geom.Position;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.Objects;


//TODO: set pick position on object from initial click -- don't just use center of object
//TODO: don't end drag when user right-clicks
//TODO: figure out how to properly end drag stream when cursor leaves Worldwind window
public class BasicDraggerRx {
    private final WorldWindow wwd;
    private final Movable movableObject;
    private final Observable<Observable<DragSelectEvent>> objectDrags;
    private final Observable<Observable<Position>> objectDragPositions;
    private final Observable<Position> mousePositionOnViewChanges;
    private final Observable<MouseEvent> mouseReleases;

    private BasicDraggerRx(final WorldWindow wwd, final Movable movableObj){
        this.wwd = wwd;
        this.movableObject = movableObj;

        final Observable<DragSelectEvent> dragSelects = MouseObservables.fromWorldWindDragSelectionEvents(wwd)
                .filter(new Func1<DragSelectEvent, Boolean>() {
                    @Override
                    public Boolean call(DragSelectEvent dragSelectEvent) {
                        return dragSelectEvent != null &&
                                dragSelectEvent.getTopPickedObject().getObject() == movableObject;
                    }
                });

        final Observable<SelectEvent> mousePressesOnObject = MouseObservables.fromWorldWindSelectEvents(wwd)
                .filter(new Func1<SelectEvent, Boolean>() {
                    @Override
                    public Boolean call(SelectEvent selectEvent) {
                        return selectEvent != null &&
                                selectEvent.getEventAction() != null &&
                                selectEvent.getEventAction().equals(SelectEvent.LEFT_PRESS) &&
                                selectEvent.getTopObject() == movableObj;
                    }
                });

        mouseReleases = MouseObservables.fromWorldWindMouseReleasedEvents(wwd.getInputHandler());

        mousePositionOnViewChanges = ViewObservables.fromWorldWindViewChanges(wwd.getView())
                .map(new Func1<PropertyChangeEvent, Position>() {
                    @Override
                    public Position call(PropertyChangeEvent propertyChangeEvent) {
                        return wwd.getCurrentPosition();
                    }
                });

        objectDrags = mousePressesOnObject.map(new Func1<SelectEvent, Observable<DragSelectEvent>>() {
            @Override
            public Observable<DragSelectEvent> call(final SelectEvent mouseEvent) {
                return dragSelects
                        .takeWhile(new Func1<DragSelectEvent, Boolean>() {
                            @Override
                            public Boolean call(DragSelectEvent dragEvent) {
                                Object topObject = dragEvent.getTopObject();
                                return topObject == movableObject;
                            }
                        })
                        .takeUntil(mouseReleases)
                        .takeUntil(MouseObservables.fromWorldWindMouseExitedEvents(wwd.getInputHandler()));
            }
        });

        objectDragPositions = objectDrags.map(new Func1<Observable<DragSelectEvent>, Observable<Position>>() {
            @Override
            public Observable<Position> call(Observable<DragSelectEvent> dragSelectEventObservable) {
                final Observable<Position> dragPositions = dragSelectEventObservable
                        .map(new Func1<DragSelectEvent, Position>() {
                            @Override
                            public Position call(DragSelectEvent dragSelectEvent) {
                                return getMouseGroundPosition(dragSelectEvent);
                            }
                        });

                return Observable.merge(dragPositions, mousePositionOnViewChanges)
                        .takeUntil(objectDragPositions)
                        .takeUntil(mouseReleases)
                        .takeUntil(MouseObservables.fromWorldWindMouseExitedEvents(wwd.getInputHandler()))
                        .takeWhile(new Func1<Position, Boolean>() {
                            @Override
                            public Boolean call(Position position) {
                                return position != null;
                            }
                        });
            }
        });

        //make object move on drags
        initObjectPositionMutators();
    }

    private void initObjectPositionMutators(){
        objectDragPositions.subscribe(new Action1<Observable<Position>>() {
            @Override
            public void call(Observable<Position> mouseDragPositions) {
                mouseDragPositions
                        .subscribe(new Action1<Position>() {
                            @Override
                            public void call(Position position) {
                                movableObject.moveTo(position);
                            }
                        });
            }
        });

        //consume events during drags
        objectDrags.subscribe(new Action1<Observable<DragSelectEvent>>() {
            @Override
            public void call(Observable<DragSelectEvent> dragSelectEventObservable) {
                dragSelectEventObservable.subscribe(new Action1<DragSelectEvent>() {
                    @Override
                    public void call(DragSelectEvent dragSelectEvent) {
                        dragSelectEvent.consume();
                    }
                });
            }
        });
    }

    private Position getMouseGroundPosition(SelectEvent dragSelectEvent){
        Line ray = wwd.getView().computeRayFromScreenPoint(dragSelectEvent.getPickPoint().x,
                dragSelectEvent.getPickPoint().y);

        final Intersection[] intersection = wwd.getView().getGlobe().intersect(ray);
        final Position position = wwd.getView().getGlobe().computePositionFromPoint(intersection[0].getIntersectionPoint());

        return position;
    }

    public static BasicDraggerRx makeDraggable(WorldWindow wwd, Movable movableObject){
        return new BasicDraggerRx(Objects.requireNonNull(wwd), Objects.requireNonNull(movableObject));
    }

    public Observable<Observable<Position>> observePositions(){
        return objectDragPositions;
    }
}
