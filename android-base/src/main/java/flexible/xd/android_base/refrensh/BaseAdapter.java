package flexible.xd.android_base.refrensh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import flexible.xd.android_base.R;
import flexible.xd.android_base.base.XdApp;
import flexible.xd.android_base.utils.LogUtils;

/**
 * Created by Flexible on 2017/10/24 0024.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterItemListener, HolderType, LoadMoreListener {
    private AdapterItemListener onItemClickListener;
    private LoadMoreListener loadMoreListener;
    public List<T> data;
    public Context ctx;
    private boolean isShowEmpty = true;
    public static int CT_EMPTY = 1004;
    public List<Integer> holderType = new LinkedList<>();


    public BaseAdapter(List<T> data, Context ctx, AdapterItemListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.data = data;
        this.ctx = ctx;
        init();
    }

    public BaseAdapter(List<T> data, Context ctx) {
        this.data = data;
        this.ctx = ctx;
        init();
    }

    public BaseAdapter(List<T> data) {
        this.data = data;
        this.ctx = XdApp.getAppContext();
        init();
    }

    @Override
    public int getItemViewType(int position) {
        return holderType.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType == CT_EMPTY) {
            return onCreateEmptyHolder(parent);
        }
        ViewHolder viewHolder = null;
        if (isFootCell(viewType)) {
            if (isLoading) {
                return onLoadMoreHolder(parent);
            }
            viewHolder = onCreateFootVHolder(parent, viewType);
        } else {
            viewHolder = onCreateVHolder(parent, viewType);
            if (viewHolder instanceof BaseVHolder) {
                BaseVHolder simpleViewHolder = (BaseVHolder) viewHolder;
                if (simpleViewHolder.isClickable()) {
                    simpleViewHolder.setAdapterItemListener(this);
                }
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holderType.get(position) == CT_EMPTY) {
            if (holder instanceof EmptyVHolder) {
                String string = emptyStr();
                ((EmptyVHolder) holder).setEmptyText(string);
            }
            return;
        }
        if (isFootCell(holderType.get(position))) {
            if (isLoading) {
                LoadMoreVHolder loadMoreVHolder = (LoadMoreVHolder) holder;
                loadMoreVHolder.setHasMore(isLoading);
                return;
            }
            onBindFootVHolder(holder, position);
        } else {
            onBindVHolder(holder, position);
        }

    }

    protected String emptyStr() {
        return "没有数据";
    }

    @Override
    public int getItemCount() {
        return holderType.size();
    }

    protected abstract RecyclerView.ViewHolder onCreateVHolder(ViewGroup parent, int type);

    protected abstract RecyclerView.ViewHolder onCreateFootVHolder(ViewGroup parent, int type);

    protected abstract void onBindVHolder(ViewHolder holder, int position);

    protected abstract void onBindFootVHolder(ViewHolder holder, int position);

    /**
     * 点击 长按事件
     *
     * @param onItemClickListener
     */
    public void setOnClickListener(AdapterItemListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public boolean onItemLongClick(int position) {
        if (null != onItemClickListener)
            return onItemClickListener.onItemLongClick(position);
        return false;
    }

    @Override
    public void onItemClick(int position) {
        if (null != onItemClickListener)
            onItemClickListener.onItemClick(position);
    }

    public static boolean isCell(int type) {
        return type <= CELL;
    }

    public static boolean isFootCell(int type) {
        return type > HEAD && type <= FOOT;
    }

    public boolean isEmpty() {
        return isEmpty(data);
    }

    public static boolean isEmpty(Object obj) {
        return obj == null ? true : (obj.getClass().isArray() ? Array.getLength(obj) == 0 : (obj instanceof Collection ? ((Collection) obj).isEmpty() : (obj instanceof Map ? ((Map) obj).isEmpty() : false)));
    }

    private ViewHolder onCreateEmptyHolder(ViewGroup parent) {
        return BaseVHolder.newViewHolder(EmptyVHolder.class, parent,
                R.layout.flexible__empty_holder);
    }

    private ViewHolder onLoadMoreHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.flexible__load_more, parent, false);
        return new LoadMoreVHolder(view);
    }

    public boolean isShowEmpty() {
        return isShowEmpty;
    }

    public void setShowEmpty(boolean showEmpty) {
        isShowEmpty = showEmpty;
    }


    public boolean hasFootView() {
        if (isLoading)
            return true;
        return false;
    }

    public void init() {
        if (data.size() == 0) {
            holderType.clear();
            holderType.add(CT_EMPTY);
            return;
        }
        if (data.size() == 0) {
            if (holderType.get(0) == CT_EMPTY)
                return;
        }
        holderType.clear();
        LogUtils.LOGE("z", data + "");
        for (int i = 0; i < data.size(); i++) {
            holderType.add(CELL);
        }
        if (hasFootView()) {
            holderType.add(FOOT);
        }
    }

    private boolean isAutoLoad = false;
    private boolean isLoadEnable = false;
    private boolean isLoading = false;

    public void setLoading(boolean loading) {
        init();
        isLoading = loading;
    }

    public void setLoadEnable(boolean loadEnable) {
        isLoadEnable = loadEnable;
    }

    public void autoLoadMore(boolean isLoad) {
        this.isAutoLoad = isLoad;
    }

    private void startLoadMore(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager) {
        if (!isLoadEnable || loadMoreListener == null) {
            return;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//停止滑动状态
                    if (!isAutoLoad && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isAutoLoad && findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {
                    scrollLoadMore();
                }
//                else if (isAutoLoad) {
//                    isAutoLoad = false;
//                }
            }
        });
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void scrollLoadMore() {
        if (!isLoading) {
            isLoading = true;
            notifyData();
            loadMore();
        }
    }

    /**
     * 加载更多事件
     *
     * @param loadMoreListener
     */
    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void loadMore() {
        LogUtils.LOGE("base", "loadmore--------------------start");
        loadMoreListener.loadMore();
    }


    /**
     * StaggeredGridLayoutManager模式时，FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterView(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isEmptyView(position)) {
                        return gridManager.getSpanCount();
                    }
                    if (isFooterView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMore(recyclerView, layoutManager);
    }

    private boolean isFooterView(int pos) {
        return holderType.get(pos) == FOOT;
    }


    private boolean isEmptyView(int pos) {
        return holderType.get(pos) == CT_EMPTY;
    }


    public void notifyData() {
        init();
        notifyDataSetChanged();
    }
}