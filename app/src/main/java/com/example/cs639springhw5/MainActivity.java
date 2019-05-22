package com.example.cs639springhw5;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    AnimalRecyclerViewAdapter mAdapter;
    EditText mNewNameText;
    EditText mNewBioText;
    Button addTabButton;
    Button clearAllButton;
    Button createTabsButton;
    ImageView selectedAnimalIcon;
    int selectedAnimalSrc;
    List<AnimalDisplay> animalDisplays;
    public static final String ANIMAL_LIST_EXTRA = "AnimalList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra(ANIMAL_LIST_EXTRA) != null) {
            animalDisplays = intent.getParcelableArrayListExtra(ANIMAL_LIST_EXTRA);
        } else {
            animalDisplays = new ArrayList<>(4);
            //Uncomment the following items for testing
//          animalDisplays.add(new AnimalDisplay(R.drawable.bird, getString(R.string.bird_title), getString(R.string.bird_description)));
//          animalDisplays.add(new AnimalDisplay(R.drawable.cat, getString(R.string.cat_title), getString(R.string.cat_description)));
//          animalDisplays.add(new AnimalDisplay(R.drawable.dog, getString(R.string.dog_title), getString(R.string.dog_description)));
        }
        mNewNameText = findViewById(R.id.nameEditText);
        mNewBioText = findViewById(R.id.biographyEditText);
        addTabButton = findViewById(R.id.addTabButton);
        clearAllButton = findViewById((R.id.clearAllButton));
        createTabsButton =findViewById(R.id.createTabsButton);

        configureRecyclerView();
        addAnimalIconClickListeners();
        setAddTabOnClickListener();
        setClearAllButtonOnClickListener();
        setCreateTabsButtonOnClickListener();
    }



    public void configureRecyclerView() {

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new AnimalRecyclerViewAdapter(this, animalDisplays, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
                System.out.println("Clicked"+position);
                AnimalDisplay display = mAdapter.getItem(position);

                switch (v.getId()) {
                    case R.id.checkbox:
                        changeImageIconDisplay(display);
                        break;
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void changeImageIconDisplay(AnimalDisplay display){
        display.isChecked = !display.isChecked;
        System.out.println(display.isChecked);
        System.out.println(display.getName());
        mAdapter.notifyDataSetChanged();
    }


    private void addAnimalIconClickListeners() {

        View.OnClickListener iconListener = view -> {

            if(selectedAnimalIcon != null)
                selectedAnimalIcon.setBackgroundColor(Color.WHITE);

            selectedAnimalIcon = selectedAnimalIcon == view ? null : (ImageView)view;

            if(selectedAnimalIcon != null)
                selectedAnimalIcon.setBackgroundColor(getResources().getColor(R.color.colorSelect));

            int selectedImageId = selectedAnimalIcon == null ? 0 : selectedAnimalIcon.getId();
            switch (selectedImageId) {
                case R.id.birdIcon:
                    selectedAnimalSrc = R.drawable.bird;
                    break;
                case R.id.catIcon:
                    selectedAnimalSrc = R.drawable.cat;
                    break;
                case R.id.dogIcon:
                    selectedAnimalSrc = R.drawable.dog;
                    break;
                case R.id.deerIcon:
                    selectedAnimalSrc = R.drawable.deer;
                    break;
                case R.id.foxIcon:
                    selectedAnimalSrc = R.drawable.fox;
                    break;
                case 0:
                    selectedAnimalSrc = 0;
                    break;

            }

        };
        //add above listener to icons
        findViewById(R.id.birdIcon).setOnClickListener(iconListener);
        findViewById(R.id.catIcon).setOnClickListener(iconListener);
        findViewById(R.id.dogIcon).setOnClickListener(iconListener);
        findViewById(R.id.deerIcon).setOnClickListener(iconListener);
        findViewById(R.id.foxIcon).setOnClickListener(iconListener);
    }

    public void setAddTabOnClickListener(){
        addTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areInputsFilled()) return;

                if(animalDisplays.size()>=4){
                    Toast.makeText(MainActivity.this, R.string.no_more_than_4_item,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String nameInput = mNewNameText.getText().toString().trim();
                String bioInput = mNewBioText.getText().toString().trim();

                animalDisplays.add(new AnimalDisplay(selectedAnimalSrc, nameInput, bioInput));

                if(selectedAnimalIcon != null){
                    selectedAnimalIcon.setBackgroundColor(Color.WHITE);
                    selectedAnimalIcon= null;
                    selectedAnimalSrc = 0;}
                mNewNameText.setText("");
                mNewBioText.setText("");
            }
        });
    }

    private void setClearAllButtonOnClickListener(){
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animalDisplays.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setCreateTabsButtonOnClickListener(){

        createTabsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(animalDisplays.size() >=1){
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                    i.putParcelableArrayListExtra(ANIMAL_LIST_EXTRA, (ArrayList<AnimalDisplay>) animalDisplays);
                    startActivity(i);
                }
                else
                    Toast.makeText(MainActivity.this, R.string.at_least_1_item,
                            Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean areInputsFilled() {
        if (mNewNameText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.name_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (mNewBioText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.bio_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
