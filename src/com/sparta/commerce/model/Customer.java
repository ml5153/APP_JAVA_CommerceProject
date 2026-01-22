package com.sparta.commerce.model;

import com.sparta.commerce.model.type.UserLevel;

import java.util.Objects;

/**
 *    `등급` 은 누적 주문 금액에 따라 정해집니다. ( 이번 주문 금액 포함)
 *     브론즈- 누적 주문금액 < 50만
 *     실버- 50만 < 누적 주문금액 < 100만
 *     골드- 100만 < 누적 주문금액 < 200만
 *     플레티넘- 200만 < 누적 주문금액
 */
public class Customer {
    private String name;
    private String mail;
    private UserLevel level;
    private int orderAmount;

    public Customer(String name, String mail, UserLevel level, int orderAmount) {
        this.name = name;
        this.mail = mail;
        this.level = level;
        this.orderAmount = orderAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return orderAmount == customer.orderAmount && Objects.equals(name, customer.name) && Objects.equals(mail, customer.mail) && level == customer.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mail, level, orderAmount);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", level=" + level +
                ", orderAmount=" + orderAmount +
                '}';
    }
}
