package cmpt276.as1.assignment2test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        extractDataFromIntent();
        setupLensUsed();
        setupDoCalculateButton();
    }
    private void extractDataFromIntent() {
        Intent intent=getIntent();
        String nameLens=intent.getStringExtra(EXTRA_NAME);
        double lensAperture=intent.getDoubleExtra(EXTRA_APERTURE,0);
        int lensFocal=intent.getIntExtra(EXTRA_FOCAL,0);
        myLens=new Lens(nameLens,lensAperture,lensFocal);
    }
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
    private void setupDoCalculateButton() {

        Button calcBtn= (Button) findViewById(R.id.btnCalculate);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userTextEntryCoC=(EditText) findViewById(R.id.editTextNumberDecimal4);
                String userDataCoC=userTextEntryCoC.getText().toString();
                double userCoC=Double.parseDouble(userDataCoC);

                EditText userTextEntryDistance=(EditText) findViewById(R.id.editTextNumberDecimal5);
                String userDataDistance=userTextEntryDistance.getText().toString();
                double userDistance=Double.parseDouble(userDataDistance);

                EditText userTextEntryAperture=(EditText)findViewById(R.id.editTextNumberDecimal6);
                String userDataAperture=userTextEntryAperture.getText().toString();
                double userAperture=Double.parseDouble(userDataAperture);

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
                textAnsDoF.setText(df2.format(answer.getDoF()/1000)+"m ");}

            }
        });
    }
}