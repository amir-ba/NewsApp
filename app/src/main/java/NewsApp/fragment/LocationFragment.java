package NewsApp.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Amir on 04.12.2015.
 */
public class LocationFragment extends MapFragment implements OnMapReadyCallback {



        private GoogleMap mMap;
        private Marker marker;


        @Override
        public void onResume() {
            super.onResume();

            setUpMapIfNeeded();
        }

        private void setUpMapIfNeeded() {

            if (mMap == null) {

                getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

            mMap = googleMap;
            setUpMap();
        }

        private void setUpMap() {

            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(51.051877, 13.741517), 12));
            mMap.addMarker(new MarkerOptions()

                    .position(new LatLng(51.02855, 13.723903))
                    .anchor(0.5f, 0.5f)
                    .title("HÜL/S590")
                    .snippet("Computer Pool"));
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    //remove previously placed Marker
                    if (marker != null) {
                        marker.remove();
                    }

                    //place marker where user just clicked
                    marker = mMap.addMarker(new MarkerOptions().position(point).title("Marker")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                }
            });

        }


    }

