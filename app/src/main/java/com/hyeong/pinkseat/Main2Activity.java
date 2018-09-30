package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hyeong.pinkseat.WebView.TrafficSubwayDetail;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    private Fragment searchFragment;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        searchFragment = new SearchFragment();
        setDefaulteFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void setDefaulteFragment(){

        /** * 화면에 보여지는 Fragment를 관리한다. * FragmentManager : Fragment를 바꾸거나 추가하는 객체 */
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        /** * R.id.container(activity_main.xml)에 띄우겠다. * 첫번째로 보여지는 Fragment는 searchFragment로 설정한다. */
        transaction.add(R.id.container, searchFragment);
        /** * Fragment의 변경사항을 반영시킨다. */
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);

        // [메뉴의 산모님 이름 변경]
        TextView menu_name = (TextView) findViewById(R.id.menu_name);
        String name;
        //로그인한 사용자의 정보를 받아옴
        Intent intent1 = getIntent();
        if(intent1.getStringExtra("name")==null){
            name = AutoLoginPreference.getName(this).toString(); //자동 로그인으로 저장된 사용자의 정보를 받음
        }else { name = intent1.getStringExtra("name");
        }
        menu_name.setText(name);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            // Handle the camera action
            getSupportActionBar().setTitle("메인");
            fragment = new SearchFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();


        }else if (id == R.id.nav_pregnant) {
            // Handle the camera action
            getSupportActionBar().setTitle("임산부석 선택");
            Intent intent = new Intent(this, TrafficSubwayDetail.class);
            intent.putExtra("OpenAPIKey","7173524e7073687a3930737a47506a");
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_usage) {
            // Handle the camera action
            getSupportActionBar().setTitle("어플 이용법");
           fragment = new UsageFragment();

//            //*
            //플래그먼트 중첩스택 없애기 용
            String fragmentTag = fragment.getClass().getSimpleName();
            getSupportFragmentManager().popBackStack(fragmentTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();


        } else if (id == R.id.nav_status) {
            getSupportActionBar().setTitle("나의 좌석 현황");
            fragment = new MySeatFragment();

//            //*
//            //플래그먼트 중첩 스택 없애기 용
            String fragmentTag = fragment.getClass().getSimpleName();
            getSupportFragmentManager().popBackStack(fragmentTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();


        } else if (id == R.id.nav_declare) {
            getSupportActionBar().setTitle("신고");
            fragment = new DeclareFragment();

//            //*
//            //플래그먼트 중첩 스택 없애기 용
            String fragmentTag = fragment.getClass().getSimpleName();
            getSupportFragmentManager().popBackStack(fragmentTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();


        } else if (id == R.id.nav_setting) {
            getSupportActionBar().setTitle("설정");
            fragment = new SettingFragment();

            //*
            //플래그먼트 중첩 스택 없애기 용
            String fragmentTag = fragment.getClass().getSimpleName();
            getSupportFragmentManager().popBackStack(fragmentTag, getSupportFragmentManager().POP_BACK_STACK_INCLUSIVE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(fragmentTag);
            transaction.commit();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}