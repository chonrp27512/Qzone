package cc.qzone.weibo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cc.qzone.R;
import cc.qzone.util.ImageConfigBuilder;
import cc.qzone.view.NineImgLayout;
import cc.qzone.view.SelectableRoundedImageView;
import cc.qzone.view.WebBoTextView;
import cc.qzone.weibo.WBUserInfoActivity;
import cc.qzone.weibo.model.WBStatuses;
import cc.qzone.weibo.model.WBUser;

/**
 * User: 靳世坤(1203596603@qq.com)
 * Date: 2015-08-27
 * Time: 10:00
 * Version 1.0
 */

public class WBWeiBoAdapter extends BaseAdapter {

    private List<WBStatuses> mList;
    private Context mContext;

    public WBWeiBoAdapter(Context context, List<WBStatuses> list){
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
            view = LayoutInflater.from(mContext).inflate(R.layout.wb_adapter_weibo, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.image_view = (SelectableRoundedImageView)view.findViewById(R.id.image_view);
            viewHolder.name = (TextView)view.findViewById(R.id.name);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            viewHolder.webBoTextView = (WebBoTextView)view.findViewById(R.id.text);
            viewHolder.nineImgLayout = (NineImgLayout)view.findViewById(R.id.nineImgLayout);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)view.getTag();
        }

        final WBStatuses wbStatuses = mList.get(i);
        if(wbStatuses!=null){
            viewHolder.webBoTextView.setTextContent(wbStatuses.getText());
            final WBUser user = wbStatuses.getWbUser();
            viewHolder.name.setText(user.getScreen_name());
            viewHolder.time.setText(user.getLocation());
            List<String> urls = wbStatuses.getPic_urls();
            viewHolder.webBoTextView.setOnTextViewClickListener(new WebBoTextView.OnTextViewClickListener() {
                @Override
                public void clickTextView(int style, String key, int index) {
                    Toast.makeText(mContext, key, Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.nineImgLayout.setOnItemClickListener(new NineImgLayout.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int index) {
                    Toast.makeText(mContext, "第" + (index + 1) + "张图片", Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.nineImgLayout.setPics(urls);

            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "用户信息：" + user.getScreen_name(), Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, WBUserInfoActivity.class);
                    intent.putExtra("uid", user.getId());
                    mContext.startActivity(intent);
                }
            });
            ImageLoader.getInstance().displayImage(user.getProfile_image_url(), viewHolder.image_view, ImageConfigBuilder.USER_HEAD_HD_OPTIONS);
        }

        return view;
    }

    static class ViewHolder{
        SelectableRoundedImageView image_view;
        TextView name;
        TextView time;
        WebBoTextView webBoTextView;
        NineImgLayout nineImgLayout;
    }
}
