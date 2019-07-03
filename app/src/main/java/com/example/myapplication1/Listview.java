package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Listview extends AppCompatActivity implements PizzaAdapter.OnItemClickListner {
        private static final String URL_DATA = "http://172.19.49.194:8080/demo/all";

        RecyclerView recyclerView;
        PizzaAdapter adapter;

        List<pizza> productslist;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listview);

            productslist = new ArrayList<>();

            recyclerView = findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            loadRecyclerviewData();

        }


        private void loadRecyclerviewData(){
             StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA, new Response.Listener<String>() {

                @Override

                public void onResponse(String response) {

                    try {

                        JSONArray products  = new JSONArray(response);

                        for (int i =0; i<products.length(); i++){

                            JSONObject productobject  = products.getJSONObject(i);

                            int id = productobject.getInt("pizzaId");
                            String name = productobject.getString("name");
                            String description = productobject.getString("description");
                            Double price = productobject.getDouble("price");
                            String imageurl = productobject.getString("imageUrl");

                            pizza product = new pizza(id, name, description, price, imageurl);
                            productslist.add(product);

                        }

                        adapter = new PizzaAdapter(Listview.this, productslist);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemCliclListener(Listview.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Listview.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
             },  new Response.ErrorListener()

                 {

                     @Override
                     public void onErrorResponse (VolleyError error){
                     Toast.makeText(Listview.this, error.getMessage(), Toast.LENGTH_LONG).show();
                     error.printStackTrace();
                 }
                 });

            Volley.newRequestQueue(this).add(stringRequest);
             }

        //send data to detail activity

        @Override
        public void onItemClick(int position) {

            Intent detailintent = new Intent(this, DetailsActivity.class);
            pizza clickItem = productslist.get(position);

            detailintent.putExtra("NAME", clickItem.getName());
            detailintent.putExtra("DETAILS", clickItem.getDescription());
            detailintent.putExtra("PRICE", clickItem.getPrice());
            detailintent.putExtra("IMG", clickItem.getImgurl());

            startActivity(detailintent);

        }


        }
