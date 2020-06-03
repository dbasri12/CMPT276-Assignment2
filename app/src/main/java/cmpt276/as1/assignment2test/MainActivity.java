package cmpt276.as1.assignment2test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.LinearSystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cmpt276.as1.assignment2test.model.Manager;
import cmpt276.as1.assignment2test.model.Lens;

public class MainActivity extends AppCompatActivity {

    private Manager manager=Manager.getInstance();
    private ArrayList<Lens> lenses= new ArrayList<Lens>();
    private static final String EXTRA_NAME="the name of lens";
    private static final String EXTRA_APERTURE="the maximum aperture";
    private static final String EXTRA_FOCAL="the focal length";
    private static final String SHARED_P="shared preferences";
    private static final String task_list="task list";
    private int indexP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        if(lenses.size()==0){
            populateManager();
            populateLensList();
        }
        populateListView();
        registerClickCallback();
        setupAddFABButton();
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
    private void saveData() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_P,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = gson.toJson(lenses);
        editor.putString(task_list,json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_P,MODE_PRIVATE);
        Gson gson = new Gson();
        String json=sharedPreferences.getString(task_list,null);
        Type type=new TypeToken<ArrayList<Lens>>(){}.getType();
        lenses=gson.fromJson(json,type);

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
                indexP=position;
                //Intent intent=new Intent(MainActivity.this,DoFCalculator.class);
                Intent intent=DoFCalculator.makeIntent(MainActivity.this,thisLens);
                startActivityForResult(intent,43);

            }
        });
    }
    private void setupAddFABButton(){
        FloatingActionButton fabAdd=findViewById(R.id.floatingActionButton);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"Add new Lens",Toast.LENGTH_SHORT).show();

                Intent intentAdd = AddLenses.makeIntent2(MainActivity.this);
                startActivityForResult(intentAdd,42);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_CANCELED){
            return;
        }

        switch(requestCode){
            case 42:
                String makeAnswer=data.getStringExtra("answerMake");
                int focalAnswer=data.getIntExtra("answerFocal",0);
                double apertureAnswer=data.getDoubleExtra("answerAperture",0);
                Lens answerTest=new Lens(makeAnswer,apertureAnswer,focalAnswer);
                lenses.add(answerTest);
                saveData();
                populateListView();
                break;
            case 43:
                String make = data.getStringExtra(EXTRA_NAME);
                int focal = data.getIntExtra(EXTRA_FOCAL, 0);
                double aperture = data.getDoubleExtra(EXTRA_APERTURE, 0);
                if(make==null){
                    lenses.remove(indexP);
                    saveData();
                    populateListView();
                    break;
                }
                else{
                lenses.remove(indexP);
                Lens lensAns = new Lens(make, aperture, focal);
                lenses.add(lensAns);
                saveData();
                populateListView();
                break;}
        }

    }
}