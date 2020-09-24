package com.example.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.shoppingcart.Model.common.ShopItem;
import com.example.shoppingcart.Model.ui.ExpandShopGroup;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateAndFillValuesInListShoppingRows extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExpandShopGroup> groups;

    public CreateAndFillValuesInListShoppingRows(Context context, ArrayList<ExpandShopGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<ShopItem> chList=groups.get(groupPosition).getItems();

        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ShopItem> chList=groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) { //get shopping group

        ExpandShopGroup group=(ExpandShopGroup)getGroup(groupPosition);
        if(view==null)
        {
            LayoutInflater inf=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inf.inflate(R.layout.expandlist_shopping_group,null);
        }
        TextView tv=(TextView)view.findViewById(R.id.tvGroup);
        tv.setText(group.getName());

        TextView tvDate=(TextView)view.findViewById(R.id.tvGroupDate);
        tvDate.setText(group.getDate().split("T")[0]);

        View viewId=view.findViewById(R.id.deleteRow);
        if(viewId!=null)
        {
            viewId.setTag(group.getShoppingId());
        }
        final TextView tvMenu=(TextView)view.findViewById(R.id.tv_menu);
        tvMenu.setTag(group.getShoppingId());
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickMenu(tvMenu);
            }
        });
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {//expand shopping items
        ShopItem child=(ShopItem)getChild(groupPosition,childPosition);
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.expendlist_shopping_item,null);
        }
        TextView tv=(TextView)view.findViewById(R.id.tvChild1);
        tv.setText(child.getCategory().toString());

        TextView tv2=(TextView)view.findViewById(R.id.tvChild2);
        tv2.setText(child.getProduct());

        TextView tv3=(TextView)view.findViewById(R.id.tvChild3);
        tv3.setText(String.valueOf(child.getQuantity()));

        TextView tv4=(TextView)view.findViewById(R.id.tvChild4);
        tv4.setText(child.getUnit());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void showPickMenu(final View anchor)
    {
        final PopupMenu popupMenu=new PopupMenu(anchor.getContext(),anchor);
        popupMenu.inflate(R.menu.sale_item_options);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                View actionView=menuItem.getActionView();
                String shoppingId=(String)anchor.getTag();
                switch (menuItem.getItemId())
                {
                    case R.id.deleteRow:
                        FirebaseDatabase.getInstance().getReference("shopping")
                                .child(MainActivity.getSubscriberId(anchor.getContext())).child(shoppingId).removeValue();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void addItem(ShopItem item,ExpandShopGroup group)
    {
        if(!groups.contains((group)))
        {
            groups.add(group);
        }
        int index=groups.indexOf(group);
        ArrayList<ShopItem> ch=groups.get(index).getItems();
        ch.add(item);
        groups.get(index).setItems(ch);
    }
}


