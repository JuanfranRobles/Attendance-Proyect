package asistencia.app;

/**
 * Created by Juan Francisco on 4/03/14.
 */
import android.os.Bundle;
import android.app.Activity;

/*
* Clase AcercaDe.
* -> Objetivo: Lanzar el menú Acerca de que contiene información sobre nuestra aplicación.
*   -> Objetivo.
*   -> Desarrolladores.
*   -> Lenguajes.
*   */
public class AcercaDe extends Activity{

    // Llamada al Layout acerda_de que contiene la estructura e información
    // sobre la App y su desarrollo.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de);
    }

}
