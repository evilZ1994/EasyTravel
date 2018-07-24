package me.codekiller.easytravel.UI.Result;

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

public class ViewPotRecyclerAdapter extends RecyclerView.Adapter<ViewPotRecyclerAdapter.ViewHolder> {
    private List<ViewPotItem> items;
    private Context context;

    public ViewPotRecyclerAdapter(Context context, List<ViewPotItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_pot_item, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.draweeView.setImageURI(items.get(position).getImgUrl());
        holder.textView.setText(items.get(position).getName());
        holder.draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPotActivity.class);
                intent.putExtra("url", items.get(position).getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView draweeView;
        private AppCompatTextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = itemView.findViewById(R.id.view_pot_pic);
            textView = itemView.findViewById(R.id.view_pot_name);
        }
    }
}
