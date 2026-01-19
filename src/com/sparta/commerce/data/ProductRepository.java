package com.sparta.commerce.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    private static final Map<CategoryType, List<Product>> categoryProduct = new HashMap<>();

    static {
        categoryProduct.put(CategoryType.ELECTRONICS, List.of(
                new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 30),
                new Product("iPhone 15", 1350000, "Apple의 최신 스마트폰", 28),
                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 5),
                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 14)
        ));

        categoryProduct.put(CategoryType.CLOTHING, List.of(
                new Product("후드 티셔츠", 59000, "편안한 오버핏 면 후드", 50),
                new Product("슬랙스 팬츠", 45000, "격식 있는 자리에 어울리는 바지", 40),
                new Product("롱 패딩", 299000, "겨울철 필수 방한 아우터", 10),
                new Product("캡 모자", 25000, "심플한 디자인의 볼캡", 100)
        ));

        categoryProduct.put(CategoryType.FOOD, List.of(
                new Product("불고기 버거", 6500, "한국식 소스의 진한 맛", 20),
                new Product("치즈 피자", 18000, "고소한 모짜렐라가 듬뿍", 15),
                new Product("아이스 아메리카노", 4500, "산미가 적고 고소한 원두", 200),
                new Product("초코 조각 케이크", 7500, "진한 다크 초콜릿의 달콤함", 12)
        ));
    }

    public static List<Product> getProducts(final CategoryType type) {
        return categoryProduct.getOrDefault(type, List.of());
    }
}
