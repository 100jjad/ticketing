package com.example.ssoheyli.ticketing_newdesign.Financial;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ssoheyli.ticketing_newdesign.Model.Pre_Invoice_List_Model;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_invoce_detail_list_Model;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

public class FacturListAdapter extends RecyclerView.Adapter<FacturListAdapter.viewhollder> {

    TextView unitPrice;

    List<Pre_Invoice_List_Model> entries = new ArrayList<>();

    ArrayList<Pre_Invoice_List_Model> filtred;

    private Context context;

    public FacturListAdapter(Context context) {
        this.context = context;
        filtred = new ArrayList<>(entries);
    }

    @NonNull
    @Override
    public viewhollder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_item_factur, viewGroup, false);
        return new FacturListAdapter.viewhollder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewhollder viewhollder, int i) {
        viewhollder.bindadress(entries.get(i),i);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class viewhollder extends RecyclerView.ViewHolder {

        TextView txv1, txv2, txv3, txv4, txv5;

        public viewhollder(@NonNull View itemView) {
            super(itemView);
            // model
            txv1 = itemView.findViewById(R.id.status);
            txv2 = itemView.findViewById(R.id.amount);
            txv3 = itemView.findViewById(R.id.deposit);
            txv4 = itemView.findViewById(R.id.date);
            txv5 = itemView.findViewById(R.id.tv1);

            // details
            unitPrice = itemView.findViewById(R.id.unit_price);
        }

        public void bindadress(Pre_Invoice_List_Model model,int i) {

            Pre_invoce_detail_list_Model details = model.getDetails().get(i);

            // model
            String fa_nu = "" + model.getFactor_number();
            String to_am = model.getTotal_amount();
            txv1.setText(fa_nu);
            txv2.setText(to_am);
            txv3.setText(model.getStatus());
            txv4.setText(model.getDate());

            Typeface atf = Typeface.createFromAsset(context.getAssets(), "irsans.ttf");
            txv1.setTypeface(atf);
            txv2.setTypeface(atf);
            txv3.setTypeface(atf);
            txv4.setTypeface(atf);
            txv5.setTypeface(atf);

            // details
            unitPrice.setText(details.getPrice());
        }
    }


}
