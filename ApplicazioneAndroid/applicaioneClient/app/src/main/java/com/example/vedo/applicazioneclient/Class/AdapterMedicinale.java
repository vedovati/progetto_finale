package com.example.vedo.applicazioneclient.Class;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vedo.applicazioneclient.MedicinaleActivity;
import com.example.vedo.applicazioneclient.MenuActivity;
import com.example.vedo.applicazioneclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdapterMedicinale extends RecyclerView.Adapter<AdapterMedicinale.ViewHolder> {

    private Context context;
    private JSONArray medicinali;

    public AdapterMedicinale (JSONArray medicinali, Context context) {
        this.medicinali = medicinali;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_medicina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) { // Quando si usa view.setAdapter(adapterView)
        // settaggio parametri viewHolder

        try {

            JSONObject medicinale =  medicinali.getJSONObject(position);

            holder.nome.setText(medicinale.get("nome").toString());


        } catch (JSONException e) {
            Log.i("(ノಠ益ಠ)ノ彡┻━┻ JSONArray", "position error");
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MedicinaleActivity.class);

                //Create the bundle
                Bundle bundle = new Bundle();

                //Add your data to bundle
                bundle.putString("nomeMedicina", holder.nome.getText().toString());

                //Add the bundle to the intent
                intent.putExtras(bundle);

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return medicinali.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // classe che collega card_view

        TextView nome;
        RelativeLayout card;

        public ViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            card = itemView.findViewById(R.id.card);
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
