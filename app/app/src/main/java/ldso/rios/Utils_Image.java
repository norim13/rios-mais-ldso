package ldso.rios;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.net.URL;

/**
 * Created by filipe on 22/12/2015.
 */
public class Utils_Image {

    /**
     * Faz load de uma imagem de um URL (e faz resize)
     * @param url
     * @return Bitmap resized
     * @throws IOException
     */
    public static Bitmap loadImageURL(String url) throws IOException {
        //In case the image is too large
        URL url_value = new URL(url);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap srcBmp = BitmapFactory.decodeStream(url_value.openConnection().getInputStream(), null, options);
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return dstBmp;
    }

    /**
     * Recorta uma imagem num quadrado
     * @param srcBmp Bitmap Original
     * @return Bitmap cropped
     * @throws IOException
     */
    public static Bitmap squareimage(Bitmap srcBmp) throws IOException {
        Bitmap dstBmp;
        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );
        }else{
            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }
        return dstBmp;
    }

    /**
     * Retorna o path no dispositivo de um determinado URI
     * @param contentURI
     * @param context
     * @return
     */
    public static String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
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
