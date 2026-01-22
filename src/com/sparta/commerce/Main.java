package com.sparta.commerce;


import com.sparta.commerce.controller.CommerceSystem;

public class Main {
    public static void main(String[] args) {
        CommerceSystem system = new CommerceSystem();
        system.start();
    }

    /** TODO 자체평가
     * 이번 커머스 프로젝트의 설계단계에서 요구사항 큰 단락으로 분석 한 이후 플로우차트/나만의 차트를 그렸습니다.
     또한 구현단계에서 입·출력을 분리하여 구현하였으며, 최대한 각 객체는 각자 역할 수 있게 레포지토리 및 클래스를 분리하였습니다.
     그러나 결과론적으로 결국 완벽 분리는 하지못했습니다.. 메뉴의 Depth가 많아지니 현 설계가 더 불편하다는 것을 깨달았습니다..
     또한 커머스시스템이 하는역할이 점점많아져 객체지향적으로도 아쉬운 부분이 듭니다.

     아직도 갈길이 멀며... 계속 클린코드를 구현할 수 있게 스스로를 압박하고 있습니다.
     다음 프로젝트때는 더 좋은코드로 구현할 수 있기를!
     */
}