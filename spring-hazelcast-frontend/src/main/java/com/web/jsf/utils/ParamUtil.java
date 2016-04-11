
package com.web.jsf.utils;

//~--- JDK imports ------------------------------------------------------------
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 *
 * @author Armen Arzumanyan
 */
public class ParamUtil {

    static public Long longValue(String strValue) {
        Long reValue = null;

        if ((strValue == null) || (strValue.trim().equals(""))) {
            strValue = null;
        } else if (strValue == null) {
            return null;
        }

        NumberFormat nf = NumberFormat.getInstance();

        try {
            reValue = (Long) nf.parse(strValue).longValue();
        } catch (Exception ex) {
        }

        return reValue;
    }

}
