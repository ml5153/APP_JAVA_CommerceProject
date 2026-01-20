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
    private CategoryType currentCategoryType = CategoryType.MAIN;
    List<Category> categories = new ArrayList<>();
    boolean isFinish = false;

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String menu;
        categories = ProductRepository.getCategories();

        while (!isFinish) {
            switch (currentCategoryType) {
                case MAIN -> landingMainMenu();
                case ELECTRONICS, CLOTHING, FOOD -> landingProductList();
                default -> System.out.println("잘못된 입력입니다.");
            }

            System.out.print("입력 > ");
            menu = scanner.nextLine();
            handleMenu(menu);
        }
        System.out.println("\n커머스 플랫폼을 종료합니다.");
    }


    private void handleMenu(String menu) {
        try {
            int inputNumber = Integer.parseInt(menu);
            if (inputNumber == 0) {
                if (currentCategoryType != CategoryType.MAIN) {
                    currentCategoryType = CategoryType.MAIN;
                } else {
                    isFinish = true;
                }
                return;
            }

            if (currentCategoryType == CategoryType.MAIN) {
                CategoryType.fromMenuNum(inputNumber)
                        .ifPresent(type -> currentCategoryType = type);
            } else {
                System.out.println("상품 상세 정보를 조회합니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("번호만 입력 가능합니다.");
        }
    }


    private void landingMainMenu() {
        System.out.println("[ 실시간 커머스 플랫폼 - %s ]".formatted(currentCategoryType.getName()));
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            System.out.println("%d. %s".formatted(i + 1, c.getName()));
        }
        System.out.println("0. 종료           | 프로그램 종료");
    }


    private void landingProductList() {
        System.out.println("[ %s 카테고리 ]".formatted(currentCategoryType.getName()));
        for (Category category : categories) {
            if (Objects.equals(category.getName(), currentCategoryType.getName())) {
                for (int i = 0; i < category.getProducts().size(); i++) {
                    Product product = category.getProducts().get(i);
                    System.out.println("%d. %-15s | %,10d원 | %10s".formatted(i + 1, product.getName(), product.getPrice(), product.getDescription()));
                }
            }
        }
        System.out.println("0. 뒤로가기");
    }


}
