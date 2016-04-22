package test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Parser;

public class Test {
    public static void main(String[] args) throws IOException {
	
	readfile();
	
	
	//downLoad1();
	System.out.println("---------------------------------------------------------------");
	
	/*String str = readFileUTF_8("d:/zc/zc.html");
	System.out.println(str);*/
    }
    
    static void downLoad(){
	URL url = null;
	URLConnection conn = null;
	String content = null;
	try {
		url = new URL("http://www.ofcard.com/showcp/cp22.html");
		conn = url.openConnection();
		conn.setDoOutput(true);
		InputStream in = null;
		try{
			in = url.openStream();
		} catch(FileNotFoundException e){
			
			return ;
		}
		
			BufferedInputStream buffin = new BufferedInputStream(in);
	    
	   
        		BufferedOutputStream buffout = new BufferedOutputStream(new FileOutputStream("d:/zc/zc.html"));
        		
                	   byte[] bt = new byte[4*1024];
                	   int len;
                	   while ((len = buffin.read(bt, 0, bt.length)) != -1) {
                	       buffout.write(bt, 0, len);
                	   }
                	   
                	   buffin.close();
                	   buffout.close();
        	   
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    }
    
    static void downLoad1(){
	URL url = null;
	URLConnection conn = null;
	String content = null;
	try {
		url = new URL("http://www.ofcard.com/showcp/cp22.html");
		conn = url.openConnection();
		conn.setDoOutput(true);
		InputStream in = null;
		try{
			in = url.openStream();
		} catch(FileNotFoundException e){
			
			return ;
		}
		
			BufferedReader bReader = new BufferedReader(new InputStreamReader(in,"gb2312"));
			String rLine ;
	   
			FileOutputStream fo = new FileOutputStream("d:/zc/zc.html");//    "d:/zc/zc.html"
			OutputStreamWriter writer = new OutputStreamWriter(fo,"utf-8");
			
			PrintWriter pw = new PrintWriter(writer);
			while ((rLine = bReader.readLine()) != null) {
				String tmp_rLine = rLine;
				int str_len = tmp_rLine.length();
				if (str_len > 0) {
				
					pw.println(tmp_rLine);
					pw.flush();
				}
				tmp_rLine = null;
			}
			in.close();
			pw.close();
        	   
        	} catch (MalformedURLException e) {
        		e.printStackTrace();
        	} catch (IOException e) {
        		e.printStackTrace();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    }
    
    static void readfile() throws IOException{
	StringBuffer s = new StringBuffer();
	FileInputStream fo = new FileInputStream("d:/zc/zc.html");
	String rLine = null;
	BufferedReader bReader = new BufferedReader(new InputStreamReader(
			fo,"UTF-8"));
	
	while ((rLine = bReader.readLine()) != null) {
		String tmp_rLine = rLine;
		
		int str_len = tmp_rLine.length();
		if (str_len > 0) {
			System.out.println(tmp_rLine);
			s.append(tmp_rLine);
		}
		tmp_rLine = null;
	}
	
	bReader.close();
	fo.close();
	
	
	/*Parser parser = Parser.createParser(s.toString(), "UTF-8");
	parser.*/
    }
    
    private static String readFileUTF_8(String templet) {
	  String templetContent = "";

	  try {
	   FileInputStream fileinputstream = new FileInputStream(templet);
	   int length = fileinputstream.available();
	   byte bytes[] = new byte[length];
	   fileinputstream.read(bytes);
	   fileinputstream.close();
	   templetContent = new String(bytes, "UTF-8");
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	  return templetContent;
	 }


}
