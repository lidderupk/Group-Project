package android.support.v4.app;

import java.util.ArrayList;

import src.com.actionbarsherlock.ActionBarSherlock;
import src.com.actionbarsherlock.ActionBarSherlock.OnCreatePanelMenuListener;
import src.com.actionbarsherlock.ActionBarSherlock.OnMenuItemSelectedListener;
import src.com.actionbarsherlock.ActionBarSherlock.OnPreparePanelListener;
import src.com.actionbarsherlock.view.Menu;
import src.com.actionbarsherlock.view.MenuInflater;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

/** I'm in ur package. Stealing ur variables. */
public abstract class Watson extends FragmentActivity implements OnCreatePanelMenuListener, OnPreparePanelListener, OnMenuItemSelectedListener {
    private static final String TAG = Messages.getString("Watson.0"); //$NON-NLS-1$

    /** Fragment interface for menu creation callback. */
    public interface OnCreateOptionsMenuListener {
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater);
    }
    /** Fragment interface for menu preparation callback. */
    public interface OnPrepareOptionsMenuListener {
        public void onPrepareOptionsMenu(Menu menu);
    }
    /** Fragment interface for menu item selection callback. */
    public interface OnOptionsItemSelectedListener {
        public boolean onOptionsItemSelected(MenuItem item);
    }

    private ArrayList<Fragment> mCreatedMenus;


    ///////////////////////////////////////////////////////////////////////////
    // Sherlock menu handling
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.1") + featureId + Messages.getString("Watson.2") + menu); //$NON-NLS-1$ //$NON-NLS-2$

        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            boolean result = onCreateOptionsMenu(menu);
            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.3") + result); //$NON-NLS-1$

            MenuInflater inflater = getSupportMenuInflater();
            boolean show = false;
            ArrayList<Fragment> newMenus = null;
            if (mFragments.mAdded != null) {
                for (int i = 0; i < mFragments.mAdded.size(); i++) {
                    Fragment f = mFragments.mAdded.get(i);
                    if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnCreateOptionsMenuListener) {
                        show = true;
                        ((OnCreateOptionsMenuListener)f).onCreateOptionsMenu(menu, inflater);
                        if (newMenus == null) {
                            newMenus = new ArrayList<Fragment>();
                        }
                        newMenus.add(f);
                    }
                }
            }

            if (mCreatedMenus != null) {
                for (int i = 0; i < mCreatedMenus.size(); i++) {
                    Fragment f = mCreatedMenus.get(i);
                    if (newMenus == null || !newMenus.contains(f)) {
                        f.onDestroyOptionsMenu();
                    }
                }
            }

            mCreatedMenus = newMenus;

            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.4") + show); //$NON-NLS-1$
            result |= show;

            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.5") + result); //$NON-NLS-1$
            return result;
        }
        return false;
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.6") + featureId + Messages.getString("Watson.7") + view + Messages.getString("Watson.8") + menu); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            boolean result = onPrepareOptionsMenu(menu);
            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.9") + result); //$NON-NLS-1$

            boolean show = false;
            if (mFragments.mAdded != null) {
                for (int i = 0; i < mFragments.mAdded.size(); i++) {
                    Fragment f = mFragments.mAdded.get(i);
                    if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnPrepareOptionsMenuListener) {
                        show = true;
                        ((OnPrepareOptionsMenuListener)f).onPrepareOptionsMenu(menu);
                    }
                }
            }

            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.10") + show); //$NON-NLS-1$
            result |= show;

            result &= menu.hasVisibleItems();
            if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.11") + result); //$NON-NLS-1$
            return result;
        }
        return false;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (ActionBarSherlock.DEBUG) Log.d(TAG, Messages.getString("Watson.12") + featureId + Messages.getString("Watson.13") + item); //$NON-NLS-1$ //$NON-NLS-2$

        if (featureId == Window.FEATURE_OPTIONS_PANEL) {
            if (onOptionsItemSelected(item)) {
                return true;
            }

            if (mFragments.mAdded != null) {
                for (int i = 0; i < mFragments.mAdded.size(); i++) {
                    Fragment f = mFragments.mAdded.get(i);
                    if (f != null && !f.mHidden && f.mHasMenu && f.mMenuVisible && f instanceof OnOptionsItemSelectedListener) {
                        if (((OnOptionsItemSelectedListener)f).onOptionsItemSelected(item)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public abstract boolean onCreateOptionsMenu(Menu menu);

    public abstract boolean onPrepareOptionsMenu(Menu menu);

    public abstract boolean onOptionsItemSelected(MenuItem item);

    public abstract MenuInflater getSupportMenuInflater();
}
