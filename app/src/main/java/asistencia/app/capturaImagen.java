package asistencia.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;

import java.io.File;

/**
 * Created by USUARIO on 1/06/14.
 */
public class capturaImagen extends Activity {
    static int TAKE_PICTURE=1;
    Uri outputFileUri;
    private EditText dni;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foto);
    }

    public void hacerFoto(View view)
    {
        dni=(EditText) findViewById(R.id.editText2DNI);
        String DNI = dni.getText().toString();
        Intent i = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()+"/Imagenes/",DNI+".jpg");
        outputFileUri = Uri.fromFile(file);
        i.putExtra(MediaStore.EXTRA_OUTPUT,outputFileUri);
        startActivityForResult(i,TAKE_PICTURE);
    }

}
