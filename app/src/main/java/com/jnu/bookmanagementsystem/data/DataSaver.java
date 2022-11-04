package com.jnu.bookmanagementsystem.data;
import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


import android.content.Context;

import androidx.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class DataSaver {
    public void Save(Context context, ArrayList<ShopItem> data)
    {
        try {
            FileOutputStream dataStream=context.openFileOutput("bookManage.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public ArrayList<ShopItem> Load(Context context)
    {
        ArrayList<ShopItem> data=new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("bookManage.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<ShopItem>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
