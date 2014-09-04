package com.notbook.entity;

public class CityClass {

	private String _id ;
	private String name ;
	private String city_num;
	private String province_id;
	public CityClass(String _id, String name, String city_num,
			String province_id) {
		super();
		this._id = _id;
		this.name = name;
		this.city_num = city_num;
		this.province_id = province_id;
	}
	public CityClass() {
		super();
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity_num() {
		return city_num;
	}
	public void setCity_num(String city_num) {
		this.city_num = city_num;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	@Override
	public String toString() {
		return "CityClass [_id=" + _id + ", name=" + name + ", city_num="
				+ city_num + ", province_id=" + province_id + "]";
	}
	
	
}
