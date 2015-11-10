package cc.qzone.adapter;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {

	public static <T extends View> T get(View view, int resID){
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if(viewHolder==null){
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(resID);
		if(childView==null){
			childView = view.findViewById(resID);
			viewHolder.put(resID, childView);
		}
		return (T) childView;
	}
}
