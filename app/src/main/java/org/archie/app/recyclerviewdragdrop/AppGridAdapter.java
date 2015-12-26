package org.archie.app.recyclerviewdragdrop;

/**
 *
 * Created by archie on 2015/12/7.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app list adapter
 * Created by archie on 15/12/1.
 */
public class AppGridAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {
    Context context;
    List<AppInfo> list;
    Gson gson;

    public AppGridAdapter(Context context, List<AppInfo> list) {
        this.context = context;
        this.list = list;
        gson = new Gson();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AppInfo info = list.get(position);
        BodyViewHolder mHolder = (BodyViewHolder) holder;
        try {
            Drawable icon = context.getPackageManager().getApplicationIcon(info.getPackageName());
            mHolder.appIcon.setImageDrawable(icon);
            mHolder.appTitle.setText(info.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = context.getPackageManager()
                        .getLaunchIntentForPackage(info.getPackageName());
                if (launchIntent != null) {
                    context.startActivity(launchIntent);
                } else {
                    Log.d("package",info.getPackageName()+"");
                }
//                //该应用的包名
//                String pkg = info.getPackageName();
//                //应用的主activity类
//                String cls = info.name;
//                ComponentName componentName = new ComponentName(pkg, cls);
//                Intent it = new Intent();
//                it.setComponent(componentName);
//                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(list, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(list, i, i - 1);
            }
        }
        Type listOfApp = new TypeToken<List<AppInfo>>(){}.getType();
        Map<String, String> saveMap = new HashMap<>();
        String value = gson.toJson(list, listOfApp);
        saveMap.put(MainActivity.saveKey,value);
        SharedPreferencesUtils.save(MainActivity.fileName,saveMap);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


    private class BodyViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appTitle;
        View view;

        public BodyViewHolder(View view) {
            super(view);
            this.view = view;
            appIcon = (ImageView) view.findViewById(R.id.app_icon);
            appTitle = (TextView) view.findViewById(R.id.app_title);
        }
    }

}
