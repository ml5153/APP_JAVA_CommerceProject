package com.sparta.commerce;

import com.sparta.commerce.data.CategoryType;
import com.sparta.commerce.data.Product;
import com.sparta.commerce.data.ProductRepository;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CommerceSystem {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!Objects.equals(choice, "0")) {
            System.out.println("[ 실시간 커머스 플랫폼 - 전자제품 ]");
            List<Product> productList = ProductRepository.getProducts(CategoryType.ELECTRONICS);

            for (int i = 0; i < productList.size(); i++) {
                Product p = productList.get(i);
                System.out.println("%d. %-15s | %,10d원 | %10s".formatted(i + 1, p.getName(), p.getPrice(), p.getDescription()));
            }

            System.out.println("0. 종료           | 프로그램 종료");
            System.out.print("입력 -> ");
            choice = scanner.nextLine();
        }
        System.out.println("\n커머스 플랫폼을 종료합니다.");
    }
}
