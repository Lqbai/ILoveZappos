package android.app.zappos.ilovezappos.adapters;

import android.app.Activity;
import android.app.zappos.ilovezappos.R;
import android.app.zappos.ilovezappos.activities.DetailsActivity;
import android.app.zappos.ilovezappos.models.Product;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by Q on 2/10/17.
 */
public class SearchListBinder {
    public static String PRODUCT_INFO = "product_info";

    @BindingAdapter("bind:imageRes")
    public static void bindImage(ImageView view, String thumbnailUrl) {
        view.setImageDrawable(null);
        Picasso.with(view.getContext()).load(thumbnailUrl).error(R.drawable.ic_product_placeholder).into(view);
    }

    @BindingAdapter("bind:items")
    public static void bindList(final RecyclerView view, ObservableArrayList<Product> list) {
        if (list == null) return;
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        view.setLayoutManager(layoutManager);
        final SearchListAdapter listAdapter = new SearchListAdapter(list);
        final Context context = view.getContext();
        listAdapter.setOnItemClickListener(new SearchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int id, Product product) {
                String serializedData = new Gson().toJson(product);
                SearchListAdapter.ViewHolder viewHolder = (SearchListAdapter.ViewHolder)view.findViewHolderForAdapterPosition(id);
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(PRODUCT_INFO, serializedData);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, viewHolder.binder.thumbnailUrl, "productInfo");
                context.startActivity(intent, options.toBundle());
            }
        });
        view.setAdapter(listAdapter);
    }
}
