package com.project.android.tailor;

import androidx.fragment.app.Fragment;

public interface NavigationHelper {

    void navigateTo(Fragment fragment, boolean addToBackstack);
}
