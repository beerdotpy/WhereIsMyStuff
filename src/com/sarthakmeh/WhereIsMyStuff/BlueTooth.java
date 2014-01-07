package com.sarthakmeh.WhereIsMyStuff;

public class BlueTooth {
	
	int bid;
	String device_name;
	String device_address;
	
	void set_device_name(String name){
		device_name=name;
	}
	
	void set_device_address(String add){
		device_address=add;
	}
	
	void set_id(int id){
		
		bid=id;
	}
	
	String get_device_name(){
		return device_name;
	}
	
	int get_id(){
		return bid;
	}
	 
	String get_device_address(){
		return device_address;
	}
   
}
