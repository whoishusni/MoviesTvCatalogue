package id.husni.moviestvcatalogue.utilities;

import android.view.View;

public class CustomItemClick implements View.OnClickListener {
    private int position;
    OnItemClick onItemClick;

    public CustomItemClick(int position, OnItemClick onItemClick) {
        this.position = position;
        this.onItemClick = onItemClick;
    }

    @Override
    public void onClick(View v) {
        onItemClick.onItemClicked(v, position);
    }

    public interface OnItemClick {
        void onItemClicked(View view, int position);
    }
}
