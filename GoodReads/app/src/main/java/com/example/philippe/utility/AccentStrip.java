package com.example.philippe.utility;
import java.text.Normalizer;
/**
 * Created by Philippe on 2016-02-06.
 */
public class AccentStrip {
        public static String flattenToAscii(String string) {
            StringBuilder sb = new StringBuilder(string.length());
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            for (char c : string.toCharArray()) {
                if (c <= '\u007F') sb.append(c);
            }
            return sb.toString();
        }
}
