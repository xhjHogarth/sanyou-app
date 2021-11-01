package com.app.sanyou.view.viewpager;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.sanyou.R;

public class TabViewPagerActivity extends AppCompatActivity implements TabHost.TabContentFactory{

    private TabHost tabHost;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_tab_view_pager);

        fragmentManager = getFragmentManager();
        Fragment[] fragments = new Fragment[]{
                ScanFragment.getInstance(this,TabViewPagerActivity.this),
                ProjectFragment.getInstance(this),
                DataFragment.getInstance(this,fragmentManager),
                MineFragment.getInstance(this)
        };



        tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        int[] titleIDs = {
                R.string.scan,
                //R.string.collect,
                R.string.project,
                R.string.data,
                R.string.mine
        };

        int[] drawableIDs = {
                R.drawable.main_tab_icon_scan,
                //R.drawable.main_tab_icon_collect,
                R.drawable.main_tab_icon_project,
                R.drawable.main_tab_icon_history,
                R.drawable.main_tab_icon_mine
        };

        for (int i = 0; i < titleIDs.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.main_tab_layout,null,false);

            ImageView icon = view.findViewById(R.id.main_tab_icon);
            TextView title = view.findViewById(R.id.main_tab_txt);
            View tab = view.findViewById(R.id.tab_bg);

            icon.setImageResource(drawableIDs[i]);
            title.setText(titleIDs[i]);

            tab.setBackgroundColor(getResources().getColor(R.color.white));

            tabHost.addTab(
                    tabHost.newTabSpec(getString(titleIDs[i]))
                    .setIndicator(view)
                    .setContent(this)
            );
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return titleIDs.length;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(tabHost != null){
                    tabHost.setCurrentTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(tabHost != null && viewPager != null){
                    int position = tabHost.getCurrentTab();
                    viewPager.setCurrentItem(position);
                }
            }
        });

        tabHost.setCurrentTab(titleIDs.length-1);
        viewPager.setCurrentItem(titleIDs.length-1);

    }


    @Override
    public View createTabContent(String s) {
        View view = new View(this);
        view.setMinimumHeight(0);
        view.setMinimumWidth(0);
        return view;
    }
}