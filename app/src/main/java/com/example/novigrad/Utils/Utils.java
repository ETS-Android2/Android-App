package com.example.novigrad.Utils;

import android.content.Context;
import android.content.Intent;

//helper class
public class Utils {

    //constructor for testing
    public Utils (Context context) { }

    public static void deleteAllEmployees(){

    }


    public static String capitalizeFirstLetters(String someString){
        StringBuilder formatted = new StringBuilder();

        someString.toLowerCase();
        String[] someWords = someString.split(" ");

        if(someWords.length == 1){
            formatted.append(someWords[0].substring(0, 1).toUpperCase() + someWords[0].substring(1));
        } else {
            for(String words : someWords){
                formatted.append(words.substring(0, 1).toUpperCase() + words.substring(1));
                if (!words.equals(someWords[someWords.length-1])){
                    formatted.append(" ");
                }
            }
        }

        return formatted.toString();
    }

    public static String formatPostalCode(String someString){
        StringBuilder formatted = new StringBuilder();
        char[] chars = someString.replaceAll("\\s+", "").toUpperCase().toCharArray();

        for(char ch : chars ){
            formatted.append(String.valueOf(ch));
            if (ch == (chars[2])){
                formatted.append(" ");
            }
        }
        return formatted.toString();
    }


    //does not pass info, just starts a new activity. Helpful for activities that do not use User objects.
    public static void travelActivity(Context fromContext, Class toContext){
        Intent intent = new Intent(fromContext, toContext);
        fromContext.startActivity(intent);
    }

}
