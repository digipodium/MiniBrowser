package rav.minibrowser.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import rav.minibrowser.Contract.HistoryContract;
import rav.minibrowser.Holder.HistoryHolder;
import rav.minibrowser.Model.History;
import rav.minibrowser.R;

/**
 * Created by Madan's-PC on 7/14/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {


    Context context;
    ArrayList<History> historyList;

    public HistoryAdapter(Context context, ArrayList<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_view, parent, false);
        return new HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, final int position) {
        final History history = historyList.get(position);
        holder.tvurl.setText(history.getUrl());
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = history.getId();
                HistoryContract contract = new HistoryContract(context);
                contract.deleteById(String.valueOf(id));
                Toast.makeText(context, " Url history deleted", Toast.LENGTH_SHORT).show();
                refreshList();
                notifyDataSetChanged();


            }
        });
    }

    public void refreshList() {
        HistoryContract contract = new HistoryContract(context);
        cursorToArray(contract.list());
    }

    private void cursorToArray(Cursor c) {
        if (c != null) {
            historyList.clear();
            if (c.getCount() ==0) {
                Toast.makeText(context, " Url History Empty", Toast.LENGTH_SHORT).show();
                return;
            }
            while (c.moveToNext()) {
                long id = c.getLong(0);
                String url = c.getString(1);
                historyList.add(new History(url, id));
            }
        } else {
            Toast.makeText(context, "there was a problem loading data", Toast.LENGTH_SHORT).show();//there was a problem loading data
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
