/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.persistence;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.persistence.databinding.ListFragmentBinding;
import com.example.android.persistence.db.entity.ProductEntity;
import com.example.android.persistence.model.Product;
import com.example.android.persistence.ui.ProductAdapter;
import com.example.android.persistence.ui.ProductClickCallback;
import com.example.android.persistence.viewmodel.ProductListViewModel;

import java.util.List;

/**
 * This is the first view - the list view - where the button is 
 */
public class ProductListFragment extends LifecycleFragment {

    public static final String TAG = "ProductListViewModel";

    private ProductAdapter mProductAdapter;

    private ListFragmentBinding mBinding;
    private ProductListViewModel proViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);

        mBinding.setCallbackDB(mProductClickCallback);

        mProductAdapter = new ProductAdapter(mProductClickCallback);
        mBinding.productsList.setAdapter(mProductAdapter);



        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProductListViewModel viewModel =
                ViewModelProviders.of(this).get(ProductListViewModel.class);
        this.proViewModel = viewModel;
        subscribeUi(viewModel);
    }


    int countInDB = 0;
    private void subscribeUi(ProductListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProducts().observe(this, new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(@Nullable List<ProductEntity> myProducts) {
                if (myProducts != null) {
                    mBinding.setIsLoading(false);
                    mProductAdapter.setProductList(myProducts);
                    countInDB = myProducts.size();
                } else {
                    mBinding.setIsLoading(true);
                }
            }
        });
    }

    private void ShowToast(int count) {
        Context context = getContext();
        CharSequence text = "Current count is " + count;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private final ProductClickCallback mProductClickCallback = new ProductClickCallback() {
        @Override
        public void onClick(Product product) {
            ShowToast(0);
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(product);
            }
        }

        @Override
        public void onClickAddDB(View v) {
            System.out.println("current count " + countInDB);
            // touching the DB at this point.
            proViewModel.addRandomProduct();
            ShowToast(countInDB);
        }


        @Override
        public void onClickDeleteDB(View v) {
            System.out.println("current count " + countInDB);
            // touching the DB at this point.
            proViewModel.deleteFirstProduct();
            ShowToast(countInDB);
        }

        @Override
        public void onClickViewDB(View view) {
            Intent intent = new Intent(getActivity(), ShowItemActivity.class);
            startActivity(intent);
        }
    };
}
