package io.demo;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// classes needed to initialize map
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

// classes needed to add the location component
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;

// classes needed to add a marker
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import io.demo.Models.Pedido;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MapaUbicacionPedido extends AppCompatActivity
        implements OnMapReadyCallback,
        MapboxMap.OnMapClickListener, PermissionsListener {


    // variables for adding location layer
    private MapView mapView;
    private MapboxMap mapboxMap;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;

    // Firebase
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference nodoUserDeliver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_mapa_ubicacion_pedido);

        mapView = findViewById(R.id.mapaView);
        mapView.onCreate(savedInstanceState);
        // Inicia el Mapa. <<No es recomendable llamar a una acción despues de esta linea si el mapa no está cargado aún>>
        mapView.getMapAsync(this);

    }

    // TODO: Pendiente, no agrega el marcador correctamente xD
    private void iniciarListenerDeDeliverPosicion(String uid_deliver) {
        // Firebase
        nodoUserDeliver = firebaseDatabase.getReference().child("users").child(uid_deliver);
        // Activar evento de cambio de Latitud y Longitud
        nodoUserDeliver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot objDeliver) {
                // Anadir el marcador al mapa
                Log.e("Aviso", "Nueva latitud y longitud");
                anadeMarcador(objDeliver.child("latitud").getValue(Double.class),objDeliver.child("longitud").getValue(Double.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    // TODO: Aqui van los metodos para activar las notificaciones cuando el pedido está cerca xD
    // [Code HERE]



    // Metodos generados por el MapBox Tutorial. Desconozco su funcionamiento (70% le entiendo) xD j3j3

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        // Style
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
                addDestinationIconSymbolLayer(style);
                //                mapboxMap.addOnMapClickListener(MapaUbicacionPedido.this)
                //                button = findViewById(R.id.btn_addmarker);
                //                button.setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View v) {
                //                        boolean simulateRoute = true;
                //                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                //                                .directionsRoute(currentRoute)
                //                                .shouldSimulateRoute(simulateRoute)
                //                                .build();
                //                        // Call this method with Context from within an Activity
                //                        NavigationLauncher.startNavigation(MapaUbicacionPedido.this, options);
                //                    }
                //                });
                //                button.setOnClickListener(new View.OnClickListener() {
                //                    @Override
                //                    public void onClick(View view) {
                //                    }
                //                });
            }
        });
        //Recupera los datos del Intent y activa el escucha
        String uid_deliver = getIntent().getExtras().getString("uid_deliver");
        iniciarListenerDeDeliverPosicion(uid_deliver);
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        //anadeMarcador(point);
        //button.setEnabled(true);
        //button.setBackgroundResource(R.color.mapboxBlue);
        return true;
    }

    public void anadeMarcador(double latitude, double longitude){
        //Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point destinationPoint = Point.fromLngLat(latitude, longitude);
        //Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                //locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            Log.e("Aviso", "Source.setGeoJson a ver si jala xD");
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }
        //getRoute(originPoint, destinationPoint);
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            // Activate the MapboxMap LocationComponent to show user location
            // Adding in LocationComponentOptions is also an optional parameter
            locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationComponent(mapboxMap.getStyle());
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
