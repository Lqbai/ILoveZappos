package android.app.zappos.ilovezappos.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.zappos.ilovezappos.Constants.Constant;
import android.app.zappos.ilovezappos.R;
import android.app.zappos.ilovezappos.fragments.HomePageFragment;
import android.app.zappos.ilovezappos.fragments.ShoppingCartFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements HomePageFragment.OnFragmentInteractionListener, ShoppingCartFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener{

    private final static String TAG = MainActivity.class.getSimpleName();
    private CharSequence mTitle;

    private boolean init = false;
    private int whichFragment;
    private BoomMenuButton boomMenuButton;
    private final static int BUTTON_LIKE = 0;
    private final static int BUTTON_INFO = 1;
    private final static int BUTTON_CART = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //inital data
        initData();

        //initial view
        initViews();


        checkIfAppHasBeenInitialized(whichFragment);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        initBoom();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        restoreActionBar();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkIfAppHasBeenInitialized(int whichFragment) {

        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();
        fragment = new HomePageFragment();
        switch (whichFragment){
            case Constant.HOME_SCREEN:
                break;
            case Constant.SHOPPING_CART_SCREEN:
                fragment = new ShoppingCartFragment();
                break;
            default:
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();
        int id = item.getItemId();
        fragment = new HomePageFragment();

        if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, AppSettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }else if(id == R.id.nav_share){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = getString(R.string.main_activity_text_share_body);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_share_name));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }
        restoreActionBar();

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void restoreActionBar () {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }



    private void initBoom() {
        //number of buttons
        int number = 3;

        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{
                R.drawable.heart,
                R.drawable.info,
                R.drawable.shopping_cart,
//                R.drawable.refresh,
//                R.drawable.settings
        };
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] STRINGS = new String[]{
                "Like",
                "Info",
                "Cart"
//                "Refresh",
//                "Settings"
        };
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];

        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = getRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(ButtonType.CIRCLE)
                .boom(BoomType.HORIZONTAL_THROW_2)
                .place(getPlaceType())
                .boomButtonShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .shareStyle(3f, getRandomColor(), getRandomColor())
                .init(boomMenuButton);

        boomMenuButton.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                Log.d(TAG," ====== " + buttonIndex);
                switch (buttonIndex){
                    case BUTTON_LIKE:

                        break;
                    case BUTTON_INFO:
                        break;
                    case BUTTON_CART:
                        Log.d(TAG," ====== 22222222222" + buttonIndex);
                        Fragment fragment;
                        FragmentManager fragmentManager = getFragmentManager();
                        fragment = new ShoppingCartFragment();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    default:break;
                }
            }
        });

        if(boomMenuButton.callOnClick()){
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }


    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mTitle = getTitle();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);
        initBoom();

    }

    private PlaceType getPlaceType() {
        return PlaceType.SHARE_3_3;
    }

    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    public int getRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void initData(){
        whichFragment = getIntent().getIntExtra(Constant.TO_SHOPPING_CART_SCREEN,0);
    }
}

