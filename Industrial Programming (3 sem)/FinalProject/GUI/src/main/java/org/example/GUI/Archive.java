package org.example.gui;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Archive {

    public static String readStringFromInFileFromArchive(String archivePath, String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        ZipFile zipFile = new ZipFile(archivePath);
        ZipEntry entry = zipFile.getEntry(filePath);

        if (entry != null) {
            InputStream inputStream = zipFile.getInputStream(entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }

            reader.close();
            inputStream.close();
        } else {
            throw new IllegalArgumentException("File not found in the archive: " + filePath);
        }

        zipFile.close();

        return content.toString().replace("\r", "");
    }

    public static byte[] readBytesFromFileInFromArchive(String archivePath, String filePath) throws IOException {
        byte[] content = null;

        ZipFile zipFile = new ZipFile(archivePath);
        ZipEntry entry = zipFile.getEntry(filePath);

        if (entry != null) {
            InputStream inputStream = zipFile.getInputStream(entry);

            int fileSize = (int) entry.getSize();
            content = new byte[fileSize];
            inputStream.read(content);

            inputStream.close();
        } else {
            throw new IllegalArgumentException("File not found in the archive: " + filePath);
        }

        zipFile.close();

        return content;
    }

    public static void writeStringToArchive(String text, String archivePath, String filePath) throws IOException {
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archivePath));
        ZipEntry entry = new ZipEntry(filePath);
        zout.putNextEntry(entry);

        byte[] buffer = text.getBytes();

        zout.write(buffer);
        zout.closeEntry();
    }

    public static void writeBytesToArchive(byte[] output_byte, String archivePath, String filePath) throws IOException {
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archivePath));
        ZipEntry entry = new ZipEntry(filePath);
        zout.putNextEntry(entry);

        zout.write(output_byte);
        zout.closeEntry();
    }
}