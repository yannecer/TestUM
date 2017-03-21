package necer.umeng;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by necer on 2017/3/21.
 */

public class AnimDialog extends Dialog implements AdapterView.OnItemClickListener {


    private GridView gv;
    public ArrayList<SnsPlatform> platforms = new ArrayList<>();

    private ShareAdapter shareAdapter;


    public AnimDialog(@NonNull Context context) {
        super(context, R.style.anim_dialog);
        setCanceledOnTouchOutside(true);//触摸屏幕取消窗体
        setCancelable(true);//按返回键取消窗体

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null);
        setContentView(contentView);
        gv = (GridView) findViewById(R.id.gv);

        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.SINA.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.YNOTE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());

        shareAdapter = new ShareAdapter(context, platforms);

        gv.setAdapter(shareAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (onShareListener != null) {
            onShareListener.onShare(platforms.get(i).mPlatform);
        }
    }


    protected interface OnShareListener {
        void onShare(SHARE_MEDIA mPlatform);
    }

    private OnShareListener onShareListener;

    public AnimDialog setOnShareListener(OnShareListener onShareListener) {
        this.onShareListener = onShareListener;
        return this;
    }

    static class ShareAdapter extends BaseAdapter {
        private Context context;
        private List<SnsPlatform> list;

        public ShareAdapter(Context context, List<SnsPlatform> list) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.item_share, null);
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
            TextView name = (TextView) convertView.findViewById(R.id.name);

            icon.setImageResource(ResContainer.getResourceId(context, "drawable", list.get(position).mIcon));
            name.setText(ResContainer.getResourceId(context, "string", list.get(position).mShowWord));

            return convertView;
        }
    }
}

