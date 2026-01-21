package com.sparta.commerce.model.type;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public enum CategoryType {
    MAIN(0, "메인"),
    ELECTRONICS(1,"전자제품"),
    CLOTHING(2,"의류"),
    FOOD(3, "식품");

    private final int menuNum;
    private final String name;

    CategoryType(final int menuNum, final String name) {
        this.menuNum = menuNum;
        this.name = name;
    }

    public int getMenuNum() {
        return menuNum;
    }

    public String getName() {
        return name;
    }


    // 습관화하기 : Objects.equals || "22".equals() <요다방식>
    public static Optional<CategoryType> fromMenuNum(final int menuNum) {
        return Arrays.stream(CategoryType.values())
                .filter(categoryType -> Objects.equals(categoryType.menuNum, menuNum))
                .findFirst();
    }

    public static Optional<CategoryType> fromName(final String name) {
        return Arrays.stream(CategoryType.values())
                .filter(categoryType -> Objects.equals(categoryType.name, name))
                .findFirst();
    }


    // 앞전에 단일객체는 Optional & StreamAPI 를 사용하다는 것이 더좋다는 것을 인지
    public static CategoryType fromNameLegacy(final String name) {
        for(CategoryType type : CategoryType.values()) {
            if(type.name.equals(name)){
                return type;
            }
        }
        return null;
    }

}
