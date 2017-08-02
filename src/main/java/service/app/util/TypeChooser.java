package service.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TypeChooser {
	
	private Map<String, List<String>> 			types = new HashMap<>();
	private Map<String,List<TypeChooserItem>>	typeItems = new HashMap<>();
	
	
	@Transactional
	public boolean put(String ts){
		if(ts==null)
			return false;

		List<TypeChooserItem> ls = new ArrayList<>();
		List<String> tys = new ArrayList<>();
		try {
			String[] ts1 = ts.split(":");
			if(ts1.length!=2) return false;
			String tyname = ts1[0];
			String tmp =ts1[1];
			ts1 = tmp.split(",");
			if(ts1.length<1) return false;
			for(int i=0;i<ts1.length;i++){
				String[] ss = ts1[i].split("-");
				if(ts1[i].contains("a"))
					ls.add(new TypeChooserItem(TypeChooserItem.HEAD,"<"+ss[1],
							null,Double.parseDouble(ss[1])));
				else if(ts1[i].contains("b"))
					ls.add(new TypeChooserItem(TypeChooserItem.TAIL,">"+ss[0],
							Double.parseDouble(ss[0]),null));
				else
					ls.add(new TypeChooserItem(TypeChooserItem.BODY,ts1[i],
							Double.parseDouble(ss[0]),Double.parseDouble(ss[1])));
			}
			
			for(TypeChooserItem item:ls)
				tys.add(item.getmItemName());
			
			types.put(tyname, tys);
			typeItems.put(tyname, ls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	public boolean remove(String typename){
		if(typename==null) 
				return false;
		types.remove(typename);
		typeItems.remove(typename);
		return true;
	}
	public String get(String typename,Double value){
		if(typename==null||value==null) return null;
		List<TypeChooserItem> items = typeItems.get(typename);
		String tn = null;
		if(items==null)return null;
		for(int i=0;i<items.size();i++){
			if(items.get(i).isIn(value)){
				tn = items.get(i).mItemName;
				break;
			}
		}
		return tn;
	}	
	public List<String> getAlltypes(String typename){
		if(typename==null) return null;
		return types.get(typename);
	}
	public boolean setAllTypes(String typename,List<String> types ){
		if(typename==null) return false;
		this.types.put(typename, types);
		return true;
	}
	public boolean setAllTypes(String typename,String[] types){
		if(typename==null) return false;
		List<String> ls = new ArrayList<String>();
		if(types==null)
			this.types.put(typename,null );
		else{
			for(String s:types)
				if(s!=null)
					ls.add(s);
			this.types.put(typename,ls);
		}
		return true;
	}
	class TypeChooserItem {
		public final static int HEAD = 0;
		public final static int BODY = 1;
		public final static int TAIL = 2;
		
		private int 	mPk = -1;
		private String  mItemName = null;
		private Double left = 0.0;
		private Double right = 0.0;
		private static final double dis = 0.001; 
		
		public TypeChooserItem(){
			
		}
		
		public TypeChooserItem(int pk,String itemname,Double left,Double right){
			this.mPk = pk;
			this.mItemName = itemname;
			this.left = left;
			this.right = right;
		}
		
		public boolean isIn(Double value){
			if(mPk == HEAD)
				return 
						value<right&&
						Math.abs(right - value) > dis;
			else if(mPk == BODY)
				return 
						
						Math.abs(left - value) <= dis||
						(value>left&&value<right)&&
						Math.abs(right - value) >dis;
			else if(mPk == TAIL)
				return 
						value>left ||
						Math.abs(left - value) <= dis;
			else return false;
		}

		public String getmItemName() {
			return mItemName;
		}

		public void setmItemName(String mItemName) {
			this.mItemName = mItemName;
		}		
	}
	
	
}
