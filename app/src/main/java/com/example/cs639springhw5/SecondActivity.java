package com.example.cs639springhw5;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.cs639springhw5.MainActivity.ANIMAL_LIST_EXTRA;

public class SecondActivity extends AppCompatActivity implements FirstFragment.FirstFragmentButtonClickHandler{

    public static final String ARG_ICONID = "iconID";
    public static final String ARG_NAME = "name";
    public static final String ARG_BIO = "bio";
    public static final String ARG_Button = "button_state";

    TextView tab0;
    TextView tab1;
    TextView tab2;
    TextView tab3;
    TextView currentTabView;
    FirstFragment currentFragment;

    FirstFragment tab0Fragment;
    FirstFragment tab1Fragment;
    FirstFragment tab2Fragment;
    FirstFragment tab3Fragment;
    ArrayList<AnimalDisplay> receivedList;

    int currentTabPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tab0 = findViewById(R.id.tab0);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);


        Intent intent = getIntent();
        receivedList = intent.getParcelableArrayListExtra(ANIMAL_LIST_EXTRA);

        currentTabView = tab0;
        currentTabView.setBackgroundColor(getResources().getColor(R.color.colorSelect));

        configureFragment();

        currentFragment = tab0Fragment;
        currentTabPosition = 0;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tab0Fragment).commit();
        addTabClickListeners();
        System.out.println(receivedList.size());
    }


    private  void configureFragment(){
        tab0Fragment = new FirstFragment();
        tab1Fragment = new FirstFragment();
        tab2Fragment = new FirstFragment();
        tab3Fragment = new FirstFragment();

        for ( int i = 0; i < receivedList.size(); i++) {

            Bundle args0 = new Bundle();
            if(receivedList.get(i).getIcon() !=0 && receivedList.get(i).isChecked)
                args0.putInt(ARG_ICONID, receivedList.get(i).getIcon());
            args0.putString(ARG_NAME, receivedList.get(i).getName());
            args0.putString(ARG_BIO, receivedList.get(i).getBio());
            if(i == 0)
                args0.putInt(ARG_Button,receivedList.size() == 1? 3:1);
            else if(i == receivedList.size()-1)
                args0.putInt(ARG_Button, 2);
            switch (i) {
                case 0:
                    tab0.setText(receivedList.get(i).getName());
                    tab0.setVisibility(View.VISIBLE);
                    tab0Fragment.setArguments(args0);
                    break;
                case 1:
                    tab1.setText(receivedList.get(i).getName());
                    tab1.setVisibility(View.VISIBLE);
                    tab1Fragment.setArguments(args0);
                    break;
                case 2:
                    tab2.setText(receivedList.get(i).getName());
                    tab2.setVisibility(View.VISIBLE);
                    tab2Fragment.setArguments(args0);
                    break;
                case 3:
                    tab3.setText(receivedList.get(i).getName());
                    tab3.setVisibility(View.VISIBLE);
                    tab3Fragment.setArguments(args0);
                    break;
            }

        }

    }

    private void addTabClickListeners() {

        View.OnClickListener tabListener = view -> {

            if(currentTabView != null)
                currentTabView.setBackgroundColor(Color.WHITE);
            currentTabView = currentTabView == null ? tab1 : (TextView)view;
            currentTabView .setBackgroundColor(getResources().getColor(R.color.colorSelect));


            int currentTabId = currentTabView == null ? 0 : currentTabView.getId();
            switch (currentTabId) {
                case R.id.tab0:
                    currentFragment = tab0Fragment;
                    currentTabPosition = 0;
                    break;
                case R.id.tab1:
                    currentFragment = tab1Fragment;
                    currentTabPosition = 1;
                    break;
                case R.id.tab2:
                    currentFragment = tab2Fragment;
                    currentTabPosition = 2;
                    break;
                case R.id.tab3:
                    currentFragment = tab3Fragment;
                    currentTabPosition = 3;
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();

        };
        //add above listener to tabs
        findViewById(R.id.tab0).setOnClickListener(tabListener);
        findViewById(R.id.tab1).setOnClickListener(tabListener);
        findViewById(R.id.tab2).setOnClickListener(tabListener);
        findViewById(R.id.tab3).setOnClickListener(tabListener);
    }

    @Override
    public void previousButtonClicked() {
        currentTabView.setBackgroundColor(Color.WHITE);
        currentTabPosition --;
        changeTabAndFragment();

    }

    @Override
    public void nextButtonClicked() {
        currentTabView.setBackgroundColor(Color.WHITE);
        currentTabPosition ++;
        changeTabAndFragment();
    }

    public void changeTabAndFragment(){

        switch (currentTabPosition) {
            case 0:
                currentFragment = tab0Fragment;
                currentTabView = tab0;
                break;
            case 1:
                currentFragment = tab1Fragment;
                currentTabView = tab1;
                break;
            case 2:
                currentFragment = tab2Fragment;
                currentTabView = tab2;
                break;
            case 3:
                currentFragment = tab3Fragment;
                currentTabView = tab3;
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
        currentTabView .setBackgroundColor(getResources().getColor(R.color.colorSelect));

    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(ANIMAL_LIST_EXTRA, (ArrayList<AnimalDisplay>) receivedList);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
