package com.example.cs639springhw5;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.cs639springhw5.SecondActivity.ARG_BIO;
import static com.example.cs639springhw5.SecondActivity.ARG_Button;
import static com.example.cs639springhw5.SecondActivity.ARG_ICONID;
import static com.example.cs639springhw5.SecondActivity.ARG_NAME;

public class FirstFragment extends Fragment {

    interface FirstFragmentButtonClickHandler{
        void previousButtonClicked();
        void nextButtonClicked();
    }

    ImageView mAnimalIcon;
    TextView mAnimalName;
    TextView mAnimalBio;
    Button mPreviousButton;
    Button mNextButton;
    FirstFragmentButtonClickHandler mClickHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mClickHandler = (FirstFragmentButtonClickHandler) context;
        }catch(ClassCastException e){
            new ClassCastException("the activity that  this fragment is attached to must be a FirstFragmentButtonClickHandler");

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mAnimalIcon = view.findViewById(R.id.icon_first_fragment);
        mAnimalName = view.findViewById(R.id.name_first_fragment);
        mAnimalBio = view.findViewById(R.id.bio_first_fragment);
        mPreviousButton = view.findViewById(R.id.previousButton);
        mNextButton = view.findViewById(R.id.nextButton);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       mPreviousButton.setOnClickListener(v -> {
           mClickHandler.previousButtonClicked();
       });
       mNextButton.setOnClickListener(v -> {
           mClickHandler.nextButtonClicked();
       });
       populateView();
    }

    private void populateView(){
        Bundle args = getArguments();
        if (args != null)
        {
            if(args.getInt(ARG_ICONID)!=0)
                mAnimalIcon.setImageResource(args.getInt(ARG_ICONID));

            mAnimalName.setText(args.getString(ARG_NAME));
            mAnimalBio.setText(args.getString(ARG_BIO));

            if(args.getInt(ARG_Button)==1)
                mPreviousButton.setVisibility(View.GONE);
            else if(args.getInt(ARG_Button)==2)
                mNextButton.setVisibility(View.GONE);
            else if(args.getInt(ARG_Button)==3){
                mPreviousButton.setVisibility(View.GONE);
                mNextButton.setVisibility(View.GONE);
            }
        }

    }

}
