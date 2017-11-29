package com.suadenuncia;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.suadenuncia.pojo.Civil;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    private FloatingActionButton fabNew;
    double lat=0.0;
    double lng=0.0;
    private Civil civilLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        inicializaComponentes();

        fabNew.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MapsActivity.this, DenunciaActivity.class);
                it.putExtra("civil", civilLogado);
                it.putExtra("longitude", lng);
                it.putExtra("latitude", lat);
                startActivity(it);
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ifsul = new LatLng(-29.8154, -51.1561);
        googleMap.addMarker(new MarkerOptions().position(ifsul));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ifsul));

        localizacaoAtual();
    }

    private void agregarMarcador(double lat, double lng){
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate localizacaoAtual=CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if(marcador!=null)marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Localização atual"));
        mMap.animateCamera(localizacaoAtual);
    }

    private void atualizarLocalizacao(Location location){
        if(location!=null){
            lat=location.getLatitude();
            lng=location.getLongitude();
            agregarMarcador(lat,lng);
        }
    }

    LocationListener locListner = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            atualizarLocalizacao(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void localizacaoAtual(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            return;
        }
        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        atualizarLocalizacao(location);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,0,locListner);
    }

    private void inicializaComponentes() {
        fabNew = (FloatingActionButton) findViewById(R.id.fab_new);
        if(getIntent().getSerializableExtra("civil") != null){
            this.civilLogado = (Civil) getIntent().getSerializableExtra("civil");
        }
    }
}
