/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package data.entities;

import java.util.Date;

/**
 *
 * @author Ruben
 */
public class Contract {
    
    private int id;
    private Rentable rentable;
    private Person renter;
    private Date start;
    private Date end;
    private float price;
    private float monthly_cost;
    private float guarentee;

    public Contract(int id, Rentable rentable, Person renter, Date start, Date end, float price, float monthly_cost, float guarentee) {
        this.id = id;
        this.rentable = rentable;
        this.renter = renter;
        this.start = start;
        this.end = end;
        this.price = price;
        this.monthly_cost = monthly_cost;
        this.guarentee = guarentee;
    }

	public Contract(int id, Person renter, Date start, Date end) {
		this.id = id;
		this.renter = renter;
		this.start = start;
		this.end = end;
	}

    public Date getEnd() {
        return end;
    }

    public float getGuarentee() {
        return guarentee;
    }

    public int getId() {
        return id;
    }

    public float getMonthly_cost() {
        return monthly_cost;
    }

    public float getPrice() {
        return price;
    }

    public Rentable getRentable() {
        return rentable;
    }

    public Person getRenter() {
        return renter;
    }

    public Date getStart() {
        return start;
    }

}
