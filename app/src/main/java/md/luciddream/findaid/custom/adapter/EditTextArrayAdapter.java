package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import md.luciddream.findaid.R;

import java.util.List;

public class EditTextArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> values;

    public EditTextArrayAdapter(@NonNull  Context context, @NonNull List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        EditText editText = (EditText) inflater.inflate(R.layout.reference_list_item, parent, false);
        editText.setSingleLine(false);
        editText.setHint((position + 1) + ". "+ values.get(position));

        return editText;
    }
}
