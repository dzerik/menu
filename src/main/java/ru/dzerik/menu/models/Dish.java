/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dzerik.menu.models;

/**
 *
 * @author Strobo
 */
public class Dish {
    private Integer id;
    private String productname;
    private Integer weight;
    private Integer fullcost;
    private Integer halfcost;
    private String description;

    public Dish(Integer id, String productname, Integer weight, Integer fullcost, Integer halfcost, String description) {
        this.id = id;
        this.productname = productname;
        this.weight = weight;
        this.fullcost = fullcost;
        this.halfcost = halfcost;
        this.description = description;
    }

    public Dish() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getFullcost() {
        return fullcost;
    }

    public void setFullcost(Integer fullcost) {
        this.fullcost = fullcost;
    }

    public Integer getHalfcost() {
        return halfcost;
    }

    public void setHalfcost(Integer halfcost) {
        this.halfcost = halfcost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public String toJSON() {
        return "{\"id\":"+ id + ","
                + "\"productname\":\"" + productname + "\","
                + "\"weight\":" + weight + ","
                + "\"fullcost\":"+ fullcost +","
                + "\"halfcost\":" + halfcost + ","
                + "\"description\":\""+ description+ "\""
                + "}";
    }
    
};

