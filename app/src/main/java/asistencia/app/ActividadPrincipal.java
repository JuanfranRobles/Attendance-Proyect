package asistencia.app;

import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

import java.io.File;

/*
* Clase ActividadPricipal.
* -> Menú principal de la aplicación. Visualización de las opciones básicas. */
public class ActividadPrincipal extends Activity {

    // Actividad principal.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (existenDirectorios())
            setContentView(R.layout.fragment_principal);
        else
        {
            File sdCard, directorio1, directorio2, directorio3;
            sdCard = Environment.getExternalStorageDirectory();
            directorio1 = new File(sdCard.getAbsolutePath() + "/Asignaturas");
            directorio2 = new File(sdCard.getAbsolutePath() + "/Alumnos");
            directorio3 = new File(sdCard.getAbsolutePath() + "/Asistencias");

            directorio1.mkdirs();
            directorio2.mkdirs();
            directorio3.mkdirs();

            setContentView(R.layout.presentacion);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actividad_principal, menu);
        return true;
    }
    // Esta actividad lanzará la información acerca de
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);
    }

    // Esta actividad lanzará el menú de asignaturas.
    public void lanzarMenuAsignaturas(View view){
        Intent i = new Intent(this, MenuAsignatura.class);
        startActivity(i);
    }

    // Esta actividad cerrará la aplicación.
    public void cerrar(View view) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        finish();
    }

    public void fotografiar(View view)
    {
        Intent i = new Intent(this,capturaImagen.class);
        startActivity(i);
    }

    public void lanzarPrincipal(View view)
    {
        Intent i = new Intent(this,ActividadPrincipal.class);
        startActivity(i);
    }

    public boolean existenDirectorios()
    {
        File sdCard, directorio1, directorio2, directorio3;
        sdCard = Environment.getExternalStorageDirectory();
        directorio1 = new File(sdCard.getAbsolutePath() + "/Asignaturas");
        directorio2 = new File(sdCard.getAbsolutePath() + "/Alumnos");
        directorio3 = new File(sdCard.getAbsolutePath() + "/Asistencias");

        if(directorio1.isDirectory() || directorio2.isDirectory() || directorio3.isDirectory())
            return true;
        else return false;

    }

}
