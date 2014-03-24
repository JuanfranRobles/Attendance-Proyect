package asistencia.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Juan Francisco on 14/03/14.
 */
public class MarcadoAsistencia extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_lista_alumnos);

        // --- Funcionalidad del CheckBox ---
        CheckBox marca = (CheckBox) findViewById(R.id.chkAsist);
        marca.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if(((CheckBox)v).isChecked()){
                    Toast.makeText(getBaseContext(), "Presente", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "No presente", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
