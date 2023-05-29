package com.formos.stripe.service.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public class NumberUtil {

    public static NumberFormat PERCENT_FORMAT;

    public static final int CURRENCY = 1;
    public static final int PERCENT = 2;

    public static final int SC_NUMBER_OF_DECIMAL = 1;

    private static NumberFormat currencyInNF;
    private static NumberFormat decimalInNF;
    private static NumberFormat percentNF;
    public static DecimalFormat CURRENCY_DOLLAR_FORMAT_NO_DECIMAL; // Sign for negative currency
    public static DecimalFormat CURRENCY_DOLLAR_FORMAT_ONE_DECIMAL; // Sign for negative currency

    public static final double FULL_PERCENT = 100.0;
    public static final double HALF_PERCENT = 50.0;

    @SuppressWarnings("unused")
    private static NumberFormat integerNF;

    @SuppressWarnings("unused")
    private static NumberFormat floatNF;

    private static NumberFormat hourNF;

    static {
        PERCENT_FORMAT = NumberFormat.getPercentInstance();
        PERCENT_FORMAT.setMinimumFractionDigits(2);
        PERCENT_FORMAT.setMaximumFractionDigits(2);

        currencyInNF = NumberFormat.getCurrencyInstance(Locale.US);
        currencyInNF.setMaximumFractionDigits(4);

        decimalInNF = DecimalFormat.getInstance();
        decimalInNF.setMaximumFractionDigits(4);

        percentNF = NumberFormat.getPercentInstance();
        percentNF.setMaximumIntegerDigits(3);
        percentNF.setMaximumFractionDigits(0);

        integerNF = NumberFormat.getIntegerInstance();
        floatNF = NumberFormat.getIntegerInstance();

        hourNF = NumberFormat.getInstance();
        hourNF.setMaximumIntegerDigits(2);
        hourNF.setMaximumFractionDigits(1);

        CURRENCY_DOLLAR_FORMAT_NO_DECIMAL = new DecimalFormat("$###,###");
        CURRENCY_DOLLAR_FORMAT_ONE_DECIMAL = new DecimalFormat("$###,###.#");
    }

    public static String formatCurrency(double number, int decimals) {
        number = roundUpDouble(number, decimals);
        NumberFormat currencyOutNF = NumberFormat.getCurrencyInstance(Locale.US);
        currencyOutNF.setMaximumFractionDigits(decimals);
        currencyOutNF.setMinimumFractionDigits(decimals);
        return currencyOutNF.format(number);
    }

    public static String formatSignedCurrency(double number, int decimals) {
        number = roundUpDouble(number, decimals);
        DecimalFormat currencyNegSign = new DecimalFormat("###,###,##0.00");
        currencyNegSign.setPositivePrefix(Currency.getInstance(Locale.getDefault()).getSymbol());
        currencyNegSign.setNegativePrefix(Currency.getInstance(Locale.getDefault()).getSymbol() + "-");
        currencyNegSign.setMinimumIntegerDigits(2);
        currencyNegSign.setMaximumFractionDigits(decimals);
        currencyNegSign.setMinimumFractionDigits(decimals);
        return currencyNegSign.format(number);
    }

    /**
     * Format currency
     *
     * @param number
     * @return
     */
    public static String formatCurrency(BigDecimal number) {
        if (number == null) {
            number = new BigDecimal(0);
        }

        String strValue = number.toString();
        int decPos = strValue.indexOf(".");
        int newScale;
        BigDecimal newBigD;
        // Strip trailing zeros after cents
        if (decPos > -1) {
            while (((strValue.length() - 1) - decPos) > 2 && strValue.charAt(strValue.length() - 1) == '0') {
                strValue = strValue.substring(0, strValue.length() - 1);
            }
            newScale = strValue.length() - 1 - decPos;
        } else {
            newScale = 0;
        }
        newBigD = number.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormat.setMaximumFractionDigits(newScale);
        currencyFormat.setMinimumFractionDigits(newScale);
        return currencyFormat.format(newBigD.doubleValue());
    }

    /**
     * Round double
     *
     * >= 0.5: round up, < 0.5: round down
     *
     * @param number
     * @return
     */
    public static Double roundUpDouble(Double num, int decPlaces) {
        if (num.isInfinite() || num.isNaN()) return 0.0;
        return new BigDecimal(Double.toString(num)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String formatDouble(Double number, int decimals) {
        if (number == null) {
            return "";
        } else {
            String format = "%,." + decimals + "f";
            return String.format(format, number);
        }
    }

    public static String formatInteger(Integer number) {
        if (number == null) {
            return "";
        } else {
            return String.format("%,d", number);
        }
    }

    public static int significantDecimalPlaces(Double num) {
        if (num == null) return 0;
        return significantDecimalPlaces(num.toString());
    }

    public static int significantDecimalPlaces(BigDecimal num) {
        if (num == null) return 0;
        return significantDecimalPlaces(num.toString());
    }

    public static int significantDecimalPlaces(String strNum) {
        String part = strNum.substring(strNum.indexOf('.') + 1);
        for (int i = part.length(); i > 0; i--) {
            char c = part.charAt(i - 1);
            if (c != '0') {
                return i;
            }
        }
        return 0;
    }

    public static Double parseDouble(String str) throws NumberFormatException {
        Double ret = null;
        if (str != null && str != "") {
            str = str.trim();
            boolean isNeg = false;
            if (str.length() > 1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
                str = str.substring(1);
                str = str.substring(0, str.length() - 1);
                str = str.trim();
                isNeg = true;
            }
            if (str.length() > 0 && str.charAt(0) == '-') {
                if (isNeg) {
                    throw new NumberFormatException("Double negative.");
                }
                str = str.substring(1);
                str = str.trim();
                isNeg = true;
            }

            if (str.length() > 0 && str.charAt(0) == '$') {
                str = str.substring(1);
                str = str.trim();
            }
            if (str.length() > 0 && isNeg && str.charAt(0) == '-') {
                throw new NumberFormatException("Double negative.");
            }
            str = str.replace(",", "");
            if (isNeg) {
                str = "-" + str;
            }
            return new Double(str);
        }
        return ret;
    }

    /**
     * parse Integer
     *
     * @param value
     * @return Integer
     */
    public static Integer parseInteger(String value) throws NumberFormatException {
        Integer ret = null;
        try {
            if (value != null && value != "") {
                ret = Integer.valueOf(value);
            }
        } catch (Exception e) {
            ret = null;
            throw new NumberFormatException(e.getMessage());
        }
        return ret;
    }

    /**
     * parse BigDecimal
     *
     * @param value
     * @return BigDecimal
     */
    public static BigDecimal parseBigDecimal(String str) throws NumberFormatException {
        BigDecimal ret = null;
        if (str != null && str != "") {
            str = str.trim();
            boolean isNeg = false;
            if (str.length() > 1 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
                str = str.substring(1);
                str = str.substring(0, str.length() - 1);
                str = str.trim();
                isNeg = true;
            }
            if (str.length() > 0 && str.charAt(0) == '-') {
                if (isNeg) {
                    throw new NumberFormatException("BigDecimal negative.");
                }
                str = str.substring(1);
                str = str.trim();
                isNeg = true;
            }

            if (str.length() > 0 && str.charAt(0) == '$') {
                str = str.substring(1);
                str = str.trim();
            }
            if (str.length() > 0 && isNeg && str.charAt(0) == '-') {
                throw new NumberFormatException("BigDecimal negative.");
            }
            str = str.replace(",", "");
            if (isNeg) {
                str = "-" + str;
            }
            return new BigDecimal(str);
        }
        return ret;
    }

    // returns the maximum of v1 or v2.
    public static BigDecimal max(BigDecimal v1, BigDecimal v2) {
        return v1.max(v2);
    }

    // Convert a number to the nearest quarter
    public static BigDecimal roundToQuarter(BigDecimal number) {
        return new BigDecimal(Math.round(number.doubleValue() * 4) / 4.0);
    }

    // Check a string if it is a Positive integer.
    public static boolean isNumeric(String s) {
        if (s == null) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBigDecimal(String s) {
        if (s == null) return true;
        try {
            new BigDecimal(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String formatBigDecimal(BigDecimal number, int decimals) {
        return formatBigDecimal(number, decimals, false);
    }

    public static String formatBigDecimal(BigDecimal number, int decimals, boolean isRemoveAllZeroTrailing) {
        if (number == null) return "";

        if (isRemoveAllZeroTrailing) {
            String strNum = number.toString();
            if (strNum.indexOf('.') != -1) {
                int numOfRemoveZero = 0;
                String part = strNum.substring(strNum.indexOf('.') + 1);
                for (int i = part.length() - 1; i >= 0; i--) {
                    char c = part.charAt(i);
                    if (c == '0') {
                        numOfRemoveZero++;
                    } else {
                        break;
                    }
                }
                int scale = part.length() - numOfRemoveZero;
                decimals = (scale > decimals) ? decimals : scale;

                if (decimals == 0 && number.longValue() == 0) {
                    return "0";
                }
            } else {
                decimals = 0;
            }
        }

        String ret = null;
        try {
            ret = formatNumber(number, decimals);
        } catch (IllegalArgumentException e) {
            decimals = number.scale();
            try {
                ret = formatNumber(number, decimals);
            } catch (IllegalArgumentException e2) {
                ret = number.toString();
            }
        }
        return ret;
    }

    private static String formatNumber(BigDecimal number, int decimals) throws IllegalArgumentException {
        number.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        String format = "%,." + decimals + "f";
        return String.format(format, number);
    }

    public static String formatCurrencyForScoreCard(BigDecimal number) {
        try {
            String s = number.toString();
            if (s.indexOf(".0") > -1) {
                number.setScale(0);
                return CURRENCY_DOLLAR_FORMAT_NO_DECIMAL.format(number);
            } else {
                number.setScale(1, BigDecimal.ROUND_HALF_UP);
                return CURRENCY_DOLLAR_FORMAT_ONE_DECIMAL.format(number);
            }
        } catch (Exception e) {
            number.setScale(1, BigDecimal.ROUND_HALF_UP);
            return CURRENCY_DOLLAR_FORMAT_ONE_DECIMAL.format(number);
        }
    }

    public static String removeZeroAfterDecimal(Double number) {
        String s = number.toString();
        if (s.indexOf(".") > -1) {
            DecimalFormat df = new DecimalFormat("###.##");
            return String.valueOf(df.format(number));
        } else {
            DecimalFormat df = new DecimalFormat("###");
            return String.valueOf(df.format(number));
        }
    }

    public static String formatCurrencyForDepositScoreCard(BigDecimal number) {
        number.setScale(0, BigDecimal.ROUND_HALF_UP);
        return CURRENCY_DOLLAR_FORMAT_NO_DECIMAL.format(number);
    }

    public static String formatPhoneNumberForScoreCard(String phone) {
        if (StringUtils.isNotBlank(phone) && phone.length() < 10) {
            String output = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6, phone.length());
            return output;
        }
        return phone;
    }

    public static String buildCEUsFormat(BigDecimal cEUs) {
        String rsCEUText = null;
        if (cEUs != null) {
            String tmpCEUsStr = (new DecimalFormat("#,##0.00")).format(cEUs);
            String[] tmpCEUsArr = tmpCEUsStr.split("\\.");
            if (tmpCEUsArr.length >= 1) {
                if (tmpCEUsArr[0].length() > 1 && tmpCEUsArr[0].startsWith("0")) {
                    tmpCEUsArr[0] = tmpCEUsArr[0].substring(1, tmpCEUsArr[0].length());
                }

                if (tmpCEUsArr.length > 1) {
                    if (tmpCEUsArr[1].length() > 1 && tmpCEUsArr[1].endsWith("0")) {
                        tmpCEUsArr[1] = tmpCEUsArr[1].substring(0, tmpCEUsArr[1].length() - 1);
                    }
                }

                rsCEUText = tmpCEUsArr[0];
                if (tmpCEUsArr.length > 1) {
                    if (StringUtils.isNotEmpty(rsCEUText)) {
                        rsCEUText += ".";
                    }
                    rsCEUText += tmpCEUsArr[1];
                }
            }
        } else {
            rsCEUText = "0.0";
        }
        return rsCEUText;
    }
}
