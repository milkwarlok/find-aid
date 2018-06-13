package md.luciddream.findaid.custom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import md.luciddream.findaid.R;
import md.luciddream.findaid.activities.DetailReferenceItemActivity;
import md.luciddream.findaid.data.model.Symptom;
import md.luciddream.findaid.data.model.Trauma;
import md.luciddream.findaid.data.specific.SpecificTrauma;

import java.util.*;

public class ReferenceItemArrayAdapter extends ArrayAdapter<SpecificTrauma> implements Filterable {
    private Context context;
    List<SpecificTrauma> originalTraumas;
    List<SpecificTrauma> showedTraumas;
    ItemFilter itemFilter = new ItemFilter();

    public ReferenceItemArrayAdapter(@NonNull  Context context, @NonNull List<SpecificTrauma> originalTraumas) {
        super(context, -1, originalTraumas);
        this.context = context;
        this.originalTraumas = new ArrayList<>(originalTraumas);
        this.showedTraumas = new ArrayList<>(originalTraumas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        if(position < showedTraumas.size()) {
            linearLayout = (LinearLayout) inflater.inflate(R.layout.reference_activity_list_item, parent,false);
        TextView heading = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_1);
        TextView subheading = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_2);
        TextView symptomListView = linearLayout.findViewById(R.id.reference_activity_list_item_text_view_3);
            heading.setText(showedTraumas.get(position).getTrauma().getName());
            subheading.setText(R.string.symptom_str);
            StringBuffer contents = new StringBuffer("");
            Symptom[] symptoms = showedTraumas.get(position).getSymptoms();
            int count = symptoms.length < 2? symptoms.length : 2;
            for (int j = 0; j < count; j++) {
                contents.append(j + 1);
                contents.append(". ");
                contents.append(symptoms[j].getName());
                contents.append("\n");
            }
            symptomListView.setText(contents);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailReferenceItemActivity.class);
                    intent.putExtra("t_id", showedTraumas.get(position).getTrauma().getT_id());
                    intent.putExtra("t_name", showedTraumas.get(position).getTrauma().getName());
                    v.getContext().startActivity(intent);
                }
            });
        }
        return linearLayout;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return itemFilter;
    }
    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint!= null && constraint.length() != 0) {
                String filterString = constraint.toString().toLowerCase();


                final List<SpecificTrauma> showedTraumas = ReferenceItemArrayAdapter.this.originalTraumas;
                int count = showedTraumas.size();

                List<SpecificTrauma> filteredSpecificTraumas = new ArrayList<>();
                String filterableString;

                for (int i = 0; i < count; i++) {
                    SpecificTrauma item = showedTraumas.get(i);
                    filterableString = item.getTrauma().getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        filteredSpecificTraumas.add(item);
                    }
                }
                results.values = filteredSpecificTraumas;
                results.count = filteredSpecificTraumas.size();
            }
            else {
                results.values = originalTraumas;
                results.count = originalTraumas.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            showedTraumas = (ArrayList<SpecificTrauma>)results.values;
            notifyDataSetChanged();
        }
    }
}
