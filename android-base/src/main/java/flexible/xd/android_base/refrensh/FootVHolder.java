package flexible.xd.android_base.refrensh;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import flexible.xd.android_base.R;


/**
 * Created by Flexible on 2017/10/30 0030.
 */

public class FootVHolder extends RecyclerView.ViewHolder {
    TextView textView;

    public FootVHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView1);
    }

    public void setHeadText(String text) {
        if (text == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }
}
