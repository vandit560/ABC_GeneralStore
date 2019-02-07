package com.eccelor.mauck.taa.abc_generalstore;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.eccelor.mauck.taa.abc_generalstore.adapter.ListItemAdapter;
import com.eccelor.mauck.taa.abc_generalstore.database.Database;
import com.eccelor.mauck.taa.abc_generalstore.model.Item;
import java.util.ArrayList;


public class ProductItemActivity extends AppCompatActivity
{
    ArrayList<Item> arrayList;

    private ListView listView;
    private AppCompatButton AddItem;
    private AppCompatButton Report;
    private AppCompatImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);

        listView = findViewById(R.id.listItem);
        AddItem = findViewById(R.id.btnAdditem);
        Report = findViewById(R.id.btnReport);
        back = findViewById(R.id.imgBack);


        final Database database = new Database(ProductItemActivity.this);
        arrayList=new ArrayList<>();
        arrayList = database.display_ItemName(getIntent().getStringExtra("id"));
        registerForContextMenu(listView);
        listView.setAdapter(new ListItemAdapter(ProductItemActivity.this,arrayList));
        listView.deferNotifyDataSetChanged();
        View.OnClickListener clickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switch (view.getId())
                {
                    case R.id.btnAdditem:
                        final Dialog dialog=new Dialog(ProductItemActivity.this);
                        dialog.setTitle("Add Item");
                        dialog.setContentView(R.layout.partial_item_name);
                        final AppCompatEditText textItemName = dialog.findViewById(R.id.textItemName);
                        final AppCompatEditText textItemQuantity = dialog.findViewById(R.id.textItemQuantity);
                        final AppCompatEditText textItemBuyPrice = dialog.findViewById(R.id.textItemBuyPrice);
                        final AppCompatEditText textItemsellPrice = dialog.findViewById(R.id.textItemSellPrice);
                        AppCompatButton Add = dialog.findViewById(R.id.btnAdd);

                        Add.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                String itemName=textItemName.getText().toString();
                                String quantity=textItemQuantity.getText().toString();
                                String price=textItemBuyPrice.getText().toString();
                                String sell = textItemsellPrice.getText().toString();

                                if (TextUtils.isEmpty(itemName))
                                {
                                    textItemName.setError("Enter ItemName");
                                    textItemName.requestFocus();
                                    return;
                                }
                                if (TextUtils.isEmpty(quantity))
                                {
                                    textItemQuantity.setError("Enter Quanity");
                                    textItemQuantity.requestFocus();
                                    return;
                                }
                                if (TextUtils.isEmpty(String.valueOf(price)))
                                {
                                    textItemBuyPrice.setError("Enter Buy Price");
                                    textItemBuyPrice.requestFocus();
                                    return;
                                }
                                if (TextUtils.isEmpty(String.valueOf(sell)))
                                {
                                    textItemsellPrice.setError("Enter Sell Price");
                                    textItemsellPrice.requestFocus();
                                    return;
                                }
                                else {

                                    Item item = new Item();
                                    item.setItem_name(textItemName.getText().toString());
                                    item.setItem_quantity(textItemQuantity.getText().toString());
                                    item.setItem_buy_price(textItemBuyPrice.getText().toString());
                                    item.setItem_sell_price(textItemsellPrice.getText().toString());
                                    item.setProId(getIntent().getStringExtra("id"));
                                    Database database = new Database(ProductItemActivity.this);
                                    database.insertItem(item);
                                    Intent intent = new Intent(ProductItemActivity.this,DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();

                                }

                            }
                        });
                        dialog.show();
                        break;

                    case R.id.btnReport:

                        final Dialog dialog1 = new Dialog(ProductItemActivity.this);
                        dialog1.setTitle("Report");
                        dialog1.setContentView(R.layout.partial_report);
                        final AppCompatButton daily = dialog1.findViewById(R.id.btnDailyReport);
                        final  AppCompatButton monthly = dialog1.findViewById(R.id.btnMonthlyReport);

                        View.OnClickListener clickListener1=new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                switch (view.getId())
                                {
                                    case R.id.btnDailyReport:
                                        final Dialog dialog2 = new Dialog(ProductItemActivity.this);
                                        dialog2.setTitle("Report");
                                        dialog2.setContentView(R.layout.partial_report_management);
                                        AppCompatButton ok = dialog2.findViewById(R.id.btnOk);
                                        ok.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View view)
                                            {
                                                Toast.makeText(ProductItemActivity.this, "Report Submit", Toast.LENGTH_SHORT).show();
                                                dialog2.dismiss();
                                            }
                                        });
                                        dialog2.show();
                                        dialog1.dismiss();
                                        break;

                                    case R.id.btnMonthlyReport:
                                        Toast.makeText(ProductItemActivity.this, "Monthly Report", Toast.LENGTH_SHORT).show();
                                        dialog1.dismiss();
                                        break;
                                }
                            }

                        };

                        daily.setOnClickListener(clickListener1);
                        monthly.setOnClickListener(clickListener1);
                        dialog1.show();
//                        TotalBuy();
                        break;
                    case R.id.imgBack:
                        onBackPressed();
                        break;


                }
            }
        };
        AddItem.setOnClickListener(clickListener);
        Report.setOnClickListener(clickListener);
        back.setOnClickListener(clickListener);

    }

        public void TotalBuy()
        {
            Long price1 = Long.valueOf(0);
            Long SellPrice = Long.valueOf(0);
            Long Total =  Long.valueOf(0);
            Long profit = Long.valueOf(0);
            int Buysum=0;
            int SellSum=0;
            for (int i = 0; i <arrayList.size(); i++)
            {
                price1 =Long.parseLong(arrayList.get(i).getItem_buy_price());
                Buysum = (int) (Buysum+price1);
                Log.e("Total Buy",Buysum+"");
            }

            for (int j = 0; j<arrayList.size(); j++)
            {
                SellPrice =Long.parseLong(arrayList.get(j).getItem_sell_price());
                SellSum =  (int) (SellSum + SellPrice);
                Log.e("Total Sell",SellSum+"");
            }

            for (int k = 0; k<arrayList.size(); k++)
            {
                profit = Long.valueOf(arrayList.get(k).getItem_quantity());
                Total = profit*(SellSum-Buysum);
            }
            Total = profit*(SellSum-Buysum);
//            Total = Long.valueOf(SellSum-Buysum);
            Log.e("Total",Total+"");
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.add("Sell Item");
        menu.add("Update Item");
        menu.add("Delete Item");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = adapterContextMenuInfo.position;

        final Item item1 = arrayList.get(position);

        final String id1=item1.getId1();
        final String name=item1.getItem_name();
        String quantity=item1.getItem_quantity();
        String buy=item1.getItem_buy_price();
        String sell=item1.getItem_sell_price();


        if (item.getTitle().equals("Update Item"))
        {
            final Dialog dialog=new Dialog(ProductItemActivity.this);
            dialog.setTitle("Update Item");
            dialog.setContentView(R.layout.partial_item_name);
            final AppCompatEditText textItemName = dialog.findViewById(R.id.textItemName);
            final AppCompatEditText textItemQuantity = dialog.findViewById(R.id.textItemQuantity);
            final AppCompatEditText textItemBuyPrice = dialog.findViewById(R.id.textItemBuyPrice);
            final AppCompatEditText textItemsellPrice = dialog.findViewById(R.id.textItemSellPrice);
            AppCompatButton Add = dialog.findViewById(R.id.btnAdd);

            textItemName.setText(name);
            textItemQuantity.setText(quantity);
            textItemBuyPrice.setText(buy);
            textItemsellPrice.setText(sell);

            Add.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String itemName=textItemName.getText().toString();
                    String quantity=textItemQuantity.getText().toString();
                    String price=textItemBuyPrice.getText().toString();
                    String sell = textItemsellPrice.getText().toString();

                    if (TextUtils.isEmpty(itemName))
                    {
                        textItemName.setError("Enter ItemName");
                        textItemName.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(quantity))
                    {
                        textItemQuantity.setError("Enter Quanity");
                        textItemQuantity.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(String.valueOf(price)))
                    {
                        textItemBuyPrice.setError("Enter Buy Price");
                        textItemBuyPrice.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(String.valueOf(sell)))
                    {
                        textItemsellPrice.setError("Enter Sell Price");
                        textItemsellPrice.requestFocus();
                        return;
                    }
                    else {

                        Item item = new Item();
                        item.setId1(id1);
                        item.setItem_name(textItemName.getText().toString());
                        item.setItem_quantity(textItemQuantity.getText().toString());
                        item.setItem_buy_price(textItemBuyPrice.getText().toString());
                        item.setItem_sell_price(textItemsellPrice.getText().toString());
                        Database database = new Database(ProductItemActivity.this);
                        database.Update_Item(item);

                        Intent intent = new Intent(ProductItemActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }
        if (item.getTitle().equals("Sell Item"))
        {
            final Dialog dialog=new Dialog(ProductItemActivity.this);
            dialog.setTitle("Sell Item");
            dialog.setContentView(R.layout.partial_item_sell);
            final AppCompatTextView textItemName = dialog.findViewById(R.id.textItemName);
            final AppCompatTextView textItemQuantity = dialog.findViewById(R.id.textItemQuantity);
            final AppCompatEditText textItemSellQuantity = dialog.findViewById(R.id.textItemSellQuantity);

            AppCompatButton Sell = dialog.findViewById(R.id.btnSell);

            textItemName.setText(name);
            textItemQuantity.setText(quantity);
            final String buyy=buy;
            final String sel=sell;
            final Long a= Long.valueOf(quantity);

            Sell.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    String sellQuantity=textItemSellQuantity.getText().toString();
                    if (TextUtils.isEmpty(sellQuantity))
                    {
                        textItemSellQuantity.setError("Enter Sell Quatity");
                        textItemSellQuantity.requestFocus();
                    }
                    else
                        {
                        Item item = new Item();
                        item.setId1(id1);

                        String b = textItemSellQuantity.getText().toString();
                        Long c= Long.valueOf(b);
                        Long qun = Long.valueOf(String.valueOf(a-c));
                        item.setItem_name(name);
                        item.setItem_quantity(String.valueOf(qun));
                        item.setItem_buy_price(buyy);
                        item.setItem_sell_price(sel);
                        Database database = new Database(ProductItemActivity.this);
                        database.Update_Item(item);
                        Intent intent = new Intent(ProductItemActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProductItemActivity.this, "Item sell", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();

        }
        if (item.getTitle().equals("Delete Item"))
        {
            Database database=new Database(ProductItemActivity.this);
            database.Delete_Item(item1.getId1());
            Intent intent = new Intent(ProductItemActivity.this,DashboardActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onContextItemSelected(item);
    }

}
