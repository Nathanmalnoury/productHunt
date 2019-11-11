package fr.ec.producthunt.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import fr.ec.producthunt.R;

public class DetailActivity extends AppCompatActivity {

  public static final String POST_URL_KEY = "post_url_key";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.detail_activity);

    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction()
        .add(R.id.detail_container, DetailPostFragment.getNewInstance(obtainPostUrlFromIntent()))
        .commit();
  }

  private String obtainPostUrlFromIntent() {

    Intent intent = getIntent();
    if (intent.getExtras().containsKey(POST_URL_KEY)) {
      return intent.getExtras().getString(POST_URL_KEY);
    } else {
      throw new IllegalStateException("Il faut passer l'url du post");
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu_webview, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.openInBrowser:
        onOpenInBrowser();
    }
    return super.onOptionsItemSelected(item);
  }

  private void onOpenInBrowser() {
    String url = obtainPostUrlFromIntent();
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }
}
