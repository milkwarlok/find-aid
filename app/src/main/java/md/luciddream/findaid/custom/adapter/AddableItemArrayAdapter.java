package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import md.luciddream.findaid.R;

import java.util.List;

public class AddableItemArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> hints;
    private List<String> values;
    private String hintTemplate;

    public AddableItemArrayAdapter(@NonNull  Context context, @NonNull List<String> hints, @NonNull List<String> values, @NonNull String hintTemplate) {
        super(context, -1, hints);
        this.context = context;
        this.hints = hints;
        this.values = values;
        this.hintTemplate = hintTemplate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.add_item_activity_list_item, parent,false);
        TextInputEditText editText = (TextInputEditText) linearLayout.findViewById(R.id.item_template_step_text_input_edit_text);
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
                    hints.add(hintTemplate);
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