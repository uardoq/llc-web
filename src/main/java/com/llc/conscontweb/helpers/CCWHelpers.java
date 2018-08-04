package com.llc.conscontweb.helpers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class CCWHelpers {


    private final byte[] JPG; // magic number: FF D8 FF
    private final byte[] PNG; // magic number: 89 50 4E 47 0D 0A 1A 0A
    private final Map<String, byte[]> fileTypes;

    private static CCWHelpers ourInstance = new CCWHelpers();

    public static CCWHelpers getInstance() {
        return ourInstance;
    }

    private CCWHelpers() {
        JPG = parseBytes("FFD8FF");
        PNG = parseBytes("89504E470D0A1A0A");
        fileTypes = new HashMap<>();
        fileTypes.put("JPG", JPG);
        fileTypes.put("PNG", PNG);
    }

    private byte[] parseBytes(String hex) {
        int bytes = hex.length() / 2;
        byte[] temp = new byte[bytes];
        for (int i = 0, j = 0, k = 2; i < bytes; i++, j += 2, k += 2) {
            // takes 2's complement
            temp[i] = (byte) Integer.parseInt(hex.substring(j, k), 16);
        }
        return temp;
    }


    private boolean compareWith(byte[] b1, byte[] b2) {
        for (int i = 0, b1len = b1.length; i < b1len; i++) {
            if (b1[i] != b2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method the first header bytes of the file and compares it to our
     * supported fileTypes map instead of relying on the filename.
     */
    public String readTypeFromSignature(byte[] signature) {
        // try file types under fileTypes until we match a signature.
        for (Map.Entry<String, byte[]> entry : fileTypes.entrySet()) {
            if (compareWith(entry.getValue(), signature)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns a map of the fields as keys and error description as values.
     */
    public Map<String, String> getErrorsInASaneFormat(final BindingResult result) {
        return new HashMap<String, String>() {{
            for (FieldError error : result.getFieldErrors()) {
                put(error.getField(), error.getDefaultMessage());
            }
        }};
    }

}
