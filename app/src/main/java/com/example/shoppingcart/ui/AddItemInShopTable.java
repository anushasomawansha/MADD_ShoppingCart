package com.example.shoppingcart.ui;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.annotation.NonNull;

import com.example.shoppingcart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AddItemInShopTable {

    public static void addRow(final Context context, final TableLayout adddalTableLayout, final DatabaseReference databaseReference)
    {
        final TableRow tr= new TableRow(context);
        TableLayout.LayoutParams paramsForRow=new TableLayout.LayoutParams(0,TableLayout.LayoutParams.WRAP_CONTENT,1f);
        paramsForRow.setMargins(10,10,0,20);
        tr.setLayoutParams(paramsForRow);

        final TableRow.LayoutParams paramsForCategoryAndProduct=new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1.4f);
        final TableRow.LayoutParams paramsForQuantity=new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,.9f);
        final TableRow.LayoutParams paramsForUnit=new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,1.4f);

        final Spinner catSpinner =new Spinner(context);
        catSpinner.setLayoutParams(paramsForCategoryAndProduct);

        final Spinner productSpinner =new Spinner(context);
        catSpinner.setLayoutParams(paramsForCategoryAndProduct);

        final EditText quantity=new EditText(context);
        quantity.setLayoutParams(paramsForQuantity);
        quantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        final Spinner unitSpinner =new Spinner(context);
        catSpinner.setLayoutParams(paramsForUnit);

        final EditText etNewCategory=new EditText(context);
        etNewCategory.setLayoutParams(paramsForCategoryAndProduct);
        etNewCategory.setHint("New Category");

        final EditText etNewProduct=new EditText(context);
        etNewProduct.setLayoutParams(paramsForCategoryAndProduct);
        etNewProduct.setHint("New Product");

        final TableRow.LayoutParams rowParamsMinus=new TableRow.LayoutParams(0,TableRow.LayoutParams.WRAP_CONTENT,.5f);
        ImageButton minusButton=new ImageButton(context);
        minusButton.setImageResource(R.drawable.ic_baseline_indeterminate_check_box_24);
        minusButton.setLayoutParams(rowParamsMinus);

        fetchUnitInSpinner(databaseReference,context,unitSpinner);
        fetchCategoryAndProduct(databaseReference,context,catSpinner,productSpinner,tr,etNewCategory,etNewProduct);
        addOrRemoveProductSpinner(productSpinner,tr,etNewProduct);

        tr.addView(catSpinner);
        tr.addView(quantity);
        tr.addView(unitSpinner);
        tr.addView(minusButton);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adddalTableLayout.removeView((ViewGroup)view.getParent());
            }
        });

        adddalTableLayout.addView(tr,adddalTableLayout.getChildCount() - 2,
                new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT));


    }

    private static void addOrRemoveProductSpinner(final Spinner productSpinner, final TableRow tr, final EditText etNewProduct) {
        productSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(productSpinner.getSelectedItem().equals("Others"))
                {
                    tr.addView(etNewProduct,2);
                }
                else{
                    tr.removeView(etNewProduct);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static void fetchCategoryAndProduct(final DatabaseReference databaseReference, final Context context, final Spinner catSpinner, final Spinner productSpinner, final TableRow tr, final EditText etNewCategory, final EditText etNewProduct) {
        fetchCategoriesInSpinner(databaseReference,context,catSpinner);
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem=(String)catSpinner.getSelectedItem();
                if(selectedItem.equals("Others"))
                {
                    tr.removeView(productSpinner);
                    tr.addView(etNewCategory,1);
                    tr.addView(etNewProduct,2);
                }
                else
                    {
                    tr.removeView(etNewCategory);
                    if(!tr.getChildAt(1).equals(productSpinner))
                    {
                        tr.addView(productSpinner,1);
                    }
                    databaseReference.child("categories").child(selectedItem)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final ArrayList<String> productList=new ArrayList<>();
                                    for(DataSnapshot areaSnapshot:dataSnapshot.getChildren())
                                    {
                                        productList.add(areaSnapshot.getValue(String.class));
                                    }
                                    Collections.sort(productList);
                                    productList.add("Others");

                                    final ArrayAdapter<String> productAdapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,productList);
                                    productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    productSpinner.setAdapter(productAdapter);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static void fetchCategoriesInSpinner(DatabaseReference databaseReference, final Context context, final Spinner catSpinner) {
        databaseReference.child("categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> catList=new ArrayList<>();
                for(DataSnapshot areaSnapshot:dataSnapshot.getChildren()){
                    catList.add(areaSnapshot.getKey());
                }
                Collections.sort(catList);
                catList.add("Others");

                final ArrayAdapter<String> catAdapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,catList);
                catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                catSpinner.setAdapter(catAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static void fetchUnitInSpinner(DatabaseReference databaseReference,final Context context, final Spinner unitSpinner) {

        databaseReference.child("Unit").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> unitList=new ArrayList<>();
                for(DataSnapshot areaSnapshot:dataSnapshot.getChildren())
                {
                    unitList.add(areaSnapshot.getValue(String.class));
                }
                Collections.sort(unitList);

                final ArrayAdapter<String> unitAdapter=new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,unitList);
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                unitSpinner.setAdapter(unitAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
