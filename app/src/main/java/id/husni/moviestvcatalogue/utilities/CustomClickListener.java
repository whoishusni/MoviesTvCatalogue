package id.husni.moviestvcatalogue.utilities;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.husni.moviestvcatalogue.R;

public class CustomClickListener {

    RecyclerView recyclerView;

    OnClickItem onClickItem;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
            onClickItem.onItemClicked(recyclerView,holder.getAdapterPosition(),v);
        }
    };

    RecyclerView.OnChildAttachStateChangeListener child = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (onClickItem != null) {
                view.setOnClickListener(listener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {

        }
    };

    public CustomClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.setTag(R.id.custom_item_id,this);
        recyclerView.addOnChildAttachStateChangeListener(child);
    }

    public CustomClickListener add(RecyclerView recyclerView) {
        CustomClickListener support = (CustomClickListener) recyclerView.getTag(R.id.custom_item_id);
        if (support == null) {
            support = new CustomClickListener(recyclerView);
        }
        return support;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        void onItemClicked(RecyclerView recyclerView, int position, View view);
    }


}
