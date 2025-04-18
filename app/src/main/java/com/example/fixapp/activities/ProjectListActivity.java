package com.example.fixapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fixapp.R;
import com.example.fixapp.data.ProjectsDbHelper;
import com.example.fixapp.models.Project;
import com.example.fixapp.utils.ProjectAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

public class ProjectListActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE     = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private RecyclerView rvProjects;
    private FloatingActionButton fabNew;
    private ProjectsDbHelper dbHelper;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        rvProjects = findViewById(R.id.rvProjects);
        fabNew     = findViewById(R.id.fabNewProject);

        dbHelper   = new ProjectsDbHelper(this);
        projects   = dbHelper.getAllProjects();

        // Adapter mit Klick‑Listener zum Öffnen von Project
        ProjectAdapter adapter = new ProjectAdapter(
                this, projects,
                project -> {
                    Intent i = new Intent(this, com.example.fixapp.models.Project.class);
                    i.putExtra("projectId", project.getId());
                    startActivity(i);
                }
        );
        rvProjects.setLayoutManager(
                new LinearLayoutManager(this)
        );
        rvProjects.setAdapter(adapter);

        fabNew.setOnClickListener(v -> showNewProjectDialog());
    }

    /** Zeigt Dialog für Projekt-Erstellung mit Foto oder ohne Foto */
    private void showNewProjectDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Neues Projekt")
                .setMessage("Möchtest du ein Foto aufnehmen oder ohne Foto fortfahren?")
                .setPositiveButton("Foto aufnehmen", (dialog, which) -> {
                    // Kamera starten
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                this,
                                new String[]{ Manifest.permission.CAMERA },
                                REQUEST_CAMERA_PERMISSION
                        );
                    } else {
                        dispatchTakePictureIntent();
                    }
                })
                .setNegativeButton("Ohne Foto fortfahren", (dialog, which) -> skipCreateProject())
                .show();
    }

    /** Kamera‑Intent starten */
    private void dispatchTakePictureIntent() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePic.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(
                    this,
                    "Keine Kamera-App gefunden – bitte im AVD Manager Kamera aktivieren oder echtes Gerät nutzen.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    /** Erstellt Projekt ohne Foto und öffnet Project */
    private void skipCreateProject() {
        long now = System.currentTimeMillis();
        String title = "Projekt " + DateFormat.getDateTimeInstance().format(now);
        long id = dbHelper.addProject(title, now, ""); // kein Thumbnail

        Project newProj = new Project(id, title, now, "");
        projects.add(0, newProj);
        rvProjects.getAdapter().notifyItemInserted(0);
        rvProjects.scrollToPosition(0);

        Intent i = new Intent(this, com.example.fixapp.models.Project.class);
        i.putExtra("projectId", id);
        startActivity(i);
    }

    /** Permission‑Callback */
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            dispatchTakePictureIntent();
        } else {
            Toast.makeText(
                    this,
                    "Kamera‑Zugriff benötigt",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    /** Ergebnis der Kamera */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE
                && resultCode == RESULT_OK
                && data != null) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            String filename = "thumb_" + System.currentTimeMillis() + ".jpg";
            File file = new File(getFilesDir(), filename);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                thumbnail.compress(
                        Bitmap.CompressFormat.JPEG, 80, fos
                );

                long now = System.currentTimeMillis();
                String title = "Projekt " + DateFormat.getDateTimeInstance().format(now);
                long id = dbHelper.addProject(title, now, file.getAbsolutePath());

                Project newProj = new Project(id, title, now, file.getAbsolutePath());
                projects.add(0, newProj);
                rvProjects.getAdapter().notifyItemInserted(0);
                rvProjects.scrollToPosition(0);

                // Chat öffnen nach Foto
                Intent i = new Intent(this, com.example.fixapp.models.Project.class);
                i.putExtra("projectId", id);
                startActivity(i);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(
                        this,
                        "Fehler beim Speichern des Bildes",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        projects.clear();
        projects.addAll(dbHelper.getAllProjects());
        rvProjects.getAdapter().notifyDataSetChanged();
    }
}
