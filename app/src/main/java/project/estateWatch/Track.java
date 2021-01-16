package project.estateWatch;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Track extends AppCompatActivity {

    private ArrayList<classListStatus> itemArrayList;  //List items Array
    private MyAppAdapter myAppAdapter; //Array Adapter
    private ListView listView; // ListView
    private boolean success = false; // boolean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        listView = (ListView) findViewById(R.id.listViews); //ListView Declaration
        itemArrayList = new ArrayList(); // Arraylist Initialization

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");


    }


    private class SyncData extends AsyncTask<String, String, String> {
        //String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;




        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Track.this, "Synchronizing",
                    "Crime reports are Loading! Please Wait...", true);

        }

        @Override
        protected String doInBackground(String... strings)
        {

            // Initialize connection variables.
            String host = "watchman.mysql.database.azure.com";
            String database = "watchman";
            String user = "myadmin@watchman";
            String password = "Aniekana12";


            String msg ="";



            try
            {
                String url = String.format("jdbc:mysql://%s/%s", host, database);

                // Set connection properties.
                Properties properties = new Properties();
                properties.setProperty("user", user);
                properties.setProperty("password", password);
                properties.setProperty("useSSL", "true");
                properties.setProperty("verifyServerCertificate", "true");
                properties.setProperty("requireSSL", "false");

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, properties);
                if(conn == null)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"Something went Wrong",Toast.LENGTH_LONG);
                    toast.show();

                }
                else if(conn != null)
                {


                    String query2 = "select ReportName,ReportDescription,Status,Date FROM crimereport";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query2);

                    if (rs != null) // if resultset not null, add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new classListStatus(rs.getString("ReportName"), rs.getString("ReportDescription"), rs.getString("Status"),rs.getString("Date")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }


        //textView.setText("Welcome " + username);

        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(Track.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new Track.MyAppAdapter(itemArrayList, Track.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }

            }
        }
    }

    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder {
            TextView ReportName,ReportDescription,Status,Date;
        }

        public List<classListStatus> crimeList;

        public Context context;
        ArrayList<classListStatus> arraylist;

        MyAppAdapter(ArrayList<classListStatus> apps, Context context) {
            this.crimeList = apps;
            this.context = context;
            arraylist = new ArrayList<classListStatus>();
            arraylist.addAll(crimeList);
        }

        @Override
        public int getCount() {
            return crimeList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            Track.MyAppAdapter.ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_contents, parent, false);
                viewHolder = new Track.MyAppAdapter.ViewHolder();
                viewHolder.ReportName = (TextView) rowView.findViewById(R.id.statusReportName);
                viewHolder.ReportDescription = (TextView) rowView.findViewById(R.id.statusReportDesc);
                viewHolder.Status = rowView.findViewById(R.id.statusReportStatus);
                viewHolder.Date = rowView.findViewById(R.id.statusReportDate);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (Track.MyAppAdapter.ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.ReportName.setText(crimeList.get(position).getstatusReportName() + "");
            viewHolder.ReportDescription.setText(crimeList.get(position).getstatusReportDescription() +"");
            viewHolder.Status.setText(crimeList.get(position).getstatusStatus() + "");
            viewHolder.Date.setText(crimeList.get(position).getstatusDate() + "");

            return rowView;
        }
    }
}
