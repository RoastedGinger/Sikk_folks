package com.example.robin.testing;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class Home_page extends AppCompatActivity implements ActionBar.TabListener {
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_page);

            /*actionBar=getActionBar();
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            ActionBar.Tab tab1 = actionBar.newTab();
            tab1.setText("Tab1");
            tab1.setTabListener(this);*/


          /*  User_home user_home = new User_home();
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.homepage,user_home,"homepage");
            fragmentTransaction.commit();  */
        }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
             ///
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu,menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if(id==R.id.signout)
            {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                startActivity(new Intent(Home_page.this,Authentication_page.class));
            }
            return super.onOptionsItemSelected(item);
        }


}
