package com.codewithmuddasir.statussaver.util;

import static android.provider.Settings.System.getString;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.documentfile.provider.DocumentFile;

import com.codewithmuddasir.statussaver.R;
import com.codewithmuddasir.statussaver.model.StatusModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static void getFolderPermissions(Context context, int requestCode, Uri initialUri) {
        Activity activity = (Activity) context;
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, initialUri);
        intent.putExtra("android.content.extra.SHOW_ADVANCED", true);
        activity.startActivityForResult(intent, requestCode);
    }

    public static boolean isStatusExist(Context context, String fileName) {
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(downloadDir, context.getString(R.string.app_name) + File.separator + fileName);
        return file.exists();
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    public static boolean saveStatus(Context context, StatusModel model) {
        if (isStatusExist(context, model.getFileName())) {
            return true;
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            return saveStatusBeforeQ(context, Uri.parse(model.getPathUri()));
        }

        String extension = getFileExtension(model.getFileName());
        String mimeType = model.getType() + "/" + extension;
        InputStream inputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(Uri.parse(model.getPathUri()));

            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, model.getFileName());
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/" + context.getString(R.string.app_name));

            Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
            if (uri != null) {
                OutputStream outputStream = context.getContentResolver().openOutputStream(uri);
                if (inputStream != null) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                outputStream.close();
                if (inputStream != null) {
                    inputStream.close();
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private static boolean saveStatusBeforeQ(Context context, Uri uri) {
        try {
            DocumentFile documentFile = DocumentFile.fromTreeUri(context, uri);
            if (documentFile != null) {
                InputStream inputStream = context.getContentResolver().openInputStream(uri);

                if (inputStream != null) {
                    File destinationFile = new File(
                            Environment.getExternalStorageDirectory() + "/Documents/" +
                                    context.getString(R.string.app_name),
                            documentFile.getName()
                    );

                    // Making dirs & file
                    if (!destinationFile.getParentFile().exists()) {
                        destinationFile.getParentFile().mkdirs();
                    }
                    if (!destinationFile.exists()) {
                        destinationFile.createNewFile();
                    }

                    // Copying content from input stream to destination file
                    FileOutputStream outputStream = new FileOutputStream(destinationFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    inputStream.close();
                    outputStream.close();

                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
