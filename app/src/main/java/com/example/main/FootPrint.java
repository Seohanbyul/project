package com.example.main;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.support.v4.content.ContextCompat.getSystemService;

public class FootPrint extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 15000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 15000;
    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

    private GoogleApiClient mGoogleApiClient;
    private boolean mLocationPermissionGranted;
    private Marker currentMarker = null;
    private final static int MAXENTRIES = 5;
    private String[] LikelyPlaceNames = null;
    private String[] LikelyAddress = null;
    private String[] LikelyAttributions = null;
    private LatLng[] LikelyLatLngs = null;

    ImageButton btnTomorrow, btnYesterday;
    TextView tvToday;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);
    MapView map;
    Date today = cal.getTime();
    GroundOverlayOptions videoMark;
    GoogleMap gMap;
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton btnFab, fabSearch, fabCal, fabToday;


    public FootPrint() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_foot, container, false);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        btnFab = layout.findViewById(R.id.btnFab);
        fabToday = layout.findViewById(R.id.fabToday);
        fabSearch = layout.findViewById(R.id.fabSearch);
        fabCal = layout.findViewById(R.id.fabCal);
        tvToday = layout.findViewById(R.id.tvToday);
        btnTomorrow = layout.findViewById(R.id.btnTomorrow);
        btnYesterday = layout.findViewById(R.id.btnYesterday);
        tvToday.setText(sdf.format(cal.getTime()));
        //TODO 버튼을 클릭하면 FloatingActionButton 애니메이션 실행
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });

        //TODO 오늘 날짜로 이동
        fabToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Toast.makeText(getApplicationContext(), "오늘 날짜로 이동", Toast.LENGTH_SHORT).show();
                cal.set(year, month, day);
                tvToday.setText(sdf.format(today));

            }
        });

        //TODO 장소 검색 화면으로 이동
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(getApplicationContext(), LocSearch.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "장소 검색하기", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO DatePickerDialog 출력
        fabCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                DatePickerDialog dateDialog = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvToday.setText(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일");
                        Toast.makeText(getApplicationContext(), "선택한 날짜로 이동합니당", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                dateDialog.show();
            }
        });

        //TODO 날짜 이동 버튼(하루 전)
        btnYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, -1);
                tvToday.setText(sdf.format(cal.getTime()));
                Toast.makeText(getApplicationContext(), "어제 날짜로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO 날짜 이동 버튼(하루 후)
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.DATE, +1);
                tvToday.setText(sdf.format(cal.getTime()));
                Toast.makeText(getApplicationContext(), "내일 날짜로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO 구글맵 제어
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, this)
                .addConnectionCallbacks(this).build();
        map = layout.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        map.getMapAsync(this);
        return layout;
    }

    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if (currentMarker != null) currentMarker.remove();
        if (location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentMarker = this.gMap.addMarker(markerOptions);
            this.gMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = this.gMap.addMarker(markerOptions);
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!checkLocationServicesStatus()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하십시오.");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                PendingResult<Status> statusPendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            this.gMap.getUiSettings().setCompassEnabled(true);
            this.gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    public void onLocationChanged(Location location) {
        Log.i(TAG, "onLocationChanged call");
        searchCurrentPlaces();
    }

    private void searchCurrentPlaces() {
        @SuppressWarnings("MissingPermission")
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                int i = 0;
                LikelyPlaceNames = new String[MAXENTRIES];
                LikelyAddress = new String[MAXENTRIES];
                LikelyAttributions = new String[MAXENTRIES];
                LikelyLatLngs = new LatLng[MAXENTRIES];
                for (PlaceLikelihood placeLikelihood : placeLikelihoods) {
                    LikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                    LikelyAddress[i] = (String) placeLikelihood.getPlace().getAddress();
                    LikelyAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
                    LikelyLatLngs[i] = placeLikelihood.getPlace().getLatLng();
                    i++;
                    if (i > MAXENTRIES - 1) {
                        break;
                    }
                }
                placeLikelihoods.release();
                Location location = new Location("");
                location.setLatitude(LikelyLatLngs[0].latitude);
                location.setLongitude(LikelyLatLngs[0].longitude);
                setCurrentLocation(location, LikelyPlaceNames[0], LikelyAddress[0]);
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude(DEFAULT_LOCATION.longitude);

        setCurrentLocation(location, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS활성 여부 확인");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (Integer.toString(requestCode) == Manifest.permission.ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mLocationPermissionGranted = true;
        }
        updateLocationUI();
    }

    //TODO 위치 권한 설정 여부에 따라 구글맵에 디바이스의 위치 설정
    private void updateLocationUI() {
        if (map == null) return;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Integer.parseInt(Manifest.permission.ACCESS_FINE_LOCATION));
        }
//        if(mLocationPermissionGranted){
//            map.setMyLocationEnabled(true);
//            map.getUiSettings().setMyLocationEnbaled(true);
//        }else{
//            map.setMyLocationEnabled(false);
//            map.getUiSettings().setMyLocationEnabled(false);
//            mLastKnownLocation=null;
//        }
    }

    //TODO 디바이스의 위치정보를 얻어 맵 이동
    private void getDeviceLocation() {
//        if(mLocationPermissionGranted){
//            mLastKnownLocation = LocationServices.Fused
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_NETWORK_LOST) {
            Log.e(TAG, "onConnectionSuspended();GooglePlayServices" + "connection lost. Cause:network lost.");
        } else if (i == CAUSE_SERVICE_DISCONNECTED) {
            Log.e(TAG, "onConnectionSuspended();GooglePlayServices" + "connection lost. Cause:service disconnected.");

        }
    }

    //TODO FloatingActionButton에 애니메이션 설정
    public void anim() {
        if (isFabOpen) {
            fabCal.startAnimation(fab_close);
            fabSearch.startAnimation(fab_close);
            fabToday.startAnimation(fab_close);
            fabCal.setClickable(false);
            fabSearch.setClickable(false);
            fabToday.setClickable(false);
            isFabOpen = false;
        } else {
            fabCal.startAnimation(fab_open);
            fabSearch.startAnimation(fab_open);
            fabToday.startAnimation(fab_open);
            fabCal.setClickable(true);
            fabSearch.setClickable(true);
            fabToday.setClickable(true);
            isFabOpen = true;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        setCurrentLocation(null, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS활성 여부 확인");
        gMap.getUiSettings().setCompassEnabled(true);
        gMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    gMap.setMyLocationEnabled(true);
                }
            }
        } else {
            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            }
            gMap.setMyLocationEnabled(true);
        }
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.584, 126.925), 15));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                videoMark = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.marker)).position(latLng, 100f, 100f);
                gMap.addGroundOverlay(videoMark);
            }
        });
        //TODO 현재위치 버튼 추가하기
        // if(ActivityCompat.checkSelfPermission(this,this.Manifest.permission.ACCESS_FINE_LOCATION))
//        if (gMap != null) {
//            LatLng latLng = new LatLng(37.566643, 126.978279);
//            CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(16f).build();
//            gMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark));
//            markerOptions.position(latLng);
//            gMap.addMarker(markerOptions);
//            MyGeocodingThread thread = new MyGeocodingThread(latLng);
//            thread.start();
//        }

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    class MyGeocodingThread extends Thread {
        LatLng latLng;

        public MyGeocodingThread(LatLng _latLng) {
            latLng = _latLng;
        }

        @Override
        public void run() {
            Geocoder geocoder = new Geocoder(getApplicationContext());
            List<Address> addresses = null;
            String addressText = "";
            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                Thread.sleep(500);
                if (addresses != null && addresses.size() > 0) {
                    Address address = addresses.get(0);
                    addressText = address.getAdminArea() + "" + (address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : address.getLocality()) + "";
                    String txt = address.getSubLocality();
                    if (txt != null) addressText += txt + "";
                    addressText += address.getThoroughfare() + "" + address.getSubThoroughfare();

                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = addressText;
                    handler.sendMessage(msg);
                }
            } catch (Exception e) {
            }
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Toast.makeText(getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        map.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
        ;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
        if (mGoogleApiClient != null) mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onLowMemory();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.unregisterConnectionFailedListener(this);
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            if (mGoogleApiClient.isConnected()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
                mGoogleApiClient.disconnect();
            }
        }
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        MapsInitializer.initialize(getActivity().getApplicationContext());
//        if (map != null) {
//            map.onCreate(savedInstanceState);
//        }
//    }
}

