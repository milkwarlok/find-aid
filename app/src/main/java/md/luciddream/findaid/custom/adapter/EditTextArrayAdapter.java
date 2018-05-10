package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import md.luciddream.findaid.R;

import java.util.ArrayList;
import java.util.List;

public class EditTextArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> hints;
    private List<String> values;

    public EditTextArrayAdapter(@NonNull  Context context, @NonNull List<String> hints, @NonNull List<String> values) {
        super(context, -1, hints);
        this.context = context;
        this.hints = hints;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        EditText editText = (EditText) inflater.inflate(R.layout.reference_list_item, parent, false);
        editText.setSingleLine(false);
        if(values != null && values.size() != 0 && !values.get(position).equals(""))
            editText.setText(values.get(position));
        else
            editText.setHint((position + 1) + ". "+ hints.get(position));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            values.set(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(values.size() != position && values.get(position).length() == 0)
                        editText.setHint((position + 1) + ". "+ hints.get(position));
                }
            }
        });

        return editText;
    }
}
