package com.example.st.arcgiscss.d3View;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;

/**
 *
 *
 */
public abstract class D3Fragment extends Fragment {

	public View setContentView(LayoutInflater inflater,int layoutResID) {
		View view = inflater.inflate(layoutResID, null);
		initInjectedView(this,view);
		return view;
	}

	private void initInjectedView(Object framgent,View sourceView){
		Field[] fields = framgent.getClass().getDeclaredFields();
		if(fields!=null && fields.length>0){
			for(Field field : fields){
				try {
					field.setAccessible(true);

					if(field.get(framgent)!= null )
						continue;

					D3View d3View = field.getAnnotation(D3View.class);
					if(d3View!=null){

						int viewId = d3View.id();
						if(viewId == 0)
							viewId = getResources().getIdentifier(field.getName(), "id",getActivity().getPackageName());
						if(viewId == 0)
							Log.e("D3Activity", "field "+ field.getName() + "not found");

						//Key, annotation initialization, equivalent to backBtn = (TextView) findViewById(R.id.back_btn);
						field.set(framgent,sourceView.findViewById(viewId));
						//event
						setListener(framgent,field,d3View.click(), Method.Click);
						setListener(framgent,field,d3View.longClick(), Method.LongClick);
						setListener(framgent,field,d3View.itemClick(), Method.ItemClick);
						setListener(framgent,field,d3View.itemLongClick(), Method.itemLongClick);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void setListener(Object activity,Field field,String methodName,Method method)throws Exception{
		if(methodName == null || methodName.trim().length() == 0)
			return;

		Object obj = field.get(activity);

		switch (method) {
			case Click:
				if(obj instanceof View){
					((View)obj).setOnClickListener(new EventListener(activity).click(methodName));
				}
				break;
			case ItemClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemClickListener(new EventListener(activity).itemClick(methodName));
				}
				break;
			case LongClick:
				if(obj instanceof View){
					((View)obj).setOnLongClickListener(new EventListener(activity).longClick(methodName));
				}
				break;
			case itemLongClick:
				if(obj instanceof AbsListView){
					((AbsListView)obj).setOnItemLongClickListener(new EventListener(activity).itemLongClick(methodName));
				}
				break;
			case focusChange:
				if (obj instanceof View) {
					((View) obj).setOnFocusChangeListener(new EventListener(activity).focusChange(methodName));
				}
				break;
			default:
				break;
		}
	}

	public enum Method{
		Click,LongClick,ItemClick,itemLongClick,focusChange
	}

}