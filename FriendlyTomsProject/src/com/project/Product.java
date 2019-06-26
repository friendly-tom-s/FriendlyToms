package com.project;

/**
 * This is where all the product data is set.
 */

public class Product {
    private String name;
    private String description;
    private String calories;
    private String category;
    private String price;
    private String noinstock;
    private String url;

    public Product(){}

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){this.description = description;}

    public void setCalories(String calories){this.calories = calories;}

    public void setCategory(String category){this.category = category;}

    public void setPrice(String price){this.price = price;}

    public void setNoinstock(String noinstock){this.noinstock = noinstock;}

    public void setUrl(String url){this.url = url;}

    public String getName(){return name;}

    public String getDescription(){return description;}

    public String getCalories(){return calories;}

    public String getCategory(){return category;}

    public String getPrice(){return price;}

    public String getNoinstock(){return noinstock;}

    public String getUrl(){return url;}
}
