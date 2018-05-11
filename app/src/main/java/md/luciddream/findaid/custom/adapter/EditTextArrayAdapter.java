package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
//        EditText editText = (EditText) inflater.inflate(R.layout.reference_list_item, parent, false);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.reference_list_item, parent,false);
        EditText editText = (EditText) linearLayout.findViewById(R.id.item_template_step_edit_text);
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
                if(s.toString().length() != 0)
                    values.set(position, s.toString());
                else {
                    editText.setHint((position + 1) + ". " + hints.get(position));
                    values.set(position, s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button button = (Button) linearLayout.findViewById(R.id.item_template_step_button);
        if(position == 0){
            button.setText("+");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hints.add("Введите шаг....");
                    values.add("");
                    notifyDataSetChanged();
                }
            });
        }
        else{
            button.setText("-");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(hints != null && !hints.isEmpty() && values != null && !values.isEmpty()) {
                        hints.remove(position);
                        values.remove(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }
        return linearLayout;
    }
}
