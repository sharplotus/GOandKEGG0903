package GOAnalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ivy
 * 
 *         2018.8.27
 * 
 *         从classfyResult内每个物种文件夹中获取所有蛋白质文件的文件名称，保存为background文件
 *
 */
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String root = "E:\\WM\\tts9\\workspace\\DAP\\downloads\\2018_08_02\\classfyResult\\";
		File maindir = new File(root);// 外层的文件夹

		String[] dirlist = maindir.list();

		for (int j = 0; j < dirlist.length; j++) {
			File file = new File(root + dirlist[j]); // 物种文件夹

			String fileName = "backgroundList_" + dirlist[j];//background文件

			File namefile = new File(root + dirlist[j], fileName+".txt");

			if (!file.exists()) {

				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(namefile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				String finname = null;
				int dot = filelist[i].lastIndexOf('.');
				if ((dot > -1) && (dot < (filelist[i].length()))) {
					finname = filelist[i].substring(0, dot);
				}
				
				try {
					if(!finname.equals(fileName)){
						fileWriter.write(finname + "\r\n");
						System.out.println(finname); // 打印到控制台显示文件夹下全部文件名
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				fileWriter.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		System.out.println("fine!");
	}

}
