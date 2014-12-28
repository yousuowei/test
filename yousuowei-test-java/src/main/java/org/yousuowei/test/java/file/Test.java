package org.yousuowei.test.java.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class Test {
	public static void main(String[] args) {
		readFile();
	}

	public static void readFile() {
		FileReader fileReader = null;
		BufferedReader bufferedFileReader = null;
		File file = new File("G:/test/2013-04-01");
		Map<String, Integer> map = new HashMap<String, Integer>();
		int num = 0;
		int num1 = 0;

		if (file != null && file.exists()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				if (file2.isFile()) {
					try {
						fileReader = new FileReader(file2);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					bufferedFileReader = new BufferedReader(fileReader);
					String line = null;
					try {
						while ((line = bufferedFileReader.readLine()) != null) {
							String[] arr = StringUtils.split(line, " ");
							if (arr != null && arr.length > 6) {
								if (arr[4].equals("STAT")) {
									String[] arrParams = StringUtils.split(
											arr[6], "-");
									if (arrParams[0].equals("online")) {
										if (map.containsKey(arrParams[2])) {
											num1 = map.get(arrParams[2]);
											num1++;
										} else {
											num1 = 1;
										}
										map.put(arrParams[2], num1);
										num++;
									}
								}
							}
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if (fileReader != null)
							fileReader.close();
						if (bufferedFileReader != null)
							bufferedFileReader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		int value = 0;
		for (String key : map.keySet()) {
			System.out.println(key + ":" + map.get(key));
			value += map.get(key);
		}
		System.out.println("总开机次数：" + num + " 总开机设备数：" + map.size()
				+ " 统计设备总开机数：" + value);
	}
}
