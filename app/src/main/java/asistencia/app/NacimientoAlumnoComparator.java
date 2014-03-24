package asistencia.app;

/**
 * Created by Juan Francisco on 11/03/14.
 */

/* Imports */
import java.util.Comparator;

/**
 *
 * @author Juan Francisco
 */
public class NacimientoAlumnoComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Alumno alumno1 = (Alumno)o1;
        Alumno alumno2 = (Alumno)o2;
        return alumno1.getFechaNacimiento().compareTo(alumno2.getFechaNacimiento());
    }
}

