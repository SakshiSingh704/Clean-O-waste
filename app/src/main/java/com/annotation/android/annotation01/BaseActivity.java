package com.annotation.android.annotation01;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;

import com.annotation.android.annotation01.utils.Utility;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.lang.reflect.Field;

/**
 * Created by BlackHat on 6/29/2017.
 */

@EActivity
public class BaseActivity extends AppCompatActivity {

    @Bean
    public Utility utility;
    public ProgressDialog progressDialog;

    @AfterInject
    void initializeView(){
        progressDialog = new ProgressDialog(this);
        if(utility==null){
            utility = new Utility();
        }
    }

    @AfterViews
    void loadView(){
        try {
            hideKeyBoard();
            //showMenu();
            /*if(ParamsCategory.count(ParamsCategory.class)<36){
                if(!utility.internetConnectivity(getApplicationContext())){
                    showAlertGetDropDownLists(getResources().getString(R.string.no_network));
                    return;
                }
                showProgressDialog();
                getRegDropdownLists();
            }*/

            /*if(UniversityModal.count(UniversityModal.class)<5){
                if(!utility.internetConnectivity(getApplicationContext())){
                    showAlertGetUniversityLists(getResources().getString(R.string.no_network));
                    return;
                }
                showProgressDialog();
                getUniversityLists();
            }*/
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void hideKeyBoard() {
        try {
            View view = getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(BaseActivity.this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Background
    void getRegDropdownLists(){
      //  MasterDataPojo masterDataPojo  = webService.getAllRegistrationDropDownLists();
        //saveRegDropDownLists(masterDataPojo);
    }

    /*@Background
    void saveRegDropDownLists(MasterDataPojo masterDataPojo){
        if(masterDataPojo!=null){
            if(masterDataPojo.getGenderTable()!=null
                    && masterDataPojo.getGenderTable().size()>0){

                for(ParamsCategory paramsCategory:  masterDataPojo.getGenderTable()){
                    ParamsCategory genderDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.gender_table.name());
                    genderDetails.save();
                }
                //caste Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getCasteTable()){
                    ParamsCategory casteDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.caste_table.name());
                    casteDetails.save();
                }
                //Category Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getCategoryTable()){
                    ParamsCategory categorydetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.category_table.name());
                    categorydetails.save();
                }
                //Blood Grp Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getBloodGrpTable()){
                    ParamsCategory bloodGrpDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.blood_grp_table.name());
                    bloodGrpDetails.save();
                }
                //Religion Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getReligionTable()){
                    ParamsCategory religionDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.religion_table.name());
                    religionDetails.save();
                }
                //Marital Status Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getMaritalStatusTable()){
                    ParamsCategory maritalstatusdetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.marital_status_table.name());
                    maritalstatusdetails.save();
                }
                //State Tables Save
                for(StateMaster paramsCategory:  masterDataPojo.getStateTable()){
                    StateMaster stateDetails = new StateMaster(paramsCategory.getState_code(),
                            paramsCategory.getState_id(), paramsCategory.getState_name());
                    stateDetails.save();
                }
                //Subject Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getSubjectTable()){
                    ParamsCategory subjectDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.subject_table.name());
                    subjectDetails.save();
                }
                //Examination Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getExaminationTable()){
                    ParamsCategory examinationDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.examination_table.name());
                    examinationDetails.save();
                }
                //Board Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getBoardTable()){
                    ParamsCategory boardDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.board_table.name());
                    boardDetails.save();
                }
                //Weightage Tables Save
                for(ParamsCategory paramsCategory:  masterDataPojo.getWeightageTable()){
                    ParamsCategory weightageDetails = new ParamsCategory(paramsCategory.getParam_id(),
                            paramsCategory.getParam_type_id(), paramsCategory.getValue(),paramsCategory.getValue_hindi(),
                            GlobalEnums.weightage_table.name());
                    weightageDetails.save();
                }

            }
        }
        hideProgress();
    }*/

    @UiThread
    public void hideProgress(){
        hideProgressDialog();
    }

    @Background
 /*   void getUniversityLists(){
        ApplicationUniversityPojo applicationUniversityPojo = webService.getApplicationUniversityPojo();
        saveUniversityList(applicationUniversityPojo);
    }

    @Background
    void saveUniversityList(ApplicationUniversityPojo applicationUniversityPojo){
        if(applicationUniversityPojo!=null){
            if(applicationUniversityPojo.getUniversityList()!=null
                    && applicationUniversityPojo.getUniversityList().size()>0){
                //University Tables Save
                for(UniversityModal appUnivList:  applicationUniversityPojo.getUniversityList()){
                    UniversityModal universityModal1 = new UniversityModal(appUnivList.getOrg_id(),
                            appUnivList.getOrg_code(), appUnivList.getOrg_name(),appUnivList.getOrg_type_id(),appUnivList.getOrg_type_name(),
                            appUnivList.getOrg_type_name_hindi(),appUnivList.getOrg_level(),appUnivList.getShort_name(),appUnivList.getLogo_path(),
                            appUnivList.getIs_active(),appUnivList.getOrg_parent_id(),appUnivList.getParent_name(),appUnivList.getEmail_id()
                            ,appUnivList.getLandline_no(),appUnivList.getAddress(),appUnivList.getDistrict_id(),appUnivList.getState_id(),appUnivList.getCountry_id(),
                            appUnivList.getPincode(),appUnivList.getIs_private(),appUnivList.getOrg_photo(),appUnivList.getOrg_rank());
                    universityModal1.save();
                }

            }
        }
        hideProgress();
    }
*/
    public void showProgressDialog(){
        try {
            if(progressDialog==null){
                progressDialog = new ProgressDialog(this);
            }
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            if(progressDialog!=null && (!progressDialog.isShowing())) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog(){
        try {
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlertGetDropDownLists(String message) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.info));
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(utility.internetConnectivity(BaseActivity.this)) {
                            showProgressDialog();
                            getRegDropdownLists();
                        }
                        else{
                            utility.showToast(getResources().getString(R.string.no_network));
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void showAlertGetUniversityLists(String message) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                this);
        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.info));
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(utility.internetConnectivity(BaseActivity.this)) {
                            showProgressDialog();
                          //  getUniversityLists();
                        }
                        else{
                            utility.showToast(getResources().getString(R.string.no_network));
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

}