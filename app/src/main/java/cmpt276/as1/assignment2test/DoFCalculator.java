package cmpt276.as1.assignment2test;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import cmpt276.as1.assignment2test.model.DOFcalc;
import cmpt276.as1.assignment2test.model.Lens;

public class DoFCalculator extends AppCompatActivity {
    private static DecimalFormat df2=new DecimalFormat("#.##");
    private static final String EXTRA_NAME="the name of lens";
    private static final String EXTRA_APERTURE="the maximum aperture";
    private static final String EXTRA_FOCAL="the focal length";

    private Lens myLens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_f_calculator);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        extractDataFromIntent();
        setupLensUsed();
        setupDoCalculateButton();
        setupEditButton();
        setupDeleteButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu2,menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemBack:
                Intent intent=new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void extractDataFromIntent() {
        Intent intent=getIntent();
        String nameLens=intent.getStringExtra(EXTRA_NAME);
        double lensAperture=intent.getDoubleExtra(EXTRA_APERTURE,0);
        int lensFocal=intent.getIntExtra(EXTRA_FOCAL,0);
        myLens=new Lens(nameLens,lensAperture,lensFocal);
    }
    //for title at the very top
    private void setupLensUsed(){
        TextView textView=(TextView) findViewById(R.id.textView);
        textView.setText("Camera details: " + myLens.toString());
    }
    public static Intent makeIntent(Context context, Lens lens){
        Intent intent=new Intent(context,DoFCalculator.class);
        intent.putExtra(EXTRA_NAME,lens.getName());
        intent.putExtra(EXTRA_FOCAL,lens.getFocalLenght());
        intent.putExtra(EXTRA_APERTURE,lens.getAperture());
        return intent;
    }
    private void setupDeleteButton() {
        Button deleteBtn=(Button) findViewById(R.id.buttonDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Toast.makeText(DoFCalculator.this,"Lens deleted",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
    private void setupEditButton() {
        Button editBtn=(Button) findViewById(R.id.buttonEdit);
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentEdit=AddLenses.makeIntentForEdit(DoFCalculator.this,myLens);
                startActivityForResult(intentEdit,42);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case 42:
                String make = data.getStringExtra(EXTRA_NAME);
                int focal = data.getIntExtra(EXTRA_FOCAL,0);
                double aperture = data.getDoubleExtra(EXTRA_APERTURE,0);
                Lens answer = new Lens(make, aperture, focal);
                myLens = answer;
                setupLensUsed();
                editedLens();
                break;
        }
    }
    private void editedLens(){
        Intent intent=new Intent();
        intent.putExtra(EXTRA_NAME,myLens.getName());
        intent.putExtra(EXTRA_FOCAL,myLens.getFocalLenght());
        intent.putExtra(EXTRA_APERTURE,myLens.getAperture());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private void setupDoCalculateButton() {

        Button calcBtn= (Button) findViewById(R.id.btnCalculate);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double userCoC=0;
                double userDistance=0;
                double userAperture=0;
                EditText userTextEntryCoC=(EditText) findViewById(R.id.editTextNumberDecimal4);
                String userDataCoC=userTextEntryCoC.getText().toString();
                if(userDataCoC.length()==0)
                    userCoC=0;
                else
                   userCoC=Double.parseDouble(userDataCoC);

                EditText userTextEntryDistance=(EditText) findViewById(R.id.editTextNumberDecimal5);
                String userDataDistance=userTextEntryDistance.getText().toString();
                if(userDataDistance.length()==0)
                    userDistance=0;
                else
                    userDistance=Double.parseDouble(userDataDistance);

                EditText userTextEntryAperture=(EditText)findViewById(R.id.editTextNumberDecimal6);
                String userDataAperture=userTextEntryAperture.getText().toString();
                if(userDataAperture.length()==0)
                    userAperture=0;
                else
                    userAperture=Double.parseDouble(userDataAperture);

                if(userCoC<=0||userDistance<=0||userAperture<1.4)
                    Toast.makeText(DoFCalculator.this,"Invalid input",Toast.LENGTH_SHORT).show();
                else{
                if(userAperture<myLens.getAperture()){
                    TextView textAnsHFocal=(TextView) findViewById(R.id.textResultHyperFocal);
                    textAnsHFocal.setText("Invalid Aperture");
                    TextView textAnsNearFP=(TextView) findViewById(R.id.textResultNearFP);
                    textAnsNearFP.setText("Invalid Aperture");
                    TextView textAnsFarFP=(TextView) findViewById(R.id.textResultFarFP);
                    textAnsFarFP.setText("Invalid Aperture");
                    TextView textAnsDoF=(TextView) findViewById(R.id.textResultDOF);
                    textAnsDoF.setText("Invalid Aperture");
                }
                else{

                DOFcalc answer=new DOFcalc(myLens,userDistance,userAperture,userCoC);
                TextView textAnsHFocal=(TextView) findViewById(R.id.textResultHyperFocal);
                textAnsHFocal.setText(df2.format(answer.getHFocal()/1000)+"m ");
                TextView textAnsNearFP=(TextView) findViewById(R.id.textResultNearFP);
                textAnsNearFP.setText(df2.format(answer.getNearFP()/1000)+"m ");
                TextView textAnsFarFP=(TextView) findViewById(R.id.textResultFarFP);
                textAnsFarFP.setText(df2.format(answer.getFarFP()/1000)+"m ");
                TextView textAnsDoF=(TextView) findViewById(R.id.textResultDOF);
                textAnsDoF.setText(df2.format(answer.getDoF()/1000)+"m ");}}

            }
        });
    }
}