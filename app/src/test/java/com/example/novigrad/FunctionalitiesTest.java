package com.example.novigrad;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.example.novigrad.Branch.AddressDialog;
import com.example.novigrad.Branch.BranchProfileActivity;
import com.example.novigrad.Branch.NumDialog;
import com.example.novigrad.Service.AdminServiceListFragment;
import com.example.novigrad.Service.EditServiceActivity;
import com.example.novigrad.Service.ServiceFormActivity;
import com.example.novigrad.Service.ServiceListActivity;
import com.example.novigrad.Utils.Authenticate;
import com.example.novigrad.Utils.Utils;

import static androidx.fragment.app.testing.FragmentScenario.launchInContainer;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

//test cases for d2 and d3
@RunWith(AndroidJUnit4.class)
public class FunctionalitiesTest {

    @Mock
    Context mMockContext;


//    @Test
//    public void testAdminUpdateServiceButton(){
//
//        // register next activity that need to be monitored.
//        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ServiceListActivity.class.getName(), null, false);
//
//        //open current activity
//        EditServiceActivity testActivity = new EditServiceActivity(mMockContext);
//        final Button button = (Button) testActivity.findViewById(R.id.updateBtn);
//        testActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // click button and open next activity.
//                button.performClick();
//            }
//        });
//
//        //Watch for the timeout
//        //example values 5000 if in ms, or 5 if it's in seconds.
//        ServiceListActivity nextActivity = (ServiceListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
//        // next activity is opened and captured.
//        assertNotNull(nextActivity);
//        nextActivity.finish();
//    }

//    @Test
//    public void testAdminViewButton(){
//        // register next activity that need to be monitored.
//        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(ServiceFormActivity.class.getName(), null, false);
//
//        //open current fragment
//        launchInContainer(AdminServiceListFragment.class);
//        onView(withId(R.id.viewBtn)).perform(click());
//
//        //Watch for the timeout
//        //example values 5000 if in ms, or 5 if it's in seconds.
//        ServiceFormActivity nextActivity = (ServiceFormActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
//        // next activity is opened and captured.
//        assertNotNull(nextActivity);
//        nextActivity.finish();
//    }

    @Test
    public void testAuthenticateZipCanada(){

        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkPostalCode = authenticate.authenticateZip("L6M 4S8");

        assertTrue(checkPostalCode);

    }

    @Test
    public void testAuthenticateZipCode(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkZipCode = authenticate.authenticateZipCode("90210");

        assertTrue(checkZipCode);
    }

    @Test
    public void testAuthenticateCity(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkCity = authenticate.authenticateCity("Oakville");

        assertTrue(checkCity);
    }

    @Test
    public void testAuthenticatePhoneNumber(){
        Authenticate authenticate = new Authenticate(mMockContext);

       // boolean checkPhone1 = authenticate.authenticatePhoneNum("(289) 681 1448");
        boolean checkPhone2 = authenticate.authenticatePhoneNum("2896811448");
        //boolean checkPhone3 = authenticate.authenticatePhoneNum("289-681-1448");
        //boolean checkPhone4 = authenticate.authenticatePhoneNum("289.681.1448");
        //boolean checkPhone5 = authenticate.authenticatePhoneNum("");

        assertTrue(checkPhone2 );
    }

    @Test
    public void testAuthenticateProvince(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkProv = authenticate.authenticateProvince("Ontario");

        assertFalse(checkProv);
    }

    @Test
    public void testAuthenticateUnit(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkUnit = authenticate.authenticateUnit("92");

        assertTrue(checkUnit); //check if this should be true or false
    }

    @Test
    public void testAuthenticateStreetNumber(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkStreetNumber = authenticate.authenticateStreetNum("2379");

        assertTrue(checkStreetNumber);
    }

    @Test
    public void testAuthenticateStreetName(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkStreetName = authenticate.authenticateStreetName("Daly Avenue");

        assertTrue(checkStreetName);
    }

    @Test
    public void testAuthenticateCountry(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkCountry = authenticate.authenticateCountry("France");

        assertFalse(checkCountry);
    }

    @Test
    public void testCapitalizeFirstLetters(){
        Utils utils = new Utils(mMockContext);

        String testString = "love";

        String newString = utils.capitalizeFirstLetters(testString);

        assertTrue(newString.equals("Love"));
    }

//    @Test
//    public void testBranchProfileActivityOpenAdDialogue(){
//        BranchProfileActivity branchProfileActivity = new BranchProfileActivity(mMockContext);
//
//        FragmentOnAttachListener = fra
//        FragmentManager fragMgr = addFragmentOnAttachListener()
//                branchProfileActivity.getSupportFragmentManager();
//        branchProfileActivity.openAdDialog();
//        AddressDialog myFragment = (AddressDialog) fragMgr.findFragmentByTag("Address");
//
//        assertTrue(myFragment != null && myFragment.isVisible());
//
//    }


}

