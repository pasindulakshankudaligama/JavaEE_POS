package dto;

import Entity.OrderDetails;

import java.util.ArrayList;

public class OrderDTO {
    private String oid;
    private String date;
    private String customerID;
    private int discount;
    private double total;
    private double subtotal;
    private ArrayList<OrderDetails> orderDetailsArrayList;

    public OrderDTO() {
    }

    public OrderDTO(String oid, String date, String customerID, int discount, double total, double subtotal, ArrayList<OrderDetails> orderDetailsArrayList) {
        this.setOid(oid);
        this.setDate(date);
        this.setCustomerID(customerID);
        this.setDiscount(discount);
        this.setTotal(total);
        this.setSubtotal(subtotal);
        this.setOrderDetailsArrayList(orderDetailsArrayList);
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public ArrayList<OrderDetails> getOrderDetailsArrayList() {
        return orderDetailsArrayList;
    }

    public void setOrderDetailsArrayList(ArrayList<OrderDetails> orderDetailsArrayList) {
        this.orderDetailsArrayList = orderDetailsArrayList;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "oid='" + oid + '\'' +
                ", date='" + date + '\'' +
                ", customerID='" + customerID + '\'' +
                ", discount=" + discount +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", orderDetailsArrayList=" + orderDetailsArrayList +
                '}';
    }
}
