package asistencia.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Juan Francisco on 24/03/14.
 */

public class ListaAsignaturas extends Activity {

    private ListView listview;
    private static final int MNU_OPC1 = 1;
    private int opcionSeleccionada = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_asignaturas);

        listview = (ListView) findViewById(R.id.list_asignaturas);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        /*listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

//                view.animate().setDuration(20).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run(){
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });

            //}
        });*/
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
                .setIcon(android.R.drawable.ic_menu_preferences);

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
}

