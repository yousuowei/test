package org.yousuowei.test.java.cache.ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * name：Cache的唯一标识 · maxElementsInMemory：内存中最大缓存对象数。 ·
 * maxElementsOnDisk：磁盘中最大缓存对象数，若是0表示无穷大。 ·
 * eternal：Element是否永久有效，一但设置了，timeout将不起作用。 ·
 * overflowToDisk：配置此属性，当内存中Element数量达到maxElementsInMemory时
 * Ehcache将会Element写到磁盘中。 ·
 * timeToIdleSeconds：设置Element在失效前的允许闲置时间。仅当element不是永久有效时使用
 * ，可选属性，默认值是0，也就是可闲置时间无穷大。 ·
 * timeToLiveSeconds：设置Element在失效前允许存活时间。最大时间介于创建时间和失效时间之间
 * 。仅当element不是永久有效时使用，默认是0.，也就是element存活时间无穷大。 ·
 * diskPersistent：是否缓存虚拟机重启期数据。（这个虚拟机是指什么虚拟机一直没看明白是什么,有高人还希望能指点一二）。 ·
 * diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。 ·
 * diskSpoolBufferSizeMB：
 * 这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。 ·
 * memoryStoreEvictionPolicy
 * ：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU
 * （最近最少使用）。你可以设置为FIFO（先进先出
 * ）或是LFU（较少使用）。这里比较遗憾，Ehcache并没有提供一个用户定制策略的接口，仅仅支持三种指定策略，感觉做的不够理想。
 * 
 * @author jie
 * 
 */
public class CommonCache {
	static Logger log = LoggerFactory.getLogger(CommonCache.class);

	private static CommonCache instance = new CommonCache();
	private static CacheManager cacheManager;

	private static Cache cache;// 设置时间失效cache
	private static String name;
	private static int maxElementsInMemory;// 可存在数量
	/**
	 * 超过可存在数量将执行此策略 三种策略 1，FIFO(first in first out)先进先出 2，LFU( Less Frequently
	 * Used):一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性， hit值最小的将会被清出缓存。3， LRU(Least
	 * Recently Used):最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，
	 * 而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
	 */
	private static MemoryStoreEvictionPolicy memoryStoreEvictionPolicy;
	private static boolean overflowToDisk;// 溢出则存磁盘
	private static String diskStorePath;// 存储磁盘文件地址
	private static boolean eternal;// 是否有恒，如果true则忽略下面俩参数
	private static long timeToLiveSeconds;// cache存储值有效时间
	private static long timeToIdleSeconds;// cache存储值闲置时间
	private static boolean diskPersistent;// 是否根据磁盘路径文件缓存重启期数据
	private static long diskExpiryThreadIntervalSeconds;// 磁盘失效线程运行时间间隔，默认是120秒
	private static RegisteredEventListeners registeredEventListeners;

	private static void setParams() {
		name = "testCache";
		maxElementsInMemory = 1;
		memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LRU;
		overflowToDisk = false;
		diskStorePath = null;
		eternal = false;
		timeToLiveSeconds = 2;
		timeToIdleSeconds = 0;
		diskPersistent = false;
		diskExpiryThreadIntervalSeconds = 0;
		registeredEventListeners = null;
	}

	private CommonCache() {
		try {
			cacheManager = CacheManager.create();
			setParams();
			cache = new Cache(name, maxElementsInMemory,
					memoryStoreEvictionPolicy, overflowToDisk, diskStorePath,
					eternal, timeToLiveSeconds, timeToIdleSeconds,
					diskPersistent, diskExpiryThreadIntervalSeconds,
					registeredEventListeners);

			cache.getCacheEventNotificationService().registerListener(
					new Listener());// 添加缓存操作监听器
			cacheManager.addCache(cache);
		} catch (Exception e) {
			log.error(e + ":");
		}
	}

	public static synchronized CommonCache getInstance() {
		if (instance == null) {
			instance = new CommonCache();
		}
		return instance;
	}

	public static void testMax() {
		int i = 0;
		try {
			for (; i < 10; i++) {
				cache.put(new Element("key_" + i, i));
			}
		} catch (Exception e) {
			log.error(e + ":" + i);
		}

		Element n;
		for (Object demp : cache.getKeys()) {
			n = cache.get(demp);
			System.out.println(demp + ":" + n.getValue());

		}
	}

	public static void testValidity() {
		cache.put(new Element("test", "测试有效时间"));
		new Thread() {
			int i = 0;

			public void run() {
				while (true) {
					Element element = cache.get("test");
					if (null != element) {
						System.out.println(element.getValue());
					} else {
						System.out.println("过期啦！:" + i);
						return;
					}
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						log.error(e + "");
					}
					cache.put(new Element("test1", "测试行"));
					System.out.println("get 过期");
					try {
						sleep(6000);
					} catch (InterruptedException e) {
						log.error(e + "");
					}
					i++;
				}
			};

		}.start();
	}

	public static void main(String[] args) {
		System.out.println("==");
		CommonCache commonCache = CommonCache.getInstance();
		commonCache.testValidity();
		// commonCache.testMax();
	}
}
