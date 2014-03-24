package asistencia.app;

import java.util.Calendar;

/**
 * Created by Juan Francisco on 15/03/14.
 */
public class Asistencia {

    private Calendar fecha;
    private boolean presente;

    /* --- CONSTRUCTORES --- */
    public Asistencia(){
        // Marca la fecha actual.
        Calendar aux = Calendar.getInstance();
        this.fecha = aux;
        this.presente = false;

    }
    public Asistencia(Calendar fecha, boolean asist){
        this.fecha = fecha;
        this.presente = asist;
    }
    /* --- FIN CONSTRUCTORES --- */

    /* --- FECHA --- */
    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }
    /* --- PRESENTE --- */
    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
    /* --- FIN GETTERS // SETTERS --- */

}