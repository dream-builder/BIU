package com.bitsofttech.biu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentResponse {

    Fragment fragment;
    FragmentManager fragmentManager;
    AppPreference appPreference;
    ReadAssetFile readAddetFile;
    String content = null;
    ImageView logo;
    DrawerLayout drawer;
    DatabaseHelper databaseHelper;
    private AdView mAdView;
    private static Integer frg=0;
    WebService webservice;
    Check check;
    AlertDialog.Builder builder;
    AppPermisssion appPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();

        syncNews();
        loadDesktop();
        nav();
        checkPermission();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void checkPermission() {
        appPermission.checkInternetPermission();
        appPermission.checkReadContactPermission();
    }

    private void syncNews(){
        if(check.checkInternet()) {

            builder.setTitle("Sync");
            builder.setMessage("Do you want to sync your app now?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    if(check.checkInternet()) {

                        webservice = new WebService(MainActivity.this);
                        webservice.execute("http://biu.ac.bd/?page_id=2510");

                        if(appPreference.get_pref(appPreference.CONTACTSYNC)!="1") {
                            ReadContact readContact = new ReadContact(MainActivity.this);
                            readContact.read();
                        }

                    }
                    dialog.dismiss();
                    // new JsonTask().execute();

                }
            });

            builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            android.app.AlertDialog alert = builder.create();
            alert.show();

        }
    }



    private void init() {
        builder = new AlertDialog.Builder(this);
        check = new Check(this);
        databaseHelper = new DatabaseHelper(this);
        appPreference= new AppPreference(this);
        readAddetFile = new ReadAssetFile(this);
        appPermission = new AppPermisssion(this);
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(frg==0) {

                new AlertDialog.Builder(this)
                        .setMessage("Do you realy want to close?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else{

                loadDesktop();
            }
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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
            //databaseHelper.openDB();
            //databaseHelper.deleteAllPost();
            //databaseHelper.closeDB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            content=readAddetFile.read_file("about_biu.txt");
            loadFragment("About BIU",R.drawable.biu_building,content );

        } else if (id == R.id.nav_chairman) {
            content=readAddetFile.read_file("message_chairman.html");
            loadFragment("Message from the Chairman",R.drawable.zafree,content );

        } else if (id == R.id.nav_vc) {
            content=readAddetFile.read_file("message_vc.html");
            loadFragment("Message of Vice-chancellor",R.drawable.vc,content );

        } else if (id == R.id.nav_trustee) {
            content=readAddetFile.read_file("board_of_trustee.html");
            loadFragment("Board of Trustee",R.drawable.biu_building,content );

        } else if (id == R.id.nav_syndicate) {
            content=readAddetFile.read_file("syndicate.html");
            loadFragment("Syndicate",R.drawable.biu_building,content );

        } else if (id == R.id.nav_academic_council) {
            content=readAddetFile.read_file("academic_council.html");
            loadFragment("Academic Council",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_apply) {
            content=readAddetFile.read_file("how_to_apply.html");
            loadFragment("How to Apply",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_prerequiresities) {
            content=readAddetFile.read_file("prerequisities.html");
            loadFragment("PREREQUISITES FOR ADMISSION",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_scholarship) {
            content=readAddetFile.read_file("scholarship.html");
            loadFragment("Scholarships & Waiver",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_admission_office) {
            content=readAddetFile.read_file("admission_office.html");
            loadFragment("Admission Office",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_contact) {
            content=readAddetFile.read_file("contact.html");
            loadFragment("Contact",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_academic_regulation) {
            content=readAddetFile.read_file("regulation.html");
            loadFragment("Academic Regulations",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_academic_calendar) {
            content=readAddetFile.read_file("academic_calendar.html");
            loadFragment("Academic Calendar",R.drawable.biu_building,content );
        }

        else if (id == R.id.nav_academic_grading_system) {
            content=readAddetFile.read_file("grading_system.html");
            loadFragment("Grading System",R.drawable.biu_building,content );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

    public void loadFragment(String title, int imageID,String data){

        appPreference.set_pref(appPreference.PAGETITLE,title);
        appPreference.set_pref(appPreference.PAGEIMAGE,String.valueOf(imageID));
        appPreference.set_pref(appPreference.PAGECONTENT,data);

        fragment = new biu_info();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main,fragment).commit();

        frg=1;

    }


    private void loadDesktop(){

        fragment = new Desktop_fragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main,fragment).commit();
        frg=0;
    }

    private void nav(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);

        logo = (ImageView) headerview.findViewById(R.id.logoImageView);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDesktop();

                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {

        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onFragmentLoadCompleted(String fragment) {

        switch (fragment){

            case "0":
                content=readAddetFile.read_file("about_biu.txt");
                loadFragment("About BIU",R.drawable.biu_building,content );
                break;
            case "1":
                content=readAddetFile.read_file("message_chairman.html");
                loadFragment("Message from the Chairman",R.drawable.zafree,content );
                break;

            case "2":
                content=readAddetFile.read_file("message_vc.html");
                loadFragment("Message of Vice-chancellor",R.drawable.vc,content );
                break;

            case "3":
                content=readAddetFile.read_file("board_of_trustee.html");
                loadFragment("Board of Trustee",R.drawable.biu_building,content );
                break;

            case "4":
                content=readAddetFile.read_file("syndicate.html");
                loadFragment("Syndicate",R.drawable.biu_building,content );
                break;

            case "5":
                content=readAddetFile.read_file("academic_council.html");
                loadFragment("Academic Council",R.drawable.biu_building,content );
                break;

            case "6":
                Fragment fragmentNews;
                fragmentNews = new NewsEventFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main,fragmentNews).commit();

                frg=1;
                break;


            case "7":

                /*Fragment fragmentResult;
                fragmentResult = new ResultFragment();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main,fragmentResult).commit();

                frg=1;
                break;*/
                content=readAddetFile.read_file("result.html");
                loadFragment("Result",R.drawable.biu_building,content );
                break;

            case "8":
                content=readAddetFile.read_file("contact.html");
                loadFragment("Contact",R.drawable.biu_building,content );
                break;
        }

        frg=1;
    }
}
