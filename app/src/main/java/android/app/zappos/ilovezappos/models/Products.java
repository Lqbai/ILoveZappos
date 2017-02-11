package android.app.zappos.ilovezappos.models;

import android.databinding.ObservableArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Q on 2/10/17.
 */
public class Products {
//    public ObservableArrayList<Product> observableList = new ObservableArrayList<>();

    @SerializedName("originalTerm")
    public String originalTerm;

    @SerializedName("currentResultCount")
    public int currentResultCount;

    @SerializedName("totalResultCount")
    public int totalResultCount;

    @SerializedName("term")
    public String term;

    @SerializedName("results")
    public ObservableArrayList<Product> productList;
}
