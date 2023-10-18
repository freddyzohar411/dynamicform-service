package com.avensys.rts.formservice.util;

public class StringUtil {

    public static String convertCamelCaseToTitleCase(String camelCaseString) {
        StringBuilder result = new StringBuilder();

        for (char c : camelCaseString.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(' ');
            }
            result.append(Character.toLowerCase(c));
        }

        result.setCharAt(0, Character.toUpperCase(result.charAt(0)));
        return result.toString();
    }

    public static String convertCamelCaseToTitleCase2(String camelCaseString) {
        StringBuilder result = new StringBuilder();

        for (char c : camelCaseString.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(' ');
            }
            result.append(c);
        }

        String[] words = result.toString().split(" ");
        result.setLength(0);

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(' ');
            }
        }

        return result.toString().trim();
    }

}
