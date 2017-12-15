package com.hlc.diurno.servicioconhilossecundarios;

import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    // llamado al crearse el servicio
    @Override
    public void onCreate() {
        // Inicializaciones para el servicio
    }
    // llamado en servicios Enlazados -- bindService()
    @Override
    public IBinder onBind(Intent arg0) {
        // return null;
        return null;
    }
    // llamado en servicios Ejecutados -- startService()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //simula la descarga de 4 archivos
        try {
            new DoBackgroundTask().execute(new URL(
                    "http://www.amazon.com/somefiles.pdf"), new URL(
                    "http://www.wrox.com/somefiles.pdf"), new URL(
                    "http://www.google.com/somefiles.pdf"), new URL(
                    "http://www.learn2develop.net/somefiles.pdf"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // re-lanzar servicio iniciado cuando haya memoria suficiente
        return START_STICKY;
    }
    // Método que simula la descarga de un archivo -- tarea de larga duración
    private int DownloadFile(URL url) {
        try {
            // ---simula el tiempo necesario para descargar el archivo---
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // devuelve el valor simulado de la descarga--
        return 100;
    }
    // Se ejeucta en un hilo de ejecución en segundo plano y es
    // donde se sitúa el código de larga duración.
    private class DoBackgroundTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalBytesDownloaded = 0;
            for (int i = 0; i < count; i++) {
                totalBytesDownloaded += DownloadFile(urls[i]);
                // ---calcula el porcentaje descargado
                // reportando el progreso---
                publishProgress((int) (((i + 1) / (float) count) * 100));
            }
            return totalBytesDownloaded;
        }

        // se invoca en el hilo de ejecución de la interfaz de usuario y se
        // llama cuando llama al método publishProgress()
        protected void onProgressUpdate(Integer... progress) {
            Log.d("Descargando Archivos", String.valueOf(progress[0])
                    + "% descargado");

            Toast.makeText(getBaseContext(),
                    String.valueOf(progress[0]) + "% descargado",
                    Toast.LENGTH_LONG).show();
        }
        // se invoca en el hilo de ejecución de la interfaz de usUario y se llama
        //cuando el método doInBackground() ha terminado de ejecutarse
        protected void onPostExecute(Long result) {
            Toast.makeText(getBaseContext(),
                    "Descargados " + result + " bytes", Toast.LENGTH_LONG)
                    .show();
            stopSelf();
        }
    }
    // llamado al finalizar el servicio
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio Destruido", Toast.LENGTH_LONG).show();
    }
}
