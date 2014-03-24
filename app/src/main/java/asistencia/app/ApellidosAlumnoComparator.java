package asistencia.app;

/**
 * Created by Juan Francisco on 11/03/14.
 */

import java.util.Comparator;

/**
 *
 * @author Juan Francisco
 */
public class ApellidosAlumnoComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Alumno alumno1 = (Alumno)o1;
        Alumno alumno2 = (Alumno)o2;
        return alumno1.getApellidos().compareTo(alumno2.getApellidos());

    }
}

