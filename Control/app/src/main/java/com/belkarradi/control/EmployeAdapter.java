package com.belkarradi.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmployeAdapter extends BaseAdapter {
    private Context context;
    private JSONArray data;

    public EmployeAdapter(Context context, JSONArray data) {
        this.context = context;
        this.data = data;
    }

    public void setData(JSONArray data) {
        this.data = data;
        notifyDataSetChanged(); // Notifiez l'adaptateur que les données ont changé
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return data.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.employelayout, parent, false);
        }

        TextView textViewId = convertView.findViewById(R.id.textViewId);
        TextView textViewNom = convertView.findViewById(R.id.textViewNom);
        TextView textViewPrenom = convertView.findViewById(R.id.textViewPrenom);
        TextView textViewdateEmbauche = convertView.findViewById(R.id.textViewdateEmbauche);


        try {
            JSONObject item = data.getJSONObject(position);
            textViewId.setText("ID: " + item.getString("id"));
            textViewNom.setText("Nom: " + item.getString("nom"));
            textViewPrenom.setText("Prenom: " + item.getString("prenom"));
            textViewdateEmbauche.setText("Date de naissance: " + item.getString("dateNaissance"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
