package asistencia.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlumnos extends ListActivity {

    private ArrayList<Alumno> alumnos;
    private String nombre_asignatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listas_alumnos);

        // Inicializamos las variables.
        alumnos = new ArrayList<Alumno>();

        // Dado que necesitamos saber de qué fichero leer los alumnos, tendremos que decodificar
        // el dato obtenido al pulsar sobre el ListView de la clase ListaAsignaturas.java
        Bundle extras = getIntent().getExtras();
        if (extras!=null)
        {
            nombre_asignatura = extras.getString("nombre_asig");
            Toast.makeText(getBaseContext(), nombre_asignatura, Toast.LENGTH_SHORT).show();

        }
        //Llamar a la función rellenarArrayList() para añadir al ListView todos los alumnos definidos en el
        //archivo --- Nombre de la asignatura pulsada --- + --- .txt ---.
        alumnos = this.rellenarArrayList();

        // Creamos el Adaptador que se usa para mostrar las opciones del listado
        ArrayAdapter<Alumno> adaptador = new AdapterAlumnos(this, alumnos);
        // Asignamos el adaptador al ListActivity para que sepa cómo dibujar el listado de opciones
        setListAdapter(adaptador);

        //Definimos el Click del Botón.

        //final Button btnboton1 = (Button)findViewById(R.id.boton_marcar_todo);

        final CheckBox chkAll = (CheckBox)findViewById(R.id.chkMarcarTodo);
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Primero marcamos el checkbox principal.
                boolean selec;
                if(chkAll.isChecked()){
                    selec = true;
                }
                else{
                    selec = false;
                }
                chkAll.setChecked(selec);
                // Ahora se marcan todos los CheckBox siguiendo las siguientes pautas:
                // -> Si se marca el CheckBox principal: -- Se marcan aquellos CheckBox que estén desmarcados.
                // -> Si se desmarca el CheckBox pricipal: -- Se desmarcan aquellos CheckBox que estén marcados.
                CheckBox cb;
                ListView mainListView = getListView();
                // Para cada elemento de la lista.
                for (int x = 0; x<mainListView.getChildCount();x++){
                    cb = (CheckBox)mainListView.getChildAt(x).findViewById(R.id.chkAsist);
                    // Si se ha pulsado "Marcar todo" entonces se marcan los que no estén marcados.
                    if(chkAll.isChecked()){
                        if(!cb.isChecked()){
                            cb.setChecked(true);
                        }
                    }
                    // Por otro lado, si se pulsa "Desmarcar todo", se desmarcan aquellos que estén marcados.
                    else{
                        if(!chkAll.isChecked()){
                            if(cb.isChecked()){
                                cb.setChecked(false);
                            }
                        }
                    }
                }
            }
        });
        chkAll.setOnCheckedChangeListener(
                new CheckBox.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked) {
                            chkAll.setText("Desmarcar todo.");
                        } else {
                            chkAll.setText("Marcar todos");
                        }
                    }
                });
        registerForContextMenu(this.getListView());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.listas_alumnos, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if (v.getId() == this.getListView().getId()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(alumnos.get(info.position).getNombre());
            inflater.inflate(R.menu.menu_contextual_alumnos, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            default:
                return super.onContextItemSelected(item);
        }
    }

    /**
     * Método que rellena el ArrayList con los animales que queremos mostrar en
     * el ListView.
     */
    public ArrayList<Alumno> rellenarArrayList() {

        // Array en el que cargarán los alumnos leídos en el fichero.
        ArrayList<Alumno> lista_alumnos = new ArrayList<Alumno>();

        //Creación de datos.
        File sdCard, directory, file;
        if (Environment.getExternalStorageState().equals("mounted")) {

            // Obtenemos el directorio de la memoria externa
            sdCard = Environment.getExternalStorageDirectory();

            try {

                //Obtenemos el direcorio donde se encuentra nuestro archivo a leer
                directory = new File(sdCard.getAbsolutePath() + "/Alumnos/");
                directory.mkdirs();

                //Creamos un objeto File de nuestro archivo a leer
                file = new File(directory, nombre_asignatura + ".txt");

                //Creamos un objeto de la clase FileInputStream
                //el cual representa un stream del archivo que vamos a leer
                FileInputStream fin = new FileInputStream(file);

                //Creaos un objeto InputStreamReader que nos permitira
                //leer el stream del archivo abierto de la ruta del archivo que se quiere leer
                InputStreamReader is = new InputStreamReader(fin);

                //Creamos el BufferedReader para leer el archivo
                BufferedReader leer = new BufferedReader(is);

                //Guarda la linea que lee leer.readLine()
                String linea;
                // Necesitaremos un contador de campos para rellenar cada campo con su respectivo valor.
                int contador_campos =0;
                // Campos String a rellenar para cada alumno.
                String nombre_alum = " ";
                String apellidos_alum = " ";
                String DNI_alum = " ";
                String nacimiento_alum = " ";

                //Se encierra la lectura entre las sentencias try-catch por si hay algún error en el transcurso de la lectura.
                try {
                    //Mientra la linea sea distinto de null sigue leyendo
                    while((linea = leer.readLine()) != null){
                        if(contador_campos == 0){
                            nombre_alum = linea;
                        }
                        if(contador_campos == 1){
                            apellidos_alum = linea;
                        }
                        if(contador_campos == 2){
                            DNI_alum = linea;
                        }
                        if(contador_campos == 3){
                            nacimiento_alum = linea;
                            Alumno auxiliar = new Alumno(nombre_alum, apellidos_alum, DNI_alum, nacimiento_alum);
                            lista_alumnos.add(auxiliar);
                            contador_campos = -1;
                        }
                        contador_campos++;

                    }

                } catch (IOException e) {
                    //En caso de error muestra lo sucedido.
                    e.printStackTrace();
                }

                is.close();

                Toast.makeText(getBaseContext(), "El archivo ha sido cargado con éxito.", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                // TODO: handle exception
                e.printStackTrace();
            }

            //Obtenemos el direcorio donde se encuentra nuestro archivo a leer
            File carpeta = new File(sdCard.getAbsolutePath() + "/Imagenes/");
            carpeta.mkdirs();

            // Cuando se hayan rellenado todos los alumnos pasamos a asignales sus imagenes.
            for(int i = 0; i < lista_alumnos.size(); i++){
                File cara = new File(carpeta, lista_alumnos.get(i).getDNI() +".png");
                // Comprobar que existe la imagen.
                if (cara.exists()){
                    //Tenemos la foto guardada en la SD, asi que la cargamos
                    //En necesario añadir "/" para completar la ruta a la carpeta ya que getAbsolutePath devuelve la ruta sin /.
                    Bitmap image = BitmapFactory.decodeFile(carpeta.getAbsolutePath() + "/" + lista_alumnos.get(i).getDNI() + ".png");
                    lista_alumnos.get(i).setFoto(image);
                }
            }

        }

        return lista_alumnos;


    }

    public void onListItemClick(ListView parent, View view, int position, long id)
    {
        if (parent.isItemChecked(position))
            alumnos.get(position).setAsiste(true);
        else
            alumnos.get(position).setAsiste(false);

    }

}

