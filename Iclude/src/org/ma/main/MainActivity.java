package org.ma.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    ListView listview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String []titles={"���٤Ƥϰ�����","�s��","����"};
        setContentView(R.layout.main);
        listview=(ListView)findViewById(R.id.listview);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        listview.setAdapter(adapter);
    }
}