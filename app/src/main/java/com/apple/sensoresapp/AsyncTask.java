package com.apple.sensoresapp;

public class AsyncTask extends android.os.AsyncTask {
    MainActivity main = new MainActivity();

    @Override
    protected Boolean doInBackground(Object[] objects) {
        return true;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
