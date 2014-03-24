package asistencia.app;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlumnos extends ListActivity {

    private ArrayList<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listas_alumnos);

        // Inicializamos las variables.
        alumnos = new ArrayList<Alumno>();

        rellenarArrayList();

        // Creamos el Adaptador que se usa para mostrar las opciones del listado
        ArrayAdapter<Alumno> adaptador = new AdapterAlumnos(this, alumnos);
        // Asignamos el adaptador al ListActivity para que sepa cómo dibujar el listado de opciones
        setListAdapter(adaptador);

        //Definimos el Click del Botón.

        final Button btnboton1 = (Button)findViewById(R.id.boton_marcar_todo);

        btnboton1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                for (int i=0; i<=alumnos.size(); i++){
                    if (alumnos.get(i).getAsiste()){
                        Toast.makeText(getBaseContext(), "Has hecho clic en la '"+ alumnos.get(i).getNombre()+"'", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listas_alumnos, menu);
        return true;
    }

    /**
     * Método que rellena el ArrayList con los animales que queremos mostrar en
     * el ListView.
     */
    private void rellenarArrayList() {

        alumnos.add(new Alumno(R.drawable.caragollum, "Smeagol", "Gollum Digol", "12365874G", "20/03/1812"));
        alumnos.add(new Alumno(R.drawable.chewaka, "Chawaka", "Yeti Pelón", "32469543G", "21/10/1500"));
    }

    public void onListItemClick(ListView parent, View view, int position, long id)
    {
        if (parent.isItemChecked(position))
            alumnos.get(position).setAsiste(true);
        else
            alumnos.get(position).setAsiste(false);

    }

}

