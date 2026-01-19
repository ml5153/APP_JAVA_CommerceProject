package com.sparta.commerce.data;

public enum CategoryType {
    ELECTRONICS("전자제품"),
    CLOTHING("의류"),
    FOOD("식품");

    private final String name;
    CategoryType(final String name) {
        this.name = name;
    }

    public CategoryType fromString(final String name) {
        for(CategoryType type : CategoryType.values()) {
            if(type.name.equals(name)){
                return type;
            }
        }
        return null;
    }
}
