package com.cococompany.android.aq.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.CategoryListItemAdapter;
import com.cococompany.android.aq.adapters.GridViewAdapter;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.ImageItem;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.CategoryService;
import com.cococompany.android.aq.utils.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by alexandrmyagkiy on 06.12.16.
 */

public class CategoriesChooseFragment extends DialogFragment {

    private static final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_dialog_categories, null);

        TabHost host = (TabHost)view.findViewById(R.id.categories_tab_host);
        host.setup();

        //Вкладка №1
        TabHost.TabSpec spec = host.newTabSpec("Categories");
        spec.setContent(R.id.categories_choose_category);
        spec.setIndicator("Categories");
        host.addTab(spec);

        //Вкладка №2
        spec = host.newTabSpec("Users");
        spec.setContent(R.id.categories_choose_user);
        spec.setIndicator("Users");
        host.addTab(spec);

        //Додавання gridView до першої вкладки
        GridView gridView = (GridView) view.findViewById(R.id.categories_grid_view);
        GridViewAdapter gridAdapter = null;
        try {
            gridAdapter = new GridViewAdapter(getContext(), R.layout.category_grid_item, getRealData());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                ImageItem item = (ImageItem) parent.getItemAtPosition(position);
//
//                //Create intent
//                Intent intent = new Intent(getActivity(), DetailsActivity.class);
//                intent.putExtra("title", item.getTitle());
//                intent.putExtra("image", item.getImage());
//
//                //Start details activity
//                startActivity(intent);
            }
        });

        // створюємо адаптер для списку користувачів
        ArrayList<User> users = fillUsersData();
        CategoryListItemAdapter listItemAdapter = new CategoryListItemAdapter(getContext(), users);

        // настраиваем список
        ListView lvMain = (ListView) view.findViewById(R.id.categories_list);
        lvMain.setAdapter(listItemAdapter);

//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.categories_tab_layout);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        TabHost tabs = (TabHost) view.findViewById(R.id.categories_tab_host);
//        TabHost.TabSpec tabPage1 = tabs.newTabSpec("one");
////        Intent intent = new Intent(getActivity(), CategoriesTab1Fragment.class);
////        tabPage1.setContent(intent);
////        tabPage1.setContent(R.id.categories_choose_category);
//        tabPage1.setIndicator("Tab 1");
//
//        TabHost.TabSpec tabPage2 = tabs.newTabSpec("two");
////        tabPage2.setContent(R.id.categories_choose_user);
//        tabPage2.setIndicator("Tab 2");
//
//        tabs.addTab(tabPage1);
//        tabs.addTab(tabPage2);

//        ViewPager viewPager = (ViewPager) view.findViewById(R.id.categories_pager);
//        CategoriesTabsPagerAdapter pagerAdapter = new CategoriesTabsPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);

//        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.categories_pager); //view.findViewById(R.id.categories_pager);
//        CategoriesTabsPagerAdapter adapter = new CategoriesTabsPagerAdapter(getActivity().getSupportFragmentManager().findFragmentByTag("Categories choose").getFragmentManager(), tabLayout.getTabCount());
//        CategoriesTabsPagerAdapter adapter = new CategoriesTabsPagerAdapter(this, tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        builder.setView(view)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoriesChooseFragment.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    // Підготовка випадкових тестових даних для gridview
    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.image_ids);
        for (int i = 0; i < imgs.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(i, -1));
            imageItems.add(new ImageItem(bitmap, "Image#" + i));
        }
        return imageItems;
    }

    // Підготовка реальних даних для gridview
    private ArrayList<ImageItem> getRealData() throws ExecutionException, InterruptedException {
        List<Category> categories = new CategoryService().getCategories();

        final ArrayList<ImageItem> imageItems = new ArrayList<>();
        TypedArray imgs = getResources().obtainTypedArray(R.array.category_images);
        Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), imgs.getResourceId(0, -1));

        for (int i = 0; i < categories.size(); i++) {
            String imageUrl = categories.get(i).getImage();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                DownloadImageTask imageTask = new DownloadImageTask();
                Bitmap bitmap = imageTask.execute(projectBaseUrl + "/" + imageUrl).get();
                imageItems.add(new ImageItem(bitmap, categories.get(i).getName()));
            } else {
                imageItems.add(new ImageItem(defaultBitmap, categories.get(i).getName()));
            }
        }
        return imageItems;
    }

    //Підготовка реальних даних для ListView
    private ArrayList<User> fillUsersData() {
        ArrayList<User> users = new UserService().getActiveUsers();
        return users;
    }

    //Для отримання зображення за його URL
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(projectBaseUrl + "/" + src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
    }
}
