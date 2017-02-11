package android.app.zappos.ilovezappos.models;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Q on 2/10/17.
 */
public interface SearchService {

    @GET("Search")
    Call<Products> listProduct(@QueryMap Map<String, String> queries);
}
