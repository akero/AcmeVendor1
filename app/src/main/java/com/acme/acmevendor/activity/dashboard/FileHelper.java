package com.acme.acmevendor.activity.dashboard;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHelper {

    private static final String FILE_NAME = "tokenFile.txt";

    /**
     * Writes the provided logintoken to a private file within the app's storage.
     *
     * @param context    the application context
     * @param logintoken the token string to write
     * @return true if writing was successful, false otherwise
     */
    public static boolean writeLoginToken(Context context, String logintoken) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(logintoken.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reads the logintoken from the private file within the app's storage.
     *
     * @param context the application context
     * @return the token string if reading was successful, null otherwise
     */
    public static String readLoginToken(Context context) {
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            fis = context.openFileInput(FILE_NAME);
            int content;
            while ((content = fis.read()) != -1) {
                stringBuilder.append((char) content);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
