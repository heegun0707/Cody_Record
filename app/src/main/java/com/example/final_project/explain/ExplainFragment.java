package com.example.final_project.explain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.final_project.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ExplainFragment extends Fragment {

    ViewPager viewPager;
    CircleIndicator circleIndicator;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_explain, container, false);

        circleIndicator = root.findViewById(R.id.circleindicator);
        viewPager = root.findViewById(R.id.view_pager);

        // 저장해놓을 프래그먼트 개수
        viewPager.setOffscreenPageLimit(3);
        // 상태저장 기능 제거
        viewPager.setSaveEnabled(false);

        //adapter로 뷰페이저 연결
        MyPagerAdapter adapter = new MyPagerAdapter(getFragmentManager());
        ExplainOneFragment explainOneFragment = new ExplainOneFragment();
        adapter.addItem(explainOneFragment);

        ExplainTwoFragment explainTwoFragment = new ExplainTwoFragment();
        adapter.addItem(explainTwoFragment);

        ExplainThreeFragment explainThreeFragment = new ExplainThreeFragment();
        adapter.addItem(explainThreeFragment);

        ExplainFourFragment explainFourFragment = new ExplainFourFragment();
        adapter.addItem(explainFourFragment);

        viewPager.setAdapter(adapter);
        // 뷰페이저에 indicator 연결
        circleIndicator.setViewPager(viewPager);

        return root;
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }
}
