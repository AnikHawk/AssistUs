package sh.com.myapplication10;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


import static sh.com.myapplication10.SaveAndLoad.Save;
import static sh.com.myapplication10.SilentMainActivity.dltSilentFlag;
import static sh.com.myapplication10.SilentMainActivity.root1Silent;
import static sh.com.myapplication10.SilentMainActivity.root2Silent;
import static sh.com.myapplication10.SilentMainActivity.savedDataSilentTime;
import static sh.com.myapplication10.SilentMainActivity.savedDataTitleSilent;

/**
 * Created by anika on 9/6/17.
 */

public class MyAdapterSilent extends RecyclerView.Adapter<MyAdapterSilent.ViewHolder> {

    private List<RecyclerItem> listItems;
    private Context mcontext;
    public static String CurrentyDeletedSilentTime="";




    public MyAdapterSilent(List<RecyclerItem> listItems, SilentMainActivity mcontext) {
        this.listItems = listItems;
        this.mcontext = mcontext;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RecyclerItem itemList = listItems.get(position);
        holder.txtTitle.setText(itemList.getTitle());
        holder.txtDescription.setText(itemList.getDescription());
        holder.Rlayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(final View view) {

                //Display option menu
                PopupMenu popupMenu = new PopupMenu(mcontext,holder.Rlayout);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.mnu_item_edit:

                                Toast.makeText(mcontext,"Saved",Toast.LENGTH_SHORT).show();

                                //  String win = ""+position;
                                Intent intent = new Intent(view.getContext(), SilentEdit.class);
                                intent.putExtra("position", ""+position);
                                view.getContext().startActivity(intent);


                                notifyDataSetChanged();

                                break;


                            case R.id.mnu_item_delete:
                                listItems.remove(position);
                                CurrentyDeletedSilentTime= savedDataSilentTime.get(position);
                                savedDataTitleSilent.remove(position);
                                savedDataSilentTime.remove(position);

                                Save (root1Silent,savedDataTitleSilent);
                                Save(root2Silent,savedDataSilentTime);

                                notifyDataSetChanged();
                                intent = new Intent(view.getContext(), SilentMainActivity.class);
                                intent.putExtra("deletedTime", CurrentyDeletedSilentTime);
                                dltSilentFlag = 1;
                                view.getContext().startActivity(intent);
                                Toast.makeText(mcontext,"Removed Successfully",Toast.LENGTH_SHORT).show();


                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
    }



    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtTitle;
        public TextView txtDescription;
        public RelativeLayout Rlayout;
        // public TextView txtOptionDigit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            txtDescription = (TextView)itemView.findViewById(R.id.txtDescription);
            //txtOptionDigit = (TextView)itemView.findViewById(R.id.txtOptionDigit);
            Rlayout = (RelativeLayout) itemView.findViewById(R.id.Rlayout);
        }
    }



}
