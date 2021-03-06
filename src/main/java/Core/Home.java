package Core;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Home extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField stf;
	private JButton sbt,lbt;
	
	public Home(){
		Container contentpane=this.getContentPane();
		contentpane.setLayout(new BorderLayout(3,3));
		
		stf=new JTextField(56);
		stf.setPreferredSize(new Dimension(300,30));
		stf.setFont(new Font("宋体",Font.PLAIN,15));
		
		lbt=new JButton("浏览");
		lbt.setPreferredSize(new Dimension(60,30));
		
		sbt=new JButton("转换");
		sbt.setPreferredSize(new Dimension(60,30));
		
		class ChooseEvent implements ActionListener{	
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fcdlg = new JFileChooser();
				fcdlg.setDialogTitle("请选择待搜索文档");
				fcdlg.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int returnval=fcdlg.showOpenDialog(null);
				if(returnval==JFileChooser.APPROVE_OPTION){
					String path=fcdlg.getSelectedFile().getPath();
					stf.setText(path);
				}
			}
		}
		lbt.addActionListener(new ChooseEvent());
		sbt.addActionListener(new EXEvents.ConvertEvent(this));
		
		JPanel npane=new JPanel();
	    npane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		JPanel cpane=new JPanel();		//列表面板
	    cpane.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
	   
	    npane.add(stf);
	    npane.add(lbt);
	    npane.add(sbt);
//	    cpane.add(jsp);
	    
		contentpane.add(npane,BorderLayout.NORTH);
		contentpane.add(cpane,BorderLayout.CENTER);
//		contentpane.add(solstar,BorderLayout.SOUTH);
		
//		this.setTableVisabel(false);
		
	    this.setTitle("添加文档");//窗体标签  
	    this.setSize(FrameSize.X,FrameSize.Y);//窗体大小  
	    this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)  
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//退出关闭JFrame  
	    this.setVisible(true);//显示窗体
	    this.setResizable(false); //锁定窗体
	}
	public String GetFilePath(){
		return stf.getText();
	}
}
