package project.estateWatch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class LearnMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_more);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        List<item> mList = new ArrayList<>();
        mList.add(new item(R.drawable.learnmoreback,"Chief Security Officer",R.drawable.ic_launcher_background,"Mr. Johnson Suleiman"));
        mList.add(new item(R.drawable.learnmoreback,"Assistant Chief Security Officer",R.drawable.ic_launcher_background,"Mr. Tunde Samuel"));
        mList.add(new item(R.drawable.learnmoreback,"D.P.O Lokogoma area command",R.drawable.ic_launcher_background,"Chief Adejo Musa"));
        mList.add(new item(R.drawable.learnmoreback,"Estate Manager",R.drawable.ic_launcher_background,"Chief Musa sadiq"));
        Adapter adapter = new Adapter(this, mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void callPhone(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:08129730264"));
        startActivity(callIntent);
    }
}
