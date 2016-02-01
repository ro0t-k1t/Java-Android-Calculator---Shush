package com.example.ronanpiercehiggins.ronansapp;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;
import android.content.Intent;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.BackendlessCollection;
import com.backendless.property.UserProperty;

import java.util.List;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import com.backendless.property.ObjectProperty;

import java.util.Iterator;

public class MainActivity extends ActionBarActivity implements LocationListener {

    //int counter = 0;

    LocationManager locationManager;
    String provider;

    double venueLat = 53.294979;

    double venueLng = -6.192170;

    Double userLat;
    Double userLng;



    public void clickFunction(View view) {


        /*Person person = new Person();
        person.setName( "Bob" );
        person.setAge( 20 );


        Backendless.Persistence.of( Person.class ).save( person, new AsyncCallback<Person>()
        {
            @Override
            public void handleResponse( Person person )
            {
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
            }
        } );*/


        Backendless.Persistence.of( Person.class).findFirst( new AsyncCallback<Person>(){
            @Override
            public void handleResponse( Person person )
            {
                Log.i("info", person.getName());
            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });


        Backendless.Data.of( BackendlessUser.class ).find( new AsyncCallback<BackendlessCollection<BackendlessUser>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<BackendlessUser> users )
            {
                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                while( userIterator.hasNext() )
                {
                    BackendlessUser user = userIterator.next();

                    Log.i("info", user.getEmail());


                }
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
            }
        } );


        /*Backendless.UserService.describeUserClass( new AsyncCallback<List<UserProperty>>()
        {
            public void handleResponse( List<UserProperty> properties )
            {
                for( UserProperty userProp : properties )
                {
                    Log.i("info", userProp.getName());
                }
            }

            public void handleFault( BackendlessFault fault )
            {
            }
        });*/

        /*String whereClause = "age = 20";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        BackendlessCollection<Person> result = Backendless.Persistence.of( Person.class ).find( dataQuery );*/

        //Log.i("info", result.toString());



        /*BackendlessUser user = new BackendlessUser();
        user.setEmail( "ronan@backendless.com" );
        user.setPassword( "my_super_password" );

        Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
        {

            public void handleResponse( BackendlessUser backendlessUser )
            {
                Log.i( "Registration", backendlessUser.getEmail() + " successfully registered" );
            }
        } );*/


        /*String whereClause = "name = 'Alice'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        BackendlessCollection<Users> result = Backendless.Persistence.of( Users.class ).find( dataQuery );*/






        Log.i("info", "function invoked");


        if (userLat != null && userLng != null) {


            double latDistance = Math.toRadians(userLat - venueLat);
            double lngDistance = Math.toRadians(userLng - venueLng);

            Log.i("info", "inside location conditional");


            double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                    (Math.cos(Math.toRadians(userLat))) *
                            (Math.cos(Math.toRadians(venueLat))) *
                            (Math.sin(lngDistance / 2)) *
                            (Math.sin(lngDistance / 2));

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double dist = 6371 * c;
            if (dist < 0.01) {

                Log.i("info", "inside conditional");


                Toast.makeText(getApplicationContext(), "You are inside the radius", Toast.LENGTH_SHORT).show();

            } else {


                Toast.makeText(getApplicationContext(), "You are outside the radius", Toast.LENGTH_SHORT).show();


            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        String appVersion = "v1";

        Backendless.initApp(this, "A0819152-C875-C222-FF18-0516AB9ACC00", "94E2E030-C1B8-0F27-FFEE-CD829BAE3400", appVersion);

        Log.i("info", "backendless success");









        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);







        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {

            Toast.makeText(getApplicationContext(), "location is not null", Toast.LENGTH_SHORT).show();

            Log.i("info", "location is not null");


        } else {

            Log.i("info", "location is null");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public void onStop() {
        super.onStop();


    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        userLat = location.getLatitude();
        userLng = location.getLongitude();

        Log.i("info", userLat.toString());
        Log.i("info", userLng.toString());



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
