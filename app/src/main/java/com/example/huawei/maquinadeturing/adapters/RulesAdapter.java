package com.example.huawei.maquinadeturing.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huawei.maquinadeturing.R;
import com.example.huawei.maquinadeturing.activities.RulesActivity;
import com.example.huawei.maquinadeturing.interfaces.OnEmptyRulesListener;
import com.example.huawei.maquinadeturing.models.Rule;

import java.util.ArrayList;

/**
 * Created by huawei on 28/03/17.
 */

public class RulesAdapter extends RecyclerView.Adapter<RulesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Rule> mRules;

    private ImageView mSadFace;
    private TextView mSadMessage;

    private OnEmptyRulesListener mEmptyRulesListener;

    public RulesAdapter(Context context, ArrayList<Rule> rules, OnEmptyRulesListener emptyRulesListener) {
        mContext = context;
        mRules = rules;

        mEmptyRulesListener = emptyRulesListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView currentState;
        TextView currentSymbol;
        TextView newSymbol;
        TextView direction;
        TextView newState;
        final ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);

            currentState = (TextView) itemView.findViewById(R.id.current_state);
            currentSymbol = (TextView) itemView.findViewById(R.id.current_symbol);
            newSymbol = (TextView) itemView.findViewById(R.id.new_symbol);
            direction = (TextView) itemView.findViewById(R.id.direction);
            newState = (TextView) itemView.findViewById(R.id.new_state);

            delete = (ImageView) itemView.findViewById(R.id.delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRules.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    checkAdapter();
                }
            });
        }


    }

    private void checkAdapter() {
        mEmptyRulesListener.checkRulesSize();
    }

    @Override
    public RulesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rule, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RulesAdapter.ViewHolder holder, int position) {
        holder.currentState.setText(holder.currentState.getText() + mRules.get(position).getCurrentState());
        holder.currentSymbol.setText(holder.currentSymbol.getText() + mRules.get(position).getCurrentSymbol());
        holder.newSymbol.setText(holder.newSymbol.getText() + mRules.get(position).getNewSymbol());
        holder.direction.setText(holder.direction.getText() + mRules.get(position).getDirection());
        holder.newState.setText(holder.newState.getText() + mRules.get(position).getNewSymbol());
    }

    @Override
    public int getItemCount() {
        return mRules.size();
    }
}
