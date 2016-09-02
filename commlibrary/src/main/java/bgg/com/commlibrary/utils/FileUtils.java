package bgg.com.commlibrary.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
	/**
	 * 创建新的文件
	 * 
	 * @param dirPath
	 *            文件目录
	 * @param name
	 *            文件名
	 */
	public static File createFileInSDCard(String dirPath, String name) throws Exception {
		File file = new File(dirPath, name);
		file.createNewFile();
		return file;
	}
	
	public static byte[] getBytesFromFile(File f) {   
        if (f == null) {   
            return null;   
        }   
        try {   
            FileInputStream stream = new FileInputStream(f);   
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);   
            byte[] b = new byte[1000];   
            int n;   
            while ((n = stream.read(b)) != -1) {  
                out.write(b, 0, n);   
               }  
            stream.close();   
            out.close();   
            return out.toByteArray();   
        } catch (IOException e) {   
        }   
        return null;   
    }   

	public static File createFileInSDCard(String name) throws Exception {
		int index = name.lastIndexOf(File.separator);
		String dir = name.substring(0, index);
		creatSDDir(dir);
		return createFileInSDCard(dir, name.substring(index + 1));
	}

	/**
	 * 创建新目录
	 */
	public static File creatSDDir(String dirPath) {
		File dirFile = new File(dirPath);
		if (!dirFile.exists())
			dirFile.mkdirs();
		return dirFile;
	}

	/**
	 * 判断文件存不存在
	 */
	public static boolean isFileExist(String name) {
		return new File(name).exists();
	}

	/**
	 * 将流转化成文件，写进内存卡
	 */
	public static File write2SDFromInput(String name, InputStream input) {
		if (input == null)
			return null;// 如果流是空的，就返回空文件
		File file = null;
		try {
			file = createFileInSDCard(name);
			FileOutputStream output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int temp = -1;
			while ((temp = input.read(buffer)) != -1) {
				output.write(buffer, 0, temp);
			}
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static String cachePath() {
		return Environment.getExternalStorageDirectory().getPath() + "/cache/";
	}

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
	public static void saveFile(Bitmap bm, String fileName) throws IOException {
		String path = cachePath();

		File dirFile = new File(path);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		
		File myCaptureFile = new File(path + fileName);

		if (!myCaptureFile.exists()) {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
			bos.flush();
			bos.close();
		}

	}
	
	
	/**
	 * 测试代码
	 * 
	 * @param filename
	 * @param des
	 * @return
	 */
	public static Boolean CopyAssetsFile(Context context,String filename, String des) {
		Boolean isSuccess = true;
		// 复制安卓apk的assets目录下任意路径的单个文件到des文件夹，注意是否对des有写权限
		AssetManager assetManager = context.getAssets();

		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(filename);
			String newFileName = des + "/" + filename;
			out = new FileOutputStream(newFileName);

			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}

			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}

		return isSuccess;

	}

	public static boolean isExistSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}

		return false;
	}

	public static void deleteFile(String path) {
		if (path == null || "".equals(path))
			return;
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static void deleteFile(File file) {
		if (file == null || !file.exists())
			return;
		file.delete();
	}

	// 删除文件夹下的所有文件
	public static void delete(File file) {

		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

}
