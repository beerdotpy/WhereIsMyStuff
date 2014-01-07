package com.sarthakmeh.WhereIsMyStuff;




import java.util.ArrayList;


import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BluetoothDeviceAdapter extends BaseAdapter {

	Context ctx;
	ArrayList<BlueTooth> btDeviceList = new ArrayList<BlueTooth>();
	LayoutInflater inflater;
	
	public BluetoothDeviceAdapter(Context context, ArrayList<BlueTooth> btDevice){
		this.ctx = context;
		this.btDeviceList =btDevice;
		inflater = LayoutInflater.from(this.ctx);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return btDeviceList.size();
	}

	@Override
	public BlueTooth getItem(int position) {
		// TODO Auto-generated method stub
		return btDeviceList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
         MyViewHolder myViewHolder;
		
		if(convertView == null){
			
		convertView = inflater.inflate(R.layout.device_list, null);
		myViewHolder = new MyViewHolder();
		convertView.setTag(myViewHolder);
		}else{
			
			myViewHolder = (MyViewHolder) convertView.getTag();
		}
		
		myViewHolder.btDeviceName = (TextView) convertView.findViewById(R.id.device_name);
		myViewHolder.btDeviceAddress = (TextView) convertView.findViewById(R.id.device_address);
		myViewHolder.btDeviceName.setText("  "+btDeviceList.get(position).get_id()+"  "+btDeviceList.get(position).get_device_name());
		myViewHolder.btDeviceAddress.setText("    "+btDeviceList.get(position).get_device_address());
		
		return convertView;
	}
	
	public static class MyViewHolder{
		
		TextView btDeviceName,btDeviceAddress;
	}

}