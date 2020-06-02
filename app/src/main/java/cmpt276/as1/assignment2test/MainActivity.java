package cmpt276.as1.assignment2test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.LinearSystem;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
            theLens.setText(currentLens.getName()+" F"+currentLens.getAperture()+" "+currentLens.getFocalLenght()+"mm ");

            return itemView;
            //return super.getView(position,convertView,parent);
        }

    }
}