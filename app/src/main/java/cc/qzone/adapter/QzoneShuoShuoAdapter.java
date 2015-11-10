package cc.qzone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import cc.qzone.R;
import cc.qzone.model.Girl;
import cc.qzone.util.ImageConfigBuilder;
import cc.qzone.view.NineImgLayout;
import cc.qzone.view.QzoneTitleView;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-08-27
 * Time: 10:00
 * Version 1.0
 */

public class QzoneShuoShuoAdapter extends BaseAdapter {

    private List<Girl> mList;
    private Context mContext;

    public QzoneShuoShuoAdapter(Context context, List<Girl> list){
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_qzone_shuo_shuo, viewGroup, false);
        }

        final QzoneTitleView titleView = ViewHolder.get(view, R.id.title_view);
        final NineImgLayout nineImgView = ViewHolder.get(view, R.id.nineImgView);
        nineImgView.setOnItemClickListener(new NineImgLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int index) {
                Toast.makeText(mContext, view+"="+index, Toast.LENGTH_SHORT).show();
            }
        });

        final Girl user = mList.get(i);
        if(user!=null){
            titleView.setName(user.getRealName());
            titleView.setTime(user.getCity());
            List<String> urls = user.getImgList();
            List<String> list = new ArrayList<>();
            //list.add("http://mnsfz-img.xiuna.com/pic/qiaopi/2015-8-13/1/0.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/yangguang/2015-8-21/1/527490182015080619151004_640.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/yangguang/2015-8-26/1/6630072707677053014.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/yangguang/2015-8-17/1/2445454597680451528.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/qiaopi/2015-8-26/1/005FDahajw1evciyfk6v4j30tn18gwns.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/qiaopi/2015-8-28/1/1060879187240748129.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/qingchun/2015-8-27/1/11773223_1440596974113_mthumb.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/qingchun/2015-7-29/1/0069QrLogw1eu3890l53lj31kw2dc4ev.jpg");
            //list.add("http://mnsfz-img.xiuna.com/pic/qingchun/2015-7-6/1/6630073807188327948.jpg");
            if(urls!=null && urls.size()>9){
                for(int j=0; j<9; j++){
                    list.add(urls.get(j));
                }
            } else {
                list = urls;
            }
            nineImgView.setPics(list);

            titleView.setOnNameClickListener(new QzoneTitleView.OnNameClickListener() {
                @Override
                public void onClick() {
                    Toast.makeText(mContext, "用户信息：" + user.getRealName(), Toast.LENGTH_SHORT).show();
                }
            });

            ImageSize imageSize = new ImageSize(180, 180);
            ImageLoader.getInstance().loadImage(user.getAvatarUrl(), imageSize, ImageConfigBuilder.USER_HEAD_HD_OPTIONS, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap source) {
                    int size = Math.min(source.getWidth(), source.getHeight());
                    int x = (source.getWidth() - size) / 2;
                    int y = (source.getHeight() - size) / 2;
                    Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
                    if (result != source) {
                    //    source.recycle();
                    }
                    titleView.setUserIcon(result);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }

        return view;
    }


}
