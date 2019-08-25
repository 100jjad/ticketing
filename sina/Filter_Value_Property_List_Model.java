package com.example.ssoheyli.ticketing_newdesign.Model;

public class Filter_Value_Property_List_Model
{
    int property_value_id;
    String property_value_name;

    public Filter_Value_Property_List_Model(int property_value_id, String property_value_name)
    {
        this.property_value_id = property_value_id;
        this.property_value_name = property_value_name;
    }

    public int getProperty_value_id()
    {
        return property_value_id;
    }

    public void setProperty_value_id(int property_value_id)
    {
        this.property_value_id = property_value_id;
    }

    public String getProperty_value_name()
    {
        return property_value_name;
    }

    public void setProperty_value_name(String property_value_name)
    {
        this.property_value_name = property_value_name;
    }
}
