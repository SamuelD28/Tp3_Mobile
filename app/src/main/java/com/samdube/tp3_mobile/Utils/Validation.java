package com.samdube.tp3_mobile.Utils;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    public static boolean MinimumLength(String p_texte, int p_minimum)
    {
        return  p_texte.length() >= p_minimum;
    }

    public static boolean MaximumLength(String p_texte, int p_maximum)
    {
         return p_texte.length() <= p_maximum;
    }

    public static boolean Entre(String p_texte, int p_maximum, int p_minimum)
    {
        return p_texte.length() <= p_maximum && p_texte.length() >= p_minimum;
    }

    public static boolean MinimumMaj(String p_texte, int p_minimum)
    {
        List<Character> listMaj = new ArrayList<Character>();
        
        for(char c : p_texte.toCharArray())
        {
            if(Character.isUpperCase(c)) {
                listMaj.add(c);
            }
        }

        return listMaj.size() >= p_minimum;
    }

    public static boolean IsEmpty(String p_texte)
    {
        return p_texte.length() == 0;
    }

    public  static  boolean MinimumNumbers(String p_texte, int p_minimum)
    {
        List<Character> listMaj = new ArrayList<Character>();

        for(char c : p_texte.toCharArray())
        {
            if(Character.isDigit(c)) {
                listMaj.add(c);
            }
        }

        return listMaj.size() >= p_minimum;
    }

}
