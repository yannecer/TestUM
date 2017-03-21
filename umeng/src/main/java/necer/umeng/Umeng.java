package necer.umeng;

import android.app.Activity;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by necer on 2017/3/21.
 */

public class Umeng implements AnimDialog.OnShareListener {

    private AnimDialog animDialog;
    private UMWeb umWeb;
    private Activity activity;
    private String text;//有道云和印象的笔记内容

    public Umeng(Activity activity) {
        this.activity = activity;
        animDialog = new AnimDialog(activity);
        animDialog.setOnShareListener(this);
    }


    public Umeng initShare(String title, String description,String text, String targetUrl, String imageUrl) {
        umWeb = new UMWeb(targetUrl);
        umWeb.setTitle(title);
        umWeb.setThumb(new UMImage(activity, imageUrl));
        umWeb.setDescription(description);
        this.text = text;
        return this;
    }

    public Umeng initShare(String title, String description, String text,String targetUrl, int drawableId) {
        umWeb = new UMWeb(targetUrl);
        umWeb.setTitle(title);
        umWeb.setThumb(new UMImage(activity, drawableId));
        umWeb.setDescription(description);
        this.text = text;
        return this;
    }

    public void show() {
        animDialog.show();
    }
    @Override
    public void onShare(SHARE_MEDIA mPlatform) {
        if (mPlatform == SHARE_MEDIA.EVERNOTE || mPlatform == SHARE_MEDIA.YNOTE) {
            //纯文字
            new ShareAction(activity)
                    .withText(text)
                    .setPlatform(mPlatform)
                    .setCallback(umShareListener).share();
        } else {
            //链接
            new ShareAction(activity)
                    .withMedia(umWeb)
                    .withText(text)
                    .setPlatform(mPlatform)
                    .setCallback(umShareListener).share();
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(activity, "分享成功啦！", Toast.LENGTH_SHORT).show();
            animDialog.dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(activity, "分享失败啦！", Toast.LENGTH_SHORT).show();
            animDialog.dismiss();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(activity, "分享取消啦！", Toast.LENGTH_SHORT).show();
            animDialog.dismiss();
        }
    };

}
