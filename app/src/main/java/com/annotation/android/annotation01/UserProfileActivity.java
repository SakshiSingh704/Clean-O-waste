package com.annotation.android.annotation01;

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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annotation.android.annotation01.Fragments.ChangePasswordFragments_;
import com.annotation.android.annotation01.Fragments.ContactUsFragments;
import com.annotation.android.annotation01.Fragments.ContactUsFragments_;
import com.annotation.android.annotation01.Fragments.HomeFragments_;
import com.annotation.android.annotation01.Fragments.MyPostFragments_;
import com.annotation.android.annotation01.Fragments.NewPostFragments;
import com.annotation.android.annotation01.Fragments.NewPostFragments_;
import com.annotation.android.annotation01.Fragments.ProfileFragments;
import com.annotation.android.annotation01.Fragments.ProfileFragments_;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.pojo.LoginResponsePojo;
import com.annotation.android.annotation01.BaseActivity;
import com.annotation.android.annotation01.utils.GlobalEnums;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_user_profile)
public class UserProfileActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Extra
    public static LoginResponseModal loginResponseModal;
    @ViewById
    FrameLayout container;
    @ViewById
    NavigationView nav_view;
    @ViewById
    Toolbar toolbar;
    @ViewById
    DrawerLayout drawer_layout;
    TextView txtUserName, txtUserEmailID;

    public void setLoginResponseModal(LoginResponseModal loginResponseModal) {
        this.loginResponseModal = loginResponseModal;
    }

    @AfterViews
    void loadView(){

        List<LoginResponsePojo> loginresponse = loginResponseModal.getResult();
        String user = loginresponse.get(0).getName();

        try {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setTitle(getResources().getString(R.string.app_name));
            }
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            toggle.syncState();
            nav_view.setNavigationItemSelectedListener(this);
            if (nav_view != null) {
                txtUserName = (TextView) nav_view.getHeaderView(0).findViewById(R.id.txtUserName);
                txtUserEmailID = (TextView) nav_view.getHeaderView(0).findViewById(R.id.txtUserEmail);
                nav_view.setCheckedItem(R.id.nav_home);
                onNavigationItemSelected(nav_view.getMenu().getItem(1));
                if (txtUserName != null) {
                    txtUserName.setText("Welcome "+user);
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
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new ProfileFragments_().builder().loginResponseModal(loginResponseModal).userCode("2").build(), GlobalEnums.FragmentProfile.name()).commit();
                break;

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new HomeFragments_().builder().build(), GlobalEnums.FragmentPost.name()).commit();
                break;

            case R.id.nav_post:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    new MyPostFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentProfile.name()).commit();
                break;
            case R.id.nav_password:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container,
//                        new ChangePasswordFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentChangePassword.name()).commit();
                break;

            case R.id.nav_new_post:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new NewPostFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentPost.name()).commit();
                break;

            case R.id.nav_logout:
                MainActivity_.intent(this).start();
                 break;

            case R.id.nav_contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new ContactUsFragments_().builder().build(), GlobalEnums.FragmentChangePassword.name()).commit();
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        UserProfileActivity_.intent(this).loginResponseModal((LoginResponseModal)loginResponseModal).start();
    }
}
