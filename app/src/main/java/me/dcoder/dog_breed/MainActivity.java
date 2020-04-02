package me.dcoder.dog_breed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.dcoder.dog_breed.model.BreedList;
import me.dcoder.dog_breed.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list_view);
        ProgressDialog progressDialog = ProgressDialog.show(this, getString(R.string.app_name), getString(R.string.loading));

        ServiceGenerator.getService().getBreadList().enqueue(new Callback<BreedList>() {
            @Override
            public void onResponse(Call<BreedList> call, Response<BreedList> response) {
                progressDialog.hide();
               if(response.isSuccessful() && response.body() != null){

                   HashMap<String, ArrayList<String>> data = response.body().data;
                   ListItem[] listItems = new ListItem[data.size()];

                   int i = 0;
                   Iterator it = data.entrySet().iterator();
                   while (it.hasNext()) {
                       Map.Entry<String, ArrayList<String>> pair = (Map.Entry)it.next();

                       String title =  pair.getKey().toString();

                       String description = "";
                       ArrayList values = pair.getValue();
                       Iterator sit = values.iterator();
                       while (sit.hasNext()) {
                           if (description != "") {
                               description += ",";
                           }
                           description += sit.next().toString();
                       }

                       listItems[i] = new ListItem(title, description);
                       i++;
                   }

                   recyclerView.setAdapter(new RecyclerViewAdapter(listItems));
               } else {
                   onFailure(call, null);
               }
            }

            @Override
            public void onFailure(Call<BreedList> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final ListItem[] items;

        public RecyclerViewAdapter(ListItem[] items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.item = items[position];
            holder.txtTitle.setText(items[position].title);
            holder.txtDescription.setText(items[position].description);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;
            public final TextView txtTitle;
            public final TextView txtDescription;
            public ListItem item;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                txtTitle = view.findViewById(R.id.list_item_title);
                txtDescription = view.findViewById(R.id.list_item_description);
            }
        }
    }

    public class ListItem {
        String title;
        String description;

        public ListItem(String title, String description) {
            this.title = title;
            this.description = description;
        }
    }
}

