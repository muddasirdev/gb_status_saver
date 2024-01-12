package com.codewithmuddasir.statussaver.util;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class Constants {
    public static final String TYPE_WHATSAPP_MAIN = "com.whatsapp";
    public static final String TYPE_WHATSAPP_BUSINESS = "com.whatsapp.w4b";

    // URIs
    public static final Uri WHATSAPP_PATH_URI_ANDROID =
            Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%2FMedia%2F.Statuses");
    public static final Uri WHATSAPP_PATH_URI_ANDROID_11 =
            Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses");

    public static final Uri WHATSAPP_BUSINESS_PATH_URI_ANDROID =
            Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%20Business%2FMedia%2F.Statuses");
    public static final Uri WHATSAPP_BUSINESS_PATH_URI_ANDROID_11 =
            Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp%20Business%2FMedia%2F.Statuses");

    public static Uri getWhatsappUri() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ? WHATSAPP_PATH_URI_ANDROID_11 : WHATSAPP_PATH_URI_ANDROID;
    }

    public static Uri getWhatsappBusinessUri() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) ? WHATSAPP_BUSINESS_PATH_URI_ANDROID_11 : WHATSAPP_BUSINESS_PATH_URI_ANDROID;
    }

    public static Uri getDocumentFolderUri() {
        return Uri.parse("content://com.android.externalstorage.documents/document/primary%3AWhatsApp%2FMedia%2F.Statuses");
    }

}
