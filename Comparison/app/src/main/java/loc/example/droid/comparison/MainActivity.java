package loc.example.droid.comparison;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.addFragment();

        mDrawerLayout = this.findViewById(R.id.drawer_layout);
        mNavView = this.findViewById(R.id.nav_view);
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_camera:
                        Log.d(TAG, "[import] menu item clicked..");
                        break;
                    case R.id.nav_gallery:
                        Log.d(TAG, "[gallery] menu item clicked..");
                        break;
                    case R.id.nav_slideshow:
                        Log.d(TAG, "[slideshow] menu item clicked..");
                        break;
                    case R.id.nav_manage:
                        Log.d(TAG, "[manage] menu item clicked..");
                        break;
                }
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mToolbar = this.findViewById(R.id.toolbar);
        this.setSupportActionBar(mToolbar);

        mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

//        this.addDrawerListener();
        try {
            this.readExternalFiles();
        } catch (FileNotFoundException fne) {
            Log.e(TAG, fne.getMessage(), fne);
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage(), ioe);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.nav_camera:
                Log.d(TAG, "nav camera menu clicked..");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void addFragment() {
        FragmentManager fm = this.getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.content_frame);
        if (f == null) {
            f = MainFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.content_frame, f)
                    .commit();
        }
    }

    protected void addDrawerListener() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                Log.d(TAG, "onDrawerSlide() method called..");
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                Log.d(TAG, "onDrawerOpened() method called..");
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                Log.d(TAG, "onDrawerClosed() method called..");
            }

            @Override
            public void onDrawerStateChanged(int i) {
                Log.d(TAG, "onDrawerStateChanged() method called [i]: " + i);
            }
        });
    }

    /**
     * 1) reads specs.json from external sd card
     * 2) if file is valid, save it to internal storage so we can read it later
     * 3) read json file
     * 4)
     */
    protected void readExternalFiles() throws FileNotFoundException, IOException {
        String dirPath = Environment.DIRECTORY_DOWNLOADS;
        File[] dirs = this.getExternalFilesDirs(dirPath);
        Log.d(TAG, "number of external download files: " + dirs.length);
        String strJson = "";
        for (File dir : dirs) {
            Log.d(TAG, "dir: " + dir);
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.isFile() && file.exists() && file.canRead()) {
                        InputStream in = new FileInputStream(file);
                        byte[] bytes = new byte[1024];
                        int bytesRead = in.read(bytes);
                        while (bytesRead != -1) {

                        }
                        strJson = file.toString();
                    }
                }
            }
        }
        Log.d(TAG, "json: " + strJson);
    }
}
