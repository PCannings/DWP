package itp.team1.jobseeker.DataParsing.CachedServerCalls;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerMemoryCache <T> {
    private LinkedList<SoftReference<T>> cache = new LinkedList<SoftReference<T>>();
       
    public void put(T data){
    	if(!cache.contains(data))
    		cache.add(new SoftReference<T>(data));
    }
    
    public ArrayList<T> get(){
    	ArrayList<T> data = new ArrayList<T>();
    	for (SoftReference<T> reference : cache) {
			if(reference.get()!=null){
				data.add(reference.get());
			}
		}
		return data;
    }

    public void clear() {
        cache.clear();
    }
}
