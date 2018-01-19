package flexible.xd.android_base.refrensh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Flexible on 2017/10/25 0025.
 */

public class BaseVHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public static <T extends RecyclerView.ViewHolder> T newViewHolder(Class<T> clazz, ViewGroup viewGroup, int resId) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                resId, viewGroup, false);
        try {
            return clazz.getDeclaredConstructor(View.class).newInstance(view);
        } catch (Exception e) {

        }
        return null;
    }

    public final View itemView;
    private boolean clickable = true;
    private AdapterItemListener adapterItemListener;

    public BaseVHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    @Override
    public void onClick(View v) {
        if (v == itemView) {
            if (null != getAdapterItemListener()) {
                getAdapterItemListener().onItemClick(getAdapterPosition());
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    public AdapterItemListener getAdapterItemListener() {
        return adapterItemListener;
    }

    public void setAdapterItemListener(AdapterItemListener adapterItemListener) {
        this.adapterItemListener = adapterItemListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public boolean isClickable() {
        return clickable;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }
}
