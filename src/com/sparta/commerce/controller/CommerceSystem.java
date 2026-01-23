package com.sparta.commerce.controller;

import com.sparta.commerce.model.CartItem;
import com.sparta.commerce.model.Category;
import com.sparta.commerce.model.Customer;
import com.sparta.commerce.model.Product;
import com.sparta.commerce.model.type.CategoryType;
import com.sparta.commerce.model.type.PriceFilter;
import com.sparta.commerce.model.type.UserLevel;
import com.sparta.commerce.model.type.ViewMode;
import com.sparta.commerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CommerceSystem {
    // 고객은 1명밖에 없는 것이라고 전제!!
    private Customer customer = null;
    private List<Category> categories = new ArrayList<>();
    private Product selectedProduct = null;
    private final List<CartItem> cartItems = new ArrayList<>();

    private CategoryType currentCategory = CategoryType.MAIN;
    private ViewMode currentViewMode = ViewMode.MAIN;
    private PriceFilter currentPriceFilter = null;

    private final Scanner scanner = new Scanner(System.in);
    private boolean isFinish = false;


    /**
     * 리펙토링 단계
     * 1st : 처음에는 카테고리 타입별로 절차지향대로 구현을 해봄
     * 2nd : 입력과 출력이 스파게티 구조로 코드가 짜있는게 맘에들지 않아, 입력과 출력을 완전히 분리하는걸 목표로 리펙토링을 함.
     * 3rd : 필수과제를 완료하고 장바구니를 시도 하려고 하다 문득! 현 구조에서 확장이 너무 어렵다라는 생각이들어서 ViewType(화면) 별로 입·출력을 구현
     * 강의 때 배운 전략패턴을 써서 화면별로 만들수도 있겠다는 생각이 들었으나, 너무 많은 클래스로 오히려 더 복잡해질 것 같다는 생각이 들어서 포기!
     */

    private void goToHome() {
        selectedProduct = null;
        currentViewMode = ViewMode.MAIN;
    }

    public void start() {
        // 데이터 초기화
        categories = ProductRepository.getCategories();

        while (!isFinish) {
            // 출력
            renderScreen();
            // 입력
            handleMenu();
        }
        System.out.println("\n커머스 플랫폼을 종료합니다.");
    }


    /**
     * 출력 메서드(상태에 따른 화면만 출력)
     */
    private void renderScreen() {
        switch (currentViewMode) {
            case MAIN -> renderMainMenu();
            case CATEGORY_FILTER -> renderCategoryFilter();
            case PRODUCT_LIST -> renderProductList();
            case PRODUCT_DETAIL -> renderProductDetail();
            case CART -> renderCart();
            case CUSTOMER -> renderCustomer();
            default -> System.out.println("잘못된 입력입니다.");
        }
    }


    /**
     * 입력 메서드 (입력한 값의 상태륿 바꿔줌)
     */
    private void handleMenu() {
        System.out.print("입력 > ");
        String input = scanner.nextLine();

        // 각 화면마다 처리
        switch (currentViewMode) {
            case MAIN -> handleMain(input);
            case CATEGORY_FILTER -> handleCategoryFilter(input);
            case PRODUCT_LIST -> handleList(input);
            case PRODUCT_DETAIL -> handleListDetail(input);
            case CART -> handleCart(input);
            case CUSTOMER -> handleCustomer(input);
        }
    }


    private void renderMainMenu() {
        if (customer != null) {
            System.out.println("""
                    
                    =========================
                    [ 고객: %s | %s | %s | %,d원 ]
                    =========================
                    """.formatted(customer.getName(), customer.getMail(), customer.getLevel().toString(), customer.getOrderAmount()));
        }
        System.out.println("[ 실시간 커머스 플랫폼 - %s ]".formatted(currentCategory.getName()));
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            System.out.println("%d. %s".formatted(i + 1, c.getName()));
        }
        System.out.println("0. 종료           | 프로그램 종료");


        if (!cartItems.isEmpty()) {
            System.out.println("""
                    
                    [ 주문 관리 ]
                    4. 장바구니 확인    | 장바구니를 확인 후 주문합니다.
                    5. 주문 취소       | 진행중인 주문을 취소합니다.
                    """);
        }
    }

    private void renderCategoryFilter() {
        System.out.println("""
                
                [ %s - 필터 선택 ]
                1. 전체 상품 보기
                2. 가격대별 필터링 (100만원 이하)
                3. 가격대별 필터링 (100만원 초과)
                0. 뒤로가기
                """.formatted(currentCategory.getName()));
    }

    private void renderProductList() {
        System.out.println("[ %s 카테고리 ]".formatted(currentCategory.getName()));
        categories.stream()
                .filter(c -> Objects.equals(c.getName(), currentCategory.getName()))
                .findFirst()
                .ifPresent(category -> {
                    List<Product> filtered = category.getProducts().stream()
                            .filter(p -> currentPriceFilter.isMatch(p.getPrice()))
                            .toList();

                    if (filtered.isEmpty()) {
                        System.out.println("조회된 상품이 없습니다.");
                    } else {
                        for (int i = 0; i < filtered.size(); i++) {
                            Product p = filtered.get(i);
                            System.out.println("%d. %-15s | %,10d원 | %s"
                                    .formatted(i + 1, p.getName(), p.getPrice(), p.getDescription()));
                        }
                    }
                });
        System.out.println("0. 뒤로가기");
    }

    private void renderProductDetail() {
        System.out.println("""
                
                상품 상세 정보를 조회합니다.
                =========================
                [ 상품 상세 정보 ]
                상품명 : %s
                가 격 : %s
                설 명 : %s
                재 고 : %,d개
                =========================
                
                위 상품을 장바구니에 추가하시겠습니까?
                1. 확인        2. 취소
                """
                .formatted(
                        selectedProduct.getName(),
                        String.format("%,d원", selectedProduct.getPrice()),
                        selectedProduct.getDescription(),
                        selectedProduct.getQuantity()
                ));
    }

    private void renderCart() {
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }

        System.out.println("=========================");
        for (CartItem item : cartItems) {
            Product p = item.getProduct();
            System.out.println("- %-15s | %,10d원 | 수량: %d개".formatted(
                    p.getName(),
                    p.getPrice(),
                    item.getQuantity()
            ));
        }
        System.out.println("=========================");

        int orderAmount = cartItems.stream()
                .mapToInt(CartItem::getOrderAmount)
                .sum();
        System.out.println("""               
                [ 총 주문 금액 ]
                %,d원
                
                1. 주문 하기      2. 메인으로 돌아가기
                """.formatted(orderAmount));

    }

    private void renderCustomer() {
        System.out.println("\n고객 이메일을 입력해주세요.");
    }

    private void handleMain(final String input) {
        try {
            int inputNum = Integer.parseInt(input);
            // 종료하기
            if (inputNum == 0) {
                isFinish = true;
                return;
            }

            // 장바구니 확인
            if (inputNum == 4) {
                currentViewMode = ViewMode.CART;
                return;
            }

            if (inputNum == 5) {
                currentViewMode = ViewMode.MAIN;
                System.out.println("주문이 취소되었습니다.\n\n");
                cartItems.clear();
                return;
            }

            // 카테고리
            CategoryType.fromMenuNum(inputNum)
                    .ifPresent(type -> {
                        currentCategory = type;
                        currentViewMode = ViewMode.CATEGORY_FILTER;
                    });
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }

    private void handleCategoryFilter(String input) {
        try {
            int inputNum = Integer.parseInt(input);
            if (inputNum == 0) {
                currentViewMode = ViewMode.MAIN;
                return;
            }

            switch (inputNum) {
                case 1 -> currentPriceFilter = PriceFilter.ALL;
                case 2 -> currentPriceFilter = PriceFilter.UNDER_100;
                case 3 -> currentPriceFilter = PriceFilter.OVER_100;
                default -> {
                    System.out.println("잘못된 번호입니다.");
                    return;
                }
            }
            currentViewMode = ViewMode.PRODUCT_LIST;

        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }

    private void handleList(final String input) {
        try {
            int inputNum = Integer.parseInt(input);
            if (inputNum == 0) {
                currentViewMode = ViewMode.CATEGORY_FILTER;
                return;
            }

            categories.stream()
                    .filter(category -> Objects.equals(category.getName(), currentCategory.getName()))
                    .findFirst()
                    .ifPresent(category -> {
                        List<Product> products = category.getProducts();
                        if (inputNum <= products.size()) {
                            CommerceSystem.this.selectedProduct = products.get(inputNum - 1);
                            CommerceSystem.this.currentViewMode = ViewMode.PRODUCT_DETAIL;
                        } else {
                            System.out.println("\n해당하는 상품 번호가 없습니다.\n다시 입력해주세요!!!!! \n");
                        }
                    });
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }

    }

    private void handleListDetail(final String input) {
        try {
            int inputNum = Integer.parseInt(input);
            boolean isRunning = true;
            while (isRunning) {
                if (inputNum == 0 || inputNum == 2) {
                    selectedProduct = null;
                    currentViewMode = ViewMode.PRODUCT_LIST;
                    return;
                }

                if (inputNum == 1) {
                    try {
                        System.out.print("담을 수량을 입력해 주세요: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity <= selectedProduct.getQuantity()) {
                            cartItems.add(new CartItem(selectedProduct, quantity));
                            System.out.println("%s가 %d개 장바구니에 추가되었습니다.".formatted(selectedProduct.getName(), quantity));
                            goToHome();
                        } else {
                            System.out.println("재고가 부족합니다.!!");
                        }
                        isRunning = false;
                    } catch (NumberFormatException e) {
                        System.out.println("개수만 입력 가능합니다.");
                    }
                } else {
                    return;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }

    private void handleCart(final String input) {
        int inputNum = Integer.parseInt(input);
        try {
            if (inputNum == 1) {
                currentViewMode = ViewMode.CUSTOMER;
            }
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }

    private void handleCustomer(final String input) {
        int totalAmount = cartItems.stream()
                .mapToInt(CartItem::getOrderAmount)
                .sum();
        int previousAmount = (customer != null) ? customer.getOrderAmount() : 0;
        int totalAccumulatedAmount = previousAmount + totalAmount;

        UserLevel level = getUserLevel(totalAccumulatedAmount);
        int discountAmount = totalAccumulatedAmount * level.getDiscountRate() / 100;
        int finalPrice = totalAccumulatedAmount - discountAmount;

        System.out.println("""
                
                해당 유저는 %s 등급 이므로 %d%% 할인이 적용됩니다.
                할인 전 금액: %,d원
                할인 금액: -%,d원
                최종 결제 금액: %,d원
                
                주문 하시겠습니까?
                1. 주문 확정  2. 메인으로 돌아가기
                """.formatted(level.name(), level.getDiscountRate(), totalAccumulatedAmount, discountAmount, finalPrice));

        System.out.print("입력 > ");
        String finalChoice = scanner.nextLine();

        if ("1".equals(finalChoice)) {
            System.out.println("\n주문이 완료되었습니다!");
            customer = new Customer("박영수", input, level, totalAccumulatedAmount);
            System.out.println("최종 결제 금액: %,d원".formatted(finalPrice));

            for (CartItem cart : cartItems) {
                Product p = cart.getProduct();
                int orderQuantity = cart.getQuantity();
                int originQuantity = p.getQuantity();
                p.setQuantity(originQuantity - orderQuantity);
                System.out.println("%s 재고가 %d -> %d개로 업데이트되었습니다."
                        .formatted(p.getName(), originQuantity, p.getQuantity()));
            }
            cartItems.clear();
        }
        goToHome();

    }


    private UserLevel getUserLevel(int totalAmount) {
        if (totalAmount >= 2_000_000) return UserLevel.PLATINUM;
        if (totalAmount >= 1_000_000) return UserLevel.GOLD;
        if (totalAmount >= 500_000) return UserLevel.SILVER;
        return UserLevel.BRONZE;
    }


}
