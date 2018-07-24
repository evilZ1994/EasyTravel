package me.codekiller.easytravel.UI.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import me.codekiller.easytravel.R;
import me.codekiller.easytravel.UI.Detail.ViewPotActivity;
import me.codekiller.easytravel.bean.ViewPotItem;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<ViewPotItem> items;
    private Context context;

    public RecyclerAdapter(Context context, List<ViewPotItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new TitleHolder(LayoutInflater.from(context).inflate(R.layout.title_item, null));
        }
        return new ViewPotHolder(LayoutInflater.from(context).inflate(R.layout.view_pot_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ((TitleHolder) holder).title.setText(items.get(position).getName());
        } else {
            ((ViewPotHolder) holder).draweeView.setImageURI(items.get(position).getImgUrl());
            ((ViewPotHolder) holder).name.setText(items.get(position).getName());
            ((ViewPotHolder) holder).draweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewPotActivity.class);
                    intent.putExtra("url", items.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewPotHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView draweeView;
        private AppCompatTextView name;

        public ViewPotHolder(View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.view_pot_pic);
            name = itemView.findViewById(R.id.view_pot_name);
        }
    }

    class TitleHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView title;

        public TitleHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
        }
    }
}
