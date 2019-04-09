package sh.com.myapplication10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        Button taskManagement= (Button) findViewById(R.id.taskManagement);
        Button Silent = (Button) findViewById(R.id.Silent);
        Button Location = (Button) findViewById(R.id.Location);

        taskManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainOption.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Silent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOption.this,SilentMainActivity.class);
                startActivity(intent);
            }
        });

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOption.this,MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}