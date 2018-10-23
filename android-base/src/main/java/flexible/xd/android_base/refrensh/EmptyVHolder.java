package flexible.xd.android_base.refrensh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import flexible.xd.android_base.R;


/**
 * Created by Flexible on 2017/10/30 0030.
 */

public class EmptyVHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public EmptyVHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv_empty);
    }

    public void setEmptyText(String text) {
        textView.setText(text);
    }
}
