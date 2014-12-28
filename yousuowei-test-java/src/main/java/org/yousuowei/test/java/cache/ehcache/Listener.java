package org.yousuowei.test.java.cache.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class Listener implements CacheEventListener {

	public void notifyElementRemoved(Ehcache ehcache, Element element)
			throws CacheException {
		System.out.println("notifyElementRemoved cacheName:"
				+ ehcache.getName() + " " + element);

	}

	public void notifyElementPut(Ehcache ehcache, Element element)
			throws CacheException {
		System.out.println("notifyElementPut cacheName:" + ehcache.getName()
				+ " " + element);
	}

	public void notifyElementUpdated(Ehcache ehcache, Element element)
			throws CacheException {
		System.out.println("notifyElementUpdated cacheName:"
				+ ehcache.getName() + " " + element);
	}

	public void notifyElementExpired(Ehcache ehcache, Element element) {
		System.out.println("notifyElementExpired cacheName:"
				+ ehcache.getName() + " " + element);
		// ehcache.put(element);

	}

	public void notifyElementEvicted(Ehcache ehcache, Element element) {
		System.out.println("notifyElementEvicted cacheName:"
				+ ehcache.getName() + " " + element);

	}

	public void notifyRemoveAll(Ehcache ehcache) {
		System.out.println("notifyRemoveAll cacheName:" + ehcache.getName());

	}

	public void dispose() {
		System.out.println("dispose ");

	}

	public Object clone() throws CloneNotSupportedException {
		System.out.println("clone ");
		return super.clone();
	}

}
