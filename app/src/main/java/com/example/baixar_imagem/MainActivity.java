package com.example.baixar_imagem;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baixar_imagem.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String url = binding.editImageLink.getText().toString();
        DownloadImage task = new DownloadImage(binding.btnStartTask,
                binding.progressBar,
                binding.imgView,
                binding.txtViewError);

        task.execute(url);
    }
}