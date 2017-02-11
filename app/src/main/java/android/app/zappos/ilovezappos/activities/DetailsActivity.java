package android.app.zappos.ilovezappos.activities;

import android.app.zappos.ilovezappos.ApplicationContext;
import android.app.zappos.ilovezappos.Constants.Constant;
import android.app.zappos.ilovezappos.R;
import android.app.zappos.ilovezappos.databinding.DetailsLayoutBinding;
import android.app.zappos.ilovezappos.models.Product;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

public class DetailsActivity extends AppCompatActivity {

    public static String PRODUCT_INFO = "product_info";
    private DetailsLayoutBinding binder;
    private ImageView imageViewLike;
    private ImageView imageViewAddCart;
    private boolean isClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String productInfo = getIntent().getStringExtra(PRODUCT_INFO);
        if(productInfo != null) {
            final Product product = new Gson().fromJson(productInfo, Product.class);
            this.binder = DataBindingUtil.setContentView(this, R.layout.details_layout);
            this.binder.setProduct(product);
            this.imageViewLike = (ImageView)findViewById(R.id.imageButtonShare);
            this.imageViewAddCart = (ImageView)findViewById(R.id.imageButtonAddToCart);
            imageViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isClicked){
                        isClicked = true;
                        imageViewLike.setColorFilter(Color.YELLOW);
                        Snackbar.make(v, " ", Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.details_activity_text_share), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = productInfo;
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_share_name));
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                            }
                        }).show();
                    }else{
                        isClicked = false;
                        imageViewLike.setColorFilter(Color.WHITE);
                    }
                }
            });

            imageViewAddCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //only can save one item to shopping cart: demo purpose; in the real world,
                    // here should be provided api to store the saved items to server, in case user delete/clear data/cache
                    // to lose their saved items
                    // used SharedPreferences instead of using 'sqlite', just because it is easy to do
                    ApplicationContext.getProductInfo().addToShoppingCart(productInfo);
                    Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                    intent.putExtra(Constant.TO_SHOPPING_CART_SCREEN, Constant.SHOPPING_CART_SCREEN);
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailsActivity.this, v, "productInfo");
                    startActivity(intent, options.toBundle());

                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
