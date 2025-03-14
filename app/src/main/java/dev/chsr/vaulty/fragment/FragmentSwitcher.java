package dev.chsr.vaulty.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.transition.TransitionInflater;

import dev.chsr.vaulty.R;

public class FragmentSwitcher {
    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment) {
        Fragment currentFragment = fragmentManager.findFragmentByTag("CURRENT");

        if (currentFragment != null && currentFragment.getClass() == fragment.getClass())
            return;
        if (currentFragment instanceof NewPasswordFragment) {
            TransitionInflater inflater = TransitionInflater.from(currentFragment.requireContext());
            currentFragment.setExitTransition(inflater.inflateTransition(R.transition.slide_right));
            if (fragment instanceof SettingsFragment)
                currentFragment.setExitTransition(inflater.inflateTransition(R.transition.slide_left));
        } else if (currentFragment instanceof PasswordListFragment) {
            TransitionInflater inflater = TransitionInflater.from(currentFragment.requireContext());
            currentFragment.setExitTransition(inflater.inflateTransition(R.transition.slide_left));
            if (fragment instanceof PasswordInfoFragment)
                currentFragment.setExitTransition(inflater.inflateTransition(R.transition.slide_right));
        }

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, "CURRENT")
                .setReorderingAllowed(true)
                .commit();
    }
}
