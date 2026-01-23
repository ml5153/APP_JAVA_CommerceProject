package com.sparta.commerce.model.type;

public enum PriceFilter {
    ALL("전체 상품 보기", 0, Integer.MAX_VALUE),
    UNDER_100("100만원 이하 상품", 0, 1_000_000),
    OVER_100("100만원 초과 상품", 1_000_000, Integer.MAX_VALUE);

    private final String menuName;
    private final int minPrice;
    private final int maxPrice;

    PriceFilter(String menuName, int minPrice, int maxPrice) {
        this.menuName = menuName;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public boolean isMatch(int price) {
        return price >= minPrice && price <= maxPrice;
    }

    public String getMenuName() { return menuName; }
}
