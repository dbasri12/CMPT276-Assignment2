package cmpt276.as1.assignment2test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.LinearSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cmpt276.as1.assignment2test.model.Manager;
import cmpt276.as1.assignment2test.model.Lens;

public class MainActivity extends AppCompatActivity {

    private Manager manager=Manager.getInstance();
    private ArrayList<Lens> lenses= new ArrayList<Lens>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateManager();
        populateLensList();
        populateListView();
        registerClickCallback();
    }
    private void populateManager(){
        manager.add(new Lens("Canon", 1.8, 50));
        manager.add(new Lens("Tamron", 2.8, 90));
        manager.add(new Lens("Sigma", 2.8, 200));
        manager.add(new Lens("Nikon", 4, 200));
    }
    private void populateLensList(){
        for(int i=0;i<4;i++)
            lenses.add(manager.get(i));
    }
    private void populateListView(){
        ArrayAdapter<Lens> adapter=new MyListAdapter();
        ListView list=(ListView)findViewById(R.id.lenslist);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Lens>{
        public MyListAdapter(){
            super(MainActivity.this,R.layout.lens_item,lenses);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.lens_item, parent, false);
            }
            Lens currentLens=lenses.get(position);

            TextView theLens=(TextView) itemView.findViewById(R.id.listofLenses);
            theLens.setText(currentLens.toString());

            return itemView;
            //return super.getView(position,convertView,parent);
        }

    }
    private void registerClickCallback(){
        ListView listToClick=(ListView) findViewById(R.id.lenslist);
        listToClick.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> paret,View viewClicked,int position,long id){
                //TextView textView=(TextView) viewClicked;
                Toast.makeText(MainActivity.this,"Calculate Depth of Field",Toast.LENGTH_SHORT).show();

                Lens thisLens=lenses.get(position);
                //Intent intent=new Intent(MainActivity.this,DoFCalculator.class);
                Intent intent=DoFCalculator.makeIntent(MainActivity.this,thisLens);
                startActivity(intent);
            }
        });
    }
}