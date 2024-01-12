package com.codewithmuddasir.statussaver;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codewithmuddasir.statussaver.util.AppName;
import com.codewithmuddasir.statussaver.util.Constants;
import com.codewithmuddasir.statussaver.util.FileUtils;
import com.codewithmuddasir.statussaver.util.LocalStorage;

import java.util.Objects;

public class PermissionFragment extends Fragment {

    AppName app;

    public PermissionFragment(AppName app) {
        this.app = app;
    }

    private int WHATSAPP_REQUEST_CODE = 5555;
    private int WHATSAPP_BUSINESS_REQUEST_CODE = 7777;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_permission, container, false);

        TextView permission = view.findViewById(R.id.permission);

        permission.setOnClickListener(v -> {
            if (app.equals(AppName.WHATSAPP)) {
//                FileUtils.getFolderPermissions(view.getContext(), WHATSAPP_REQUEST_CODE, Constants.getWhatsappUri());


//                Activity activity = (Activity) view.getContext();
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Constants.getWhatsappUri());
                intent.putExtra("android.content.extra.SHOW_ADVANCED", true);

                startActivityForResult(intent, WHATSAPP_REQUEST_CODE);

            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Constants.getWhatsappBusinessUri());
                intent.putExtra("android.content.extra.SHOW_ADVANCED", true);

                startActivityForResult(intent, WHATSAPP_BUSINESS_REQUEST_CODE);

//                FileUtils.getFolderPermissions(view.getContext(), WHATSAPP_BUSINESS_REQUEST_CODE, Constants.getWhatsappBusinessUri());
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            Uri treeUri = Objects.requireNonNull(data).getData();
            requireActivity().getContentResolver().takePersistableUriPermission(
                    Objects.requireNonNull(treeUri),
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );
            if (requestCode == WHATSAPP_REQUEST_CODE) {
                LocalStorage.putStringValue(
                        view.getContext(),
                        LocalStorage.PREF_KEY_WP_TREE_URI,
                        Objects.requireNonNull(treeUri).toString()
                );
                LocalStorage.putBooleanValue(
                        view.getContext(),
                        LocalStorage.PREF_KEY_WP_PERMISSION_GRANTED,
                        true
                );

//                getWhatsAppStatuses();
            } else if (requestCode == WHATSAPP_BUSINESS_REQUEST_CODE) {
                LocalStorage.putStringValue(
                        view.getContext(),
                        LocalStorage.PREF_KEY_WP_BUSINESS_TREE_URI,
                        Objects.requireNonNull(treeUri).toString()
                );
                LocalStorage.putBooleanValue(
                        view.getContext(),
                        LocalStorage.PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED,
                        true
                );
//                getWhatsAppBusinessStatuses();
            }
        }
    }

}