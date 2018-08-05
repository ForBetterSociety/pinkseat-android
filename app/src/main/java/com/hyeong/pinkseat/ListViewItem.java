package com.hyeong.pinkseat;

/**
 * Created by JY on 2018-08-05.
 */

//(6번 화면의 커스텀 리스트뷰를 위한 '리스트뷰 아이템 설정' 클래스)
public class ListViewItem {

    private String text1;
    private String text2;

    public void setText(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return this.text1;
    }

    public String getText2() {
        return this.text2;
    }

}
