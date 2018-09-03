package GOAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class FunctionMapping {

	public static void main(String[] args) {
		String root ="E:\\WM\\GOandKEGG\\data\\GO\\term\\";
		String spename = "HUMAN";
		FunctionalFeaturesUtil_1 getnm=new FunctionalFeaturesUtil_1();
		getnm.test(spename,getnm);
		System.out.println(root);
		try {
			GOMapping(root + "GoC.txt", 
					root + "mapping\\mapping_GoC.txt");
			GOMapping(root + "GoP.txt", 
					root + "mapping\\mapping_GoP.txt");
			GOMapping(root + "GoF.txt", 
					root + "mapping\\mapping_GoF.txt");
			KEGG_Mapping(getnm,spename,root + "KEGG.txt",
					root + "mapping\\mapping_KEGG.txt");
		} catch (Exception e) {
				e.printStackTrace();
		}
	}
		
	public static void KEGG_Mapping(FunctionalFeaturesUtil_1 getnm,String spename, String infile, String outfile) throws Exception {
		
		
		System.out.println(outfile);
		HttpClient httpclient = new DefaultHttpClient();
		BufferedReader br = new BufferedReader(new FileReader(new File(infile)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outfile)));
		bw.write("ID" + "\t" + "count"+"\t" +"M"+"\t"+"Pvalue"+ "\t"+"Term"+ "\t"+"n" + "\t"+"m"+ "\n");
		String tmpstr = "";
		while((tmpstr = br.readLine()) != null) {
			tmpstr = tmpstr.trim();
			if(tmpstr.equals(""))
				continue;
			String[] arr = tmpstr.split("\\s+");
			HttpGet submitpost = new HttpGet("http://www.genome.jp/dbget-bin/www_bget?pathway+" + arr[0].substring(5));
			ResponseHandler<String> handler = new BasicResponseHandler();
			String resstr = httpclient.execute(submitpost, handler);
			Document doc = Jsoup.parse(resstr.substring(resstr.indexOf("<nobr>Name</nobr></th>") + 22));
//			System.out.println(doc);
			Elements links = doc.select("div");	
//			System.out.println(links.get(0).text());
			bw.write(tmpstr + "\t" + links.get(0).text() + "\t"+getnm.getn + "\t"+getnm.getm+ "\n");
			bw.flush();
			System.out.println(arr[0] + "\t" + links.get(0).text());
	        submitpost.releaseConnection();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	
	
	public static void GOMapping(String infile, String outfile) throws Exception {
		System.out.println(outfile);
		HttpClient httpclient = new DefaultHttpClient();
		//HttpClient httpclient = new HttpClient();
		BufferedReader br = new BufferedReader(new FileReader(new File(infile)));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outfile)));
		String tmpstr = "";
		while((tmpstr = br.readLine()) != null) {
			tmpstr = tmpstr.trim();
			if(tmpstr.equals(""))
				continue;
			String[] arr = tmpstr.split("\\s+");
			System.out.println(arr[0]);
			HttpGet submitpost = new HttpGet("http://amigo.geneontology.org/amigo/term/" + arr[0]);
			ResponseHandler<String> handler = new BasicResponseHandler();
			String resstr = httpclient.execute(submitpost, handler);
			Document doc = Jsoup.parse(resstr);
//			System.out.println(doc);
			Elements links = doc.select("h1");	
//			System.out.println(links.get(0).text());
			bw.write(tmpstr + "\t" + links.get(0).text() + "\n");
			bw.flush();
			System.out.println(arr[0] + "\t" + links.get(0).text());
	        submitpost.releaseConnection();
		}
		bw.flush();
		bw.close();
		br.close();
	}
	

}
