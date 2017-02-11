package android.app.zappos.ilovezappos.adapters;

import android.app.zappos.ilovezappos.R;
import android.app.zappos.ilovezappos.databinding.SearchListItemBinding;
import android.app.zappos.ilovezappos.models.Product;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Q on 2/10/17.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(final int id, final Product product);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public SearchListItemBinding binder;

        public ViewHolder(View v) {
            super(v);
            this.binder = DataBindingUtil.bind(v);
        }
    }

    private ObservableArrayList<Product> products;
    private OnItemClickListener onItemClickListener;

    public SearchListAdapter(ObservableArrayList<Product> products) {
        this.products = products;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchListAdapter.ViewHolder holder, final int position) {
        final Product product = products.get(position);
        holder.binder.setProduct(product);
        final int paintFlag = holder.binder.originalPrice.getPaintFlags();
        if(product.getOriginalPrice().equals(product.getPrice())){
            holder.binder.originalPrice.setVisibility(View.INVISIBLE);
        }else{
            holder.binder.originalPrice.setPaintFlags(paintFlag | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.binder.executePendingBindings();
        holder.binder.setClicklistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
