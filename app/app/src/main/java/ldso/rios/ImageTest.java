package ldso.rios;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;

public class ImageTest extends AppCompatActivity {
    private static final int SELECT_PHOTO = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_test);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                    File f = new File(getRealPathFromURI(selectedImage));

                    Log.e("path",selectedImage.getPath());

                    if(f.exists())
                        Log.e("existe","ficheiro existe");
                    else
                        Log.e("Nao existe","ficheiro nao existe");
                    Log.e("vai tentar","");
                    try {
                        Log.e("vai tentar","");
                        DB_functions.alternativoGuardarios(f, User.getInstance().getEmail(),User.getInstance().getAuthentication_token(),"","","","",null,"",1f,1f,"Leça");
                    } catch (Exception e) {
                        Log.e("erro","erro na DB");
                        e.printStackTrace();
                    }
                }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }




}
