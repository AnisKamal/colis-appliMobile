package com.colis.colis_mobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.colis.colis_mobile.models.PostModel;
import com.colis.colis_mobile.models.ProfileModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    ListView myList;

    List<ProfileModel> profileModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        myList = view.findViewById(R.id.listContact);

        ProfileModel p1 = new ProfileModel("Anis Kamal", "");
        ProfileModel p2 = new ProfileModel("Said Hossain", "");
        ProfileModel p3 = new ProfileModel("hassan Tarek", "");
        profileModelList.add(p1);
        profileModelList.add(p2);
        profileModelList.add(p3);

        ListContactAdapter adapter = new ListContactAdapter(profileModelList, getContext());

        myList.setAdapter(adapter);

        return view;
    }
}