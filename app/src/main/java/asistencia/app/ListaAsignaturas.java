package asistencia.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Juan Francisco on 24/03/14.
 */

public class ListaAsignaturas extends Activity{

    private ListView listview;
    private static final int MNU_OPC1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_asignaturas);

        listview = (ListView) findViewById(R.id.list_asignaturas);

        final ArrayList<String> list;

        list = rellenarAdapterAsignaturas();

        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                String nombre_asignatura = list.get(position);
                Intent i = new Intent(getApplicationContext(), ListaAlumnos.class);
                i.putExtra("nombre_asig", nombre_asignatura);
                startActivity(i);
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();

            }
        });
        /*Bundle extras = getIntent().getExtras();
        if (extras!=null)
        {
            String identificador = extras.getString("identificador");
        }*/
        registerForContextMenu(listview);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.list_asignaturas)
        {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo)menuInfo;

            menu.setHeaderTitle(listview.getAdapter().getItem(info.position).toString());

            inflater.inflate(R.menu.menu_contextual_asignaturas, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            default:
                return super.onContextItemSelected(item);
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private void construirMenu(Menu menu)
    {
        menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Añadir Asignatura")
                .setIcon(android.R.drawable.ic_menu_add);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        construirMenu(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MNU_OPC1:
                aniadirAsignatura();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Lanzar la pantalla de lista de alumnos.
    public void aniadirAsignatura(){
        Intent i = new Intent(this, IntroAsignaturas.class);
        startActivity(i);
    }

    public ArrayList<String> rellenarAdapterAsignaturas(){

        ArrayList<String> lista_asignaturas = new ArrayList<String>();

        //Creación de datos.
        File sdCard, directory, file;
        if (Environment.getExternalStorageState().equals("mounted")) {

            // Obtenemos el directorio de la memoria externa
            sdCard = Environment.getExternalStorageDirectory();

            try {

                //Obtenemos el direcorio donde se encuentra nuestro archivo a leer
                directory = new File(sdCard.getAbsolutePath() + "/Asignaturas/");

                //Creamos un objeto File de nuestro archivo a leer
                file = new File(directory, "asignaturas.txt");

                //Creamos un objeto de la clase FileInputStream
                //el cual representa un stream del archivo que vamos a leer
                FileInputStream fin = new FileInputStream(file);

                //Creaos un objeto InputStreamReader que nos permitira
                //leer el stream del archivo abierto de la ruta del archivo que se quiere leer
                InputStreamReader is = new InputStreamReader(fin);

                //Creamos el BufferedReader para leer el archivo
                BufferedReader leer = new BufferedReader(is);
                //Variable en la que se guardará todo el texto leido en el archivo.
                //StringBuilder textoFinal = new StringBuilder();
                //Guarda la linea que lee leer.readLine()
                String linea;

                //Se encierra la lectura entre las sentencias try-catch por si hay algún error en el transcurso de la lectura.
                try {
                    //Mientra la linea sea distinto de null sigue leyendo
                    while((linea = leer.readLine()) != null){

                            lista_asignaturas.add(linea);

                    }
                    //lista_asignaturas.remove(lista_asignaturas.size()-1);
                } catch (IOException e) {
                    //En caso de error muestra lo sucedido.
                    e.printStackTrace();
                }

                is.close();

                Toast.makeText(getBaseContext(),
                        "El archivo ha sido cargado",
                        Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

        }
        return lista_asignaturas;
    }
}

