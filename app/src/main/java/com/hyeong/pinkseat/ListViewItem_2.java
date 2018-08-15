package com.hyeong.pinkseat;

/**
 * Created by JY on 2018-08-07.
 */

//(8번 화면의 커스텀 리스트뷰를 위한 '리스트뷰 아이템 설정' 클래스)
public class ListViewItem_2 {

    private String text_date, text_train_time, text_plat_num, text_seat_num, text_timer;

    public void setText(String text1, String text2, String text3, String text4, String text5) {
        this.text_date = text1;
        this.text_train_time = text2;
        this.text_plat_num = text3;
        this.text_seat_num = text4;
        this.text_timer = text5;
    }

    public String getText1() {
        return this.text_date;
    }

    public String getText2() {
        return this.text_train_time;
    }

    public String getText3() {
        return this.text_plat_num;
    }

    public String getText4() {
        return this.text_seat_num;
    }

    public String getText5() {
        return this.text_timer;
    }

}
