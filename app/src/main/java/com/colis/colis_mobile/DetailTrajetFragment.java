package com.colis.colis_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.colis.colis_mobile.models.PostModel;

import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTrajetFragment} factory method to
 * create an instance of this fragment.
 */
public class DetailTrajetFragment extends Fragment {

    private static final Logger logger = Logger.getLogger(DetailTrajetFragment.class.getName());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PostModel selectedPost = (PostModel) bundle.getSerializable("selectedPost");
            logger.info("selecter post : " + selectedPost.getLieuDestination());
        }
        return inflater.inflate(R.layout.fragment_detail_trajet, container, false);

    }
}