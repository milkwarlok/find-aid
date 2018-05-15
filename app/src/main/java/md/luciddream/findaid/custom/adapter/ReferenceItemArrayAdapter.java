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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import md.luciddream.findaid.R;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;

import java.util.List;

public class ReferenceItemArrayAdapter extends ArrayAdapter<String> {
    private Context context;

    private List<String> traumas;
    private List<List<String>> symptomsList;

    public ReferenceItemArrayAdapter(@NonNull  Context context, @NonNull List<String> traumas, @NonNull List<List<String>> symptomsList) {
        super(context, -1, traumas);
        this.context = context;
        this.traumas = traumas;
        this.symptomsList = symptomsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.reference_activity_list_item, parent,false);
        TextView heading = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_1);
        //TextView subHeading = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_2);
        TextView symptomListView = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_3);
        heading.setText(traumas.get(position));
        List<String> symptoms = symptomsList.get(position);
        StringBuffer symptomListContents = new StringBuffer("");
        int maxLength = symptoms.size() < 2? symptoms.size():2;
        for(int i = 0 ; i < maxLength; i++){
            symptomListContents.append('\t');
            symptomListContents.append(i + 1);
            symptomListContents.append(". ");
            symptomListContents.append(symptoms.get(i));
            symptomListContents.append('\n');
        }
        symptomListView.setText(symptomListContents.toString());
        return linearLayout;
    }
}
