package com.phoenixhell.app.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

  public static String readFileToString(String path) {
    try {
      return Files.readString(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException("读取文件失败", e);
    }
  }

  public static void writeStringToFile(String content, String path) {
    try {
      Files.writeString(Paths.get(path), content);
    } catch (IOException e) {
      throw new RuntimeException("写入文件失败", e);
    }
  }

  public static void copy(String srcPath, String destPath) {
    Path src = Paths.get(srcPath);
    Path dest = Paths.get(destPath);
    try {
      if (Files.isDirectory(src)) {
        // 复制目录内容
        Files.walkFileTree(src, new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path targetDir = dest.resolve(src.relativize(dir));
            if (!Files.exists(targetDir)) {
              Files.createDirectory(targetDir);
            }
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.copy(file, dest.resolve(src.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
            return FileVisitResult.CONTINUE;
          }
        });
      } else {
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new RuntimeException("复制失败", e);
    }
  }

  public static void delete(String path) {
    Path p = Paths.get(path);
    try {
      if (Files.isDirectory(p)) {
        Files.walkFileTree(p, new SimpleFileVisitor<>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            return FileVisitResult.CONTINUE;
          }

          @Override
          public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            return FileVisitResult.CONTINUE;
          }
        });
      } else {
        Files.deleteIfExists(p);
      }
    } catch (IOException e) {
      throw new RuntimeException("删除失败", e);
    }
  }

  public static boolean exists(String path) {
    return Files.exists(Paths.get(path));
  }

  public static void mkdirs(String path) {
    Path p = Paths.get(path);
    try {
      if (!Files.exists(p)) {
        Files.createDirectories(p);
      }
    } catch (IOException e) {
      throw new RuntimeException("创建目录失败", e);
    }
  }

  public static long size(String path) {
    try {
      return Files.size(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException("获取文件大小失败", e);
    }
  }

  public static List<Path> listFiles(String dirPath) {
    List<Path> fileList = new ArrayList<>();
    Path dir = Paths.get(dirPath);
    try {
      Files.walkFileTree(dir, new SimpleFileVisitor<>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
          fileList.add(file);
          return FileVisitResult.CONTINUE;
        }
      });
    } catch (IOException e) {
      throw new RuntimeException("遍历文件失败", e);
    }
    return fileList;
  }

  public static String getExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex >= 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex + 1);
    }
    return "";
  }

  public static void move(String srcPath, String destPath) {
    Path src = Paths.get(srcPath);
    Path dest = Paths.get(destPath);
    try {
      Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException("移动失败", e);
    }
  }

  public static boolean rename(String srcPath, String newName) {
    Path src = Paths.get(srcPath);
    Path target = src.resolveSibling(newName);
    try {
      Files.move(src, target, StandardCopyOption.REPLACE_EXISTING);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
