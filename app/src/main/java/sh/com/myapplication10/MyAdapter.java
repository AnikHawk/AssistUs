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

import static sh.com.myapplication10.MainActivity.TaskFlag;
import static sh.com.myapplication10.MainActivity.dltFlag;
import static sh.com.myapplication10.MainActivity.root1;
import static sh.com.myapplication10.MainActivity.root2;
import static sh.com.myapplication10.MainActivity.root3;
import static sh.com.myapplication10.MainActivity.root4;
import static sh.com.myapplication10.MainActivity.savedDataLine1;
import static sh.com.myapplication10.MainActivity.savedDataLine2;
import static sh.com.myapplication10.MainActivity.savedDataLine3;
import static sh.com.myapplication10.MainActivity.savedDataLine4;
import static sh.com.myapplication10.MainActivity.savedDataLine5;
import static sh.com.myapplication10.MainActivity.savedDataLine6;
import static sh.com.myapplication10.SaveAndLoad.Save;

/**
 * Created by anika on 9/1/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<RecyclerItem> listItems;
    private Context mcontext;
    public static String CurrentyDeletedTaskTime="";




    public MyAdapter(List<RecyclerItem> listItems, MainActivity mcontext) {
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

                                //      Toast.makeText(mcontext,"saved",Toast.LENGTH_LONG).show();

                                //  String win = ""+position;
                                Intent intent = new Intent(view.getContext(), TaskManagementEdit.class);
                                intent.putExtra("position", ""+position);
                                view.getContext().startActivity(intent);


                                notifyDataSetChanged();
                                break;


                            case R.id.mnu_item_delete:
                                listItems.remove(position);
                                CurrentyDeletedTaskTime= savedDataLine2.get(position);
                                savedDataLine1.remove(position);
                                savedDataLine2.remove(position);
                                savedDataLine3.remove(position);
                                savedDataLine4.remove(position);
                                savedDataLine5.remove(position);
                                savedDataLine6.remove(position);
                                Save (root1,savedDataLine1);
                                Save(root2,savedDataLine2);
                                Save(root3, savedDataLine3);
                                Save(root4,savedDataLine4 );
                                Save(root3, savedDataLine5);
                                Save(root4,savedDataLine6 );
                                notifyDataSetChanged();
                                intent = new Intent(view.getContext(), MainActivity.class);
                                intent.putExtra("deletedTime", CurrentyDeletedTaskTime);
                                dltFlag = 1;
                                view.getContext().startActivity(intent);
                                Toast.makeText(mcontext,"Task Deleted Successfully",Toast.LENGTH_LONG).show();


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
