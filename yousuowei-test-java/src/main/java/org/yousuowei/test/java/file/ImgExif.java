package org.yousuowei.test.java.file;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifInteropDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifThumbnailDirectory;

public class ImgExif {

	@Test
	public void test() {
		File jpegFile = new File("C:\\Users\\jie\\Desktop\\test.jpg");
		Metadata metadata = null;
		try {
			metadata = JpegMetadataReader.readMetadata(jpegFile);
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator directorys = metadata.getDirectories().iterator();
		while (directorys.hasNext()) {
			Directory directory = (Directory) directorys.next();
			Iterator tags = null;
			if (null != directory) {
				tags = directory.getTags().iterator();
				while (tags.hasNext()) {
					Tag tag = (Tag) tags.next();
					System.out.println(tag);
				}
			}
		}
		
//		ExifInterface exifInterface = new ExifInterface(path);
//		int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//		switch (orientation) {
//		case ExifInterface.ORIENTATION_ROTATE_90:
//		degree = 90;
//		break;
//		case ExifInterface.ORIENTATION_ROTATE_180:
//		degree = 180;
//		break;
//		case ExifInterface.ORIENTATION_ROTATE_270:
//		degree = 270;
//		break;
//		}

	}
}
