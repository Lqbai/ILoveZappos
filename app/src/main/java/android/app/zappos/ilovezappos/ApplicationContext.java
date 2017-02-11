package android.app.zappos.ilovezappos;

import android.app.Application;
import android.app.zappos.ilovezappos.models.Product;
import android.content.Context;
import android.util.Log;

/**
 * Created by Q on 2/10/17.
 */
public class ApplicationContext extends Application {

    private static final String TAG = ApplicationContext.class.getSimpleName();

    private static Context context;
    private static Product mProductInfo = new Product();

    public void onCreate(){
        super.onCreate();

        Log.d(TAG,"onCreate is called!");
        context = this.getApplicationContext();
        if(mProductInfo == null){
            mProductInfo = new Product();
        }
    }

    public static Context getContext(){return context;}

    public static Product getProductInfo(){return mProductInfo;}
}
