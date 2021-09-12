package com.example.novigrad;

import android.app.Instrumentation;
import android.content.Context;
import android.widget.Button;

import com.example.novigrad.Branch.AddressSearchActivity;
import com.example.novigrad.Branch.SearchActivity;
import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;
import com.example.novigrad.Users.Service;
import com.example.novigrad.Users.User;
import com.example.novigrad.Utils.Authenticate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

//test cases for d4
public class CustomerTest {

    @Mock
    Context mMockContext;


    //used in filterHours method of Customer class, authenticates search by hours
    @Test
    public void testAuthenticateTimeCompare(){
        Authenticate authenticate = new Authenticate(mMockContext);

        boolean checkTimeCompare = authenticate.timeCompare("7:00", "19:00");

        assertFalse(checkTimeCompare);
    }

    //related to search by address
    @Test
    public void testFilterAddress(){
        Customer customer = new Customer("Nick", "Smith", "nick@gmail.com", "Passw0rd!");

        List<User> users = customer.filterAddress("Daly");

        boolean checkIfNotIncluded = users.contains("Daly");  //should be false

        assertFalse(checkIfNotIncluded);
    }

    //related to search by service
    @Test
    public void testFilterServices(){
        Customer customer = new Customer("Vivianne", "Yee", "vivyee@gmail.com", "Passw0rd!");

        List<User> users = customer.filterServices("g1");

        boolean checkIfNotIncluded = users.contains("g1");  //should be false

        assertFalse(checkIfNotIncluded);
    }

    //the following test cases test the authentication of addresses (related to search by address)
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

}
