 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
	
/**
* 压缩算法 总
* 实现文件压缩，文件夹压缩，以及文件和文件夹的混合压缩
*/
public class CompactAlgorithm {
	
	/**
	* 完成的结果文件--输出的压缩文件
	*/
	File targetFile;
	
	public CompactAlgorithm() {}
	
	public CompactAlgorithm(File target) {
		targetFile = target;
		if (targetFile.exists())
			targetFile.delete();
		}
	
	/**
	* 压缩文件
	* /Users/apple/compresstest/test.txt    test.txt.zip
	* @param srcfile
	*/
	public void zipFiles(File srcfile) {
	
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(targetFile));
	
			if(srcfile.isFile()){
				zipFile(srcfile, out, "");
			}
			else{
				File[] list = srcfile.listFiles();
				for (int i = 0; i < list.length; i++) {
					compress(list[i], out, "");//compress
				}
			}
	
			System.out.println("压缩完毕");
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null)
					out.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* 压缩文件夹里的文件
	* 起初不知道是文件还是文件夹 统一调用该方法
	* @param file
	* @param out
	* @param basedir
	*/
	private void compress(File file, ZipOutputStream out, String basedir) {
		//判断是目录还是文件
		if (file.isDirectory()) {
			this.zipDirectory(file, out, basedir);//zipdirectory
		}
		else{
			this.zipFile(file, out, basedir);//zipfile
		}
	}
	
	/**
	* 压缩单个文件
	*
	* @param srcfile
	*/
	public void zipFile(File srcfile, ZipOutputStream out, String basedir) {
		if (!srcfile.exists())
			return;
	
		byte[] buf = new byte[1024];
		FileInputStream in = null;
	
		try {
			int len;
			in = new FileInputStream(srcfile);
			out.putNextEntry(new ZipEntry(basedir + srcfile.getName()));
	
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null)
					out.closeEntry();
				if (in != null)
					in.close();
				}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	* 压缩文件夹
	* @param dir
	* @param out
	* @param basedir
	*/
	public void zipDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;
	
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) { //递归
			compress(files[i], out, basedir + dir.getName() + "/");//compress
		}
	}
	
	/**
	* 功能:解压缩
	*
	* @param zipfile：需要解压缩的文件
	* @param descDir：解压后的目标目录
	*/
	public void unZipFiles(File zipfile, String descDir) {
		try {
			@SuppressWarnings("resource")
			ZipFile zf = new ZipFile(zipfile);
			//String name = zf.getName().substring(zf.getName().lastIndexOf('\\')+1, zf.getName().lastIndexOf('.'));
			for (@SuppressWarnings("rawtypes")
			Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				System.out.println("zipEntryName---"+descDir+zipEntryName);
				InputStream in = zf.getInputStream(entry);
				FileOutputStream out = new FileOutputStream(new File(descDir + zipEntryName));
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
				System.out.println("解压缩完成.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	public static void main(String[] args) {
		File f = new File("test.txt");
		new CompactAlgorithm(new File( "test",f.getName()+".zip")).zipFiles(f);
	}*/
}