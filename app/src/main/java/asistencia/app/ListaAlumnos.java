package asistencia.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlumnos extends ListActivity implements OnClickListener{

    private ArrayList<Alumno> alumnos;
    private String nombre_asignatura;
    private String nombre_sesion;

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
                }
        );

        /* Para guardar la asistencia de los alumnos en un día determinado. */
        final Button guardar = (Button)findViewById(R.id.boton_guardar);

        guardar.setOnClickListener(this);
        registerForContextMenu(this.getListView());

    }

    // Métodos para comprobar la disponibilidad de la SD.
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

    // Lanzar un promt o ventana emergente para dar un nombre al archivo en el que se guardan las asistencias.
    @Override
    public void onClick(View v) {
        // Comprobamos si se ha pulsado el botón para guardar la asistencia de los alumnos.
        if(v.getId()==findViewById(R.id.boton_guardar).getId()){
            //Se abre la vista actual.
            LayoutInflater li = LayoutInflater.from(this);
            // Establecemos el prompt en la vista actual.
            View prompt = li.inflate(R.layout.prompt_guardar, null);
            // Construimos un nuevo menú de alerta.
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // Le asignamos la vista que le hemos dado.
            alertDialogBuilder.setView(prompt);
            // Creamos un EditText para leer lo que se escriba.

            final EditText nombre_ses = (EditText) prompt.findViewById(R.id.nombre_sesion);
            // Mostramos el mensaje del cuadro de dialogo
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Rescatamos el nombre del EditText y lo mostramos por pantalla

                            nombre_sesion = nombre_ses.getText().toString();
                            //nombre_sesion = String.valueOf(aux.getText().toString());
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Cancelamos el cuadro de dialogo
                    dialog.cancel();
                }

            });

            // Creamos un AlertDialog y lo mostramos
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();


            // Una ver realizado esto, tendremos que guardar una lista con los alumnos y su asistencia.
            // El formato será el siguiente:
            // Nombre de la sesión.
            // Fecha de la sesión.
            // Apellidos, Nombre del alumno
            // DNI
            // Si asiste o no marcado con V o X.

            // Creamos la fecha de la sesión
            Calendar fecha_sesion = Calendar.getInstance();
            String fecha = fecha_sesion.getTime().toString() + " -- " + Integer.toString(fecha_sesion.DAY_OF_MONTH) + "/" +
                Integer.toString(fecha_sesion.MONTH) + "/" + Integer.toString(fecha_sesion.YEAR) + ".";
            // Pasamos a escribir en un nuevo fichero con el nombre de la NombreSesión-Fecha
            File sdCard, directory_padre, directory_hijo, file;

            try {
                // validamos si se encuentra montada nuestra memoria externa y se puede escribir en ella...
                if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {

                    // Obtenemos el directorio de la memoria externa
                    sdCard = Environment.getExternalStorageDirectory();

                    // Clase que permite grabar texto en un archivo
                    FileOutputStream fout;
                    try {
                        // instanciamos un onjeto File para crear un nuevo
                        // directorio
                        // la memoria externa
                        directory_padre = new File(sdCard.getAbsolutePath()
                                + "/Asistencias");
                        // se crea el nuevo directorio donde se creará el
                        // archivo
                        directory_padre.mkdirs();

                        // Se crea o identifica (si ya está creado) el directorio hijo.
                        directory_hijo = new File(directory_padre.getAbsolutePath()
                                + "/" + nombre_asignatura);
                        directory_hijo.mkdirs();
                        // creamos el archivo en el nuevo directorio creado
                        file = new File(directory_hijo, nombre_sesion + "-" + fecha + ".txt");

                        // Es necesario definir a true el segundo campo de FileOutputStream para que no sobreescriba.
                        fout = new FileOutputStream(file,true);
                        // Convierte un stream de caracteres en un stream de
                        // bytes
                        // Para comprobar cada CheckBox nocesitamos definir un obejto que instancie cada checkbox de un alumno.
                        CheckBox cb;
                        // Instanciamos la lista de alumnos
                        ListView mainListView = getListView();
                        // Definimos un OutputStreamWriter para escribir en el archivo.
                        OutputStreamWriter ows = new OutputStreamWriter(fout);
                        // Y un stream para definir cómo escribir.
                        String cadena;
                        // Para cada elemento de la lista.
                        for (int x = 0; x<mainListView.getChildCount();x++){
                            cb = (CheckBox)mainListView.getChildAt(x).findViewById(R.id.chkAsist);
                            // Si está marcado el CheckBox escribimos Apellidos - DNI - V
                            if(cb.isChecked()){
                                cadena = alumnos.get(x).getApellidos() + " -- " + alumnos.get(x).getDNI() + " -- " + " V";
                            }
                            // En otro caso escribimos Apellidos - DNI - X
                            else{
                                cadena = alumnos.get(x).getApellidos() + " -- " + alumnos.get(x).getDNI() + " -- " + " F";
                            }
                            // Escribimos
                            ows.write(cadena); // Escribe en el buffer la cadena de texto
                            ows.write("\n");
                        }


                        ows.flush(); // Vuelca lo que hay en el buffer dentro del archivo
                        ows.close(); // Cierra el archivo de texto

                        Toast.makeText(getBaseContext(),
                                "El archivo de asistencia se ha almacenado correctamente!!!",
                                Toast.LENGTH_SHORT).show();

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

