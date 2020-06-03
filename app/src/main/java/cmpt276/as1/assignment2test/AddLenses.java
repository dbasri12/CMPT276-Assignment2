package cmpt276.as1.assignment2test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

import cmpt276.as1.assignment2test.model.Lens;

public class AddLenses extends AppCompatActivity {
    private static DecimalFormat df2=new DecimalFormat("#.##");
    private static final String EXTRA_NAME="the name of lens";
    private static final String EXTRA_APERTURE="the maximum aperture";
    private static final String EXTRA_FOCAL="the focal length";

    private Lens myLens;
    private static boolean checkEdit=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lenses);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDoSaveButton();
        setupCancelButton();
        if(checkEdit==false){
            extractDataFromIntent();
            //setupDoSaveButtonForEdit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent=new Intent();
        switch (item.getItemId()){
            case R.id.itemBack:
                setResult(Activity.RESULT_CANCELED,intent);
                checkEdit=true;
                finish();
                return true;
            case R.id.itemSave:
                EditText userTextMake= (EditText) findViewById(R.id.editMake);
                String userEntryMake=userTextMake.getText().toString();
                int userFocal=0;
                double userAperture=0;

                EditText userTextFocal=(EditText) findViewById(R.id.editFocalNew);
                String userEntryFocal=userTextFocal.getText().toString();
                if(userEntryFocal.length()==0)
                    Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                else
                    userFocal= Integer.parseInt(userEntryFocal);

                EditText userTextAperture=(EditText) findViewById(R.id.editApertureNew);
                String userEntryAperture=userTextAperture.getText().toString();
                if(userEntryAperture.length()==0)
                    Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                else
                    userAperture=Double.parseDouble(userEntryAperture);
                checkEdit=true;
                if(userEntryMake.length()==0 ||  userFocal<=0 || userAperture<=1.4){
                    Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_CANCELED,intent);
                }
                else{
                    intent.putExtra(EXTRA_NAME,userEntryMake);
                    intent.putExtra(EXTRA_FOCAL,userFocal);
                    intent.putExtra(EXTRA_APERTURE,userAperture);
                    setResult(Activity.RESULT_OK,intent);
                    finish();}
        }
        return super.onOptionsItemSelected(item);
    }
    private void extractDataFromIntent() {
        Intent intent=getIntent();
        String nameLens=intent.getStringExtra(EXTRA_NAME);
        double lensAperture=intent.getDoubleExtra(EXTRA_APERTURE,0);
        int lensFocal=intent.getIntExtra(EXTRA_FOCAL,0);
        myLens=new Lens(nameLens,lensAperture,lensFocal);
        EditText userMake=(EditText) findViewById(R.id.editMake);
        userMake.setText(myLens.getName());
        EditText userAperture=(EditText) findViewById(R.id.editApertureNew);
        userAperture.setText(df2.format(myLens.getAperture()));
        EditText userFocal=(EditText) findViewById(R.id.editFocalNew);
        userFocal.setText(df2.format(myLens.getFocalLenght()));
    }

    public static Intent makeIntent2(Context c){
        Intent intent =new Intent(c,AddLenses.class);
        return intent;
    }
    public static Intent makeIntentForEdit(Context context, Lens lens){
        Intent intent=new Intent(context,AddLenses.class);
        intent.putExtra(EXTRA_NAME,lens.getName());
        intent.putExtra(EXTRA_FOCAL,lens.getFocalLenght());
        intent.putExtra(EXTRA_APERTURE,lens.getAperture());
        checkEdit=false;
        return intent;
    }

    private void setupCancelButton(){
        Button cancelBtn=findViewById(R.id.buttonBack);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                checkEdit=true;
                finish();
            }
        });
    }

    private void setupDoSaveButton(){
        Button saveBtn=findViewById(R.id.buttonSave);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userTextMake= (EditText) findViewById(R.id.editMake);
                String userEntryMake=userTextMake.getText().toString();
                int userFocal=0;
                double userAperture=0;

                EditText userTextFocal=(EditText) findViewById(R.id.editFocalNew);
                String userEntryFocal=userTextFocal.getText().toString();
                if(userEntryFocal.length()==0)
                    userFocal=0;
                    //Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                else
                    userFocal= Integer.parseInt(userEntryFocal);

                EditText userTextAperture=(EditText) findViewById(R.id.editApertureNew);
                String userEntryAperture=userTextAperture.getText().toString();
                if(userEntryAperture.length()==0)
                    userAperture=0;
                    //Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                else
                    userAperture=Double.parseDouble(userEntryAperture);
                checkEdit=true;

                Intent intent=new Intent();
                if(userEntryMake.length()==0 ||  userFocal<=0 || userAperture<=1.4){
                    Toast.makeText(AddLenses.this,"Invalid input",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_CANCELED,intent);
                }
                else{
                intent.putExtra(EXTRA_NAME,userEntryMake);
                intent.putExtra(EXTRA_FOCAL,userFocal);
                intent.putExtra(EXTRA_APERTURE,userAperture);
                Toast.makeText(AddLenses.this,"Lens saved",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,intent);
                finish();}
            }
        });
    }
}