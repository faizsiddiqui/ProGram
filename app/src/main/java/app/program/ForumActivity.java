package app.program;

import android.os.Bundle;
import android.view.MenuItem;

import app.fragments.Forum.Forum;

/**
 * Not for public use
 * Created by FAIZ on 06-04-2015.
 */
public class ForumActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(findViewById(R.id.forumFrame) != null){
            if (savedInstanceState != null) {
                Forum forum = (Forum) getSupportFragmentManager().findFragmentByTag("FRAGMENT_FORUM");
            } else {
                Forum forum = new Forum();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.forumFrame, forum, "FRAGMENT_FORUM")
                        .commit();
            }
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_forum;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStack();
                    return true;
                } else finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
