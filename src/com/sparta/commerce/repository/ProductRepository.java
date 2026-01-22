package com.sparta.commerce.repository;

import com.sparta.commerce.model.Category;
import com.sparta.commerce.model.Product;
import com.sparta.commerce.model.type.CategoryType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    /**
     * 구현하다가 깨달은점.
     * 과제에서는 Category 리스트안에 Product 리스트를 넣어서 구현하라 했다!
     * 하지만 LinkedHashMap이 좀더 적당하지 않을까라는 생각이들었다. 찾아봤을 때 메모리점유율은 높지만 데이터가 얼마되지 않을때는
     * 직관적이고 조회도 빠르고 순서도 보장하니 더 최선의 선택이지 않을까 싶다.
     */
    //    private static final Map<CategoryType, List<Product>> categoryProduct = new LinkedHashMap<>();
    private static final List<Category> categories = new ArrayList<>();

    public static List<Category> getCategories() {
        return categories;
    }


    static {
        /**
         * 상품을 주문 시에 재고를 차감해야하기때문에 불변의 List.of() 가 아닌 Arrays.asList()를 사용해야한다.
         */

        categories.add(
                new Category(
                        CategoryType.ELECTRONICS.getName(),
                        Arrays.asList(
                                new Product("Galaxy S24", 1200000, "최신 안드로이드 스마트폰", 30),
                                new Product("iPhone 15", 1350000, "Apple의 최신 스마트폰", 28),
                                new Product("MacBook Pro", 2400000, "M3 칩셋이 탑재된 노트북", 5),
                                new Product("AirPods Pro", 350000, "노이즈 캔슬링 무선 이어폰", 14)
                        )
                ));

        categories.add(
                new Category(
                        CategoryType.CLOTHING.getName(),
                        Arrays.asList(
                                new Product("후드 티셔츠", 59000, "편안한 오버핏 면 후드", 40000),
                                new Product("슬랙스 팬츠", 45000, "격식 있는 자리에 어울리는 바지", 3000),
                                new Product("롱 패딩", 299000, "겨울철 필수 방한 아우터", 2900),
                                new Product("캡 모자", 25000, "심플한 디자인의 볼캡", 100000)
                        )
                ));

        categories.add(
                new Category(
                        CategoryType.FOOD.getName(),
                        Arrays.asList(
                                new Product("불고기 버거", 6500, "한국식 소스의 진한 맛", 20),
                                new Product("치즈 피자", 18000, "고소한 모짜렐라가 듬뿍", 15),
                                new Product("아이스 아메리카노", 4500, "산미가 적고 고소한 원두", 200),
                                new Product("초코 조각 케이크", 7500, "진한 다크 초콜릿의 달콤함", 12)
                        )
                ));
    }

}
