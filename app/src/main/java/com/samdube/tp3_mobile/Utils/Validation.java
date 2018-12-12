package com.samdube.tp3_mobile.Utils;

/**
 * Utility class that hold function used to verify input content
 */
public class Validation {

    /** Function that check if the string length is between the specified range
     * @param p_texte string to check
     * @param p_maximum maximum length
     * @param p_minimum minimum length
     * @return true if its in the specified range
     */
    public static boolean Between(String p_texte, int p_maximum, int p_minimum)
    {
        return p_texte.length() <= p_maximum && p_texte.length() >= p_minimum;
    }
}
