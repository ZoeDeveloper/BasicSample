package com.example.android.persistence;

import android.arch.lifecycle.LifecycleActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.android.persistence.databinding.ActivityListItemBinding;

public class ShowItemActivity extends LifecycleActivity {

    private ActivityListItemBinding mBinding;


    @Nullable
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_item);
        //return mBinding.getRoot();
        //ViewModelProviders.of(this, factory).get(ProductViewModel.class);
    }
}

/*
http://blog.iamsuleiman.com/android-architecture-components-tutorial-room-livedata-viewmodel/
 */
/* OLD WAY
mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
 */