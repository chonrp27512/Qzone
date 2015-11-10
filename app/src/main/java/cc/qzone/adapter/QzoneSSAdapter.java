package cc.qzone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.qzone.R;
import cc.qzone.model.Girl;
import cc.qzone.util.ImageConfigBuilder;
import cc.qzone.view.NineImgLayout;
import cc.qzone.view.SelectableRoundedImageView;

/**
 * User: (1203596603@qq.com)
 * Date: 2015-08-27
 * Time: 10:00
 * Version 1.0
 */

public class QzoneSSAdapter extends BaseAdapter {

    private List<Girl> mList;
    private Context mContext;

    public QzoneSSAdapter(Context context, List<Girl> list){
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
        ViewHolder viewHolder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_qzone_ss, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.image_view = (SelectableRoundedImageView)view.findViewById(R.id.image_view);
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            viewHolder.nineImgLayout = (NineImgLayout)view.findViewById(R.id.nineImgLayout);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        final Girl user = mList.get(i);
        if(user!=null){
            viewHolder.name.setText(user.getRealName());
            viewHolder.time.setText(user.getCity());
            List<String> urls = user.getImgList();
            List<String> list = new ArrayList<>();
            if(urls!=null && urls.size()>9){
                for(int j=0; j<9; j++){
                    list.add(urls.get(j));
                }
            } else {
                list = urls;
            }
            viewHolder.nineImgLayout.setOnItemClickListener(new NineImgLayout.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int index) {
                    Toast.makeText(mContext, "第"+(index+1)+"张图片", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.nineImgLayout.setPics(list);

            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "用户信息：" + user.getRealName(), Toast.LENGTH_SHORT).show();
                }
            });
            ImageLoader.getInstance().displayImage(user.getAvatarUrl(), viewHolder.image_view, ImageConfigBuilder.USER_HEAD_HD_OPTIONS);

           // UrlImageViewHelper.setUrlDrawable(viewHolder.image_view, user.getAvatarUrl());
        }

        return view;
    }

    static class ViewHolder{
        SelectableRoundedImageView image_view;
        TextView name;
        TextView time;
        NineImgLayout nineImgLayout;
    }
}
