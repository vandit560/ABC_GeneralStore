package com.eccelor.mauck.taa.abc_generalstore.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.eccelor.mauck.taa.abc_generalstore.model.Product;
import com.eccelor.mauck.taa.abc_generalstore.model.Item;
import java.util.ArrayList;

/**
 * Created by ECLR-01 on 06-10-2017.
 */

public class Database extends SQLiteOpenHelper
{

    String TableProduct = "Product", // table_name
            id = "id", ProductName = "ProductName"; // autoincrement value in product_table

    String TableItem = "Item", // table_name
            id1 = "id1",                        // autoincrement value in item_table
            ItemName = "ItemName",              // show item_nae
            ItemQuantity = "ItemQuantity",      // show item_quantity
            ItemBuyPrice = "ItemBuyPrice",      // show item_buy_price
            ItemSellPrice = "ItemSellPrice",    // show item_sell_price
            proId = "proId";                    // add integer value

    public Database(Context context)
    {
        super(context, "ABC", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) // create two table
    {
        String food = " create table " + TableProduct + "("
                + id + " integer primary key autoincrement, "
                + ProductName + " text)";
        sqLiteDatabase.execSQL(food);

        String item = " create table " + TableItem + "("
                + id1 + " integer primary key autoincrement, "
                + proId +" integer, "
                + ItemName + " text, "
                + ItemQuantity + " text, "
                + ItemBuyPrice + " text, "
                + ItemSellPrice + " text)";
        sqLiteDatabase.execSQL(item);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public void insertProduct(Product product) // add record in product_table
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductName, product.getProductName());
        database.insert(TableProduct,null,contentValues);
        database.close();
    }


    public void insertItem(Item item) // add record in item_table
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(proId,item.getProId());
        contentValues.put(ItemName,item.getItem_name());
        contentValues.put(ItemQuantity,item.getItem_quantity());
        contentValues.put(ItemBuyPrice,item.getItem_buy_price());
        contentValues.put(ItemSellPrice,item.getItem_sell_price());
        database.insert(TableItem,null,contentValues);
        database.close();
    }

    public ArrayList<Product> display_ProductName() // show all record in product_table
    {
        ArrayList<Product> arrayList=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = " select * from " + TableProduct;
        Cursor cursor = database.rawQuery(sql,null);

        while (cursor.moveToNext())
        {
            Product product = new Product();
            product.setId(cursor.getString(0));
            product.setProductName(cursor.getString(1));
            arrayList.add(product);
        }
        database.close();
        return arrayList;
    }

    public ArrayList<Item> display_ItemName(String pid) // show  perticular item record
    {
        ArrayList<Item> arrayList=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String sql = " select * from " + TableItem + " where "+proId+"="+pid;
        Cursor cursor = database.rawQuery(sql,null);

        while (cursor.moveToNext())
        {
            Item item = new Item();
            item.setId1(cursor.getString(0));
            item.setItem_name(cursor.getString(2));
            item.setItem_quantity(cursor.getString(3));
            item.setItem_buy_price(cursor.getString(4));
            item.setItem_sell_price(cursor.getString(5));
            arrayList.add(item);
        }
        database.close();
        return arrayList;
    }


    public void Update_Product(Product product) // update perticular product
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductName, product.getProductName());
        database.update(TableProduct,contentValues,id+"="+product.getId(),null);
        database.close();
    }

    public void Update_Item(Item item) // update perticular item
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemName,item.getItem_name());
        contentValues.put(ItemQuantity,item.getItem_quantity());
        contentValues.put(ItemBuyPrice,item.getItem_buy_price());
        contentValues.put(ItemSellPrice,item.getItem_sell_price());
        database.update(TableItem,contentValues,id1+"="+item.getId1(),null);
        database.close();
    }
    public void Delete_Product(String id) // delete perticular record
    {
        SQLiteDatabase database=getReadableDatabase();
        database.delete(TableProduct,this.id+"="+id,null);
        database.close();
    }
    public void Delete_Item(String id1)
    {
        SQLiteDatabase database=getReadableDatabase();
        database.delete(TableItem,this.id1+"="+id1,null);
        database.close();
    }
}
