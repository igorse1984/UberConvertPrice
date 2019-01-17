package ru.igorsharov.uberconvertprice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.igorsharov.uberconvertprice.R;
import ru.igorsharov.uberconvertprice.recycler_view.CustomModelCard;
import ru.igorsharov.uberconvertprice.recycler_view.CustomRecyclerViewAdapter;


public class RideFragment extends Fragment {

    private static final String BUNDLE_CONTENT = "bundle_content";


    public static RideFragment newInstance(final String content) {
        final RideFragment fragment = new RideFragment();
        final Bundle arguments = new Bundle();
        arguments.putString(BUNDLE_CONTENT, content);
        fragment.setArguments(arguments);
        Log.d("@@@", "RideFragment newInstance");
        return fragment;
    }

    private String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(BUNDLE_CONTENT)) {
            content = getArguments().getString(BUNDLE_CONTENT);
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride, container, false);

        initRecyclerView(view);

        return view;
    }


    private void initRecyclerView(View view) {

        List<CustomModelCard> customModelCardsList = new ArrayList<CustomModelCard>();
        int imgID;

        for (int i = 1; i <= 100; i++) {
            if (i % 2 == 0) {
                imgID = R.drawable.ic_menu_send;
            } else {
                imgID = R.drawable.ic_menu_gallery;
            }
            customModelCardsList.add(new CustomModelCard("Title " + i, "Description " + i, imgID));
        }

        RecyclerView recyclerView = view.findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter adapter = new CustomRecyclerViewAdapter(customModelCardsList);
        recyclerView.setAdapter(adapter);

    }
}
