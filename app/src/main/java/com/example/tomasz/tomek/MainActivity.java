package com.example.tomasz.tomek;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PrefsFragment.OnFragmentInteractionListener,fragmentMain.OnFragmentInteractionListener,addInfo.OnFragmentInteractionListener{

    FragmentManager fragmentManager = getFragmentManager();

    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Fragment fragment = new fragmentMain();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();



         }
    }
//dodawanie pozycji do panelu navigacyjnego wraz z edycja 
    private void addDrawerItems() {
        String[] osArray = { "General Info","Settings","Additional battery info","Upgrade to PRO", "About" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setBackgroundResource(R.color.indigo_300);



        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment objFragment = null;

                switch (position) {
                    case 0: {
                        objFragment = new fragmentMain();
                        setTitle("Info");

                        break;
                    }
                    case 1: {
                        objFragment = new PrefsFragment();
                        setTitle("Settings");

                        break;
                    }
                    case 2: {
                        objFragment = new addInfo();
                        setTitle("Additional Info");

                        break;
                    }
                    case 3: {
                        // final String appPackageName = getPackageName(); 
                        objFragment = new fragmentMain();
                        openAppRating(getApplicationContext());

                        break;
                    }
                }

                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.beginTransaction()
                        .replace(R.id.content_frame, objFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
                mDrawerLayout.closeDrawer(mDrawerList);


            }

        });
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

           
             //otwarcie panelu
             
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //getSupportActionBar().setTitle("BatteryWidget");
                invalidateOptionsMenu();
            }

            //zamkniecie panelu
             
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); 
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // odczyt menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       //sprawdzenie czy klik na action barze
        int id = item.getItemId();

        

      // uruchomienie navigation drawera
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String title) {
        getActionBar().setTitle(title);


    }



}




