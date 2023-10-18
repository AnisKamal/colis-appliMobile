package com.colis.colis_mobile;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colis.colis_mobile.models.PostModel;
import com.colis.colis_mobile.models.ProfileModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment } factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {

    ImageButton copieButton;
    ImageView profileImage;
    TextView numeroText, fullName ;

    private static final Logger logger = Logger.getLogger(ContactFragment.class.getName());

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        logger.info("initialisation fragment ");

        Bundle bundle = getArguments();
        logger.info(bundle.toString());
        if(bundle != null){

        logger.info("message a propos de bundle ......");

        copieButton = view.findViewById(R.id.copieNPhone);
        profileImage = view.findViewById(R.id.profileContact);
        numeroText = view.findViewById(R.id.NPhone);
        fullName = view.findViewById(R.id.nomProfile);

            PostModel selectedPost = (PostModel) bundle.getSerializable("selectedPost");

            if(selectedPost.getUser().getUrlPhoto() != null ){
                Picasso.get().load(selectedPost.getUser().getUrlPhoto()).transform(new CircleTransformation())
                        .into(profileImage, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("Picasso", "Error loading image", e);
                            }
                        });
            }else{
                profileImage.setImageResource(R.drawable.utilisateur);
            }

            fullName.setText(selectedPost.getUser().getName());

            //numeroText.setText(selectedPost.getProfile().getNTelephone());

        copieButton.setOnClickListener(copie -> {
            String textCopie = numeroText.getText().toString() ;
            if(!textCopie.isEmpty()){
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(textCopie);
                Toast.makeText(getContext(), "Text copié ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "pas de numero de telephone à copier ", Toast.LENGTH_SHORT).show();
            }
            });

        }
        return view ;
    }
}