package rav.minibrowser;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import rav.minibrowser.Adapter.HistoryAdapter;
import rav.minibrowser.Contract.HistoryContract;
import rav.minibrowser.Model.History;

public class BrowserHistory extends AppCompatActivity {

    private RecyclerView rvview;
    private HistoryContract contract;
    private HistoryAdapter adapter;
    private ArrayList<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_history);

        rvview = (RecyclerView) findViewById(R.id.rvview);
        contract = new HistoryContract(this);
        historyList = new ArrayList<>();
       Cursor c = contract.list();
        cursorToArray(c);
        adapter = new HistoryAdapter(this,historyList);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rvview.setLayoutManager(layout);
        rvview.setAdapter(adapter);
    }

    private void cursorToArray(Cursor c) {
        if (c != null) {
            if (c.getCount() < 1) {
                Toast.makeText(this, "History Empty", Toast.LENGTH_SHORT).show();
            }
            while (c.moveToNext()) {
                long id = c.getLong(0);
                String url = c.getString(1);
                historyList.add(new History(url,id));
            }


        } else {
            Toast.makeText(this, "there was a problem loading data", Toast.LENGTH_SHORT).show();//there was a problem loading data
        }
    }
}

