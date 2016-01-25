package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
// THE ABUT PAGE FRAGMENT
public class AboutActivity extends Fragment {
    //create view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // select the content
        View rootView = inflater.inflate(R.layout.activity_about, container, false);
        // select image elements and add image
       ImageView juay = (ImageView) rootView.findViewById(R.id.imageView3_juray);
       ImageView amir = (ImageView) rootView.findViewById(R.id.fotoAmir);
       ImageView rohini = (ImageView) rootView.findViewById(R.id.imageView);
       ImageView vasilious = (ImageView) rootView.findViewById(R.id.imageView2_vasi);
        Picasso.with(getActivity()).load(R.drawable.juraj)    .into(juay);
        Picasso.with(getActivity()).load(R.drawable.amir)    .into(amir);
        Picasso.with(getActivity()).load(R.drawable.rohini)    .into(rohini);
        Picasso.with(getActivity()).load(R.drawable.vasilis)    .into(vasilious);
        // show the view
        return rootView;
    }
}