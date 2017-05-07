package com.bitsofttech.biu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class Desktop_fragment extends Fragment {

    View view;
    GridView iconGrid;
    public FragmentResponse delegate=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FragmentResponse) {
            delegate = (FragmentResponse) context;
        } else {
            // Throw an error!
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_desktop_fragment,container,false);
        this.view=view;

        loadIcon();

        return view;
    }


    private void loadIcon(){

        iconGrid=(GridView) view.findViewById(R.id.iconGridView);

        String[] web = {
                "About BIU",
                "Message of Chairman",
                "Message of VC",
                "Board of Trustees",
                "Syndicate",
                "Academic Council",
                "Notice",
                "Result",
                "Contact"

        } ;
        int[] imageId = {
                R.drawable.about,
                R.drawable.message,
                R.drawable.message,
                R.drawable.trustees,
                R.drawable.syndicate,
                R.drawable.council,
                R.drawable.notice,
                R.drawable.result,
                R.drawable.contact,




        };

        IconGridAdapter adapter = new IconGridAdapter(getContext(), web, imageId);
        iconGrid.setAdapter(adapter);

        iconGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.grid_text);
                delegate.onFragmentLoadCompleted(String.valueOf(position));
            }
        });
    }
}
