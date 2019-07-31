package com.example.ssoheyli.ticketing_newdesign.Financial;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssoheyli.ticketing_newdesign.Helpfull.API;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Config;
import com.example.ssoheyli.ticketing_newdesign.Helpfull.Constans;
import com.example.ssoheyli.ticketing_newdesign.MainActivity;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_Payment_Inventory;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Get_invoice_detail_list;
import com.example.ssoheyli.ticketing_newdesign.Model.Model_Post_Payment_Inventory;
import com.example.ssoheyli.ticketing_newdesign.Model.Pre_Invoice_List_Model1;
import com.example.ssoheyli.ticketing_newdesign.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test_Adapter_PreInvoice_List extends RecyclerView.Adapter<Test_Adapter_PreInvoice_List.viewhollder> {

    TextView unitPrice;

    List<Pre_Invoice_List_Model1> entries = new ArrayList<>();

    ArrayList<Pre_Invoice_List_Model1> filtred;

    private Context context;

    public Test_Adapter_PreInvoice_List(Context context) {
        this.context = context;
        filtred = new ArrayList<>(entries);
    }

    @NonNull
    @Override
    public viewhollder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.list_item_factur_test2, viewGroup, false);
        return new viewhollder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewhollder viewhollder, int i) {
        viewhollder.bindadress(entries.get(i), i);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class viewhollder extends RecyclerView.ViewHolder {

        TextView txv1, txv2, txv3, txv4, txv5, txv6, txv7;
        Button bn_payment_by_amount, bn_online_payment, bn_increas_amount, bn_cancel_invoice;
        int amount = 0;

        ConstraintLayout detailLayout1, detailLayout2;
        TextView productName1, productPrice1, productName2, productPrice2;

        public viewhollder(@NonNull View itemView) {
            super(itemView);
            // model
            txv1 = itemView.findViewById(R.id.status);
            txv2 = itemView.findViewById(R.id.amount);
            txv3 = itemView.findViewById(R.id.deposit);
            txv4 = itemView.findViewById(R.id.date);
            txv5 = itemView.findViewById(R.id.tv1);
            txv6 = itemView.findViewById(R.id.vat_price);
//            txv6 = itemView.findViewById(R.id.productName1);
            txv7 = itemView.findViewById(R.id.discount_price);

            productName1 = itemView.findViewById(R.id.productName1);
            productPrice1 = itemView.findViewById(R.id.productPrice1);

            productName2 = itemView.findViewById(R.id.productName2);
            productPrice2 = itemView.findViewById(R.id.productPrice2);

            //Layouts
            detailLayout1 = itemView.findViewById(R.id.detailLayout1);
            detailLayout2 = itemView.findViewById(R.id.detailLayout2);


            // Buttons
            bn_payment_by_amount = itemView.findViewById(R.id.bn_payment_by_amount);
            bn_online_payment = itemView.findViewById(R.id.bn_online_payment);
            bn_increas_amount = itemView.findViewById(R.id.bn_increas_amount);
            bn_cancel_invoice = itemView.findViewById(R.id.bn_cancel_invoice);

            // details
            unitPrice = itemView.findViewById(R.id.unit_price);
        }

        public void bindadress(Pre_Invoice_List_Model1 model, int i) {

            List<Model_Get_invoice_detail_list> details = model.getDetails();

            // model
            String fa_nu = "" + model.getFactor_number();
            String to_am = model.getTotal_amount();
            txv1.setText(fa_nu);
            txv2.setText(to_am);
            txv3.setText(model.getStatus());
            txv4.setText(model.getDate());
            productName1.setText("" + details.get(0).getName() + "(" + details.get(0).getNumber() + ")");
            String price = "" + details.get(0).getPrice();
            price = edit_price(price);
            productPrice1.setText(price);
            detailLayout2.setVisibility(View.GONE);
            txv7.setText("" + details.get(0).getDiscount());
            txv6.setText("" + details.get(0).getDiscount());


            bn_online_payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Invoice_Bank_Payment.class);
                    context.startActivity(intent);
                }
            });

//            bn_increas_amount.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.putExtra("animation", 1);
//                    context.startActivity(intent);
//                }
//            });


            // TODO: 7/30/2019 SET LISTENERES FOR DIALOGUE

            bn_increas_amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, context.getString(R.string.payment_dialouge_method), Toast.LENGTH_SHORT).show();
                    LayoutInflater factory = LayoutInflater.from(context);
                    final View deleteDialogView = factory.inflate(R.layout.increase_money_dialogue, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                    deleteDialog.setView(deleteDialogView);
                    deleteDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_2; //style id

                    ImageView imageView1 = deleteDialogView.findViewById(R.id.payment_immage);
                    ImageView imageView2 = deleteDialogView.findViewById(R.id.recipt_image);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageView1.setImageTintList(ContextCompat.getColorStateList(context, Constans.colorprimary));
                        imageView2.setImageTintList(ContextCompat.getColorStateList(context, Constans.colorprimary));
                    }
                    deleteDialogView.findViewById(R.id.bank_recipt).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    deleteDialogView.findViewById(R.id.payment).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    deleteDialog.show();
                }
            });


            bn_cancel_invoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel_Invoice();
                }
            });


            bn_payment_by_amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_Amount() > 0) {
                        payment_Inventory();
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.preinvoice_notenough_credit), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // TODO: 7/30/2019 Use visibility to change layouts and data from server

//            String details_info = "";
//
//            for (int j = 0; j < details.size(); j++) {
//
//                details_info = details_info.concat(details.get(j).getName() + "------------" + "" + details.get(j).getPrice() + "\n");
//
//                if (Config.get_Language(context).equals("persian")) {
//
//                    details_info = details_info.concat(details.get(j).getName() + "(" + details.get(j).getNumber() + ")"
//                            + " : " + " نام مجص"
//                    );
//
//                } else {
//
//                }
//
//            }
//
//            txv6.setText(details_info);

//
//            Typeface atf = Typeface.createFromAsset(context.getAssets(), "irsans.ttf");
//            txv1.setTypeface(atf);
//            txv2.setTypeface(atf);
//            txv3.setTypeface(atf);
//            txv4.setTypeface(atf);
//            txv5.setTypeface(atf);

            // details
//      unitPrice.setText(details.get(i).getPrice());

        }


        public String edit_price(String price) {
            int lengh = price.length();
            StringBuilder price2 = new StringBuilder(price);
            while (lengh > 3) {
                lengh = lengh - 3;
                price2.insert(lengh, ",");
            }

            return price2.toString();
        }

        public void cancel_Invoice() {
            Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = retrofit2.create(API.class);
            Model_Post_Payment_Inventory model_post = new Model_Post_Payment_Inventory();
            model_post.setInvoice_id(Invoice_List.id);
            model_post.setToken(Config.getToken(context));
            Call<Model_Get_Payment_Inventory> model = api.cancel_invoice(model_post);
            model.enqueue(new Callback<Model_Get_Payment_Inventory>() {
                @Override
                public void onResponse(Call<Model_Get_Payment_Inventory> call, Response<Model_Get_Payment_Inventory> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.server_1571), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model_Get_Payment_Inventory> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.server_1572), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public int check_Amount() {
            Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = retrofit2.create(API.class);
            Model_Post_Payment_Inventory model_post = new Model_Post_Payment_Inventory();
            model_post.setInvoice_id(Invoice_List.id);
            model_post.setToken(Config.getToken(context));
            Call<Model_Get_Payment_Inventory> model = api.check_Inventory(model_post);
            model.enqueue(new Callback<Model_Get_Payment_Inventory>() {
                @Override
                public void onResponse(Call<Model_Get_Payment_Inventory> call, Response<Model_Get_Payment_Inventory> response) {
                    if (response.isSuccessful()) {
                        amount = response.body().getAmount();
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.server_1571), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model_Get_Payment_Inventory> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.server_1572), Toast.LENGTH_SHORT).show();
                }
            });
            return amount;
        }

        public void payment_Inventory() {
            Retrofit retrofit2 = new Retrofit.Builder().baseUrl(Config.getBaseUrl()).addConverterFactory(GsonConverterFactory.create()).build();
            API api = retrofit2.create(API.class);
            Model_Post_Payment_Inventory model_post = new Model_Post_Payment_Inventory();
            model_post.setInvoice_id(Invoice_List.id);
            model_post.setToken(Config.getToken(context));
            Call<Model_Get_Payment_Inventory> model = api.payment_Inventory(model_post);
            model.enqueue(new Callback<Model_Get_Payment_Inventory>() {
                @Override
                public void onResponse(Call<Model_Get_Payment_Inventory> call, Response<Model_Get_Payment_Inventory> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Factor_List.class);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.server_1561), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Model_Get_Payment_Inventory> call, Throwable t) {
                    Toast.makeText(context, context.getResources().getString(R.string.server_1562), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


}