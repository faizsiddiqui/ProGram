package app.program;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import app.fragments.Base.About;

/**
 * Not for public use
 * Created by FAIZ on 29-04-2015.
 */
public class OtherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fragment = getIntent().getStringExtra("fragment");
        if (findViewById(R.id.forumFrame) != null) {
            if (savedInstanceState != null) {
                if(fragment.equals("About")) {
                    About about = (About) getSupportFragmentManager().findFragmentByTag("FRAGMENT_ABOUT");
                }
            } else {
                if(fragment.equals("About")) {
                    About about = new About();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.forumFrame, about, "FRAGMENT_ABOUT")
                            .commit();
                }
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_forum;
    }
}
