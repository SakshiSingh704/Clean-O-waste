package com.annotation.android.annotation01;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annotation.android.annotation01.Fragments.ContactUsFragments_;
import com.annotation.android.annotation01.Fragments.HomeFragments_;
import com.annotation.android.annotation01.Fragments.LoginFragments_;
import com.annotation.android.annotation01.Fragments.RegistrationFragments_;
import com.annotation.android.annotation01.utils.GlobalEnums;
import com.annotation.android.annotation01.BaseActivity;
import com.annotation.android.annotation01.utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.annotation.android.annotation01.R.id.toolbar;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @ViewById
    FrameLayout container;
    @ViewById
    NavigationView nav_view;
    @ViewById
    Toolbar toolbar;
    @ViewById
    DrawerLayout drawer_layout;
    TextView txtUserName, txtUserEmailID;
    @Bean
    Utility utility;

    @AfterViews
    void loadView(){
        if (!utility.internetConnectivity(MainActivity.this)) {
            utility.showToast(getResources().getString(R.string.no_network));
            return;
        }
        try {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //toolbar.setTitle(getResources().getString(R.string.login));
                //getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            toggle.syncState();
            nav_view.setNavigationItemSelectedListener(this);
            if (nav_view != null) {
                txtUserName = (TextView) nav_view.getHeaderView(0).findViewById(R.id.txtUserName);
                txtUserEmailID = (TextView) nav_view.getHeaderView(0).findViewById(R.id.txtUserEmail);
                nav_view.setCheckedItem(R.id.nav_home);
                onNavigationItemSelected(nav_view.getMenu().getItem(0));
                if (txtUserName != null) {
                    txtUserName.setText("Welcome User");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
   }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new HomeFragments_().builder().build(), GlobalEnums.FragmentHome.name()).commit();
                break;

            case R.id.nav_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new LoginFragments_().builder().userCode("2").build(), GlobalEnums.FragmentLogin.name()).commit();
                break;

            case R.id.nav_register:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new RegistrationFragments_().builder().build(), GlobalEnums.FragmentRegister.name()).commit();
                break;

            case R.id.nav_admin_login:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new LoginFragments_().builder().userCode("1").build(),GlobalEnums.FragmentContactUs.name()).commit();
                break;

            case R.id.nav_contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new ContactUsFragments_().builder().build(), GlobalEnums.FragmentChangePassword.name()).commit();
                break;

            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new AboutUsFragments_().builder().build(), GlobalEnums.FragmentChangePassword.name()).commit();
                break;

            case R.id.nav_about_team:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new AboutTeamFragments_().builder().build(), GlobalEnums.FragmentChangePassword.name()).commit();
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}