package android.app.zappos.ilovezappos.models;

import android.app.zappos.ilovezappos.ApplicationContext;
import android.app.zappos.ilovezappos.Constants.Constant;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Q on 2/10/17.
 */
public class Product {

    @SerializedName("brandName")
    public String brandName;

    @SerializedName("thumbnailImageUrl")
    public String thumbnailImageUrl;

    @SerializedName("productId")
    public String productId;

    @SerializedName("originalPrice")
    public String originalPrice;

    @SerializedName("styleId")
    public String styleId;

    @SerializedName("colorId")
    public String colorId;

    @SerializedName("price")
    public String price;

    @SerializedName("percentOff")
    public String percentOff;

    @SerializedName("productUrl")
    public String productUrl;

    @SerializedName("productName")
    public String productName;

    public String getOriginalPrice() {
        return originalPrice;
    }

    public String getPrice(){
        return price;
    }

    public int getOriginalColor() {
        return Color.GRAY;
    }

    public int getPriceColor() {
        return originalPrice.equals(this.price) ? Color.GREEN : Color.RED;
    }

    public void addToShoppingCart(String productInfo){
        SharedPreferences pref = ApplicationContext.getContext().getSharedPreferences(Constant.PREFERENCE_INDICATOR, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constant.PRODUCT_INFO, productInfo);
        editor.commit();
    }

    public String getSavedProductFromShoppingCart(){
        SharedPreferences pref = ApplicationContext.getContext().getSharedPreferences(Constant.PREFERENCE_INDICATOR, 0);
        return pref.getString(Constant.PRODUCT_INFO, "");
    }
}
