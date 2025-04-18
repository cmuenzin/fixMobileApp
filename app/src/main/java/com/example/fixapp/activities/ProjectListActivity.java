package com.example.fixapp.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fixapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProjectListActivity extends AppCompatActivity {
    private RecyclerView rvProjects;
    private FloatingActionButton fabNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ganz wichtig: das neue Layout setzen
        setContentView(R.layout.activity_project_list);

        // Views aus dem Layout holen
        rvProjects = findViewById(R.id.rvProjects);
        fabNew = findViewById(R.id.fabNewProject);

        // RecyclerView initial konfigurieren (erstmal leerer Adapter)
        rvProjects.setLayoutManager(new LinearLayoutManager(this));
        // TODO: später mit echtem Adapter füllen:
        // rvProjects.setAdapter(new ProjectAdapter(projectList));

        // Klick auf den FAB: Kamera‑Intent starten
        fabNew.setOnClickListener(v -> {
            // TODO: hier die Kamera öffnen und Projekt anlegen
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // hier evtl. Zustand speichern
    }

    @Override
    protected void onResume() {
        super.onResume();
        // hier evtl. Daten nachladen
    }
}
