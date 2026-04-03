package com.example.smsreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 短信列表适配器
 */
public class SmsAdapter extends BaseAdapter {

    private Context context;
    private List<SmsInfo> smsList;

    public SmsAdapter(Context context, List<SmsInfo> smsList) {
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public int getCount() {
        return smsList.size();
    }

    @Override
    public Object getItem(int position) {
        return smsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sms, parent, false);
            holder = new ViewHolder();
            holder.tvAddress = convertView.findViewById(R.id.tv_address);
            holder.tvDate    = convertView.findViewById(R.id.tv_date);
            holder.tvBody    = convertView.findViewById(R.id.tv_body);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SmsInfo sms = smsList.get(position);
        holder.tvAddress.setText(sms.getAddress());
        holder.tvDate.setText(sms.getDate());
        holder.tvBody.setText(sms.getBody());

        return convertView;
    }

    static class ViewHolder {
        TextView tvAddress;
        TextView tvDate;
        TextView tvBody;
    }
}
