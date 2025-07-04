package ce326.hw3;

import java.io.*;

class PPMFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        String name = file.getName();
        if(name.length()>4 && name.substring(name.length()-4, name.length()).toLowerCase().equals(".ppm"))
            return true;
        return false;
    }

    public String getDescription() { return "PPM File"; }
}

class YUVFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        String name = file.getName();
        if(name.length()>4 && name.substring(name.length()-4, name.length()).toLowerCase().equals(".yuv"))
            return true;
        return false;
    }

    public String getDescription() { return "YUV File"; }
}
