package rav.minibrowser.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import rav.minibrowser.R;

/**
 * Created by Madan's-PC on 7/14/2017.
 */

public class HistoryHolder extends RecyclerView.ViewHolder {

    public TextView tvurl;
    public CardView card;
    public Button btndelete;


    public HistoryHolder(View itemView) {
        super(itemView);
        tvurl = (TextView) itemView.findViewById(R.id.tvurl);
        btndelete = (Button) itemView.findViewById(R.id.btndelete);
        card = (CardView) itemView.findViewById(R.id.card);
    }
}
