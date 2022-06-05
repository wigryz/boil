package com.pomidor.boil.transport.model;

public class RecipientToSupplierData {
    private Integer supplierId;
    private Integer recipientId;
    private int productAmount = 0;
    private double profit;
    private double sellingPrice=0;
    private double costs=0;

    private double delta;

    private Boolean base=false;
    private Boolean imaginary = false;

    public RecipientToSupplierData(Integer supplierId, Integer recipientId, double profit, Boolean imaginary) {
        this.supplierId = supplierId;
        this.recipientId = recipientId;
        this.profit = profit;
        this.imaginary = imaginary;
    }

    public RecipientToSupplierData(int supplierId, int recipientId, double sellingPrice, double transportCost, double buyingCost) {
        this.supplierId = supplierId;
        this.recipientId = recipientId;
        this.sellingPrice = sellingPrice;
        this.costs = transportCost+buyingCost;
        this.profit = sellingPrice - transportCost - buyingCost;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
        base = true;
    }

    public void addProductAmount(int productAmount){
        this.productAmount +=productAmount;
        base=true;
    }
    public void subtractProductAmount(int productAmount){
        this.productAmount-=productAmount;
        if(this.productAmount<0){
            throw new RuntimeException();
        }
        if(this.productAmount==0){
            base=false;
        }
    }

    public void setDelta(double alfa, double beta) {
        delta=profit-alfa-beta;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getCosts() {
        return costs;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public double getProfit() {
        return profit;
    }

    public double getDelta() {
        return delta;
    }

    public Boolean getBase() {
        return base;
    }

    public Boolean getImaginary() {
        return imaginary;
    }
}
