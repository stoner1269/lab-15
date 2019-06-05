package net;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class NIODemo {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Not enough amount of command line arguments");
            return;
        }
        String srcfile = getSrcFile(args); // откуда
        String dstfile = getDstFile(args); // куда
        int bufsize = getBufferSize(args); // размер буффера
        try (FileChannel inputChannel = new FileInputStream(srcfile).getChannel(); FileChannel outputChannel = new FileOutputStream(dstfile).getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(bufsize);
            while (true) {
                int read = inputChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getSrcFile(String[] args) {
        String[] strings;
        for (String s : args) {
            strings = s.split("=");
            if (strings[0].equals("--srcfile"))
                return String.valueOf(Paths.get(strings[1]));
        }
        return null;
    }

    private static String getDstFile(String[] args) {
        String[] strings;
        for (String s : args) {
            strings = s.split("=");
            if (strings[0].equals("--dstfile"))
                return String.valueOf(Paths.get(strings[1]));
        }
        return null;
    }

    private static int getBufferSize(String[] args) {
        String[] strings;
        for (String s : args) {
            strings = s.split("=");
            if (strings[0].equals("--bufsize"))
                return Integer.parseInt(strings[1]);
        }
        return -1;
    }
}