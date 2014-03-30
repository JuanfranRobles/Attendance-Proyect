package asistencia.app;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

/*
* Clase ActividadPricipal.
* -> Menú principal de la aplicación. Visualización de las opciones básicas. */
public class ActividadPrincipal extends Activity {

    // Actividad principal.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_principal);
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
}
