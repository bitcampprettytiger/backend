package com.example.bitcamptiger.menu;


import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.menu.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MenuTestDataInitializer implements CommandLineRunner {
    //추천메뉴 탑5 표출을 위한 테스트 데이터 입력 하기
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void run(String... args) throws Exception {

        // 조회수를 설정하여 테스트 데이터 저장
        Menu menu1 = new Menu();
        menu1.setMenuName("땡초우동");
        menu1.setMenuViews(66);

        Menu menu2 = new Menu();
        menu2.setMenuName("골뱅이");
        menu2.setMenuViews(35);

        Menu menu3 = new Menu();
        menu3.setMenuName("떡볶이");
        menu3.setMenuViews(70);

        Menu menu4 = new Menu();
        menu4.setMenuName("타코야끼");
        menu4.setMenuViews(88);

        Menu menu5 = new Menu();
        menu5.setMenuName("회무침");
        menu5.setMenuViews(29);

        Menu menu6 = new Menu();
        menu6.setMenuName("닭발");
        menu6.setMenuViews(93);

        Menu menu7 = new Menu();
        menu7.setMenuName("오뎅");
        menu7.setMenuViews(54);

        Menu menu8 = new Menu();
        menu8.setMenuName("치킨");
        menu8.setMenuViews(25);

        menuRepository.save(menu1);
        menuRepository.save(menu2);
        menuRepository.save(menu3);
        menuRepository.save(menu4);
        menuRepository.save(menu5);
        menuRepository.save(menu6);
        menuRepository.save(menu7);
        menuRepository.save(menu8);


        //임의로 쓴 정보 저장
        menuRepository.saveAll(Arrays.asList(menu1, menu2, menu3, menu4, menu5, menu6, menu7, menu8));

        // 로그로 탑 5 메뉴 조회 및 출력
        List<Menu> top5Menus = menuRepository.findTop5ByOrderByMenuViewsDesc();
        System.out.println("[ Top 5 Menus ]");
        for (Menu menu : top5Menus) {
            System.out.println("메뉴이름: " + menu.getMenuName() + " ---> 조회수: " + menu.getMenuViews());
        }
    }


}
