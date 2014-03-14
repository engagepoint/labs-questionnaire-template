package com.engagepoint.controller.page;

import com.engagepoint.model.questionnaire.TemplateBean;
import com.engagepoint.utils.XmlImportExport;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.*;
import java.util.List;

@Named
@RequestScoped
public class FileController implements Serializable {

    private static String pathToTempFile;
    private StreamedContent downloadFile;
    private UploadedFile uploadFile;

    public FileController() throws IOException {
        if (pathToTempFile.isEmpty()) {
            return;
        }
        File file = new File(pathToTempFile);
        file.deleteOnExit();
        InputStream stream = new FileInputStream(file);
        if (pathToTempFile.contains("xml")) {
            downloadFile = new DefaultStreamedContent(stream, "application/xml", "ExportedTemplates.xml");
        }
    }

    public static void setPathToTempFile(String pathToTempFile) {
        FileController.pathToTempFile = pathToTempFile;
    }

    public StreamedContent getDownloadFile() {
        return downloadFile;
    }

    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    /**
     * Create temporary XML file with many templates.
     */
    public static File createTempXml(List<TemplateBean> list) throws IOException {
        File tmpFile = File.createTempFile("temp", ".xml");
        XmlImportExport.exportXmlTemplates(list, tmpFile.getPath());
        tmpFile.deleteOnExit();
        return tmpFile;
    }
}
