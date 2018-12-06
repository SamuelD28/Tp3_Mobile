package com.samdube.tp3_mobile.Marker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class LocationClusterRenderer extends DefaultClusterRenderer<LocationClusterItem> {

    private final IconGenerator iconGenerator;
    private final ImageView imageView;
    private final int markerWidth;
    private final int markerHeight;

    public LocationClusterRenderer(Context context, GoogleMap map, ClusterManager<LocationClusterItem> clusterManager, IconGenerator iconGenerator, ImageView imageView, int markerWidth, int markerHeight) {
        super(context, map, clusterManager);
        this.iconGenerator = iconGenerator;
        this.imageView = imageView;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;

//        iconGenerator = new IconGenerator(context.getApplicationContext());
//        imageView = new ImageView(context.getApplicationContext());
//        markerWidth = 60; //fix this
//        markerHeight = 60; //fix this
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(markerWidth, markerHeight));
//        int padding = 10; // fix this
//        imageView.setPadding(padding,padding,padding,padding);
//        iconGenerator.setContentView(imageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(LocationClusterItem item, MarkerOptions markerOptions) {
//        imageView.setImageResource(item.getIconPicture());
//        Bitmap icon = iconGenerator.makeIcon();
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<LocationClusterItem> cluster) {
        return false;
    }
}
