package com.formos.stripe.service.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class StringUtil {

    public static final Integer AFFLIATE_NAME_MAX_LENGTH = 30;
    public static final String ELIPSIS_SIGN = "...";
    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 100;

    public static boolean hasValue(String str) {
        return (str != null && !str.trim().isEmpty());
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().isEmpty());
    }

    public static Boolean isEmpty(Object value) {
        return (value == null || isEmpty(value.toString()));
    }

    public static String formatPhone(String tel) {
        if (tel == null) return "";

        String number = tel.trim();
        if (!NumberUtil.isNumeric(number)) {
            return tel;
        }

        if (number.length() == 10) {
            return "(" + number.substring(0, 3) + ") " + number.substring(3, 6) + "-" + number.substring(6);
        } else if (number.length() == 7) {
            return number.substring(0, 3) + "-" + number.substring(3);
        } else {
            return tel;
        }
    }

    public static String trimLongEmail(String email) {
        if (StringUtils.isNotBlank(email) && email.length() > 24) {
            String shortEmail = email.substring(email.indexOf("@"));
            int endIndex = 20 - shortEmail.length();
            shortEmail = (endIndex > 0 ? email.substring(0, endIndex) : "") + "..." + shortEmail;
            return shortEmail;
        }
        return email;
    }

    public static String replaceDynamicText(String content, Map<String, String> dictionary) {
        String replaceContent = content;
        if (dictionary.isEmpty() || isEmpty(replaceContent)) {
            return replaceContent;
        }

        for (Entry<String, String> val : dictionary.entrySet()) {
            replaceContent = StringUtils.replaceIgnoreCase(replaceContent, val.getKey(), val.getValue());
        }
        return replaceContent;
    }

    public static boolean isValidEmail(String email) {
        // orig => String emailRegex =
        // "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email == null) {
            return false;
        }
        if (email.length() < EMAIL_MIN_LENGTH || email.length() > EMAIL_MAX_LENGTH) {
            return false;
        }

        String emailRegex =
            "^(?=.{1,254}$)(?=.{1,64}@)[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        Pattern pat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        return pat.matcher(email).matches();
    }

    public static String replaceValidString(String str) {
        if (str.toLowerCase().equals("null".toLowerCase()) || str.toLowerCase().equals("#VALUE!".toLowerCase())) {
            return "";
        }
        return str;
    }

    public static String replaceValidNullString(String str) {
        if (str.equals("") || str.toLowerCase().equals("null".toLowerCase()) || str.toLowerCase().equals("#VALUE!".toLowerCase())) {
            return null;
        }
        return str;
    }

    public static String removeWhiteSpaceChars(String text) {
        String str = text.replace(StringUtils.SPACE, StringUtils.EMPTY);
        while (str.indexOf(StringUtils.SPACE) != -1) {
            str = str.replace(StringUtils.SPACE, StringUtils.EMPTY);
        }
        return str;
    }
}
