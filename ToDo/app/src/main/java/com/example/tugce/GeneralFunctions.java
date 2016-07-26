package com.example.tugce;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tugce.
 */
public class GeneralFunctions {

    //Add '0' if day or month is one digit
    public static String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    //DB Operations
    public static void CopyDB(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }
}
