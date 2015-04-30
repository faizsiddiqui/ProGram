package app.program;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.Toast;

import app.fragments.Base.About;
import app.fragments.Base.Help;

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
                } else if(fragment.equals("Help")){
                    Help help = (Help) getSupportFragmentManager().findFragmentByTag("FRAGMENT_HELP");
                }
            } else {
                if(fragment.equals("About")) {
                    About about = new About();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.forumFrame, about, "FRAGMENT_ABOUT")
                            .commit();
                } else if (fragment.equals("Help")){
                    Help help = new Help();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.forumFrame, help, "FRAGMENT_HELP")
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
