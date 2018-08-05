package com.hyeong.pinkseat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by JY on 2018-08-05.
 */

// (6번 화면의 커스텀 리스트뷰를 위한 '레이아웃' 클래스)
public class CheckableLinearLayout extends LinearLayout implements Checkable {

    // 만약 CheckBox가 아닌 View를 추가한다면 아래의 변수 사용 가능.
    // private boolean mIsChecked ;
    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        // mIsChecked = false ;
    }

    //현재 Checked 상태를 리턴
    @Override
    public boolean isChecked() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox1) ;

        return cb.isChecked() ;
        // return mIsChecked ;
    }

    //현재 Checked 상태를 바꿈. (UI에 반영)
    @Override
    public void toggle() {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox1) ;

        setChecked(cb.isChecked() ? false : true) ;
        // setChecked(mIsChecked ? false : true) ;
    }

    //Checked 상태를 checked 변수대로 설정
    @Override
    public void setChecked(boolean checked) {
        CheckBox cb = (CheckBox) findViewById(R.id.checkbox1) ;

        if (cb.isChecked() != checked) {
            cb.setChecked(checked) ;
        }

        // CheckBox 가 아닌 View의 상태 변경.
    }

}
