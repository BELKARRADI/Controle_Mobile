package com.belkarradi.control;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int selectedOptionId;

    private EditText dateEmbauche,nom,prenom,photo;
    private Button sendProf,redirectProfs;
    RequestQueue requestQueue;
    String insertUrl = "http://10.0.2.2:8082/api/Employe";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom=findViewById(R.id.Nom);
        prenom=findViewById(R.id.prenom);
        photo=findViewById(R.id.photo);
        dateEmbauche=findViewById(R.id.date);





        sendProf=findViewById(R.id.submitEmploye);
        sendProf.setOnClickListener(this);


        redirectProfs=findViewById(R.id.AffichageEmploye);
        redirectProfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AffichageEmploye.class);
                startActivity(intent);
            }
        });



        Spinner spinner = findViewById(R.id.spinner); // Référencez le Spinner depuis le layout

// Créez une liste d'options (comme mentionné dans la réponse précédente)
        List<Option> options = new ArrayList<>();
        options.add(new Option(1, "Service1"));
        options.add(new Option(2, "Service2"));
        options.add(new Option(3, "Service3"));
        options.add(new Option(4, "Service4"));
        options.add(new Option(5, "Service5"));

// Créez un ArrayAdapter pour lier les options au Spinner
        ArrayAdapter<Option> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);

// Spécifiez le modèle d'affichage du Spinner (par exemple, simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Associez l'adaptateur au Spinner
        spinner.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {


        Spinner spinner = findViewById(R.id.spinner);
        Option selectedOption = (Option) spinner.getSelectedItem();
        selectedOptionId = selectedOption.getId();


        JSONObject jsonBody = new JSONObject();
        JSONObject ServiceObj = new JSONObject();
        try {
            ServiceObj.put("id", selectedOptionId);


        }catch (JSONException e){
            e.printStackTrace();

        }


        try {
            jsonBody.put("nom", nom.getText().toString() );
            jsonBody.put("prenom", prenom.getText().toString() );
            jsonBody.put("photo", photo.getText().toString() );
            jsonBody.put("dateNaissance", dateEmbauche.getText().toString() );

            //selectedOptionId
            jsonBody.put("service", ServiceObj);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Employe", "JSON Data: " + jsonBody.toString()); // Log the JSON data

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                insertUrl, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Employe", "Response: " + response.toString());
                // Effacer les champs après un envoi réussi
                nom.setText("");
                prenom.setText("");
                photo.setText("");
                dateEmbauche.setText("");


                // Afficher une boîte de dialogue (popup) pour indiquer le succès
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Succès");
                builder.setMessage("Employe ajouté avec succès");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Vous pouvez ajouter un code supplémentaire ici si nécessaire
                    }
                });
                builder.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Employe", "Error: " + error.toString()); // Log any errors
            }
        });
        requestQueue.add(request);

    }
}