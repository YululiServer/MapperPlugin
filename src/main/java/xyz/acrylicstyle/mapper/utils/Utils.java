package xyz.acrylicstyle.mapper.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class Utils {
    private Utils() {}

    public static String uploadFile(String zipFileName) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://upload.acrylicstyle.xyz/upload.php");
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(zipFileName);
        } catch (FileNotFoundException e) {
            Log.error("Couldn't read file");
            e.printStackTrace();
            return null;
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addBinaryBody("upfile", inputStream, ContentType.create("application/zip"), zipFileName);
        builder.addTextBody("type", "json", ContentType.TEXT_PLAIN);
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        try {
            HttpResponse response = client.execute(post);
            return inputStreamToString(response.getEntity().getContent());
        } catch (IOException e) {
            Log.error("Couldn't upload file!");
            e.printStackTrace();
        }
        return null;
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isReader);
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = reader.readLine()) != null) sb.append(str);
        return sb.toString();
    }

    private static List<String> fileList = new ArrayList<>();

    public static void compressDirectory(String dir, String zipFile) {
        File directory = new File(dir);
        getFileList(directory);
        try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            HashSet<String> names = new HashSet<>();
            for (String filePath : fileList) {
                if (names.add(filePath)) {
                    String name = filePath.substring(directory.getAbsolutePath().length() + 1);
                    ZipEntry zipEntry = new ZipEntry(name);
                    zos.putNextEntry(zipEntry);
                    try (FileInputStream fis = new FileInputStream(filePath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) zos.write(buffer, 0, length);
                        zos.closeEntry();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            names.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }

    public static ItemStack getItemStack(Material material, int amount, String displayName) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item.setItemMeta(meta);
        return item;
    }
}
