package asistencia.app;

/**
 * Created by Juan Francisco on 11/03/14.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.ParseException;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.util.Collections;

/**
 *
 * @author Juan Francisco
 */
public class Alumno{

    /* Definición de atributos. */
    // Fotografía.
    private Bitmap foto;
    // Identificador.
    private long id;
    // Nombre del alumno.
    private String nombre;
    // Apellidos del alumno.
    private String apellidos;
    // Documento Nacional de Identificación asociado al alumno.
    private String DNI;
    // Fecha de nacimiento del alumno.
    private Calendar fecha_nacimiento;
    // Ruta de la imagen de la cara del alumno.
    // Será de la forma:
    // -> "DNI del alumno"
    // -> ".X" donde X es el formato asociado a las imágenes (JPG, PNG, BMP, ...)
    //private String ruta_imagen;
    // Número de asistencias del alumno.
    private int num_asistencias;
    // Número de faltas del alumno.
    private int num_faltas;
    // Booleano para comprobar si el alumno asiste o no a una determinada clase.
    private boolean asiste;
    // Lista de asistencias.
    private ArrayList<Asistencia> asistencias;

    /* --------------------- Constructores para la clase. --------------------- */
    public Alumno(){

        num_asistencias = 0;
        num_faltas = 0;
        this.asistencias = null;

    }
    /*public Alumno(String nombre_al, String apellidos_al, String DNI_al, String nacimiento_al, long id_al){
        super();
        // Para asignarle una fotografía con su DNI.
        String ruta = "/imagenes/" + DNI_al + ".jpg";
        Drawable auxiliar = Drawable.createFromPath(ruta);
        this.id = id_al;
        this.foto = auxiliar;
        this.nombre = nombre_al;
        this.apellidos = apellidos_al;
        this.DNI = DNI_al;
        Calendar aux = convertDateString(nacimiento_al);
        this.fecha_nacimiento = aux;
        //ruta_imagen = "/imagenes/" + DNI + ".jpg";
        this.num_asistencias = 0;
        this.num_faltas = 0;
        this.asiste = false;
        this.asistencias = null;

    }*/
    /*public Alumno(String nombre_al, String apellidos_al, String DNI_al, String nacimiento_al){
        super();
        // Para asignarle una fotografía con su DNI.
        String ruta = "/imagenes/" + DNI_al + ".jpg";
        Drawable auxiliar = Drawable.createFromPath(ruta);
        this.foto = auxiliar;
        this.nombre = nombre_al;
        this.apellidos = apellidos_al;
        this.DNI = DNI_al;
        Calendar aux = convertDateString(nacimiento_al);
        this.fecha_nacimiento = aux;
        //ruta_imagen = "/imagenes/" + DNI + ".jpg";
        this.num_asistencias = 0;
        this.num_faltas = 0;
        this.asiste = false;
        this.asistencias = null;


    }*/
    // Con foto
    public Alumno(String nombre_al, String apellidos_al, String DNI_al, String nacimiento_al, long id_al){
        super();
        // Para asignarle una fotografía con su DNI.
        //String ruta = "/imagenes/" + DNI_al + ".jpg";
        //Drawable auxiliar = Drawable.createFromPath(ruta);
        //id_foto = android.R.drawable.ic_menu_my_calendar
        this.id = id_al;
        //this.foto.setImageResource(id_foto);
        this.nombre = nombre_al;
        this.apellidos = apellidos_al;
        this.DNI = DNI_al;
        Calendar aux = convertDateString(nacimiento_al);
        this.fecha_nacimiento = aux;
        //ruta_imagen = "/imagenes/" + DNI + ".jpg";
        this.num_asistencias = 0;
        this.num_faltas = 0;
        this.asiste = false;
        this.asistencias = null;

    }

    public Alumno(String nombre_al, String apellidos_al, String DNI_al, String nacimiento_al){
        super();
        // Para asignarle una fotografía con su DNI.
        //String ruta = "/imagenes/" + DNI_al + ".jpg";
        //Drawable auxiliar = Drawable.createFromPath(ruta);
        //this.id_foto = id_foto_al;
        //this.foto.setImageResource(android.R.drawable.ic_menu_my_calendar);
        this.nombre = nombre_al;
        this.apellidos = apellidos_al;
        this.DNI = DNI_al;
        Calendar aux = convertDateString(nacimiento_al);
        this.fecha_nacimiento = aux;
        this.num_asistencias = 0;
        this.num_faltas = 0;
        this.asiste = false;
        this.asistencias = null;

    }
    /* Alumno sin foto. Para poder cargarlo desde fichero y después asignarle su fotografía. */
    /*public Alumno(String nombre_al, String apellidos_al, String DNI_al, String nacimiento_al){
        super();
        // Para asignarle una fotografía con su DNI.
        //String ruta = "/imagenes/" + DNI_al + ".jpg";
        //Drawable auxiliar = Drawable.createFromPath(ruta);
        this.id_foto = 0;
        this.nombre = nombre_al;
        this.apellidos = apellidos_al;
        this.DNI = DNI_al;
        Calendar aux = convertDateString(nacimiento_al);
        this.fecha_nacimiento = aux;
        this.num_asistencias = 0;
        this.num_faltas = 0;
        this.asiste = false;
        this.asistencias = null;

    }*/
    /* --------------------- Fin constructores --------------------- */
    /* Bloque de métodos get: Consulta de valores de los objetos de la clase alumno. */
    // Consultar nombre del alumno.
    public String getNombre(){
        return nombre;
    }
    // Consultar el ID del alumno.
    public long getId(){
        return id;
    }
    // Consultar la fotografía del alumno.
    public Bitmap getFoto(){
        return this.foto;
    }
    // Consultar apellidos del alumno.
    public String getApellidos(){
        return apellidos;
    }
    // Consultar DNI del alumno.
    public String getDNI(){
        return DNI;
    }
    // Consultar fecha de nacimiento del alumno.
    public Calendar getFechaNacimiento(){
        return fecha_nacimiento;
    }
    // Consultar número de asistencias a clase del alumno.
    public int getNumAsistencias(){
        return num_asistencias;
    }
    // Devolver la lista de asistencias de un alumno.
    public ArrayList<Asistencia> getAsistencias(){ return this.asistencias; }
    // Consultar número de faltas que ha tenido el alumno.
    public int getNumFaltas(){
        return num_faltas;
    }
    public boolean getAsiste(){ return asiste; }

    /* ------------------- Fin getters -------------------*/

    /* Bloque de métodos Set: Definir los datos de un alumno. */
    // Fija el valor del atributo nombre del objeto alumno.
    public void setNombre(String nombre_alum){
        this.nombre = nombre_alum;
    }
    // Fija el valor del atributo apellidos del objeto alumno.
    public void setApellidos(String apellidos_alum){
        this.apellidos = apellidos_alum;
    }
    // Fija el valor del atributo DNI del objeto alumno.
    public void setDNI(String DNI_alum){
        boolean correcto = true;
        if(DNI_alum.length() > 9){
            correcto = false;
            System.out.println("Error Tamaño");

        }
        else{
            for(int i = 0; i < 8; i++){
                if(DNI_alum.charAt(i) >= '0' && DNI_alum.charAt(i) <= '9'){

                }
                else{
                    correcto = false;
                    System.out.println("Error dígitos");
                }
            }
            if(correcto == true){
                if(DNI_alum.charAt(8) >= 'A' && DNI_alum.charAt(8) <= 'Z'){

                }
                else{
                    correcto = false;
                    System.out.println("Error letra");
                }
            }
        }
        if(correcto){
            this.DNI = DNI_alum;
        }
        else{
            System.out.println("El DNI introducido no es correcto.");
        }

    }
    // Fija el valor del atributo fecha_nacimiento del objeto alumno.
    public void setFechaNacimiento(String nacimiento_alum){
        Calendar aux = convertDateString(nacimiento_alum);
        this.fecha_nacimiento = aux;
    }
    public void  setFoto(Bitmap image){
        /*if(image != null){
            this.foto.setImageBitmap(image);
        }
        else{
            this.foto.setImageResource(android.R.drawable.ic_menu_my_calendar);
        }*/
        this.foto = image;
    }
    public void setAsiste(boolean valor) {
        this.asiste = valor;
    }
    /* ------------------- Fin setters -------------------*/

    /* Bloque de métodos de la clase: Funcionalidades de la clase. */
    public int asistenciasTotales(){

        int asist_totales = 0;
        asist_totales = num_asistencias + num_faltas;

        return asist_totales;
    }
    // Mostrar un alumno con sus datos asociados.
    public void mostrarAlumno(){
        System.out.println("----------------------------------------------------");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Apellidos: " + this.apellidos);
        System.out.println("DNI: " + this.DNI);
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Fecha de nacimiento: " + formateador.format(this.fecha_nacimiento));
        System.out.println("----------------------------------------------------");
    }
    /* Añadir un método que calcule el porcentaje de clases a las que ha asistido el alumno al final del curso. */
    public Calendar convertDateString(String fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        java.util.Date date;
        Calendar calendar = new GregorianCalendar();

        try{
            date = sdf.parse(fecha);
            calendar.setTime(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        return calendar;

    }
    public String obtenerCadenaDeFecha(Calendar fecha){
        return (fecha.get(Calendar.DAY_OF_MONTH) + "/" + fecha.get(Calendar.MONTH) + "/" + fecha.get(Calendar.YEAR));
    }

    public void aniadirAsistencia(Asistencia asiste){

        this.asistencias.add(asiste);

    }

}

