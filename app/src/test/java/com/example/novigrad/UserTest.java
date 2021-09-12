package com.example.novigrad;

import com.example.novigrad.Users.Customer;
import com.example.novigrad.Users.Employee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

//UNIT TESTING WITH JUNIT4
public class UserTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting Up Testing");

    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Testing Ended");
    }

    //TESTS FOR GETTERS

    @Test
    public void getFirstNameCustomerTest() {
  
        Customer client = new Customer("Miguel", "Garzon", "mgarzon@gmail.com", "SEg2105$$");
        assertEquals("Miguel", client.getFirstName());
    }

    @Test
    public void getFirstNameEmployeeTest() {
        Employee worker = new Employee("Vivienne", "Cruz", "vcruz@novigrad.ca", "vivienneNovigrad2020!");
        assertEquals("Vivienne", worker.getFirstName());
    }

    @Test
    public void getEmailEmployeeTest() {
        Employee workerTwo = new Employee("Dhari", "Dixit", "ddixit@novigrad.ca", "dhariNovigrad2020!");
        assertEquals("ddixit@novigrad.ca", workerTwo.getEmail());
    }

    @Test
    public void getEmailCustomerTest() {
        Customer clientTwo = new Customer("Vivianne", "Yee", "vyee@gmail.com", "citizenTwoNovigrad2020!");
        assertEquals("vyee@gmail.com", clientTwo.getEmail());
    }

    @Test
    public void getROLEEmployeeTest() {
        Employee workerThree = new Employee("Rachel", "Jamer", "rjamer@novigrad.ca", "billyNovigrad2020!");
        assertEquals("employee", workerThree.getROLE());
    }

    @Test
    public void getROLECustomerTest() {
        Customer clientThree = new Customer("Billy", "Bolton", "bbolt@gmail.com", "citizenThreeNovigrad2020!");
        assertEquals("customer", clientThree.getROLE());
    }

    @Test
    public void getFirstNameNullTest() {
        Customer clientFour = new Customer(null, "Pasta", "npasta@gmail.com", "citizenFourNovigrad2020!");
        assertEquals(null, clientFour.getFirstName());
    }

    @Test
    public void manyClientsTest() {
        Customer clientFive = new Customer("Avocado", "Toast", "atoast@gmail.com", "citizenFiveNovigrad2020!");
        Customer clientSix = new Customer("Avocado", "EggRoll", "aeggroll@gmail.com", "citizenSixNovigrad2020!");

        assertEquals("Avocado", clientSix.getFirstName());
        assertEquals("Avocado", clientFive.getFirstName());

        assertEquals("aeggroll@gmail.com", clientSix.getEmail());
    }
}