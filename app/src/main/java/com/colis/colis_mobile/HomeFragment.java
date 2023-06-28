package com.colis.colis_mobile;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.colis.colis_mobile.models.PostModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {



    private LinearLayout listLayout ;
    private ListView myList ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        PostModel post1 = new PostModel(
                "Paris",
                LocalDateTime.now(),
                "Dakar",
                LocalDateTime.now(),
                20d,
                "EUR",
                20,
                20,
                "Faite attention a vos colis"
        );



        PostModel post2 = new PostModel(
                "Marseille",
                LocalDateTime.now(),
                "Abidjan",
                LocalDateTime.now(),
                20d,
                "EUR",
                20,
                20,
                "test description "
        );

        PostModel post3 = new PostModel(
                "Casablanca",
                LocalDateTime.now(),
                "Bamako",
                LocalDateTime.now(),
                40d,
                "USD",
                10,
                5,
                " "
        );

        List<PostModel> postModelList = new ArrayList<>();

        postModelList.add(post1);
        postModelList.add(post2);
        postModelList.add(post3);

        myList = view.findViewById(R.id.listPost);

        ListPostAdapter adapter = new ListPostAdapter(postModelList, getContext());

        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostModel selectedPost = (PostModel) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedPost", selectedPost);
                DetailTrajetFragment detailTrajetFragment= new DetailTrajetFragment();
                detailTrajetFragment.setArguments(bundle);
                replaceFragment(detailTrajetFragment);
            }
        });

        return view;

    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        //fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}