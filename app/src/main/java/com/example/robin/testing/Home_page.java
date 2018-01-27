package com.example.robin.testing;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home_page extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_all);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#827717"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setupWithViewPager(mViewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_page.this,Other_Stuffs.class));
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });
    }
            private Boolean exit = false;
            @Override
            public void onBackPressed() {
                if (exit) {
                    moveTaskToBack(true);
                    finish(); // finish activity
                } else {
                    Toast.makeText(this, "Press Back again to Exit.",
                            Toast.LENGTH_SHORT).show();
                    exit = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            exit = false;
                        }
                    }, 3 * 1000);
                }
            }
            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.menu, menu);
                return true;
              }
                @Override
                public boolean onOptionsItemSelected(MenuItem item) {
                    ConnectivityManager connec =
                            (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                    int id = item.getItemId();
                    if (id == R.id.signout) {
                        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                            firebaseAuth = FirebaseAuth.getInstance();
                            firebaseAuth.signOut();
                            startActivity(new Intent(Home_page.this, Authentication_page.class));
                        }
                        else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

                            Toast.makeText(this, "Cant try this at the moment(Internet not Connected", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                    return super.onOptionsItemSelected(item);
                }


                    public static class PlaceholderFragment extends Fragment {
                    private static final String ARG_SECTION_NUMBER = "section_number";

                    public PlaceholderFragment() {
                    }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
            public static PlaceholderFragment newInstance(int sectionNumber) {
                PlaceholderFragment fragment = new PlaceholderFragment();
                Bundle args = new Bundle();
                args.putInt(ARG_SECTION_NUMBER, sectionNumber);
                fragment.setArguments(args);
                return fragment;
            }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                View rootView = inflater.inflate(R.layout.user_home, container, false);
                return rootView;
                } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                    View rootView = inflater.inflate(R.layout.other_folks, container, false);
                    return rootView;
                }
               return  null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

            @Override
                public Fragment getItem(int position) {
                    // getItem is called to instantiate the fragment for the given page.
                    // Return a PlaceholderFragment (defined as a static inner class below).
                    return PlaceholderFragment.newInstance(position + 1);
                  }

                    @Override
                    public int getCount() {
                        // Show 3 total pages.
                        return 2;
                    }

                        @Override
                        public CharSequence getPageTitle(int position) {
                            switch (position) {
                                case 0:
                                    return "PAGE 1";
                                case 1:
                                    return "PAGE 2";
                            }
                            return null;
                        }
    }
}
