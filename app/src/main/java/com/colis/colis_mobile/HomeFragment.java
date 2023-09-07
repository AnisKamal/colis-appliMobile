package com.colis.colis_mobile;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.colis.colis_mobile.api.PostApi;
import com.colis.colis_mobile.api.RetrofitService;
import com.colis.colis_mobile.models.PostModel;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    private LinearLayout listLayout;

    AutoCompleteTextView autoCompleteTextViewDepart, autoCompleteTextViewDestination;
    private ListView myList;

    private Button searchButton;

    private static final Logger logger = Logger.getLogger(HomeFragment.class.getName());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoCompleteTextViewDepart = view.findViewById(R.id.depart);

        autoCompleteTextViewDestination = view.findViewById(R.id.arrive);

        searchButton = view.findViewById(R.id.search_button);


        List<String> recommandations = readTextFile();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                recommandations
        );

        autoCompleteTextViewDepart.setAdapter(adapter);

        autoCompleteTextViewDestination.setAdapter(adapter);

        RetrofitService retrofitService = new RetrofitService();
        PostApi postApi = retrofitService.getRetrofit().create(PostApi.class);

        myList = view.findViewById(R.id.listPost);

        postApi.findLastPosts()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String responseBody = response.body().string();
                            Type postListType = new TypeToken<List<PostModel>>() {
                            }.getType();
                            List<PostModel> postModelList = retrofitService.getGson().fromJson(responseBody, postListType);
                            ListPostSearchAdapter adapter = new ListPostSearchAdapter(postModelList, getContext());
                            myList.setAdapter(adapter);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "probleme de reseau", Toast.LENGTH_SHORT).show();
                    }
                });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostModel selectedPost = (PostModel) parent.getItemAtPosition(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedPost", selectedPost);
                DetailTrajetFragment detailTrajetFragment = new DetailTrajetFragment();
//                ContactFragment contactFragment = new ContactFragment();
                detailTrajetFragment.setArguments(bundle);
//                contactFragment.setArguments(bundle);
                replaceFragment(detailTrajetFragment);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rDepart = autoCompleteTextViewDepart.getText().toString();
                String rDestination = autoCompleteTextViewDestination.getText().toString();

                if(rDepart.trim().isEmpty() || rDestination.trim().isEmpty()){
                    Toast.makeText(getContext(), "Veuillez remplir les deux champs ", Toast.LENGTH_SHORT).show();
                }
                else{
                    postApi.findPostSearch(rDepart, rDestination).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String responseBody = response.body().string();
                                Type postListType = new TypeToken<List<PostModel>>() {}.getType();
                                List<PostModel> postModelList = retrofitService.getGson().fromJson(responseBody, postListType);
                                ListPostSearchAdapter adapter = new ListPostSearchAdapter(postModelList, getContext());
                                myList.setAdapter(adapter);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "Probleme de reseau ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        return view;

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        //fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private List<String> readTextFile() {
        List<String> dataList = new ArrayList<>();

        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream is = getResources().openRawResource(R.raw.pays);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }

            reader.close();
            isr.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

}