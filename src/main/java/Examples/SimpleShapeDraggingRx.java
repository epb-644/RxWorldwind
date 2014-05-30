package Examples;

import WidgetWrappers.BasicDraggerRx;
import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.geom.Sector;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;
import gov.nasa.worldwind.render.SurfaceImage;
import rx.Observable;
import rx.functions.Action1;

import javax.swing.*;

/**
 * Created by eburns-admin on 5/30/2014.
 */
public class SimpleShapeDraggingRx extends JFrame {
    public SimpleShapeDraggingRx(){
        final WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
        this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());

        // Add a layer containing an image
        SurfaceImage si = new SurfaceImage("images/400x230-splash-nww.png", Sector.fromDegrees(35, 45, -115, -95));
        RenderableLayer layer = new RenderableLayer();
        layer.addRenderable(si);
        insertBeforePlacenames(wwd, layer);

        final BasicDraggerRx draggableSurfaceImage = BasicDraggerRx.makeDraggable(wwd, si);
        final Observable<Observable<Position>> dragStreams = draggableSurfaceImage.observePositions();

        dragStreams.subscribe(new Action1<Observable<Position>>() {
            @Override
            public void call(Observable<Position> positionObservable) {
                System.out.println("BEGINNING DRAG");
                positionObservable.subscribe(new rx.Observer<Position>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("DRAGGING COMPLETED");
                    }

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(Position position) {
                        //dragging object
                    }
                });
            }
        });
    }

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new SimpleShapeDraggingRx();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void insertBeforePlacenames(WorldWindow wwd, Layer layer){
        // Insert the layer into the layer list just before the placenames.
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof PlaceNameLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition, layer);
    }
}
