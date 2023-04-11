package mvcdemo.util;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * @author Xenqiao
 * @create 2023/3/21 13:47
 */
public class CopyJLabel implements MouseListener {
    private JLabel jLabel;

    /** 该函数使用范围比较小，主要目的在于实现 JDialog界面窗口的 JLabel文本显示哈希值的双击复制功能
     *  **/
    public CopyJLabel(JLabel jLabel) {
        this.jLabel = jLabel;
    }
        @Override
        public void mouseClicked (MouseEvent e){

            if (e.getClickCount() == 2) {
                String clipboardStr = jLabel.getText();
                setClipboardString(clipboardStr);
                jLabel.setOpaque(false);
                jLabel.setForeground(Color.BLUE);
                jLabel.setFont(new Font("楷体", Font.BOLD, 25));
                getClipboardString();
            }
        }

        @Override
        public void mousePressed (MouseEvent e){

        }

        @Override
        public void mouseReleased (MouseEvent e){

        }

        @Override
        public void mouseEntered (MouseEvent e){

        }


        @Override
        public void mouseExited (MouseEvent e){
            // TODO Auto-generated method stub
            jLabel.setOpaque(true);
            jLabel.setForeground(Color.black);
            jLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        }


    /**
     * 获取剪贴板内容(粘贴)
     */
    public String getClipboardString() {
        //获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //获取剪贴板内容
        Transferable trans = clipboard.getContents(clipboard);
        if(trans != null) {
            //判断剪贴板内容是否支持文本
            if(trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String clipboardStr = null;

                //获取剪贴板的文本内容
                try {
                    clipboardStr = (String) trans.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return clipboardStr;
            }
        }
        return null;
    }

    /**
     * 设置剪贴板内容(复制)
     */
    public static void setClipboardString(String str) {
        //获取协同剪贴板，单例
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //封装文本内容
        Transferable trans = new StringSelection(str);
        //把文本内容设置到系统剪贴板上
        clipboard.setContents(trans, null);
    }
}
