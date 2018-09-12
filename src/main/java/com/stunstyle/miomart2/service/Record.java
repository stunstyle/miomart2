package com.stunstyle.miomart2.service;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

public class Record {
    private final ObjectProperty<Product> product = new SimpleObjectProperty<>(this, "product", null);
    private final IntegerProperty quantity = new SimpleIntegerProperty(this, "quantity", -1);

    public Record(Product product, int quantity) {
        this.product.set(product);
        this.quantity.set(quantity);
    }

    public Product getProduct() {
        return product.get();
    }

    public void setProduct(Product product) {
        this.product.set(product);
    }

    public ObjectProperty<Product> productProperty() {
        return product;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return Objects.equals(productProperty().get(), record.productProperty().get()) &&
                Objects.equals(quantityProperty().get(), record.quantityProperty().get());
    }

    @Override
    public int hashCode() {

        return Objects.hash(product, quantity);
    }

}
