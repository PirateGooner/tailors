package com.project.android.tailor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDesignsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DesignsRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DesignsViewModel designsViewModel;
    private Observer<QuerySnapshot> observer;

    public NewDesignsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_designs, container, false);
    }

    public void onViewCreated(final View view, final Bundle savedInstanceState){


        designsViewModel= ViewModelProviders.of(requireActivity()).get(DesignsViewModel.class);

        recyclerView=requireActivity().findViewById(R.id.recyclerView_newDesigns);

        layoutManager=new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter=new DesignsRecyclerAdapter(requireActivity(),null,designsViewModel);
        recyclerView.setAdapter(mAdapter);

        observer=new Observer<QuerySnapshot> (){
            public void onChanged(QuerySnapshot snapshot){
                   // Log.d("MyActivity","On changed called");
                    if(snapshot!=null) {
                        for (DocumentChange dc : snapshot.getDocumentChanges()) {
                            int position = 0;
                            switch (dc.getType()) {

                                case ADDED:

                                    mAdapter.dataList.add(0, dc.getDocument().toObject(DesignsModel.class));
                                    mAdapter.notifyItemInserted(0);
                                    break;


                                case MODIFIED:
                                    if (!(mAdapter.dataList.isEmpty())) {
                                        for (DesignsModel model : mAdapter.dataList) {
                                            if (model.getDesignID() == dc.getDocument().toObject(DesignsModel.class).getDesignID()) {
                                                position = mAdapter.dataList.indexOf(model);
                                                mAdapter.dataList.set(position, dc.getDocument().toObject(DesignsModel.class));
                                                mAdapter.notifyItemChanged(position);
                                                break;
                                            }
                                        }
                                    }
                                    break;

                                case REMOVED:
                                    if (!(mAdapter.dataList.isEmpty())) {
                                        for (DesignsModel model : mAdapter.dataList) {
                                            if (model.getDesignID() == dc.getDocument().toObject(DesignsModel.class).getDesignID()) {
                                                position = mAdapter.dataList.indexOf(model);
                                                mAdapter.dataList.remove(position);
                                                mAdapter.notifyItemRemoved(position);
                                                break;
                                            }
                                        }
                                    }
                                    break;

                            }
                        }
                    }
            }
        };
        designsViewModel.setData();
        designsViewModel.allDesignsSnapshot.observe(getViewLifecycleOwner(),observer);
    }

    public void onPause(){
        super.onPause();
        designsViewModel.allDesignsSnapshot.removeObserver(observer);
        designsViewModel.setNull();
    }

}
