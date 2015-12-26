package org.archie.app.recyclerviewdragdrop;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    AppGridAdapter mAdapter;
    List<AppInfo> infos = new ArrayList<>();
    public static final String fileName = "app";
    public static final String defaultValue = "default";
    public static final String saveKey = "appinfos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
    }

    private void setupView() {
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.app_list);
        mAdapter = new AppGridAdapter(this, infos);
        mRecyclerView.setAdapter(mAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2 );
        mRecyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String result = SharedPreferencesUtils.getValue(fileName,saveKey,defaultValue);
        Gson gson = new Gson();
        Type listOfApp = new TypeToken<List<AppInfo>>(){}.getType();
        if (result.equals(defaultValue)) {
            Map<String, String> saveMap = new HashMap<>();
            final PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> installedApplications =
                    packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            int index = 0;
            for (ApplicationInfo info: installedApplications) {
                AppInfo appInfo = new AppInfo(index,
                        info.loadLabel(this.getPackageManager()).toString(),info.packageName);
                infos.add(appInfo);
                index ++;
            }
            String value = gson.toJson(infos, listOfApp);
            saveMap.put(saveKey,value);
            SharedPreferencesUtils.save(fileName,saveMap);
        } else {
            List<AppInfo> list = gson.fromJson(result, listOfApp);
            infos.addAll(list);
        }
    }
}
