package com.llc.conscontweb.helpers;

import com.llc.conscontweb.controller.ContactFormWithAttachments;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import sun.reflect.annotation.ExceptionProxy;

import javax.activation.UnsupportedDataTypeException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    public File processMultipartImageFile(MultipartFile multipartFile) {

        String filename = multipartFile.getOriginalFilename();
        if (filename == null || filename.equals("")) {
            // if file does not have a name, fallback to epoch time for the name
            filename = String.valueOf(System.currentTimeMillis());
        }
        File tempFile;
        final String fileExtension;
        final BufferedImage bufferedImage;

        try {
            final byte[] bytes = multipartFile.getBytes();
            if ((fileExtension = this.readTypeFromSignature(bytes)) == null) {
                throw new UnsupportedDataTypeException("Image type not PNG/JPG");
            }
            // save file to /tmp/
            bufferedImage = ImageIO.read(multipartFile.getInputStream());
            tempFile = new File("/tmp/" + filename);
            ImageIO.write(bufferedImage, fileExtension, tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return tempFile;
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
