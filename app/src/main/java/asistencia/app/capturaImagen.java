package asistencia.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by USUARIO on 1/06/14.
 */
public class capturaImagen extends Activity {
    static int TAKE_PICTURE=1;
    Uri outputFileUri;
    private EditText dni, nombre,apellidos,fecha,asignatura;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto);
    }

    public void hacerFoto(View view)
    {
        nombre=(EditText) findViewById(R.id.editTextnom);
        apellidos=(EditText) findViewById(R.id.editText2ape);
        dni=(EditText) findViewById(R.id.editText2DNI);
        fecha=(EditText) findViewById(R.id.editText3fech);
        asignatura=(EditText) findViewById(R.id.editText4asig);

        String nom,ape,DNI,fech,asig;

        nom = nombre.getText().toString();
        ape = apellidos.getText().toString();
        DNI = dni.getText().toString();
        fech = fecha.getText().toString();
        asig = asignatura.getText().toString();

        /***********************************************************************************************/
        File sdCard, directory, file;
        sdCard=Environment.getExternalStorageDirectory();
        FileOutputStream fout;
        try {
            // instanciamos un onjeto File para crear un nuevo
            // directorio
            // la memoria externa
            directory = new File(sdCard.getAbsolutePath()
                    + "/Alumnos/");
            // se crea el nuevo directorio donde se creará el
            // archivo
            directory.mkdirs();

            // creamos el archivo en el nuevo directorio creado
            file = new File(directory, asig + ".txt");

            fout = new FileOutputStream(file,true);
            // Convierte un stream de caracteres en un stream de
            // bytes
            OutputStreamWriter ows = new OutputStreamWriter(fout);
            ows.write(nom); // Escribe en el buffer la cadena de texto
            ows.write("\n");
            ows.write(ape); // Escribe en el buffer la cadena de texto
            ows.write("\n");
            ows.write(DNI); // Escribe en el buffer la cadena de texto
            ows.write("\n");
            ows.write(fech); // Escribe en el buffer la cadena de texto
            ows.write("\n");
            ows.flush(); // Vuelca lo que hay en el buffer dentro del archivo
            ows.close(); // Cierra el archivo de texto

            Toast.makeText(getBaseContext(),
                    "El archivo se ha almacenado!!!",
                    Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /***********************************************************************************************/



        Intent i = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        File file2 = new File(Environment.getExternalStorageDirectory()+"/Imagenes/",DNI+".jpg");
        outputFileUri = Uri.fromFile(file2);
        i.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        startActivityForResult(i,TAKE_PICTURE);
    }

}
