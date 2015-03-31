package app.widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Not for public use
 * Created by FAIZ on 29-03-2015.
 */
public class MaterialListPreference extends ListPreference {
    private MaterialDialog.Builder mBuilder;
    private Context context;

    public MaterialListPreference(Context context) {
        super(context);
        this.context = context;
    }

    public MaterialListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void showDialog(Bundle state) {
        mBuilder = new MaterialDialog.Builder(context);
        mBuilder.title(getTitle());
        mBuilder.items(getEntries());
        mBuilder.icon(getDialogIcon());
        mBuilder.positiveText(getPositiveButtonText());
        mBuilder.itemsCallbackSingleChoice(1, new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                if (which >= 0 && getEntryValues() != null) {
                    String value = getEntryValues()[which].toString();
                    if (callChangeListener(value))
                        setValue(value);
                }
            }
        });

        final View contentView = onCreateDialogView();
        if (contentView != null) {
            onBindDialogView(contentView);
            mBuilder.customView(contentView, true);
        }
        else
            mBuilder.content(getDialogMessage());

        mBuilder.show();
    }

}
