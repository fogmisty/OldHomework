import java.io.*;
//import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
//import java.util.zip.ZipFile;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public abstract class GZIPUtil {
	/**
     * 归档
     */
    static String archive(String entry) throws IOException {
        File file = new File(entry);

        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file.getAbsolutePath() + ".tar"));
        String base = file.getName();
        if(file.isDirectory()){
            archiveDir(file, tos, base);
        }else{
            archiveHandle(tos, file, base);
        }

        tos.close();
        return file.getAbsolutePath() + ".tar";
    }

    /**
     * 递归处理，准备好路径
     */
    private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws IOException {
        File[] listFiles = file.listFiles();
        for(File fi : listFiles){
            if(fi.isDirectory()){
                archiveDir(fi, tos, basePath + File.separator + fi.getName());
            }else{
                archiveHandle(tos, fi, basePath);
            }
        }
    }

    /**
     * 具体归档处理（文件）
     */
    private static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws IOException {
        TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
        tEntry.setSize(fi.length());

        tos.putArchiveEntry(tEntry);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = bis.read(buffer)) != -1){
            tos.write(buffer, 0 , read);
        }
        bis.close();
        tos.closeArchiveEntry();//这里必须写，否则会失败
    }
    
    /**
     * 把tar包压缩成gz
     */
    public static String compressArchive(String path) throws IOException{
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

        GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(new BufferedOutputStream(new FileOutputStream(path + ".gz")));

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = bis.read(buffer)) != -1){
            gcos.write(buffer, 0, read);
        }
        gcos.close();
        bis.close();
        return path + ".gz";
    }
    
    /**
     * 解压
     */
	public static void unCompressArchiveGz(String archive) throws IOException {

        File file = new File(archive);

        BufferedInputStream bis = 
        new BufferedInputStream(new FileInputStream(file));

        String fileName = 
        file.getName().substring(0, file.getName().lastIndexOf("."));

        String finalName = file.getParent() + File.separator + fileName;

        BufferedOutputStream bos = 
        new BufferedOutputStream(new FileOutputStream(finalName));

        GzipCompressorInputStream gcis = 
        new GzipCompressorInputStream(bis);

        byte[] buffer = new byte[1024];
        int read = -1;
        while((read = gcis.read(buffer)) != -1){
            bos.write(buffer, 0, read);
        }
        gcis.close();
        bos.close();

        unCompressTar(finalName);//////
    }
    
    /**
     * 解压tar
     */
    private static void unCompressTar(String finalName) throws IOException {

        File file = new File(finalName);
        String parentPath = file.getParent();
        TarArchiveInputStream tais = 
        new TarArchiveInputStream(new FileInputStream(file));

        TarArchiveEntry tarArchiveEntry = null;

        while((tarArchiveEntry = tais.getNextTarEntry()) != null){
            String name = tarArchiveEntry.getName();
            File tarFile = new File(parentPath, name);
            if(!tarFile.getParentFile().exists()){
                tarFile.getParentFile().mkdirs();
            }

            BufferedOutputStream bos = 
            new BufferedOutputStream(new FileOutputStream(tarFile));/////

            int read = -1;
            byte[] buffer = new byte[1024];
            while((read = tais.read(buffer)) != -1){
                bos.write(buffer, 0, read);
            }
            bos.close();
        }
        tais.close();
        file.delete();//删除tar文件
    }
    /**
     * 解压tar.gz文件
     * tar文件只是把多个文件或文件夹打包合成一个文件，本身并没有进行压缩。gz是进行过压缩的文件。
     */
    
    public static void deGzipArchive(String dir, String filepath)
    		throws Exception {
    	final File input = new File(filepath);
    	final InputStream is = new FileInputStream(input);
    	final CompressorInputStream in = new GzipCompressorInputStream(is, true);
    	TarArchiveInputStream tin = new TarArchiveInputStream(in);
    	TarArchiveEntry entry = tin.getNextTarEntry();
    	while (entry != null) {
    		File archiveEntry = new File(dir, entry.getName());
    		archiveEntry.getParentFile().mkdirs();
    		if (entry.isDirectory()) {
    			archiveEntry.mkdir();
    		    entry = tin.getNextTarEntry();
    		    continue;
    		}
    		OutputStream out = new FileOutputStream(archiveEntry);///////////////
    		IOUtils.copy(tin, out);
    		out.close();
    		entry = tin.getNextTarEntry();
    	}
    	in.close();
    	tin.close();
    }
    
    
    /**
    * 解压tar.gz 文件
    * @param file 要解压的tar.gz文件对象
    * @param outputDir 要解压到某个指定的目录下
    * @throws UnsupportedEncodingException
    * @throws IOException
    */
    public static void unTarGz(String fileNamePath, String outputDir) throws UnsupportedEncodingException {
    	File file = new File(fileNamePath);
    	TarArchiveInputStream tarIn = null;
    	try {
    		tarIn = new TarArchiveInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(file))), 8192);
    		createDirectory(outputDir, null);// 创建输出目录
    		ArchiveEntry entry = null;
    		while ((entry = tarIn.getNextEntry()) != null) {
    			if(entry.isDirectory()) {// 是目录
    				createDirectory(outputDir, entry.getName());// 创建空目录
    			}
    			else {// 是文件
    				File tmpFile = new File(outputDir + "/" + entry.getName());
    				createDirectory(tmpFile.getParent() + "/", null);// 创建输出目录
    				OutputStream out = null;
    				try {
    					out = new FileOutputStream(tmpFile);
    					int length = 0;
    					byte[] b = new byte[2048];
    					while ((length = tarIn.read(b)) != -1) {
    						out.write(b, 0, length);
    					}
    				} catch (IOException ex) {
    					throw ex;
    				} finally {
    					if (out != null) {
    						out.close();
    					}
    				}
    			}
    		}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		try {
    			if (tarIn != null) {
    				tarIn.close();
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    /**
    * 构建目录
    * @param outputDir
    * @param subDir
    */
    public static void createDirectory(String outputDir, String subDir) {//change static
    	File file = new File(outputDir);
    	if (!(subDir == null || subDir.trim().equals(""))) {// 子目录不为空
    		file = new File(outputDir + "/" + subDir);
    	}
    	if (!file.exists()) {
    		file.mkdirs();
    	}
    }
    /*
    public static void main(String[] args) throws IOException {

        String entry = "/Users/apple/compresstest/test3.txt";//需要压缩的文件夹
        String archive = archive(entry);//生成tar包
        String path = compressArchive(archive);//生成gz包

//      unCompressArchiveGz("C:\\Users\\yutao\\Desktop\\pageage\\test.tar.gz");//解压
    }*/
    
    
}