package com.stunstyle.miomart2.service;

import java.util.Objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private final StringProperty name
            = new SimpleStringProperty(this, "productId", null);

    private final DoubleProperty buyingPrice
            = new SimpleDoubleProperty(this, "buyingPrice", -1);

    private final DoubleProperty sellingPrice
            = new SimpleDoubleProperty(this, "sellingPrice", -1);

    public Product() {
        this(null, -1, -1);
    }

    public Product(String name, double buyingPrice, double sellingPrice) {
        // TODO: validation
        this.name.set(name);
        this.buyingPrice.set(buyingPrice);
        this.sellingPrice.set(sellingPrice);
    }

    public final String getName() {
        return name.get();
    }

    public final void setName(String name) {
        this.nameProperty().set(name);
    }

    public final StringProperty nameProperty() {
        return name;
    }

    public final double getSellingPrice() {
        return sellingPrice.get();
    }

    public final void setSellingPrice(double sellingPrice) {
        this.sellingPriceProperty().set(sellingPrice);
    }

    public final DoubleProperty sellingPriceProperty() {
        return sellingPrice;
    }

    public final double getBuyingPrice() {
        return buyingPrice.get();
    }

    public final void setBuyingPrice(double buyingPrice) {
        this.buyingPriceProperty().set(buyingPrice);
    }

    public final DoubleProperty buyingPriceProperty() {
        return buyingPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(nameProperty().get(), product.nameProperty().get()) &&
                Objects.equals(buyingPriceProperty().get(), product.buyingPriceProperty().get()) &&
                Objects.equals(sellingPriceProperty().get(), product.sellingPriceProperty().get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyingPrice, sellingPrice);
    }
}
