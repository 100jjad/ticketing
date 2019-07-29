package com.example.ssoheyli.ticketing_newdesign.Ticket;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.lang.reflect.Field;
import java.util.ArrayList;

public class Adapter_viewPager_ticket extends FragmentPagerAdapter {

    Fragment[] mFragments;

    Tab[] mTabs;

    private static final Field sActiveField;

    static {
        Field f = null;
        try {
            Class<?> c = Class.forName("android.support.v4.app.FragmentManagerImpl");
            f = c.getDeclaredField("mActive");
            f.setAccessible(true);
        } catch (Exception e) {
        }

        sActiveField = f;
    }

    public Adapter_viewPager_ticket(FragmentManager fm, Tab[] tabs) {
        super(fm);
        mTabs = tabs;
        mFragments = new Fragment[mTabs.length];

        try {
            ArrayList<Fragment> mActive = (ArrayList<Fragment>) sActiveField.get(fm);
            if (mActive != null) {
                for (Fragment fragment : mActive) {
                    if (fragment instanceof TicketList_New_Fragment)
                        setFragment(Tab.New, fragment);
                    else if (fragment instanceof TicketList_Pending_Fragment)
                        setFragment(Tab.Pending, fragment);
                    else if (fragment instanceof TicketList_Finished_Fragment)
                        setFragment(Tab.Finished, fragment);
                    else if (fragment instanceof TicketList_Waiting_Fragment)
                        setFragment(Tab.Waiting, fragment);
                    else if (fragment instanceof TicketList_Responded_Fragment)
                        setFragment(Tab.Responded, fragment);
                    else if (fragment instanceof TicketList_Expired_Fragment)
                        setFragment(Tab.Expired, fragment);
                    else if (fragment instanceof TicketList_Suspended_Fragment)
                        setFragment(Tab.Suspended, fragment);
                    else if (fragment instanceof TicketList_All_Fragment)
                        setFragment(Tab.All, fragment);
                }
            }
        } catch (Exception e) {
        }
    }

    private void setFragment(Tab tab, Fragment f) {
        for (int i = 0; i < mTabs.length; i++)
            if (mTabs[i] == tab) {
                mFragments[i] = f;
                break;
            }
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                mFragments[position] = TicketList_New_Fragment.newInstance();
                break;
            case 1:
                mFragments[position] = TicketList_Pending_Fragment.newInstance();
                break;
            case 2:
                mFragments[position] = TicketList_Finished_Fragment.newInstance();
                break;
            case 3:
                mFragments[position] = TicketList_Waiting_Fragment.newInstance();
                break;
            case 4:
                mFragments[position] = TicketList_Responded_Fragment.newInstance();
                break;
            case 5:
                mFragments[position] = TicketList_Expired_Fragment.newInstance();
                break;
            case 6:
                mFragments[position] = TicketList_Suspended_Fragment.newInstance();
                break;
            case 7:
                mFragments[position] = TicketList_All_Fragment.newInstance();
                break;
        }
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return 8;
    }

    public enum Tab {

        New("New"),
        Pending("Pending"),
        Finished("Finished"),
        Waiting("Waiting for customer"),
        Responded("Responded"),
        Expired("Expired"),
        Suspended("Suspended"),
        All("All");

        private final String name;

        private Tab(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName != null) && name.equals(otherName);
        }

        public String toString() {
            return name;
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs[position].toString().toUpperCase();
    }
}
