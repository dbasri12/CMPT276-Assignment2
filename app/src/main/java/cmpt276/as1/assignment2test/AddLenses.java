package cmpt276.as1.assignment2test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddLenses extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lenses);
        setupDoSaveButton();
        setupCancelButton();
    }

    public static Intent makeIntent2(Context c){
        Intent intent =new Intent(c,AddLenses.class);
        return intent;
    }

    private void setupCancelButton(){
        Button cancelBtn=findViewById(R.id.buttonBack);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(Activity.RESULT_CANCELED,intent);
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

                EditText userTextFocal=(EditText) findViewById(R.id.editFocalNew);
                String userEntryFocal=userTextFocal.getText().toString();
                int userFocal= Integer.parseInt(userEntryFocal);

                EditText userTextAperture=(EditText) findViewById(R.id.editApertureNew);
                String userEntryAperture=userTextAperture.getText().toString();
                double userAperture=Double.parseDouble(userEntryAperture);

                Intent intent=new Intent();
                intent.putExtra("answerMake",userEntryMake);
                intent.putExtra("answerFocal",userFocal);
                intent.putExtra("answerAperture",userAperture);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}