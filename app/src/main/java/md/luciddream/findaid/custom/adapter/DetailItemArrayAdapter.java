package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import md.luciddream.findaid.R;

import java.util.List;

public class DetailItemArrayAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> values;
    public DetailItemArrayAdapter(@NonNull Context context, @NonNull List<String> values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.detail_adapter_item, parent, false);

        TextView textView = (TextView) linearLayout.findViewById(R.id.detail_adapter_item_text_view);
        textView.setText((position + 1) + ". " + values.get(position));
        return linearLayout;
    }
}
