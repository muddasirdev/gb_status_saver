package com.codewithmuddasir.statussaver;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.codewithmuddasir.statussaver.adapter.StatusAdapter;
import com.codewithmuddasir.statussaver.model.StatusModel;
import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.FileType;
import com.codewithmuddasir.statussaver.util.LocalStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WhatsappStatusFragment extends Fragment {

    FileType fileType;
    AppName appName;

    public WhatsappStatusFragment(FileType fileType, AppName appName) {
        this.fileType = fileType;
        this.appName = appName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_whatsapp_status, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);


        if (appName.equals(AppName.OWN_APP)) {
            if (fileType.equals(FileType.IMAGE_FILE)) {

                File documentsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), view.getContext().getString(R.string.app_name));

                if (documentsDirectory != null) {

                    List<StatusModel> list = new ArrayList<StatusModel>();

                    for (File file : documentsDirectory.listFiles()) {
                        if (!file.getName().equals(".nomedia") && file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg"))) {

                            StatusModel model = new StatusModel(
                                    file.toURI().toString(),
                                    file.getName(),
                                    FileType.IMAGE_FILE,
                                    false
                            );

                            list.add(model);

                        }
                    }

                    StatusAdapter statusAdapter = new StatusAdapter(list, getActivity(), appName);
                    recyclerView.setAdapter(statusAdapter);

                }
            } else {
                String uri = "";

                File documentsDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), view.getContext().getString(R.string.app_name));

                if (documentsDirectory != null) {

                    List<StatusModel> list = new ArrayList<StatusModel>();

                    for (File file : documentsDirectory.listFiles()) {
                        if (!file.getName().equals(".nomedia") && file.isFile() && (file.getName().endsWith(".mp4"))) {

                            StatusModel model = new StatusModel(
                                    file.toURI().toString(),
                                    file.getName(),
                                    FileType.VIDEO_FILE,
                                    false
                            );

                            list.add(model);

                        }
                    }

                    StatusAdapter statusAdapter = new StatusAdapter(list, getActivity(), appName);
                    recyclerView.setAdapter(statusAdapter);
                }
            }
        } else {
            if (fileType.equals(FileType.IMAGE_FILE)) {

                String uri = "";

                Uri treeUri;

                if (appName.equals(AppName.WHATSAPP)) {
                    uri = LocalStorage.PREF_KEY_WP_TREE_URI;


                } else if (appName.equals(AppName.WHATSAPP_BUSINESS)) {
                    uri = LocalStorage.PREF_KEY_WP_BUSINESS_TREE_URI;

                }

                treeUri = Uri.parse(LocalStorage.getStringValue(getContext(), uri));

                view.getContext().getContentResolver().takePersistableUriPermission(
                        treeUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                DocumentFile fileDocument = DocumentFile.fromTreeUri(view.getContext(), treeUri);

                if (fileDocument != null) {

                    List<StatusModel> list = new ArrayList<StatusModel>();

                    for (DocumentFile file : fileDocument.listFiles()) {
                        if (!file.getName().equals(".nomedia") && file.isFile() && (file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg"))) {

                            StatusModel model = new StatusModel(
                                    file.getUri().toString(),
                                    file.getName(),
                                    FileType.IMAGE_FILE,
                                    false
                            );

                            list.add(model);

                        }
                    }

                    StatusAdapter statusAdapter = new StatusAdapter(list, getActivity(), appName);
                    recyclerView.setAdapter(statusAdapter);

                }
            } else {
                String uri = "";

                if (appName.equals(AppName.WHATSAPP)) {
                    uri = LocalStorage.PREF_KEY_WP_TREE_URI;
                } else if (appName.equals(AppName.WHATSAPP_BUSINESS)) {
                    uri = LocalStorage.PREF_KEY_WP_BUSINESS_TREE_URI;
                } else {
                    uri = Uri.parse(Environment.DIRECTORY_DOCUMENTS + "/" + view.getContext().getString(R.string.app_name)).toString();
                }

                Uri treeUri = Uri.parse(LocalStorage.getStringValue(getContext(), uri));

                view.getContext().getContentResolver().takePersistableUriPermission(
                        treeUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                );

                DocumentFile fileDocument = DocumentFile.fromTreeUri(view.getContext(), treeUri);

                if (fileDocument != null) {

                    List<StatusModel> list = new ArrayList<StatusModel>();

                    for (DocumentFile file : fileDocument.listFiles()) {
                        if (!file.getName().equals(".nomedia") && file.isFile() && (file.getName().endsWith(".mp4"))) {

                            StatusModel model = new StatusModel(
                                    file.getUri().toString(),
                                    file.getName(),
                                    FileType.VIDEO_FILE,
                                    false
                            );

                            list.add(model);

                        }
                    }

                    StatusAdapter statusAdapter = new StatusAdapter(list, getActivity(), appName);
                    recyclerView.setAdapter(statusAdapter);

                }
            }
        }

        return view;
    }
}