package com.example.fixapp.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fixapp.R;
import com.example.fixapp.models.Project;

import java.text.DateFormat;
import java.util.List;

/**
 * Bindet eine Liste von Project‑Objekten an die RecyclerView.
 */
public class ProjectAdapter
        extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Project project);
    }

    private Context context;
    private List<Project> projects;
    private OnItemClickListener listener;

    public ProjectAdapter(Context context,
                          List<Project> projects,
                          OnItemClickListener listener) {
        this.context = context;
        this.projects = projects;
        this.listener = listener;
    }

    /** ViewHolder hält References auf die Views jedes Items. */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ivThumb  = itemView.findViewById(R.id.ivThumbnail);
            tvTitle  = itemView.findViewById(R.id.tvTitle);
            tvDate   = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(projects.get(pos));
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.project_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            ViewHolder holder, int position) {

        Project p = projects.get(position);
        holder.tvTitle.setText(p.getTitle());
        // Datum formatiert darstellen
        String dateStr = DateFormat.getDateTimeInstance()
                .format(p.getDate());
        holder.tvDate.setText(dateStr);
        // Thumbnail von Pfad laden
        holder.ivThumb.setImageBitmap(
                BitmapFactory.decodeFile(p.getThumbnailPath())
        );
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }
}
