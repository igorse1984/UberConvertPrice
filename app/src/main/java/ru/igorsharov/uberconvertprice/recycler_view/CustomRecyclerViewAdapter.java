package ru.igorsharov.uberconvertprice.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.igorsharov.uberconvertprice.R;

/**
 * Created by Игорь on 14.03.2018.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CardViewHolder> {

    private List<CustModelCard> mListCard;

    public CustomRecyclerViewAdapter(List<CustModelCard> listCard) {
        mListCard = listCard;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CustModelCard currentCustModelCard = mListCard.get (position);
        holder.tvTitle.setText(currentCustModelCard.getTitle());
        holder.tvDescr.setText(currentCustModelCard.getDescr ());
        holder.imgView.setImageResource(currentCustModelCard.getImgId());
    }

    @Override
    public int getItemCount() {
        return mListCard.size ();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView tvTitle;
        TextView tvDescr;

        CardViewHolder(View view) {
            super(view);
            imgView = view.findViewById(R.id.thumbnail);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvDescr = view.findViewById(R.id.tvDescr);
        }
    }
}