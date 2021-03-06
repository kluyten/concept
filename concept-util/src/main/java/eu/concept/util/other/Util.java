package eu.concept.util.other;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Christos Paraskeva
 */
public class Util {

    /**
     * Encodes a String based on a specific algorithm
     *
     * @param content The String to encrypt
     * @param algorithm The algorithm to be used
     * @return The encoded String
     */
    public static String createAlgorithm(String content, String algorithm) {
        String encryptedContent = "";
        try {
            //Producing the SHA hash for the input
            MessageDigest m;
            m = MessageDigest.getInstance(algorithm);
            m.update(content.getBytes(), 0, content.length());
            encryptedContent = new BigInteger(1, m.digest()).toString(16);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return encryptedContent;
    }

    public static String getRandomHexString(int numchars) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

    /**
     *
     * This method converts an Inputstream to Byte Array
     *
     * @param _is
     *
     * @return The byte array of the given inputstream
     *
     */
    public static byte[] convertInputStreamToByteArray(InputStream _is) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[0];

            while ((nRead = _is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            return buffer.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String getComponentName(String className) {

        switch (className) {
            case "BriefAnalysis":
                return "BA";
            case "FileManagement":
                return "FM";

            case "Sketch":
                return "SK";

            case "MindMap":
                return "MM";

            case "Moodboard":
                return "MB";

            case "Storyboard":
                return "SB";
            //Unknown name of component    
            default:
                return "N/A";
        }

    }

    public static void main(String[] args) {
        String sha1_manager1 = Util.createAlgorithm("manager1", "SHA");
        String sha1_designer1 = Util.createAlgorithm("designer1", "SHA");
        String sha1_client1 = Util.createAlgorithm("client1", "SHA");

        System.out.println("SHA1 (manager1): " + sha1_manager1);
        System.out.println("SHA1 (designer1): " + sha1_designer1);
        System.out.println("SHA1 (client1): " + sha1_client1);

        String salt = "56ca7800420a4d73619ea72af8c7fd54";
        //String salt = Util.getRandomHexString(32);

        String sha1_salt = Util.createAlgorithm(salt + sha1_manager1, "SHA");
        System.out.println("Salt= " + salt);
        System.out.println("salt+sha1=" + salt + sha1_manager1);
        System.out.println("SHA1(salt+SHA1(palin_text)) =  " + sha1_salt);
    }

}
