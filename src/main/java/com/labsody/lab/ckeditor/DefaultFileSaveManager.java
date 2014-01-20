package com.labsody.lab.ckeditor;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by guava on 1/20/14.
 */
public class DefaultFileSaveManager implements FileSaveManager {

    @Override
    public String saveFile(FileItem fileItem, String imageBaseDir, String imageDomain) {
        String originalFileName = FilenameUtils.getName(fileItem.getName());
        String relUrl;
        // filename
        String subDir = File.separator
                + DirectoryPathManager
                .getDirectoryPathByDateType(DirectoryPathManager.DIR_DATE_TYPE.DATE_POLICY_YYYY_MM);
        String fileName = RandomStringUtils.randomAlphanumeric(20)
                + "."
                + StringUtils.lowerCase(StringUtils.substringAfterLast(
                originalFileName, "."));

        File newFile = new File(imageBaseDir + subDir + fileName);
        File fileToSave = DirectoryPathManager.getUniqueFile(newFile
                .getAbsoluteFile());

        try {
            FileUtils.writeByteArrayToFile(fileToSave, fileItem.get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String savedFileName = FilenameUtils.getName(fileToSave
                .getAbsolutePath());
        relUrl = StringUtils.replace(subDir, "\\", "/")
                + savedFileName;
        return imageDomain + relUrl;
    }
}
