package com.leateck.phase.wdmaterialidentification0100;

import org.apache.commons.lang3.*;
import com.rockwell.mes.commons.base.ifc.utility.*;
import java.io.*;

public class ExceptionHelper0610
{
    public static String buildExceptionText(final String parameterText, final String additionalInfo) {
        final StringBuilder sb = new StringBuilder();
        if (parameterText != null) {
            sb.append(parameterText);
        }
        if (StringUtils.isNotEmpty((CharSequence)additionalInfo)) {
            if (sb.length() != 0) {
                sb.append(StringConstants.LINE_BREAK);
            }
            sb.append(additionalInfo);
        }
        return sb.toString();
    }
}
