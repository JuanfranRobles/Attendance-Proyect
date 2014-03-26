package asistencia.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Juan Francisco on 25/03/14.
 */
public class IntroAsignaturas extends Activity implements OnClickListener {

    private EditText txtNombre;
    private EditText txtCurso;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introducir_asignatura);

        txtNombre = (EditText)findViewById(R.id.editTextNombreAsig);
        txtCurso = (EditText)findViewById(R.id.editTextCursoAsig);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardarAsig);
        btnGuardar.setOnClickListener(this);

    }
    /* Método para saber si sólo se puede leer en la SD. */
    public static boolean isExternalStorageReadOnly() {
        boolean lectura_escritura = false;
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            lectura_escritura = true;
        }
        else{
            lectura_escritura = false;
        }
        return lectura_escritura;
    }
    /* Método para saber si tenemos montada una SD en el sistema. */
    public static boolean isExternalStorageAvailable() {
        boolean sd_montada = false;
        String extStorageState = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(extStorageState)){
            sd_montada= true;
        }
        else{
            sd_montada = false;
        }

        return sd_montada;
    }
    @Override
    public void onClick(View arg0) {

        File sdCard, directory, file;

        try {
            // validamos si se encuentra montada nuestra memoria externa y se puede escribir en ella...
            if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();

                //if (arg0.equals(btnGuardar)) {
                    String str_nombre = txtNombre.getText().toString();
                    String str_curso = txtCurso.getText().toString();
                    // Clase que permite grabar texto en un archivo
                    FileOutputStream fout;
                    try {
                        // instanciamos un onjeto File para crear un nuevo
                        // directorio
                        // la memoria externa
                        directory = new File(sdCard.getAbsolutePath()
                                + "/Asignaturas");
                        // se crea el nuevo directorio donde se creará el
                        // archivo
                        directory.mkdirs();

                        // creamos el archivo en el nuevo directorio creado
                        file = new File(directory, "asignaturas.txt");

                        fout = new FileOutputStream(file,true);
                        // Convierte un stream de caracteres en un stream de
                        // bytes
                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        String cadena = str_nombre;/* + "   ---   " + "Curso: " + str_curso + "º";*/
                        ows.write(cadena); // Escribe en el buffer la cadena de texto
                        ows.write("\n");
                        ows.flush(); // Volca lo que hay en el buffer al archivo
                        ows.close(); // Cierra el archivo de texto

                        Toast.makeText(getBaseContext(),
                                "El archivo se ha almacenado!!!",
                                Toast.LENGTH_SHORT).show();

                        txtNombre.setText("");
                        txtCurso.setText("");

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                //}

            }else{
                Toast.makeText(getBaseContext(),
                        "El almacenamineto externio no se encuentra disponible",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            // TODO: handle exception

        }

    }
}
