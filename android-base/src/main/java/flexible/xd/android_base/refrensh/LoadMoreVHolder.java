package flexible.xd.android_base.refrensh;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import flexible.xd.android_base.R;


public class LoadMoreVHolder extends RecyclerView.ViewHolder {

	private ProgressBar progressBar;

	private TextView textView;

	public LoadMoreVHolder(View itemView) {
		super(itemView);
		progressBar = (ProgressBar) itemView.findViewById( R.id.progressbar1);
		textView = (TextView) itemView.findViewById(R.id.textView1);
	}
	
	public void setHasMore(boolean has){
		if(has){
			progressBar.setVisibility(View.VISIBLE);
			textView.setText("加载更多中•••");
		}else {
			progressBar.setVisibility(View.GONE);
			textView.setText("没有更多数据了。");
		}
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}
}
