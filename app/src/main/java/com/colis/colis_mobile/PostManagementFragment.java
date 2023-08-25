package com.colis.colis_mobile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.colis.colis_mobile.api.PostApi;
import com.colis.colis_mobile.api.RetrofitService;
import com.colis.colis_mobile.models.PostModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostManagementFragment#} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class PostManagementFragment extends Fragment {
    ListView myList;

    ImageButton addPostButton;

     List<PostModel> postModelList = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(PostManagementFragment.class.getName());


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_management, container, false);


        RetrofitService retrofitService = new RetrofitService();
        PostApi postApi = retrofitService.getRetrofit().create(PostApi.class);



        myList = view.findViewById(R.id.MyPostsId);



        postApi.findByUser("fc6cc457-2548-4740-b704-b3113714b581")
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String responseBody = response.body().string();
                                    Type postListType = new TypeToken<List<PostModel>>(){}.getType();
                                    postModelList = retrofitService.getGson().fromJson(responseBody, postListType);
                                    ListPostManagementAdapter adapter = new ListPostManagementAdapter(postModelList, getContext());
                                    myList.setAdapter(adapter);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), "probleme de reseaux  ", Toast.LENGTH_SHORT ).show();
                            }
                        });


        logger.info("valeur de la liste  : " + postModelList.toString());


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostModel selectedPost = (PostModel) parent.getItemAtPosition(position);
                if(!selectedPost.isActivity()){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedPost", selectedPost);
                    InformationPostFragment informationPostFragment = new InformationPostFragment();
                    informationPostFragment.setArguments(bundle);
                    replaceFragment(informationPostFragment);
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedPost2", selectedPost);
                    AddPostFragment addPostFragment = new AddPostFragment();
                    addPostFragment.setArguments(bundle);
                    replaceFragment(addPostFragment);
                }

            }
        });

        addPostButton = view.findViewById(R.id.addPostId);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddPostFragment());
            }
        });


        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}