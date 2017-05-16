package fr.free.nrw.commons.theme;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.free.nrw.commons.R;
import fr.free.nrw.commons.hamburger.HamburgerMenuContainer;
import fr.free.nrw.commons.hamburger.NavigationBaseFragment;
import fr.free.nrw.commons.utils.FragmentUtils;

import static android.support.v4.view.GravityCompat.START;

public class NavigationBaseActivity extends BaseActivity implements HamburgerMenuContainer {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.drawer_pane)
    RelativeLayout drawerPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initDrawer() {
        initSubviews();
        NavigationBaseFragment baseFragment = new NavigationBaseFragment();
        baseFragment.setDrawerLayout(drawerLayout, drawerPane);
        FragmentUtils.addAndCommitFragmentWithImmediateExecution(getSupportFragmentManager(),
                R.id.drawer_fragment,
                baseFragment);
    }

    private void initSubviews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        setDrawerPaneWidth();
    }

    private void setDrawerPaneWidth() {
        ViewGroup.LayoutParams params = drawerPane.getLayoutParams();
        // set width to lowerBound of 80% of the screen size
        params.width = (getResources().getDisplayMetrics().widthPixels * 70) / 100;
        drawerPane.setLayoutParams(params);
    }

    @Override
    public void setDrawerListener(ActionBarDrawerToggle listener) {
        drawerLayout.setDrawerListener(listener);
    }

    @Override
    public void toggleDrawer() {
        if (drawerLayout.isDrawerVisible(START)) {
            drawerLayout.closeDrawer(START);
        } else {
            drawerLayout.openDrawer(START);
        }
    }

    @Override
    public boolean isDrawerVisible() {
        return drawerLayout.isDrawerVisible(START);
    }
}
