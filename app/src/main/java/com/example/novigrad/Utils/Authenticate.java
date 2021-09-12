package com.example.novigrad.Utils;
import android.content.Context;

import java.util.regex.Pattern;

public class Authenticate {

        //constructor for testing
        public Authenticate(Context context) {}

        private static boolean verification(String input, String regex) {
                try {
                        if (input == null || input.equals(null) || input.length() <= 2) {
                                return false;
                        }
                } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                }
                input = input.replaceAll("\\s+", ""); //removes all white space in input

                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(input).matches();
        }

        //ie. Canada: H0H 0H0
        public static boolean authenticatePostalCode(String input){
                //https://gist.github.com/jamesbar2/1c677c22df8f21e869cca7e439fc3f5b

                String regex = "^(?=[^DdFfIiOoQqUu\\\\d\\\\s])[A-Za-z]\\\\d(?=[^DdFfIiOoQqUu\\\\d\\\\s])[A-Za-z]\\\\s{0,1}\\\\d(?=[^DdFfIiOoQqUu\\\\d\\\\s])[A-Za-z]\\\\d$";

                return verification(input, regex);

        }

        //ie. USA
        public static boolean authenticateZipCode(String input){
                //https://gist.OH github.com/jamesbar2/1c677c22df8f21e869cca7e439fc3f5b

                String regex = "^\\b\\d{5}\\b(?:[- ]{1}\\d{4})?$";

                return verification(input, regex);

        }

        public static boolean authenticateUsername(String input) {
                String regex = "\\b[a-zA-Z][a-zA-Z0-9\\-._]{3,}\\b";
                return verification(input, regex);
        }

        public static boolean authenticatePrice(String input) {

                String regex = "^(0(?!\\.00)|[1-9]\\d{0,6})\\.\\d{2}$";

                return verification(input, regex);
        }

        public static boolean authenticatePassword(String input) {

        /* password must have at least:
        (?=.*[0-9]) a digit must occur at least once
        (?=.*[a-z]) a lower case letter must occur at least once
        (?=.*[A-Z]) an upper case letter must occur at least once
        (?=.*[@#$%^&!+=]) a special character must occur at least once
        (?=\\S+$) no whitespace allowed in the entire string
        .{8,} at least 8 characters

        //source: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
Shareable UML-Online Doc: [Click Me](https://cruise.umple.org/umpleonline/umple.php?model=200916jctrj2jz9gs5)
        */

                String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&!+=])(?=\\S+$).{6,}";
                return verification(input, regex);
        }

        public static boolean authenticateName(String input) {
                //source: http://www.java2s.com/Tutorial/Java/0120__Development/Validatethefirstnameandlastname.htm
                String regex = "[a-zA-z]+([ '-][a-zA-Z]+)*";

                return verification(input, regex);
        }

        public static boolean authenticateEmail(String input) {
                //source: https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
                String regex = "^[a-zA-Z0-9_!#$%&'*+=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                return verification(input, regex);
        }

        public static boolean isEmployee(String input) {
                try {
                        if (input == null || input.equals(null) || input.length() <= 2) {
                                return false;
                        }
                } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                }
                //precondition: email already authenticated by this point
                return input.split("@")[1].toLowerCase().equals("novigrad.ca");
        }

        public static boolean isAdmin(String input) {
                //precondition: email already authenticated by this point
                try {
                        if (input == null || input.equals(null) || input.length() <= 2) {
                                return false;
                        }
                } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                }

                return isEmployee(input) && input.split("@")[0].toLowerCase().equals("admin");
        }



        //FOLLOWING TWO METHODS FOR NEXT DELIVERABLE *please ignore*
        //address verification
        public static boolean authenticateStreetName(String input){
                //source: http://www.java2s.com/Tutorial/Java/0120__Development/Validatecityandstate.htm
                String regex ="([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
                return verification(input,regex);
        }

        public static boolean authenticateStreetNum(String input){
                //source: https://www.codementor.io/@seranguyen/java-regular-expression-part-3-matching-text-for-validation-2-r3lnwr612
                if(input.length() == 0){
                        return false;
                }
                String regex = "[0-9]{1,}";            //assumption: unit number will be between 0-9999
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(input).matches();
        }

        public static boolean authenticateCountry(String input){
                //source: http://www.java2s.com/Tutorial/Java/0120__Development/ValidateAddress.htm
                return input.toLowerCase().equals("canada");
        }

        public static boolean authenticateUnit(String input){
                //source: https://www.codementor.io/@seranguyen/java-regular-expression-part-3-matching-text-for-validation-2-r3lnwr612
                if (input.length() == 0){ return true;}
                return authenticateStreetNum(input);
        }

        public static boolean authenticateZip(String input){
                /*
                Canadian Postal Code Format:
                A1A 1A1, where:
                - A is any letter in the alphabet with the exception of: DFIOQU
                - 1 is any digit between 0 and 9
                */
                //source:https://howtodoinjava.com/java/regex/canada-postal-code-validation/
                String regex ="^(?!.*[DFIOQU])[a-zA-Z][0-9][a-zA-Z] ?[0-9][a-zA-Z][0-9]$";
                return verification(input,regex);
        }

        public static boolean authenticateCity(String input){
                //source: http://www.java2s.com/Tutorial/Java/0120__Development/Validatecityandstate.htm
                String regex = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
                return verification(input,regex);
        }

        public static boolean authenticateProvince(String input){
                /*//source: http://www.java2s.com/Tutorial/Java/0120__Development/Validatecityandstate.htm
                String regex ="([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
                return verification(input,regex);*/
                input = input.replaceAll("\\s+", ""); //removes all white space in input
                return input.toLowerCase().equals("novigrad");
        }

        public static boolean authenticatePhoneNum(String input){
                //source: https://howtodoinjava.com/java/regex/java-regex-validate-and-format-north-american-phone-numbers/
                if(input.length() != 10){
                        return false;
                }
                String regex="^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(input).matches();
                //(000) 000-0000 --> setup for phone number
        }
        public static boolean authenticateAddress(String input){
        /*
           input street Number
           input street Name
           input city
           input postal code
           input province
         */
                String regex = null;
                return verification(input, regex);
        }


        public static boolean timeCompare(String openTime, String closeTime){
                if(openTime.equals("Closed") ^ closeTime.equals("Closed")){ return false; }
                return (Integer.valueOf(closeTime.split(":")[0]) - Integer.valueOf(openTime.split(":")[0])) <= 0;
        }

        public static boolean timeCompare(String openTime, String between, String closingTime){
                if(openTime.equals("Closed") || openTime.equals("Closing") || closingTime.equals("Closed") || closingTime.equals(("Closing"))){ return false; }
                return ((Integer.valueOf(between.split(":")[0]) - Integer.valueOf(openTime.split(":")[0])) >= 0) && ((Integer.valueOf(closingTime.split(":")[0]) - Integer.valueOf(between.split(":")[0])) >= 0);
        }



}
