package edu.osu.cse.nearjoin;

import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Fish on 11/11/2014.
 */
public class MapEventFragment extends MapFragment implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    private static final long UPDATE_INTERVAL =
            MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
    // A fast frequency ceiling in milliseconds
    private static final long FASTEST_INTERVAL =
            MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationClient mLocationClient;
    Location mCurrentLocation;
    LocationRequest mLocationRequest;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mLocationClient = new LocationClient(this.getActivity(), this, this);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create();
        // Use high accuracy
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        setUpMapIfNeeded();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        this.mLocationClient.connect();
    }

    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link com.google.android.gms.maps.SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (this.mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            this.mMap = getMap();

        }
        // Check if we were successful in obtaining the map.
        if (this.mMap != null) {
            setUpMap();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        this.mMap.setMyLocationEnabled(true);
    }

    private LatLng getLatLngFromAddress(String streetAddress) {
        Geocoder coder = new Geocoder(this.getActivity());
        LatLng location = new LatLng(40.002466, -83.015941);

        try {
            List<Address> addresses = coder.getFromLocationName(streetAddress, 5);
            if (addresses == null) {
                return location;
            }
            Address address = addresses.get(0);
            double lat = address.getLatitude();
            double lng = address.getLongitude();
            location = new LatLng(lat, lng);
        } catch (Exception e) {
            e.printStackTrace();
            return location;
        }
        return location;
    }

    /*
        private void placeMarkersOfEvents(ArrayList<EventRecord> events){
            for (EventRecord event : events){
                // Get address of location
                String address = event.getLocationAddress();
                LatLng location = getLatLngFromAddress(address);
                MarkerOptions markOpts = new MarkerOptions().position(location);
                // Add other details to marker
                this.mMap.addMarker(markOpts);
            }
        }

    */
    private void drawRadiusAroundLocation(Location location) {
        this.mMap.clear();
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))   //set center
                .radius(2500)   //set radius in meters
                .fillColor(0x40ff0000)
                .strokeWidth(5);
        this.mMap.addCircle(circleOptions);
    }

    private void moveToLocation(Location location) {
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate updateLocation = CameraUpdateFactory.newLatLng(coords);
        this.mMap.moveCamera(updateLocation);
    }

    private void zoomToLocation(Location location, float zoom) {
        LatLng coords = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate updateLocation = CameraUpdateFactory.newLatLngZoom(coords, zoom);
        this.mMap.moveCamera(updateLocation);
    }

    private void setLocationIfPossible(Location location) {
        this.mCurrentLocation = location;
        if (this.mCurrentLocation == null) {
            this.mCurrentLocation = new Location("");
            this.mCurrentLocation.setLatitude(40.002466);
            this.mCurrentLocation.setLongitude(-83.015941);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Display the connection status
        this.mLocationClient.requestLocationUpdates(mLocationRequest, this);
        setLocationIfPossible(this.mLocationClient.getLastLocation());
        setUpMapIfNeeded();
        zoomToLocation(this.mCurrentLocation, 13f);
        drawRadiusAroundLocation(this.mCurrentLocation);
    }

    @Override
    public void onDisconnected() {
        // Display the connection status
    }

    @Override
    public void onLocationChanged(Location location) {
        setLocationIfPossible(location);
        setUpMapIfNeeded();
        moveToLocation(this.mCurrentLocation);
        drawRadiusAroundLocation(this.mCurrentLocation);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this.getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Location location = new Location("");
            location.setLatitude(40.002466);
            location.setLongitude(-83.015941);
            this.mCurrentLocation = location;
        }
    }

}
