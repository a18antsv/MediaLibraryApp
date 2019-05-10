package com.github.a18antsv.medialibraryapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.a18antsv.medialibraryapp.R;
import com.github.a18antsv.medialibraryapp.database.DbHelper;
import com.github.a18antsv.medialibraryapp.fragment.FragmentAddList;
import com.github.a18antsv.medialibraryapp.fragment.FragmentGetDataByExampleList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static android.R.attr.data;
import static android.R.attr.inset;
import static com.github.a18antsv.medialibraryapp.database.DataContract.Entry.*;

public class MainActivity extends AppCompatActivity implements FragmentAddList.onDataPassListener, FragmentGetDataByExampleList.onListExamplePassListener {
    private List<String> lists;
    private DbHelper dbHelper;
    private ListView listView;
    private ArrayAdapter adapter;
    private FloatingActionButton addListFab;
    private FragmentAddList fragmentAddList;
    private FragmentGetDataByExampleList fragmentGetDataByExampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview_lists);
        lists = new ArrayList<>();
        addListFab = findViewById(R.id.fab_add_list);

        dbHelper = new DbHelper(this);
        dbHelper.getWritableDatabase();

        Cursor c = dbHelper.getData("SELECT * FROM " + LIST_TABLE_NAME, null);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            lists.add(c.getString(c.getColumnIndex(LIST_COL_NAME)));
        }
        c.close();

        adapter = new ArrayAdapter(this, R.layout.listview_lists_item, R.id.list_name, lists);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ListContentActivity.class);
                intent.putExtra("LISTNAME",lists.get(i));
                startActivity(intent);
            }
        });

        addListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(findViewById(R.id.fragment_addlist_container) != null) {
                    fragmentAddList = new FragmentAddList();
                    Bundle args = new Bundle();
                    args.putString("ACTION", "add");
                    fragmentAddList.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_addlist_container, fragmentAddList).commit();
                }
            }
        });
    }

    public int insertProductGetKey(String title, int price, String release, String genre, String comment, String imgUrl) {
        dbHelper.insertIntoProduct(title, price, release, genre, comment, imgUrl);
        Cursor c = dbHelper.getData(
                "SELECT " + PRODUCT_COL_KEY +
                        " FROM " + PRODUCT_TABLE_NAME +
                        " ORDER BY " + PRODUCT_COL_KEY + " DESC" +
                        " LIMIT 1",
                null
        );
        c.moveToFirst();
        int productkey = c.getInt(c.getColumnIndex(PRODUCT_COL_KEY));
        c.close();
        return productkey;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.option_add_to_database:
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
                break;
            case R.id.option_delete_from_database:
                Intent intent2 = new Intent(MainActivity.this, DeleteProductActivity.class);
                startActivity(intent2);
                break;
            case R.id.option_get_example_data:
                fragmentGetDataByExampleList = new FragmentGetDataByExampleList();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_addlist_container, fragmentGetDataByExampleList).commit();
                break;
            case R.id.option_about:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String listName = (String) adapter.getItem(info.position);
        menu.setHeaderTitle(listName);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final String listName = adapter.getItem(info.position).toString();

        switch(item.getItemId()) {
            case R.id.option_edit:
                if(findViewById(R.id.fragment_addlist_container) != null) {
                    fragmentAddList = new FragmentAddList();
                    Bundle args = new Bundle();
                    args.putString("ACTION", "edit");
                    args.putString("LISTNAME", listName);
                    args.putInt("ITEMPOS", (int)(long)info.id);
                    fragmentAddList.setArguments(args);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_addlist_container, fragmentAddList).commit();
                }
                return true;
            case R.id.option_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure?");
                builder.setMessage("This list and all its content will be deleted.");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deletedRows = dbHelper.deleteList(listName);
                        lists.remove((int)(long)info.id);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Successfully deleted " + deletedRows + " row(s)", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onDataPass(String action, String data, String listName, int itemPos) {
        if(!dbHelper.duplicateData(LIST_TABLE_NAME, LIST_COL_NAME, data)) {
            if(action == "add") {
                if(dbHelper.insertList(data)) {
                    lists.add(data);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "List created!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No rows affected", Toast.LENGTH_SHORT).show();
                }
            } else if(action == "edit") {
                int updatedRows = dbHelper.updateList(data, listName);
                if(updatedRows > 0) {
                    lists.set(itemPos, data);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Successfully updated " + updatedRows + " row(s)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No rows affected...", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Multiple lists cannot have the same name...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onListExamplePass(String listName) {
        new FetchData().execute(listName);
    }

    private class FetchData extends AsyncTask<String,Void,String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            // These two variables need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a Java string.
            String jsonStr = null;
            String newListName = params[0];

            try {
                // Construct the URL for the Internet service
                URL url = new URL("http://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=a18antsv");

                // Create the request to the PHP-service, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                return new String[] {jsonStr, newListName};
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in
                // attempting to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("Network error", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            try {
                String jsonStr = result[0];
                String listName = result[1];
                String[] exampleLists = getResources().getStringArray(R.array.example_lists);

                JSONArray products = new JSONArray(jsonStr);

                if(listName.equals(exampleLists[0])) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for(int i = 1; i < exampleLists.length; i++) {
                        if(!dbHelper.duplicateData(LIST_TABLE_NAME, LIST_COL_NAME, exampleLists[i])) {
                            dbHelper.insertList(exampleLists[i]);
                            lists.add(exampleLists[i]);
                            adapter.notifyDataSetChanged();
                            stringBuilder.append(exampleLists[i] + " created!\n");
                        } else {
                            stringBuilder.append(exampleLists[i] + " already exists...\n");
                        }
                    }
                    Toast.makeText(getApplicationContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();

                    for(int i = 0; i < products.length(); i++) {
                        JSONObject product = products.getJSONObject(i);
                        JSONObject auxData = new JSONObject(product.getString("auxdata"));

                        String category = product.getString("category");

                        if(category.equals("book")) {
                            int productkey = insertProductGetKey(
                                    auxData.getString("title"),
                                    auxData.getInt("price"),
                                    auxData.getString("release"),
                                    auxData.getString("genre"),
                                    auxData.getString("comment"),
                                    auxData.getString("img")
                            );
                            dbHelper.insertIntoBook(
                                    productkey,
                                    auxData.getString("author"),
                                    auxData.getInt("pages"),
                                    auxData.getString("type"),
                                    auxData.getString("publisher"),
                                    auxData.getString("isbn")

                            );
                            dbHelper.insertIntoList(productkey, exampleLists[1]);
                        } else if(category.equals("movie")) {
                            int productkey = insertProductGetKey(
                                    auxData.getString("title"),
                                    auxData.getInt("price"),
                                    auxData.getString("release"),
                                    auxData.getString("genre"),
                                    auxData.getString("comment"),
                                    auxData.getString("img")
                            );
                            dbHelper.insertIntoMovie(
                                    productkey,
                                    auxData.getInt("length"),
                                    auxData.getInt("age"),
                                    auxData.getString("company"),
                                    auxData.getInt("rating")
                            );
                            dbHelper.insertIntoList(productkey, exampleLists[2]);
                        } else if(category.equals("song")) {
                            int productkey = insertProductGetKey(
                                    auxData.getString("title"),
                                    auxData.getInt("price"),
                                    auxData.getString("release"),
                                    auxData.getString("genre"),
                                    auxData.getString("comment"),
                                    auxData.getString("img")
                            );
                            dbHelper.insertIntoSong(
                                    productkey,
                                    auxData.getInt("length"),
                                    auxData.getString("label"),
                                    auxData.getString("artist")
                            );
                            dbHelper.insertIntoList(productkey, exampleLists[3]);
                        } else if(category.equals("game")) {
                            int productkey = insertProductGetKey(
                                    auxData.getString("title"),
                                    auxData.getInt("price"),
                                    auxData.getString("release"),
                                    auxData.getString("genre"),
                                    auxData.getString("comment"),
                                    auxData.getString("img")
                            );
                            dbHelper.insertIntoGame(
                                    productkey,
                                    auxData.getString("platform"),
                                    auxData.getInt("age"),
                                    auxData.getString("developer"),
                                    auxData.getString("publisher")
                            );
                            dbHelper.insertIntoList(productkey, exampleLists[4]);
                        }
                    }
                } else if(listName.equals(exampleLists[1])) {

                } else if(listName.equals(exampleLists[2])) {

                } else if(listName.equals(exampleLists[3])) {

                } else if(listName.equals(exampleLists[4])) {

                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
}