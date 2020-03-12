package com.customized.libs.core.libs.lemur.map;

/**
 * @author yan
 */
public class ProvCityConvertTest {

    private static final String IMPORT_PATH = "datasource/lot-lat.xls";
    private static final String EXPORT_PATH = "datasource/lot-lat-converted.xls";

    public static void main(String[] args) {
        GisExcelParserConvertUtil.convertAndExportExcel(IMPORT_PATH, EXPORT_PATH);
    }
}
