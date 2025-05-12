package com.vak.oop.controller;

import com.vak.oop.model.ExportEntity;
import com.vak.oop.service.ExportService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.MFXTableView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.vak.oop.view.HomeView.logger;

public class ExportController implements Initializable {
    static public EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private ExportService exportService;
    @FXML
    private MFXTableView<ExportEntity> exportTable;
    @FXML
    private MFXTableColumn<ExportEntity> epid;
    @FXML
    private MFXTableColumn<ExportEntity> pdname;
    @FXML
    private MFXTableColumn<ExportEntity> username;
    @FXML
    private MFXTableColumn<ExportEntity> pdprice;
    @FXML
    private MFXTableColumn<ExportEntity> pdquantity;
    @FXML
    private MFXTableColumn<ExportEntity> pdtotalprice;
    @FXML
    private MFXTableColumn<ExportEntity> date;
    @FXML
    private HBox paginationContainer;
    private static final int ITEMS_PER_PAGE = 1;
    private int currentPage = 1;
    private int totalPages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exportService = new ExportService(entityManager);
        setCol();
        setWidth();
        loadExports();
    }

    private void setCol() {
        epid.setRowCellFactory(_ -> new MFXTableRowCell<>(ExportEntity::getEpid));
        pdname.setRowCellFactory(_ -> new MFXTableRowCell<>(export -> export.getProduct().getPdname()));
        username.setRowCellFactory(_ -> new MFXTableRowCell<>(export -> export.getUser().getUsername())); // corrected
        pdprice.setRowCellFactory(_ -> new MFXTableRowCell<>(ExportEntity::getPdprice));
        pdquantity.setRowCellFactory(_ -> new MFXTableRowCell<>(ExportEntity::getPdquantity));
        pdtotalprice.setRowCellFactory(_ -> new MFXTableRowCell<>(ExportEntity::getPdtotalprice));
        date.setRowCellFactory(_ -> new MFXTableRowCell<>(ExportEntity::getDate));
    }

    private void setWidth() {
        epid.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.10));
        pdname.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.25));
        username.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.15));
        pdprice.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.10));
        pdquantity.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.10));
        pdtotalprice.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.20));
        date.prefWidthProperty().bind(exportTable.widthProperty().multiply(0.10));
    }

    private void loadExports() {
        int totalItems = exportService.getTotalExportCount();
        totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
        currentPage = Math.max(1, Math.min(currentPage, totalPages));
        List<ExportEntity> pageItems = exportService.getExportsByPage(currentPage, ITEMS_PER_PAGE);
        exportTable.getItems().setAll(pageItems);
        updatePaginationUI();
    }

    private void updatePaginationUI() {
        paginationContainer.getChildren().clear();
        if (currentPage > 1) {
            paginationContainer.getChildren().add(createNavButton("←", 1));
        }
        if (currentPage > 2) {
            paginationContainer.getChildren().add(new Label("..."));
        }
        if (currentPage > 2) {
            paginationContainer.getChildren().add(createPageButton(currentPage - 1));
        }
        paginationContainer.getChildren().add(createPageLabel(currentPage));
        if (currentPage < totalPages - 1) {
            paginationContainer.getChildren().add(createPageButton(currentPage + 1));
        }
        if (currentPage < totalPages - 1) {
            paginationContainer.getChildren().add(new Label("..."));
        }
        if (currentPage < totalPages) {
            paginationContainer.getChildren().add(createNavButton("→", totalPages));
        }
    }

    private MFXButton createPageButton(int page) {
        MFXButton btn = new MFXButton(String.valueOf(page));
        btn.getStyleClass().add("paginationLabel");
        btn.setOnAction(_ -> {
            currentPage = page;
            loadExports();
        });
        return btn;
    }

    private Label createPageLabel(int page) {
        Label lbl = new Label(String.valueOf(page));
        lbl.getStyleClass().add("paginationLabel");
        return lbl;
    }

    private MFXButton createNavButton(String label, int targetPage) {
        MFXButton btn = new MFXButton(label);
        btn.getStyleClass().add("Btn");
        btn.setOnAction(_ -> {
            currentPage = targetPage;
            loadExports();
        });
        return btn;
    }

    @FXML
    private void exportToExcel() {
        List<ExportEntity> exports = exportService.getAllExports();
        if (exports.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No Data To Export!", ButtonType.OK);
            alert.setHeaderText("");
            alert.showAndWait();
            return;
        }
        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("Exports");
            org.apache.poi.xssf.usermodel.XSSFCellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.WHITE.getIndex());
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.BLACK.getIndex());
            headerStyle.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
            headerStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            headerStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            org.apache.poi.xssf.usermodel.XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            cellStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
            org.apache.poi.xssf.usermodel.XSSFRow headerRow = sheet.createRow(0);
            String[] headers = {"EXPORT ID", "PRODUCT NAME", "USERNAME", "PRICE", "QUANTITY", "TOTAL PRICE", "DATE"};
            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.xssf.usermodel.XSSFCell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            for (int i = 0; i < exports.size(); i++) {
                ExportEntity export = exports.get(i);
                org.apache.poi.xssf.usermodel.XSSFRow row = sheet.createRow(i + 1);
                createCell(row, 0, export.getEpid(), cellStyle);
                createCell(row, 1, export.getProduct().getPdname(), cellStyle);
                createCell(row, 2, export.getUser().getUsername(), cellStyle);
                createCell(row, 3, export.getPdprice(), cellStyle);
                createCell(row, 4, export.getPdquantity(), cellStyle);
                createCell(row, 5, export.getPdtotalprice(), cellStyle);
                createCell(row, 6, export.getDate().toString(), cellStyle);
            }
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("");
            fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            java.io.File file = fileChooser.showSaveDialog(exportTable.getScene().getWindow());
            if (file != null) {
                try (java.io.FileOutputStream fileOut = new java.io.FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Successful!", ButtonType.OK);
                successAlert.setHeaderText("");
                successAlert.showAndWait();
            }
        } catch (Exception e) {
            logger.error("", e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed!", ButtonType.OK);
            errorAlert.setHeaderText("");
            errorAlert.showAndWait();
        }
    }

    private void createCell(org.apache.poi.xssf.usermodel.XSSFRow row, int column, Object value, org.apache.poi.xssf.usermodel.XSSFCellStyle style) {
        org.apache.poi.xssf.usermodel.XSSFCell cell = row.createCell(column);
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            cell.setCellValue(String.valueOf(value));
        }
        cell.setCellStyle(style);
    }

    @FXML
    private void exportToPdf() {
        ExportEntity selectedExport = exportTable.getSelectionModel().getSelectedValue();
        if (selectedExport == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please Select An Item To Export!", ButtonType.OK);
            alert.setHeaderText("");
            alert.showAndWait();
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        java.io.File file = fileChooser.showSaveDialog(exportTable.getScene().getWindow());
        if (file != null) {
            try (com.lowagie.text.Document document = new com.lowagie.text.Document()) {
                com.lowagie.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(file));
                document.open();
                com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 24, com.lowagie.text.Font.BOLD, new java.awt.Color(0, 0, 0));
                com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("BILL", titleFont);
                title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);
                com.lowagie.text.Font smallFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.NORMAL);
                com.lowagie.text.Paragraph date = new com.lowagie.text.Paragraph("Date: " + selectedExport.getDate(), smallFont);
                date.setAlignment(com.lowagie.text.Element.ALIGN_RIGHT);
                date.setSpacingAfter(20);
                document.add(date);
                com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(2);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);
                addRow(table, "Username:", selectedExport.getUser().getUsername());
                addRow(table, "Export ID:", String.valueOf(selectedExport.getEpid()));
                addRow(table, "Product Name:", selectedExport.getProduct().getPdname());
                addRow(table, "Price:", "$" + selectedExport.getPdprice());
                addRow(table, "Quantity:", String.valueOf(selectedExport.getPdquantity()));
                addRow(table, "Total Price:", "$" + selectedExport.getPdtotalprice());
                document.add(table);
                com.lowagie.text.Paragraph footer = new com.lowagie.text.Paragraph("Thank You For Using Our Service!", smallFont);
                footer.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
                footer.setSpacingBefore(30);
                document.add(footer);
                document.close();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Successful!", ButtonType.OK);
                successAlert.setHeaderText(null);
                successAlert.showAndWait();
            } catch (Exception e) {
                logger.error("", e);
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Failed!", ButtonType.OK);
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
            }
        }
    }

    private void addRow(com.lowagie.text.pdf.PdfPTable table, String key, String value) {
        com.lowagie.text.pdf.PdfPCell cell1 = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(key));
        cell1.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        table.addCell(cell1);
        com.lowagie.text.pdf.PdfPCell cell2 = new com.lowagie.text.pdf.PdfPCell(new com.lowagie.text.Phrase(value));
        cell2.setBorder(com.lowagie.text.Rectangle.NO_BORDER);
        table.addCell(cell2);
    }
}