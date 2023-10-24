package com.colis.colis_mobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.colis.colis_mobile.api.RetrofitService;
import com.colis.colis_mobile.api.UserApi;
import com.colis.colis_mobile.models.PostModel;
import com.colis.colis_mobile.models.UserModel;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import android.Manifest;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoProfileSettingFragment} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class PhotoProfileSettingFragment extends Fragment {

    private Button profileButton;

    private ImageView profileImage;

    private int PICK_IMAGE_REQUEST_CODE = 100;

    private static final Logger logger = Logger.getLogger(PhotoProfileSettingFragment.class.getName());

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_profile_setting, container, false);

        profileButton = view.findViewById(R.id.changeProfileId);
        profileImage = view.findViewById(R.id.profileSettingId);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String urlPhoto = sharedPreferences.getString("photo","");

        Picasso.get().load(urlPhoto).transform(new CircleTransformation()).into(profileImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Log.e("Picasso", "Error loading image", e);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST_CODE);
                } else {
                    Intent imageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(imageIntent, PICK_IMAGE_REQUEST_CODE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            // Handle the selected image.

            File imageFile = new File(getRealPathFromURI(selectedImageUri));
            RequestBody imageRequestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image-profile", imageFile.getName(), imageRequestBody);

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
            String id = sharedPreferences.getString("id", "");


            logger.info("execution de onActivityResult ");

            RetrofitService retrofitService  = new RetrofitService();

            UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);;



/*            retrofitService = new RetrofitService();
            userApi = retrofitService.getRetrofit().create(UserApi.class);*/

            userApi.updateImage(id, imagePart).enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Image modifier avec succes ",Toast.LENGTH_SHORT).show();

                    String responseBody = null;
                    try {
                        responseBody = response.body().string();
                        Type postListType = new TypeToken<UserModel>() {}.getType();
                        UserModel userModel = retrofitService.getGson().fromJson(responseBody, postListType);

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("photo", userModel.getUrlPhoto());
                        editor.apply();

                        replaceFragment(new PhotoProfileSettingFragment());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    logger.info("Error: " + t.getMessage());
                    Toast.makeText(getContext(), "probleme sur l image",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
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