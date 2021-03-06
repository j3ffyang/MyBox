package mara.mybox.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.MessageFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import mara.mybox.data.PdfInformation;
import mara.mybox.data.VisitHistory;
import mara.mybox.tools.FileTools;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.logger;
import static mara.mybox.value.AppVariables.message;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.fit.pdfdom.PDFDomTreeConfig;
import org.fit.pdfdom.resource.IgnoreResourceHandler;
import thridparty.PDFResourceToDirHandler;

/**
 * @Author Mara
 * @CreateDate 2019-9-1
 * @License Apache License Version 2.0
 */
public class PdfConvertHtmlsBatchController extends PdfBatchController {

    protected boolean separatedHtml;
    protected SaveType fontSaveType, imageSaveType;
    protected PDFDomTreeConfig domConfig;

    @FXML
    protected ToggleGroup saveGroup, fontGroup, imageGroup;
    @FXML
    protected RadioButton separateRadio;
    @FXML
    protected CheckBox appendColorCheck, appendCompressionCheck, appendQualityCheck, appendDensityCheck;

    protected enum SaveType {
        Embed, Ignore, File
    }

    public PdfConvertHtmlsBatchController() {
        baseTitle = AppVariables.message("PdfConvertHtmlsBatch");

        targetPathKey = "HtmlFilePath";

        TargetPathType = VisitHistory.FileType.Html;
        TargetFileType = VisitHistory.FileType.Html;
    }

    @Override
    public void initOptionsSection() {
        try {
            domConfig = PDFDomTreeConfig.createDefaultConfig();

            saveGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    separatedHtml = separateRadio.isSelected();
                }
            });
            separatedHtml = separateRadio.isSelected();

            fontGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    String selected = ((RadioButton) newValue).getText();
                    if (message("SaveAsFile").equals(selected)) {
                        fontSaveType = SaveType.File;
                    } else if (message("Embed").equals(selected)) {
                        fontSaveType = SaveType.Embed;
                    } else if (message("Ignore").equals(selected)) {
                        fontSaveType = SaveType.Ignore;
                    }
                }
            });
            fontSaveType = SaveType.File;

            imageGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                @Override
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    String selected = ((RadioButton) newValue).getText();
                    if (message("SaveAsFile").equals(selected)) {
                        imageSaveType = SaveType.File;
                    } else if (message("Embed").equals(selected)) {
                        imageSaveType = SaveType.Embed;
                    } else if (message("Ignore").equals(selected)) {
                        imageSaveType = SaveType.Ignore;
                    }
                }
            });
            imageSaveType = SaveType.File;

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    @Override
    public String handleFile(File srcFile, File targetPath) {
        int generated = 0;
        doc = null;
        try {
            countHandling(srcFile);
            currentParameters.currentSourceFile = srcFile;
            if (!isPreview) {
                PdfInformation info = tableData.get(currentParameters.currentIndex);
                actualParameters.fromPage = info.getFromPage();
                if (actualParameters.fromPage <= 0) {
                    actualParameters.fromPage = 1;
                }
                actualParameters.toPage = info.getToPage();
                actualParameters.password = info.getUserPassword();
                actualParameters.startPage = actualParameters.fromPage;
                actualParameters.currentPage = actualParameters.fromPage;
            }

            try ( PDDocument pd = PDDocument.load(currentParameters.currentSourceFile,
                    currentParameters.password, AppVariables.pdfMemUsage)) {
                doc = pd;

                if (currentParameters.toPage <= 0 || currentParameters.toPage > doc.getNumberOfPages()) {
                    currentParameters.toPage = doc.getNumberOfPages();
                }
                int total = currentParameters.toPage - currentParameters.fromPage + 1;
                updateFileProgress(0, total);
                currentParameters.currentTargetPath = targetPath;

                String filePrefix = FileTools.getFilePrefix(currentParameters.currentSourceFile.getName());
                if (separatedHtml) {
                    currentParameters.currentTargetPath = new File(targetPath.getAbsolutePath() + File.separator + filePrefix);
                    if (!currentParameters.currentTargetPath.exists()) {
                        currentParameters.currentTargetPath.mkdirs();
                    }
                    for (currentParameters.currentPage = currentParameters.startPage;
                            currentParameters.currentPage <= currentParameters.toPage; currentParameters.currentPage++) {
                        if (task.isCancelled()) {
                            break;
                        }
                        String fileName = currentParameters.currentTargetPath + File.separator
                                + filePrefix + "_p" + currentParameters.currentPage;
                        File htmlFile = writeHhml(fileName, currentParameters.currentPage, currentParameters.currentPage);
                        if (htmlFile != null) {
                            generated++;
                            targetFileGenerated(htmlFile);
                        }
                        updateFileProgress(currentParameters.currentPage - currentParameters.fromPage + 1, total);
                    }

                } else {
                    String fileName = currentParameters.currentTargetPath + File.separator + filePrefix;
                    File htmlFile = writeHhml(fileName, currentParameters.startPage, currentParameters.toPage);
                    if (htmlFile != null) {
                        generated++;
                        targetFileGenerated(htmlFile);
                    }
                    updateFileProgress(total, total);

                }

                doc.close();
            }
            currentParameters.startPage = 1;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        updateInterface("CompleteFile");
        return MessageFormat.format(AppVariables.message("HandlePagesGenerateNumber"),
                currentParameters.currentPage - currentParameters.fromPage, generated);
    }

    protected File writeHhml(String fileName, int start, int end) {
        try {
            File htmlFile = new File(fileName + ".html");
            File subPath = new File(fileName);
            if (fontSaveType == SaveType.File || imageSaveType == SaveType.File) {
                subPath.mkdirs();
            }
            switch (fontSaveType) {
                case File:
                    domConfig.setFontHandler(new PDFResourceToDirHandler(subPath));
                    break;
                case Embed:
                    domConfig.setFontHandler(PDFDomTreeConfig.embedAsBase64());
                    break;
                default:
                    domConfig.setFontHandler(new IgnoreResourceHandler());
                    break;
            }
            switch (imageSaveType) {
                case File:
                    domConfig.setImageHandler(new PDFResourceToDirHandler(subPath));
                    break;
                case Embed:
                    domConfig.setImageHandler(PDFDomTreeConfig.embedAsBase64());
                    break;
                default:
                    domConfig.setImageHandler(new IgnoreResourceHandler());
                    break;
            }
            PDFDomTree parser = new PDFDomTree(domConfig);
            parser.setStartPage(start);                                       // 1-based
            parser.setEndPage(end);
            try ( Writer output = new PrintWriter(htmlFile, "utf-8")) {
                parser.writeText(doc, output);
                return htmlFile;
            } catch (Exception e) {
//                logger.debug(e.toString());
                return null;
            }
        } catch (Exception e) {
//            logger.error(e.toString());
            return null;
        }
    }

}
