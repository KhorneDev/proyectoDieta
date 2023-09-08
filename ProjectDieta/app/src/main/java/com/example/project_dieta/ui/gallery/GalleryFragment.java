package com.example.project_dieta.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_dieta.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {
    private Button btnIngresar;
    private EditText etNombre, etCalorias, etCarbos, etProteinas, etGrasas;
    private RequestQueue resRequestQueue;
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnIngresar = binding.btnIngresar;
        etNombre = binding.etNombre;
        etCalorias = binding.etCalorias;
        resRequestQueue = Volley.newRequestQueue(getContext());
        etCarbos = binding.etCarbos;
        etProteinas = binding.etProteinas;
        etGrasas = binding.etGrasas;

        // Asigna el evento onClick al botón
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarAlimento(v);
            }
        });

        return root;
    }

    // Agrega el método ingresarAlimento
    public void ingresarAlimento(View v) {
        Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();

        //Extraccion de valores
        String nombre = etNombre.getText().toString();
        int calorias = Integer.parseInt(etCalorias.getText().toString());
        float proteinas = Float.parseFloat(etProteinas.getText().toString());
        float grasas = Float.parseFloat(etGrasas.getText().toString());
        float carbos = Float.parseFloat(etCarbos.getText().toString());

        //URL para la inserción de datos
        String url = "http://172.16.32.50/proyectoDieta/insertarAlimento.php";

        //Creación de un JSON con los datos del formulario
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nombre", nombre);
            jsonObject.put("calorias",calorias);
            jsonObject.put("proteinas", proteinas);
            jsonObject.put("grasas", grasas);
            jsonObject.put("carbohridatos", carbos);
            Log.d("Los valores", String.valueOf(jsonObject));
        }catch (JSONException e){
            e.printStackTrace();
        }

        //Creación de la solicitud POST con VOLLEY
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Manejo exitoso de la solicitud
                        try {
                            String mensaje = response.getString("mensaje");
                            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error en la solicitud "+error, Toast.LENGTH_SHORT).show();
                        Log.d("Error", String.valueOf(error));
                    }
                }
        );
        resRequestQueue.add(request);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
