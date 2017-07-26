package com.swarmmanager.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

public class EncoderDecoder {

    private static final Logger LOGGER = Logger.getLogger(EncoderDecoder.class.getName());

    public static String jsonUrlEncode(String json) {
        try {
            return URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding json", e);
        }
        return null;
    }

    public static String base64URLEncode(String data) {
        if (data != null) {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(data.getBytes());
        }
        return "";
    }

    public static String base64URLDecode(String data) {
        if (data != null) {
            return new String(Base64.getUrlDecoder().decode(data.getBytes()));
        }
        return "";
    }

    public static String base64Decode(String data) {
        if (data != null) {
            return new String(Base64.getDecoder().decode(data.getBytes()));
        }
        return "";
    }

}
