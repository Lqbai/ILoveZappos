package android.app.zappos.ilovezappos.fragments;

import android.app.zappos.ilovezappos.ApplicationContext;
import android.app.zappos.ilovezappos.databinding.FragmentShoppingCartBinding;
import android.app.zappos.ilovezappos.models.Product;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.zappos.ilovezappos.R;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link ShoppingCartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageProduct;
    private TextView tvBrandName;
    private TextView tvProductName;
    private TextView tvProductPrice;

    private FragmentShoppingCartBinding binder;

    private OnFragmentInteractionListener mListener;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
//        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false);

        imageProduct = (ImageView)view.findViewById(R.id.thumbnailUrl);
        tvBrandName = (TextView)view.findViewById(R.id.brandName);
        tvProductName = (TextView)view.findViewById(R.id.productName);
        tvProductPrice = (TextView)view.findViewById(R.id.productPrice);
        if(ApplicationContext.getProductInfo().getSavedProductFromShoppingCart()!=null){
            String productInfo = ApplicationContext.getProductInfo().getSavedProductFromShoppingCart();
            final Product product = new Gson().fromJson(productInfo, Product.class);
            Picasso.with(view.getContext()).load(product.thumbnailImageUrl).error(R.drawable.ic_product_placeholder).into(imageProduct);
            tvBrandName.setText(product.brandName);
            tvProductName.setText(product.productName);
            tvProductPrice.setText(product.price);
        }



        return view;
//        return binder.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
