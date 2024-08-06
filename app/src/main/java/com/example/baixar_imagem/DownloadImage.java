package com.example.baixar_imagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class DownloadImage extends AsyncTask<String, Object, Void> {
    private final Button btnProcessar;
    private final ProgressBar pgbImagem;
    private final ImageView imgView;
    private final TextView txtError;
    private int current;
    private int previous;

    public DownloadImage(Button button, ProgressBar progressBar, ImageView imageView, TextView error) {
        this.btnProcessar = button;
        this.pgbImagem = progressBar;
        this.imgView = imageView;
        this.txtError = error;
    }

    @Override
    protected Void doInBackground(String... link) {
        Bitmap bitmap = null;
        String url = link[0];

        InputStream in = null;
        try {
            in = new URL(url).openStream();
        } catch (MalformedURLException e) {
            publishProgress("error", "MalformedURL");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bitmap = BitmapFactory.decodeStream(in);

        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        publishProgress("success", bitmap);

        return null;
    }

    @Override
    protected void onPreExecute() {
        btnProcessar.setEnabled(false);
        pgbImagem.setVisibility(View.VISIBLE);
        txtError.setText("");
    }

    @Override
    protected void onProgressUpdate(Object... data) {
        if (!(data[0] instanceof String)) {
            txtError.setText("Primeiro dado não é uma string.");
            return;
        }

        String result = (String) data[0];

        if (Objects.equals(result, "error")) {
            if (Objects.equals(data[1], "MalformedURL")) {
                txtError.setText("URL inválida.");
            }
        }
        else if (Objects.equals(result, "success")) {
            if (!(data[1] instanceof Bitmap)) {
                txtError.setText("A URL não é uma imagem.");
                return;
            }

            Bitmap bitmap = (Bitmap) data[1];

            imgView.setImageBitmap(bitmap);
        }
        else {
            txtError.setText("Erro desconhecido.");
        }
    }

    @Override
    protected void onPostExecute(Void nothing) {
        this.btnProcessar.setEnabled(true);
        pgbImagem.setVisibility(View.INVISIBLE);
    }
}
