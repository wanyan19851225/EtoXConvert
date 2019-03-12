package Core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

	public class EXEvents {

	public static class ConvertEvent implements ActionListener{
		
		private Home h;
		public ConvertEvent(Home h){
			this.h=h;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String s=h.GetFilePath();
			if(!s.isEmpty()){
				EXConvertProgress pb=new EXConvertProgress(this.h);
				int size=pb.GetFileNum();
//				jf.solstar.setProgressBarMaximum(size);
				pb.execute();
			}
			else
				JOptionPane.showMessageDialog(null, "请选择要添加索引的文档", "警告", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
