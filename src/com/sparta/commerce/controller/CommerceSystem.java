package com.sparta.commerce.controller;

import com.sparta.commerce.model.Category;
import com.sparta.commerce.model.Product;
import com.sparta.commerce.model.type.CategoryType;
import com.sparta.commerce.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CommerceSystem {
    private CategoryType currentCategory = CategoryType.MAIN;
    private List<Category> categories = new ArrayList<>();
    private boolean isFinish = false;
    private Product selectedProduct;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String menu;
        categories = ProductRepository.getCategories();

        while (!isFinish) {
            // 출력
            renderScreen();

            // 입력
            System.out.print("입력 > ");
            menu = scanner.nextLine();
            handleMenu(menu);
        }
        System.out.println("\n커머스 플랫폼을 종료합니다.");
    }


    /**
     * 출력 메서드(상태에 따른 화면만 출력)
     */
    private void renderScreen() {
        if (selectedProduct != null) {
            landingProductDetail();
            return;
        }

        switch (currentCategory) {
            case MAIN -> landingMainMenu();
            case ELECTRONICS, CLOTHING, FOOD -> landingProductList();
            default -> System.out.println("잘못된 입력입니다.");
        }

    }


    /**
     * 입력 메서드 (입력한 값의 상태륿 바꿔줌)
     *
     * @param menu : 입력한 메뉴값
     */
    private void handleMenu(String menu) {
        try {
            // 상세화면 처리
            if (selectedProduct != null) {
                selectedProduct = null;
                return;
            }

            // 입력값 0일 때 처리
            int inputNumber = Integer.parseInt(menu);
            if (inputNumber == 0) {
                if (currentCategory != CategoryType.MAIN) {
                    currentCategory = CategoryType.MAIN;
                } else {
                    isFinish = true;
                }
                return;
            }

            // 각 메뉴별 처리
            if (currentCategory == CategoryType.MAIN) {
                CategoryType.fromMenuNum(inputNumber)
                        .ifPresent(type -> currentCategory = type);
            } else {
                categories.stream()
                        .filter(category -> Objects.equals(category.getName(), currentCategory.getName()))
                        .findFirst()
                        .ifPresent(category -> {
                            List<Product> products = category.getProducts();
                            if (inputNumber > 0 && inputNumber <= products.size()) {
                                CommerceSystem.this.selectedProduct = products.get(inputNumber - 1);
                            } else {
                                System.out.println("\n해당하는 상품 번호가 없습니다.\n다시 입력해주세요!!!!! \n");
                            }
                        });
            }
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }


    private void landingMainMenu() {
        System.out.println("[ 실시간 커머스 플랫폼 - %s ]".formatted(currentCategory.getName()));
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            System.out.println("%d. %s".formatted(i + 1, c.getName()));
        }
        System.out.println("0. 종료           | 프로그램 종료");
    }


    private void landingProductList() {
        System.out.println("[ %s 카테고리 ]".formatted(currentCategory.getName()));
        for (Category category : categories) {
            if (Objects.equals(category.getName(), currentCategory.getName())) {
                for (int i = 0; i < category.getProducts().size(); i++) {
                    Product product = category.getProducts().get(i);
                    System.out.println("%d. %-15s | %,10d원 | %10s".formatted(i + 1, product.getName(), product.getPrice(), product.getDescription()));
                }
            }
        }
        System.out.println("0. 뒤로가기");
    }

    private void landingProductDetail() {
        System.out.println("""
                
                상품 상세 정보를 조회합니다.
                =========================
                [ 상품 상세 정보 ]
                상품명 : %s
                가 격 : %s
                설 명 : %s
                재 고 : %,d개
                =========================               
                엔터를 누르면 목록으로 돌아갑니다.
                """
                .formatted(
                        selectedProduct.getName(),
                        String.format("%,d원", selectedProduct.getPrice()),
                        selectedProduct.getDescription(),
                        selectedProduct.getQuantity()
                ));
    }


}
